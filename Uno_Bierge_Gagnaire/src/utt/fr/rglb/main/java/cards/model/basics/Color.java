package utt.fr.rglb.main.java.cards.model.basics;

import com.google.common.base.Preconditions;

/**
 * Enum�ration contenant les diff�rentes valeurs de couleur de cartes
 */
public enum Color {
	RED,		//Rouge
	BLUE,		//Bleu
	GREEN,		//Vert
	YELLOW,		//Jaune
	JOKER;		//Joker (sans couleur)
	
	/**
	 * M�thode permettant de comparer les couleurs entre elles (la comparaison est effectu�e par rapport � l'ordre dans lequel elles apparaissent dans l'�num�ration
	 */
	public final int compateTo(Color otherColor) {
		Preconditions.checkNotNull(otherColor,"[ERROR] Cannot compare colors : provided one is null");
		return this.ordinal() - otherColor.ordinal();
	}
}
