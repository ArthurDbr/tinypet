<h1 align="center"> TinyPet </h1> <br>

<p align="center">
  Site web de pétitions, réalisé dans le cadre du module de développement d'applications sur le CLOUD.
</p>

**[tinypet2.appspot.com](tinypet2.appspot.com)**

## Sommaire

- [Interface](Interface)
- [Fonctionnalités](Fonctionnalités)
- [API Endpoints](API Endpoints)
- [Contributors](Contributors)
- [Build Process](Build Process)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Interface

Single Page App développée en MithrilJS
Conception de l'UI sous Adobe XD

![Imgur Image](https://imgur.com/a/4Z4Ip7p.jpg)

Cette partie du projet fût réaliser avec le langage mithril. 
Le projet est découpé en components réunis dans une Single Page App.
index.html à la racine du projet d'importe les feuilles de stylé CSS et les scripts Firebase ainsi que la logique de l'application dans le fichier App.js
Dans le dossier /src nous pouvons trouver le css de l'application qui utilise le framework bootstrap. 
Le dossier /models regroupe les classes faisant des requetes vers notre back-end ou des appels à firebase. 
Le dossier views regroupe les différents components de notre application.

## Fonctionnalités

* Inscription / Connexion par adresse email avec Firebase
* Créeation d'une pétition
* Visualisation du top 100 des pétitions
* Visualisation des pétitions de l'utilisateur connecté
* Vote pour une pétition
* Visualisation du nombre de pétitions en cours
* Visualisation du nombre de votes totaux 

**Les requêtes vers le Datastore s'appuient à la fois sur les dépendances bas niveau fournies par Google ainsi que sur la librairie Objectify**

<p >Aggrégats réalisés à l'écriture (création de pétition et vote) pour limiter les requêtes Datastore 
</p>
![Imgur Image](https://imgur.com/a/yMgF9iW.jpg)

## API Endpoints

**Get list of top 100 petitions**
GET https://tinypet2.appspot.com/_ah/api/tinyapi/v1/petitions

**Get petition by ID**
GET https://tinypet2.appspot.com/_ah/api/tinyapi/v1/petitions/PETID

**Get petitions created by User**
GET https://tinypet2.appspot.com/_ah/api/tinyapi/v1/petitions/user/USERID

**Get petitions voted by User**
GET https://tinypet2.appspot.com/_ah/api/tinyapi/v1/petitions/vote/user/USERID

**Post a vote to a petition**
GET https://tinypet2.appspot.com/_ah/api/tinyapi/v1/petitions/vote/PETID/USERID

**Post a petition**
POST https://tinypet2.appspot.com/_ah/api/tinyapi/v1/petitions?title=TITLE&description=DESCRIPTION&creator=CREATOR PETIT&idCreator=USERID

**Get aggregates (number of votes and petitions on the app)**
GET https://tinypet2.appspot.com/_ah/api/tinyapi/v1/aggregates

## Contributors

Arthur DEBAR & Alexis PETIT

## Build Process

Sur Eclipse : 'Deploy to Appengine'

