package carteModele;

public class CarteSpeciale extends Carte {	
	private final Effet effet;

	public CarteSpeciale(int valeur, Couleur couleur, Effet effet) {
		super(valeur, couleur);
		this.effet = effet;
	}
	
	@Override
	public Boolean estSpeciale() {
		return true;
	}
	
	@Override
	public void afficherNombre() {
		//N'affiche rien : une carte spéciale n'a pas de numéro
	}

	
	public void declencherEffet() {
		this.effet.declencherEffet();
	}
	
	
}
