package main.java.gameContext.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import main.java.player.controller.PlayerController;

public class TurnModel {
	private LinkedList<PlayerController> players;
	private TurnOrder turnOrder;
	private int currentPlayerIndex;

	public TurnModel() {
		this.turnOrder = TurnOrder.Clockwise;
		this.players = new LinkedList<PlayerController>();
		this.currentPlayerIndex = -1;
	}

	public void createPlayersFrom(Collection<String> playerNames) {
		for(String name: playerNames) {
			this.players.add(new PlayerController(name));
		}
		scramblePlayers();
	}

	public void createPlayersWithoutScramblingFrom(Collection<String> playerNames) {
		for(String name: playerNames) {
			this.players.add(new PlayerController(name));
		}
	}

	private void scramblePlayers() {
		Collections.shuffle(this.players);
	}

	public void reverseCurrentOrder() {
		if(indicatesDefaultTurnOrder()) {
			this.turnOrder = TurnOrder.CounterClockwise;
		} else {
			this.turnOrder = TurnOrder.Clockwise;
		}
	}

	public boolean indicatesDefaultTurnOrder() {
		return this.turnOrder.equals(TurnOrder.Clockwise);
	}

	public PlayerController cycleThroughPlayers() {
		if(this.indicatesDefaultTurnOrder()) {
			return this.players.get(findNextPlayerIndex());
		} else {
			return this.players.get(findPreviousPlayerIndex());
		}
	}

	private int findNextPlayerIndex() {
		++(this.currentPlayerIndex);
		if(this.currentPlayerIndex >= this.players.size()) {
			this.currentPlayerIndex = 0;
		}
		return this.currentPlayerIndex;
	}

	private int findPreviousPlayerIndex() {
		--(this.currentPlayerIndex);
		if(this.currentPlayerIndex < 0) {
			this.currentPlayerIndex = this.players.size() - 1;
		}
		return this.currentPlayerIndex;
	}

	public int getNumberOfPlayers() {
		return this.players.size();
	}
}
