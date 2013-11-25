package main.modele.carteModele;

public class EffetPasserTour implements Effet {
	@Override
	public void declencherEffet() {
		System.out.println("[EFFET DECLENCHE] Le joueur suivant devra passer son tour");
	}

	@Override
	public String toString() {
		return "Cette carte permet d'empecher le joueur suivant de jouer son tour";
	}
	
	@Override
	public String afficherDescription() {
		return toString();
	}
}
