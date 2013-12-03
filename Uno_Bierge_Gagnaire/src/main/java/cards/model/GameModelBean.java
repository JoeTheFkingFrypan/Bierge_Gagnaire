package main.java.cards.model;

import java.util.Collection;
import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Card;
import main.java.cards.model.basics.CardSpecial;
import main.java.cards.model.basics.Color;
import main.java.console.view.View;

public class GameModelBean {
	private Card lastCardPlayed;
	private Color globalColor;
	private View consoleView;

	/* ========================================= CONSTRUCTOR ========================================= */

	public GameModelBean(Card lastCardPlayed, Color globalColor, View consoleView) {
		Preconditions.checkNotNull(lastCardPlayed,"[ERROR] provided card was null");
		Preconditions.checkNotNull(globalColor,"[ERROR] provided global color was null");
		Preconditions.checkNotNull(globalColor,"[ERROR] provided view was null");
		this.lastCardPlayed = lastCardPlayed;
		this.globalColor = globalColor;
		this.consoleView = consoleView;
	}

	/* ========================================= COMPARAISON - HIGH LEVEL ========================================= */

	public boolean isCompatibleWith(Collection<Card> cardsFromPlayer) {
		for(Card currentCard : cardsFromPlayer) {
			if(isCompatibleWith(currentCard)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isCompatibleWith(Card cardFromPlayer) {
		if(cardFromPlayer.isSpecial()) {
			CardSpecial explicitConversion = (CardSpecial)cardFromPlayer;
			return compatibilityCheckWithSpecialCard(explicitConversion);
		} else {
			return compatibilityCheckWithNumberedCard(cardFromPlayer);
		}
	}

	private boolean compatibilityCheckWithSpecialCard(CardSpecial specialCardFromPlayer) {
		Preconditions.checkArgument(specialCardFromPlayer.isSpecial(),"[ERROR] Provided card is not special");
		if(globalComparaisonIsEnough(specialCardFromPlayer) || specialCardComparaisonIsEnough(specialCardFromPlayer)) {
			return true;
		}
		return false;
	}

	private boolean compatibilityCheckWithNumberedCard(Card cardFromPlayer) {
		Preconditions.checkArgument(!cardFromPlayer.isSpecial(),"[ERROR] Provided card is not special");
		if(globalComparaisonIsEnough(cardFromPlayer) || numberedCardComparaisonIsEnough(cardFromPlayer) ) {
			return true;
		}
		return false;
	}


	/* ========================================= COMPARAISON - UTILS ========================================= */

	private boolean numberedCardComparaisonIsEnough(Card currentCard) {
		if(getLastCardPlayed().isCompatibleWith(currentCard)) {
			return true;
		}
		return false;
	}
	
	private boolean specialCardComparaisonIsEnough(CardSpecial currentCard) {
		if(getLastCardPlayed().isCompatibleWith(currentCard)) {
			return true;
		}
		return false;
	}

	private boolean globalComparaisonIsEnough(Card currentCard) {
		if(globalColorIsSet()) {
			if(currentCard.getCouleur().equals(this.globalColor)) {
				System.out.println("--Global color matched : ["+this.globalColor+"] .vs. ["+currentCard.getCouleur()+"]");
				return true;
			}
		}
		return false;
	}

	/* ========================================= GETTERS ========================================= */

	public boolean globalColorIsSet() {
		return ! this.globalColor.equals(Color.JOKER);
	}

	public Card getLastCardPlayed() {
		return this.lastCardPlayed;
	}

	/* ========================================= DISPLAY ========================================= */

	public void appendGlobalColorIfItIsSet() {
		if(globalColorIsSet()) {
			this.consoleView.appendBoldText("* And global color is set to ");
			displayGlobalColorTextWithAppropriateColor();
			this.consoleView.insertBlankLine();
		}
	}

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