package main.java.cards.model.basics;

import main.java.gameContext.model.GameFlags;

/**
 * Effet permettant au joueur actuel de forcer le joueur suivant à piocher un certain nombre de cartes 
 */
public class EffectPlus2 implements Effect {
	private final int cartesDevantEtrePiochees;

	public EffectPlus2 (int cartesDevantEtrePiochees) {
		this.cartesDevantEtrePiochees = cartesDevantEtrePiochees;
	}
	
	@Override
	public GameFlags triggerEffect() {
		return GameFlags.PLUS_TWO;
	}

	@Override
	public String toString() {
		return "Le joueur suivant devra piocher " + this.cartesDevantEtrePiochees + " cartes";
	}
	
	@Override
	public String getDescription() {
		return "+" + this.cartesDevantEtrePiochees;
	}
}
