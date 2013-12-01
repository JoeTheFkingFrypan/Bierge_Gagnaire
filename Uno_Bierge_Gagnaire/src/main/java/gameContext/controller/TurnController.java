package main.java.gameContext.controller;

import java.util.Collection;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Carte;
import main.java.gameContext.model.TurnModel;
import main.java.player.controller.PlayerController;

public class TurnController {
	private TurnModel turnModel;
	
	public TurnController() {
		this.turnModel = new TurnModel();
	}
	
	public void createPlayersFrom(Collection<String> playerNames) {
		Preconditions.checkNotNull(playerNames,"[ERROR] Provided played names cannot be null");
		Preconditions.checkArgument(playerNames.size() >= 2,"[ERROR] There must be at least two players in the game");
		this.turnModel.createPlayersFrom(playerNames);
	}
	
	public void createPlayersWithoutScamblingFrom(Collection<String> playerNames) {
		Preconditions.checkNotNull(playerNames,"[ERROR] Provided played names cannot be null");
		Preconditions.checkArgument(playerNames.size() >= 2,"[ERROR] There must be at least two players in the game");
		this.turnModel.createPlayersWithoutScramblingFrom(playerNames);
	}
	
	public void reverseCurrentOrder() {
		this.turnModel.reverseCurrentOrder();
	}
	
	public PlayerController findNextPlayer() {
		return this.turnModel.cycleThroughPlayers();
	}
	
	public void giveCardsToNextPlayer(Collection<Carte> cards) {
		this.turnModel.giveCardsToNextPlayer(cards);
	}
}