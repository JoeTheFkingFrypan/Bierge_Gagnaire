LO02 UNO Project
Version du projet : 2.0.0

=====================================
SECTION 01 - Auteurs
---------------------------- 

	* Laurent BIERGE
	* Romain GAGNAIRE

=====================================
SECTION 02 - Notes
-------------------------

Eclipse & Codes ANSI :

	* La version "console" de notre jeu UNO utilise des codes ANSI pour afficher du texte en couleur dans la console
	* Eclipse en prend pas en charge ce code, affichant le texte en noir quoi qu'il arrive, raison pour laquelle le lancement du programme depuis Eclipse est d�conseill�
	* Pour avoir la gestion des codes ANSI dans la console d'�clipse, se r�f�rer � la SECTION 4

M�thode la plus simple :

	* Il est donc pr�f�rable de lancer le JAR pr�sent dans le dossier DIST. Un executable (.exe) est fourni permettant de lancer automatique le programme sous Windows.
	* [1] Pour Mac & Linux, il est fort possible que vous ayez � cr�er un dossier DIST (dans le dossier DIST --sous-entendu dist/dist) et de copier le fichier de configuration config.ini dans ce dernier (--sous-entendu /dist/dist/config.ini) si jamais vous souhaitez utiliser la fonction de lancement rapide de partie (utilisant une lecture du fichier de configuration)

[1] Ceci pour une raison tr�s simple : 

	* Le programme utilise le fichier de configuration situ� dans le dossier DIST.
	* Lors de l'export de l'executable JAR, le dossier contenant l'executable �tant le dossier DIST, il est n�cessaire de recr�er ce m�me sch�ma pour pouvoir charger le fichier de configuration
	* De cette mani�re il est possible d'executer le programme au sein d'Eclipse ET depuis le JAR executable

=====================================
SECTION 03 - Maven
--------------------------

Maven & Eclipse :

	* Ce projet utilise Maven pour g�rer automatiquement les d�pendances. Cependant, il n'est install� nativement QUE dans la version "Eclipse Java EE IDE for Web Developers" 
	* Pour �tre lanc� depuis Eclipse, le projet peut necessiter l'installation de plugins au sein d'Eclipse en fonction de la version d'Eclipse install�e.
	* A noter que l'utilisation de Maven impose le fait que que les classes compil�es se trouveront dans le dossier target/classes/*
	
Version d'Eclipse :

	* Pour connaitre la version d'Eclipse, il suffit de cliquer sur "Help" -> "About Eclipse".
	* Pour installer un plugin dans Eclipse, il suffit de cliquer sur "Help" -> "Eclipse Marketplace" et taper dans find le nom associ�.

Pour la version "Eclipse Java EE IDE for Web Developers" :

	* Aucun plugin � installer

Pour la version "Eclipse Standard/SDK" (et toutes les autres) :

	* plugin "Maven Integration for Eclipse (Juno and newer)" : http://goo.gl/vhmB4A
	* plugin "Maven Integration for Eclipse WTP (Juno)" : http://goo.gl/pf2Oos

Importation du projet sous Eclipse avec Maven :

	* Il suffit ensuite pour IMPORTER LE PROJET dans Eclipse de faire "File" -> "Import" -> "Existing Maven Project"

=====================================
SECTION 04 - Plugins Recommand�s
----------------------------------------------

Pour quelles raisons :

	* Si vous souhaitez tester la couverture du code par les tests unitaires : EclEmma
	* Si vous souhaitez avoir un affichage du texte en couleur dans la console d'Eclipse : ANSI Escape in Console

EclEmma :

	* "EclEmma" (http://goo.gl/kUMw96) est recommand� mais pas requis.
	* M�thode d'installation : "Help" -> "Eclipse Marketplace" et taper dans find le nom associ�.

ANSI Escape in Console :

	* "ANSI Escape in Console" (http://goo.gl/YfzVB4) est dispensable si le programme est lanc� directement depuis le jar --m�thode recommand�e
	* M�thode d'installation : "Help" -> "Install new software" -> Entrer http://www.mihai-nita.net/eclipse dans le champs "Work With" -> Cocher "ANSI console" -> Next/Finish

=====================================
SECTION 05 - Statut de l'application
--------------------------------------------

pom.xml :

	* GroupId : Uno_Bierge_Gagnaire
	* ArtifactId : Uno_Bierge_Gagnaire
	* Version : 2.0.0
	* Packaging : jar

Couverture actuelle du code (tests unitaires) : 61.3 %

=====================================
SECTION 05 - Outils et biblioth�ques utilis�es
-------------------------------------------------------

Outils utilis�s :

	* Maven

Utilisation de biblioth�ques (voir pom.xml) :

	* JUnit : tests unitaires
	* Mockito & PowerMock : fonctionnalit�s avanc�es pour les tests unitaires (am�lioration des possibilit�s de tests unitaires permettant de simuler le comportement d'objets)
	* Guava : multiples outils (gestion des pr�conditions, regex simplif�es, matchers, entre autres)
	* Gson : lecture et r�cup�ration d'informations � partir de fichiers formatt�s en JSON (pour fichier de configuration)
	* Jansi : affichage en couleur dans la console
	
=====================================
SECTION 06 - Fichier de configuration
-------------------------------------------------------

Pour quel emploi :

	* Il est possible de cr�er un fichier de configuration afin de permettre de lancer une partie sans avoir � taper le nom et le type de tous les joueurs
	* Pour cela il suffit de cr�er un fichier nomm� "config.ini" respectant formatt� en JSON selon les r�gles d�crites ci-apr�s
	* Il sera demand�, lors du lancement de l'application si vous souhaitez charger le fichier de configuration afin de cr�er tous les joueurs
	
L'arborescence JSON au sein du fichier de configuration doit respecter les r�gles suivantes :

	{
		"players": [ 
			{
				//Donn�es d'un joueur
			}
		]
	}
	
Les donn�es d'un joueur HUMAIN se pr�sente sous la forme suivante (avec %% correspondant � une chaine de texte quelconque) :
	
	"nickname": "%%", 
	"status": "%%" 
	
Les donn�es d'un joueur IA se pr�sente sous la forme suivante (avec %% correspondant � une chaine de texte quelconque), notez que le champs difficultyLevel DOIT contenir un nombre compris entre 0 et 2 :
	
	"nickname": "AI 02", 
	"status": "AI", 
	"difficultyLevel [0-2]": "1" 

Ainsi, un fichier de configuration valide contiendra de 2 � 7 joueurs et se pr�sentera de la fa�on suivante (par exemple) :

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

S'il y a moins de 2 joueurs, ou plus de 7, le fichier de configuration sera rejett� au moment de son chargement