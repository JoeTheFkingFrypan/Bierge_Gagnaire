package main.modele.piocheModele;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import main.modele.carteModele.Carte;

/**
 * Classe permettant de piocher des cartes, comprenant initialement l'ensemble des 108 cartes de jeu
 */
public class Pioche {
	private CardGenerator generator;
	private Queue<Carte> pioche;
	
	public Pioche() {
		this.generator = new CardGenerator();
		this.pioche = generateShuffledCards();
	}
	
	/**
	 * M�thode priv�e permettant d'initialiser la pioche (cr�ation des 108 cartes dans un ordre al�atoire)
	 * @return Une Queue contenant toutes les cartes dans un ordre al�atoire
	 */
	private Queue<Carte> generateShuffledCards() {
		return this.generator.generateCards();
	}
	
	/**
	 * M�thode permettant de tirer une (ou plusieurs) carte(s) depuis la pioche
	 * @param count Nombre de cartes � tirer
	 * @return Une collection contenant le nombre de cartes donn�
	 */
	public Collection<Carte> drawCards(int count) {
		//TODO: handle empty pile
		Collection<Carte> cardsToDeal = new LinkedList<Carte>();
		for(int i=0; i<count; i++) {
			cardsToDeal.add(pioche.poll());
		}
		return cardsToDeal;
	}
	
	/**
	 * M�thode permettant de remplir la pioche si jamais il n'y a plus suffisament de cartes
	 * @param givenCards Collection de cartes � utiliser pour reconstituer la pioche
	 */
	public void refill(Collection<Carte> givenCards) {
		this.pioche = generator.refillCardsFrom(givenCards);
	}
	
	/**
	 * M�thode permettant sp�cifiant la fa�on dont s'affiche la pioche
	 */
	@Override
	public String toString() {
		return "[Pioche] Contient actuellement " + this.size() + " cartes";
	}
	
	/**
	 * M�thode permettant de r�cuperer le nombre de cartes contenues dans la pioche
	 * @return Nombre de cartes pr�sentes dans la pioche
	 */
	public int size() {
		return this.pioche.size();
	}
	
	/**
	 * M�thode permettant de s'assurer la pr�sence d'une carte donn�e dans la pioche
	 * @param c Carte dont la pr�sence est � tester
	 * @return TRUE si la carte est contenue, FALSE sinon
	 */
	public Boolean contains(Carte c) {
		return this.pioche.contains(c);
	}

	/**
	 * M�thode permettant d'afficher dans la console toutes les cartes contenues dans la pioche
	 */
	public void displayAllCards() {
		System.out.println(pioche);
	}
}
