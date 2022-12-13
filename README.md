# Projet PGL

- Informations sur le projet frontend [ici](frontend/README.md)

## Backend

Comment est découpé notre backend ?
On a 2 grands dossiers le dossier **Test**, où l'on réalise tous les tests concernants le backend. Il y a **Main**, où on créé, modifie, appelle, supprime, ... Le dossier **Main** est _le_ dossier où tous les éléments constituants le backend sont présents.

## Organisation du backend

- **Main**
  - **config**
    > Configuration de l'application, des mails en html, partie mot de passe oublié, doc de l'API et le site
  - **consumeApi**
    > Utilisation / appel de l'Api d'irigo et Traçage d'entité bus, busStop et BusLine
    - **response**
      > Récupération des information pour bus, busStop, BusLine et StopTime
  - **controller**
    > Envoi/traitements des informations recceuilli dans service. Pour autorisation, bus, commentaire et l'utilisateur
  - **dao**
    > Regrouper les accès aux données persistantes des bus, busLine, busStop, comment, passwordReset et l'utilisateur
  - **enumeration**
    > Enumération pour l'inscription à propos les habitudes de voyage, le sex et la fréquence de voyage
  - **exception**
    > Traitement des différents types de
  - **model**
    > Création des classes utilisateur, récupération de mot de passe, note pour la ligne, coordonnées, commentaires, arrêt de bus, ligne de bus eet bus
  - **payload**
    > Dto sont des objets qui transportent les données entre les processus afin de réduire le nombre d'appels de méthodes. Il y en a pour l'autentification, les commentaires et pour l'utilisateur
    - **mapper**
      > on set les commentaires, l'utilisateur et l'autentification via le dto
    - **request**
      > on fait des requêtes pour l'oubli du mot de passe, la connexion, l'opinion, la réinitialisation du mot de passe, l'inscription et la sauvegade des données utilisateur
      - **mapper**
        > on set les requêtes à partir de la dto pour les commentaires et l'utilisateur
    - **response**
      > on gère les réponses de requête pour les bus, les lignes de bus, les arrêts de bus, la gestion des erreurs, quand le temps est dépassé et la classe response
      - **mapper**
        > on set les réponses de requêtes
  - **security**
    > on gère de manière plus sécurisé les accès, l'autentification, les données utilisateurs et la connexion
  - **service**
    > Regroupe les fonctions concernant l'utilisateur, les bus, les commentaires, les mails et l'autentification
- **Test**
  > Test sur l'utilisateur et sur la Dao des commentaires
  - **service**
    > Test concernant le mail et l'utilisateur

## Informations importantes avant de lancer de manière locale

Afin de pouvoir lancer le backend vous devez avant avoir fait les éléments suivant:

- Installer mariadb ou un autre logiciel pour la base de donnée
- Créer la table dans votre base de donnée
- Modifier dans les propriétés du backend le nom de table et le mot de passe pour accéder à la base de donnée
- Utiliser IntelliJ avec les modules Spring
