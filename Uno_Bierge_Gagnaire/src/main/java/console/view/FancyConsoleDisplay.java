package main.java.console.view;

import main.java.console.model.ConsoleCodesAnsi;

import org.fusesource.jansi.AnsiConsole;

public class FancyConsoleDisplay {

	/* ========================================= TYPES OF DISPLAY ========================================= */
	
	/**
	 * Méthode permettant d'afficher du texte dans la console (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	private void printText(String text) {
		AnsiConsole.out.println(text);
	}

	/**
	 * Méthode permettant d'afficher du texte dans la console (sans retour à la ligne)
	 * @param text Texte à afficher
	 */
	private void appendText(String text) {
		AnsiConsole.out.print(text);
	}

	/* ========================================= HEADER & SENTENSES ========================================= */

	/**
	 * Méthode permettant d'afficher du texte dans la console en gras (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void displayBoldText(String text) {
		text = turnBold(text);
		printText(text);
	}
	
	/**
	 * Méthode permettant d'afficher du texte dans la console en gras (sans retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void appendBoldText(String text) {
		text = turnBold(text);
		appendText(text);
	}

	/**
	 * Méthode permettant d'afficher un message d'erreur dans la console -en gras et en rouge (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void displayErrorText(String text) {
		generateRedEmphasis(text);
	}

	/**
	 * Méthode permettant d'afficher un message de succès dans la console -en gras et en vert (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void displaySuccessText(String text) {
		generateGreenEmphasis(text);
	}
	
	/**
	 * Méthode permettant d'afficher un message de séparation dans la console -en blanc sur fond bleu (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void displaySeparationText(String text) {
		generateWhiteOnBlueEmphasis(text);
	}

	/* ========================================= HEADER UTILS ========================================= */
	
	/**
	 * Méthode permettant d'afficher un message d'erreur dans la console -en gras et en rouge (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void generateRedEmphasis(String text) {
		text = turnRed(text);
		text = turnBold(text);
		printText(text);
	}

	/**
	 * Méthode permettant d'afficher un message de succès dans la console -en gras et en vert (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void generateGreenEmphasis(String text) {
		text = turnGreen(text);
		text = turnBold(text);
		printText(text);
	}
	
	/**
	 * Méthode permettant d'afficher un message de séparation dans la console -en blanc sur fond bleu (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void generateWhiteOnBlueEmphasis(String text) {
		text = turnBold(text);
		text = whiteOnBlue(text);
		printText(text);
	}

	/* ========================================= APPEND TEXT ========================================= */

	
	
	/**
	 * Méthode permettant d'ajouter du texte de couleur bleue dans la console (sans aller à la ligne)
	 * @param text Texte à afficher
	 */
	public void appendBlueText(String text) {
		text = turnBlue(text);
		appendText(text);
	}

	/**
	 * Méthode permettant d'ajouter du texte de couleur rouge dans la console (sans aller à la ligne)
	 * @param text Texte à afficher
	 */
	public void appendRedText(String text) {
		text = turnRed(text);
		appendText(text);
	}

	/**
	 * Méthode permettant d'ajouter du texte de couleur verte dans la console (sans aller à la ligne)
	 * @param text Texte à afficher
	 */
	public void appendGreenText(String text) {
		text = turnGreen(text);
		appendText(text);
	}

	/**
	 * Méthode permettant d'ajouter du texte de couleur jaune dans la console (sans aller à la ligne)
	 * @param text Texte à afficher
	 */
	public void appendYellowText(String text) {
		text = turnYellow(text);
		appendText(text);
	}

	/**
	 * Méthode permettant d'ajouter du texte de couleur magenta [représentant le joker] dans la console (sans aller à la ligne)
	 * @param text Texte à afficher
	 */
	public void appendJokerText(String text) {
		text = turnMagenta(text);
		appendText(text);
	}

	/* ========================================= UTILS ========================================= */
	
	/**
	 * Méthode privée rajoutant les codes ANSI necessaires pour rendre la chaine de caractères en rouge
	 * @param textToTurnRed Texte à transformer
	 * @return String contenant la chaine de caractère fournie entourée des codes ANSI appropriés
	 */
	private String turnRed(String textToTurnRed) {
		return ConsoleCodesAnsi.COLOR_RED + textToTurnRed + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * Méthode privée rajoutant les codes ANSI necessaires pour rendre la chaine de caractères en bleu
	 * @param textToTurnBlue Texte à transformer
	 * @return String contenant la chaine de caractère fournie entourée des codes ANSI appropriés
	 */
	private String turnBlue(String textToTurnBlue) {
		return ConsoleCodesAnsi.COLOR_CYAN + textToTurnBlue + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * Méthode privée rajoutant les codes ANSI necessaires pour rendre la chaine de caractères en vert
	 * @param textToTurnGreen Texte à transformer
	 * @return String contenant la chaine de caractère fournie entourée des codes ANSI appropriés
	 */
	private String turnGreen(String textToTurnGreen) {
		return ConsoleCodesAnsi.COLOR_GREEN + textToTurnGreen + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * Méthode privée rajoutant les codes ANSI necessaires pour rendre la chaine de caractères en jaune
	 * @param textToTurnYellow Texte à transformer
	 * @return String contenant la chaine de caractère fournie entourée des codes ANSI appropriés
	 */
	private String turnYellow(String textToTurnYellow) {
		return ConsoleCodesAnsi.COLOR_YELLOW + textToTurnYellow + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * Méthode privée rajoutant les codes ANSI necessaires pour rendre la chaine de caractères en magenta
	 * @param textToTurnMagenta Texte à transformer
	 * @return String contenant la chaine de caractère fournie entourée des codes ANSI appropriés
	 */
	private String turnMagenta(String textToTurnMagenta) {
		return ConsoleCodesAnsi.COLOR_MAGENTA + textToTurnMagenta + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * Méthode privée rajoutant les codes ANSI necessaires pour rendre la chaine de caractères en gras
	 * @param textToTurnBold Texte à transformer
	 * @return String contenant la chaine de caractère fournie entourée des codes ANSI appropriés
	 */
	private String turnBold(String textToTurnBold) {
		return ConsoleCodesAnsi.ANSI_BOLD + textToTurnBold + ConsoleCodesAnsi.ANSI_NORMAL;
	}

	/**
	 * Méthode privée rajoutant les codes ANSI necessaires pour rendre la chaine de caractères en blanc sur fond bleu
	 * @param whiteOnBlue Texte à transformer
	 * @return String contenant la chaine de caractère fournie entourée des codes ANSI appropriés
	 */
	private String whiteOnBlue(String textToInvert) {
		return ConsoleCodesAnsi.ANSI_WHITEONBLUE + textToInvert + ConsoleCodesAnsi.ANSI_NORMAL;
	}

	/* ========================================= BASIC DISPLAYS ========================================= */

	/**
	 * Méthode permettant de vider la console
	 */
	public void clearDisplay() {
		AnsiConsole.systemInstall();
		printText(ConsoleCodesAnsi.ANSI_CLS);
	}

	/**
	 * Méthode permettant d'afficher une ligne vide dans la console
	 */
	public void displayBlankLine() {
		printText("");
	}

	/**
	 * Méthode permettant de créer un titre avec barres de séparation de longueur adéquate
	 * @param length Nombre de caractères composant la chaine
	 */
	public void displaySeparationBar(int length) {
		String bar = "";
		for(int i=0; i<length; i++) {
			bar += "-";
		}
		printText(bar);
	}
}
