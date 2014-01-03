===============
LO02 UNO Project
===============

	Version du projet : 3.0.0
	Couverture actuelle du code : 61.3 %

	GroupId : Uno_Bierge_Gagnaire
	ArtifactId : Uno_Bierge_Gagnaire
	Version : 3.0.0
	Packaging : jar

=====================================
SECTION 01 - Auteurs
---------------------------- 

	Laurent BIERGE
	Romain GAGNAIRE
	
=====================================
SECTION 02 - Sommaire
---------------------------- 

	1. Auteurs
	2. Sommaire
	3. Notes importantes
	4. Outils et bibliothèques utilisés
	5. Fichier de configuration
	6. Plugins recommandés
	7. Intégration de Maven sur Eclipse
	8. Intégration de JavaFX sur Eclipse
	9. Analyse des journaux

=====================================
SECTION 03 - Notes importantes
---------------------------- 

	Prérequis :
	Afin de pouvoir fonctionner correctement Eclipse a besoin d'un certain nombre de plugins et d'outils (dont certains sont installés par défaut) :
		Maven (se référer à la SECTION 7 - Intégration de Maven sur Eclipse)
		e(fx)clipse (se référer à la SECTION 8 - Intégration de JavaFX sur Eclipse)

	Eclipse & Codes ANSI :
		La version "console" de notre jeu UNO utilise des codes ANSI pour afficher du texte en couleur dans la console
		Eclipse en prend pas en charge ce code, affichant le texte en noir quoi qu'il arrive, raison pour laquelle le lancement du programme depuis Eclipse est déconseillé
		Pour avoir la gestion des codes ANSI dans la console d'éclipse, se référer à la SECTION 06 - Plugins Recommandés

	Méthode la plus simple :
		Il est donc préférable de lancer le JAR présent dans le dossier DIST. Un executable (UNO_Automated_Windows_Launcher.exe) est fourni permettant de lancer automatique le programme sous Windows. Il créera automatiquement les dossiers/fichiers de configuration qui pourraient vous être utiles.
		Pour Mac & Linux, vous devrez créer un dossier DIST (ie: dist/dist) et copier le fichier de configuration config.ini dans ce dernier (ie: /dist/dist/config.ini) si jamais vous souhaitez utiliser la fonction de lancement rapide de partie (utilisant une lecture du fichier de configuration)

	Ceci pour une raison très simple : 
		Eclipse utilise le fichier de configuration situé dans le dossier dist (on se trouve ainsi avec dist/config.ini)
		Le JAR executable se trouvant lui-même dans le dossier dist, il est nécessaire de recréer la même arborescence depuis cette nouvelle racine pour pouvoir charger le fichier de configuration. (Raison pour laquelle on se retrouve avec dist/dist/config.ini)
		De cette manière il est possible d'executer le programme au sein d'Eclipse ET depuis le JAR executable

=====================================
SECTION 04 - Outils et bibliothèques utilisées
---------------------------- 

Outils utilisés :
	Maven
	Log4j

Utilisation de bibliothèques (voir pom.xml) :
	JUnit : tests unitaires
	Mockito & PowerMock : fonctionnalités avancées pour les tests unitaires (amélioration des possibilités de tests unitaires permettant de simuler le comportement d'objets)
	Guava : multiples outils (gestion des préconditions, regex simplifées, matchers, entre autres)
	Gson : lecture et récupération d'informations à partir de fichiers formattés en JSON (pour fichier de configuration)
	Jansi : affichage en couleur dans la console
	Log4j : journalisation des différents évenements
	JavaFX : affichage graphique de la partie

=====================================
SECTION 05 - Fichier de configuration
---------------------------- 

Pour quelle raison :
	Il est possible de créer un fichier de configuration afin de permettre de lancer une partie sans avoir à taper le nom et le type de tous les joueurs
	Pour cela il suffit de créer un fichier nommé config.ini respectant formatté en JSON selon les règles décrites ci-après
	Il sera demandé, lors du lancement de l'application si vous souhaitez charger le fichier de configuration afin de créer tous les joueurs
	
L'arborescence JSON au sein du fichier de configuration doit respecter les règles suivantes :
	{
		"players": [ 
			{
				//Données d'un joueur
			}
		]
	}
	
Les données d'un joueur HUMAIN se présente sous la forme suivante (avec %% correspondant à une chaine de texte quelconque) :
	"nickname": "%%", 
	"status": "%%" 
	
Les données d'un joueur IA se présente sous la forme suivante (avec %% correspondant à une chaine de texte quelconque)
Veuillez noter que le champs difficultyLevel  DOIT contenir un nombre compris  entre 0 et 2 :
	"nickname": "AI 02", 
	"status": "AI", 
	"difficultyLevel [0-2]": "1" 

Ainsi, un fichier de configuration valide contiendra de 2 à 7 joueurs et se présentera de la façon suivante (par exemple) :
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

S'il y a moins de 2 joueurs, ou plus de 7, le fichier de configuration sera rejetté au moment de son chargement

=====================================
SECTION 06 - Plugins Recommandés
---------------------------- 

Pour quelles raisons :
	Si vous souhaitez tester la couverture du code par les tests unitaires : EclEmma
	Si vous souhaitez avoir un affichage du texte en couleur dans la console d'Eclipse : ANSI Escape in Console

EclEmma :
	EclEmma (http://goo.gl/kUMw96) est recommandé mais pas requis.
	Méthode d'installation : Help -> Eclipse Marketplace et taper dans find le nom associé.

ANSI Escape in Console :
	ANSI Escape in Console  (http://goo.gl/Yth0XK))n'est pas nécessaire si le programme est exécuté depuis le JAR --Méthode recommandée
	Si vous souhaitez tout de même utiliser Eclipse pour lancer le UNO la démarche est comme suit : Help -> Install new software -> Entrer http://www.mihai-nita.net/eclipse dans le champs Work With -> Cocher ANSI console -> Cliquer surNext/Finish

=====================================
SECTION 07 - Intégration de Maven sur Eclipse
---------------------------- 

Pour quelle raison :
	Ce projet utilise Maven pour gérer automatiquement les dépendances. 
	Cependant, il n'est installé nativement QUE dans la version Eclipse Java EE IDE for Web Developers
	Pour être lancé depuis Eclipse, le projet peut nécessiter l'installation de plugins au sein d'Eclipse en fonction de la version d'Eclipse installée.
	A noter que l'utilisation de Maven impose le fait que que les classes compilées se trouveront dans le dossier target/classes/*
	
Version d'Eclipse :
	Pour connaitre la version d'Eclipse, il suffit de cliquer sur Help -> About Eclipse.
	Pour installer un plugin dans Eclipse, il suffit de cliquer sur Help -> Eclipse Marketplace et taper dans find le nom associé.

Pour la version Eclipse Java EE IDE for Web Developers :
	Aucun plugin à installer

Pour la version Eclipse Standard/SDK (et toutes les autres) :
	Installer le plugin Maven Integration for Eclipse (Juno and newer) : ((http://goo.gl/vhmB4A)
	Installer le plugin Maven Integration for Eclipse WTP (Juno) : (http://goo.gl/pf2Oos)

Importation du projet sous Eclipse avec Maven :
	Il suffit de faire File -> Import -> Existing Maven Project

Fonctionnement de Maven (pour télécharger toutes les dépendances)
	1. Clique-droit sur la racine du projet -> Run As -> Maven Clean
	2. Clique-droit sur la racine du projet -> Run As -> Maven Install
	3. Clique-droit sur la racine du projet -> Maven -> Update Project	
	
=====================================
SECTION 08 - Intégration de JavaFX sur Eclipse
---------------------------- 

Pour quelle raison :
	Ce projet utilise JavaFX pour l'affichage dans une interface graphique
	JavaFX est un ensemble d'outils créés par Oracle permettant de concevoir et de créer des applications riches en java.
	Il n'existe à l'heure actuelle aucune intégration simple de ce framework dans Maven, raison motivant la nécessité du plugin e(fx)clipse, permettant l'intégration dans Eclipse

Installation de e(fx)clipse
	Installer le plugin e(fx)clipse : (http://goo.gl/oAqh6Q)
	Méthode d'installation : Help -> Eclipse Marketplace et taper dans find le nom associé.

Configuration du projet pour fonctionner avec JavaFX. Deux choix sont possible : 
	Utiliser `JavaFX 8` afin de profiter des dernières innovations
	Utiliser `JavaFX 2` demandant très peu de pré-requis
	
Pour `JavaFX 8` :
	1. Installer le JDK 8 ([site de téléchargement](https://jdk8.java.net/download.html)) --Notez que le JRE 8 est inclu dans le fichier d'installation du JDK 8
	2. Dans cette fenêtre, cliquer sur `JRE System Library` -> `Edit` -> Dans `Alternate JRE` selectionner `JR8`
	3. Dans Eclipse, `Clique-droit` sur la racine du projet -> `Properties` -> `Java Build Path` -> `Libraries`
	4. Vérifier que la version du fichier `jfxrt.jar` correspond à la version du JRE utilisée (32 bits ou 64 bits)
	5. Cliquer sur `Add JARs` pour l'ajouter -> aller dans `libs` puis dans `JDK8` puis dans le dossier `32 bits` ou `64 bits` et selectionner le jar correspondant
	
Pour `JavaFX 2` :
	1. Dans Eclipse, `Clique-droit` sur la racine du projet -> `Properties` -> `Java Build Path` -> `Libraries`
	2. Dans cette fenêtre, cliquer sur `JRE System Library` -> `Edit` -> Dans `Alternate JRE` selectionner `JR7`
	3. Vérifier que la version du fichier `jfxrt.jar` correspond à la version du JRE utilisée (32 bits ou 64 bits)
	4. Cliquer sur `Add JARs` pour l'ajouter -> aller dans `libs` puis dans `JDK7` puis dans le dossier `32 bits` ou `64 bits` et selectionner le jar correspondant

=====================================
SECTION 09 - Analyse des journaux
---------------------------- 

Le programme libre Notepad++ est formidable pour une telle chose puisqu'il laisse libre l'utilisateur de définir la coloration syntaxique

Pour le configurer rien de plus simple il suffit de télécharger un de ces fichiers xml :
	Pour un affichage BLANC sur NOIR : http://goo.gl/TSF6oo
	Pour un affichage NOIR sur BLANC : http://goo.gl/SNXYTN

Puis dans Notepad++ : 
	1. Cliquer sur `Langage` -> `Définir votre langage` -> `Importer` -> Naviguer jusqu'au fichier téléchargé
	2. Redemarrer Notepad++
	3. Cliquer sur `Langage` -> cliquer sur `log4j-black` ou `log4j-white` (tout en bas de la liste) quand un des fichiers .log est ouvert pour avoir la coloration associée	