package main.java.console.view;

import main.java.console.model.ConsoleCodesAnsi;

import org.fusesource.jansi.AnsiConsole;

import com.google.common.base.Preconditions;

public class FancyConsoleDisplay {

	/* ========================================= TYPES OF DISPLAY ========================================= */
	
	/**
	 * Méthode permettant d'afficher du texte dans la console (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	private void printText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		AnsiConsole.out.println(text);
	}

	/**
	 * Méthode permettant d'afficher du texte dans la console (sans retour à la ligne)
	 * @param text Texte à afficher
	 */
	private void appendText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		AnsiConsole.out.print(text);
	}

	/* ========================================= HEADER & SENTENSES ========================================= */

	/**
	 * Méthode permettant d'afficher du texte dans la console en gras (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void displayBoldText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnBold(text);
		printText(text);
	}
	
	/**
	 * Méthode permettant d'afficher du texte dans la console en gras (sans retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void appendBoldText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnBold(text);
		appendText(text);
	}

	/**
	 * Méthode permettant d'afficher un message d'erreur dans la console -en gras et en rouge (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void displayErrorText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		generateRedEmphasis(text);
	}

	/**
	 * Méthode permettant d'afficher un message de succès dans la console -en gras et en vert (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void displaySuccessText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		generateGreenEmphasis(text);
	}
	
	/**
	 * Méthode permettant d'afficher un message de séparation dans la console -en blanc sur fond bleu (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void displaySeparationText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		generateWhiteOnBlueEmphasis(text);
	}

	/* ========================================= HEADER UTILS ========================================= */
	
	/**
	 * Méthode permettant d'afficher un message d'erreur dans la console -en gras et en rouge (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void generateRedEmphasis(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnRed(text);
		text = turnBold(text);
		printText(text);
	}

	/**
	 * Méthode permettant d'afficher un message de succès dans la console -en gras et en vert (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void generateGreenEmphasis(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnGreen(text);
		text = turnBold(text);
		printText(text);
	}
	
	/**
	 * Méthode permettant d'afficher un message de séparation dans la console -en blanc sur fond bleu (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void generateWhiteOnBlueEmphasis(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnBold(text);
		text = whiteOnBlue(text);
		printText(text);
	}

	public void displayJokerText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnBold(text);
		text = turnMagenta(text);
		printText(text);
	}
	
	/* ========================================= APPEND TEXT ========================================= */

	
	
	/**
	 * Méthode permettant d'ajouter du texte de couleur bleue dans la console (sans aller à la ligne)
	 * @param text Texte à afficher
	 */
	public void appendBlueText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnBlue(text);
		appendText(text);
	}

	/**
	 * Méthode permettant d'ajouter du texte de couleur rouge dans la console (sans aller à la ligne)
	 * @param text Texte à afficher
	 */
	public void appendRedText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnRed(text);
		appendText(text);
	}

	/**
	 * Méthode permettant d'ajouter du texte de couleur verte dans la console (sans aller à la ligne)
	 * @param text Texte à afficher
	 */
	public void appendGreenText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnGreen(text);
		appendText(text);
	}

	/**
	 * Méthode permettant d'ajouter du texte de couleur jaune dans la console (sans aller à la ligne)
	 * @param text Texte à afficher
	 */
	public void appendYellowText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnYellow(text);
		appendText(text);
	}

	/**
	 * Méthode permettant d'ajouter du texte de couleur magenta [représentant le joker] dans la console (sans aller à la ligne)
	 * @param text Texte à afficher
	 */
	public void appendJokerText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
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
		Preconditions.checkNotNull(textToTurnRed,"[ERROR] Impossible to display text : provided one is null");
		return ConsoleCodesAnsi.COLOR_RED + textToTurnRed + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * Méthode privée rajoutant les codes ANSI necessaires pour rendre la chaine de caractères en bleu
	 * @param textToTurnBlue Texte à transformer
	 * @return String contenant la chaine de caractère fournie entourée des codes ANSI appropriés
	 */
	private String turnBlue(String textToTurnBlue) {
		Preconditions.checkNotNull(textToTurnBlue,"[ERROR] Impossible to display text : provided one is null");
		return ConsoleCodesAnsi.COLOR_CYAN + textToTurnBlue + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * Méthode privée rajoutant les codes ANSI necessaires pour rendre la chaine de caractères en vert
	 * @param textToTurnGreen Texte à transformer
	 * @return String contenant la chaine de caractère fournie entourée des codes ANSI appropriés
	 */
	private String turnGreen(String textToTurnGreen) {
		Preconditions.checkNotNull(textToTurnGreen,"[ERROR] Impossible to display text : provided one is null");
		return ConsoleCodesAnsi.COLOR_GREEN + textToTurnGreen + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * Méthode privée rajoutant les codes ANSI necessaires pour rendre la chaine de caractères en jaune
	 * @param textToTurnYellow Texte à transformer
	 * @return String contenant la chaine de caractère fournie entourée des codes ANSI appropriés
	 */
	private String turnYellow(String textToTurnYellow) {
		Preconditions.checkNotNull(textToTurnYellow,"[ERROR] Impossible to display text : provided one is null");
		return ConsoleCodesAnsi.COLOR_YELLOW + textToTurnYellow + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * Méthode privée rajoutant les codes ANSI necessaires pour rendre la chaine de caractères en magenta
	 * @param textToTurnMagenta Texte à transformer
	 * @return String contenant la chaine de caractère fournie entourée des codes ANSI appropriés
	 */
	private String turnMagenta(String textToTurnMagenta) {
		Preconditions.checkNotNull(textToTurnMagenta,"[ERROR] Impossible to display text : provided one is null");
		return ConsoleCodesAnsi.COLOR_MAGENTA + textToTurnMagenta + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * Méthode privée rajoutant les codes ANSI necessaires pour rendre la chaine de caractères en gras
	 * @param textToTurnBold Texte à transformer
	 * @return String contenant la chaine de caractère fournie entourée des codes ANSI appropriés
	 */
	private String turnBold(String textToTurnBold) {
		Preconditions.checkNotNull(textToTurnBold,"[ERROR] Impossible to display text : provided one is null");
		return ConsoleCodesAnsi.ANSI_BOLD + textToTurnBold + ConsoleCodesAnsi.ANSI_NORMAL;
	}

	/**
	 * Méthode privée rajoutant les codes ANSI necessaires pour rendre la chaine de caractères en blanc sur fond bleu
	 * @param whiteOnBlue Texte à transformer
	 * @return String contenant la chaine de caractère fournie entourée des codes ANSI appropriés
	 */
	private String whiteOnBlue(String textToInvert) {
		Preconditions.checkNotNull(textToInvert,"[ERROR] Impossible to display text : provided one is null");
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
		Preconditions.checkArgument(length > 0,"[ERROR] Impossible to display separation bar with length = 0");
		String bar = "";
		for(int i=0; i<length; i++) {
			bar += "-";
		}
		printText(bar);
	}
}
