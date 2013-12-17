package utt.fr.rglb.main.java.cards.model.basics;

import utt.fr.rglb.main.java.game.model.GameFlag;

/**
 * Effet permettant au joueur actuel de forcer le joueur suivant � piocher un certain nombre de cartes 
 */
public class EffectPlus2 extends EffectImpl {
	private static final long serialVersionUID = 1L;
	private final int cardsThatMustBeDrawn;

	/**
	 * Constructeur permettant de cr�er un EffectPlus2
	 * @param cardsThatMustBeDrawn Nombre de cartes devant �tre pioch�es
	 */
	public EffectPlus2 (int cardsThatMustBeDrawn) {
		this.cardsThatMustBeDrawn = cardsThatMustBeDrawn;
	}
	
	@Override
	public GameFlag triggerEffect() {
		return GameFlag.PLUS_TWO;
	}

	@Override
	public String toString() {
		return "Le joueur suivant devra piocher " + this.cardsThatMustBeDrawn + " cartes";
	}
	
	@Override
	public String getDescription() {
		return "+" + this.cardsThatMustBeDrawn;
	}
}
