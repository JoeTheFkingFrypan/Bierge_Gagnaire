package utt.fr.rglb.main.java.player.AI;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.basics.Card;

/**
 * Classe correspondant à l'implémentation d'une stratégie
 * La carte choisie sera la carte jouable ayant la plus haute valeur en points
 */
public class DrawMostValuableCard extends CardPickerStrategyImpl {
	@Override
	public int findBestCardToPlay(Collection<Integer> playableIndexes, Collection<Card> cardCollection) {
		Preconditions.checkState(playableIndexes.size() > 0,"[ERROR] Cannot find best card to play : no available indexes");
		ArrayList<Card> cards = new ArrayList<Card>(cardCollection);
		int bestCardIndex = 0;
		int highestValue = -1;
		for(Integer index : playableIndexes) {
			Card playableCard = cards.get(index);
			if(playableCard.getValue() > highestValue) {
				highestValue = playableCard.getValue();
				bestCardIndex = index;
			}
		}
		return bestCardIndex;
	}
}
