package main.java.gameContext.controller;

import java.util.Collection;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Carte;
import main.java.gameContext.model.GameModel;

public class GameController {
	private GameModel gameModel;
	
	public GameController() {
		this.gameModel = new GameModel();
	}
	
	public Collection<Carte> drawCards(int count) {
		Preconditions.checkArgument(count>0, "[ERROR] Amount of cards drawn must be strictly higher than 0 (Expected : 1+)");
		return this.gameModel.drawCards(count);
	}
	
	public boolean playCard(Carte chosenCard) {
		Preconditions.checkNotNull(chosenCard, "[ERROR] Card to play cannot be null");
		return this.gameModel.playCard(chosenCard);
	}

	public Carte showLastCardPlayed() {
		return gameModel.showLastCardPlayed();
	}
}
