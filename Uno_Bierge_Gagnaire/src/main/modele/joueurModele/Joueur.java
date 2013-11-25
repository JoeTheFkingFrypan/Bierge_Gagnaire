package main.modele.joueurModele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import main.modele.carteModele.Carte;

public class Joueur {
	private List<Carte> main;
	private final String alias;
	private int score;
	
	public Joueur(String alias) {
		this.main = new ArrayList<Carte>();
		this.alias = alias;
		this.score = 0;
	}
	
	public void pickUpCards(Collection<Carte> c) {
		this.main.addAll(c);
	}
	
	public void displayHand() {
		System.out.println();
		for(Carte c : this.main) {
			System.out.println("* " + c);
		}
	}
	
	public Carte playCard(int index) {
		if(index < this.main.size()) {
			Carte cardToPlay = this.main.get(index);
			this.main.remove(index);
			return cardToPlay;
		} else {
			throw new PlayerException("Incorrect index (out-of-bound) while choosing card from player " + this.getAlias() + "[tried = " + index + " but max = " + this.main.size() + "]"); 
		}
	}
	
	public String getAlias() {
		return this.alias;
	}
	
	public int getScore() {
		return this.score;
	}
	
	@Override
	public String toString() {
		return "[JOUEUR] " + getAlias() + " a " + getScore() + " points. Il lui reste " + this.main.size() + " cartes en main";
	}
}
