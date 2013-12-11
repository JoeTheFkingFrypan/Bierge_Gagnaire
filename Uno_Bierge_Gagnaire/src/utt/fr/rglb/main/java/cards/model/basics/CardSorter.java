package utt.fr.rglb.main.java.cards.model.basics;

import java.util.Comparator;

/**
 * Classe permettant d'ordonner les cartes
 */
public class CardSorter implements Comparator<Card> {
	/**
	 * Méthode permettant de comparer 2 cartes afin de pouvoir les trier au sein d'une collection
	 * Tri par couleur puis par valeur/numéro
	 */
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
