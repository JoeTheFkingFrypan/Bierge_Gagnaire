package main.java.gameContext.controller;

import java.util.Collection;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Carte;
import main.java.gameContext.model.GameModel;

public class GameController {
	private GameModel gameModel;
	
	@Deprecated
	public GameController (GameModel cardsModel) {
		Preconditions.checkNotNull(cardsModel,"[ERROR] Model cannot be null");
		this.gameModel = cardsModel;
	}
	
	public GameController() {
		this.gameModel = new GameModel();
	}
	
	public Collection<Carte> drawCards(int count) {
		return this.gameModel.drawCards(count);
	}
	
	public boolean playCard(Carte chosenCard) {
		return this.gameModel.playCard(chosenCard);
	}
	
	public void reverseCurrentOrder() {
		gameModel.reverseCurrentOrder();
	}
	
	public boolean indicatesNormalTurnOrder() {
		return gameModel.indicatesDefaultTurnOrder();
	}
}
