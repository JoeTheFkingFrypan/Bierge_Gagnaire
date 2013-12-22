package utt.fr.rglb.main.java.player.AI;

import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;

/**
 * Interface d�finissant les comportements communs � toutes les strat�gies
 */
public interface CardPickerStrategy {
	
	/**
	 * M�thode permettant � l'IA de d�terminer quelle est la meilleure carte � jouer, parmis celles jouables
	 * @param playableIndexes Index des cartes jouables
	 * @param cardCollection Cartes en main
	 * @return int correspondant � l'index de la carte choisie
	 */
	public int chooseCardFrom(Collection<Integer> playableIndexes, Collection<Card> cardCollection);
	
	/**
	 * M�thode permettant � l'IA de d�terminer quelle est la meilleure couleur � choisir lors du jeu d'un JOKER
	 * @param cardCollection Cartes en main
	 * @return int correspondant � l'index de la couleur choisie
	 */
	public Color chooseBestColor(Collection<Card> cardCollection);

	/**
	 * M�thode permettant � l'IA de d�terminer si accuser le joueur pr�c�dent de bluffer est une bonne id�e ou pas
	 * @param cardCollection Cartes en main
	 * @return <code>TRUE</code> si l'IA d�cide d'accuser le joueur pr�c�dent, <code>FALSE</code> sinon
	 */
	public boolean chooseIfAccusingPreviousPlayerOfBluffingIsWorthIt(Collection<Card> cardCollection);
}
