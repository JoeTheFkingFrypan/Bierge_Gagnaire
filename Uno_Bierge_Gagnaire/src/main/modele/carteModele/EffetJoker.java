package main.modele.carteModele;

/**
 * Effet permettant au joueur de choisir la couleur à utiliser
 */
public class EffetJoker implements Effet {

	@Override
	public void declencherEffet() {
		System.out.println("[EFFET DECLENCHE] Le joueur actuel doit choisir une couleur");
	}
	
	@Override
	public String toString() {
		return "Cette carte permet d'empecher au joueur actuel de choisir une couleur";
	}

	@Override
	public String afficherDescription() {
		return toString();
	}
}
