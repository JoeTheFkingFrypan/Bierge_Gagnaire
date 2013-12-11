package utt.fr.rglb.main.java.cards.model.basics;

import utt.fr.rglb.main.java.turns.model.GameFlag;

/**
 * Effet permettant d'inverser le sens de jeu
 */
public class EffectReverse implements Effect {
	
	@Override
	public GameFlag triggerEffect() {
		return GameFlag.REVERSE;
	}

	@Override
	public String toString() {
		return "Le sens de jeu est invers�";
	}
	
	@Override
	public String getDescription() {
		return "Inversion";
	}
}