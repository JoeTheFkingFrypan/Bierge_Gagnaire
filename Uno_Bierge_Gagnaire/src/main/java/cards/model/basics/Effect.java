package main.java.cards.model.basics;

import main.java.gameContext.model.GameFlag;

/**
 * Interface spécifiant les comportements communs de tous les effets
 */
public interface Effect {
	/**
	 * Méthode permettant de déclencher un effet et de récuperer l'état associé
	 * @return Enumeration d'etat résultat du déclenchement de l'effet
	 */
	public GameFlag triggerEffect();
	
	/**
	 * Méthode permettant d'afficher un effet dans l'interface
	 * @return String à afficher
	 */
	public String toString();
	
	/**
	 * Méthode permettant d'afficher la description d'un effet
	 * @return String correspondant à la description de l'effet
	 */
	public String getDescription();
}