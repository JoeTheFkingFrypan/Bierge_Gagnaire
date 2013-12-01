package main.java.player.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Carte;

/**
 * Classe correspondant à un joueur
 */
public class PlayerModel {
	private List<Carte> main;
	private final String alias;
	private int score;

	public PlayerModel(String alias) {
		Preconditions.checkNotNull(alias);
		this.main = new ArrayList<Carte>();
		this.alias = alias;
		this.score = 0;
	}

	public int getNumberOfCardsInHand() {
		return this.main.size();
	}

	public void pickUpCards(Collection<Carte> c) {
		Preconditions.checkNotNull(c,"[ERROR] Cannot pickup cards : provided collection is null");
		Preconditions.checkArgument(c.size() > 0, "[ERROR] Cannot pickup cards : provided collection is empty");
		this.main.addAll(c);
	}
	
	public void pickUpOneCard(Carte card) {
		Preconditions.checkNotNull(card,"[ERROR] Cannot pickup card : provided card is null");
		this.main.add(card);
	}

	public void displayHand() {
		System.out.println();
		for(Carte c : this.main) {
			System.out.println("* " + c);
		}
	}

	public Carte peekAtCard(int index) {
		Preconditions.checkState(this.main.size() > 0, "[ERROR] Cannot play that card : player has no card");
		Preconditions.checkArgument(index >= 0, "[ERROR] Cannot play that card : provided index must not be negative");
		Preconditions.checkArgument(index < this.main.size(), "[ERROR] Cannot play that card : provided index is too high (doesn't exists for this player)");
		Carte cardToPlay = this.main.get(index);
		return cardToPlay;
	}
	
	public Carte playCard(int index) {
		Preconditions.checkState(this.main.size() > 0, "[ERROR] Cannot play that card : player has no card");
		Preconditions.checkArgument(index >= 0, "[ERROR] Cannot play that card : provided index must not be negative");
		Preconditions.checkArgument(index < this.main.size(), "[ERROR] Cannot play that card : provided index is too high (doesn't exists for this player)");
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

	public Collection<Carte> getCardsInHand() {
		Collection<Carte> cardsInHand = this.main;
		return cardsInHand;
	}
}
