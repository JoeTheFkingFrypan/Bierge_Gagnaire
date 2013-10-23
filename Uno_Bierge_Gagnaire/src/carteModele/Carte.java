package carteModele;

public class Carte {
	private final int valeur;
	private final int nombre;
	private final Couleur couleur;
	
	public Carte (int valeur, Couleur couleur) {
		this.valeur = valeur;
		this.nombre = valeur;
		this.couleur = couleur;
	}
	
	public Boolean estSpeciale() {
		return false;
	}
	
	public int getValeur() {
		return this.valeur;
	}
	
	public int getNombre() {
		return this.valeur;
	}
	
	public Couleur getCouleur() {
		return this.couleur;
	}
		
	/*=============== METHODE d'AFFCHAGE ===============*/
	
	public void afficherNombre() {
		System.out.println(debugIDDisplay() + "Son numero est : " + this.nombre);
	}
	
	public void afficherValeur() {
		System.out.println(debugIDDisplay() + "Sa valeur est : " + this.valeur);
	}
	
	public void afficherCouleur() {
		System.out.println(debugIDDisplay() + "Sa couleur est : " + this.couleur);
	}
	
	protected String debugIDDisplay() {
		return "[" + this.toString() + "] ";
	}
}
