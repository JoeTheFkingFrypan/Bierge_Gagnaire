package main.java.player.IA;

import java.util.ArrayList;

import main.java.cards.model.basics.Card;

public class DrawMostValuableCard extends CardPickerStrategyImpl {
	@Override
	protected int findBestCardToPlay(ArrayList<Card> cards) {
		int currentPosition = 0;
		int bestCardIndex = 0;
		int highestValue = -1;
		for(Card c : cards) {
			if(c.getValeur() > highestValue) {
				highestValue = c.getValeur();
				bestCardIndex = currentPosition;
			}
			currentPosition++;
		}
		return bestCardIndex;
	}
}
