package utt.fr.rglb.main.java.player.AI;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import utt.fr.rglb.main.java.cards.model.basics.Card;

/**
 * Classe correspondant � l'impl�mentation d'une strat�gie
 */
public class DrawMostValuableCard extends CardPickerStrategyImpl {
	private static final long serialVersionUID = 1L;

	/**
	 * M�thode permettant d'obtenir l'index de la carte la plus appropri�e, bas� sur le type de strat�gie
	 * </br>La carte choisie sera la carte jouable ayant la plus haute valeur en points
	 */
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
	
	/**
	 * M�thode permettant � l'IA de d�terminer si accuser le joueur pr�c�dent de bluffer est une bonne id�e ou pas
	 * </br>La d�cision d'accuser le joueur pr�c�dent est faite de la mani�re suivante
	 * </br> - Si l'IA a moins de 6 cartes, il essayera toujours de v�rifier
	 * </br> - Si l'IA a plus de 6 cartes ou plus, la d�cision est al�atoire
	 */
	@Override
	public boolean chooseIfAccusingPreviousPlayerOfBluffingIsWorthIt(Collection<Card> cardCollection) {
		if(cardCollection.size() < 6) {
			return true;
		}
		Random a = new Random();
		return a.nextBoolean();
	}
}
