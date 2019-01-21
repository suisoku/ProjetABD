call dbms_scheduler.drop_job('deleteNoUsedImagesJob');


-- Function that count the difference between the date of today and the date passed in parameters
create or replace function countDays(date IN date) RETURN
  number IS
Begin
  return trunc(sysdate - date);
end;
/


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
commit;

-- Trigger Une commande n'est possible que si le support d'impression est en stock

create or replace trigger commandePossible
  before insert or update of IDCOMMANDE,STATUT on COMMANDE
  for each row
  declare
    nb number;
    begin
   select count(*) into nb
    from COMMANDE_IMPRESSION ci join (select IDIMPRESSION,IDPRODUIT from CADRE
                                      union
                                      select IDIMPRESSION,IDPRODUIT from AGENDA
                                      union
                                      select IDIMPRESSION,IDPRODUIT from CALENDRIER
                                      union
                                      select IDIMPRESSION,IDPRODUIT from ALBUM
                                      union
                                      select IDIMPRESSION,IDPRODUIT from TIRAGE) Imp
      on ci.IDIMPRESSION = Imp.IDIMPRESSION
                                join (select * from CADREPRODUIT
                                      union
                                      select * from AGENDAPRODUIT
                                      union
                                      select * from CALENDRIERPRODUIT
                                      union
                                      select * from ALBUMPRODUIT
                                      union
                                      select * from TIRAGEPRODUIT) Inv
      on imp.IDPRODUIT = Inv. IDPRODUIT join INVENTAIRE i on inv.IDPRODUIT = i.IDPRODUIT
    where i.STOCK >= ci.QUANTITE and ci.IDCOMMANDE = :new.IDCOMMANDE and :new.STATUT = 'EnCoursLivraison';
    if nb <> 0 then raise_application_error(-20100,'Produit non disponible');end if;
    end;
/
-- Trigger R9 & R10 --

select c.NOM, p.CHEMIN
  from image i join photo p on i.CHEMIN = p.CHEMIN
  join PHOTO_IMPRESSION pi on p.IDPHOTO = pi.IDPHOTO
  join IMPRESSION im on pi.IDIMPRESSION = im.IDIMPRESSION
  join CLIENT c on im.IDCLIENT = c.IDCLIENT
where c.IDCLIENT <> i.IDCLIENT and i.PARTAGER = 1;

