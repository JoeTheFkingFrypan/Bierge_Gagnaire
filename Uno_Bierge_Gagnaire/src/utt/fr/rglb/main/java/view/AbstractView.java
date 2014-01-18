package utt.fr.rglb.main.java.view;

import java.io.Serializable;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;

/**
 * Classe définissant les méthodes d'affichage
 */
public abstract class AbstractView implements Serializable {
	private static final long serialVersionUID = 1L;

	/* ========================================= EMPHASIS ========================================= */

	/**
	 * Méthode permettant d'afficher un titre avec emphase
	 * @param title Titre à afficher
	 */
	public abstract void displayTitle(String title);

	/**
	 * Méthode permettant d'utiliser du texte comme barre de séparation (affichage en couleurs négatives)
	 * @param text Texte à afficher
	 */
	public abstract void displaySeparationText(String text);

	/* ========================================= CARD DISPLAY ========================================= */

	/**
	 * Méthode permettant d'afficher une carte dans l'interface
	 * @param string Message à afficher avant la carte
	 * @param card Carte à afficher
	 */
	public abstract void displayCard(String string, Card card);
	
	/**
	 * Méthode permettant d'afficher une collection de cartes complète
	 * @param string Message à afficher avant la collection
	 * @param cards Collection de cartes à afficher
	 */
	public abstract void displayCardCollection(String string, Collection<Card> cards);
	
	/**
	 * Méthode privée permettant d'afficher une carte avec gestion de sa couleur et de son type (numérotée, spéciale)
	 * @param cardToDisplay Carte à afficher
	 */
	public abstract void displayOneCard(Card cardToDisplay) ;

	/**
	 * Méthode privée permettant d'afficher une unique carte avec gestion de sa couleur et de son type (numérotée, spéciale) en accolant son index
	 * @param cardToDisplay Carte à afficher
	 * @param index Index de la carte 
	 */
	public abstract void displayOneCard(Card cardToDisplay, int index);
	
	/* ========================================= TEXT DISPLAY ========================================= */
	
	/**
	 * Méthode permettant d'afficher une uniquement ligne de couleur jaune dans l'interface (avec 2 mots mis en emphase)
	 * @param part01 Première partie du texte à afficher
	 * @param placeholder01 Partie mise en emphase (blanc)
	 * @param part02 Deuxième partie du texte à afficher
	 * @param placeholder02 Partie mise en emphase (blanc)
	 * @param part03 Troisième partie du texte à afficher
	 */
	public abstract void displayOneLineOfYellowText(String part01, String placeholder01, String part02, String placeholder02, String part03);
	
	/**
	 * Méthode permettant d'afficher une unique ligne de couleur verte dans l'interface
	 * @param string Message à afficher
	 */
	public abstract void displayOneLineOfGreenText(String string);
	
	/**
	 * Méthode permettant d'afficher une unique ligne de couleur verte avec un élément mis en emphase (placeholder)
	 * @param part01 Première partie du texte à afficher
	 * @param placeholder01 Partie mise en emphase (blanc)
	 * @param part02 Deuxième partie du texte à afficher
	 */
	public abstract void displayGreenEmphasisUsingPlaceholders(String part01, String placeholder01, String part02);
	
	/**
	 * Méthode permettant d'afficher une unique ligne de couleur verte avec un élément mis en emphase (placeholder)
	 * @param part01 Première partie du texte à afficher
	 * @param placeholder01 Partie mise en emphase (blanc)
	 * @param part02 Deuxième partie du texte à afficher
	 * @param placeholder02 Partie mise en emphase (blanc)
	 * @param part03 Troisième partie du texte à afficher
	 */
	public abstract void displayGreenEmphasisUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03);
	
	/**
	 * Méthode permettant d'afficher une unique ligne de couleur magenta dans l'interface
	 * @param string Message à afficher
	 */
	public abstract void displayOneLineOfJokerText(String string);

	/**
	 * Méthode permettant d'afficher 2 lignes de couleur magenta dans la console
	 * @param string Message à afficher sur la 1ère ligne
	 * @param string2 Message à afficher sur la 2ème ligne
	 */
	public abstract void displayTwoLinesOfJokerText(String string, String string2);

	/**
	 * Méthode permettant d'afficher une unique ligne de couleur magenta dans l'interface (avec 1 mot mis en emphase)
	 * @param part01 Première partie du texte à afficher
	 * @param placeholder01 Partie mise en emphase (blanc)
	 * @param part02 Deuxième partie du texte à afficher
	 */
	public abstract void displayJokerEmphasisUsingPlaceholders(String part01, String placeholder01, String part02);

	/**
	 * Méthode permettant d'afficher une unique ligne de couleur magenta dans l'interface (avec 1 mot mis en emphase et affichage d'une carte)
	 * Utilisée lors du jeu d'une carte par l'IA --permet d'avoir un retour visuel sur ses décisions
	 * @param part01 Première partie du texte à afficher
	 * @param card Carte à afficher
	 */
	public abstract void displayJokerEmphasisUsingPlaceholders(String part01, Card card);

	/**
	 * Méthode permettant d'afficher une unique ligne de couleur magenta dans l'interface (avec 2 mots mis en emphase)
	 * @param part01 Première partie du texte à afficher
	 * @param placeholder01 Partie mise en emphase (blanc)
	 * @param part02 Deuxième partie du texte à afficher
	 * @param placeholder02 Partie mise en emphase (blanc)
	 * @param part03 Troisième partie du texte à afficher
	 */
	public abstract void displayJokerEmphasisUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03);

	/**
	 * Méthode permettant d'afficher une unique ligne de couleur magenta dans l'interface (avec 3 mots mis en emphase)
	 * @param part01 Première partie du texte à afficher
	 * @param placeholder01 Partie mise en emphase (blanc)
	 * @param part02 Deuxième partie du texte à afficher
	 * @param placeholder02 Partie mise en emphase (blanc)
	 * @param part03 Troisième partie du texte à afficher
	 * @param placeholder03 Partie mise en emphase (blanc)
	 * @param part04 Quatrième partie du texte à afficher
	 */
	public abstract void displayJokerEmphasisUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03, String placeholder03, String part04);

	/**
	 * Méthode permettant d'afficher une unique ligne de couleur blanche dans l'interface
	 * @param string Message à afficher
	 */
	public abstract void displayOneLineOfBoldText(String string);

	/**
	 * Méthode permettant d'afficher du texte de couleur blanche (avec 1 mot emphasé) sans aller à la ligne
	 * @param part01 Première partie du texte à afficher
	 * @param placeholder01 Partie mise en emphase (joker/magenta)
	 * @param part02 Deuxième partie du texte à afficher
	 */
	public abstract void StartOneLineOfBoldText(String part01, String placeholder01, String part02);
	
	/**
	 * Méthode permettant d'afficher du texte de couleur blanche sur une ligne déjà existante
	 * @param string Message à afficher
	 */
	public abstract void AppendOneLineOfBoldText(String string);
	
	/**
	 * Méthode permettant d'afficher une unique ligne de couleur rouge dans l'interface
	 * @param string Message à afficher
	 */
	public abstract void displayOneLineOfRedText(String string);

	/**
	 * Méthode permettant d'afficher 2 lignes de couleur rouge dans la console
	 * @param string Message à afficher sur la 1ère ligne
	 * @param string2 Message à afficher sur la 2ème ligne
	 */
	public abstract void displayErrorMessage(String string, String string2);

	/**
	 * Méthode permettant d'afficher une unique ligne de couleur rouge dans l'interface (avec 1 mot mis en emphase)
	 * @param part01 Première partie du texte à afficher
	 * @param placeholder01 Partie mise en emphase (blanc)
	 * @param part02 Deuxième partie du texte à afficher
	 */
	public abstract void displayErrorMessageUsingPlaceholders(String part01, String placeholder01, String part02);

	/**
	 * Méthode permettant d'afficher une unique ligne de couleur rouge dans l'interface (avec 2 mots mis en emphase)
	 * @param part01 Première partie du texte à afficher
	 * @param placeholder01 Partie mise en emphase (blanc)
	 * @param part02 Deuxième partie du texte à afficher
	 * @param placeholder02 Partie mise en emphase (blanc)
	 */
	public abstract void displayErrorMessageUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02);

	/**
	 * Méthode permettant d'afficher une unique ligne de couleur rouge dans l'interface (avec 2 mots mis en emphase)
	 * @param part01 Première partie du texte à afficher
	 * @param placeholder01 Partie mise en emphase (blanc)
	 * @param part02 Deuxième partie du texte à afficher
	 * @param placeholder02 Partie mise en emphase (blanc)
	 * @param part03 Troisième partie du texte à afficher
	 */
	public abstract void displayErrorMessageUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03);

	/**
	 * Méthode permettant d'afficher une 2 lignes de couleur rouge dans l'interface (avec 2 mots mis en emphase)
	 * @param part01 Première partie du texte à afficher sur la 1ère ligne
	 * @param placeholder01 Partie mise en emphase (blanc)
	 * @param part02 Deuxième partie du texte à afficher sur la 1ère ligne
	 * @param placeholder02 Partie mise en emphase (blanc)
	 * @param part03 Troisième partie du texte à afficher sur la 1ère ligne
	 * @param part04 Quatrième partie du texte à afficher sur la 2ème ligne
	 */
	public abstract void displayErrorMessageUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03, String part04);
	
	/**
	 * Méthode permettant d'afficher une 2 lignes de couleur rouge dans l'interface (avec 3 mots mis en emphase)
	 * @param part01 Première partie du texte à afficher sur la 1ère ligne
	 * @param placeholder01 Partie mise en emphase (blanc)
	 * @param part02 Deuxième partie du texte à afficher sur la 1ère ligne
	 * @param placeholder02 Partie mise en emphase (blanc)
	 * @param part03 Troisième partie du texte à afficher sur la 1ère ligne
	 * @param placeholder03 Partie mise en emphase (blanc)
	 * @param part04 Quatrième partie du texte à afficher sur la 1ère ligne
	 * @param part05 Cinquième partie du texte à afficher sur la 2ème ligne
	 */
	public abstract void displayErrorMessageUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03, String placeholder03, String part04, String part05);
	
	/**
	 * Méthode permettant d'afficher le nom de la couleur globale dans l'interface (coloré de manière adéquate)
	 */
	public abstract void displayTextBasedOnItsColor(String part01, Color globalColor, String redName,String BlueName,String GreenName,String YellowName);
	
	/**
	 * Méthode surchargée permettant d'afficher le nom de la couleur choisie dans l'interface (coloré de manière adéquate)
	 */
	public abstract void displayTextBasedOnItsColor(String string, Color chosenColor,String colorName);
	
	/* ========================================= COLOR DEPENDANT TEXT ========================================= */

	/**
	 * Méthode permettant d'afficher toutes les couleurs disponibles dans l'interface (avec texte et index fixes)
	 */
	public abstract void displayAvailableColors();

	/**
	 * Méthode permettant d'afficher les choix accessibles à l'utilisateur (Y/N question avec index et couleurs distinctives)
	 * @param question Question à afficher à l'utilisateur
	 * @param choice1 Premier choix (affiché en vert)
	 * @param choice2 Deuxième choix (affiché en rouge)
	 */
	public abstract void displayChoice(String question, String choice1, String choice2);

	/**
	 * Méthode permettant d'afficher les choix accessibles à l'utilisateur (A/B/C question avec index et couleurs distinctives)
	 * @param question Question à afficher à l'utilisateur
	 * @param choice1 Premier choix (affiché en vert)
	 * @param choice2 Deuxième choix (affiché en jaune)
	 * @param choice3 Troisième choix (affiché en rouge)
	 */
	public abstract void displayChoice(String question, String choice1, String choice2, String choice3);

	/* ========================================= UTILS ========================================= */

	/**
	 * Méthode permettant de nettoyer la console (suppression de toutes lignes affichées)
	 */
	public abstract void clearDisplay();
}
