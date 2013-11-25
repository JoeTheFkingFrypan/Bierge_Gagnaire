package main.modele.carteModele;

public class Carte implements Comparable<Carte> {
	private final int valeur;
	private final int nombre;
	private final Couleur couleur;

	public Carte (int valeur, Couleur couleur) {
		validateValueAndColor(valeur,couleur); 
		this.valeur = valeur;
		this.nombre = valeur;
		this.couleur = couleur;
	}

	protected void validateValueAndColor(int valeur, Couleur couleur) {
		if(valeur < 0 || valeur > 9) {
			throw new CarteModeleException("[ERROR] Invalid card number (expected 0-9, was : " + valeur + ")");
		}
		if(couleur.equals(Couleur.JOKER)) {
			throw new CarteModeleException("[ERROR] Invalid card color (expected {ROUGE, BLEUE, VERTE, JAUNE} was : " + couleur + ")");
		}
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
	
	@Override
	public boolean equals(Object other) {
		boolean isCard = other.getClass().equals(Carte.class);
		if(!isCard) {
			System.out.println(other.getClass() + " vs " + Carte.class);
			return false;
		} else {
			Carte otherCard = (Carte)other;
			boolean sameColor = this.getCouleur().equals(otherCard.getValeur());
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
