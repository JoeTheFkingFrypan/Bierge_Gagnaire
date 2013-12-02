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
	 * Méthode permettant d'afficher du texte en gras et aller à la ligne
	 * @param text Texte à afficher
	 */
	public void displayBoldText(String text) {
		this.consoleDisplay.displayBoldText(text);
	}
	
	/**
	 * Méthode permettant d'afficher du texte en gras sans aller à la ligne
	 * @param text Texte à afficher
	 */
	public void appendBoldText(String text) {
		this.consoleDisplay.appendBoldText(text);
	}
	
	/**
	 * Méthode permettant d'afficher un texte d'erreur (rouge/gras)
	 * @param text Texte à afficher
	 */
	public void displayErrorText(String text) {
		this.consoleDisplay.displayErrorText(text);
	}
	
	/**
	 * Méthode permettant d'afficher un texte de succès (vert/gras)
	 * @param text Texte à afficher
	 */
	public void displaySuccessText(String text) {
		this.consoleDisplay.displaySuccessText(text);
	}
	
	/* ========================================= CLEARING ========================================= */
	
	/**
	 * Méthode permettant de nettoyer la console (suppression de toutes lignes affichées)
	 */
	public void clearDisplay() {
		this.consoleDisplay.clearDisplay();
	}
	
	/**
	 * Méthode permettant d'aller à la ligne (ligne vide)
	 */
	public void insertBlankLine() {
		this.consoleDisplay.displayBlankLine();
	}

	/* ========================================= EMPHASIS ========================================= */
	
	/**
	 * Méthode permettant d'afficher un titre avec emphase
	 * @param title Titre à afficher
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
	 * Méthode permettant d'utiliser du texte comme barre de séparation (affichage en couleurs négatives)
	 * @param text Texte à afficher
	 */
	public void displaySeparationText(String text) {
		this.consoleDisplay.displaySeparationText(text);
	}
	
	/* ========================================= CARD DISPLAY ========================================= */
	
	/**
	 * Méthode permettant d'afficher une collection de cartes complète et d'aller à la ligne
	 * @param cardsToDisplay Collection de cartes à afficher
	 */
	public void displayCardCollection(Collection<Carte> cardsToDisplay) {
		int index = 0;
		for(Carte currentCard : cardsToDisplay) {
			displayOneCard(currentCard,index++);
		}
		insertBlankLine();
	}
	
	/**
	 * Méthode permettant d'afficher une unique carte et d'aller à la ligne
	 * @param cardToDisplay Carte à afficher
	 */
	public void displayCard(Carte cardToDisplay) {
		displayOneCard(cardToDisplay);
		insertBlankLine();
	}
	
	/**
	 * Méthode privée permettant d'afficher une carte avec gestion de sa couleur et de son type (numérotée, spéciale)
	 * @param cardToDisplay Carte à afficher
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
	 * Méthode privée permettant d'afficher une unique carte avec gestion de sa couleur et de son type (numérotée, spéciale) en accolant son index
	 * @param cardToDisplay Carte à afficher
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
	 * Méthode privée permettant d'afficher une carte spéciale dans la console (avec gestion de la couleur et de son effet)
	 * @param cardToDisplay Carte à afficher
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
	 * Méthode privée permettant d'afficher une carte spéciale dans la console (avec gestion de la couleur et de son effet) en accolant son index
	 * @param cardToDisplay Carte à afficher
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
	 * Méthode privée permettant d'afficher une carte numérotée (avec gestion de sa couleur) 
	 * @param cardToDisplay Carte à afficher
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
	 * Méthode privée permettant d'afficher une carte numérotée (avec gestion de sa couleur) 
	 * @param cardToDisplay Carte à afficher
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
