package utt.fr.rglb.main.java.cards.model.basics;

import utt.fr.rglb.main.java.game.model.GameFlag;

/**
 * Effet permettant d'inverser le sens de jeu
 */
public class EffectReverse extends EffectImpl {
	private static final long serialVersionUID = 1L;

	@Override
	public GameFlag triggerEffect() {
		return GameFlag.REVERSE;
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