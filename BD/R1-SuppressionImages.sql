Create table Client
(
  idClient  integer primary key,
  mail      varchar2(250) NOT NULL,
  nom       varchar2(250) NOT NULL,
  prenom    varchar2(250) NOT NULL,
  mdp       varchar2(250) NOT NULL,
  telephone number(10)    NOT NULL
);

Create table Image
(
  chemin          varchar2(250) primary key,
  idClient        integer,
  resolution      varchar2(2),
  partager        varchar2(1),
  dateUtilisation date,
  constraint image_c1 check (resolution in ('2K', '4K', '8K')),
  constraint image_c2 check (partager in ('1', '0')),
  Foreign key (idClient) references Client (idClient)
);

insert into Client values (1,'cbzakaria95','CHOUKCHOU BRAHAM','Zakaria','lol123',078491779);
select * from Client;

insert into Image values ('/usr/tmp/1.jpg',1,'2K',1,'10-JAN-19');
insert into Image values ('/usr/tmp/69.jpg',1,'2K',1,'11-JAN-19');
select * from Image;


-- Function that count the difference between the date of today and the date passed in parameters
create or replace function countDays (date IN date) RETURN
  number IS
Begin
  return trunc(sysdate - date);
end;
/
commit ;
select countDays('10-jan-19') from dual;

create or replace procedure deleteNoUsedImages IS
  begin
  delete from Image
    where countDays(dateUtilisation) >= 10;
  end;
/


begin
DBMS_SCHEDULER.CREATE_JOB (
   job_name           =>  'deleteNoUsedImagesJob',
   job_type           =>  'STORED_PROCEDURE',
   job_action         =>  'deleteNoUsedImages',
   start_date         =>  systimestamp,
   repeat_interval    =>  'FREQ = DAILY',
   end_date           =>  null,
   auto_drop          =>  false,
   comments           =>  'image(s) deleted');
END;
/

call dbms_scheduler.run_job('deleteNoUsedImagesJob');


-- Trigger which updates Image Date when ever the image is used in photo --
CREATE or Replace trigger updateImageTable
  after insert or update  on Photo
  for each row
  begin
    update Image
      set dateUtilisation = sysdate
    where chemin = :NEW.chemin;
  end;
/


