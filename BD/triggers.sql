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

-- Trigger which updates Image Date when ever the image is used in photo --
CREATE or Replace trigger updateImageTable
  after insert or update
  on Photo
  for each row
begin
  update Image
  set dateUtilisation = sysdate
  where chemin = :NEW.chemin;
end;
/
commit;