package main.java.cards.model.basics;

import main.java.gameContext.model.GameFlags;

/**
 * Effet permettant à un joueur de forcer le joueur suivant à passer son tour
 */
public class EffetPasserTour implements Effet {
	@Override
	public GameFlags declencherEffet() {
		return GameFlags.COLOR_PICK;
	}

	@Override
	public String toString() {
		return "Passe";
	}
	
	@Override
	public String afficherDescription() {
		return toString();
	}
	
	//System.out.println("[EFFET DECLENCHE] Le joueur suivant devra passer son tour");
}
