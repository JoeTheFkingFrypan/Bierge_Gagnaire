package main.modele.carteModele;

public class EffetChangerSens implements Effet {
	@Override
	public void declencherEffet() {
		System.out.println("[EFFET DECLENCHE] Le sens de jeu est inversé");
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