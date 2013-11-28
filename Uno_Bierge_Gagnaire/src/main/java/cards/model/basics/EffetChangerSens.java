package main.java.cards.model.basics;

/**
 * Effet permettant d'inverser le sens de jeu
 */
public class EffetChangerSens implements Effet {
	@Override
	public void declencherEffet() {
		System.out.println("[EFFET DECLENCHE] Le sens de jeu est invers�");
	}

	@Override
	public String toString() {
		return "Cette carte permet d'inverser le sens du jeu";
	}
	
	@Override
	public String afficherDescription() {
		return toString();
	}
}