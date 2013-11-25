package main.modele.piocheModele;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import main.modele.carteModele.Carte;

public class Pioche {
	private CardGenerator generator;
	private Queue<Carte> pioche;
	
	public Pioche() {
		this.generator = new CardGenerator();
		this.pioche = generateShuffledCards();
	}
	
	private Queue<Carte> generateShuffledCards() {
		return this.generator.generateCards();
	}
	
	public Collection<Carte> drawCards(int count) {
		Collection<Carte> cardsToDeal = new LinkedList<Carte>();
		for(int i=0; i<count; i++) {
			cardsToDeal.add(pioche.poll());
		}
		return cardsToDeal;
	}
	
	public void refill(Collection<Carte> givenCards) {
		this.pioche = generator.refillCardsFrom(givenCards);
	}
	
	@Override
	public String toString() {
		return "[Pioche] Contient actuellement " + pioche.size() + " cartes";
	}
	
	public int size() {
		return this.pioche.size();
	}
	
	public Boolean contains(Carte c) {
		return this.pioche.contains(c);
	}
	
	public void displayAllCards() {
		System.out.println(pioche);
	}
}
