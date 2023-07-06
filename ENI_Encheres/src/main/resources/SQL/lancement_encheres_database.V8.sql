-- Script de création de la base de données ENCHERES
--   type :      SQL Server 2012
--
DROP TABLE IF EXISTS ENCHERES;
DROP TABLE IF EXISTS RETRAITS;
DROP TABLE IF EXISTS ARTICLES_VENDUS;
DROP TABLE IF EXISTS UTILISATEURS;
DROP TABLE IF EXISTS CATEGORIES;


CREATE TABLE CATEGORIES (
    no_categorie   INTEGER IDENTITY(1,1) NOT NULL,
    libelle        VARCHAR(30) NOT NULL
)

ALTER TABLE CATEGORIES ADD constraint categorie_pk PRIMARY KEY (no_categorie)

CREATE TABLE ENCHERES (
    no_utilisateur   INTEGER NOT NULL,
    no_article       INTEGER NOT NULL,
    date_enchere     datetime NOT NULL,
	montant_enchere  INTEGER NOT NULL

)

ALTER TABLE ENCHERES ADD constraint enchere_pk PRIMARY KEY (no_utilisateur, no_article)

CREATE TABLE RETRAITS (
	no_article         INTEGER NOT NULL,
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(15) NOT NULL,
    ville            VARCHAR(30) NOT NULL
)

ALTER TABLE RETRAITS ADD constraint retrait_pk PRIMARY KEY  (no_article)

CREATE TABLE UTILISATEURS (
    no_utilisateur   INTEGER IDENTITY(1,1) NOT NULL,
    pseudo           VARCHAR(30) NOT NULL unique,
    nom              VARCHAR(30) NOT NULL,
    prenom           VARCHAR(30) NOT NULL,
    email            VARCHAR(60) NOT NULL unique,
    telephone        VARCHAR(15),
    rue              VARCHAR(30) NOT NULL,
    code_postal      VARCHAR(10) NOT NULL,
    ville            VARCHAR(30) NOT NULL,
    mot_de_passe     VARCHAR(60) NOT NULL,
    credit           INTEGER NOT NULL,
    administrateur   bit NOT NULL
)

ALTER TABLE UTILISATEURS ADD constraint utilisateur_pk PRIMARY KEY (no_utilisateur)


CREATE TABLE ARTICLES_VENDUS (
    no_article                    INTEGER IDENTITY(1,1) NOT NULL,
    nom_article                   VARCHAR(30) NOT NULL,
    description                   VARCHAR(300) NOT NULL,
	date_debut_encheres           DATE NOT NULL,
    date_fin_encheres             DATE NOT NULL,
    prix_initial                  INTEGER,
    prix_vente                    INTEGER,
    no_utilisateur                INTEGER NOT NULL,
    no_categorie                  INTEGER NOT NULL,
	etat						  bit
)

ALTER TABLE ARTICLES_VENDUS ADD constraint articles_vendus_pk PRIMARY KEY (no_article)

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT encheres_utilisateur_fk FOREIGN KEY ( no_utilisateur ) REFERENCES UTILISATEURS ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ENCHERES
    ADD CONSTRAINT encheres_articles_vendus_fk FOREIGN KEY ( no_article )
        REFERENCES ARTICLES_VENDUS ( no_article )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE RETRAITS
    ADD CONSTRAINT retraits_articles_vendus_fk FOREIGN KEY ( no_article )
        REFERENCES ARTICLES_VENDUS ( no_article )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT articles_vendus_categories_fk FOREIGN KEY ( no_categorie )
        REFERENCES categories ( no_categorie )
ON DELETE NO ACTION 
    ON UPDATE no action 

ALTER TABLE ARTICLES_VENDUS
    ADD CONSTRAINT ventes_utilisateur_fk FOREIGN KEY ( no_utilisateur )
        REFERENCES utilisateurs ( no_utilisateur )
ON DELETE NO ACTION 
    ON UPDATE no action 

	
USE ENCHERES_DB

insert into CATEGORIES (libelle) values ('Voiture'), ('Informatique'), ('Divertissement'), ('Vetements'), ('Musique');


insert into UTILISATEURS (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) values 
('YvanDeur', 'Deur','Yvan', 'besoindargent@hess.com','0606060606', 'rue de la brocante', '44000','Nantes','inutile','1000',0)
,('JeannotLaDépense', 'Chérie', 'Jean', 'encheres@acheter.com', '0102030405', 'rue de la money money', '56000', 'Bourges', 'inutile', '1000', 0)
,('HeussMoulaga', 'Ga', 'Moula', 'moulaga@richesse.com', '0999999999', 'rue du flex', '96000', 'La Baule', 'inutile', '1000', 0)
,('Tata', 'Le Goat', 'Tahina', 'futurrennais@kenavo.com', '0612458954', 'rue du cidre', '35000', 'Rennes', '$2a$10$0ylGLLxa6LEI2B3xwEKAJeyf6jMaGk5mg4nQhxOmqTffOs1np4Hbm', '10000', 0)
,('GarageDeDent', 'Garage', 'Dedent', 'voiture@vroum.com', '0606060606', 'rue du joint de culasse', '93000', 'Seine-St-Denis Style', 'inutile', '2000', 0)
,('HUIT', 'HUIT', 'HUIT', 'huit@huit.huit', '0888888888', 'rue du grand huit', '88888', 'Sètes', 'inutile', '8880', 0)
;



insert into ARTICLES_VENDUS (nom_article, description, date_debut_encheres,date_fin_encheres,prix_initial, prix_vente, no_utilisateur,no_categorie, etat) values 
('Fauteuil', 'Voilà un fauteuil qu''il est bien', '03/07/2023', '12/07/2023', 210, 300,1,2,0)
,('Ordinateur', 'Pour gank mid-lane lvl 4', '20/06/2023', '14/07/2023', 200, 100,1,2,0)
,('Manette', 'La manette de la mort qui tue !', '02/07/2023', '03/07/2023', 110, 220, 3,2,0)
,('Souris du futur', 'Ya un click droit ET un click gauche. ET une MOLETTE', '12/06/2023', '08/07/2023', 400, 430, 1, 2,0)
,('General Lee', 'Hyyyhhy houhouhou !', '02/07/2023', '14/07/2023', 320, 190, 5,1,0)
,('Mercedes', 'J''pouvais pas la conduire, bien sûre j''étais trop petit', '23/06/2023', '02/07/2023', 110, 190, 5, 1,0)
,('Patrick Balkany', 'Danseur de talent', '13/06/2023', '12/07/2023', 110, 190, 6,3,0)
,('Tenue de travail', 'Sobre et décontractée', '10/07/2023', '25/07/2023', 200, 200, 6, 4, 0)
,('Album Frédéric Francois', 'Les meilleures chansons en LIVE', '12/07/2023', '28/07/2023', 350, 350, 4, 5, 0)
,('Album Yvette Horner', 'Accordéon quand tu nous tiens', '02/07/2023', '26/07/2023', 110, 150, 4, 5, 0)
,('Sarouel presque neuf', 'Idéal ZAD ou enterrement', '03/07/2023', '28/07/2023', 90, 120, 6, 4, 0)
,('Concert privé', 'Showcase avec le meilleur chanteur francais', '05/07/2023', '25/07/2023', 290, 310, 1, 3, 0)
,('PC Portable', 'Modèle léger et compact', '04/07/2023', '02/08/2023', 150, 170, 4, 2, 0)
,('Pc Gamer', 'Modèle unique', '26/06/2023', '02/07/2023', 260, 350, 3, 2, 0)
;

insert into RETRAITS (no_article, rue, code_postal, ville) values 
(1, 'rue du confort', '29000', 'Brest')
,(2, 'rue de la carte mère', '99000', 'Silicon Valley')
,(3, 'rue du croc mort', '66600', 'Santa Muerte')
,(4, 'rue du click', '12000', 'Logitech')
,(5, 'rue de la peur du sheriff', '99000', 'Texas')
,(6, 'rue de la caisse', '72000', 'Le Mans')
,(7, 'rue de la samba', '62000', 'Levallois-Perret')
,(8, 'rue rouge', '33000', 'Transformation!')
,(9, 'rue de la guitare', '22000', 'Guitare')
,(10, 'rue de la teinture', '15000', 'Accordeon')
,(11, 'rue des bolas', '35000', 'Rennes')
,(12, 'rue de la bonne humeur', '12000', 'Toulouse')
,(13, 'rue de la pizza', '44000', 'Nantes')
,(14, 'rue du setup', '93000', 'Neuilly')
;

insert into ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) values 
(2, 1, '28/06/2023', 300),
(2, 3, '20/06/2023', 190),
(2, 4, '19/06/2023', 410),
(3, 4, '21/06/2023', 430),
(4, 3, '01/07/2023', 220),
(4, 14, '28/06/2023', 350),
(4, 11, '04/07/2023', 120),
(2, 13, '06/07/2023', 170),
(3, 10,  '03/07/2023', 150),
(4, 12, '06/07/2023', 310)

;
