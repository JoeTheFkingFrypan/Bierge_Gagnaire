package main.java.cards.model.basics;

import main.java.gameContext.model.GameFlags;

/**
 * Effet permettant à un joueur de forcer le joueur suivant à passer son tour
 */
public class EffectSkip implements Effect {
	@Override
	public GameFlags triggerEffect() {
		return GameFlags.SKIP;
	}

	@Override
	public String toString() {
		return "Le joueur suivant devra passer son tour";
	}
	
	@Override
	public String getDescription() {
		return "Passe";
	}
}
