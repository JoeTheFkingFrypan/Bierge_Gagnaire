package utt.fr.rglb.main.java.player.AI;

import java.util.Collection;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;

/**
 * Interface définissant les comportements communs à toutes les stratégies
 */
public interface CardPickerStrategy {
	
	/**
	 * Méthode permettant à l'IA de déterminer quelle est la meilleure carte à jouer, parmis celles jouables
	 * @param playableIndexes Index des cartes jouables
	 * @param cardCollection Cartes en main
	 * @return int correspondant à l'index de la carte choisie
	 */
	public int chooseCardFrom(Collection<Integer> playableIndexes, Collection<Card> cardCollection);
	
	/**
	 * Méthode permettant à l'IA de déterminer quelle est la meilleure couleur à choisir lors du jeu d'un JOKER
	 * @param cardCollection Cartes en main
	 * @return int correspondant à l'index de la couleur choisie
	 */
	public Color chooseBestColor(Collection<Card> cardCollection);
}
