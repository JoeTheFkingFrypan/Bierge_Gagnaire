package main.java.cards.model;

import java.util.Collection;
import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Card;
import main.java.cards.model.basics.CardSpecial;
import main.java.cards.model.basics.Color;
import main.java.console.view.View;

/**
 * Classe dont le rôle est de permettre un test de compatibilité entre la carte choisie par l'utilisateur et la carte de référence de manière plus aisée
 * Elle comprend également la couleur globale définie lors du jeu d'un joker afin de centraliser tous les critères de validité lors du choix d'une carte
 */
public class GameModelBean {
	private Card lastCardPlayed;
	private Color globalColor;
	private View consoleView;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur de GameModelBean
	 * @param lastCardPlayed Carte dernièrement jouée (carte visible du talon)
	 * @param globalColor Couleur globale ayant éventuellement été choisie
	 * @param consoleView Vue permettant d'afficher des informations dans l'interface
	 */
	public GameModelBean(Card lastCardPlayed, Color globalColor, View consoleView) {
		Preconditions.checkNotNull(lastCardPlayed,"[ERROR] provided card was null");
		Preconditions.checkNotNull(globalColor,"[ERROR] provided global color was null");
		Preconditions.checkNotNull(globalColor,"[ERROR] provided view was null");
		this.lastCardPlayed = lastCardPlayed;
		this.globalColor = globalColor;
		this.consoleView = consoleView;
	}

	/* ========================================= COMPARAISON - HIGH LEVEL ========================================= */

	/**
	 * Méthode permettant de s'assurer que la collection de cartes passée en paramètre a au moins une carte compatible avec la référence/couleur globale
	 * @param cardsFromPlayer Collection de cartes
	 * @return TRUE si la collection contient au moins une carte compatible, FALSE sinon
	 */
	public boolean isCompatibleWith(Collection<Card> cardsFromPlayer) {
		Preconditions.checkNotNull(cardsFromPlayer,"[ERROR] Could not check compatibility : provided card collection is null");
		for(Card currentCard : cardsFromPlayer) {
			if(isCompatibleWith(currentCard)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Méthode permettant de s'assurer que la carte passée en paramètre est compatible avec la référence/couleur globale
	 * @param cardFromPlayer Carte dont on souhaite connaitre la compatibilité
	 * @return TRUE si la carte est compatible, FALSE sinon
	 */
	public boolean isCompatibleWith(Card cardFromPlayer) {
		Preconditions.checkNotNull(cardFromPlayer,"[ERROR] Could not check compatibility : provided card is null");
		if(cardFromPlayer.isSpecial()) {
			CardSpecial explicitConversion = (CardSpecial)cardFromPlayer;
			return compatibilityCheckWithSpecialCard(explicitConversion);
		} else {
			return compatibilityCheckWithNumberedCard(cardFromPlayer);
		}
	}

	/**
	 * Méthode privée permettant de procéder à la comparaison entre la carte de référence et la carte SPECIALE passée en paramètre
	 * @param specialCardFromPlayer Carte dont on souhaite connaitre la compatibilité
	 * @return TRUE si la carte est compatible, FALSE sinon
	 */
	private boolean compatibilityCheckWithSpecialCard(CardSpecial specialCardFromPlayer) {
		Preconditions.checkNotNull(specialCardFromPlayer,"[ERROR] Could not check compatibility : provided special card is null");
		Preconditions.checkArgument(specialCardFromPlayer.isSpecial(),"[ERROR] Provided card is NOT special");
		if(globalComparaisonIsEnough(specialCardFromPlayer) || specialCardComparaisonIsEnough(specialCardFromPlayer)) {
			return true;
		}
		return false;
	}

	/**
	 * Méthode privée permettant de procéder à la comparaison entre la carte de référence et la carte passée en paramètre
	 * @param cardFromPlayer Carte dont on souhaite connaitre la compatibilité
	 * @return TRUE si la carte est compatible, FALSE sinon
	 */
	private boolean compatibilityCheckWithNumberedCard(Card cardFromPlayer) {
		Preconditions.checkNotNull(cardFromPlayer,"[ERROR] Could not check compatibility : provided card is null");
		Preconditions.checkArgument(!cardFromPlayer.isSpecial(),"[ERROR] Provided card IS special");
		if(globalComparaisonIsEnough(cardFromPlayer) || numberedCardComparaisonIsEnough(cardFromPlayer) ) {
			return true;
		}
		return false;
	}


	/* ========================================= COMPARAISON - UTILS ========================================= */

	/**
	 * Méthode privée permettant de vérifier si une "simple" comparaison est suffisante pour attester de sa compatibilité avec la référence
	 * @param currentCard Carte dont on souhaite connaitre la compatibilité
	 */
	private boolean numberedCardComparaisonIsEnough(Card currentCard) {
		Preconditions.checkNotNull(currentCard,"[ERROR] Could not check compatibility : provided card is null");
		if(getLastCardPlayed().isCompatibleWith(currentCard)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Méthode privée permettant de vérifier si une "simple" comparaison est suffisante pour attester de sa compatibilité avec la référence
	 * @param currentCard Carte dont on souhaite connaitre la compatibilité
	 * @return TRUE si la carte est compatible (couleur identique, effet identique) 
	 */
	private boolean specialCardComparaisonIsEnough(CardSpecial currentCard) {
		Preconditions.checkNotNull(currentCard,"[ERROR] Could not check compatibility : provided special card is null");
		if(getLastCardPlayed().isCompatibleWith(currentCard)) {
			return true;
		}
		return false;
	}

	/**
	 * Méthode privée permettant de vérifier si une comparaison de couleur entre la couleur globale et celle de la carte passée en paramètre est suffisante pour attester de sa compatibilité
	 * @param currentCard Carte dont on souhaite connaitre la compatibilité
	 * @return TRUE si la carte est compatible (couleur identique) 
	 */
	private boolean globalComparaisonIsEnough(Card currentCard) {
		if(globalColorIsSet()) {
			if(currentCard.getCouleur().equals(this.globalColor)) {
				return true;
			}
		}
		return false;
	}

	/* ========================================= GETTERS ========================================= */

	/**
	 * Méthode permettant de savoir si une couleur globale est définie (si un joker/+4 a été précédement joué)
	 * @return TRUE si une couleur globale est définie, FALSE sinon
	 */
	public boolean globalColorIsSet() {
		return ! this.globalColor.equals(Color.JOKER);
	}

	/**
	 * Méthode permettant de récuperer la dernière carte ayant été jouée (sans la retirer)
	 * @return La dernière carte ayant été jouée
	 */
	public Card getLastCardPlayed() {
		return this.lastCardPlayed;
	}

	/* ========================================= DISPLAY ========================================= */

	/**
	 * Méthode permettant d'ajouter la couleur globale dans l'interface (colorée de manière adéquate)
	 */
	public void appendGlobalColorIfItIsSet() {
		if(globalColorIsSet()) {
			this.consoleView.appendBoldText("* And global color is set to ");
			displayGlobalColorTextWithAppropriateColor();
			this.consoleView.insertBlankLine();
		}
	}

	/**
	 * Méthode privée permettant d'afficher le nom de la couleur globale dans l'interface (coloré de manière adéquate)
	 */
	private void displayGlobalColorTextWithAppropriateColor() {
		if(this.globalColor.equals(Color.RED)) {
			this.consoleView.appendBoldRedText("[RED]");
		} else if(this.globalColor.equals(Color.BLUE)) {
			this.consoleView.appendBoldBlueText("[BLUE]");
		} else if(this.globalColor.equals(Color.GREEN)) {
			this.consoleView.appendBoldGreenText("[GREEN]");
		} else if(this.globalColor.equals(Color.YELLOW)) {
			this.consoleView.appendBoldYellowText("[YELLOW]");
		}
	}
}