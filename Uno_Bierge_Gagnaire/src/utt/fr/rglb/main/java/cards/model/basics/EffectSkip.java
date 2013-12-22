package utt.fr.rglb.main.java.cards.model.basics;

import utt.fr.rglb.main.java.game.model.GameFlag;

/**
 * Effet permettant à un joueur de forcer le joueur suivant à passer son tour
 */
public class EffectSkip extends EffectImpl {
	private static final long serialVersionUID = 1L;

	@Override
	public GameFlag triggerEffect() {
		return GameFlag.SKIP;
	}

	@Override
	public GameFlag triggerSecondaryEffect() {
		return triggerEffect();
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
