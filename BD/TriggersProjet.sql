drop trigger trigger_codePromo;
drop trigger trigger_GenerationCodePromo;
drop trigger trigger_Statut_Commande;
drop trigger trigger_insert_Commande;
drop trigger trigger_delete_commande;
drop trigger trigger_insert_impression;
drop trigger trigger_Desactivation_Client
drop trigger trigger_ImageUsed;
drop trigger trigger_file_attenteImage;



--Des codes de promo sont donnés et ne sont utilisables qu'une seule fois par client
-- pour une future commande (offre promotionnelle). Ce code est ensuite automatiquement supprimé.
create or replace trigger trigger_codePromo
after update on CodePromo
declare
  nbCodeSupprimer int;
begin

  select nvl(count(*),0) into nbCodeSupprimer
  from CodePromo
  where used ='1';

  if nbCodeSupprimer>0 then
    delete from codePromo where used='1';
    DBMS_OUTPUT.PUT('On a supprimer '||nbCodeSupprimer||' code promos');
  end if;
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

  if :new.montant >100 then
    select nvl(max(idCode)+1,1) into newCode
    from codePromo;

    DBMS_OUTPUT.PUT('On a genere un nouveau code promo automatique pour le client '||:new.idClient);
    insert into codePromo values(newCode,'CodeAuto100euro',0.95,'0',:new.idClient);
  end if;

end;
/



--Une commande passe du statut En cours de preparation==>(En cours de livraison==>Livré) || Annulé
create or replace trigger trigger_Statut_Commande
after update of statut on Commande
for each row
begin

  if :old.statut = 'EnCoursPreparation' and :new.statut not in ('Annule','EnCoursLivraison') then
    raise_application_error(-20102,'Une commande en cours de preparation doit etre passé au statut livraison ou annulé');
  elsif :old.statut = 'Annule' then
    raise_application_error(-20103,'Une commande annulé reste annulé');
  elsif :old.statut = 'EnCoursLivraison' and :new.statut != 'Livre' then
      raise_application_error(-20104,'Une commande en cours de livraison doit etre passé au statut Livré');
  elsif :old.statut = 'Livre' then
    raise_application_error(-20105,'Une commande Livré ne peut pas changer de statut');
  end if;

end;
/


--Une commande est forcément insérer avec le statut En cours de preparation
--Empecher une insertion de commande avec une adresse non associee au client ou un client desactif ou en cours de desactivation
create or replace trigger trigger_insert_Commande
after insert on Commande
for each row
declare
  idC int;
  isActif int;
begin

  if :new.statut <> 'EnCoursPreparation' then
    raise_application_error(-20106,'Une commande est forcément insérer avec le statut En cours de preparation');
  end if;

  select idClient into idC
  from adresse
  where idAdresse = :new.idAdresse;


  if idC != :new.idClient then
    raise_application_error(-20109,'On ne peut pas mettre une adresse qui n est pas associer au client');
  end if;

  select actif into isActif
  from client
  where idClient = :new.idClient;

  if isactif ='0' then
    raise_application_error(-20113,'Ce client ne peut pas faire de commande pour l instant');
  end if;

end;
/

--On ne peut pas effacer une commande historise
create or replace trigger trigger_delete_commande
before delete on commande
for each row
begin
  if :old.historise=1 then
    raise_application_error(-20107,'Une commande historise ne peut pas etre effacer');
  end if;
end;
/


--On ne peut pas faire une impression si on a pas uploader au moins une image
create or replace trigger trigger_insert_impression
after insert on impression
for each row
declare
  nbImages int;
begin
  select nvl(count(chemin),0) into nbImages
  from image
  where idClient = :new.idClient;

  if nbImages = 0 then
    raise_application_error(-20108,'Il faut au moins upload une image avant de faire une impression');
  end if;
end;
/


--On verifie si on peut desactiver le client
CREATE  or replace trigger trigger_Desactivation_Client
after update of actif on client
for each row
declare
  imp int;
  nbImagePartager int;
begin

  if :old.actif = '1' and :new.actif='0' then

      select nvl(count(chemin),0) into nbImagePartager
      from IMAGE
      where IDCLIENT=:new.idClient and partager='1';

      if nbImagePartager > 0 then
          select nvl(count(i.CHEMIN),0) into imp
          from image i join PHOTO p on i.chemin=p.CHEMIN join photo_impression pi on p.IDPHOTO=pi.IDPHOTO join impression im on pi.IDIMPRESSION=im.IDIMPRESSION
          where i.idClient=:new.idClient and i.partager='1' and im.idClient !=:new.idClient;

          if imp = 0 then
            update image set partager = '0' where IDCLIENT= :new.idClient;
          else
            update image set fileAttente='1' where IDCLIENT= :new.idClient;
          end if;

      end if;
  end if;
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

  --V1 On verifie que l image n est pas en file d'attente
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

ccreate or replace trigger trigger_file_attenteImage
after update of statut on commande
declare
  Cursor c1 is
    select i.chemin, i.fileAttente
    from IMAGE i
    where  i.fileAttente!='0';

  nbCommandeAPrep int;
  unTuple c1%rowtype;
begin

  open c1;

    fetch c1 into unTuple;

    while(c1%found) loop


      select nvl(count(c.idCommande),0) into nbCommandeAPrep
      from COMMANDE c join COMMANDE_IMPRESSION ci on c.IDCOMMANDE=ci.IDCOMMANDE join PHOTO_IMPRESSION pi on ci.IDIMPRESSION=pi.IDIMPRESSION
        join PHOTO p on p.IDPHOTO=pi.IDPHOTO join IMAGE I on p.CHEMIN = I.CHEMIN
      where c.statut='EncoursPreparation' and i.chemin=unTuple.chemin;


      if nbCommandeAPrep = 0 then


        if unTuple.fileAttente = '1' then
          update IMAGE set FILEATTENTE='0' where chemin=unTuple.chemin;
          update IMAGE set partager='0' where chemin=unTuple.chemin;
        end if;

      end if;
    fetch c1 into unTuple;
    end loop;


  close c1;

end;
/
