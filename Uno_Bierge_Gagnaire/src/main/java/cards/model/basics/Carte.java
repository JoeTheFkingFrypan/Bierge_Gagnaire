package main.java.cards.model.basics;

import com.google.common.base.Preconditions;

/**
 * Classe mère correspondant à une carte de jeu (valeur, numéro, couleur)
 * Définit les comportements communs à toutes les cartes
 */
public class Carte implements Comparable<Carte> {
	private final int numero;
	private final Couleur couleur;

	/**
	 * Constructeur de carte, soumis à plusieurs contraintes
	 * @param valeur Numéro de la carte (doit être supérieure à 0 dans tous les cas, et inférieur à 9 s'il s'agit d'une carte numérotée)
	 * @param couleur Couleur de la carte (doit être différent de null, et être différent de Joker s'il s'agit d'une carte numérotée)
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
	 * Méthode permettant de vérifier si une carte est spéciale ou non
	 * @return TRUE s'il s'agit d'une CarteSpeciale, FALSE sinon
	 */
	public Boolean isSpecial() {
		return false;
	}

	/**
	 * Méthode permettant de récuperer la valeur d'une carte
	 * @return La valeur de la carte (dans le cas d'une carte numérotée, il s'agit aussi de son numéro)
	 */
	public Integer getValeur() {
		return this.numero;
	}

	/**
	 * Méthode permettant de récuperer la couleur d'une carte
	 * @return La couleur de la carte
	 */
	public Couleur getCouleur () {
		return this.couleur;
	}

	/*=============== METHODES d'AFFCHAGE ===============*/

	/**
	 * Méthode permettant spécifiant la façon dont s'affiche une carte
	 */
	@Override
	public String toString() {
		return "[CARTE NUMEROTEE] Numero=" + this.getValeur() + ", Couleur=" + this.getCouleur();
	}

	/*=============== METHODES de COMPARAISON ===============*/
	
	/**
	 * Méthode définissant les critères d'égalité entre deux cartes
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
	 * Méthode définissant les critères de comparaison entre deux cartes
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
	 * Méthode permettant de savoir si une carte peut être jouée par dessus la carte actuelle
	 * @param otherCard Carte que l'on souhaite eventuellement jouer
	 * @return TRUE si la carte est "compatible" (si elle peut être jouée), false sinon
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
	 * Méthode protégée permettant de savoir si la couleur de la carte actuelle est la même que la couleur passée en paramètre
	 * @param colorFromAnotherCard Couleur d'une 2ème carte, passée en paramètre
	 * @return TRUE si les 2 couleurs sont identiques, FALSE sinon
	 */
	protected boolean hasSameColorThan(Couleur colorFromAnotherCard) {
		return this.getCouleur().equals(colorFromAnotherCard);
	}
	
	/**
	 * Méthode protégée permettant de savoir si la valeur de la carte actuelle est la même que la valeur passée en paramètre
	 * @param valueFromAnotherCard Valeur d'une 2ème carte, passée en paramètre
	 * @return TRUE si les 2 valeurs sont identiques, FALSE sinon
	 */
	protected boolean hasSameValueThan(int valueFromAnotherCard) {
		return this.getValeur().equals(valueFromAnotherCard);
	}
}