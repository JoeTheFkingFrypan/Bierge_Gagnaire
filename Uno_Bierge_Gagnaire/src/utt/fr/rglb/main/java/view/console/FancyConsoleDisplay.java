package utt.fr.rglb.main.java.view.console;

import java.io.Serializable;

import com.google.common.base.Preconditions;

import org.fusesource.jansi.AnsiConsole;

import utt.fr.rglb.main.java.console.model.ConsoleCodesAnsi;

/**
 * Classe permettant d'afficher du texte en couleur dans la console 
 * </br>(ou avec emphase --gras, couleurs inversées, etc)
 */
public class FancyConsoleDisplay implements Serializable {
	private static final long serialVersionUID = 1L;

	/* ========================================= WHITE TEXT ========================================= */

	/**
	 * Méthode permettant d'afficher du texte dans la console en gras (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void displayBoldText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnBold(text);
		AnsiConsole.out.println(text);
	}

	/**
	 * Méthode permettant d'afficher du texte dans la console en gras (sans retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void appendBoldText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnBold(text);
		AnsiConsole.out.print(text);
	}

	/* ========================================= RED TEXT ========================================= */

	/**
	 * Méthode permettant d'afficher un message d'erreur dans la console -en gras et en rouge (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void displayErrorText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		generateRedEmphasis(text);
	}

	/* ========================================= GREEN TEXT ========================================= */

	/**
	 * Méthode permettant d'afficher un message de succès dans la console -en gras et en vert (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void displaySuccessText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		generateGreenEmphasis(text);
	}

	/* ========================================= HEADER & EMPHASIS ========================================= */

	/**
	 * Méthode permettant d'afficher un message de séparation dans la console -en blanc sur fond bleu (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void displaySeparationText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		generateWhiteOnBlueEmphasis(text);
	}

	/**
	 * Méthode permettant d'afficher un message d'erreur dans la console -en gras et en rouge (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void generateRedEmphasis(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnRed(text);
		text = turnBold(text);
		AnsiConsole.out.println(text);
	}

	/**
	 * Méthode permettant d'afficher un message de succès dans la console -en gras et en vert (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void generateGreenEmphasis(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnGreen(text);
		text = turnBold(text);
		AnsiConsole.out.println(text);
	}

	/**
	 * Méthode permettant d'afficher un message de séparation dans la console -en blanc sur fond bleu (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void generateWhiteOnBlueEmphasis(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnBold(text);
		text = whiteOnBlue(text);
		AnsiConsole.out.println(text);
	}

	/**
	 * Méthode permettant d'afficher un message de "fonctionnement" -trace des actions des utilisateurs sur la partie -en magenta (avec retour à la ligne)
	 * @param text Texte à afficher
	 */
	public void generateJokerEmphasis(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnBold(text);
		text = turnMagenta(text);
		AnsiConsole.out.println(text);
	}

	/* ========================================= APPEND TEXT ========================================= */

	/**
	 * Méthode permettant d'ajouter du texte de couleur bleue dans la console (sans aller à la ligne)
	 * @param text Texte à afficher
	 */
	public void appendBlueText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnBlue(text);
		AnsiConsole.out.print(text);
	}

	/**
	 * Méthode permettant d'ajouter du texte de couleur rouge dans la console (sans aller à la ligne)
	 * @param text Texte à afficher
	 */
	public void appendRedText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnRed(text);
		AnsiConsole.out.print(text);
	}

	/**
	 * Méthode permettant d'ajouter du texte de couleur verte dans la console (sans aller à la ligne)
	 * @param text Texte à afficher
	 */
	public void appendGreenText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnGreen(text);
		AnsiConsole.out.print(text);
	}

	/**
	 * Méthode permettant d'ajouter du texte de couleur jaune dans la console (sans aller à la ligne)
	 * @param text Texte à afficher
	 */
	public void appendYellowText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnYellow(text);
		AnsiConsole.out.print(text);
	}

	/**
	 * Méthode permettant d'ajouter du texte de couleur magenta [représentant le joker] dans la console (sans aller à la ligne)
	 * @param text Texte à afficher
	 */
	public void appendJokerText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnMagenta(text);
		AnsiConsole.out.print(text);
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
	 * @param textToInvert Texte à transformer
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
		AnsiConsole.out.println(ConsoleCodesAnsi.ANSI_CLS);
	}

	/**
	 * Méthode permettant d'afficher une ligne vide dans la console
	 */
	public void displayBlankLine() {
		AnsiConsole.out.println("");
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
		AnsiConsole.out.println(bar);
	}
}
