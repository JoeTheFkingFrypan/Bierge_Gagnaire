package main.modele.carteModele;

import com.google.common.base.Preconditions;

public class Carte implements Comparable<Carte> {
	private final int valeur;
	private final int nombre;
	private final Couleur couleur;

	public Carte (int valeur, Couleur couleur) {
		Preconditions.checkNotNull(couleur,"[ERROR] Color cannot be null");
		Preconditions.checkArgument(valeur >= 0 && valeur <= 9,"[ERROR] Invalid card number (expected 0-9, was : " + valeur + ")");
		Preconditions.checkArgument(!couleur.equals(Couleur.JOKER),"[ERROR] Invalid card color (expected {ROUGE, BLEUE, VERTE, JAUNE} was : " + couleur + ")");
		this.valeur = valeur;
		this.nombre = valeur;
		this.couleur = couleur;
	}

	public Boolean estSpeciale() {
		return false;
	}

	public Integer getValeur() {
		return this.valeur;
	}

	public Integer getNombre() {
		return this.nombre;
	}

	public Couleur getCouleur () {
		return this.couleur;
	}

	/*=============== METHODES d'AFFCHAGE ===============*/

	@Override
	public String toString() {
		return "[CARTE NUMEROTEE] Numero=" + this.getNombre() + ", Valeur=" + this.getValeur() + ", Couleur=" + this.getCouleur();
	}

	/*=============== METHODES de COMPARAISON ===============*/

	@Override
	public boolean equals(Object other) {
		boolean isNumberedCard = other.getClass().equals(Carte.class);
		if(!isNumberedCard) {
			return false;
		} else {
			Carte otherCard = (Carte)other;
			boolean sameColor = this.getCouleur().equals(otherCard.getCouleur());
			boolean sameNumber = this.getNombre().equals(otherCard.getNombre());
			boolean sameValue = this.getValeur().equals(otherCard.getValeur());
			return sameColor && sameNumber && sameValue;
		}
	}

	@Override
	public int compareTo(Carte otherCard) {
		if(!this.getNombre().equals(otherCard.getNombre())) {
			return this.getNombre().compareTo(otherCard.getNombre());
		} else {
			return this.getCouleur().compareTo(otherCard.getCouleur());
		}
	}
}
