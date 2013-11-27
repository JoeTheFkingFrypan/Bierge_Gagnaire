package main.java.cards.model.basics;

/**
 * Effet permettant au joueur actuel de choisir une couleur et de forcer le joueur suivant à piocher 4 cartes
 */
public class EffetPlus4 implements Effet {

	@Override
	public void declencherEffet() {
		System.out.println("[EFFET DECLENCHE] Le joueur actuel choisit une couleur et le joueur suivant devra piocher 4 cartes");
	}

	@Override
	public String afficherDescription() {
		return "Cette carte permet de au joueur actuel de choiri une couleur et de faire piocher 4 cartes au joueur suivant";
	}

	@Override
	public String toString() {
		return afficherDescription();
	}
}
