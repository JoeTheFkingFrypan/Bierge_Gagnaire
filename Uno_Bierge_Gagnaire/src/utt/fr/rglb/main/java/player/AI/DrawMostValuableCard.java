package utt.fr.rglb.main.java.player.AI;

import java.util.ArrayList;
import java.util.Collection;
import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.cards.model.basics.Card;

public class DrawMostValuableCard extends CardPickerStrategyImpl {
	@Override
	public int findBestCardToPlay(Collection<Integer> playableIndexes, Collection<Card> cardCollection) {
		Preconditions.checkState(playableIndexes.size() > 0,"[ERROR] Cannot find best card to play : no available indexes");
		ArrayList<Card> cards = new ArrayList<Card>(cardCollection);
		int bestCardIndex = 0;
		int highestValue = -1;
		for(Integer index : playableIndexes) {
			Card playableCard = cards.get(index);
			if(playableCard.getValeur() > highestValue) {
				highestValue = playableCard.getValeur();
				bestCardIndex = index;
			}
		}
		return bestCardIndex;
	}
}
