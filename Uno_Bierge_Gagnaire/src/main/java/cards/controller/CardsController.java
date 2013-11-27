package main.java.cards.controller;

import java.util.Collection;
import com.google.common.base.Preconditions;

import main.java.cards.model.CardsModel;
import main.java.cards.model.basics.Carte;

public abstract class CardsController {
	private CardsModel cardsModel;
	
	public CardsController (CardsModel cardsModel) {
		Preconditions.checkNotNull(cardsModel,"[ERROR] Model cannot be null");
		this.cardsModel = cardsModel;
	}
	
	public Collection<Carte> drawCards(int count) {
		return this.cardsModel.drawCards(count);
	}
	
	public boolean playCard(Carte chosenCard) {
		return this.cardsModel.playCard(chosenCard);
	}
}
