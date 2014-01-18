package utt.fr.rglb.main.java.player.controller;

import com.google.common.base.Preconditions;

import java.io.BufferedReader;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.console.model.InputReader;
import utt.fr.rglb.main.java.main.ServerException;
import utt.fr.rglb.main.java.player.model.PlayerModel;
import utt.fr.rglb.main.java.view.AbstractView;

/**
 * Classe dont le rôle est de gérer tout ce qui touche à un joueur
 */
public class PlayerControllerConsoleOriented extends AbstractPlayerController {
	private static final long serialVersionUID = 1L;
	private BufferedReader inputStream;

	/* ========================================= CONSTRUCTOR ========================================= */

	public PlayerControllerConsoleOriented(String name, AbstractView consoleView, BufferedReader inputStream) {
		Preconditions.checkNotNull(name,"[ERROR] name cannot be null");
		Preconditions.checkNotNull(consoleView,"[ERROR] view cannot be null");
		this.player = new PlayerModel(name);
		this.consoleView = consoleView;
		this.inputStream = inputStream;
	}

	/* ========================================= CARD PICKUP ========================================= */
	
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
	public Card startTurn(InputReader inputReader, CardsModelBean gameModelBean) {
		Preconditions.checkNotNull(inputReader,"[ERROR] Impossible to start turn, inputReader is null");
		Preconditions.checkNotNull(gameModelBean,"[ERROR] Impossible to start turn, gameModelbean is null");
		String alias = this.player.toString();
		Collection<Card> cardCollection = this.getCardsInHand();
		String answer = inputReader.getValidAnswer(alias,cardCollection,gameModelBean,this.inputStream);
		int index = inputReader.getNumberFromString(answer,this.inputStream);
		boolean unoHasBeenAnnounced = inputReader.findIfUnoHasBeenAnnounced(answer);
		Card choosenCard = this.player.peekAtCard(index);
		boolean wantsToPlayAnotherCard = false;
		if(choosenCard.isPlusFour()) {
			wantsToPlayAnotherCard = findIfBluffIsNeeded(inputReader,gameModelBean, choosenCard, wantsToPlayAnotherCard);
		}
		while(!gameModelBean.isCompatibleWith(choosenCard) || wantsToPlayAnotherCard) {
			answer = inputReader.getAnotherValidAnswerFromInputDueToIncompatibleCard(alias,cardCollection,gameModelBean,this.inputStream);
			index = inputReader.getNumberFromString(answer,this.inputStream);
			unoHasBeenAnnounced = inputReader.findIfUnoHasBeenAnnounced(answer);
			choosenCard = this.player.peekAtCard(index);
			if(choosenCard.isPlusFour()) {
				wantsToPlayAnotherCard = findIfBluffIsNeeded(inputReader,gameModelBean, choosenCard, wantsToPlayAnotherCard);
			} else {
				wantsToPlayAnotherCard = false;
			}
		}
		if(unoHasBeenAnnounced) {
			this.player.setUnoAnnoucement();
		}
		return this.player.playCard(index);
	}


	@Override
	protected boolean findIfBluffIsNeeded(InputReader inputReader, CardsModelBean gameModelBean, Card choosenCard, boolean wantsToPlayAnotherCard) {
		boolean hasPlayableCardsAsideFromPlusFour = gameModelBean.findIfPlayerHasPlayableCardsAsideFromPlusFour(this.player.getCardsInHand());
		if(hasPlayableCardsAsideFromPlusFour) {
			wantsToPlayAnotherCard = inputReader.askIfHeWantsToPlayAnotherCard(this.inputStream);
			if(!wantsToPlayAnotherCard) {
				choosenCard.setBluffOn();
			}
		}
		return wantsToPlayAnotherCard;
	}


	@Override
	public void unableToPlayThisTurn(CardsModelBean gameModelbean) {
		Preconditions.checkNotNull(gameModelbean,"[ERROR] Impossible to start turn, gameModelbean is null");
		Collection<Card> cardsInHand = this.player.getCardsInHand();
		this.consoleView.displayCardCollection("You now have : ",cardsInHand);
		this.consoleView.displayCard("The last card play was : ",gameModelbean.getLastCardPlayed());
		gameModelbean.appendGlobalColorIfItIsSet();
		this.consoleView.displayTwoLinesOfJokerText("Sadly, even after picking a new card, you didn't have any playable","Your turn will now automatically end");
		chillForTwoSec("");
	}

	/* ========================================= EFFECTS RELATED ========================================= */


	@Override
	public Color hasToChooseColor(boolean isRelatedToPlus4, InputReader inputReader) {
		Preconditions.checkNotNull(inputReader,"[ERROR] Impossible to start turn, inputReader is null");
		if(isRelatedToPlus4) {
			this.consoleView.displaySeparationText("---- Back to you, " + this.getAlias() + " ----");
		}
		this.consoleView.displayOneLineOfJokerText("You played a Joker, please choose a color");
		return inputReader.getValidColor(this.inputStream);
	}


	@Override
	public boolean askIfHeWantsToCheckIfItsLegit(InputReader inputReader) {
		this.consoleView.displaySeparationText("---- Your call, " + this.getAlias() + " ----");
		Preconditions.checkNotNull(inputReader,"[ERROR] Impossible to start turn, inputReader is null");
		this.consoleView.displayOneLineOfJokerText("Previous player played a +4");
		return inputReader.askIfHeWantsToCheckIfItsLegit(this.inputStream);
	}
	
	/* ========================================= GETTERS & UTILS ========================================= */

	@Override
	protected void chillForTwoSec(String stringToDisplay) {
		Preconditions.checkNotNull(stringToDisplay,"[ERROR] Impossible display message while waiting : provided message is null");
		try {
			for(int i=0; i<4; i++) {
				Thread.sleep(500);
				this.consoleView.AppendOneLineOfBoldText(stringToDisplay);
			}
		} catch (InterruptedException e) {
			throw new ServerException("[ERROR] Something went wrong while [IA] " + this.getAlias() + " was peacefully chilling",e);
		}
	}
}