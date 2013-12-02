package main.java.cards.model.basics;

import main.java.gameContext.model.GameFlags;

/**
 * Effet permettant au joueur de choisir la couleur à utiliser
 */
public class EffetJoker implements Effet {

	@Override
	public GameFlags declencherEffet() {
		return GameFlags.COLOR_PICK;
	}
	
	@Override
	public String toString() {
		return "Joker";
	}

	@Override
	public String afficherDescription() {
		return "Le joueur actuel doit choisir une couleur";
	}
}
