package utt.fr.rglb.main.java.cards.model.basics;

import java.io.Serializable;

import com.google.common.base.Preconditions;

/**
 * Classe mère correspondant à une carte de jeu (valeur, numéro, couleur)
 * Définit les comportements communs à toutes les cartes
 */
public class Card implements Serializable {
	private static final long serialVersionUID = 1L;
	protected final int value;
	protected final Color color;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur de carte
	 * @param value Numéro de la carte (doit être supérieure à 0 dans tous les cas, et inférieur à 9 s'il s'agit d'une carte numérotée)
	 * @param color Couleur de la carte (doit être différent de Joker s'il s'agit d'une carte numérotée)
	 */
	public Card (int value, Color color) {
		Preconditions.checkNotNull(color,"[ERROR] Color cannot be null");
		Preconditions.checkArgument(value >= 0,"[ERROR] Invalid card number (expected > 0, was : " + value + ")");
		if(!isSpecial()) {
			Preconditions.checkArgument(value <= 9,"[ERROR] Invalid card number (expected 0-9, was : " + value + ")");
			Preconditions.checkArgument(!color.equals(Color.JOKER),"[ERROR] Invalid card color (expected {ROUGE, BLEUE, VERTE, JAUNE} was : " + color + ")");
		}
		this.value = value;
		this.color = color;
	}

	/* ========================================= ADVANCED COMPARAISON ========================================= */

	/**
	 * Méthode permettant de savoir si une carte peut être jouée par dessus la carte actuelle
	 * @param otherCard Carte que l'on souhaite eventuellement jouer
	 * @return <code>TRUE</code> si la carte est "compatible" (si elle peut être jouée), <code>false</code> sinon
	 */
	public boolean isCompatibleWith(Card otherCard) {
		if(this.hasSameValueThan(otherCard.getValue())) {
			return true;
		} else if(this.hasSameColorThan(otherCard.getColor())) {
			return true;
		} else if(otherCard.isJoker()){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Méthode protégée permettant de savoir si la couleur de la carte actuelle est la même que la couleur passée en paramètre
	 * @param colorFromAnotherCard Couleur d'une 2ème carte, passée en paramètre
	 * @return <code>TRUE</code> si les 2 couleurs sont identiques, <code>false</code> sinon
	 */
	protected boolean hasSameColorThan(Color colorFromAnotherCard) {
		Preconditions.checkNotNull(colorFromAnotherCard,"[ERROR] Cannot verify if both have same color : provided color is null");
		return this.getColor().equals(colorFromAnotherCard);
	}

	/**
	 * Méthode protégée permettant de savoir si la valeur de la carte actuelle est la même que la valeur passée en paramètre
	 * @param valueFromAnotherCard Valeur d'une 2ème carte, passée en paramètre
	 * @return <code>TRUE</code> si les 2 valeurs sont identiques, <code>false</code> sinon
	 */
	protected boolean hasSameValueThan(int valueFromAnotherCard) {
		return this.getValue().equals(valueFromAnotherCard);
	}

	/* ========================================= BASIC COMPARAISON ========================================= */

	/**
	 * Méthode définissant les critères d'égalité entre deux cartes
	 */
	@Override
	public boolean equals(Object other) {
		boolean isNumberedCard = other.getClass().equals(Card.class);
		if(!isNumberedCard) {
			return false;
		} else {
			Card otherCard = (Card)other;
			boolean sameColor = hasSameColorThan(otherCard.getColor());
			boolean sameValue = hasSameValueThan(otherCard.getValue());
			return sameColor && sameValue;
		}
	}

	/* ========================================= GETTERS ========================================= */

	/**
	 * Méthode permettant de vérifier si une carte est spéciale ou non
	 * @return <code>TRUE</code> s'il s'agit d'une CarteSpeciale, <code>false</code> sinon
	 */
	public Boolean isSpecial() {
		return false;
	}

	/**
	 * Méthode permettant de récuperer la valeur d'une carte
	 * @return La valeur de la carte (dans le cas d'une carte numérotée, il s'agit aussi de son numéro)
	 */
	public Integer getValue() {
		return this.value;
	}

	/**
	 * Méthode permettant de récuperer la couleur d'une carte
	 * @return La couleur de la carte
	 */
	public Color getColor () {
		return this.color;
	}

	/**
	 * Méthode permettant déterminer facilement si une carte est de couleur rouge
	 * @return Renvoit <code>TRUE</code> si la carte est rouge, <code>false</code> sinon
	 */
	public boolean isRed() {
		return this.color.equals(Color.RED);
	}

	/**
	 * Méthode permettant déterminer facilement si une carte est de couleur bleue
	 * @return Renvoit <code>TRUE</code> si la carte est bleue, <code>false</code> sinon
	 */
	public boolean isBlue() {
		return this.color.equals(Color.BLUE);
	}
	
	/**
	 * Méthode permettant déterminer facilement si une carte est de couleur verte
	 * @return Renvoit <code>TRUE</code> si la carte est verte, <code>false</code> sinon
	 */
	public boolean isGreen() {
		return this.color.equals(Color.GREEN);
	}
	
	/**
	 * Méthode permettant déterminer facilement si une carte est de couleur jaune
	 * @return Renvoit <code>TRUE</code> si la carte est jaune, <code>false</code> sinon
	 */
	public boolean isYellow() {
		return this.color.equals(Color.YELLOW);
	}
	
	/**
	 * Méthode permettant déterminer facilement si une carte est sans couleur (joker)
	 * @return Renvoit <code>TRUE</code> si la carte est sans couleur (joker), <code>false</code> sinon
	 */
	public boolean isJoker() {
		return this.color.equals(Color.JOKER);
	}

	/* ========================================= DISPLAY ========================================= */

	/**
	 * Méthode permettant spécifiant la façon dont s'affiche une carte
	 */
	@Override
	public String toString() {
		return "[CARTE NUMEROTEE] Numero=" + this.getValue() + ", Couleur=" + this.getColor();
	}
}