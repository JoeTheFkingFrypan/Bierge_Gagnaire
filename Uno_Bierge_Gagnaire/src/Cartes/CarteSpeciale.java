package Cartes;

public class CarteSpeciale extends Carte {	
	public CarteSpeciale(int valeur, Couleur couleur, Effet effet) {
		super(valeur, couleur, effet);
	}
	
	@Override
	public void afficherNombre() {
		System.out.println(debugIDDisplay() + "Son numero est : [AUCUN] (étant spéciale, elle n'a pas de numéro)");
	}
}
