package main.java.cards.model.basics;

import main.java.gameContext.model.GameFlags;

/**
 * Interface sp�cifiant les comportements communs de tous les effets
 */
public interface Effet {
	public GameFlags declencherEffet();
	public String toString();
	public String afficherDescription();
}