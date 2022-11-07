# Installation du projet

## Pré-requis :

### Node (npm) & yarn

Vous devez installer [Node](https://nodejs.org/en/download/) sur votre machine, car comme vous avez dû le voir en regardant des tutoriels React nous avons besoin de l'outil **npm** (Node Package Manager). Cet outil est un gestionnaire de packages en ligne de commande, il vous permet d'installer de nombreuses librairie, framework, etc via la console. Il est inspensable dans la plupart des projets JavaScript (front et back). Il est installé en même temps que vous installez Node.

Vous devez ensuite installer **yarn** qui est un autre gestionnaire de package dont le fonctionnement est similaire à npm mais avec de meilleurs performances.

Pour l'installer il suffit de taper cela dans la console :
`npm install --global yarn`

Une fois cela fait vous aurez accès au commandes commençant par `yarn` dans la console au même titre que celle avec `npm`

Si vous avez des questions n'hésitez pas.

### VS Code

Vous devez installer les plugins VS Code suivant pour une meilleur compatibilité avec certains outils du projet :

- [Prettier](https://marketplace.visualstudio.com/items?itemName=esbenp.prettier-vscode)
- [ESLint](https://marketplace.visualstudio.com/items?itemName=dbaeumer.vscode-eslint)
- Conventionnal Commits

#### Prettier

Prettier est un formateur de code, je l'ai installé et préconfiguré dans le projet. L'extension VS Code permet de formatter automatiquement le code sans passer par la console.

Une fois installé vous devez modifier quelques configurations dans VS Code :

Pour accéder au paramètres :
`Fichier > Préférences > Paramètres`

Rechercher `Default Formatter` dans la barre de recherche.
Et modifier la configuration de ce paramètre avec le menu déroulant pour `Prettier - Code formatter`

Ensuite cherchez `Format On Save` et cochez l'option.

=> Désormais lorsque vous enregistrez un fichier il sera formatté selon les règles définies dans `.prettierrc`.

#### ESLint

ESLint est un analyseur de code qui permet de détecter des erreurs dans votre code, comme pour prettier je l'ai installé et pré-configuré dans le projet. Pour l'utiliser de manière optimal et voir les erreurs directement dans VS Code il faut installer l'extension.

Aucune configuration supplémentaire dans VS Code n'est nécessaire.

## Installation et Lancement

Une fois tous les pré-requis remplis vous devez installer les dépendances du projet en tapant `yarn` dans la console en étant dans le répertoire _frontend_. Cette commande va installer tous les packages.

Une fois cela fait vous pourrez lancer le projet avec `yarn dev`.

## Dépendances du projet

Le projets utilise différentes dépendances, en voici les principales :

- **react** & **react-dom** : Librairies du coeur de React
- [**react-router**](https://reactrouter.com/en/main) permet d'avoir "plusieurs pages"
- [**redux**](https://redux.js.org/) state manager

Je ne détailles pas les autres car elles servent surtout pour le développement.

## Annexe

Apprendre React :

- Sur le site de React :

  - https://fr.reactjs.org/tutorial/tutorial.html
  - https://fr.reactjs.org/docs/hello-world.html

- OpenClassroom
- Grafikart

Plein d'autre ressources sont disponibles sur internet ou sur YouTube.

Jetez aussi un oeil à Redux et React Router
