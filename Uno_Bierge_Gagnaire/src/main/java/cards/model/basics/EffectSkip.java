package main.java.cards.model.basics;

import main.java.gameContext.model.GameFlag;

/**
 * Effet permettant � un joueur de forcer le joueur suivant � passer son tour
 */
public class EffectSkip implements Effect {
	@Override
	public GameFlag triggerEffect() {
		return GameFlag.SKIP;
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
