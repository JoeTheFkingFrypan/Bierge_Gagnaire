package utt.fr.rglb.main.java.view.console;

import java.util.Collection;

import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.CardSpecial;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.view.AbstractView;

/**
 * Classe d'affichage permettant d'avoir une interface console
 */
public class ConsoleView extends AbstractView {
	private static final long serialVersionUID = 1L;
	private FancyConsoleDisplay consoleDisplay;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur de ConsoleView
	 * Affiche le titre dans la console, ainsi que les auteurs
	 */
	public ConsoleView() {
		this.consoleDisplay = new FancyConsoleDisplay();
		clearDisplay();
		displayHeader();
		displayAuthors();
	}
	
	/* ========================================= HEADER & TITLE DISPLAYS ========================================= */
	
	/**
	 * Méthode privée permettant d'afficher le titre du programme
	 */
	public void displayHeader() {
		this.consoleDisplay.generateRedEmphasis("  UUUUUUUU     UUUUUUUU  NNNNNNNN        NNNNNNNN       OOOOOOOOO     ");
		this.consoleDisplay.generateRedEmphasis("  U::::::U     U::::::U  N:::::::N       N::::::N     OO:::::::::OO   ");
		this.consoleDisplay.generateRedEmphasis("  U::::::U     U::::::U  N::::::::N      N::::::N   OO:::::::::::::OO "); 
		this.consoleDisplay.generateRedEmphasis("  UU:::::U     U:::::UU  N:::::::::N     N::::::N  O:::::::OOO:::::::O"); 
		this.consoleDisplay.generateRedEmphasis("   U:::::U     U:::::U   N::::::::::N    N::::::N  O::::::O   O::::::O");  
		this.consoleDisplay.generateRedEmphasis("   U:::::D     D:::::U   N:::::::::::N   N::::::N  O:::::O     O:::::O");
		this.consoleDisplay.generateRedEmphasis("   U:::::D     D:::::U   N:::::::N::::N  N::::::N  O:::::O     O:::::O");
		this.consoleDisplay.generateRedEmphasis("   U:::::D     D:::::U   N::::::N N::::N N::::::N  O:::::O     O:::::O");
		this.consoleDisplay.generateRedEmphasis("   U:::::D     D:::::U   N::::::N  N::::N:::::::N  O:::::O     O:::::O");
		this.consoleDisplay.generateRedEmphasis("   U:::::D     D:::::U   N::::::N   N:::::::::::N  O:::::O     O:::::O");  
		this.consoleDisplay.generateRedEmphasis("   U:::::D     D:::::U   N::::::N    N::::::::::N  O:::::O     O:::::O");        
		this.consoleDisplay.generateRedEmphasis("   U::::::U   U::::::U   N::::::N     N:::::::::N  O::::::O   O::::::O");          
		this.consoleDisplay.generateRedEmphasis("   U:::::::UUU:::::::U   N::::::N      N::::::::N  O:::::::OOO:::::::O");
		this.consoleDisplay.generateRedEmphasis("    UU:::::::::::::UU    N::::::N       N:::::::N   OO:::::::::::::OO ");
		this.consoleDisplay.generateRedEmphasis("      UU:::::::::UU      N::::::N        N::::::N     OO:::::::::OO   ");
		this.consoleDisplay.generateRedEmphasis("        UUUUUUUUU        NNNNNNNN         NNNNNNN       OOOOOOOOO     ");
		this.consoleDisplay.generateRedEmphasis("");
	}
	
	/**
	 * Méthode privée permettant d'afficher les auteurs du programme
	 */
	private void displayAuthors() {
		this.consoleDisplay.generateGreenEmphasis("  ___  _ ____ ____ ____ ____      /    ____ ____ ____ _  _ ____ _ ____ ____");
		this.consoleDisplay.generateGreenEmphasis("  |__] | |___ |__/ | __ |___     /     | __ |__| | __ |\\ | |__| | |__/ |___");
		this.consoleDisplay.generateGreenEmphasis("  |__] | |___ |  \\ |__] |___    /      |__] |  | |__] | \\| |  | | |  \\ |___");    
		this.consoleDisplay.generateRedEmphasis("");                                                                
	}
	
	/* ========================================= EMPHASIS ========================================= */

	/**
	 * Méthode permettant d'afficher un titre avec emphase
	 * @param title Titre à afficher
	 */
	@Override
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
	@Override
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
	@Override
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
	@Override
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
	@Override
	public void displayOneCard(Card cardToDisplay) {
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
	@Override
	public void displayOneCard(Card cardToDisplay, int index) {
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
	
	@Override
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

	
	@Override
	public void displayOneLineOfGreenText(String string) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.displaySuccessText(string);
		this.consoleDisplay.displayBlankLine();
	}

	@Override
	public void displayGreenEmphasisUsingPlaceholders(String part01, String placeholder01, String part02) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendGreenText(part01);
		this.consoleDisplay.appendBoldText(placeholder01);
		this.consoleDisplay.appendGreenText(part02);
		this.consoleDisplay.displayBlankLine();
	}

	@Override
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
	
	@Override
	public void displayOneLineOfJokerText(String string) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendJokerText(string);
		this.consoleDisplay.displayBlankLine();
	}
	
	@Override
	public void displayTwoLinesOfJokerText(String string, String string2) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendJokerText(string);
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendJokerText(string2);
		this.consoleDisplay.displayBlankLine();
	}
	
	@Override
	public void displayJokerEmphasisUsingPlaceholders(String part01, String placeholder01, String part02) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendJokerText(part01);
		this.consoleDisplay.appendBoldText(placeholder01);
		this.consoleDisplay.appendJokerText(part02);
		this.consoleDisplay.displayBlankLine();
	}

	@Override
	public void displayJokerEmphasisUsingPlaceholders(String part01, Card card) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendJokerText(part01);
		displayOneCard(card);
		this.consoleDisplay.displayBlankLine();
	}

	@Override
	public void displayJokerEmphasisUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendJokerText(part01);
		this.consoleDisplay.appendBoldText(placeholder01);
		this.consoleDisplay.appendJokerText(part02);
		this.consoleDisplay.appendBoldText(placeholder02);
		this.consoleDisplay.appendJokerText(part03);
		this.consoleDisplay.displayBlankLine();
	}

	@Override
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

	@Override
	public void displayOneLineOfBoldText(String string) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.displayBoldText(string);
		this.consoleDisplay.displayBlankLine();
	}

	@Override
	public void StartOneLineOfBoldText(String part01, String placeholder01, String part02) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendBoldText(part01);
		this.consoleDisplay.appendJokerText(placeholder01);
		this.consoleDisplay.appendBoldText(part02);
	}
	
	@Override
	public void AppendOneLineOfBoldText(String string) {
		this.consoleDisplay.appendBoldText(string);
	}
	
	/* ========================================= RED TEXT ========================================= */

	@Override
	public void displayOneLineOfRedText(String string) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.displayErrorText(string);
		this.consoleDisplay.displayBlankLine();
	}

	@Override
	public void displayErrorMessage(String string, String string2) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.displayErrorText(string);
		this.consoleDisplay.displayErrorText(string2);
		this.consoleDisplay.displayBlankLine();
	}

	@Override
	public void displayErrorMessageUsingPlaceholders(String part01, String placeholder01, String part02) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendRedText(part01); 
		this.consoleDisplay.appendBoldText(placeholder01);
		this.consoleDisplay.appendRedText(part02);
		this.consoleDisplay.displayBlankLine();
	}

	@Override
	public void displayErrorMessageUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendRedText(part01); 
		this.consoleDisplay.appendBoldText(placeholder01);
		this.consoleDisplay.appendRedText(part02);
		this.consoleDisplay.appendBoldText(placeholder02);
		this.consoleDisplay.displayBlankLine();
	}
	
	@Override
	public void displayErrorMessageUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendRedText(part01);
		this.consoleDisplay.appendBoldText(placeholder01);
		this.consoleDisplay.appendRedText(part02);
		this.consoleDisplay.appendBoldText(placeholder02);
		this.consoleDisplay.appendRedText(part03);
		this.consoleDisplay.displayBlankLine();
	}

	@Override
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

	@Override
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

	@Override
	public void displayTextBasedOnItsColor(String part01, Color globalColor, String redName,String BlueName,String GreenName,String YellowName) {
		this.consoleDisplay.appendBoldText(part01);
		if(globalColor.equals(Color.RED)) {
			this.consoleDisplay.appendRedText(redName);
		} else if(globalColor.equals(Color.BLUE)) {
			this.consoleDisplay.appendBlueText(BlueName);
		} else if(globalColor.equals(Color.GREEN)) {
			this.consoleDisplay.appendGreenText(GreenName);
		} else if(globalColor.equals(Color.YELLOW)) {
			this.consoleDisplay.appendYellowText(YellowName);
		}
		this.consoleDisplay.displayBlankLine();
	}
	
	@Override
	public void displayTextBasedOnItsColor(String part01, Color chosenColor, String colorName) {
		this.consoleDisplay.appendBoldText(part01);
		if(chosenColor.equals(Color.RED)) {
			this.consoleDisplay.appendRedText(colorName);
		} else if(chosenColor.equals(Color.BLUE)) {
			this.consoleDisplay.appendBlueText(colorName);
		} else if(chosenColor.equals(Color.GREEN)) {
			this.consoleDisplay.appendGreenText(colorName);
		} else if(chosenColor.equals(Color.YELLOW)) {
			this.consoleDisplay.appendYellowText(colorName);
		}
		this.consoleDisplay.displayBlankLine();
	}

	/**
	 * Méthode permettant d'afficher les choix accessibles à l'utilisateur (Y/N question avec index et couleurs distinctives)
	 * @param question Question à afficher à l'utilisateur
	 * @param choice1 Premier choix (affiché en vert)
	 * @param choice2 Deuxième choix (affiché en rouge)
	 */
	@Override
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
	@Override
	public void displayChoice(String question, String choice1, String choice2, String choice3) {
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendJokerText(question);
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.appendGreenText(choice1);
		this.consoleDisplay.appendYellowText(choice2);
		this.consoleDisplay.appendRedText(choice3);
		this.consoleDisplay.displayBlankLine();

	}

	/* ========================================= COLOR DEPENDANT TEXT ========================================= */

	/**
	 * Méthode permettant d'afficher toutes les couleurs disponibles dans l'interface (avec texte et index fixes)
	 */
	@Override
	public void displayAvailableColors() {
		this.consoleDisplay.appendBoldText("Available Colors are : ");
		this.consoleDisplay.appendRedText("0:Red ");
		this.consoleDisplay.appendBlueText("1:Blue ");
		this.consoleDisplay.appendGreenText("2:Green ");
		this.consoleDisplay.appendYellowText("3:Yellow ");
		this.consoleDisplay.displayBlankLine();
		this.consoleDisplay.displayBoldText("Please pick one using number");
	}
	
	/* ========================================= UTILS ========================================= */

	/**
	 * Méthode permettant de nettoyer la console (suppression de toutes lignes affichées)
	 */
	@Override
	public void clearDisplay() {
		this.consoleDisplay.clearDisplay();
	}
}
