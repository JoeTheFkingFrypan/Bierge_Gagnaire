package main.java.player.controller;

import java.util.Collection;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Carte;
import main.java.player.model.PlayerModel;

public class PlayerController {
	private PlayerModel player;
	
	public PlayerController(PlayerModel player) {
		Preconditions.checkNotNull(player,"[ERROR] Model cannot be null");
		this.player = player;
	}
	
	public void pickUpCards(Collection<Carte> c) {
		Preconditions.checkNotNull(c,"[ERROR] Card collection picked up cannot be null");
		Preconditions.checkArgument(c.size()>0, "[ERROR] Card collection picked cannot be empty");
		this.player.pickUpCards(c);
	}
	
	public Carte playCard(int index) {
		Preconditions.checkArgument(index >= 0 && index < this.player.getNumberOfCardsInHand(),"[ERROR] Incorrect index : must be > 0 (tried = " + index + ", but max is = " + this.player.getNumberOfCardsInHand());
		return this.player.playCard(index);
	}
	
	public String getAlias() {
		return this.player.getAlias();
	}
	
	@Override
	public String toString() {
		return this.player.toString();
	}
}
