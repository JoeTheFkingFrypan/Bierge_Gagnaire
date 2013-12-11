package utt.fr.rglb.main.java.cards.model.basics;

import java.util.Comparator;

public class CardSorter implements Comparator<Card> {
	public int compare(Card firstCard, Card secondCard){
		if(!firstCard.getCouleur().equals(secondCard.getCouleur())) {
			Color colorFromFirstCard = firstCard.getCouleur();
			Color colorFromSecondCard = secondCard.getCouleur();
			return colorFromFirstCard.compareTo(colorFromSecondCard);
		} else if(!firstCard.getValeur().equals(secondCard.getValeur())) {
			Integer valueFromFirstCard = firstCard.getValeur();
			Integer valueFromSecondCard = secondCard.getValeur();
			return valueFromFirstCard.compareTo(valueFromSecondCard);
		} else {
			return 0;
		}
	}
}
