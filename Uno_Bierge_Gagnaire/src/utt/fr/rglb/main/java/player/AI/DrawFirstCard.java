package utt.fr.rglb.main.java.player.AI;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.Iterator;

import utt.fr.rglb.main.java.cards.model.basics.Card;

/**
 * Classe correspondant � l'impl�mentation d'une strat�gie
 * La carte choisie sera la 1�re carte jouable dans la main du joueur
 */
public class DrawFirstCard extends CardPickerStrategyImpl {
	private static final long serialVersionUID = 1L;

	@Override
	public int findBestCardToPlay(Collection<Integer> playableIndexes, Collection<Card> cardCollection) {
		Preconditions.checkState(playableIndexes.size() > 0,"[ERROR] Cannot find best card to play : no available indexes");
		Iterator<Integer> iterator = playableIndexes.iterator();
		return iterator.next();
	}
}
