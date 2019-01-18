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
drop table Image;
drop table Commande;
drop table CodePromo;
drop table Adresse;
drop table Client;


Create table Client
(
  idClient  integer primary key,
  mail      varchar2(250) NOT NULL,
  nom       varchar2(250) NOT NULL,
  prenom    varchar2(250) NOT NULL,
  mdp       varchar2(250) NOT NULL,
  telephone number(10)    NOT NULL
);

Create table Adresse
(
  idClient   integer,
  nomAdresse varchar2(250),
  Adresse    varchar2(40) NOT NULL,
  primary key (idClient, nomAdresse),
  Foreign key (idClient) references Client (idClient)
);

--<!-- Mettre une date et la mettre en clé primaire--!>
Create table CodePromo
(
  code      varchar2(250),
  reduction float   NOT NULL,
  used      varchar2(1) NOT NULL,
  idClient  integer,
  primary key (code, idClient),
  Foreign key (idClient) references Client (idClient),
  constraint codeProm_c1 check ( used in ('1','0'))
);


-- Comment on retrouve l utilisation d un code promo dans une commande?
Create table Commande
(
  idCommande    integer primary key ,
  idClient      integer,
  datePaiemant  date          NOT NULL,
  montant       integer       NOT NULL,
  historise varchar2(5) NOT NULL,
	renduPdf varchar2(250) NOT NULL,
  statut        varchar2(250) NOT NULL,
  modeLivraison varchar2(250) NOT NULL,
  constraint commande_c1 check (statut in ('EnCoursPreparation', 'EnCoursLivraison', 'Livre', 'Annule')),
  constraint commande_c2 check (modeLivraison in ('PointRelais', 'Domicile')),
  constraint commande_c3 check (historise in ('1', '0')),
  Foreign key (idClient) references Client (idClient)
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

Create table Photo
(
  idPhoto      integer,
  chemin       varchar2(250),
  commentaire  varchar2(50),
  typeRetouche varchar2(250) NOT NULL,
  primary key (idPhoto),
  Foreign key (chemin) references Image (chemin)
);

Create table Impression
(
  idImpression integer primary key ,
  idClient     integer,
  nom          varchar2(250) NOT NULL,
  Foreign key (idClient) references Client (idClient)
);
create TABLE Commande_Impression (
  idCommande integer,
  idImpression integer,
  quantite integer not null,
  primary key (idCommande,idImpression),
  foreign key (idCommande) references Commande(idCommande),
  foreign key (idImpression) references Impression(idImpression)
);
Create table Photo_Impression
(
  idPhoto                  integer,
  idImpression             integer,
  specificationParticuliere varchar2(250) NOT NULL,
  primary key (idPhoto, idImpression),
  Foreign key (idPhoto) references Photo (idPhoto),
  Foreign key (idImpression) references Impression (idImpression)
);

Create table Photo_Tirage_Impression
(
  idPhoto      integer,
  idImpression integer,
  quantite     integer NOT NULL,
  primary key (idPhoto, idImpression),
  Foreign key (idPhoto,idImpression) references Photo_Impression (idPhoto,idImpression)
);

Create table Inventaire
(
  idProduit       int primary key,
  nomCommercial  varchar2(250) NOT NULL,
  caracteristique varchar2(250),
  stock           int           NOT NULL,
  prix            int           NOT NULL
);

Create table CadreProduit
(
  idProduit int primary key,
  Foreign key (idProduit) references Inventaire (idProduit)
);

Create table CalendrierProduit
(
  idProduit int primary key,
  Foreign key (idProduit) references Inventaire (idProduit)
);
Create table AgendaProduit
(
  idProduit int primary key,
  Foreign key (idProduit) references Inventaire (idProduit)
);

Create table AlbumProduit
(
  idProduit int primary key,
  Foreign key (idProduit) references Inventaire (idProduit)
);

Create table TirageProduit
(
  idProduit int primary key,
  Foreign key (idProduit) references Inventaire (idProduit)
);

Create table Cadre
(
  idImpression integer primary key ,
  idProduit integer,
  miseEnpage varchar2(20),
  Foreign key (idImpression) references Impression (idImpression),
  foreign key (idProduit) references CadreProduit(idProduit)
);

Create table Agenda
(
  idImpression integer primary key ,
  idProduit    integer,
  typeAgenda   varchar2(250) NOT NULL,
  modele       varchar2(250) NOT NULL,
  constraint agenda_c1 check (typeAgenda in ('Semainier', 'Hebdomadaire')),
  Foreign key (idImpression) references Impression (idImpression),
  Foreign key (idProduit) references AgendaProduit (idProduit)
);

Create table Calendrier
(
  idImpression   integer primary key ,
  idProduit      int,
  typeCalendrier varchar2(250) NOT NULL,
  constraint calendrier_c1 check ( typeCalendrier in ('Bureau', 'Mural')),
  Foreign key (idImpression) references Impression (idImpression),
  Foreign key (idProduit) references CalendrierProduit (idProduit)
);

Create table Album
(
  idImpression integer primary key ,
  idProduit    int,
  titre        varchar2(250) NOT NULL,
  miseEnPage   varchar2(250) NOT NULL,
  constraint album_c1 check (miseEnPage in ('A4', 'A5')),
  Foreign key (idProduit) references AlbumProduit (idProduit),
  Foreign key (idImpression) references Impression (idImpression)
);

Create table Tirage
(
  idImpression     integer primary key ,
  idProduit        int,
  formatImpression varchar2(250) NOT NULL,
  nbrExemplaire    int           NOT NULL,
  Foreign key (idProduit) references TirageProduit (idProduit),
  Foreign key (idImpression) references Impression (idImpression),
  constraint tirage_c1 check (formatImpression in ('A0', 'A1', 'A2', 'A3', 'A4', 'A5', 'A6', 'A7', 'A8', 'A9'))
);

Create table Admin
(
  idAdmin   integer primary key,
  mail      varchar2(250) NOT NULL,
  nom       varchar2(250) NOT NULL,
  prenom    varchar2(250) NOT NULL,
  mdp       varchar2(250) NOT NULL
);

Create table AdminClient
(
  idAdmin   integer,
  idClient  integer,
  dateModif date,
  primary key (idAdmin, idClient, dateModif),
  Foreign key (idAdmin) references Admin (idAdmin),
  Foreign key (idClient) references Client (idClient)
);


-- erreur sur le rendu, il y a idImage au lieu de chemin
Create table AdminImage
(
  idAdmin   integer,
  chemin    varchar2(250),
  dateModif date,
  primary key (idAdmin, chemin, dateModif),
  Foreign key (idAdmin) references Admin (idAdmin),
  Foreign key (chemin) references Image (chemin)
);

Create table AdminInventaire
(
  idAdmin   integer,
  idProduit integer,
  dateModif date,
  primary key (idAdmin, idProduit, dateModif),
  Foreign key (idAdmin) references Admin (idAdmin),
  Foreign key (idProduit) references Inventaire (idProduit)
);

Create table AdminCommande
(
  idAdmin    integer,
  idCommande integer,
  dateModif  date,
  primary key (idAdmin, idCommande, dateModif),
  Foreign key (idAdmin) references Admin (idAdmin),
  Foreign key (idCommande) references Commande (idCommande)
);