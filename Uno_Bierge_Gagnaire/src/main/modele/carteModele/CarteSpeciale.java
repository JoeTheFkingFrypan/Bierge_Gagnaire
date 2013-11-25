package main.modele.carteModele;

import com.google.common.base.Preconditions;

public class CarteSpeciale extends Carte {	
	private final Effet effet;

	public CarteSpeciale(int valeur, Couleur couleur, Effet effet) {
		super(valeur, couleur);
		Preconditions.checkArgument(valeur >= 0,"[ERROR] Invalid card value (expected > 0, was : " + valeur + ")");
		Preconditions.checkNotNull(couleur,"[ERROR] Color cannot be null");
		Preconditions.checkNotNull(effet,"[ERROR] Effect cannot be null");
		this.effet = effet;
	}

	@Override
	public Boolean estSpeciale() {
		return true;
	}

	public void declencherEffet() {
		this.effet.declencherEffet();
	}

	public String getEffet() {
		return this.effet.afficherDescription();
	}

	/*=============== METHODES d'AFFCHAGE ===============*/

	@Override
	public String toString() {
		return "[CARTE SPECIALE] Pas de numero, Valeur=" + super.getValeur() + ", Couleur=" + super.getCouleur() + ", Effet=" + this.effet;
	}

	/*=============== METHODES de COMPARAISON ===============*/

	@Override
	public boolean equals(Object other) {
		boolean isSpecialCard = other.getClass().equals(CarteSpeciale.class);
		if(!isSpecialCard) {
			return false;
		} else {
			CarteSpeciale otherSpecialCard = (CarteSpeciale)other;
			boolean sameColor = this.getCouleur().equals(otherSpecialCard.getCouleur());
			boolean sameNumber = this.getNombre().equals(otherSpecialCard.getNombre());
			boolean sameValue = this.getValeur().equals(otherSpecialCard.getValeur());
			boolean sameEffect = this.getEffet().equals(otherSpecialCard.getEffet());
			return sameColor && sameNumber && sameValue && sameEffect;
		}
	}
}
