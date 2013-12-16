package utt.fr.rglb.main.java.cards.model.basics;

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
		System.out.println("[" + this + "]" + this.ordinal() + " .vs. [" + otherColor + "] " + otherColor.ordinal());
		return this.ordinal() - otherColor.ordinal();
	}
}
