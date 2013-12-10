package utt.fr.rglb.main.java.player.AI;

import java.util.ArrayList;
import java.util.Collection;

import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;

public class DrawFromColorMajority extends CardPickerStrategyImpl {
	@Override
	protected int findBestCardToPlay(Collection<Integer> playableIndexes, Collection<Card> cardCollection) {
		Preconditions.checkState(playableIndexes.size() > 0,"[ERROR] Cannot find best card to play : no available indexes");
		ColorPicker colorPicker = new ColorPicker(cardCollection);
		Color bestColor = colorPicker.findBestSuitableColor();
		ArrayList<Card> cards = new ArrayList<Card>(cardCollection);
		int bestCardIndex = 0;
		int highestValue = -1;
		for(Integer index : playableIndexes) {
			Card playableCard = cards.get(index);
			if(playableCard.getValeur() > highestValue && playableCard.getCouleur().equals(bestColor)) {
				highestValue = playableCard.getValeur();
				bestCardIndex = index;
			}
		}
		return bestCardIndex;
	}
}
