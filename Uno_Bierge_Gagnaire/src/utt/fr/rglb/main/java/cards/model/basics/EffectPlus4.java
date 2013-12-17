package utt.fr.rglb.main.java.cards.model.basics;

import utt.fr.rglb.main.java.game.model.GameFlag;

/**
 * Effet permettant au joueur actuel de choisir une couleur et de forcer le joueur suivant � piocher 4 cartes
 */
public class EffectPlus4 extends EffectImpl {
	private static final long serialVersionUID = 1L;

	@Override
	public GameFlag triggerEffect() {
		return GameFlag.PLUS_FOUR;
	}

	@Override
	public String toString() {
		return "Le joueur actuel choisit une couleur et le joueur suivant devra piocher 4 cartes";
	}
	
	@Override
	public String getDescription() {
		return "+4";
	}
}