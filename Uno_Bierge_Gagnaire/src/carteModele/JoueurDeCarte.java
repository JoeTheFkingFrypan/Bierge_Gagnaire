package carteModele;

public class JoueurDeCarte {

	public void jouerCarte(Carte c) {
		if(c.estSpeciale()) {
			System.out.println("Ceci est une carte sp�ciale");
			CarteSpeciale cs = (CarteSpeciale) c;
			cs.declencherEffet();
		} else {
			System.out.println("Ceci est une carte normale");
		}
		System.out.println("");
	}
}
