package Cartes;

public class CarteSpeciale extends Carte {	
	public CarteSpeciale(int valeur, Couleur couleur, Effet effet) {
		super(valeur, couleur, effet);
	}
	
	@Override
	public void afficherNombre() {
		System.out.println(debugIDDisplay() + "Son numero est : [AUCUN] (�tant sp�ciale, elle n'a pas de num�ro)");
	}
}
