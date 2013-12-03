package main.java.cards.model.basics;

import main.java.gameContext.model.GameFlags;

/**
 * Effet permettant d'inverser le sens de jeu
 */
public class EffectReverse implements Effect {
	@Override
	public GameFlags triggerEffect() {
		return GameFlags.REVERSE;
	}

	@Override
	public String toString() {
		return "Le sens de jeu est inversé";
	}
	
	@Override
	public String getDescription() {
		return "Inversion";
	}
}