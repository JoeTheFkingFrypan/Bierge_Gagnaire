package Cartes;

public class EffetPiocherCarte implements Effet {
	private final int cartesDevantEtrePiochees;

	public EffetPiocherCarte (int cartesDevantEtrePiochees) {
		this.cartesDevantEtrePiochees = cartesDevantEtrePiochees;
	}
	
	@Override
	public void declencherEffet() {
		System.out.println("[EFFET DECLENCHE] Le joueur suivant devra piocher " + this.cartesDevantEtrePiochees + " cartes");
	}

	@Override
	public String toString() {
		return "Cette carte permet de faire piocher " + this.cartesDevantEtrePiochees + " cartes à l'adversaire";
	}

	
	
}
