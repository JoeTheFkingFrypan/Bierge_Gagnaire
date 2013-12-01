package main.java.player.controller;

import java.util.Collection;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Carte;
import main.java.console.model.InputReader;
import main.java.console.view.View;
import main.java.player.model.PlayerModel;

public class PlayerController {
	private PlayerModel player;
	private View consoleView;

	public PlayerController(String name, View consoleView) {
		Preconditions.checkNotNull(name,"[ERROR] name cannot be null");
		this.player = new PlayerModel(name);
		this.consoleView = consoleView;
	}

	public void pickUpCards(Collection<Carte> c) {
		Preconditions.checkNotNull(c,"[ERROR] Card collection picked up cannot be null");
		Preconditions.checkArgument(c.size()>0, "[ERROR] Card collection picked cannot be empty");
		this.player.pickUpCards(c);
	}

	public void pickUpOneCard(Carte card) {
		Preconditions.checkNotNull(card,"[ERROR] Card picked up cannot be null");
		this.consoleView.appendBoldText("You had no playable cards in you hand, you've drawn : ");
		this.consoleView.displayCard(card);
		this.player.pickUpOneCard(card);
	}

	public Carte playCard(int index) {
		Preconditions.checkState(this.player.getNumberOfCardsInHand() > 0, "[ERROR] Impossible to play a card : player has none");
		Preconditions.checkArgument(index >= 0 && index < this.player.getNumberOfCardsInHand(),"[ERROR] Incorrect index : must be > 0 (tried = " + index + ", but max is = " + this.player.getNumberOfCardsInHand());
		return this.player.playCard(index);
	}

	public String getAlias() {
		return this.player.getAlias();
	}

	public int getScore() {
		return this.player.getScore();
	}

	public int getNumberOfCardsInHand() {
		return this.player.getNumberOfCardsInHand();
	}

	@Override
	public String toString() {
		return this.player.toString();
	}

	private Collection<Carte> getDisplayableCardCollection() {
		return this.player.getCardsInHand();
	}

	public boolean hasAtLeastOnePlayableCard(Carte referenceCard) {
		Collection<Carte> cardCollection = this.getDisplayableCardCollection();
		for(Carte currentCard : cardCollection) {
			if(referenceCard.isCompatibleWith(currentCard)) {
				return true;
			}
		}
		return false;
	}

	public void unableToPlayThisTurn(Carte currentCard) {
		Collection<Carte> cardsInHand = this.player.getCardsInHand();
		this.consoleView.appendBoldText("You now have : ");
		this.consoleView.displayCardCollection(cardsInHand);
		this.consoleView.appendBoldText("The last card play was : ");
		this.consoleView.displayCard(currentCard);
		this.consoleView.displayBoldText("Sadly, even after picking a new card, you didn't have any playable");
		this.consoleView.displayBoldText("Your turn will now automatically end");
	}

	public Carte startTurn(InputReader inputReader, Carte referenceCard) {
		String alias = this.player.getAlias();
		Collection<Carte> cardCollection = this.getDisplayableCardCollection();
		int index = inputReader.getFirstValidIndexFromInput(alias,cardCollection,referenceCard);
		Carte choosenCard = this.player.peekAtCard(index);
		while(!choosenCard.isCompatibleWith(referenceCard)) {
			index = inputReader.getAnotherValidIndexFromInputDueToIncompatibleCard(alias,cardCollection,referenceCard);
			choosenCard = this.player.peekAtCard(index);
		}
		return this.player.playCard(index);
	}
}
