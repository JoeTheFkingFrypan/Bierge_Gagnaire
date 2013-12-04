package main.java.cards.model;

import java.util.Collection;
import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Card;
import main.java.cards.model.basics.CardSpecial;
import main.java.cards.model.basics.Color;
import main.java.console.view.View;

/**
 * Classe dont le r�le est de permettre un test de compatibilit� entre la carte choisie par l'utilisateur et la carte de r�f�rence de mani�re plus ais�e
 * Elle comprend �galement la couleur globale d�finie lors du jeu d'un joker afin de centraliser tous les crit�res de validit� lors du choix d'une carte
 */
public class GameModelBean {
	private Card lastCardPlayed;
	private Color globalColor;
	private View consoleView;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur de GameModelBean
	 * @param lastCardPlayed Carte derni�rement jou�e (carte visible du talon)
	 * @param globalColor Couleur globale ayant �ventuellement �t� choisie
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
	 * M�thode permettant de s'assurer que la collection de cartes pass�e en param�tre a au moins une carte compatible avec la r�f�rence/couleur globale
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
	 * M�thode permettant de s'assurer que la carte pass�e en param�tre est compatible avec la r�f�rence/couleur globale
	 * @param cardFromPlayer Carte dont on souhaite connaitre la compatibilit�
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
	 * M�thode priv�e permettant de proc�der � la comparaison entre la carte de r�f�rence et la carte SPECIALE pass�e en param�tre
	 * @param specialCardFromPlayer Carte dont on souhaite connaitre la compatibilit�
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
	 * M�thode priv�e permettant de proc�der � la comparaison entre la carte de r�f�rence et la carte pass�e en param�tre
	 * @param cardFromPlayer Carte dont on souhaite connaitre la compatibilit�
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
	 * M�thode priv�e permettant de v�rifier si une "simple" comparaison est suffisante pour attester de sa compatibilit� avec la r�f�rence
	 * @param currentCard Carte dont on souhaite connaitre la compatibilit�
	 */
	private boolean numberedCardComparaisonIsEnough(Card currentCard) {
		Preconditions.checkNotNull(currentCard,"[ERROR] Could not check compatibility : provided card is null");
		if(getLastCardPlayed().isCompatibleWith(currentCard)) {
			return true;
		}
		return false;
	}
	
	/**
	 * M�thode priv�e permettant de v�rifier si une "simple" comparaison est suffisante pour attester de sa compatibilit� avec la r�f�rence
	 * @param currentCard Carte dont on souhaite connaitre la compatibilit�
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
	 * M�thode priv�e permettant de v�rifier si une comparaison de couleur entre la couleur globale et celle de la carte pass�e en param�tre est suffisante pour attester de sa compatibilit�
	 * @param currentCard Carte dont on souhaite connaitre la compatibilit�
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
	 * M�thode permettant de savoir si une couleur globale est d�finie (si un joker/+4 a �t� pr�c�dement jou�)
	 * @return TRUE si une couleur globale est d�finie, FALSE sinon
	 */
	public boolean globalColorIsSet() {
		return ! this.globalColor.equals(Color.JOKER);
	}

	/**
	 * M�thode permettant de r�cuperer la derni�re carte ayant �t� jou�e (sans la retirer)
	 * @return La derni�re carte ayant �t� jou�e
	 */
	public Card getLastCardPlayed() {
		return this.lastCardPlayed;
	}

	/* ========================================= DISPLAY ========================================= */

	/**
	 * M�thode permettant d'ajouter la couleur globale dans l'interface (color�e de mani�re ad�quate)
	 */
	public void appendGlobalColorIfItIsSet() {
		if(globalColorIsSet()) {
			this.consoleView.appendBoldText("* And global color is set to ");
			displayGlobalColorTextWithAppropriateColor();
			this.consoleView.insertBlankLine();
		}
	}

	/**
	 * M�thode priv�e permettant d'afficher le nom de la couleur globale dans l'interface (color� de mani�re ad�quate)
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