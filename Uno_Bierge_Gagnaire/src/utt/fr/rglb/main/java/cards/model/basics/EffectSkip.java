package utt.fr.rglb.main.java.cards.model.basics;

import utt.fr.rglb.main.java.turns.model.GameFlag;

/**
 * Effet permettant à un joueur de forcer le joueur suivant à passer son tour
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
