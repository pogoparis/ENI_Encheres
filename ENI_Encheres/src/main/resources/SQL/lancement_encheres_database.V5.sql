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
    no_categorie                  INTEGER NOT NULL
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

insert into CATEGORIES (libelle) values ('voiture'), ('informatique'), ('esclaves');


insert into UTILISATEURS (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) values 
('Yvan Deur', 'Deur','Yvan', 'besoindargent@hess.com','0606060606', 'rue de la brocante', '44000','Nantes','azerty1234','300',0)
,('Dédé la dépense', 'Chérie', 'Jean', 'encheres@acheter.com', '0102030405', 'rue de la money money', '56000', 'Bourges', 'inutile', '800', 0)
,('MOI MOI MOI', 'Ga', 'Moula', 'moulaga@richesse.com', '0999999999', 'rue du flex', '96000', 'La Baule', 'inutile', '900', 0)
,('Tata', 'Le Goat', 'Tahina', 'futurrennais@kenavo.com', '0612458954', 'rue du cidre', '35000', 'Rennes', '$2a$10$0ylGLLxa6LEI2B3xwEKAJeyf6jMaGk5mg4nQhxOmqTffOs1np4Hbm', '10000', 0)
;



insert into ARTICLES_VENDUS (nom_article, description, date_debut_encheres,date_fin_encheres,prix_initial, prix_vente, no_utilisateur,no_categorie) values 
('Fauteuil', 'Voilà un fauteuil qu''il est bien', '03/07/2023', '05/07/2023', 210, 300,1,1)
,('Ordinateur', 'Pour gank mid-lane lvl 4', '20/06/2023', '11/07/2023', 100, 100,1,2)
,('Patrick Balkany', 'Danseur de talent', '13/06/2023', '02/07/2023', 110, 190, 1,3)
,('Souris du futur', 'Ya un click droit ET un click gauche. ET une MOLETTE', '12/06/2023', '08/07/2023', 400, 430, 1, 2)
;

insert into RETRAITS (no_article, rue, code_postal, ville) values (1, 'rue de la confusion', '29000', 'Brest');
insert into RETRAITS (no_article, rue, code_postal, ville) values (2, 'rue de la carte mère', '99000', 'Silicon Valley');
insert into RETRAITS (no_article, rue, code_postal, ville) values (3, 'rue de la samba', '62000', 'Levallois-Perret');
insert into RETRAITS (no_article, rue, code_postal, ville) values (4, 'rue du click', '12000', 'Logitech');

insert into ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) values 
(2, 1, '28/06/2023', 300),
(2, 3, '20/06/2023', 190),
(2, 4, '19/06/2023', 410),
(3, 4, '21/06/2023', 430),
(4, 3, '01/07/2023', 220)
;
