package main.java.player.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Carte;

/**
 * Classe correspondant à un joueur
 */
public class Joueur {
	private List<Carte> main;
	private final String alias;
	private int score;

	public Joueur(String alias) {
		Preconditions.checkNotNull(alias);
		this.main = new ArrayList<Carte>();
		this.alias = alias;
		this.score = 0;
	}

	public int getNumberOfCardsInHand() {
		return this.main.size();
	}

	public void pickUpCards(Collection<Carte> c) {
		Preconditions.checkNotNull(c,"[ERROR] Card collection picked up cannot be null");
		Preconditions.checkArgument(c.size()>0, "[ERROR] Card collection picked cannot be empty");
		this.main.addAll(c);
	}

	public void displayHand() {
		System.out.println();
		for(Carte c : this.main) {
			System.out.println("* " + c);
		}
	}

	public Carte playCard(int index) {
		Preconditions.checkArgument(index >= 0 && index < this.main.size(),"[ERROR] Incorrect index : must be > 0 (tried = " + index + ", but max is = " + this.main.size());
		Carte cardToPlay = this.main.get(index);
		this.main.remove(index);
		return cardToPlay;
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
