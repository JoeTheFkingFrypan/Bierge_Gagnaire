package main.java.cards.controller;

import java.util.Collection;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Carte;
import main.java.cards.model.basics.CarteSpeciale;
import main.java.cards.model.GameModel;
import main.java.gameContext.model.GameFlags;

public class GameController {
	private GameModel gameModel;
	public GameController() {
		this.gameModel = new GameModel();
	}
	
	public Collection<Carte> drawCards(int count) {
		Preconditions.checkArgument(count>0, "[ERROR] Amount of cards drawn must be strictly higher than 0 (Expected : 1+)");
		return this.gameModel.drawCards(count);
	}

	public Carte showLastCardPlayed() {
		return gameModel.showLastCardPlayed();
	}
	
	public GameFlags playCard(Carte chosenCard) {
		Preconditions.checkNotNull(chosenCard, "[ERROR] Card to play cannot be null");
		this.gameModel.playCard(chosenCard);
		return triggerItsEffectIfItHasOne(chosenCard);
	}

	private GameFlags triggerItsEffectIfItHasOne(Carte chosenCard) {
		if(chosenCard.isSpecial()) {
			CarteSpeciale explicitSpecialCard = (CarteSpeciale)chosenCard;
			return explicitSpecialCard.declencherEffet();
		} else {
			return GameFlags.NORMAL;
		}
	}
}
