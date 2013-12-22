package utt.fr.rglb.main.java.console.view;

import com.google.common.base.Preconditions;

import java.io.Serializable;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.CardSpecial;
import utt.fr.rglb.main.java.cards.model.basics.Color;

/**
 * Classe définissant les méthodes d'affichage
 */
public abstract class View implements Serializable {
	private static final long serialVersionUID = 1L;
	protected FancyConsoleDisplay consoleDisplay;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur de vue
	 */
	public View() {
		this.consoleDisplay = new FancyConsoleDisplay();
	}

	/* ========================================= EMPHASIS ========================================= */

	/**
	 * Méthode permettant d'afficher un titre avec emphase
	 * @param title Titre à afficher
	 */
	public void displayTitle(String title) {
		Preconditions.checkNotNull(title,"[ERROR] Impossible to display text : provided one is null");
		int length = title.length();
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.displaySeparationBar(length);
		this.consoleDisplay.displayBoldText(title);
		this.consoleDisplay.displaySeparationBar(length);
		this.consoleDisplay.displayBlankLine();
	}

	/**
	 * Méthode permettant d'utiliser du texte comme barre de séparation (affichage en couleurs négatives)
	 * @param text Texte à afficher
	 */
	public void displaySeparationText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.displaySeparationText(text);
	}

	/* ========================================= CARD DISPLAY ========================================= */

	/**
	 * Méthode permettant d'afficher une carte dans l'interface
	 * @param string Message à afficher avant la carte
	 * @param card Carte à afficher
	 */
	public void displayCard(String string, Card card) {
		Preconditions.checkNotNull(card,"[ERROR] Impossible to display card : provided one is null");
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendBoldText(string);
		displayOneCard(card);
		this.consoleDisplay.displayBlankLine();
	}

	/**
	 * Méthode permettant d'afficher une collection de cartes complète
	 * @param string Message à afficher avant la collection
	 * @param cards Collection de cartes à afficher
	 */
	public void displayCardCollection(String string, Collection<Card> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Impossible to display card collection : provided one is null");
		int index = 0;
		this.consoleDisplay.appendBoldText("You now have : ");
		for(Card currentCard : cards) {
			displayOneCard(currentCard,index++);
		}
		this.consoleDisplay.displayBlankLine();
	}

	/**
	 * Méthode privée permettant d'afficher une carte avec gestion de sa couleur et de son type (numérotée, spéciale)
	 * @param cardToDisplay Carte à afficher
	 */
	private void displayOneCard(Card cardToDisplay) {
		Preconditions.checkNotNull(cardToDisplay,"[ERROR] Impossible to display card : provided one is null");
		if(cardToDisplay.isSpecial()) {
			CardSpecial explictSpecialCardToDisplay = (CardSpecial)cardToDisplay;
			displaySpecialCard(explictSpecialCardToDisplay);
		} else {
			displayNumberedCard(cardToDisplay);
		}
	}

	/**
	 * Méthode privée permettant d'afficher une unique carte avec gestion de sa couleur et de son type (numérotée, spéciale) en accolant son index
	 * @param cardToDisplay Carte à afficher
	 * @param index Index de la carte 
	 */
	private void displayOneCard(Card cardToDisplay, int index) {
		Preconditions.checkNotNull(cardToDisplay,"[ERROR] Impossible to display card : provided one is null");
		Preconditions.checkArgument(index >= 0,"[ERROR] Impossible to display card : provided index is invalid");
		if(cardToDisplay.isSpecial()) {
			CardSpecial explictSpecialCardToDisplay = (CardSpecial)cardToDisplay;
			displaySpecialCard(explictSpecialCardToDisplay,index);
		} else {
			displayNumberedCard(cardToDisplay,index);
		}
	}

	/* ========================================= SPECIAL CARD DISPLAY ========================================= */

	/**
	 * Méthode privée permettant d'afficher une carte spéciale dans la console (avec gestion de la couleur et de son effet)
	 * @param cardToDisplay Carte à afficher
	 */
	private void displaySpecialCard(CardSpecial cardToDisplay) {
		Preconditions.checkNotNull(cardToDisplay,"[ERROR] Impossible to display card : provided one is null");
		String effect = cardToDisplay.getEffect();
		if(cardToDisplay.isBlue()) {
			this.consoleDisplay.appendBlueText("[" + effect + "] ");
		} else if(cardToDisplay.isRed()) {
			this.consoleDisplay.appendRedText("[" + effect + "] ");
		} else if(cardToDisplay.isGreen()) {
			this.consoleDisplay.appendGreenText("[" + effect + "] ");
		} else if(cardToDisplay.isYellow()) {
			this.consoleDisplay.appendYellowText("[" + effect + "] ");
		} else { 	//if(colorFromCard.equals(Couleur.JOKER)
			this.consoleDisplay.appendJokerText("[" + effect + "] ");
		}
	}

	/**
	 * Méthode privée permettant d'afficher une carte spéciale dans la console (avec gestion de la couleur et de son effet) en accolant son index
	 * @param cardToDisplay Carte à afficher
	 * @param index Index de la carte
	 */
	private void displaySpecialCard(CardSpecial cardToDisplay, int index) {
		Preconditions.checkNotNull(cardToDisplay,"[ERROR] Impossible to display card : provided one is null");
		Preconditions.checkArgument(index >= 0,"[ERROR] Impossible to display card : provided index is invalid");
		String effect = cardToDisplay.getEffect();
		if(cardToDisplay.isBlue()) {
			this.consoleDisplay.appendBlueText(index + ":[" + effect + "] ");
		} else if(cardToDisplay.isRed()) {
			this.consoleDisplay.appendRedText(index + ":[" + effect + "] ");
		} else if(cardToDisplay.isGreen()) {
			this.consoleDisplay.appendGreenText(index + ":[" + effect + "] ");
		} else if(cardToDisplay.isYellow()) {
			this.consoleDisplay.appendYellowText(index + ":[" + effect + "] ");
		} else { 	//if(colorFromCard.equals(Couleur.JOKER)
			this.consoleDisplay.appendJokerText(index + ":[" + effect + "] ");
		}
	}

	/* ========================================= NUMBERED CARD DISPLAY ========================================= */

	/**
	 * Méthode privée permettant d'afficher une carte numérotée (avec gestion de sa couleur) 
	 * @param cardToDisplay Carte à afficher
	 */
	private void displayNumberedCard(Card cardToDisplay) {
		Preconditions.checkNotNull(cardToDisplay,"[ERROR] Impossible to display card : provided one is null");
		int value = cardToDisplay.getValue();
		if(cardToDisplay.isBlue()) {
			this.consoleDisplay.appendBlueText("[" + value + "] ");
		} else if(cardToDisplay.isRed()) {
			this.consoleDisplay.appendRedText("[" + value + "] ");
		} else if(cardToDisplay.isGreen()) {
			this.consoleDisplay.appendGreenText("[" + value + "] ");
		} else {	//if(colorFromCard.equals(Couleur.JAUNE)
			this.consoleDisplay.appendYellowText("[" + value + "] ");
		}
	}

	/**
	 * Méthode privée permettant d'afficher une carte numérotée (avec gestion de sa couleur) 
	 * @param cardToDisplay Carte à afficher
	 * @param index Index de la carte
	 */
	private void displayNumberedCard(Card cardToDisplay, int index) {
		Preconditions.checkNotNull(cardToDisplay,"[ERROR] Impossible to display card : provided one is null");
		Preconditions.checkArgument(index >= 0,"[ERROR] Impossible to display card : provided index is invalid");
		int value = cardToDisplay.getValue();
		if(cardToDisplay.isBlue()) {
			this.consoleDisplay.appendBlueText(index + ":[" + value + "] ");
		} else if(cardToDisplay.isRed()) {
			this.consoleDisplay.appendRedText(index + ":[" + value + "] ");
		} else if(cardToDisplay.isGreen()) {
			this.consoleDisplay.appendGreenText(index + ":[" + value + "] ");
		} else {	//if(colorFromCard.equals(Couleur.JAUNE)
			this.consoleDisplay.appendYellowText(index + ":[" + value + "] ");
		}
	}
	
	/* ========================================= YELLOW TEXT ========================================= */

	public void displayOneLineOfYellowText(String part01, String placeholder01, String part02, String placeholder02, String part03) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendYellowText(part01); 
		this.consoleDisplay.appendBoldText(placeholder01);
		this.consoleDisplay.appendYellowText(part02);
		this.consoleDisplay.appendBoldText(placeholder02);
		this.consoleDisplay.appendYellowText(part03);
		this.consoleDisplay.displayBlankLine();
	}
	
	/* ========================================= GREEN TEXT ========================================= */

	/**
	 * Méthode permettant d'afficher une unique ligne de couleur verte dans l'interface
	 * @param string Message à afficher
	 */
	public void displayOneLineOfGreenText(String string) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.displaySuccessText(string);
		this.consoleDisplay.displayBlankLine();
	}

	/**
	 * Méthode permettant d'afficher une unique ligne de couleur verte avec un élément mis en emphase (placeholder)
	 * @param part01 Première partie du texte à afficher
	 * @param placeholder01 Partie mise en emphase (blanc)
	 * @param part02 Deuxième partie du texte à afficher
	 */
	public void displayGreenEmphasisUsingPlaceholders(String part01, String placeholder01, String part02) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendGreenText(part01);
		this.consoleDisplay.appendBoldText(placeholder01);
		this.consoleDisplay.appendGreenText(part02);
		this.consoleDisplay.displayBlankLine();
	}

	public void displayGreenEmphasisUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendGreenText(part01);
		this.consoleDisplay.appendBoldText(placeholder01);
		this.consoleDisplay.appendGreenText(part02);
		this.consoleDisplay.appendBoldText(placeholder02);
		this.consoleDisplay.appendGreenText(part03);
		this.consoleDisplay.displayBlankLine();
	}
	
	/* ========================================= JOKER TEXT ========================================= */
	
	/**
	 * Méthode permettant d'afficher une unique ligne de couleur magenta dans l'interface
	 * @param string Message à afficher
	 */
	public void displayOneLineOfJokerText(String string) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendJokerText(string);
		this.consoleDisplay.displayBlankLine();
	}

	/**
	 * Méthode permettant d'afficher 2 lignes de couleur magenta dans la console
	 * @param string Message à afficher sur la 1ère ligne
	 * @param string2 Message à afficher sur la 2ème ligne
	 */
	public void displayTwoLinesOfJokerText(String string, String string2) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendJokerText(string);
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendJokerText(string2);
		this.consoleDisplay.displayBlankLine();
	}
	
	/**
	 * Méthode permettant d'afficher une unique ligne de couleur magenta dans l'interface (avec 1 mot mis en emphase)
	 * @param part01 Première partie du texte à afficher
	 * @param placeholder01 Partie mise en emphase (blanc)
	 * @param part02 Deuxième partie du texte à afficher
	 */
	public void displayJokerEmphasisUsingPlaceholders(String part01, String placeholder01, String part02) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendJokerText(part01);
		this.consoleDisplay.appendBoldText(placeholder01);
		this.consoleDisplay.appendJokerText(part02);
		this.consoleDisplay.displayBlankLine();
	}

	/**
	 * Méthode permettant d'afficher une unique ligne de couleur magenta dans l'interface (avec 1 mot mis en emphase et affichage d'une carte)
	 * Utilisée lors du jeu d'une carte par l'IA --permet d'avoir un retour visuel sur ses décisions
	 * @param part01 Première partie du texte à afficher
	 * @param card Carte à afficher
	 */
	public void displayJokerEmphasisUsingPlaceholders(String part01, Card card) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendJokerText(part01);
		displayOneCard(card);
		this.consoleDisplay.displayBlankLine();
	}

	/**
	 * Méthode permettant d'afficher une unique ligne de couleur magenta dans l'interface (avec 2 mots mis en emphase)
	 * @param part01 Première partie du texte à afficher
	 * @param placeholder01 Partie mise en emphase (blanc)
	 * @param part02 Deuxième partie du texte à afficher
	 * @param placeholder02 Partie mise en emphase (blanc)
	 * @param part03 Troisième partie du texte à afficher
	 */
	public void displayJokerEmphasisUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendJokerText(part01);
		this.consoleDisplay.appendBoldText(placeholder01);
		this.consoleDisplay.appendJokerText(part02);
		this.consoleDisplay.appendBoldText(placeholder02);
		this.consoleDisplay.appendJokerText(part03);
		this.consoleDisplay.displayBlankLine();
	}

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
	public void displayJokerEmphasisUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03, String placeholder03, String part04) {
		this.consoleDisplay.appendJokerText(part01);
		this.consoleDisplay.appendBoldText(placeholder01);
		this.consoleDisplay.appendJokerText(part02);
		this.consoleDisplay.appendBoldText(placeholder02);
		this.consoleDisplay.appendJokerText(part03);
		this.consoleDisplay.appendBoldText(placeholder03);
		this.consoleDisplay.appendJokerText(part04);
		this.consoleDisplay.displayBlankLine();
	}

	/* ========================================= WHITE TEXT ========================================= */

	/**
	 * Méthode permettant d'afficher une unique ligne de couleur blanche dans l'interface
	 * @param string Message à afficher
	 */
	public void displayOneLineOfBoldText(String string) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.displayBoldText(string);
		this.consoleDisplay.displayBlankLine();
	}

	/**
	 * Méthode permettant d'afficher du texte de couleur blanche (avec 1 mot emphasé) sans aller à la ligne
	 * @param part01 Première partie du texte à afficher
	 * @param placeholder01 Partie mise en emphase (joker/magenta)
	 * @param part02 Deuxième partie du texte à afficher
	 */
	public void StartOneLineOfBoldText(String part01, String placeholder01, String part02) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendBoldText(part01);
		this.consoleDisplay.appendJokerText(placeholder01);
		this.consoleDisplay.appendBoldText(part02);
	}
	
	/**
	 * Méthode permettant d'afficher du texte de couleur blanche sur une ligne déjà existante
	 * @param string Message à afficher
	 */
	public void AppendOneLineOfBoldText(String string) {
		this.consoleDisplay.appendBoldText(string);
	}
	
	/* ========================================= RED TEXT ========================================= */

	/**
	 * Méthode permettant d'afficher une unique ligne de couleur rouge dans l'interface
	 * @param string Message à afficher
	 */
	public void displayOneLineOfRedText(String string) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.displayErrorText(string);
		this.consoleDisplay.displayBlankLine();
	}

	/**
	 * Méthode permettant d'afficher 2 lignes de couleur rouge dans la console
	 * @param string Message à afficher sur la 1ère ligne
	 * @param string2 Message à afficher sur la 2ème ligne
	 */
	public void displayErrorMessage(String string, String string2) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.displayErrorText(string);
		this.consoleDisplay.displayErrorText(string2);
		this.consoleDisplay.displayBlankLine();
	}

	/**
	 * Méthode permettant d'afficher une unique ligne de couleur rouge dans l'interface (avec 1 mot mis en emphase)
	 * @param part01 Première partie du texte à afficher
	 * @param placeholder01 Partie mise en emphase (blanc)
	 * @param part02 Deuxième partie du texte à afficher
	 */
	public void displayErrorMessageUsingPlaceholders(String part01, String placeholder01, String part02) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendRedText(part01); 
		this.consoleDisplay.appendBoldText(placeholder01);
		this.consoleDisplay.appendRedText(part02);
		this.consoleDisplay.displayBlankLine();
	}

	/**
	 * Méthode permettant d'afficher une unique ligne de couleur rouge dans l'interface (avec 2 mots mis en emphase)
	 * @param part01 Première partie du texte à afficher
	 * @param placeholder01 Partie mise en emphase (blanc)
	 * @param part02 Deuxième partie du texte à afficher
	 * @param placeholder02 Partie mise en emphase (blanc)
	 */
	public void displayErrorMessageUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendRedText(part01); 
		this.consoleDisplay.appendBoldText(placeholder01);
		this.consoleDisplay.appendRedText(part02);
		this.consoleDisplay.appendBoldText(placeholder02);
		this.consoleDisplay.displayBlankLine();
	}
	
	/**
	 * Méthode permettant d'afficher une unique ligne de couleur rouge dans l'interface (avec 2 mots mis en emphase)
	 * @param part01 Première partie du texte à afficher
	 * @param placeholder01 Partie mise en emphase (blanc)
	 * @param part02 Deuxième partie du texte à afficher
	 * @param placeholder02 Partie mise en emphase (blanc)
	 * @param part03 Troisième partie du texte à afficher
	 */
	public void displayErrorMessageUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendRedText(part01);
		this.consoleDisplay.appendBoldText(placeholder01);
		this.consoleDisplay.appendRedText(part02);
		this.consoleDisplay.appendBoldText(placeholder02);
		this.consoleDisplay.appendRedText(part03);
		this.consoleDisplay.displayBlankLine();
	}

	/**
	 * Méthode permettant d'afficher une 2 lignes de couleur rouge dans l'interface (avec 2 mots mis en emphase)
	 * @param part01 Première partie du texte à afficher sur la 1ère ligne
	 * @param placeholder01 Partie mise en emphase (blanc)
	 * @param part02 Deuxième partie du texte à afficher sur la 1ère ligne
	 * @param placeholder02 Partie mise en emphase (blanc)
	 * @param part03 Troisième partie du texte à afficher sur la 1ère ligne
	 * @param part04 Quatrième partie du texte à afficher sur la 2ème ligne
	 */
	public void displayErrorMessageUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03, String part04) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendRedText(part01); 
		this.consoleDisplay.appendBoldText(placeholder01);
		this.consoleDisplay.appendRedText(part02);
		this.consoleDisplay.appendBoldText(placeholder02);
		this.consoleDisplay.appendRedText(part03);
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.displayErrorText(part04);
	}

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
	public void displayErrorMessageUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03, String placeholder03, String part04, String part05) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendRedText(part01); 
		this.consoleDisplay.appendBoldText(placeholder01);
		this.consoleDisplay.appendRedText(part02);
		this.consoleDisplay.appendBoldText(placeholder02);
		this.consoleDisplay.appendRedText(part03);
		this.consoleDisplay.appendBoldText(placeholder03); 
		this.consoleDisplay.appendRedText(part04);
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.displayErrorText(part05);
	}

	/* ========================================= COLOR DEPENDANT TEXT ========================================= */

	/**
	 * Méthode permettant d'afficher le nom de la couleur globale dans l'interface (coloré de manière adéquate)
	 */
	public void displayTextBasedOnItsColor(String part01, Color globalColor, String redText, String blueText, String greenText, String yellowText) {
		this.consoleDisplay.appendBoldText(part01);
		if(globalColor.equals(Color.RED)) {
			this.consoleDisplay.appendRedText(redText);
		} else if(globalColor.equals(Color.BLUE)) {
			this.consoleDisplay.appendBlueText(blueText);
		} else if(globalColor.equals(Color.GREEN)) {
			this.consoleDisplay.appendGreenText(greenText);
		} else if(globalColor.equals(Color.YELLOW)) {
			this.consoleDisplay.appendYellowText(yellowText);
		}
		this.consoleDisplay.displayBlankLine();
	}

	/**
	 * Méthode permettant d'afficher toutes les couleurs disponibles dans l'interface (avec texte et index fixes)
	 */
	public void displayAvailableColors() {
		this.consoleDisplay.appendBoldText("Available Colors are : ");
		this.consoleDisplay.appendRedText("0:Red ");
		this.consoleDisplay.appendBlueText("1:Blue ");
		this.consoleDisplay.appendGreenText("2:Green ");
		this.consoleDisplay.appendYellowText("3:Yellow ");
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.displayBoldText("Please pick one using number");
	}

	/**
	 * Méthode permettant d'afficher les choix accessibles à l'utilisateur (Y/N question avec index et couleurs distinctives)
	 * @param question Question à afficher à l'utilisateur
	 * @param choice1 Premier choix (affiché en vert)
	 * @param choice2 Deuxième choix (affiché en rouge)
	 */
	public void displayChoice(String question, String choice1, String choice2) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendJokerText(question);
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendGreenText(choice1);
		this.consoleDisplay.appendRedText(choice2);
		this.consoleDisplay.displayBlankLine();
	}

	/**
	 * Méthode permettant d'afficher les choix accessibles à l'utilisateur (A/B/C question avec index et couleurs distinctives)
	 * @param question Question à afficher à l'utilisateur
	 * @param choice1 Premier choix (affiché en vert)
	 * @param choice2 Deuxième choix (affiché en jaune)
	 * @param choice3 Troisième choix (affiché en rouge)
	 */
	public void displayChoice(String question, String choice1, String choice2, String choice3) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendJokerText(question);
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendGreenText(choice1);
		this.consoleDisplay.appendYellowText(choice2);
		this.consoleDisplay.appendRedText(choice3);
		this.consoleDisplay.displayBlankLine();

	}

	/* ========================================= UTILS ========================================= */

	/**
	 * Méthode permettant de nettoyer la console (suppression de toutes lignes affichées)
	 */
	public void clearDisplay() {
		this.consoleDisplay.clearDisplay();
	}
}
