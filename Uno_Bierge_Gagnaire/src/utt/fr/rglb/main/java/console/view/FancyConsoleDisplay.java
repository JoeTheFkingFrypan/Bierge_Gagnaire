package utt.fr.rglb.main.java.console.view;

import com.google.common.base.Preconditions;
import org.fusesource.jansi.AnsiConsole;

import utt.fr.rglb.main.java.console.model.ConsoleCodesAnsi;

public class FancyConsoleDisplay {

	/* ========================================= WHITE TEXT ========================================= */

	/**
	 * M�thode permettant d'afficher du texte dans la console en gras (avec retour � la ligne)
	 * @param text Texte � afficher
	 */
	public void displayBoldText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnBold(text);
		AnsiConsole.out.println(text);
	}
	
	/**
	 * M�thode permettant d'afficher du texte dans la console en gras (sans retour � la ligne)
	 * @param text Texte � afficher
	 */
	public void appendBoldText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnBold(text);
		 AnsiConsole.out.print(text);
	}

	/* ========================================= RED TEXT ========================================= */
	
	/**
	 * M�thode permettant d'afficher un message d'erreur dans la console -en gras et en rouge (avec retour � la ligne)
	 * @param text Texte � afficher
	 */
	public void displayErrorText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		generateRedEmphasis(text);
	}

	/* ========================================= GREEN TEXT ========================================= */
	
	/**
	 * M�thode permettant d'afficher un message de succ�s dans la console -en gras et en vert (avec retour � la ligne)
	 * @param text Texte � afficher
	 */
	public void displaySuccessText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		generateGreenEmphasis(text);
	}
	
	/**
	 * M�thode permettant d'afficher un message de s�paration dans la console -en blanc sur fond bleu (avec retour � la ligne)
	 * @param text Texte � afficher
	 */
	public void displaySeparationText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		generateWhiteOnBlueEmphasis(text);
	}

	/* ========================================= HEADER & EMPHASIS ========================================= */
	
	/**
	 * M�thode permettant d'afficher un message d'erreur dans la console -en gras et en rouge (avec retour � la ligne)
	 * @param text Texte � afficher
	 */
	public void generateRedEmphasis(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnRed(text);
		text = turnBold(text);
		AnsiConsole.out.println(text);
	}

	/**
	 * M�thode permettant d'afficher un message de succ�s dans la console -en gras et en vert (avec retour � la ligne)
	 * @param text Texte � afficher
	 */
	public void generateGreenEmphasis(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnGreen(text);
		text = turnBold(text);
		AnsiConsole.out.println(text);
	}
	
	/**
	 * M�thode permettant d'afficher un message de s�paration dans la console -en blanc sur fond bleu (avec retour � la ligne)
	 * @param text Texte � afficher
	 */
	public void generateWhiteOnBlueEmphasis(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnBold(text);
		text = whiteOnBlue(text);
		AnsiConsole.out.println(text);
	}

	/**
	 * M�thode permettant d'afficher un message de "fonctionnement" -trace des actions des utilisateurs sur la partie -en magenta (avec retour � la ligne)
	 * @param text Texte � afficher
	 */
	public void generateJokerEmphasis(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnBold(text);
		text = turnMagenta(text);
		AnsiConsole.out.println(text);
	}
	
	/* ========================================= APPEND TEXT ========================================= */

	/**
	 * M�thode permettant d'ajouter du texte de couleur bleue dans la console (sans aller � la ligne)
	 * @param text Texte � afficher
	 */
	public void appendBlueText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnBlue(text);
		 AnsiConsole.out.print(text);
	}

	/**
	 * M�thode permettant d'ajouter du texte de couleur rouge dans la console (sans aller � la ligne)
	 * @param text Texte � afficher
	 */
	public void appendRedText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnRed(text);
		 AnsiConsole.out.print(text);
	}

	/**
	 * M�thode permettant d'ajouter du texte de couleur verte dans la console (sans aller � la ligne)
	 * @param text Texte � afficher
	 */
	public void appendGreenText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnGreen(text);
		 AnsiConsole.out.print(text);
	}

	/**
	 * M�thode permettant d'ajouter du texte de couleur jaune dans la console (sans aller � la ligne)
	 * @param text Texte � afficher
	 */
	public void appendYellowText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnYellow(text);
		 AnsiConsole.out.print(text);
	}

	/**
	 * M�thode permettant d'ajouter du texte de couleur magenta [repr�sentant le joker] dans la console (sans aller � la ligne)
	 * @param text Texte � afficher
	 */
	public void appendJokerText(String text) {
		Preconditions.checkNotNull(text,"[ERROR] Impossible to display text : provided one is null");
		text = turnMagenta(text);
		 AnsiConsole.out.print(text);
	}

	/* ========================================= UTILS ========================================= */
	
	/**
	 * M�thode priv�e rajoutant les codes ANSI necessaires pour rendre la chaine de caract�res en rouge
	 * @param textToTurnRed Texte � transformer
	 * @return String contenant la chaine de caract�re fournie entour�e des codes ANSI appropri�s
	 */
	private String turnRed(String textToTurnRed) {
		Preconditions.checkNotNull(textToTurnRed,"[ERROR] Impossible to display text : provided one is null");
		return ConsoleCodesAnsi.COLOR_RED + textToTurnRed + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * M�thode priv�e rajoutant les codes ANSI necessaires pour rendre la chaine de caract�res en bleu
	 * @param textToTurnBlue Texte � transformer
	 * @return String contenant la chaine de caract�re fournie entour�e des codes ANSI appropri�s
	 */
	private String turnBlue(String textToTurnBlue) {
		Preconditions.checkNotNull(textToTurnBlue,"[ERROR] Impossible to display text : provided one is null");
		return ConsoleCodesAnsi.COLOR_CYAN + textToTurnBlue + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * M�thode priv�e rajoutant les codes ANSI necessaires pour rendre la chaine de caract�res en vert
	 * @param textToTurnGreen Texte � transformer
	 * @return String contenant la chaine de caract�re fournie entour�e des codes ANSI appropri�s
	 */
	private String turnGreen(String textToTurnGreen) {
		Preconditions.checkNotNull(textToTurnGreen,"[ERROR] Impossible to display text : provided one is null");
		return ConsoleCodesAnsi.COLOR_GREEN + textToTurnGreen + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * M�thode priv�e rajoutant les codes ANSI necessaires pour rendre la chaine de caract�res en jaune
	 * @param textToTurnYellow Texte � transformer
	 * @return String contenant la chaine de caract�re fournie entour�e des codes ANSI appropri�s
	 */
	private String turnYellow(String textToTurnYellow) {
		Preconditions.checkNotNull(textToTurnYellow,"[ERROR] Impossible to display text : provided one is null");
		return ConsoleCodesAnsi.COLOR_YELLOW + textToTurnYellow + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * M�thode priv�e rajoutant les codes ANSI necessaires pour rendre la chaine de caract�res en magenta
	 * @param textToTurnMagenta Texte � transformer
	 * @return String contenant la chaine de caract�re fournie entour�e des codes ANSI appropri�s
	 */
	private String turnMagenta(String textToTurnMagenta) {
		Preconditions.checkNotNull(textToTurnMagenta,"[ERROR] Impossible to display text : provided one is null");
		return ConsoleCodesAnsi.COLOR_MAGENTA + textToTurnMagenta + ConsoleCodesAnsi.COLOR_WHITE;
	}

	/**
	 * M�thode priv�e rajoutant les codes ANSI necessaires pour rendre la chaine de caract�res en gras
	 * @param textToTurnBold Texte � transformer
	 * @return String contenant la chaine de caract�re fournie entour�e des codes ANSI appropri�s
	 */
	private String turnBold(String textToTurnBold) {
		Preconditions.checkNotNull(textToTurnBold,"[ERROR] Impossible to display text : provided one is null");
		return ConsoleCodesAnsi.ANSI_BOLD + textToTurnBold + ConsoleCodesAnsi.ANSI_NORMAL;
	}

	/**
	 * M�thode priv�e rajoutant les codes ANSI necessaires pour rendre la chaine de caract�res en blanc sur fond bleu
	 * @param whiteOnBlue Texte � transformer
	 * @return String contenant la chaine de caract�re fournie entour�e des codes ANSI appropri�s
	 */
	private String whiteOnBlue(String textToInvert) {
		Preconditions.checkNotNull(textToInvert,"[ERROR] Impossible to display text : provided one is null");
		return ConsoleCodesAnsi.ANSI_WHITEONBLUE + textToInvert + ConsoleCodesAnsi.ANSI_NORMAL;
	}

	/* ========================================= BASIC DISPLAYS ========================================= */

	/**
	 * M�thode permettant de vider la console
	 */
	public void clearDisplay() {
		AnsiConsole.systemInstall();
		AnsiConsole.out.println(ConsoleCodesAnsi.ANSI_CLS);
	}

	/**
	 * M�thode permettant d'afficher une ligne vide dans la console
	 */
	public void displayBlankLine() {
		AnsiConsole.out.println("");
	}

	/**
	 * M�thode permettant de cr�er un titre avec barres de s�paration de longueur ad�quate
	 * @param length Nombre de caract�res composant la chaine
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
