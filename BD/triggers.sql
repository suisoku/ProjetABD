drop trigger trigger_codePromo;
drop trigger trigger_GenerationCodePromo;
drop trigger trigger_Statut_Commande;
drop trigger trigger_insert_Commande;
drop trigger trigger_delete_commande;
drop trigger trigger_insert_impression;
drop trigger trigger_Desactivation_Client;
drop trigger trigger_ImageUsed;
drop trigger trigger_file_attenteImage;
drop trigger commandePossible1;
drop trigger mailSending;
drop trigger commandePossible;
--call dbms_scheduler.drop_job('deleteNoUsedImagesJob');
drop procedure deleteNoUsedImages;
drop function countDays;





-- Function that count the difference between the date of today and the date passed in parameters
create or replace function countDays(date IN date) RETURN
  number IS
Begin
  return trunc(sysdate - date);
end;
/

-- Procedure qui supprime les images non utilisées (10 jours) --

create or replace procedure deleteNoUsedImages IS
begin
  delete
  from Image
  where countDays(dateUtilisation) >= 10;
end;
/



/*begin
  DBMS_SCHEDULER.CREATE_JOB(
      job_name => 'deleteNoUsedImagesJob',
      job_type => 'STORED_PROCEDURE',
      job_action => 'deleteNoUsedImages',
      start_date => systimestamp,
      repeat_interval => 'FREQ = DAILY',
      end_date => null,
      auto_drop => false,
      comments => 'image(s) deleted');
END;
/
*/

-- Trigger which updates Image Date when ever the image is used in photo_Impression --
CREATE or Replace trigger updateImageTable
  after insert or update
  on PHOTO_IMPRESSION
  for each row
begin
  update Image
  set dateUtilisation = sysdate
  where chemin in (select CHEMIN from photo where PHOTO.IDPHOTO = :new.IDPHOTO);
end;
/

-- Trigger which updates Image Date when ever the image is used in photo --
/*CREATE or Replace trigger updateImageTable1
  after insert or update
  on PHOTO
  for each row
begin
  update Image
  set dateUtilisation = sysdate
  where chemin = CHEMIN;
end;
/
*/


-- Trigger Une commande n'est possible que si le support d'impression est en stock

create or replace trigger commandePossible
  before insert or update on COMMANDE_IMPRESSION
  for each row
  declare
    nb number(4);
  begin
    select count(*) into nb
    from (select IDIMPRESSION,IDPRODUIT from CADRE
                                      union
                                      select IDIMPRESSION,IDPRODUIT from AGENDA
                                      union
                                      select IDIMPRESSION,IDPRODUIT from CALENDRIER
                                      union
                                      select IDIMPRESSION,IDPRODUIT from ALBUM
                                      union
                                      select IDIMPRESSION,IDPRODUIT from TIRAGE) Imp
        join (select * from CADREPRODUIT
                                      union
                                      select * from AGENDAPRODUIT
                                      union
                                      select * from CALENDRIERPRODUIT
                                      union
                                      select * from ALBUMPRODUIT
                                      union
                                      select * from TIRAGEPRODUIT) Inv
      on imp.IDPRODUIT = Inv.IDPRODUIT join INVENTAIRE i on inv.IDPRODUIT = i.IDPRODUIT
    where i.STOCK < :new.QUANTITE and :new.IDIMPRESSION = imp.IDIMPRESSION  ;
    if nb <> 0 then raise_application_error(-20100,'Produit non disponible');end if;
  end;
/


-- Trigger Mailing simulation --

create or replace trigger mailSending
  after delete or update of idclient, CHEMIN, PARTAGER on IMAGE
  for each row
  declare
     cursor clients is select c.NOM, c.MAIL, p.CHEMIN
     from  photo p
      join PHOTO_IMPRESSION pi on p.IDPHOTO = pi.IDPHOTO
      join IMPRESSION im on pi.IDIMPRESSION = im.IDIMPRESSION
      join CLIENT c on im.IDCLIENT = c.IDCLIENT
     where :new.CHEMIN = p.CHEMIN and c.IDCLIENT <> :new.IDCLIENT  and :new.PARTAGER = 0;

    client clients%ROWTYPE;

    begin
    open clients;
    fetch clients into client;
    while (clients%found) LOOP
      DBMS_output.put_line('sending mail to ' ||client.NOM ||' at ' || client.MAIL || ' because of this image, it is deleted or updated: ' || client.CHEMIN);
      fetch clients into client;
    end loop;
    close clients;
  end;
/

-- Une commande est possible sur des impressions dont les photos utilisées --
-- apartients au client qui a effectué la cmd, ou partagées par un autre client --


/*create or replace trigger commandePossible1
  before insert or update on COMMANDE_IMPRESSION
  for each row
  declare
    nb number(4);
  begin
    select Count(*) into nb
    from PHOTO_IMPRESSION pi join CLIENT_USE_IMAGE cui1 on pi.IDIMPRESSION = cui1.IDIMPRESSION
      where :new.IDIMPRESSION = cui1.IDIMPRESSION and pi.IDPHOTO not in ( select cui.IDPHOTO
                                  FROM CLIENT_USE_IMAGE cui join commande c on cui.IDCLIENT = c.IDCLIENT
                                  and :new.IDCOMMANDE = c.IDCOMMANDE);
    if (nb <> 0 )
      then raise_application_error(-20100,' image non paratgée');
    end if;
  end;
/ */


--Des codes de promo sont donnés et ne sont utilisables qu'une seule fois par client
-- pour une future commande (offre promotionnelle). Ce code est ensuite automatiquement supprimé.
create or replace trigger trigger_codePromo
after update of used on CodePromo
declare
  nbCodeSupprimer int;
begin

  --V1 : Il y a des codePromo utilisés?
  select nvl(count(*),0) into nbCodeSupprimer
  from CodePromo
  where used ='1';

  if nbCodeSupprimer>0 then
    delete from codePromo where used='1';
  end if;
  --Fin V1
end;
/

-- Génération d'un code promo pour un client lorsque sa facture dépasse 100€.
-- Ce code sera supprimé automatiquement lorsque le client l'utilisera dans une commande
create or replace trigger trigger_GenerationCodePromo
after insert on Commande
for each row
declare
  newcode int;
begin

  --V1 Verification que le montant de la commande a depasser les 100euros
  if :new.montant >100 then
    --Recuperation de l'id a affecté au nouveau code
    select nvl(max(idCode)+1,1) into newCode
    from codePromo;

    DBMS_OUTPUT.PUT('On a genere un nouveau code promo automatique pour le client '||:new.idClient);
    insert into codePromo values(newCode,'CodeAuto100euro',0.95,'0',:new.idClient);
  end if;
  --Fin V1

end;
/



--Une commande passe obligatoirement du statut En cours de preparation==>(En cours de livraison==>Livré) || Annulé
create or replace trigger trigger_Statut_Commande
after update of statut on Commande
for each row
begin

  --V1 Verification de la cohérence du statut avant et aprés la mise à jour
  if :old.statut = 'EnCoursPreparation' and :new.statut not in ('Annule','EnCoursLivraison') then
    raise_application_error(-20102,'Une commande en cours de preparation doit etre passé au statut livraison ou annulé');
  elsif :old.statut = 'Annule' then
    --On ne peut pas modifier le statut d'une commande annulé
    raise_application_error(-20103,'Une commande annulé reste annulé');
  elsif :old.statut = 'EnCoursLivraison' and :new.statut != 'Livre' then
      raise_application_error(-20104,'Une commande en cours de livraison doit etre passé au statut Livré');
  elsif :old.statut = 'Livre' then
    --On ne peut pas modifier le statut d'une commande livré
    raise_application_error(-20105,'Une commande Livré ne peut pas changer de statut');
  end if;
  --Fin V1

end;
/


--Une commande est forcément insérer avec le statut En cours de preparation
--Empecher une insertion de commande avec une adresse non associee au client ou un client desactiver
create or replace trigger trigger_insert_Commande
after insert on Commande
for each row
declare
  idC int;
  isActif int;
begin

  --V1 Une commande est forcément insérer avec le statut en cours de préparation
  if :new.statut <> 'EnCoursPreparation' then
    raise_application_error(-20106,'Une commande est forcément insérer avec le statut En cours de preparation');
  end if;
  --Fin V1

  --V2 : On regarde si le client associé à l'adresse de livraison est bien le client qui a passé commande
  select idClient into idC
  from adresse
  where idAdresse = :new.idAdresse;


  if idC != :new.idClient then
    raise_application_error(-20109,'On ne peut pas mettre une adresse qui n est pas associer au client');
  end if;
  --Fin V2

  --V3 : On regarde si le client qui passe commande a bien le statut actif
  select actif into isActif
  from client
  where idClient = :new.idClient;

  if isactif ='0' then
    raise_application_error(-20113,'Ce client ne peut pas faire de commande pour l instant');
  end if;
  --Fin V3

end;
/

--On ne peut pas effacer une commande historise
create or replace trigger trigger_delete_commande
before delete on commande
for each row
begin
  --V1 : On regarde si la commande est historise
  if :old.historise=1 then
    raise_application_error(-20107,'Une commande historise ne peut pas etre effacer');
  end if;
  --Fin V1
end;
/


--On ne peut pas faire une impression si on a pas uploader au moins une image
create or replace trigger trigger_insert_impression
after insert on impression
for each row
declare
  nbImages int;
begin
  --V1 : On regarde combien d'image le client à déjà uploader
  select nvl(count(chemin),0) into nbImages
  from image
  where idClient = :new.idClient;

  if nbImages = 0 then
    raise_application_error(-20108,'Il faut au moins upload une image avant de faire une impression');
  end if;
  -- Fin V1
end;
/


--On effectue toutes les actions correctives liées à la désactivation client
CREATE  or replace trigger trigger_Desactivation_Client
after update of actif on client
for each row
declare
  imp int;
  nbImagePartager int;
begin
  --V1 : On regarde si on es dans le cas d'une désactivation de client
  if :old.actif = '1' and :new.actif='0' then
      --V2 : On regarde le nombre d'image partager par le client
      select nvl(count(chemin),0) into nbImagePartager
      from IMAGE
      where IDCLIENT=:new.idClient and partager='1';

      if nbImagePartager > 0 then
          --V3 : On regarde si des images partager par ce client sont utilisés par des commandes
          -- ayant le statut 'En cours de préparation' et réalisé par un autre client
          select nvl(count(i.CHEMIN),0) into imp
          from image i join PHOTO p on i.chemin=p.CHEMIN join photo_impression pi on p.IDPHOTO=pi.IDPHOTO join impression im on pi.IDIMPRESSION=im.IDIMPRESSION
               join COMMANDE_IMPRESSION ci on im.IDIMPRESSION=ci.IDIMPRESSION join COMMANDE co on co.IDCOMMANDE=ci.IDCOMMANDE
          where i.idClient=:new.idClient and i.partager='1' and co.idClient !=:new.idClient and co.statut='EnCoursPreparation';

          if imp = 0 then
            --Pas utilisé donc on modifie directement l'attribut de partage
            update image set partager = '0' where IDCLIENT= :new.idClient;
          else
            --Mise en file d'attente pour le statut de partage
            update image set fileAttente='1' where IDCLIENT= :new.idClient;
          end if;
          --Fin V3
      end if;
      --Fin V2
  end if;
  --Fin V1
end;
/




-- Un client ne peut utiliser un fichier partagé par une autre personne
-- que si lui-même partage au moins un fichier.
create or replace trigger trigger_ImageUsed
after insert on Photo_impression
for each row
declare
  proprioImage int;
  idC int;
  nbImagesPartager int;
  fileA int;
  statutShared int;
begin

  --V1 : On verifie que l image n est pas en file d'attente
  select fileAttente, partager into fileA,statutShared
  from photo p join image i on p.chemin=i.chemin
  where idPhoto = :new.idPhoto;

  if fileA != 0 then
    raise_application_error(-20111,'Cette image est en cours de desactivation donc on ne peut plus l utiliser');
  end if;
  --Fin V1

  --V2 On verifie que le client a creer une impression au prealable
  select nvl(idClient,-1) into idC
  from impression
  where idImpression = :new.idImpression;

  if idC = -1 then
    raise_application_error(-20100,'Il faut creer l impression avant de lui associer une image');
  end if;
  --Fin V2

  --V3 On regarde si le client est proprio de l image en cours d insertion
  select nvl(count(distinct i.chemin),0) into proprioImage
  from Image i join Photo p on i.chemin=p.chemin
  where i.idClient=idC and p.idPhoto = :new.idPhoto;

  if proprioImage = 0 then

    --V4 On verifie que l image a le statut partager
    if statutShared = 0 then
     raise_application_error(-20112,'Cette image n est pas partager');
    end if;
    --Fin V4

    --V5 On verifie que le client a deja partager des images
    select nvl(count(*),0) into nbImagesPartager
    from image
    where idClient=idC and partager='1';

    if nbImagesPartager < 1 then
      raise_application_error(-20101,'Il faut partager une image avant de pouvoir utiliser une image externe');
    end if;
    --Fin V5
  end if;
  --Fin V3


end;
/

--Trigger gérant la file d'attente d'une image après le changement de statut d'une commande
create or replace trigger trigger_file_attenteImage
after update of statut on commande
declare

  --Selection de toutes les images étant en file d'attente
  Cursor c1 is
    select i.chemin, i.fileAttente
    from IMAGE i
    where  i.fileAttente!='0';

  nbCommandeAPrep int;
  unTuple c1%rowtype;
begin
  --Ouverture du curseur
  open c1;

    --Récupération d'un tuple d'image
    fetch c1 into unTuple;
    --On boucle sur les tuples contenu dans le curseur
    while(c1%found) loop

      --V1 : On regarde s'il y a encore des commandes en cours de préparation associé à cette image
      select nvl(count(c.idCommande),0) into nbCommandeAPrep
      from COMMANDE c join COMMANDE_IMPRESSION ci on c.IDCOMMANDE=ci.IDCOMMANDE join PHOTO_IMPRESSION pi on ci.IDIMPRESSION=pi.IDIMPRESSION
        join PHOTO p on p.IDPHOTO=pi.IDPHOTO join IMAGE I on p.CHEMIN = I.CHEMIN
      where c.statut='EncoursPreparation' and i.chemin=unTuple.chemin;


      if nbCommandeAPrep = 0 then

        --V2 : On regarde le statut de file d'attente
        if unTuple.fileAttente = '1' then
          --On es dans le cas où il faut juste modifier l'attribut de partage
          update IMAGE set FILEATTENTE='0' where chemin=unTuple.chemin;
          update IMAGE set partager='0' where chemin=unTuple.chemin;
        else
          --On es dans le cas où il faut supprimer l'image donc il faut au préalable
          --Supprimer les tuples photo_impression qui lui sont associé
          delete from PHOTO_IMPRESSION pi where pi.idPhoto in (select IDPHOTO
                                                          FROM PHOTO
                                                          where CHEMIN=unTuple.chemin);
          --On supprime les instances de photos associés a l'image
          delete from photo where chemin=unTuple.chemin;
          --Puis on supprime finalement l'image
          delete from IMAGE where chemin=unTuple.chemin;
        end if;
        --Fin V2

    end if;
    --Fin V1

    --Récupération du prochain tuple
    fetch c1 into unTuple;
    end loop;
    
  close c1;

end;
/
