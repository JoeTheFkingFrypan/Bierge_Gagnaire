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
	Si vous souhaitez de même utiliser Eclipse pour lancer le UNO la démarche est comme suit : Help -> Install new software -> Entrer http://www.mihai-nita.net/eclipse dans le champs Work With -> Cocher ANSI console -> Cliquer surNext/Finish

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

=====================================
SECTION 09 - Analyse des journaux
---------------------------- 

Le programme libre Notepad++ est formidable pour une telle chose puisqu'il laisse libre l'utilisateur de définir la coloration syntaxique

Pour le configurer rien de plus simple il suffit d'éditier le fichier "" situé dans "%APPDATA%\Notepad++", et de rajouter les lignes suivantes (ou de créer le fichier s'il n'existe pas) :

	<NotepadPlus>
		<UserLang name="Log4j" ext="log">
			<Settings>
				<Global caseIgnored="no" escapeChar=":" />
				<TreatAsSymbol comment="yes" commentLine="no" />
				<Prefix words1="no" words2="no" words3="no" words4="no" />
			</Settings>
			<KeywordLists>
				<Keywords name="Delimiters">[&apos;0]&apos;0</Keywords>
				<Keywords name="Folder+"></Keywords>
				<Keywords name="Folder-"></Keywords>
				<Keywords name="Operators">-</Keywords>
				<Keywords name="Comment">1/* 2*/ 0// 0</Keywords>
				<Keywords name="Words1">ERROR</Keywords>
				<Keywords name="Words2">FATAL</Keywords>
				<Keywords name="Words3">INFO</Keywords>
				<Keywords name="Words4">WARN</Keywords>
			</KeywordLists>
			<Styles>
				<WordsStyle name="DEFAULT" styleID="11" fgColor="FFFFFF" bgColor="FFFFFF" colorStyle="0" fontName="Courier New" fontStyle="0" />
				<WordsStyle name="FOLDEROPEN" styleID="12" fgColor="000000" bgColor="FFFFFF" fontName="" fontStyle="0" />
				<WordsStyle name="FOLDERCLOSE" styleID="13" fgColor="000000" bgColor="FFFFFF" fontName="" fontStyle="0" />
				<WordsStyle name="KEYWORD1" styleID="5" fgColor="FF8080" bgColor="FFFFFF" colorStyle="1" fontName="" fontStyle="0" />
				<WordsStyle name="KEYWORD2" styleID="6" fgColor="FF0000" bgColor="FFFFFF" colorStyle="1" fontName="" fontStyle="1" />
				<WordsStyle name="KEYWORD3" styleID="7" fgColor="0080FF" bgColor="FFFFFF" colorStyle="1" fontName="" fontStyle="0" />
				<WordsStyle name="KEYWORD4" styleID="8" fgColor="FFFF00" bgColor="FFFFFF" colorStyle="1" fontName="" fontStyle="0" />
				<WordsStyle name="COMMENT" styleID="1" fgColor="008040" bgColor="FFFFFF" fontName="" fontStyle="0" />
				<WordsStyle name="COMMENT LINE" styleID="2" fgColor="008040" bgColor="FFFFFF" fontName="" fontStyle="0" />
				<WordsStyle name="NUMBER" styleID="4" fgColor="FFFF80" bgColor="FFFFFF" fontName="" fontStyle="0" />
				<WordsStyle name="OPERATOR" styleID="10" fgColor="FFFFFF" bgColor="FFFFFF" colorStyle="2" fontName="" fontStyle="0" />
				<WordsStyle name="DELIMINER1" styleID="14" fgColor="C0C0C0" bgColor="FFFFFF" colorStyle="1" fontName="" fontStyle="0" />
				<WordsStyle name="DELIMINER2" styleID="15" fgColor="FF8080" bgColor="FFFFFF" colorStyle="1" fontName="" fontStyle="0" />
				<WordsStyle name="DELIMINER3" styleID="16" fgColor="000000" bgColor="FFFFFF" fontName="" fontStyle="0" />
			</Styles>
		</UserLang>
	</NotepadPlus>
