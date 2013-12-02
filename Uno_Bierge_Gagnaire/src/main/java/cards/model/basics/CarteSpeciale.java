package main.java.cards.model.basics;

import main.java.gameContext.model.GameFlags;

import com.google.common.base.Preconditions;

/**
 * Classe correspondant � une carte sp�ciale (carte avec un effet)
 */
public class CarteSpeciale extends Carte {	
	private final Effet effet;

	/* ========================================= CONSTRUCTOR ========================================= */
	
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

	/* ========================================= EFFECT ========================================= */

	/**
	 * M�thode permettant de d�clencher l'execution d'un effet
	 * @return 
	 */
	public GameFlags declencherEffet() {
		return this.effet.declencherEffet();
	}
	
/* ========================================= ADVANCED COMPARAISON ========================================= */
	
	/**
	 * M�thode permettant de savoir si une carte peut �tre jou�e par dessus la carte actuelle 
	 * A noter que dans le cas des cartes sp�ciales, la valeur n'est pas un crit�re de compatibilit�
	 * @param otherCard Carte que l'on souhaite eventuellement jouer
	 * @return TRUE si la carte est "compatible" (si elle peut �tre jou�e), false sinon
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
	 * M�thode priv�e permettant de savoir si l'effet de la carte actuelle est le m�me que l'effet pass� en param�tre
	 * @param valueFromOtherCard Effet d'une 2�me carte, pass� en param�tre
	 * @return TRUE si les 2 effets sont identiques, FALSE sinon
	 */
	private boolean hasSameEffectThan(String effectFromAnotherCard) {
		return this.getEffet().equals(effectFromAnotherCard);
	}
	
	
	
	/* ========================================= BASIC COMPARAISON ========================================= */

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
			boolean sameColor = hasSameColorThan(otherSpecialCard.getCouleur());
			boolean sameValue = hasSameValueThan(otherSpecialCard.getValeur());
			boolean sameEffect = hasSameEffectThan(otherSpecialCard.getEffet());
			return sameColor && sameValue && sameEffect;
		}
	}
	
	/* ========================================= GETTERS ========================================= */
	
	/**
	 * M�thode permettant de r�cuperer la description d'un effet
	 * @return String contenant la description de l'effet de la carte
	 */
	public String getEffet() {
		return this.effet.afficherDescription();
	}
	
	/**
	 * M�thode permettant de v�rifier si une carte est sp�ciale ou non
	 * @return TRUE s'il s'agit d'une CarteSpeciale, FALSE sinon
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
		return "[CARTE SPECIALE] Valeur=" + super.getValeur() + ", Couleur=" + super.getCouleur() + ", Effet=" + this.effet;
	}
}
