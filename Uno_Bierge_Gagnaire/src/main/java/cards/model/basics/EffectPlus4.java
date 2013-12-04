package main.java.cards.model.basics;

import main.java.gameContext.model.GameFlag;

/**
 * Effet permettant au joueur actuel de choisir une couleur et de forcer le joueur suivant à piocher 4 cartes
 */
public class EffectPlus4 implements Effect {

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
