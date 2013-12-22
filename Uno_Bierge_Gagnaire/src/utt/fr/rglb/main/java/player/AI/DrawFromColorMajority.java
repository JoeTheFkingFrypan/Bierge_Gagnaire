package utt.fr.rglb.main.java.player.AI;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;

/**
 * Classe correspondant � l'impl�mentation d'une strat�gie
 */
public class DrawFromColorMajority extends CardPickerStrategyImpl {
	private static final long serialVersionUID = 1L;

	/**
	 * M�thode permettant d'obtenir l'index de la carte la plus appropri�e, bas� sur le type de strat�gie
	 * </br>La carte choisie sera la 1�re carte jouable dont la couleur est celle majoritaire dans la main du joueur
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
	 * M�thode permettant � l'IA de d�terminer si accuser le joueur pr�c�dent de bluffer est une bonne id�e ou pas
	 * </br>La d�cision d'accuser le joueur pr�c�dent est faite de la mani�re suivante
	 * </br> - Si l'IA a moins de 4 cartes, il essayera toujours de v�rifier
	 * </br> - Si l'IA a plus de 4 cartes ou plus, la d�cision est al�atoire
	 */
	@Override
	public boolean chooseIfAccusingPreviousPlayerOfBluffingIsWorthIt(Collection<Card> cardCollection) {
		if(cardCollection.size() < 4) {
			return true;
		}
		Random a = new Random();
		return a.nextBoolean();
	}
}
