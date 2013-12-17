package utt.fr.rglb.main.java.cards.model.basics;

import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.game.model.GameFlag;


/**
 * Classe correspondant à une carte spéciale (carte avec un effet)
 */
public class CardSpecial extends Card {	
	private static final long serialVersionUID = 1L;
	private final Effect effect;

	/* ========================================= CONSTRUCTOR ========================================= */
	
	/**
	 * Constructeur de carte spéciale
	 * @param value Valeur de la carte (doit être supérieure à 0)
	 * @param color Couleur de la carte
	 * @param effect Effet de la carte
	 */
	public CardSpecial(int value, Color color, Effect effect) {
		super(value, color);
		Preconditions.checkNotNull(effect,"[ERROR] Effect cannot be null");
		this.effect = effect;
	}

	/* ========================================= EFFECT ========================================= */

	/**
	 * Méthode permettant de déclencher l'execution d'un effet
	 * @return GameFlag correspondant à l'état induit par le déclenchement de l'effet
	 */
	public GameFlag triggerEffect() {
		return this.effect.triggerEffect();
	}
	
	@Override
	public Integer getValue() {
		return this.value;
	}
	
	@Override
	public Color getColor() {
		return this.color;
	}
	
/* ========================================= ADVANCED COMPARAISON ========================================= */
	
	@Override
	public boolean isCompatibleWith(Card otherCard) {
		Preconditions.checkNotNull(otherCard,"[ERROR] Impossible to test compatibility : provided card is null");
		if(otherCard.isSpecial()) {
			CardSpecial explicitConversionFromOtherCard = (CardSpecial)otherCard;
			return isCompatibleWithSpecialCard(explicitConversionFromOtherCard);
		} else {
			return isCompatibleWithNumberedCard(otherCard);
		}
	}
	
	/**
	 * Méthode permettant de savoir si une carte peut être jouée par dessus la carte actuelle (dans le cas d'une carte SPECIALE)
	 * A noter que dans le cas des cartes spéciales, la valeur n'est pas un critère de compatibilité
	 * @param otherCard Carte que l'on souhaite eventuellement jouer
	 * @return <code>TRUE</code> si la carte est "compatible" (si elle peut être jouée), <code>FALSE</code> sinon
	 */
	private boolean isCompatibleWithSpecialCard(CardSpecial otherCard) {
		Preconditions.checkNotNull(otherCard,"[ERROR] Impossible to test compatibility : provided card is null");
		if(this.hasSameColorThan(otherCard.getColor())) {
			return true;
		} else if(this.hasSameEffectThan(otherCard.getEffect())) {
			return true;
		} else if(otherCard.getColor().equals(Color.JOKER)){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Méthode privée permettant de gérer la comparaison entre carte spéciale et la carte passée en paramètre (dans le cas d'une carte NUMEROTEE)
	 * @param otherCard Carte dont on souhaite tester la compatibilité
	 * @return <code>TRUE</code> si la carte est compatible, <code>FALSE</code> sinon
	 */
	private boolean isCompatibleWithNumberedCard(Card otherCard) {
		Preconditions.checkNotNull(otherCard,"[ERROR] Impossible to test compatibility : provided card is null");
		if(this.hasSameColorThan(otherCard.getColor())) {
			return true;
		} else if(otherCard.isJoker()){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Méthode privée permettant de savoir si l'effet de la carte actuelle est le même que l'effet passé en paramètre
	 * @param effectFromAnotherCard Effet d'une 2ème carte, passé en paramètre
	 * @return <code>TRUE</code> si les 2 effets sont identiques, <code>FALSE</code> sinon
	 */
	private boolean hasSameEffectThan(String effectFromAnotherCard) {
		Preconditions.checkNotNull(effectFromAnotherCard,"[ERROR] Impossible to compare effets : provided effect is null");
		return this.getEffect().equals(effectFromAnotherCard);
	}
	
	/* ========================================= BASIC COMPARAISON ========================================= */

	/**
	 * Méthode définissant les critères d'égalité entre deux cartes spéciales
	 */
	@Override
	public boolean equals(Object other) {
		boolean isSpecialCard = (other instanceof CardSpecial);
		if(!isSpecialCard) {
			return false;
		} else {
			CardSpecial otherSpecialCard = (CardSpecial)other;
			boolean sameColor = hasSameColorThan(otherSpecialCard.getColor());
			boolean sameValue = hasSameValueThan(otherSpecialCard.getValue());
			boolean sameEffect = hasSameEffectThan(otherSpecialCard.getEffect());
			return sameColor && sameValue && sameEffect;
		}
	}
	
	/* ========================================= GETTERS ========================================= */
	
	/**
	 * Méthode permettant de récuperer la description d'un effet
	 * @return String contenant la description de l'effet de la carte
	 */
	public String getEffect() {
		return this.effect.getDescription();
	}
	
	/**
	 * Méthode permettant de vérifier si une carte est spéciale ou non
	 * @return <code>TRUE</code> s'il s'agit d'une CarteSpeciale, <code>FALSE</code> sinon
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
		return "[CARTE SPECIALE] Valeur=" + super.getValue() + ", Couleur=" + super.getColor() + ", Effet=" + this.effect;
	}
}
