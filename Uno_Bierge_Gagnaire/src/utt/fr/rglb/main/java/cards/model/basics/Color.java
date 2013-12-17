package utt.fr.rglb.main.java.cards.model.basics;

import com.google.common.base.Preconditions;

/**
 * Enumération contenant les différentes valeurs de couleur de cartes
 */
public enum Color {
	RED,		//Rouge
	BLUE,		//Bleu
	GREEN,		//Vert
	YELLOW,		//Jaune
	JOKER;		//Joker (sans couleur)
	
	/**
	 * Méthode permettant de comparer les couleurs entre elles (la comparaison est effectuée par rapport à l'ordre dans lequel elles apparaissent dans l'énumération
	 */
	public final int compateTo(Color otherColor) {
		Preconditions.checkNotNull(otherColor,"[ERROR] Cannot compare colors : provided one is null");
		return this.ordinal() - otherColor.ordinal();
	}
}
