package main.java.cards.model;

import java.util.Collection;

import main.java.cards.model.basics.Carte;
import main.java.cards.model.pile.Talon;
import main.java.cards.model.stock.Pioche;
import main.java.console.model.AbstractModel;

public class CardsModel extends AbstractModel {
	private Pioche pioche;
	private Talon talon;
	
	public CardsModel () {
		this.pioche = new Pioche();
		this.talon = new Talon();
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
}
