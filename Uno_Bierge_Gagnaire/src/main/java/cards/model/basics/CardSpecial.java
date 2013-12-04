package main.java.cards.model.basics;

import main.java.gameContext.model.GameFlags;

import com.google.common.base.Preconditions;


/**
 * Classe correspondant à une carte spéciale (carte avec un effet)
 */
public class CardSpecial extends Card {	
	private final Effect effet;

	/* ========================================= CONSTRUCTOR ========================================= */
	
	/**
	 * Constructeur de carte spéciale, soumis à plusieurs contraintes
	 * @param valeur Valeur de la carte (doit être supérieure à 0)
	 * @param couleur Couleur de la carte (ne doit pas être null)
	 * @param effet Effet de la carte (ne doit pas être null)
	 */
	public CardSpecial(int valeur, Color couleur, Effect effet) {
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
		return this.effet.triggerEffect();
	}
	
/* ========================================= ADVANCED COMPARAISON ========================================= */
	
	@Override
	public boolean isCompatibleWith(Card otherCard) {
		if(otherCard.isSpecial()) {
			CardSpecial explicitConversionFromOtherCard = (CardSpecial)otherCard;
			return isCompatibleWithSpecialCard(explicitConversionFromOtherCard);
		} else {
			return isCompatibleWithNumberedCard(otherCard);
		}
	}
	
	/**
	 * Méthode permettant de savoir si une carte peut être jouée par dessus la carte actuelle 
	 * A noter que dans le cas des cartes spéciales, la valeur n'est pas un critère de compatibilité
	 * @param otherCard Carte que l'on souhaite eventuellement jouer
	 * @return TRUE si la carte est "compatible" (si elle peut être jouée), false sinon
	 */
	private boolean isCompatibleWithSpecialCard(CardSpecial otherCard) {
		if(this.hasSameColorThan(otherCard.getCouleur())) {
			return true;
		} else if(this.hasSameEffectThan(otherCard.getEffet())) {
			return true;
		} else if(otherCard.getCouleur().equals(Color.JOKER)){
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isCompatibleWithNumberedCard(Card otherCard) {
		if(this.hasSameColorThan(otherCard.getCouleur())) {
			return true;
		} else if(otherCard.getCouleur().equals(Color.JOKER)){
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
		boolean isSpecialCard = other.getClass().equals(CardSpecial.class);
		if(!isSpecialCard) {
			return false;
		} else {
			CardSpecial otherSpecialCard = (CardSpecial)other;
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
		return this.effet.getDescription();
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
