package main.java.console.view;

import java.util.Collection;

import main.java.cards.model.basics.Carte;
import main.java.cards.model.basics.CarteSpeciale;
import main.java.cards.model.basics.Couleur;


public abstract class View {
	protected FancyConsoleDisplay consoleDisplay;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	public View() {
		this.consoleDisplay = new FancyConsoleDisplay();
	}
	
	/* ========================================= TEXT DISPLAY ========================================= */
	
	/**
	 * M�thode permettant d'afficher du texte en gras et aller � la ligne
	 * @param text Texte � afficher
	 */
	public void displayBoldText(String text) {
		this.consoleDisplay.displayBoldText(text);
	}
	
	/**
	 * M�thode permettant d'afficher du texte en gras sans aller � la ligne
	 * @param text Texte � afficher
	 */
	public void appendBoldText(String text) {
		this.consoleDisplay.appendBoldText(text);
	}
	
	/**
	 * M�thode permettant d'afficher un texte d'erreur (rouge/gras)
	 * @param text Texte � afficher
	 */
	public void displayErrorText(String text) {
		this.consoleDisplay.displayErrorText(text);
	}
	
	/**
	 * M�thode permettant d'afficher un texte de succ�s (vert/gras)
	 * @param text Texte � afficher
	 */
	public void displaySuccessText(String text) {
		this.consoleDisplay.displaySuccessText(text);
	}
	
	/* ========================================= CLEARING ========================================= */
	
	/**
	 * M�thode permettant de nettoyer la console (suppression de toutes lignes affich�es)
	 */
	public void clearDisplay() {
		this.consoleDisplay.clearDisplay();
	}
	
	/**
	 * M�thode permettant d'aller � la ligne (ligne vide)
	 */
	public void insertBlankLine() {
		this.consoleDisplay.displayBlankLine();
	}

	/* ========================================= EMPHASIS ========================================= */
	
	/**
	 * M�thode permettant d'afficher un titre avec emphase
	 * @param title Titre � afficher
	 */
	public void displayTitle(String title) {
		int length = title.length();
		insertBlankLine();
		this.consoleDisplay.displaySeparationBar(length);
		this.consoleDisplay.displayBoldText(title);
		this.consoleDisplay.displaySeparationBar(length);
		insertBlankLine();
	}
	
	/**
	 * M�thode permettant d'utiliser du texte comme barre de s�paration (affichage en couleurs n�gatives)
	 * @param text Texte � afficher
	 */
	public void displaySeparationText(String text) {
		this.consoleDisplay.displaySeparationText(text);
	}
	
	/* ========================================= CARD DISPLAY ========================================= */
	
	/**
	 * M�thode permettant d'afficher une collection de cartes compl�te et d'aller � la ligne
	 * @param cardsToDisplay Collection de cartes � afficher
	 */
	public void displayCardCollection(Collection<Carte> cardsToDisplay) {
		int index = 0;
		for(Carte currentCard : cardsToDisplay) {
			displayOneCard(currentCard,index++);
		}
		insertBlankLine();
	}
	
	/**
	 * M�thode permettant d'afficher une unique carte et d'aller � la ligne
	 * @param cardToDisplay Carte � afficher
	 */
	public void displayCard(Carte cardToDisplay) {
		displayOneCard(cardToDisplay);
		insertBlankLine();
	}
	
	/**
	 * M�thode priv�e permettant d'afficher une carte avec gestion de sa couleur et de son type (num�rot�e, sp�ciale)
	 * @param cardToDisplay Carte � afficher
	 */
	private void displayOneCard(Carte cardToDisplay) {
		if(cardToDisplay.isSpecial()) {
			CarteSpeciale explictSpecialCardToDisplay = (CarteSpeciale)cardToDisplay;
			displaySpecialCard(explictSpecialCardToDisplay);
		} else {
			displayNumberedCard(cardToDisplay);
		}
	}
	
	/**
	 * M�thode priv�e permettant d'afficher une unique carte avec gestion de sa couleur et de son type (num�rot�e, sp�ciale) en accolant son index
	 * @param cardToDisplay Carte � afficher
	 * @param index Index de la carte 
	 */
	private void displayOneCard(Carte cardToDisplay, int index) {
		if(cardToDisplay.isSpecial()) {
			CarteSpeciale explictSpecialCardToDisplay = (CarteSpeciale)cardToDisplay;
			displaySpecialCard(explictSpecialCardToDisplay,index);
		} else {
			displayNumberedCard(cardToDisplay,index);
		}
	}
	
	/* ========================================= SPECIAL CARD DISPLAY ========================================= */

	/**
	 * M�thode priv�e permettant d'afficher une carte sp�ciale dans la console (avec gestion de la couleur et de son effet)
	 * @param cardToDisplay Carte � afficher
	 */
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
	
	/**
	 * M�thode priv�e permettant d'afficher une carte sp�ciale dans la console (avec gestion de la couleur et de son effet) en accolant son index
	 * @param cardToDisplay Carte � afficher
	 * @param index Index de la carte
	 */
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
	
	/* ========================================= NUMBERED CARD DISPLAY ========================================= */
	
	/**
	 * M�thode priv�e permettant d'afficher une carte num�rot�e (avec gestion de sa couleur) 
	 * @param cardToDisplay Carte � afficher
	 */
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
	
	/**
	 * M�thode priv�e permettant d'afficher une carte num�rot�e (avec gestion de sa couleur) 
	 * @param cardToDisplay Carte � afficher
	 * @param index Index de la carte
	 */
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
