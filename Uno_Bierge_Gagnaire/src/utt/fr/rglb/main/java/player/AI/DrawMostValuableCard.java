package utt.fr.rglb.main.java.player.AI;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import utt.fr.rglb.main.java.cards.model.basics.Card;

/**
 * Classe correspondant à l'implémentation d'une stratégie
 */
public class DrawMostValuableCard extends CardPickerStrategyImpl {
	private static final long serialVersionUID = 1L;

	/**
	 * Méthode permettant d'obtenir l'index de la carte la plus appropriée, basé sur le type de stratégie
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
	 * Méthode permettant à l'IA de déterminer si accuser le joueur précédent de bluffer est une bonne idée ou pas
	 * </br>La décision d'accuser le joueur précédent est faite de la manière suivante
	 * </br> - Si l'IA a moins de 6 cartes, il essayera toujours de vérifier
	 * </br> - Si l'IA a plus de 6 cartes ou plus, la décision est aléatoire
	 */
	@Override
	public boolean chooseIfAccusingPreviousPlayerOfBluffingIsWorthIt(Collection<Card> cardCollection) {
		if(cardCollection.size() < 6) {
			return true;
		}
		Random a = new Random();
		return a.nextBoolean();
	}
	
	@Override
	public String toString() {
		return "Strong";
	}
}
