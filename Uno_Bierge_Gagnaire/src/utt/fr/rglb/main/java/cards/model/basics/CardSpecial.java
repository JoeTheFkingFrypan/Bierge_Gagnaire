package utt.fr.rglb.main.java.cards.model.basics;

import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.game.model.GameFlag;


/**
 * Classe correspondant � une carte sp�ciale (carte avec un effet)
 */
public class CardSpecial extends Card {	
	private static final long serialVersionUID = 1L;
	private final Effect effect;

	/* ========================================= CONSTRUCTOR ========================================= */
	
	/**
	 * Constructeur de carte sp�ciale
	 * @param value Valeur de la carte (doit �tre sup�rieure � 0)
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
	 * M�thode permettant de d�clencher l'execution d'un effet
	 * @return GameFlag correspondant � l'�tat induit par le d�clenchement de l'effet
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
	 * M�thode permettant de savoir si une carte peut �tre jou�e par dessus la carte actuelle (dans le cas d'une carte SPECIALE)
	 * A noter que dans le cas des cartes sp�ciales, la valeur n'est pas un crit�re de compatibilit�
	 * @param otherCard Carte que l'on souhaite eventuellement jouer
	 * @return <code>TRUE</code> si la carte est "compatible" (si elle peut �tre jou�e), <code>FALSE</code> sinon
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
	 * M�thode priv�e permettant de g�rer la comparaison entre carte sp�ciale et la carte pass�e en param�tre (dans le cas d'une carte NUMEROTEE)
	 * @param otherCard Carte dont on souhaite tester la compatibilit�
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
	 * M�thode priv�e permettant de savoir si l'effet de la carte actuelle est le m�me que l'effet pass� en param�tre
	 * @param effectFromAnotherCard Effet d'une 2�me carte, pass� en param�tre
	 * @return <code>TRUE</code> si les 2 effets sont identiques, <code>FALSE</code> sinon
	 */
	private boolean hasSameEffectThan(String effectFromAnotherCard) {
		Preconditions.checkNotNull(effectFromAnotherCard,"[ERROR] Impossible to compare effets : provided effect is null");
		return this.getEffect().equals(effectFromAnotherCard);
	}
	
	/* ========================================= BASIC COMPARAISON ========================================= */

	/**
	 * M�thode d�finissant les crit�res d'�galit� entre deux cartes sp�ciales
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
	 * M�thode permettant de r�cuperer la description d'un effet
	 * @return String contenant la description de l'effet de la carte
	 */
	public String getEffect() {
		return this.effect.getDescription();
	}
	
	/**
	 * M�thode permettant de v�rifier si une carte est sp�ciale ou non
	 * @return <code>TRUE</code> s'il s'agit d'une CarteSpeciale, <code>FALSE</code> sinon
	 */
	@Override
	public Boolean isSpecial() {
		return true;
	}

	/* ========================================= DISPLAY ========================================= */

	/**
	 * M�thode permettant sp�cifiant la fa�on dont s'affiche une carte sp�ciale
	 */
	@Override
	public String toString() {
		return "[CARTE SPECIALE] Valeur=" + super.getValue() + ", Couleur=" + super.getColor() + ", Effet=" + this.effect;
	}
}
