package main.java.console.view;

import main.java.console.model.ConsoleCodesAnsi;

import org.fusesource.jansi.AnsiConsole;

public class FancyConsoleDisplay {

	private void printText(String text) {
		AnsiConsole.out.println(text);
	}

	private void appendText(String text) {
		AnsiConsole.out.print(text);
	}

	/* ========================================= HEADER & SENTENSES ========================================= */

	public void displayBoldText(String text) {
		text = turnBold(text);
		printText(text);
	}
	
	public void appendBoldText(String text) {
		text = turnBold(text);
		appendText(text);
	}

	public void displayErrorText(String text) {
		generateRedEmphasis(text);
	}

	public void displaySuccessText(String text) {
		generateGreenEmphasis(text);
	}
	
	public void displaySeparationText(String text) {
		generateBlueEmphasis(text);
	}

	public void generateRedEmphasis(String text) {
		text = turnRed(text);
		text = turnBold(text);
		printText(text);
	}

	public void generateGreenEmphasis(String text) {
		text = turnGreen(text);
		text = turnBold(text);
		printText(text);
	}
	
	public void generateBlueEmphasis(String text) {
		text = turnBold(text);
		text = whiteOnBlue(text);
		printText(text);
	}

	/* ========================================= CARDS ========================================= */

	public void appendBoldIndex(Integer index) {
		String text = turnBold(index.toString() + ":");
		appendText(text);
	}
	
	public void appendBlueText(String text) {
		text = turnBlue(text);
		appendText(text);
	}

	public void appendRedText(String text) {
		text = turnRed(text);
		appendText(text);
	}

	public void appendGreenText(String text) {
		text = turnGreen(text);
		appendText(text);
	}

	public void appendYellowText(String text) {
		text = turnYellow(text);
		appendText(text);
	}

	public void appendJokerText(String text) {
		text = turnMagenta(text);
		appendText(text);
	}

	/* ========================================= UTILS ========================================= */
	
	private String turnRed(String textToTurnRed) {
		return ConsoleCodesAnsi.COLOR_RED + textToTurnRed + ConsoleCodesAnsi.COLOR_WHITE;
	}

	private String turnBlue(String textToTurnBlue) {
		return ConsoleCodesAnsi.COLOR_CYAN + textToTurnBlue + ConsoleCodesAnsi.COLOR_WHITE;
	}

	private String turnGreen(String textToTurnGreen) {
		return ConsoleCodesAnsi.COLOR_GREEN + textToTurnGreen + ConsoleCodesAnsi.COLOR_WHITE;
	}

	private String turnYellow(String textToTurnYellow) {
		return ConsoleCodesAnsi.COLOR_YELLOW + textToTurnYellow + ConsoleCodesAnsi.COLOR_WHITE;
	}

	private String turnMagenta(String textToTurnMagenta) {
		return ConsoleCodesAnsi.COLOR_MAGENTA + textToTurnMagenta + ConsoleCodesAnsi.COLOR_WHITE;
	}

	private String turnBold(String textToTurnBold) {
		return ConsoleCodesAnsi.ANSI_BOLD + textToTurnBold + ConsoleCodesAnsi.ANSI_NORMAL;
	}

	private String whiteOnBlue(String textToInvert) {
		return ConsoleCodesAnsi.ANSI_WHITEONBLUE + textToInvert + ConsoleCodesAnsi.ANSI_NORMAL;
	}

	/* ========================================= BASIC DISPLAYS ========================================= */

	public void clearDisplay() {
		AnsiConsole.systemInstall();
		printText(ConsoleCodesAnsi.ANSI_CLS);
	}

	public void displayBlankLine() {
		printText("");
	}

	public void displaySeparationBar(int length) {
		String bar = "";
		for(int i=0; i<length; i++) {
			bar += "-";
		}
		printText(bar);
	}
}
