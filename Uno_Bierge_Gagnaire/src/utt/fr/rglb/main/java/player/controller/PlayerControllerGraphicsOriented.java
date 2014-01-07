package utt.fr.rglb.main.java.player.controller;

import java.util.Collection;
import java.util.List;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.util.Duration;

import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.console.model.InputReader;
import utt.fr.rglb.main.java.player.model.PlayerModel;
import utt.fr.rglb.main.java.view.AbstractView;
import utt.fr.rglb.main.java.view.graphics.CustomImageView;

public class PlayerControllerGraphicsOriented extends AbstractPlayerController {
	private static final long serialVersionUID = 1L;
	private List<CustomImageView> displayableCards;

	public PlayerControllerGraphicsOriented(String name, AbstractView consoleView) {
		Preconditions.checkNotNull(name,"[ERROR] name cannot be null");
		Preconditions.checkNotNull(consoleView,"[ERROR] view cannot be null");
		this.player = new PlayerModel(name);
		this.consoleView = consoleView;
	}
	
/* ========================================= CARD PICKUP ========================================= */
	
	@Override
	public Collection<Card> getCardsInHand() {
		return this.player.getCardsInHand();
	}
	
	public Card peekAtCard(int cardIndex) {
		return this.player.peekAtCard(cardIndex);
	}
	
	@Override
	public void pickUpCards(Collection<Card> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Card collection picked up cannot be null");
		Preconditions.checkArgument(cards.size()>0, "[ERROR] Card collection picked cannot be empty");
		this.player.pickUpCards(cards);
		this.player.resetUnoAnnoucement();
	}

	@Override
	public void pickUpOneCard(Card card) {
		Preconditions.checkNotNull(card,"[ERROR] Card picked up cannot be null");
		this.consoleView.displayCard("You had no playable cards in you hand, you've drawn : ",card);
		this.player.pickUpOneCard(card);
		this.player.resetUnoAnnoucement();
	}
	
	@Override
	public void isForcedToPickUpCards(Collection<Card> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Card collection picked up cannot be null");
		Preconditions.checkArgument(cards.size()>0, "[ERROR] Card collection picked cannot be empty");
		Integer numberOfCards = cards.size();
		this.consoleView.displayErrorMessageUsingPlaceholders("Player [",getAlias(),"] was forced to draw ",numberOfCards.toString()," cards");
		this.player.pickUpCards(cards);
		this.player.resetUnoAnnoucement();
	}
	
	@Override
	public void isForcedToPickUpCardsLegitCase(Collection<Card> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Card collection picked up cannot be null");
		Preconditions.checkArgument(cards.size()>0, "[ERROR] Card collection picked cannot be empty");
		Integer numberOfCards = cards.size();
		this.consoleView.displayErrorMessageUsingPlaceholders("Sadly for you, previous player ", "wasn't ", " bluffing");
		this.consoleView.displayErrorMessageUsingPlaceholders("Player [",getAlias(),"] receives ",numberOfCards.toString()," cards for accusing previous player of bluffing");
		this.player.pickUpCards(cards);
		this.player.resetUnoAnnoucement();
	}
	
	@Override
	public void isForcedToPickUpCardsBluffCase(Collection<Card> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Card collection picked up cannot be null");
		Preconditions.checkArgument(cards.size()>0, "[ERROR] Card collection picked cannot be empty");
		Integer numberOfCards = cards.size();
		this.consoleView.displayGreenEmphasisUsingPlaceholders("Player [",getAlias(),"] got caught bluffing : first of all he receives ",numberOfCards.toString()," cards");
		this.player.pickUpCards(cards);
		this.player.resetUnoAnnoucement();
	}

	/* ========================================= TURN HANDLING ========================================= */
	
	@Override
	public Card playCard(int index, CustomImageView thisImageView) {
		Preconditions.checkState(this.player.getNumberOfCardsInHand() > 0, "[ERROR] Impossible to play a card : player has none");
		Preconditions.checkArgument(index >= 0 && index < this.player.getNumberOfCardsInHand(),"[ERROR] Incorrect index : must be > 0 (tried = " + index + ", but max is = " + this.player.getNumberOfCardsInHand());
		this.displayableCards.remove(thisImageView);
		return this.player.playCard(index);
	}
	
	@Override
	public Card startTurn(InputReader inputReader, CardsModelBean gameModelBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean findIfBluffIsNeeded(InputReader inputReader,
			CardsModelBean gameModelBean, Card choosenCard,
			boolean wantsToPlayAnotherCard) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unableToPlayThisTurn(CardsModelBean gameModelbean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color hasToChooseColor(boolean isRelatedToPlus4,
			InputReader inputReader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean askIfHeWantsToCheckIfItsLegit(InputReader inputReader) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void chillForTwoSec(String stringToDisplay) {
		// TODO Auto-generated method stub
		
	}

	public void setDisplayableCards(List<CustomImageView> displayableCards) {
		this.displayableCards = displayableCards;
	}

	public SequentialTransition generateEffectFromDisplayableCards() {
		SequentialTransition cardsAnimation = new SequentialTransition();
		for(CustomImageView imageView : this.displayableCards) {
			cardsAnimation.getChildren().add(imageView.generateEffect());
			cardsAnimation.getChildren().add(new PauseTransition(Duration.millis(50)));
		}
		return cardsAnimation;
	}

	public void addCustomImageView(CustomImageView imageView) {
		this.displayableCards.add(imageView);
	}

	public void updateCardsCompatibilityAndIndex(Card chosenCard) {
		int currentCardIndex = 0;
		for(CustomImageView imageView : this.displayableCards) {
			imageView.setNewCompatibilityAndIndex(chosenCard,currentCardIndex);
			currentCardIndex++;
		}
	}
}
