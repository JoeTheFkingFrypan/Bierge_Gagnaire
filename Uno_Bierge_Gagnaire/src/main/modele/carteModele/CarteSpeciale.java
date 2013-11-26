package main.modele.carteModele;

import com.google.common.base.Preconditions;

/**
 * Classe correspondant à une carte spéciale (carte avec un effet)
 */
public class CarteSpeciale extends Carte {	
	private final Effet effet;

	/**
	 * Constructeur de carte spéciale, soumis à plusieurs contraintes
	 * @param valeur Valeur de la carte (doit être supérieure à 0)
	 * @param couleur Couleur de la carte (ne doit pas être null)
	 * @param effet Effet de la carte (ne doit pas être null)
	 */
	public CarteSpeciale(int valeur, Couleur couleur, Effet effet) {
		super(valeur, couleur);
		Preconditions.checkNotNull(effet,"[ERROR] Effect cannot be null");
		this.effet = effet;
	}

	/**
	 * Méthode permettant de vérifier si une carte est spéciale ou non
	 * @return TRUE s'il s'agit d'une CarteSpeciale, FALSE sinon
	 */
	@Override
	public Boolean estSpeciale() {
		return true;
	}

	/**
	 * Méthode permettant de déclencher l'execution d'un effet
	 */
	public void declencherEffet() {
		this.effet.declencherEffet();
	}

	/**
	 * Méthode permettant de récuperer la description d'un effet
	 * @return String contenant la description de l'effet de la carte
	 */
	public String getEffet() {
		return this.effet.afficherDescription();
	}

	/*=============== METHODES d'AFFCHAGE ===============*/

	/**
	 * Méthode permettant spécifiant la façon dont s'affiche une carte spéciale
	 */
	@Override
	public String toString() {
		return "[CARTE SPECIALE] Valeur=" + super.getValeur() + ", Couleur=" + super.getCouleur() + ", Effet=" + this.effet;
	}

	/*=============== METHODES de COMPARAISON ===============*/

	/**
	 * Méthode définissant les critères d'égalité entre deux cartes spéciales
	 */
	@Override
	public boolean equals(Object other) {
		boolean isSpecialCard = other.getClass().equals(CarteSpeciale.class);
		if(!isSpecialCard) {
			return false;
		} else {
			CarteSpeciale otherSpecialCard = (CarteSpeciale)other;
			boolean sameColor = this.getCouleur().equals(otherSpecialCard.getCouleur());
			boolean sameValue = this.getValeur().equals(otherSpecialCard.getValeur());
			boolean sameEffect = this.getEffet().equals(otherSpecialCard.getEffet());
			return sameColor && sameValue && sameEffect;
		}
	}
}
