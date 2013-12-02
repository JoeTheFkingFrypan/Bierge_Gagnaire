package main.java.cards.model.basics;

import main.java.gameContext.model.GameFlags;

import com.google.common.base.Preconditions;

/**
 * Classe correspondant à une carte spéciale (carte avec un effet)
 */
public class CarteSpeciale extends Carte {	
	private final Effet effet;

	/* ========================================= CONSTRUCTOR ========================================= */
	
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

	/* ========================================= EFFECT ========================================= */

	/**
	 * Méthode permettant de déclencher l'execution d'un effet
	 * @return 
	 */
	public GameFlags declencherEffet() {
		return this.effet.declencherEffet();
	}
	
/* ========================================= ADVANCED COMPARAISON ========================================= */
	
	/**
	 * Méthode permettant de savoir si une carte peut être jouée par dessus la carte actuelle 
	 * A noter que dans le cas des cartes spéciales, la valeur n'est pas un critère de compatibilité
	 * @param otherCard Carte que l'on souhaite eventuellement jouer
	 * @return TRUE si la carte est "compatible" (si elle peut être jouée), false sinon
	 */
	public boolean isCompatibleWith(CarteSpeciale otherCard) {
		//TODO: handle global colors (joker)
		if(this.hasSameColorThan(otherCard.getCouleur())) {
			return true;
		} else if(this.hasSameEffectThan(otherCard.getEffet())) {
			return true;
		} else if(otherCard.getCouleur().equals(Couleur.JOKER)){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Méthode privée permettant de savoir si l'effet de la carte actuelle est le même que l'effet passé en paramètre
	 * @param valueFromOtherCard Effet d'une 2ème carte, passé en paramètre
	 * @return TRUE si les 2 effets sont identiques, FALSE sinon
	 */
	private boolean hasSameEffectThan(String effectFromAnotherCard) {
		return this.getEffet().equals(effectFromAnotherCard);
	}
	
	
	
	/* ========================================= BASIC COMPARAISON ========================================= */

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
			boolean sameColor = hasSameColorThan(otherSpecialCard.getCouleur());
			boolean sameValue = hasSameValueThan(otherSpecialCard.getValeur());
			boolean sameEffect = hasSameEffectThan(otherSpecialCard.getEffet());
			return sameColor && sameValue && sameEffect;
		}
	}
	
	/* ========================================= GETTERS ========================================= */
	
	/**
	 * Méthode permettant de récuperer la description d'un effet
	 * @return String contenant la description de l'effet de la carte
	 */
	public String getEffet() {
		return this.effet.afficherDescription();
	}
	
	/**
	 * Méthode permettant de vérifier si une carte est spéciale ou non
	 * @return TRUE s'il s'agit d'une CarteSpeciale, FALSE sinon
	 */
	@Override
	public Boolean isSpecial() {
		return true;
	}

	/* ========================================= DISPLAY ========================================= */

	/**
	 * Méthode permettant spécifiant la façon dont s'affiche une carte spéciale
	 */
	@Override
	public String toString() {
		return "[CARTE SPECIALE] Valeur=" + super.getValeur() + ", Couleur=" + super.getCouleur() + ", Effet=" + this.effet;
	}
}
