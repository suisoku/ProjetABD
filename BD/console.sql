drop table AdminCommande;
drop table AdminClient;
drop table AdminImage;
drop table AdminInventaire;
drop table Admin;
drop table Tirage;
drop table Cadre;
drop table Calendrier;
drop table Agenda;
drop table Album;
drop table TirageProduit;
drop table CadreProduit;
drop table CalendrierProduit;
drop table AgendaProduit;
drop table AlbumProduit;
drop table Inventaire;
drop table Photo_Tirage_Impression;
drop table Photo_Impression;
drop table Photo;
drop table Commande_Impression;
drop table Impression;
drop table Client_Use_Image;
drop table Image;
drop table Commande;
drop table CodePromo;
drop table Adresse;
drop table Client;


Create table Client
(
  idClient  NUMBER primary key,
  mail      varchar2(250) NOT NULL,
  nom       varchar2(250) NOT NULL,
  prenom    varchar2(250) NOT NULL,
  mdp       varchar2(250) NOT NULL,
  telephone number(10)    NOT NULL
);



Create table Adresse
(
  idClient   NUMBER,
  nomAdresse varchar2(250),
  Adresse    varchar2(40) NOT NULL,
  constraint pk_Adresse primary key (idClient, nomAdresse),
  CONSTRAINT fk_Adresse Foreign key (idClient) references Client (idClient) on delete cascade
);


Create table CodePromo
(
  idCode    number(10) primary key,
  code      varchar2(250) NOT NULL,
  reduction float       NOT NULL,
  used      varchar2(1) NOT NULL,
  idClient  NUMBER,
  constraint fk_CodePromo Foreign key (idClient) references Client (idClient) on delete cascade,
  constraint codeProm_c1 check ( used in ('1', '0')),
  constraint codeProm_c2 check (reduction between 0.01 and 0.99)
);


-- Comment on retrouve l utilisation d un code promo dans une commande?
Create table Commande
(
  idCommande    NUMBER primary key,
  idClient      NUMBER,
  datePaiemant  date          NOT NULL,
  montant       NUMBER       NOT NULL,
  historise     NUMBER(1)    NOT NULL,
  renduPdf      varchar2(250) NOT NULL,
  statut        varchar2(250) NOT NULL,
  modeLivraison varchar2(250) NOT NULL,
  constraint commande_c1 check (statut in ('EnCoursPreparation', 'EnCoursLivraison', 'Livre', 'Annule')),
  constraint commande_c2 check (modeLivraison in ('PointRelais', 'Domicile')),
  constraint commande_c3 check (historise in ('1', '0')),
  constraint commande_c4 check (montant >= 0),
  constraint commande_c3 check (historise in ('1', '0')),
  constraint fk_commande Foreign key (idClient) references Client (idClient) on delete set null
);

Create table Image
(
  chemin          varchar2(250) primary key,
  idClient        NUMBER,
  resolution      varchar2(2) NOT NULL,
  partager        NUMBER(1) NOT NULL,
  dateUtilisation date NOT NULL,
  constraint image_c1 check (resolution in ('2K', '4K', '8K')),
  constraint image_c2 check (partager in ('1', '0')),
  constraint fk_image Foreign key (idClient) references Client (idClient)
);

Create table Client_Use_Image
(
  idClient integer,
  chemin varchar2(250),
  primary key (idClient,chemin),
  foreign key (idClient) references Client(idClient),
  foreign key (chemin) references Image(chemin)
);

Create table Photo
(
  idPhoto      NUMBER primary key,
  chemin       varchar2(250),
  commentaire  varchar2(50),
  typeRetouche varchar2(250) NOT NULL,
  constraint fk_Photo Foreign key (chemin) references Image (chemin) -- On ne fait rien --
);



Create table Impression
(
  idImpression NUMBER primary key,
  idClient     NUMBER,
  nom          varchar2(250) NOT NULL,
  constraint fk_impression Foreign key (idClient) references Client (idClient) on delete cascade
);


create TABLE Commande_Impression
(
  idCommande   NUMBER,
  idImpression NUMBER,
  quantite     NUMBER not null,
  constraint pk_CommandeImpression primary key (idCommande, idImpression),
  constraint commande_impression_c1 check (quantite > 0),
  constraint fk_CommandeImpression1 foreign key (idCommande) references Commande (idCommande) on delete set null,
  constraint fk_CommandeImpression2 foreign key (idImpression) references Impression (idImpression) on delete set null
);

Create table Photo_Impression
(
  idPhoto                   NUMBER,
  idImpression              NUMBER,
  specificationParticuliere varchar2(250) NOT NULL,
  constraint pk_PhotoImpression primary key (idPhoto, idImpression),
  constraint fk_PhotoImpression1 Foreign key (idPhoto) references Photo (idPhoto),
  constraint fk_PhotoImpression2 Foreign key (idImpression) references Impression (idImpression)
);


Create table Photo_Tirage_Impression
(
  idPhoto      NUMBER,
  idImpression NUMBER,
  quantite     NUMBER NOT NULL,
  constraint pk_PhotoTirageImpression primary key (idPhoto, idImpression),
  constraint fk_PhotoTirageImpression
    Foreign key (idPhoto, idImpression) references Photo_Impression (idPhoto, idImpression)
);


Create table Inventaire
(
  idProduit       NUMBER primary key,
  nomCommercial   varchar2(250) NOT NULL,
  caracteristique varchar2(250),
  stock           NUMBER       NOT NULL,
  prix            NUMBER       NOT NULL,
  constraint inventaire_c1 check (stock>=0),
  constraint inventaire_c2 check (prix>0)
);


Create table CadreProduit
(
  idProduit NUMBER primary key,
  constraint fk_CadreProduit Foreign key (idProduit) references Inventaire (idProduit) on delete cascade
);


Create table CalendrierProduit
(
  idProduit NUMBER primary key,
  constraint fk_CalendrierProduit Foreign key (idProduit) references Inventaire (idProduit) on delete cascade
);



Create table AgendaProduit
(
  idProduit NUMBER primary key,
  constraint fk_AgendaProduit Foreign key (idProduit) references Inventaire (idProduit) on delete cascade
);


Create table AlbumProduit
(
  idProduit NUMBER primary key,
  constraint fk_AlbumProduit Foreign key (idProduit) references Inventaire (idProduit) on delete cascade
);


Create table TirageProduit
(
  idProduit NUMBER primary key,
  constraint fk_TirageProduit Foreign key (idProduit) references Inventaire (idProduit) on delete cascade
);


Create table Cadre
(
  idImpression NUMBER primary key,
  idProduit    NUMBER,
  miseEnpage   varchar2(20) NOT NULL,
  constraint fk_Cadre1 Foreign key (idImpression) references Impression (idImpression) on delete cascade,
  constraint fk_Cadre2 foreign key (idProduit) references CadreProduit (idProduit) on delete cascade
);



Create table Agenda
(
  idImpression NUMBER primary key,
  idProduit    NUMBER,
  typeAgenda   varchar2(250) NOT NULL,
  modele       varchar2(250) NOT NULL,
  constraint agenda_c1 check (typeAgenda in ('Semainier', 'Hebdomadaire')),
  constraint fk_Agenda1 Foreign key (idImpression) references Impression (idImpression) on delete cascade,
  constraint fk_Agenda2 Foreign key (idProduit) references AgendaProduit (idProduit) on delete cascade
);


Create table Calendrier
(
  idImpression   NUMBER primary key,
  idProduit      int,
  typeCalendrier varchar2(250) NOT NULL,
  constraint calendrier_c1 check ( typeCalendrier in ('Bureau', 'Mural')),
  constraint fk_Calendrier1 Foreign key (idImpression) references Impression (idImpression) on delete cascade,
  constraint fk_Calendreir2 Foreign key (idProduit) references CalendrierProduit (idProduit) on delete cascade
);



Create table Album
(
  idImpression NUMBER primary key,
  idProduit    int,
  titre        varchar2(250) NOT NULL,
  miseEnPage   varchar2(250) NOT NULL,
  constraint album_c1 check (miseEnPage in ('A4', 'A5')),
  constraint fk_Album2 Foreign key (idImpression) references Impression (idImpression) on delete cascade,
  constraint fk_Album1 Foreign key (idProduit) references AlbumProduit (idProduit) on delete cascade
);


Create table Tirage
(
  idImpression     NUMBER primary key,
  idProduit        int,
  formatImpression varchar2(250) NOT NULL,
  nbrExemplaire    NUMBER           NOT NULL,
  constraint fk_Tirage1 Foreign key (idImpression) references Impression (idImpression) on delete cascade,
  constraint fk_Tirage2 Foreign key (idProduit) references TirageProduit (idProduit) on delete cascade,
  constraint tirage_c1 check (formatImpression in ('A0', 'A1', 'A2', 'A3', 'A4', 'A5', 'A6', 'A7', 'A8', 'A9')),
  constraint tirage_c2 check (nbrExemplaire>0)
);

Create table Admin
(
  idAdmin NUMBER primary key,
  mail    varchar2(250) NOT NULL,
  nom     varchar2(250) NOT NULL,
  prenom  varchar2(250) NOT NULL,
  mdp     varchar2(250) NOT NULL
);


Create table AdminClient
(
  idAdmin   NUMBER,
  idClient  NUMBER,
  dateModif date,
  constraint pk_AdminClient primary key (idAdmin, idClient, dateModif),
  constraint fk_AdminClient1 Foreign key (idAdmin) references Admin (idAdmin) on delete set null,
  constraint fk_AdminClient2 Foreign key (idClient) references Client (idClient) on delete set null
);


-- erreur sur le rendu, il y a idImage au lieu de chemin
Create table AdminImage
(
  idAdmin   NUMBER,
  chemin    varchar2(250),
  dateModif date,
  constraint pk_AdminImage primary key (idAdmin, chemin, dateModif),
  constraint fk_AdminImage1 Foreign key (idAdmin) references Admin (idAdmin) on delete set null,
  constraint fk_AdminImage2 Foreign key (chemin) references Image (chemin) on delete set null
);


Create table AdminInventaire
(
  idAdmin   NUMBER,
  idProduit NUMBER,
  dateModif date,
  constraint pk_AdminInventaire primary key (idAdmin, idProduit, dateModif),
  constraint fk_AdminInventaire1 Foreign key (idAdmin) references Admin (idAdmin) on delete set null,
  constraint fk_AdminInventaire2 Foreign key (idProduit) references Inventaire (idProduit) on delete set null
);

Create table AdminCommande
(
  idAdmin    NUMBER,
  idCommande NUMBER,
  dateModif  date,
  constraint pk_AdminCommande primary key (idAdmin, idCommande, dateModif),
  constraint fk_AdminCommande1 Foreign key (idAdmin) references Admin (idAdmin) on delete set null,
  constraint fk_AdminCommande2 Foreign key (idCommande) references Commande (idCommande) on delete set null
);

commit ;