package main.java.cards.model.basics;

import main.java.gameContext.model.GameFlags;

/**
 * Effet permettant d'inverser le sens de jeu
 */
public class EffetChangerSens implements Effet {
	@Override
	public GameFlags declencherEffet() {
		return GameFlags.INVERSION;
	}

	@Override
	public String toString() {
		return "Inversion";
	}
	
	@Override
	public String afficherDescription() {
		return "Le sens de jeu est inversé";
	}
}