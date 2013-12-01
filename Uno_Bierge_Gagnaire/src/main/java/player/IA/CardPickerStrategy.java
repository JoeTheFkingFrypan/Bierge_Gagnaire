package main.java.player.IA;

import java.util.ArrayList;

import main.java.cards.model.basics.Carte;

public interface CardPickerStrategy {
	public Carte choseCardFrom(ArrayList<Carte> cards);
}
