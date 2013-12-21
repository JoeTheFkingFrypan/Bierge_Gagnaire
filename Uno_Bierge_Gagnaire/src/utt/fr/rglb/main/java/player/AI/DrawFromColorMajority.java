package utt.fr.rglb.main.java.player.AI;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;

/**
 * Classe correspondant à l'implémentation d'une stratégie
 * </br>La carte choisie sera la 1ère carte jouable dont la couleur est celle majoritaire dans la main du joueur
 */
public class DrawFromColorMajority extends CardPickerStrategyImpl {
	private static final long serialVersionUID = 1L;

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
			if(playableCard.getValue() > highestValue && playableCard.getColor().equals(bestColor)) {
				highestValue = playableCard.getValue();
				bestCardIndex = index;
			}
		}
		return bestCardIndex;
	}
}
