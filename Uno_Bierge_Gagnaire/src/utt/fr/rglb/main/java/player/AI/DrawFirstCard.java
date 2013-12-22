package utt.fr.rglb.main.java.player.AI;

import com.google.common.base.Preconditions;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import utt.fr.rglb.main.java.cards.model.basics.Card;

/**
 * Classe correspondant � l'impl�mentation d'une strat�gie
 * 
 * </br>La d�cision d'accuser le joueur pr�c�dent est faite al�atoirement
 */
public class DrawFirstCard extends CardPickerStrategyImpl {
	private static final long serialVersionUID = 1L;

	/**
	 * M�thode permettant d'obtenir l'index de la carte la plus appropri�e, bas� sur le type de strat�gie
	 * </br>La carte choisie sera la 1�re carte jouable dans la main du joueur
	 */
	@Override
	public int findBestCardToPlay(Collection<Integer> playableIndexes, Collection<Card> cardCollection) {
		Preconditions.checkState(playableIndexes.size() > 0,"[ERROR] Cannot find best card to play : no available indexes");
		Iterator<Integer> iterator = playableIndexes.iterator();
		return iterator.next();
	}

	/**
	 * M�thode permettant � l'IA de d�terminer si accuser le joueur pr�c�dent de bluffer est une bonne id�e ou pas
	 * </br>La d�cision d'accuser le joueur pr�c�dent est faite al�atoirement
	 */
	@Override
	public boolean chooseIfAccusingPreviousPlayerOfBluffingIsWorthIt(Collection<Card> cardCollection) {
		Random a = new Random();
		return a.nextBoolean();
	}
}
