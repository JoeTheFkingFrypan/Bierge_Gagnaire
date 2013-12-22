package utt.fr.rglb.main.java.player.AI;

import com.google.common.base.Preconditions;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import utt.fr.rglb.main.java.cards.model.basics.Card;

/**
 * Classe correspondant à l'implémentation d'une stratégie
 * 
 * </br>La décision d'accuser le joueur précédent est faite aléatoirement
 */
public class DrawFirstCard extends CardPickerStrategyImpl {
	private static final long serialVersionUID = 1L;

	/**
	 * Méthode permettant d'obtenir l'index de la carte la plus appropriée, basé sur le type de stratégie
	 * </br>La carte choisie sera la 1ère carte jouable dans la main du joueur
	 */
	@Override
	public int findBestCardToPlay(Collection<Integer> playableIndexes, Collection<Card> cardCollection) {
		Preconditions.checkState(playableIndexes.size() > 0,"[ERROR] Cannot find best card to play : no available indexes");
		Iterator<Integer> iterator = playableIndexes.iterator();
		return iterator.next();
	}

	/**
	 * Méthode permettant à l'IA de déterminer si accuser le joueur précédent de bluffer est une bonne idée ou pas
	 * </br>La décision d'accuser le joueur précédent est faite aléatoirement
	 */
	@Override
	public boolean chooseIfAccusingPreviousPlayerOfBluffingIsWorthIt(Collection<Card> cardCollection) {
		Random a = new Random();
		return a.nextBoolean();
	}
}
