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


-- Insertion CodePromo -- Voir ça avec la Team --

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

commit ;

select * from CLIENT;
select * from ADRESSE;
select * from COMMANDE;
select * from IMAGE;
select * from PHOTO;
select * from IMPRESSION;
select * from COMMANDE_IMPRESSION;