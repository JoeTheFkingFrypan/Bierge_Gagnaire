package main.java.player.IA;

import java.util.ArrayList;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Carte;

public abstract class CardPickerStrategyImpl implements CardPickerStrategy {

	@Override
	public Carte choseCardFrom(ArrayList<Carte> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Cannot chose card : collection is null");
		Preconditions.checkArgument(cards.size() > 0,"[ERROR] Cannot chose card : collection is empty");
		return cards.get(findBestCardToPlay(cards));
	}
	
	protected abstract int findBestCardToPlay(ArrayList<Carte> cards);
}
