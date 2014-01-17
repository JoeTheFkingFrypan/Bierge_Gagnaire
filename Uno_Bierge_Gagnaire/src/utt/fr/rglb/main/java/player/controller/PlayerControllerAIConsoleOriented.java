package utt.fr.rglb.main.java.player.controller;

import com.google.common.base.Preconditions;

import java.io.BufferedReader;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.console.model.InputReader;
import utt.fr.rglb.main.java.player.AI.CardPickerStrategy;
import utt.fr.rglb.main.java.view.AbstractView;

/**
 * Classe correspondant à un joueur controllé par une IA </br>
 * Version console
 */
public class PlayerControllerAIConsoleOriented extends PlayerControllerConsoleOriented {
	private static final long serialVersionUID = 1L;
	private CardPickerStrategy cardPickerStrategy;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	public PlayerControllerAIConsoleOriented(String name,AbstractView consoleView, CardPickerStrategy cardPickerStrategy, BufferedReader inputStream) {
		super(name,consoleView,inputStream);
		Preconditions.checkNotNull(cardPickerStrategy,"[ERROR] Impossible create AI player : provided strategy is null");
		this.cardPickerStrategy = cardPickerStrategy;
	}
	
	/* ========================================= CARD PICKUP ========================================= */
	
	@Override
	public void pickUpOneCard(Card card) {
		Preconditions.checkNotNull(card,"[ERROR] Card picked up cannot be null");
		this.consoleView.displayCard("He had no playable cards in you hand, he has drawn : ",card);
		this.player.pickUpOneCard(card);
		this.player.resetUnoAnnoucement();
	}
	
	/* ========================================= TURN HANDLING ========================================= */
	
	@Override
	public Card startTurn(InputReader inputReader, CardsModelBean gameModelBean) {
		Preconditions.checkNotNull(inputReader,"[ERROR] Impossible to start turn, inputReader is null");
		Preconditions.checkNotNull(gameModelBean,"[ERROR] Impossible to start turn, gameModelbean is null");
		String alias = this.player.toString();
		Collection<Card> cardCollection = this.getCardsInHand();
		Integer cardsLeft = cardCollection.size();
		this.consoleView.StartOneLineOfBoldText("[IA] ", alias, " is choosing a card ");
		chillForTwoSec(".");
		Collection<Integer> playableIndexes = gameModelBean.findPlayableCardsFrom(cardCollection);
		int bestIndex = cardPickerStrategy.chooseCardFrom(playableIndexes,cardCollection);
		decideIfAnnouncingUnoIsNecessary(alias);
		Card suitableCard = this.player.playCard(bestIndex);
		if(suitableCard.isPlusFour()) {
			boolean hasPlayableCardsAsideFromPlusFour = gameModelBean.findIfPlayerHasPlayableCardsAsideFromPlusFour(cardCollection);
			if(hasPlayableCardsAsideFromPlusFour) {
				suitableCard.setBluffOn();
			}
		}
		
		this.consoleView.displayCard("He played : ", suitableCard);
		this.consoleView.displayJokerEmphasisUsingPlaceholders("He has ", cardsLeft.toString(), " cards remaining");
		return suitableCard;
	}
	
	/**
	 * Méthode permettant à l'IA de déterminer si l'annonce de UNO doit être faite 
	 * @param alias 
	 */
	public void decideIfAnnouncingUnoIsNecessary(String alias) {
		Preconditions.checkNotNull(cardPickerStrategy,"[ERROR] Impossible to decided if annoucing UNO is necessary : provided player name is null");
		if(this.player.getNumberOfCardsInHand() == 2) {
			this.player.setUnoAnnoucement();
		}
	}
	
	@Override
	public void unableToPlayThisTurn(CardsModelBean gameModelbean) {
		Preconditions.checkNotNull(gameModelbean,"[ERROR] Impossible to start turn, gameModelbean is null");
		this.consoleView.displayCard("The last card play was : ",gameModelbean.getLastCardPlayed());
		gameModelbean.appendGlobalColorIfItIsSet();
		this.consoleView.displayTwoLinesOfJokerText("Sadly, even after picking a new card, he didn't have any playable","His turn will now automatically end");
		chillForTwoSec("");
	}
	
	/* ========================================= EFFECTS RELATED ========================================= */
	
	@Override
	public Color hasToChooseColor(boolean isRelatedToPlus4,InputReader inputReader) {
		Preconditions.checkNotNull(cardPickerStrategy,"[ERROR] Impossible to choose a color : provided inputReader is null");
		Collection<Card> cardCollection = this.getCardsInHand();
		return this.cardPickerStrategy.chooseBestColor(cardCollection);
	}
	
	@Override
	public boolean askIfHeWantsToCheckIfItsLegit(InputReader inputReader) {
		Preconditions.checkNotNull(inputReader,"[ERROR] Impossible to start turn, inputReader is null");
		this.consoleView.displayOneLineOfJokerText("Previous player played a +4");
		Collection<Card> cardCollection = this.getCardsInHand();
		return this.cardPickerStrategy.chooseIfAccusingPreviousPlayerOfBluffingIsWorthIt(cardCollection);
	}
}
