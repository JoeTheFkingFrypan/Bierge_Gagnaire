package main.java.cards.model.basics;

/**
 * Interface sp�cifiant les comportements communs de tous les effets
 */
public interface Effet {
	public void declencherEffet();
	public String toString();
	public String afficherDescription();
}