package main.java.cards.model.basics;

import main.java.gameContext.model.GameFlags;

/**
 * Effet permettant au joueur actuel de choisir une couleur et de forcer le joueur suivant à piocher 4 cartes
 */
public class EffetPlus4 implements Effet {

	@Override
	public GameFlags declencherEffet() {
		return GameFlags.PLUS_FOUR;
	}

	@Override
	public String afficherDescription() {
		return "+4";
	}

	@Override
	public String toString() {
		return "Le joueur actuel choisit une couleur et le joueur suivant devra piocher 4 cartes";
	}
}
