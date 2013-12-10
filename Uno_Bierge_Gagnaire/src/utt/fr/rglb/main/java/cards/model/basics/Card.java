package utt.fr.rglb.main.java.cards.model.basics;

import com.google.common.base.Preconditions;

/**
 * Classe m�re correspondant � une carte de jeu (valeur, num�ro, couleur)
 * D�finit les comportements communs � toutes les cartes
 */
public class Card {
	protected final int value;
	protected final Color color;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur de carte
	 * @param valeur Num�ro de la carte (doit �tre sup�rieure � 0 dans tous les cas, et inf�rieur � 9 s'il s'agit d'une carte num�rot�e)
	 * @param couleur Couleur de la carte (doit �tre diff�rent de Joker s'il s'agit d'une carte num�rot�e)
	 */
	public Card (int valeur, Color couleur) {
		Preconditions.checkNotNull(couleur,"[ERROR] Color cannot be null");
		Preconditions.checkArgument(valeur >= 0,"[ERROR] Invalid card number (expected > 0, was : " + valeur + ")");
		if(!isSpecial()) {
			Preconditions.checkArgument(valeur <= 9,"[ERROR] Invalid card number (expected 0-9, was : " + valeur + ")");
			Preconditions.checkArgument(!couleur.equals(Color.JOKER),"[ERROR] Invalid card color (expected {ROUGE, BLEUE, VERTE, JAUNE} was : " + couleur + ")");
		}
		this.value = valeur;
		this.color = couleur;
	}

	/* ========================================= ADVANCED COMPARAISON ========================================= */

	/**
	 * M�thode permettant de savoir si une carte peut �tre jou�e par dessus la carte actuelle
	 * @param otherCard Carte que l'on souhaite eventuellement jouer
	 * @return TRUE si la carte est "compatible" (si elle peut �tre jou�e), FALSE sinon
	 */
	public boolean isCompatibleWith(Card otherCard) {
		if(this.hasSameValueThan(otherCard.getValeur())) {
			return true;
		} else if(this.hasSameColorThan(otherCard.getCouleur())) {
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
	 * @return TRUE si les 2 couleurs sont identiques, FALSE sinon
	 */
	protected boolean hasSameColorThan(Color colorFromAnotherCard) {
		Preconditions.checkNotNull(colorFromAnotherCard,"[ERROR] Cannot verify if both have same color : provided color is null");
		return this.getCouleur().equals(colorFromAnotherCard);
	}

	/**
	 * M�thode prot�g�e permettant de savoir si la valeur de la carte actuelle est la m�me que la valeur pass�e en param�tre
	 * @param valueFromAnotherCard Valeur d'une 2�me carte, pass�e en param�tre
	 * @return TRUE si les 2 valeurs sont identiques, FALSE sinon
	 */
	protected boolean hasSameValueThan(int valueFromAnotherCard) {
		return this.getValeur().equals(valueFromAnotherCard);
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
			boolean sameColor = hasSameColorThan(otherCard.getCouleur());
			boolean sameValue = hasSameValueThan(otherCard.getValeur());
			return sameColor && sameValue;
		}
	}

	/* ========================================= GETTERS ========================================= */

	/**
	 * M�thode permettant de v�rifier si une carte est sp�ciale ou non
	 * @return TRUE s'il s'agit d'une CarteSpeciale, FALSE sinon
	 */
	public Boolean isSpecial() {
		return false;
	}

	/**
	 * M�thode permettant de r�cuperer la valeur d'une carte
	 * @return La valeur de la carte (dans le cas d'une carte num�rot�e, il s'agit aussi de son num�ro)
	 */
	public Integer getValeur() {
		return this.value;
	}

	/**
	 * M�thode permettant de r�cuperer la couleur d'une carte
	 * @return La couleur de la carte
	 */
	public Color getCouleur () {
		return this.color;
	}

	public boolean isRed() {
		return this.color.equals(Color.RED);
	}

	public boolean isBlue() {
		return this.color.equals(Color.BLUE);
	}
	
	public boolean isGreen() {
		return this.color.equals(Color.GREEN);
	}
	
	public boolean isYellow() {
		return this.color.equals(Color.YELLOW);
	}
	
	public boolean isJoker() {
		return this.color.equals(Color.JOKER);
	}

	/* ========================================= DISPLAY ========================================= */

	/**
	 * M�thode permettant sp�cifiant la fa�on dont s'affiche une carte
	 */
	@Override
	public String toString() {
		return "[CARTE NUMEROTEE] Numero=" + this.getValeur() + ", Couleur=" + this.getCouleur();
	}
}