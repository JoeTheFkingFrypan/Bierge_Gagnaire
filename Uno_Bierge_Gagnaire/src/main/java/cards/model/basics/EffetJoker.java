package main.java.cards.model.basics;

import main.java.gameContext.model.GameFlags;

/**
 * Effet permettant au joueur de choisir la couleur à utiliser
 */
public class EffetJoker implements Effect {

	@Override
	public GameFlags triggerEffect() {
		return GameFlags.COLOR_PICK;
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
