package main.java.console.view;

import main.java.console.model.ConsoleCodesAnsi;

import org.fusesource.jansi.AnsiConsole;

public class FancyConsoleDisplay {

	/* ========================================= TYPES OF DISPLAY ========================================= */
	
	/**
	 * M�thode permettant d'afficher du texte dans la console (avec retour � la ligne)
	 * @param text Texte � afficher
	 */
	private void printText(String text) {
		AnsiConsole.out.println(text);
	}

	/**
	 * M�thode permettant d'afficher du texte dans la console (sans retour � la ligne)
	 * @param text Texte � afficher
	 */
	private void appendText(String text) {
		AnsiConsole.out.print(text);
	}

	/* ========================================= HEADER & SENTENSES ========================================= */

	/**
	 * M�thode permettant d'afficher du texte dans la console en gras (avec retour � la ligne)
	 * @param text Texte � afficher
	 */
	public void displayBoldText(String text) {
		text = turnBold(text);
		printText(text);
	}
	
	/**
	 * M�thode permettant d'afficher du texte dans la console en gras (sans retour � la ligne)
	 * @param text Texte � afficher
	 */
	public void appendBoldText(String text) {
		text = turnBold(text);
		appendText(text);
	}

	/**
	 * M�thode permettant d'afficher un message d'erreur dans la console -en gras et en rouge (avec retour � la ligne)
	 * @param text Texte � afficher
	 */
	public void displayErrorText(String text) {
		generateRedEmphasis(text);
	}

	/**
	 * M�thode permettant d'afficher un message de succ�s dans la console -en gras et en vert (avec retour � la ligne)
	 * @param text Texte � afficher
	 */
	public void displaySuccessText(String text) {
		generateGreenEmphasis(text);
	}
	
	/**
	 * M�thode permettant d'afficher un message de s�paration dans la console -en blanc sur fond bleu (avec retour � la ligne)
	 * @param text Texte � afficher
	 */
	public void displaySeparationText(String text) {
		generateWhiteOnBlueEmphasis(text);
	}

	/* ========================================= HEADER UTILS ========================================= */
	
	/**
	 * M�thode permettant d'afficher un message d'erreur dans la console -en gras et en rouge (avec retour � la ligne)
	 * @param text Texte � afficher
	 */
	public void generateRedEmphasis(String text) {
		text = turnRed(text);
		text = turnBold(text);
		printText(text);
	}

	/**
	 * M�thode permettant d'afficher un message de succ�s dans la console -en gras et en vert (avec retour � la ligne)
	 * @param text Texte � afficher
	 */
	public void generateGreenEmphasis(String text) {
		text = turnGreen(text);
		text = turnBold(text);
		printText(text);
	}
	
	/**
	 * M�thode permettant d'afficher un message de s�paration dans la console -en blanc sur fond bleu (avec retour � la ligne)
	 * @param text Texte � afficher
	 */
	public void generateWhiteOnBlueEmphasis(String text) {
		text = turnBold(text);
		text = whiteOnBlue(text);
		printText(text);
	}

	/* ========================================= APPEND TEXT ========================================= */

	
	
	/**
	 * M�thode permettant d'ajouter du texte de couleur bleue dans la console (sans aller � la ligne)
	 * @param text Texte � afficher
	 */
	public void appendBlueText(String text) {
		text = turnBlue(text);
		appendText(text);
	}

	/**
	 * M�thode permettant d'ajouter du texte de couleur rouge dans la console (sans aller � la ligne)
	 * @param text Texte � afficher
	 */
	public void appendRedText(String text) {
		text = turnRed(text);
		appendText(text);
	}

	/**
	 * M�thode permettant d'ajouter du texte de couleur verte dans la console (sans aller � la ligne)
	 * @param text Texte � afficher
	 */
	public void appendGreenText(String text) {
		text = turnGreen(text);
		appendText(text);
	}

	/**
	 * M�thode permettant d'ajouter du texte de couleur jaune dans la console (sans aller � la ligne)
	 * @param text Texte � afficher
	 */
	public void appendYellowText(String text) {
		text = turnYellow(text);
		appendText(text);
	}

	/**
	 * M�thode permettant d'ajouter du texte de couleur magenta [repr�sentant le joker] dans la console (sans aller � la ligne)
	 * @param text Texte � afficher
	 */
	public void appendJokerText(String text) {
		text = turnMagenta(text);
		appendText(text);
	}

	/* ========================================= UTILS ========================================= */
	
	/**
	 * M�thode priv�e rajoutant les codes ANSI necessaires pour rendre la chaine de caract�res en rouge
	 * @param textToTurnRed Texte � transformer
	 * @return String contenant la chaine de caract�re fournie entour�e des codes ANSI appropri�s
	 */
	private String turnRed(String textToTurnRed) {
		return ConsoleCodesAnsi.COLOR_RED + textToTurnRed + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * M�thode priv�e rajoutant les codes ANSI necessaires pour rendre la chaine de caract�res en bleu
	 * @param textToTurnBlue Texte � transformer
	 * @return String contenant la chaine de caract�re fournie entour�e des codes ANSI appropri�s
	 */
	private String turnBlue(String textToTurnBlue) {
		return ConsoleCodesAnsi.COLOR_CYAN + textToTurnBlue + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * M�thode priv�e rajoutant les codes ANSI necessaires pour rendre la chaine de caract�res en vert
	 * @param textToTurnGreen Texte � transformer
	 * @return String contenant la chaine de caract�re fournie entour�e des codes ANSI appropri�s
	 */
	private String turnGreen(String textToTurnGreen) {
		return ConsoleCodesAnsi.COLOR_GREEN + textToTurnGreen + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * M�thode priv�e rajoutant les codes ANSI necessaires pour rendre la chaine de caract�res en jaune
	 * @param textToTurnYellow Texte � transformer
	 * @return String contenant la chaine de caract�re fournie entour�e des codes ANSI appropri�s
	 */
	private String turnYellow(String textToTurnYellow) {
		return ConsoleCodesAnsi.COLOR_YELLOW + textToTurnYellow + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * M�thode priv�e rajoutant les codes ANSI necessaires pour rendre la chaine de caract�res en magenta
	 * @param textToTurnMagenta Texte � transformer
	 * @return String contenant la chaine de caract�re fournie entour�e des codes ANSI appropri�s
	 */
	private String turnMagenta(String textToTurnMagenta) {
		return ConsoleCodesAnsi.COLOR_MAGENTA + textToTurnMagenta + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * M�thode priv�e rajoutant les codes ANSI necessaires pour rendre la chaine de caract�res en gras
	 * @param textToTurnBold Texte � transformer
	 * @return String contenant la chaine de caract�re fournie entour�e des codes ANSI appropri�s
	 */
	private String turnBold(String textToTurnBold) {
		return ConsoleCodesAnsi.ANSI_BOLD + textToTurnBold + ConsoleCodesAnsi.ANSI_NORMAL;
	}

	/**
	 * M�thode priv�e rajoutant les codes ANSI necessaires pour rendre la chaine de caract�res en blanc sur fond bleu
	 * @param whiteOnBlue Texte � transformer
	 * @return String contenant la chaine de caract�re fournie entour�e des codes ANSI appropri�s
	 */
	private String whiteOnBlue(String textToInvert) {
		return ConsoleCodesAnsi.ANSI_WHITEONBLUE + textToInvert + ConsoleCodesAnsi.ANSI_NORMAL;
	}

	/* ========================================= BASIC DISPLAYS ========================================= */

	/**
	 * M�thode permettant de vider la console
	 */
	public void clearDisplay() {
		AnsiConsole.systemInstall();
		printText(ConsoleCodesAnsi.ANSI_CLS);
	}

	/**
	 * M�thode permettant d'afficher une ligne vide dans la console
	 */
	public void displayBlankLine() {
		printText("");
	}

	/**
	 * M�thode permettant de cr�er un titre avec barres de s�paration de longueur ad�quate
	 * @param length Nombre de caract�res composant la chaine
	 */
	public void displaySeparationBar(int length) {
		String bar = "";
		for(int i=0; i<length; i++) {
			bar += "-";
		}
		printText(bar);
	}
}
