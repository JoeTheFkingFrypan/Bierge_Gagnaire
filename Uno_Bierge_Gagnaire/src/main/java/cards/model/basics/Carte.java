package main.java.cards.model.basics;

import com.google.common.base.Preconditions;

/**
 * Classe m�re correspondant � une carte de jeu (valeur, num�ro, couleur)
 * D�finit les comportements communs � toutes les cartes
 */
public class Carte implements Comparable<Carte> {
	private final int numero;
	private final Couleur couleur;

	/**
	 * Constructeur de carte, soumis � plusieurs contraintes
	 * @param valeur Num�ro de la carte (doit �tre sup�rieure � 0 dans tous les cas, et inf�rieur � 9 s'il s'agit d'une carte num�rot�e)
	 * @param couleur Couleur de la carte (doit �tre diff�rent de null, et �tre diff�rent de Joker s'il s'agit d'une carte num�rot�e)
	 */
	public Carte (int valeur, Couleur couleur) {
		Preconditions.checkNotNull(couleur,"[ERROR] Color cannot be null");
		Preconditions.checkArgument(valeur >= 0,"[ERROR] Invalid card number (expected > 0, was : " + valeur + ")");
		if(!isSpecial()) {
			Preconditions.checkArgument(valeur <= 9,"[ERROR] Invalid card number (expected 0-9, was : " + valeur + ")");
			Preconditions.checkArgument(!couleur.equals(Couleur.JOKER),"[ERROR] Invalid card color (expected {ROUGE, BLEUE, VERTE, JAUNE} was : " + couleur + ")");
		}
		this.numero = valeur;
		this.couleur = couleur;
	}

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
		return this.numero;
	}

	/**
	 * M�thode permettant de r�cuperer la couleur d'une carte
	 * @return La couleur de la carte
	 */
	public Couleur getCouleur () {
		return this.couleur;
	}

	/*=============== METHODES d'AFFCHAGE ===============*/

	/**
	 * M�thode permettant sp�cifiant la fa�on dont s'affiche une carte
	 */
	@Override
	public String toString() {
		return "[CARTE NUMEROTEE] Numero=" + this.getValeur() + ", Couleur=" + this.getCouleur();
	}

	/*=============== METHODES de COMPARAISON ===============*/
	
	/**
	 * M�thode d�finissant les crit�res d'�galit� entre deux cartes
	 */
	@Override
	public boolean equals(Object other) {
		boolean isNumberedCard = other.getClass().equals(Carte.class);
		if(!isNumberedCard) {
			return false;
		} else {
			Carte otherCard = (Carte)other;
			boolean sameColor = this.getCouleur().equals(otherCard.getCouleur());
			boolean sameValue = this.getValeur().equals(otherCard.getValeur());
			return sameColor && sameValue;
		}
	}

	/**
	 * M�thode d�finissant les crit�res de comparaison entre deux cartes
	 */
	@Override
	public int compareTo(Carte otherCard) {
		if(!this.getValeur().equals(otherCard.getValeur())) {
			return this.getValeur().compareTo(otherCard.getValeur());
		} else {
			return this.getCouleur().compareTo(otherCard.getCouleur());
		}
	}
	
	/**
	 * M�thode permettant de savoir si une carte peut �tre jou�e par dessus la carte actuelle
	 * @param otherCard Carte que l'on souhaite eventuellement jouer
	 * @return TRUE si la carte est "compatible" (si elle peut �tre jou�e), false sinon
	 */
	public boolean isCompatibleWith(Carte otherCard) {
		//TODO: handle global colors (joker)
		if(this.hasSameColorThan(otherCard.getCouleur())) {
			return true;
		} else if(this.hasSameValueThan(otherCard.getValeur())) {
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
	protected boolean hasSameColorThan(Couleur colorFromAnotherCard) {
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
}