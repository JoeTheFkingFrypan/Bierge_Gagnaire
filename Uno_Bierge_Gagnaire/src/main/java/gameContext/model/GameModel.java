package main.java.gameContext.model;

import java.util.Collection;

import main.java.cards.model.basics.Carte;
import main.java.cards.model.pile.Talon;
import main.java.cards.model.stock.Pioche;
import main.java.console.model.AbstractModel;

public class GameModel extends AbstractModel {
	private Pioche pioche;
	private Talon talon;
	private TurnOrder currentOrder;
	
	public GameModel () {
		this.pioche = new Pioche();
		this.talon = new Talon();
		this.currentOrder = TurnOrder.Clockwise;
	}
	
	public Collection<Carte> drawCards(int count) {
		if(this.pioche.hasNotEnoughCards(count)) {
			Collection<Carte> cardsFromPile = talon.emptyPile();
			this.pioche.refill(cardsFromPile);
		}
		return this.pioche.drawCards(count);
	}
	
	public boolean playCard(Carte chosenCard) {
		//TODO: find out if there's something better
		if(this.talon.accept(chosenCard)) {
			this.talon.receiveCard(chosenCard);
			return true;
		} else {
			return false;
		}
	}
	
	public void reverseCurrentOrder() {
		if(indicatesDefaultTurnOrder()) {
			this.currentOrder = TurnOrder.CounterClockwise;
		} else {
			this.currentOrder = TurnOrder.Clockwise;
		}
	}
	
	public boolean indicatesDefaultTurnOrder() {
		return this.currentOrder.equals(TurnOrder.Clockwise);
	}
}
