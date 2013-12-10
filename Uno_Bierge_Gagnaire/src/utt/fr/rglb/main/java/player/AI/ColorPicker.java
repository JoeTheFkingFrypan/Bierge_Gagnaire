package utt.fr.rglb.main.java.player.AI;

import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;

public class ColorPicker {
	private int amountOfRedCards;
	private int amountOfBlueCards;
	private int amountOfGreenCards;
	private int amountOfYellowCards;

	public ColorPicker(Collection<Card> cardCollection) {
		this.amountOfRedCards = 0;
		this.amountOfBlueCards = 0;
		this.amountOfGreenCards = 0;
		this.amountOfYellowCards = 0;
		countCards(cardCollection);
	}

	private void countCards(Collection<Card> cardCollection) {
		for(Card currentCard : cardCollection) {
			if(currentCard.isRed()) {
				amountOfRedCards++;
			} else if(currentCard.isBlue()) {
				amountOfBlueCards++;
			} else if(currentCard.isGreen()) {
				amountOfGreenCards++;
			} else if(currentCard.isYellow()) {
				amountOfYellowCards++;
			}
		}
	}
	
	public Color findBestSuitableColor() {
		if(redCardMajority()) {
			return Color.RED;
		} else if(blueCardMajority()) {
			return Color.BLUE;
		} else if(greenCardMajority()) {
			return Color.GREEN;
		} else {
			return Color.YELLOW;
		}
	}
	
	private boolean redCardMajority() {
		return this.amountOfRedCards >= this.amountOfBlueCards && this.amountOfRedCards >= this.amountOfGreenCards && this.amountOfRedCards >= this.amountOfYellowCards;
	}
	
	private boolean blueCardMajority() {
		return this.amountOfBlueCards >= this.amountOfGreenCards && this.amountOfBlueCards >= this.amountOfYellowCards;
	}
	
	private boolean greenCardMajority() {
		return this.amountOfGreenCards >= this.amountOfYellowCards;
	}
}
