package main.java.cards.model.basics;

import main.java.gameContext.model.GameFlags;

/**
 * Effet permettant � un joueur de forcer le joueur suivant � passer son tour
 */
public class EffetPasserTour implements Effet {
	@Override
	public GameFlags declencherEffet() {
		return GameFlags.INTERDICTION;
	}

	@Override
	public String toString() {
		return "Passe";
	}
	
	@Override
	public String afficherDescription() {
		return "Le joueur suivant devra passer son tour";
	}
}
