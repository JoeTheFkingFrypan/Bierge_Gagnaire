LO02 UNO Project
---

<p>Version du projet : 3.0.0</p>
Couverture actuelle du code : 61.3 %


* GroupId : Uno_Bierge_Gagnaire
* ArtifactId : Uno_Bierge_Gagnaire
* Version : 2.0.0
* Packaging : jar

SECTION 01 - Auteurs
---
* Laurent BIERGE
* Romain GAGNAIRE
	
SECTION 02 - Sommaire
---
1. Auteurs
2. Sommaire
3. Notes importantes
4. Outils et bibliothèques utilisés
5. Fichier de configuration
6. Plugins recommandés
7. Intégration de Maven sur Eclipse
8. Intégration de JavaFX sur Eclipse
9. Analyse des journaux

SECTION 03 - Notes importantes
---

#### Prérequis

Afin de pouvoir fonctionner correctement Eclipse a besoin d'un certain nombre de plugins et d'outils (dont certains sont installés par défaut) :

* `Maven` (se référer à la SECTION 7 - Intégration de Maven sur Eclipse)

#### Eclipse & Codes ANSI :

> La version "console" de notre jeu UNO utilise des codes ANSI pour afficher du texte en couleur dans la console

> Eclipse en prend pas en charge ce code, affichant le texte en noir quoi qu'il arrive, raison pour laquelle le lancement du programme depuis Eclipse est déconseillé

> Pour avoir la gestion des codes ANSI dans la console d'éclipse, se référer à la __`SECTION 06 - Plugins Recommandés`__

#### Méthode la plus simple :

* Il est donc préférable de lancer le JAR présent dans le dossier DIST. Un executable (`UNO_Automated_Windows_Launcher.exe`) est fourni permettant de lancer automatique le programme sous Windows. Il créera automatiquement les dossiers/fichiers de configuration qui pourraient vous être utiles.
* Pour Mac & Linux, vous devrez créer un dossier DIST (ie: `dist/dist`) et copier le fichier de configuration config.ini dans ce dernier (ie: `/dist/dist/config.ini`) si jamais vous souhaitez utiliser la fonction de lancement rapide de partie (utilisant une lecture du fichier de configuration)

#### Ceci pour une raison très simple : 

> Eclipse utilise le fichier de configuration situé dans le dossier `dist` (on se trouve ainsi avec`dist/config.ini`)

> Le JAR executable se trouvant lui-même dans le dossier `dist`, il est nécessaire de recréer la même arborescence depuis cette nouvelle racine pour pouvoir charger le fichier de configuration. (Raison pour laquelle on se retrouve avec`dist/dist/config.ini`)

>De cette manière il est possible d'executer le programme au sein d'Eclipse <b>ET</b> depuis le JAR executable

SECTION 04 - Outils et bibliothèques utilisées
---

#### Outils utilisés :

* `Maven`
* `Log4J`
* `JavaFX`

#### Utilisation de bibliothèques (voir `pom.xml`) :

* `JUnit` : tests unitaires
* `Mockito` & `PowerMock` : fonctionnalités avancées pour les tests unitaires (amélioration des possibilités de tests unitaires permettant de simuler le comportement d'objets)
* `Guava` : multiples outils (gestion des préconditions, regex simplifées, matchers, entre autres)
* `Gson` : lecture et récupération d'informations à partir de fichiers formattés en JSON (pour fichier de configuration)
* `Jansi` : affichage en couleur dans la console
* `Log4j` : journalisation des différents évenements
* `JavaFX` : affichage graphique de la partie

SECTION 05 - Fichier de configuration
---

#### Pour quelle raison :

* Il est possible de créer un fichier de configuration afin de permettre de lancer une partie sans avoir à taper le nom et le type de tous les joueurs
* Pour cela il suffit de créer un fichier nommé `config.ini` respectant formatté en `JSON` selon les règles décrites ci-après
* Il sera demandé, lors du lancement de l'application si vous souhaitez charger le fichier de configuration afin de créer tous les joueurs
	
#### L'arborescence __JSON__ au sein du fichier de configuration doit respecter les __règles__ suivantes :

	{
		"players": [ 
			{
				//Données d'un joueur
			}
		]
	}
	
#### Les données d'un joueur __HUMAIN__ se présente sous la forme suivante (avec %% correspondant à une chaine de texte quelconque) :
	
	"nickname": "%%", 
	"status": "%%" 
	
Les données d'un joueur __IA__ se présente sous la forme suivante (avec %% correspondant à une chaine de texte quelconque)

Veuillez noter que le champs difficultyLevel  __DOIT__ contenir un nombre compris  __entre 0 et 2__ :
	
	"nickname": "AI 02", 
	"status": "AI", 
	"difficultyLevel [0-2]": "1" 

Ainsi, un fichier de configuration valide contiendra de __2 à 7 joueurs__ et se présentera de la façon suivante (par exemple) :

	{ 
		"players": [ 
			{ 
				"nickname": "player1", 
				"status": "human" 
			}, 
			{ 
				"nickname": "AI1", 
				"status": "AI", 
				"difficultyLevel [0-2]": "0" 
			}, 
			{ 
				"nickname": "AI2", 
				"status": "AI", 
				"difficultyLevel [0-2]": "1" 
			}
		] 
	}

S'il y a moins de 2 joueurs, ou plus de 7, le fichier de configuration __sera rejetté__ au moment de son chargement

SECTION 06 - Plugins Recommandés
---

#### Pour quelles raisons :

> Si vous souhaitez tester la couverture du code par les tests unitaires : __`EclEmma`__

> Si vous souhaitez avoir un affichage du texte en couleur dans la console d'Eclipse : __`ANSI Escape in Console`__

> Si vous souhaitez pouvoir editer de manière intuitive les fichiers fxml, css (ou tout autres dérivés de JavaFX) : __`e(fx)clipse`__

#### EclEmma :

* `EclEmma` ([site officiel](http://goo.gl/kUMw96)) est recommandé mais pas requis.
* Méthode d'installation : `Help` -> `Eclipse Marketplace` et taper dans `find` le nom associé.

#### ANSI Escape in Console :

* `ANSI Escape in Console`  ([site officiel](http://goo.gl/Yth0XK)) n'est pas nécessaire si le programme est exécuté depuis le JAR --__Méthode recommandée__
* Si vous souhaitez tout de même utiliser Eclipse pour lancer le UNO la démarche est comme suit : `Help` -> `Install new software` -> Entrer `http://www.mihai-nita.net/eclipse` dans le champs `Work With` -> Cocher `ANSI console` -> Cliquer sur`Next/Finish`

#### E(fx)clipse

* Se référerer à la SECTION 08 - Intégration de JavaFX sur Eclipse

SECTION 07 - Intégration de Maven sur Eclipse
---

#### Pour quelle raison :

> Ce projet utilise Maven pour gérer automatiquement les dépendances. 

> Cependant, il n'est installé nativement <b>QUE</b> dans la version __`Eclipse Java EE IDE for Web Developers`__

> Pour être lancé depuis Eclipse, le projet peut nécessiter l'installation de plugins au sein d'Eclipse en fonction de la version d'Eclipse installée.

> A noter que l'utilisation de Maven impose le fait que que les classes compilées se trouveront dans le dossier `target/classes/*`
	
#### Version d'Eclipse :

* Pour connaitre la version d'Eclipse, il suffit de cliquer sur `Help` -> `About Eclipse`.
* Pour installer un plugin dans Eclipse, il suffit de cliquer sur `Help` -> `Eclipse Marketplace` et taper dans `find` le nom associé.

#### Pour la version __`Eclipse Java EE IDE for Web Developers`__ :

* Aucun plugin à installer

#### Pour la version __`Eclipse Standard/SDK`__ (et toutes les autres) :

* Installer le plugin `Maven Integration for Eclipse (Juno and newer)` : ([site officiel](http://goo.gl/vhmB4A))
* Installer le plugin `Maven Integration for Eclipse WTP (Juno)` : ([site officiel](http://goo.gl/pf2Oos))
* Méthode d'installation : `Help` -> `Eclipse Marketplace` et taper dans `find` le nom associé.

#### Importation du projet sous Eclipse avec Maven :

* Il suffit de faire `File` -> `Import` -> `Existing Maven Project`

#### Fonctionnement de Maven (pour télécharger toutes les dépendances)
1. `Clique-droit` sur la racine du projet -> `Run As` -> `Maven Clean`
2. `Clique-droit` sur la racine du projet -> `Run As` -> `Maven Install`
3. `Clique-droit` sur la racine du projet -> `Maven` -> `Update Project`

SECTION 08 - Intégration de JavaFX sur Eclipse
---

### Pour quelle raison :

> Ce projet utilise JavaFX pour l'affichage dans une interface graphique

> JavaFX est un ensemble d'outils créés par Oracle permettant de concevoir et de créer des applications riches en java.

> Il n'existe à l'heure actuelle aucune intégration simple de ce framework dans Maven, raison motivant l'importation de jars correspondant à JavaFX dans le dossier `libs` du projet

> Un outil est mis à disposition par Oracle afin de faciliter le développement d'applications utilisant JavaFX : 

> [JavaFX SceneBuilder](http://www.oracle.com/technetwork/java/javafx/tools/index.html) permettant de créer intuitivement des interfaces graphiques par des outils visuels

### Installation de e(fx)clipse

* Son rôle est de faciliter l'intégration de JavaFX dans Eclipse, en fournissant un éditeur de fichiers fxml et de l'autocomplétion concernant les fichiers css
* Installer le plugin `e(fx)clipse` : ([site officiel](http://goo.gl/oAqh6Q))
* Méthode d'installation : `Help` -> `Eclipse Marketplace` et taper dans `find` le nom associé.

###Configuration du projet pour fonctionner avec JavaFX

Deux choix sont possible : 
	
* Utiliser `JavaFX 8` afin de profiter des dernières innovations (animations, fenêtre modales, etc)
* Utiliser `JavaFX 2` demandant très peu de pré-requis
	
Pour `JavaFX 8` :

1. Installer le JDK 8 ([site de téléchargement](https://jdk8.java.net/download.html)) --Notez que le JRE 8 est inclu dans le fichier d'installation du JDK 8
2. Dans Eclipse, faire `Clique-droit` sur la racine du projet -> `Properties` -> `Java Build Path` -> `Libraries`
3. Vérifier que `JRE System Library` indique `[jre8]`

Si tel n'est pas le cas :
1. Cliquer sur `JRE System Library` -> `Edit` -> Dans `Alternate JRE` sélectionner `JR8`

Pour `JavaFX 2` :

1. Dans Eclipse, faire `Clique-droit` sur la racine du projet -> `Properties` -> `Java Build Path` -> `Libraries`
2. Vérifier que `JRE System Library` indique `[jre7]`

Si tel n'est pas le cas :

1. Cliquer sur `JRE System Library` -> `Edit` -> Dans `Alternate JRE` sélectionner `JR7`

#### Dans tous les cas

1. Vérifier que les 4 jars jfxrt.X.X.YYbits soient présents (1 seul suffit, mais cela suppose que vous sachiez lequel correspond à votre version de Java)
2. Pour en rajouter un : Cliquer sur `Add JARs` pour l'ajouter -> aller dans le dossier `libs` puis dans `JDKX` puis dans le dossier `32 bits` ou `64 bits` et selectionner le jar correspondant

SECTION 09 - Analyse des journaux
---

Le programme libre Notepad++ est formidable pour une telle chose puisqu'il laisse libre l'utilisateur de définir la coloration syntaxique

Pour le configurer rien de plus simple il suffit de télécharger un de ces fichiers xml :

* Pour un affichage BLANC sur NOIR : http://goo.gl/TSF6oo
* Pour un affichage NOIR sur BLANC : http://goo.gl/SNXYTN

Puis dans Notepad++ : 

1. Cliquer sur `Langage` -> `Définir votre langage` -> `Importer` -> Naviguer jusqu'au fichier téléchargé
2. Redemarrer Notepad++
3. Cliquer sur `Langage` -> cliquer sur `log4j-black` ou `log4j-white` (tout en bas de la liste) quand un des fichiers .log est ouvert pour avoir la coloration associée