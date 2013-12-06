LO02 UNO Project
==============

Auteurs :
* Laurent BIERGE
* Romain GAGNAIRE

=====================================

Avant-propos :
* Pour fonctionner le projet necessite l'installation de plugins au sein d'Eclipse.
* Le nombre de plugins à installer varie en fonction de la version d'Eclipse installée
* Pour connaitre la version d'Eclipse, il suffit de cliquer sur "Help" puis sur "About Eclipse".
* Pour installer un plugin dans Eclipse, il suffit de cliquer sur "Help" puis sur "Eclipse Marketplace" et taper dans find le nom associé.

=====================================

Pour la version "Eclipse Java EE IDE for Web Developers" 
* Aucun

Pour la version "Eclipse Standard/SDK" (et toutes les autres)
* plugin "Maven Integration for Eclipse (Juno and newer)" : http://goo.gl/vhmB4A
* plugin "Maven Integration for Eclipse WTP (Juno)" : http://goo.gl/pf2Oos

A noter que le plugin "EclEmma" (http://goo.gl/kUMw96) est recommandé mais pas requis.
Il permet juste de constater de la couverture des tests unitaires (dans une certaine mesure)

=====================================

Version du projet : 0.7.0

=====================================

Outils utilisés :
- Maven

- GroupId : Uno_Bierge_Gagnaire
- ArtifactId : Uno_Bierge_Gagnaire
- Version : 0.7.0
- Packaging : jar

=====================================

Utilisation des bibliothèques :
- JUnit : tests unitaires
- Mockito & PowerMock : fonctionnalités avancées pour les tests unitaires (amélioration des possibilités de tests unitaires permettant de simuler le comportement d'objets)
- Guava : multiples outils (gestion des préconditions, regex simplifées, matchers, entre autres)
- Jansi : affichage en couleur dans la console