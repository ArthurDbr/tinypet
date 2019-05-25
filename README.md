<h1 align="center"> TinyPet </h1> <br>

<p align="center">
  Site web de pétitions, réalisé dans le cadre du module de développement d'applications sur le CLOUD.
</p>

**[tinypet2.appspot.com](tinypet2.appspot.com)**

## Sommaire

- [Interface]
- [Fonctionnalités]
- [API Endpoints]
- [Contributors]
- [Build Process]
- [Backers])
- [Sponsors]
- [Acknowledgments]

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Interface

Single Page App développée en MithrilJS
Conception de l'UI sous Adobe XD

<p align="center">
  <img src = "https://imgur.com/a/4Z4Ip7p" width=350>
</p>

## Fonctionnalités

* Inscription / Connexion par adresse email avec Firebase
* Créeation d'une pétition
* Visualisation du top 100 des pétitions
* Visualisation des pétitions de l'utilisateur connecté
* Vote pour une pétition
* Visualisation du nombre de pétitions en cours
* Visualisation du nombre de votes totaux 

<p align="center">Aggrégats réalisés à l'écriture (création de pétition et vote) pour limiter les requêtes Datastore 
  <img src = "https://imgur.com/a/yMgF9iW" width=700>
</p>

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

