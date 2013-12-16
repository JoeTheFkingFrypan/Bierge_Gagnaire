package utt.fr.rglb.main.java.cards.model.basics;

import java.io.Serializable;

import com.google.common.base.Preconditions;

/**
 * Classe m�re correspondant � une carte de jeu (valeur, num�ro, couleur)
 * D�finit les comportements communs � toutes les cartes
 */
public class Card implements Serializable {
	private static final long serialVersionUID = 1L;
	protected final int value;
	protected final Color color;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur de carte
	 * @param value Num�ro de la carte (doit �tre sup�rieure � 0 dans tous les cas, et inf�rieur � 9 s'il s'agit d'une carte num�rot�e)
	 * @param color Couleur de la carte (doit �tre diff�rent de Joker s'il s'agit d'une carte num�rot�e)
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
	 * M�thode permettant de savoir si une carte peut �tre jou�e par dessus la carte actuelle
	 * @param otherCard Carte que l'on souhaite eventuellement jouer
	 * @return <code>TRUE</code> si la carte est "compatible" (si elle peut �tre jou�e), <code>false</code> sinon
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
	 * M�thode prot�g�e permettant de savoir si la couleur de la carte actuelle est la m�me que la couleur pass�e en param�tre
	 * @param colorFromAnotherCard Couleur d'une 2�me carte, pass�e en param�tre
	 * @return <code>TRUE</code> si les 2 couleurs sont identiques, <code>false</code> sinon
	 */
	protected boolean hasSameColorThan(Color colorFromAnotherCard) {
		Preconditions.checkNotNull(colorFromAnotherCard,"[ERROR] Cannot verify if both have same color : provided color is null");
		return this.getColor().equals(colorFromAnotherCard);
	}

	/**
	 * M�thode prot�g�e permettant de savoir si la valeur de la carte actuelle est la m�me que la valeur pass�e en param�tre
	 * @param valueFromAnotherCard Valeur d'une 2�me carte, pass�e en param�tre
	 * @return <code>TRUE</code> si les 2 valeurs sont identiques, <code>false</code> sinon
	 */
	protected boolean hasSameValueThan(int valueFromAnotherCard) {
		return this.getValue().equals(valueFromAnotherCard);
	}

	/* ========================================= BASIC COMPARAISON ========================================= */

	/**
	 * M�thode d�finissant les crit�res d'�galit� entre deux cartes
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
	 * M�thode permettant de v�rifier si une carte est sp�ciale ou non
	 * @return <code>TRUE</code> s'il s'agit d'une CarteSpeciale, <code>false</code> sinon
	 */
	public Boolean isSpecial() {
		return false;
	}

	/**
	 * M�thode permettant de r�cuperer la valeur d'une carte
	 * @return La valeur de la carte (dans le cas d'une carte num�rot�e, il s'agit aussi de son num�ro)
	 */
	public Integer getValue() {
		return this.value;
	}

	/**
	 * M�thode permettant de r�cuperer la couleur d'une carte
	 * @return La couleur de la carte
	 */
	public Color getColor () {
		return this.color;
	}

	/**
	 * M�thode permettant d�terminer facilement si une carte est de couleur rouge
	 * @return Renvoit <code>TRUE</code> si la carte est rouge, <code>false</code> sinon
	 */
	public boolean isRed() {
		return this.color.equals(Color.RED);
	}

	/**
	 * M�thode permettant d�terminer facilement si une carte est de couleur bleue
	 * @return Renvoit <code>TRUE</code> si la carte est bleue, <code>false</code> sinon
	 */
	public boolean isBlue() {
		return this.color.equals(Color.BLUE);
	}
	
	/**
	 * M�thode permettant d�terminer facilement si une carte est de couleur verte
	 * @return Renvoit <code>TRUE</code> si la carte est verte, <code>false</code> sinon
	 */
	public boolean isGreen() {
		return this.color.equals(Color.GREEN);
	}
	
	/**
	 * M�thode permettant d�terminer facilement si une carte est de couleur jaune
	 * @return Renvoit <code>TRUE</code> si la carte est jaune, <code>false</code> sinon
	 */
	public boolean isYellow() {
		return this.color.equals(Color.YELLOW);
	}
	
	/**
	 * M�thode permettant d�terminer facilement si une carte est sans couleur (joker)
	 * @return Renvoit <code>TRUE</code> si la carte est sans couleur (joker), <code>false</code> sinon
	 */
	public boolean isJoker() {
		return this.color.equals(Color.JOKER);
	}

	/* ========================================= DISPLAY ========================================= */

	/**
	 * M�thode permettant sp�cifiant la fa�on dont s'affiche une carte
	 */
	@Override
	public String toString() {
		return "[CARTE NUMEROTEE] Numero=" + this.getValue() + ", Couleur=" + this.getColor();
	}
}