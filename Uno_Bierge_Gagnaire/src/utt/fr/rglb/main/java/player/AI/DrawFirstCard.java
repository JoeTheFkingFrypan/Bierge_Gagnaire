package utt.fr.rglb.main.java.player.AI;

import java.util.Collection;
import java.util.Iterator;

import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.cards.model.basics.Card;

public class DrawFirstCard extends CardPickerStrategyImpl {
	@Override
	public int findBestCardToPlay(Collection<Integer> playableIndexes, Collection<Card> cardCollection) {
		Preconditions.checkState(playableIndexes.size() > 0,"[ERROR] Cannot find best card to play : no available indexes");
		Iterator<Integer> iterator = playableIndexes.iterator();
		return iterator.next();
	}
}
