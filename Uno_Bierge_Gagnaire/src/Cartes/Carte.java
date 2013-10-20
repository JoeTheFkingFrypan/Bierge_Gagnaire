package Cartes;

public class Carte {
	private final int valeur;
	private final int nombre;
	private final Couleur couleur;
	private final Effet effet;
	
	public Carte (int valeur, Couleur couleur, Effet effet) {
		this.valeur = valeur;
		this.nombre = valeur;
		this.couleur = couleur;
		this.effet = effet;
	}
	
	public void afficherNombre() {
		System.out.println(debugIDDisplay() + "Son numero est : " + this.nombre);
	}
	
	public void afficherValeur() {
		System.out.println(debugIDDisplay() + "Sa valeur est : " + this.valeur);
	}
	
	public void afficherCouleur() {
		System.out.println(debugIDDisplay() + "Sa couleur est : " + this.couleur);
	}
	
	public void afficherEffet() {
		System.out.println(debugIDDisplay() + this.effet.toString());
	}
	
	public void declencherEffet() {
		this.effet.declencherEffet();
	}
	
	protected String debugIDDisplay() {
		return "[" + this.toString() + "] ";
	}
}
