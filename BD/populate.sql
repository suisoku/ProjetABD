-- Insertion Clients --

insert into Client values (1,'cbzakaria95','CHOUKCHOU BRAHAM','Zakaria','lol123',0784917799);
insert into Client values (2,'nour','Ziani','Nour','ahaha',076666666);
insert into Client values (3,'iv','Fontaine','Iv','bayern',077777777);
insert into Client values (4,'regis','Robineau','Regis','macron',07888888);
insert into Client values (5,'sidson','Aidara','sidson','manu',079999999);

-- Insertion Address --

insert into Adresse values (1,'zakAddress','Madrid');
insert into Adresse values (2,'nourAddress','Casa');
insert into Adresse values (3,'ivAddress','Munich');
insert into Adresse values (4,'regisAddress','Moscow');
insert into Adresse values (5,'sidAddress','Manchester');


-- Insertion CodePromo --

insert into CodePromo values (1,0.05,'1',1);
insert into CodePromo values (2,0.10,'0',2);
insert into CodePromo values (3,0.05,'1',3);
insert into CodePromo values (4,0.15,'0',4);
insert into CodePromo values (5,0.20,'1',5);

-- Insertion Commande --


insert into Commande values (1,1,'20-JAN-2019',69,1,'wtf','EnCoursPreparation','PointRelais');
insert into Commande values (2,2,'19-JAN-2019',70,0,'wtf','EnCoursLivraison','Domicile');
insert into Commande values (3,3,'18-JAN-2019',75,1,'wtf','Livre','PointRelais');
insert into Commande values (4,4,'17-JAN-2019',85,0,'wtf','Annule','Domicile');
insert into Commande values (5,5,'16-JAN-2019',90,1,'wtf','EnCoursPreparation','PointRelais');


-- Insertion Image --


insert into Image values ('/usr/tmp/1.jpg', 1, '2K', 1, '11-JAN-19');
insert into Image values ('/usr/tmp/2.jpg', 2, '4K', 0, '12-JAN-19');
insert into Image values ('/usr/tmp/3.jpg', 3, '8K', 1, '13-JAN-19');
insert into Image values ('/usr/tmp/4.jpg', 4, '4K', 0, '14-JAN-19');
insert into Image values ('/usr/tmp/5.jpg', 5, '2K', 1, '15-JAN-19');

-- Insertion Photo --

insert into Photo values (1,'/usr/tmp/2.jpg','sexy bitch','retouche1');
insert into Photo values (2,'/usr/tmp/2.jpg','ooow yeah','retouche2');
insert into Photo values (3,'/usr/tmp/3.jpg','nice Nigga','retouche3');
insert into Photo values (4,'/usr/tmp/4.jpg','stylé','retouche4');
insert into Photo values (5,'/usr/tmp/5.jpg','dark chocolat','retouche5');

-- Insertion Impression --

insert into Impression values (1,1,'zakImpression');
insert into Impression values (2,2,'nourImpression');
insert into Impression values (3,3,'ivImpression');
insert into Impression values (4,4,'regisImpression');
insert into Impression values (5,5,'sidsonImpression');

-- Insertion Commande_Impression --

insert into Commande_Impression values (1,1,10);
insert into Commande_Impression values (2,2,20);
insert into Commande_Impression values (3,3,30);
insert into Commande_Impression values (4,4,40);
insert into Commande_Impression values (5,5,50);

-- Insertion Photo_Impression --

insert into PHOTO_IMPRESSION values (1,1, 'page 1');
insert into PHOTO_IMPRESSION values (2,2, 'page 2');
insert into PHOTO_IMPRESSION values (3,3, 'page 3');
insert into PHOTO_IMPRESSION values (4,4, 'page 4');
insert into PHOTO_IMPRESSION values (5,5, 'page 5');

-- Insertion Photo_Impression_Tirage --

insert into Photo_Tirage_Impression values (4,4,10);

-- Insertion Inventaire --

insert into Inventaire values (1,'AH-0V','good',10,200);
insert into Inventaire values (2,'ML-69','Awsome',10,400);
insert into Inventaire values (3,'M2','good',30,300);
insert into Inventaire values (4,'R32','Yeaaah',50,380);
insert into Inventaire values (5,'RS3','baad',2,200);


-- Insertion CadreProduit --

insert into CadreProduit values (1);


-- Insertion CalendrierProduit --

insert into CalendrierProduit values (2);

-- Insertion AgendaProduit --

insert into AgendaProduit values (3);

-- Insertion AlbumProduit --

insert into AlbumProduit values (5);

-- Insertion TirageProduit --

INSERT into TirageProduit values (4);

-- Insertion Cadre --

insert into Cadre values (1,1,'paysage');

-- Insertion Calendrier --

insert into Calendrier values (2,2,'Bureau');

-- Insertion Agenda --

insert into Agenda values (3,3,'Semainier','3D');

-- Insertion Album --

insert into ALBUM values (5,5,'Mariage','A5');

-- Insertion Tirage --

insert into TIRAGE values (4,4,'A9',10);

-- Insertion Admin --

insert into Admin values (1,'toto','to','to','aze');
insert into Admin values (2,'titi','ti','ti','qsd');
insert into Admin values (3,'tata','ta','ta','wxc');
insert into Admin values (4,'lolo','lo','lo','rty');
insert into Admin values (5,'lili','li','li','fgh');

-- Insertion AdminClient --

insert into ADMINCLIENT values (1,1,'14-JAN-19');
insert into ADMINCLIENT values (2,2,'15-JAN-19');
insert into ADMINCLIENT values (3,3,'16-JAN-19');
insert into ADMINCLIENT values (4,4,'17-JAN-19');
insert into ADMINCLIENT values (5,5,'14-JAN-19');

-- Insertion AdminImage --


insert into AdminImage values (1,'/usr/tmp/2.jpg','14-JAN-19');
insert into AdminImage values (2,'/usr/tmp/2.jpg','15-JAN-19');
insert into AdminImage values (3,'/usr/tmp/3.jpg','16-JAN-19');
insert into AdminImage values (4,'/usr/tmp/4.jpg','17-JAN-19');
insert into AdminImage values (5,'/usr/tmp/5.jpg','18-JAN-19');


-- Insertion AdminInventaire --

insert into ADMININVENTAIRE values (1,1,'14-JAN-19');
insert into ADMININVENTAIRE values (2,2,'15-JAN-19');
insert into ADMININVENTAIRE values (3,3,'16-JAN-19');
insert into ADMININVENTAIRE values (4,4,'17-JAN-19');
insert into ADMININVENTAIRE values (5,5,'18-JAN-19');

-- Insertion AdminCommande --

insert into AdminCommande values (1,1,'14-JAN-19');
insert into AdminCommande values (2,2,'15-JAN-19');
insert into AdminCommande values (3,3,'16-JAN-19');
insert into AdminCommande values (4,4,'17-JAN-19');
insert into AdminCommande values (5,5,'18-JAN-19');


commit ;

call dbms_scheduler.run_job('deleteNoUsedImagesJob');

select * from CLIENT;
select * from ADRESSE;
select * from CODEPROMO;
select * from COMMANDE;
select * from IMAGE;
select * from PHOTO;
select * from IMPRESSION;
select * from COMMANDE_IMPRESSION;
select * from PHOTO_IMPRESSION;
select * from PHOTO_TIRAGE_IMPRESSION;
select * from INVENTAIRE;
select * from CADREPRODUIT;
select * from CALENDRIERPRODUIT;
select * from AGENDAPRODUIT;
select * from ALBUMPRODUIT;
select * from TIRAGEPRODUIT;
select * from CADRE;
select * from CALENDRIER;
select * from AGENDA;
select * from ALBUM;
select * from TIRAGE;
select * from ADMIN;
select * from ADMINCLIENT;
select * from ADMININVENTAIRE;
select * from ADMINIMAGE;
select * from ADMINCOMMANDE;