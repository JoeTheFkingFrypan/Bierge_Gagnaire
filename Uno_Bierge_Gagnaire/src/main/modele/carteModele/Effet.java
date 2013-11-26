package main.modele.carteModele;

/**
 * Interface spécifiant les comportements communs de tous les effets
 */
public interface Effet {
	public void declencherEffet();
	public String toString();
	public String afficherDescription();
}