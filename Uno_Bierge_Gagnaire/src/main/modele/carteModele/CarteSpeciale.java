package main.modele.carteModele;

public class CarteSpeciale extends Carte {	
	private final Effet effet;

	public CarteSpeciale(int valeur, Couleur couleur, Effet effet) {
		super(valeur, couleur);
		validateValueAndColor(valeur,couleur);
		this.effet = effet;
	}
	
	@Override
	protected void validateValueAndColor(int valeur, Couleur couleur) {
		if(valeur < 0) {
			throw new CarteModeleException("[ERROR] Invalid card number (must not be under 0, was : " + valeur + ")");
		}
	}
	
	@Override
	public Boolean estSpeciale() {
		return true;
	}
	
	public void declencherEffet() {
		this.effet.declencherEffet();
	}
	
	@Override
	public String toString() {
		return "[CARTE SPECIALE] Pas de numero, Valeur=" + super.getValeur() + ", Couleur=" + super.getCouleur() + ", Effet=" + this.effet;
	}

	@Override
	public boolean equals(Object other) {
		boolean isSpecialCard = other.getClass().equals(CarteSpeciale.class);
		if(!isSpecialCard) {
			return false;
		} else {
			Carte otherCard = (CarteSpeciale)other;
			boolean sameColor = this.getCouleur().equals(otherCard.getValeur());
			boolean sameNumber = this.getNombre().equals(otherCard.getNombre());
			boolean sameValue = this.getValeur().equals(otherCard.getValeur());
			boolean sameEffect = this.toString().equals(otherCard.toString());
			return sameColor && sameNumber && sameValue && sameEffect;
		}
	}
}
