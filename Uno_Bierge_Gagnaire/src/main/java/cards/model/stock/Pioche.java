package main.java.cards.model.stock;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import main.java.cards.model.basics.Carte;
import main.java.console.model.AbstractModel;

/**
 * Classe permettant de piocher des cartes, comprenant initialement l'ensemble des 108 cartes de jeu
 */
public class Pioche extends AbstractModel {
	private CardGenerator generator;
	private Queue<Carte> pioche;
	
	public Pioche() {
		this.generator = new CardGenerator();
		this.pioche = generateShuffledCards();
	}
	
	/**
	 * Méthode privée permettant d'initialiser la pioche (création des 108 cartes dans un ordre aléatoire)
	 * @return Une Queue contenant toutes les cartes dans un ordre aléatoire
	 */
	private Queue<Carte> generateShuffledCards() {
		return this.generator.generateCards();
	}
	
	/**
	 * Méthode permettant de tirer une (ou plusieurs) carte(s) depuis la pioche
	 * @param count Nombre de cartes à tirer
	 * @return Une collection contenant le nombre de cartes donné
	 */
	public Collection<Carte> drawCards(int count) {
		Collection<Carte> cardsToDeal = new LinkedList<Carte>();
		for(int i=0; i<count; i++) {
			cardsToDeal.add(pioche.poll());
		}
		return cardsToDeal;
	}
		
	/**
	 * Méthode permettant de remplir la pioche si jamais il n'y a plus suffisament de cartes
	 * @param givenCards Collection de cartes à utiliser pour reconstituer la pioche
	 */
	public void refill(Collection<Carte> givenCards) {
		this.pioche = generator.refillCardsFrom(givenCards);
	}
	
	/**
	 * Méthode permettant spécifiant la façon dont s'affiche la pioche
	 */
	@Override
	public String toString() {
		return "[Pioche] Contient actuellement " + this.size() + " cartes";
	}
	
	/**
	 * Méthode permettant de récuperer le nombre de cartes contenues dans la pioche
	 * @return Nombre de cartes présentes dans la pioche
	 */
	public int size() {
		return this.pioche.size();
	}
	
	public boolean hasNotEnoughCards(int cardCountToBeDrawn) {
		return cardCountToBeDrawn > this.size();
	}
	
	/**
	 * Méthode permettant de s'assurer la présence d'une carte donnée dans la pioche
	 * @param c Carte dont la présence est à tester
	 * @return TRUE si la carte est contenue, FALSE sinon
	 */
	public Boolean contains(Carte c) {
		return this.pioche.contains(c);
	}
}
