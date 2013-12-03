package main.java.player.IA;

import java.util.ArrayList;

import main.java.cards.model.basics.Card;

public interface CardPickerStrategy {
	public Card choseCardFrom(ArrayList<Card> cards);
}
