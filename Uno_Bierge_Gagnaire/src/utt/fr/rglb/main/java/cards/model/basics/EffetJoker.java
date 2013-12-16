package utt.fr.rglb.main.java.cards.model.basics;

import utt.fr.rglb.main.java.turns.model.GameFlag;

/**
 * Effet permettant au joueur de choisir la couleur à utiliser
 */
public class EffetJoker extends EffectImpl {
	private static final long serialVersionUID = 1L;

	@Override
	public GameFlag triggerEffect() {
		return GameFlag.COLOR_PICK;
	}
	
	@Override
	public String toString() {
		return "Le joueur actuel doit choisir une couleur";
	}

	@Override
	public String getDescription() {
		return "Joker";
	}
}
