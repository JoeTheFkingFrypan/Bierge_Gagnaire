package main.java.console.view;

import java.util.Collection;

import main.java.cards.model.basics.Carte;
import main.java.cards.model.basics.CarteSpeciale;
import main.java.cards.model.basics.Couleur;


public abstract class View {
	protected FancyConsoleDisplay consoleDisplay;
	
	public View() {
		this.consoleDisplay = new FancyConsoleDisplay();
	}
	
	
	public void displayBoldText(String text) {
		this.consoleDisplay.displayBoldText(text);
	}
	
	public void displayErrorText(String text) {
		this.consoleDisplay.displayErrorText(text);
	}
	
	public void displaySuccessText(String text) {
		this.consoleDisplay.displaySuccessText(text);
	}
	
	public void clearDisplay() {
		this.consoleDisplay.clearDisplay();
	}
	
	public void insertBlankLine() {
		this.consoleDisplay.displayBlankLine();
	}

	public void displayTitle(String title) {
		int length = title.length();
		insertBlankLine();
		this.consoleDisplay.displaySeparationBar(length);
		this.consoleDisplay.displayBoldText(title);
		this.consoleDisplay.displaySeparationBar(length);
		insertBlankLine();
	}
	
	/* ========================================= CARD DISPLAY ========================================= */
	
	public void displayCardCollection(Collection<Carte> cardsToDisplay) {
		int index = 0;
		for(Carte currentCard : cardsToDisplay) {
			displayOneCard(currentCard,index++);
		}
		insertBlankLine();
	}
	
	public void displayCard(Carte cardToDisplay) {
		displayOneCard(cardToDisplay);
		insertBlankLine();
	}
	
	private void displayOneCard(Carte cardToDisplay) {
		if(cardToDisplay.isSpecial()) {
			CarteSpeciale explictSpecialCardToDisplay = (CarteSpeciale)cardToDisplay;
			displaySpecialCard(explictSpecialCardToDisplay);
		} else {
			displayNumberedCard(cardToDisplay);
		}
	}
	
	private void displayOneCard(Carte cardToDisplay, int index) {
		if(cardToDisplay.isSpecial()) {
			CarteSpeciale explictSpecialCardToDisplay = (CarteSpeciale)cardToDisplay;
			displaySpecialCard(explictSpecialCardToDisplay,index);
		} else {
			displayNumberedCard(cardToDisplay,index);
		}
	}
	
	/* ========================================= SPECIAL CARDS ========================================= */

	private void displaySpecialCard(CarteSpeciale cardToDisplay) {
		Couleur color = cardToDisplay.getCouleur();
		String effect = cardToDisplay.getEffet();
		if(color.equals(Couleur.BLEUE)) {
			this.consoleDisplay.appendBlueText("[" + effect + "] ");
		} else if(color.equals(Couleur.ROUGE)) {
			this.consoleDisplay.appendRedText("[" + effect + "] ");
		} else if(color.equals(Couleur.VERTE)) {
			this.consoleDisplay.appendGreenText("[" + effect + "] ");
		} else if(color.equals(Couleur.JAUNE)) {
			this.consoleDisplay.appendYellowText("[" + effect + "] ");
		} else { 	//if(colorFromCard.equals(Couleur.JOKER)
			this.consoleDisplay.appendJokerText("[" + effect + "] ");
		}
	}
	
	private void displaySpecialCard(CarteSpeciale cardToDisplay, int index) {
		Couleur color = cardToDisplay.getCouleur();
		String effect = cardToDisplay.getEffet();
		if(color.equals(Couleur.BLEUE)) {
			this.consoleDisplay.appendBoldIndex(index);
			this.consoleDisplay.appendBlueText("[" + effect + "] ");
		} else if(color.equals(Couleur.ROUGE)) {
			this.consoleDisplay.appendBoldIndex(index);
			this.consoleDisplay.appendRedText("[" + effect + "] ");
		} else if(color.equals(Couleur.VERTE)) {
			this.consoleDisplay.appendBoldIndex(index);
			this.consoleDisplay.appendGreenText("[" + effect + "] ");
		} else if(color.equals(Couleur.JAUNE)) {
			this.consoleDisplay.appendBoldIndex(index);
			this.consoleDisplay.appendYellowText("[" + effect + "] ");
		} else { 	//if(colorFromCard.equals(Couleur.JOKER)
			this.consoleDisplay.appendBoldIndex(index);
			this.consoleDisplay.appendJokerText("[" + effect + "] ");
		}
	}
	
	/* ========================================= NUMBERED CARDS ========================================= */
	
	private void displayNumberedCard(Carte cardToDisplay) {
		Couleur color = cardToDisplay.getCouleur();
		int value = cardToDisplay.getValeur();
		if(color.equals(Couleur.BLEUE)) {
			this.consoleDisplay.appendBlueText("[" + value + "] ");
		} else if(color.equals(Couleur.ROUGE)) {
			this.consoleDisplay.appendRedText("[" + value + "] ");
		} else if(color.equals(Couleur.VERTE)) {
			this.consoleDisplay.appendGreenText("[" + value + "] ");
		} else {	//if(colorFromCard.equals(Couleur.JAUNE)
			this.consoleDisplay.appendYellowText("[" + value + "] ");
		}
	}
	
	private void displayNumberedCard(Carte cardToDisplay, int index) {
		Couleur color = cardToDisplay.getCouleur();
		int value = cardToDisplay.getValeur();
		if(color.equals(Couleur.BLEUE)) {
			this.consoleDisplay.appendBoldIndex(index);
			this.consoleDisplay.appendBlueText("[" + value + "] ");
		} else if(color.equals(Couleur.ROUGE)) {
			this.consoleDisplay.appendBoldIndex(index);
			this.consoleDisplay.appendRedText("[" + value + "] ");
		} else if(color.equals(Couleur.VERTE)) {
			this.consoleDisplay.appendBoldIndex(index);
			this.consoleDisplay.appendGreenText("[" + value + "] ");
		} else {	//if(colorFromCard.equals(Couleur.JAUNE)
			this.consoleDisplay.appendBoldIndex(index);
			this.consoleDisplay.appendYellowText("[" + value + "] ");
		}
		
	}
}
