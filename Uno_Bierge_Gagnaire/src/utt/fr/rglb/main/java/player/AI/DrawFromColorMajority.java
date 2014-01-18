package utt.fr.rglb.main.java.player.AI;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;

/**
 * Classe correspondant à l'implémentation d'une stratégie
 */
public class DrawFromColorMajority extends CardPickerStrategyImpl {
	private static final long serialVersionUID = 1L;

	/**
	 * Méthode permettant d'obtenir l'index de la carte la plus appropriée, basé sur le type de stratégie
	 * </br>La carte choisie sera la 1ère carte jouable dont la couleur est celle majoritaire dans la main du joueur
	 */
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
	
	/**
	 * Méthode permettant à l'IA de déterminer si accuser le joueur précédent de bluffer est une bonne idée ou pas
	 * </br>La décision d'accuser le joueur précédent est faite de la manière suivante
	 * </br> - Si l'IA a moins de 4 cartes, il essayera toujours de vérifier
	 * </br> - Si l'IA a plus de 4 cartes ou plus, la décision est aléatoire
	 */
	@Override
	public boolean chooseIfAccusingPreviousPlayerOfBluffingIsWorthIt(Collection<Card> cardCollection) {
		if(cardCollection.size() < 4) {
			return true;
		}
		Random a = new Random();
		return a.nextBoolean();
	}
	
	@Override
	public String toString() {
		return "Normal";
	}
}
