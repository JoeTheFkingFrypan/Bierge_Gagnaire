package main.java.cards.model;

import java.util.Collection;

import main.java.cards.model.basics.Carte;
import main.java.cards.model.pile.Talon;
import main.java.cards.model.stock.Pioche;
import main.java.console.model.AbstractModel;
import main.java.main.ServerException;

public class GameModel extends AbstractModel {
	private Pioche pioche;
	private Talon talon;
	
	public GameModel () {
		this.pioche = new Pioche();
		this.talon = new Talon();
		drawStarterCard();
	}
	
	private void drawStarterCard() {
		refillStockIfNeeded(1);
		Carte starterCard = this.pioche.drawOneCard();
		this.talon.receiveCard(starterCard);
	}
	
	public Collection<Carte> drawCards(int count) {
		refillStockIfNeeded(count);
		return this.pioche.drawCards(count);
	}

	private void refillStockIfNeeded(int count) {
		if(this.pioche.hasNotEnoughCards(count)) {
			Collection<Carte> cardsFromPile = talon.emptyPile();
			this.pioche.refill(cardsFromPile);
		}
	}
	
	public void playCard(Carte chosenCard) {
		if(this.talon.accept(chosenCard)) {
			this.talon.receiveCard(chosenCard);
		} else {
			throw new ServerException("[FATAL ERROR] Card could not be played --Something went terribly wrong somewhere");
		}
	}
	
	public Carte showLastCardPlayed() {
		return this.talon.showLastCardPlayed();
	}
	
	public int getStockSize() {
		return this.pioche.size();
	}
	
	public int getPileSize() {
		return this.talon.size();
	}
}
