package utt.fr.rglb.main.java.player.controller;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.cards.model.GameModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.console.model.InputReader;
import utt.fr.rglb.main.java.console.view.View;
import utt.fr.rglb.main.java.player.AI.CardPickerStrategy;

public class PlayerControllerAI extends PlayerController {
	private CardPickerStrategy cardPickerStrategy;
	
	public PlayerControllerAI(String name,View consoleView, CardPickerStrategy cardPickerStrategy) {
		super(name,consoleView);
		this.cardPickerStrategy = cardPickerStrategy;
	}
	
	@Override
	public Card startTurn(InputReader inputReader, GameModelBean gameModelBean) {
		Preconditions.checkNotNull(inputReader,"[ERROR] Impossible to start turn, inputReader is null");
		Preconditions.checkNotNull(gameModelBean,"[ERROR] Impossible to start turn, gameModelbean is null");
		String alias = this.player.toString();
		Collection<Card> cardCollection = this.getCardsInHand();
		Integer cardsLeft = cardCollection.size();
		this.consoleView.StartOneLineOfBoldText("[IA] ", alias, " is choosing a card ");
		chillForTwoSec(".");
		List<Integer> playableIndexes = gameModelBean.findPlayableCardsFrom(cardCollection);
		int bestIndex = cardPickerStrategy.chooseCardFrom(playableIndexes,cardCollection);
		decideIfAnnouncingUnoIsNecessary(alias);
		Card suitableCard = this.player.playCard(bestIndex);
		this.consoleView.displayCard("He played : ", suitableCard);
		this.consoleView.displayJokerEmphasisUsingPlaceholders("He has ", cardsLeft.toString(), " cards remaining");
		return suitableCard;
	}
	
	/**
	 * Méthode permettant à l'IA de déterminer si l'annonce de UNO doit être faite 
	 * @param alias 
	 */
	public void decideIfAnnouncingUnoIsNecessary(String alias) {
		if(this.player.getNumberOfCardsInHand() == 2) {
			this.player.setUnoAnnoucement();
		}
	}
	
	@Override
	public Color hasToChooseColor(InputReader inputReader) {
		Collection<Card> cardCollection = this.getCardsInHand();
		return this.cardPickerStrategy.chooseBestColor(cardCollection);
	}
	
	@Override
	public void unableToPlayThisTurn(GameModelBean gameModelbean) {
		Preconditions.checkNotNull(gameModelbean,"[ERROR] Impossible to start turn, gameModelbean is null");
		this.consoleView.displayCard("The last card play was : ",gameModelbean.getLastCardPlayed());
		gameModelbean.appendGlobalColorIfItIsSet();
		this.consoleView.displayTwoLinesOfJokerText("Sadly, even after picking a new card, he didn't have any playable","His turn will now automatically end");
		chillForTwoSec("");
	}
	
	@Override
	public void pickUpOneCard(Card card) {
		Preconditions.checkNotNull(card,"[ERROR] Card picked up cannot be null");
		this.consoleView.displayCard("He had no playable cards in you hand, he has drawn : ",card);
		this.player.pickUpOneCard(card);
		this.player.resetUnoAnnoucement();
	}
}
