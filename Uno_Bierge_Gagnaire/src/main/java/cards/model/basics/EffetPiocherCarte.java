package main.java.cards.model.basics;

import main.java.gameContext.model.GameFlags;

/**
 * Effet permettant au joueur actuel de forcer le joueur suivant � piocher un certain nombre de cartes 
 */
public class EffetPiocherCarte implements Effet {
	private final int cartesDevantEtrePiochees;

	public EffetPiocherCarte (int cartesDevantEtrePiochees) {
		this.cartesDevantEtrePiochees = cartesDevantEtrePiochees;
	}
	
	@Override
	public GameFlags declencherEffet() {
		return GameFlags.PLUS_TWO;
	}

	@Override
	public String toString() {
		return "Le joueur suivant devra piocher " + this.cartesDevantEtrePiochees + " cartes";
	}
	
	@Override
	public String afficherDescription() {
		return "+" + this.cartesDevantEtrePiochees;
	}
}
