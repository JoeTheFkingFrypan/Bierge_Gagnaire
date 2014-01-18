package utt.fr.rglb.main.java.cards.model.basics;

import java.io.Serializable;
import java.util.Comparator;

import com.google.common.base.Preconditions;

/**
 * Classe permettant d'ordonner les cartes
 */
public class CardSorter implements Comparator<Card>, Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Méthode permettant de comparer 2 cartes afin de pouvoir les trier au sein d'une collection
	 * Tri par couleur puis par valeur/numéro
	 */
	public int compare(Card firstCard, Card secondCard){
		Preconditions.checkNotNull(firstCard,"[ERROR] Cannot compare cards : the first one provided is null");
		Preconditions.checkNotNull(secondCard,"[ERROR] Cannot compare cards : the second one provided is null");
		if(!firstCard.getColor().equals(secondCard.getColor())) {
			Color colorFromFirstCard = firstCard.getColor();
			Color colorFromSecondCard = secondCard.getColor();
			return colorFromFirstCard.compareTo(colorFromSecondCard);
		} else if(!firstCard.getValue().equals(secondCard.getValue())) {
			Integer valueFromFirstCard = firstCard.getValue();
			Integer valueFromSecondCard = secondCard.getValue();
			return valueFromFirstCard.compareTo(valueFromSecondCard);
		} else {
			return 0;
		}
	}
}
