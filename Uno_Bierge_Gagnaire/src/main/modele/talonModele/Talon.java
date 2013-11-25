package main.modele.talonModele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import main.modele.carteModele.Carte;

public class Talon {
	private final LinkedList<Carte> talon;
	
	public Talon() {
		this.talon = new LinkedList<Carte>();
	}
	
	public void playCard(Carte c) {
		//System.out.println("[CARTE JOUEE] " + c);
		talon.addFirst(c);
	}
	
	public Collection<Carte> emptyPile() {
		List<Carte> cards = new ArrayList<Carte>(this.talon);
		this.talon.clear();
		return cards;
	}
	
	@Override
	public String toString() {
		return "[Talon] " + talon.size() + " cartes ont été jouées";
	}

	public int size() {
		return this.talon.size();
	}
}
