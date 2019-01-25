call dbms_scheduler.drop_job('deleteNoUsedImagesJob');


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


begin
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
CREATE or Replace trigger updateImageTable1
  after insert or update
  on PHOTO
  for each row
begin
  update Image
  set dateUtilisation = sysdate
  where chemin = CHEMIN;
end;
/


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


create or replace trigger commandePossible1
  before insert or update on COMMANDE_IMPRESSION
  for each row
  declare
    nb number(4);
  begin
    select Count(*) into nb
    from PHOTO_IMPRESSION pi
      where pi.IDPHOTO not in ( select cui.IDPHOTO
                                  FROM CLIENT_USE_IMAGE cui join commande c on cui.IDCLIENT = c.IDCLIENT
                                  where :new.IDCOMMANDE = c.IDCOMMANDE
                                  and :new.IDIMPRESSION = cui.IDIMPRESSION);
    if (nb <> 0 )
      then raise_application_error(-20100,' image non paratgée');
    end if;
  end;
/

-- Un client ne peut utiliser un fichier partagé par une autre personne --
-- que si lui-même partage au moins un fichier --
/*create or replace trigger imageUsed
  before insert or update on PHOTO_IMPRESSION
  for each row
  declare
    nb number(4);
  begin
    select count(*) into nb
    from CLIENT c  join impage i on  c.IDCLIENT = i.IDCLIENT

    join

  end;
/*/
commit;

