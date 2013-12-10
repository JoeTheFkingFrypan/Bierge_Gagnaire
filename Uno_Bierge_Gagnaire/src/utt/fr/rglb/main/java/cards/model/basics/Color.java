package utt.fr.rglb.main.java.cards.model.basics;

/**
 * Enumération contenant les différentes valeurs de couleur de cartes
 * @see [JOKER] Couleur joker (sans couleur)
 * @see [YELLOW] Couleur jaune
 * @see [GREEN] Couleur verte
 * @see [BLUE] Couleur bleue
 * @see [RED] Couleur rouge
 */
public enum Color {
	RED,
	BLUE,
	GREEN,
	YELLOW,
	JOKER;
	
	public final int compateTo(Color otherColor) {
		System.out.println("[" + this + "]" + this.ordinal() + " .vs. [" + otherColor + "] " + otherColor.ordinal());
		return this.ordinal() - otherColor.ordinal();
	}
}
