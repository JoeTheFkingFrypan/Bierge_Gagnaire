package main.java.cards.model.pile;

import java.util.Collection;
import java.util.Stack;

import main.java.cards.model.basics.Carte;

/**
 * Classe permettant de r�ception les cartes jou�es (avec comparaison par rapport � la derni�re re�ue).
 * Sert �galement � reconstituer la pioche lorsque cette derni�re est vide.
 */
public class Talon {
	private final Stack<Carte> talon;
	
	public Talon() {
		this.talon = new Stack<Carte>();
	}
	
	/**
	 * M�thode permettant de jouer une carte
	 * @param c Carte � jouer
	 */
	public void receiveCard(Carte c) {
		talon.push(c);
	}
	
	/**
	 * M�thode permettant de savoir si la carte choisie est compatible avec celle sur le talon
	 * @param chosenCard Carte choisie
	 * @return TRUE si les deux cartes sont compatibles, FALSE sinon
	 */
	public boolean accept(Carte chosenCard) {
		return this.talon.peek().isCompatibleWith(chosenCard);
	}
	
	/**
	 * M�thode permettant de vider le talon de ses cartes (sauf de la derni�re jou�e) et de les transf�rer
	 * @return L'ensemble des cartes (except�e la derni�re jou�e) provenant du talon
	 */
	public Collection<Carte> emptyPile() {
		Collection<Carte> allCardsExceptLastPlayed = new Stack<Carte>();
		Carte lastCardPlayed = this.talon.pop();
		allCardsExceptLastPlayed.addAll(this.talon);
		this.talon.clear();
		this.talon.add(lastCardPlayed);
		return allCardsExceptLastPlayed;
	}
	
	/**
	 * M�thode permettant sp�cifiant la fa�on dont s'affiche le talon
	 */
	@Override
	public String toString() {
		return "[Talon] " + talon.size() + " cartes ont �t� jou�es";
	}

	/**
	 * M�thode permettant de r�cuperer le nombre de cartes contenues dans le talon
	 * @return Nombre de cartes pr�sentes dans le talon
	 */
	public int size() {
		return this.talon.size();
	}
}
