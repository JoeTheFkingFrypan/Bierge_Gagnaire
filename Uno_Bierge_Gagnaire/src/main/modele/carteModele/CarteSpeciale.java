package main.modele.carteModele;

import com.google.common.base.Preconditions;

/**
 * Classe correspondant � une carte sp�ciale (carte avec un effet)
 */
public class CarteSpeciale extends Carte {	
	private final Effet effet;

	/**
	 * Constructeur de carte sp�ciale, soumis � plusieurs contraintes
	 * @param valeur Valeur de la carte (doit �tre sup�rieure � 0)
	 * @param couleur Couleur de la carte (ne doit pas �tre null)
	 * @param effet Effet de la carte (ne doit pas �tre null)
	 */
	public CarteSpeciale(int valeur, Couleur couleur, Effet effet) {
		super(valeur, couleur);
		Preconditions.checkNotNull(effet,"[ERROR] Effect cannot be null");
		this.effet = effet;
	}

	/**
	 * M�thode permettant de v�rifier si une carte est sp�ciale ou non
	 * @return TRUE s'il s'agit d'une CarteSpeciale, FALSE sinon
	 */
	@Override
	public Boolean estSpeciale() {
		return true;
	}

	/**
	 * M�thode permettant de d�clencher l'execution d'un effet
	 */
	public void declencherEffet() {
		this.effet.declencherEffet();
	}

	/**
	 * M�thode permettant de r�cuperer la description d'un effet
	 * @return String contenant la description de l'effet de la carte
	 */
	public String getEffet() {
		return this.effet.afficherDescription();
	}

	/*=============== METHODES d'AFFCHAGE ===============*/

	/**
	 * M�thode permettant sp�cifiant la fa�on dont s'affiche une carte sp�ciale
	 */
	@Override
	public String toString() {
		return "[CARTE SPECIALE] Valeur=" + super.getValeur() + ", Couleur=" + super.getCouleur() + ", Effet=" + this.effet;
	}

	/*=============== METHODES de COMPARAISON ===============*/

	/**
	 * M�thode d�finissant les crit�res d'�galit� entre deux cartes sp�ciales
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
