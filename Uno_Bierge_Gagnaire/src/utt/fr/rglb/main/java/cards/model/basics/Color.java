package utt.fr.rglb.main.java.cards.model.basics;

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
		System.out.println("[" + this + "]" + this.ordinal() + " .vs. [" + otherColor + "] " + otherColor.ordinal());
		return this.ordinal() - otherColor.ordinal();
	}
}
