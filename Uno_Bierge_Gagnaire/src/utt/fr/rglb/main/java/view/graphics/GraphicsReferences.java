package utt.fr.rglb.main.java.view.graphics;

import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;

public class GraphicsReferences {
	private int indexFromActivePlayer;
	private CardsModelBean cardReferences;
	private boolean hasDrawnOneTime;
	private boolean hasDrawnTwoTimes;
	private Card firstCardDrawn;
	private Card secondCardDrawn;

	public GraphicsReferences(CardsModelBean cardReferences, int indexFromActivePlayer) {
		this.cardReferences = cardReferences;
		this.indexFromActivePlayer = indexFromActivePlayer;
		this.hasDrawnOneTime = false;
		this.hasDrawnTwoTimes = false;
	}

	public void setNeedOfDrawingOneTime(Card firstCardDrawn) {
		this.hasDrawnOneTime = true;
		this.firstCardDrawn = firstCardDrawn;
	}
	
	public void setNeedOfDrawingTwoTimes(Card firstCardDrawn, Card secondCardDrawn) {
		this.hasDrawnOneTime = true;
		this.hasDrawnTwoTimes = true;
		this.firstCardDrawn = firstCardDrawn;
		this.secondCardDrawn = secondCardDrawn;
	}

	public int getIndexFromActivePlayer() {
		return indexFromActivePlayer;
	}

	public boolean hasDrawnOneTime() {
		return this.hasDrawnOneTime;
	}

	public Card getFirstCardDrawn() {
		return this.firstCardDrawn;
	}

	public boolean getCompatibilityFromFirstCard() {
		return this.cardReferences.isCompatibleWith(this.firstCardDrawn);
	}
	
	public boolean hasDrawnTwoTimes() {
		return this.hasDrawnTwoTimes;
	}

	public boolean getCompatibilityWith(Card cardDrawn) {
		return this.cardReferences.isCompatibleWith(cardDrawn);
	}

	public Card getSecondCardDrawn() {
		return this.secondCardDrawn;
	}

	public boolean hasNoPlayableCards() {
		return !this.cardReferences.isCompatibleWith(this.secondCardDrawn);
	}
}
