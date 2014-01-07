package utt.fr.rglb.main.java.view.graphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.animation.SequentialTransition;
import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.player.controller.PlayerControllerGraphicsOriented;
import utt.fr.rglb.main.java.view.graphics.fxml.FXMLControllerGameScreen;

public class GraphicsPlayers {
	private static final Logger log = LoggerFactory.getLogger(GraphicsPlayers.class);
	private FXMLControllerGameScreen fxmlControllerGameScreen;
	private List<PlayerControllerGraphicsOriented> players;
	private int indexFromActivePlayer; 
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	public GraphicsPlayers(List<PlayerControllerGraphicsOriented> players, FXMLControllerGameScreen fxmlControllerGameScreen) {
		this.players = players;
		this.indexFromActivePlayer = 0;
		this.fxmlControllerGameScreen = fxmlControllerGameScreen;
	}
	
	/* ========================================= PLAYER ========================================= */
	
	public String getAliasFromPlayerNumber(int playerNumber) {
		Preconditions.checkArgument(playerNumber >= 0 && playerNumber < this.players.size(),"[ERROR] player number is invalid");
		return this.players.get(playerNumber).getAlias();
	}
	
	public String getAliasFromActivePlayer() {
		return this.players.get(this.indexFromActivePlayer).getAlias();
	}
	
	public void setActivePlayer(int indexFromActivePlayer) {
		Preconditions.checkArgument(indexFromActivePlayer >= 0 && indexFromActivePlayer < this.players.size(),"[ERROR] player number is invalid");
		log.debug("Old index = " + indexFromActivePlayer);
		this.indexFromActivePlayer = indexFromActivePlayer;
		log.debug("New index = " + indexFromActivePlayer);
	}
	
	/* ========================================= FIRST TURN ========================================= */

	public boolean initialPlayerNeedsToDraw(CardsModelBean references) {
		Collection<Card> cardsFromPlayer = this.players.get(this.indexFromActivePlayer).getCardsInHand();
		return !references.isCompatibleWith(cardsFromPlayer);
	}
	
	/* ========================================= CARDS ========================================= */
	
	public void test(String alpha, int cardIndex) {
		PlayerControllerGraphicsOriented player = this.players.get(this.indexFromActivePlayer);
		log.debug("[" + player.getAlias() + " - CLICKED] (" + cardIndex + ") " + alpha + " " +  player.peekAtCard(cardIndex).toString());
	}
	
	public Card chooseCardFromActivePlayerAt(int cardIndex, CustomImageView thisImageView) {
		log.debug("chooseCardFromActivePlayerAt");
		Card choosenCard = chooseCardFromPlayer(cardIndex,this.indexFromActivePlayer, thisImageView);
		for(PlayerControllerGraphicsOriented currentPlayer : this.players) {
			currentPlayer.updateCardsCompatibilityAndIndex(choosenCard);
		}
		return choosenCard;
	}

	private Card chooseCardFromPlayer(int cardIndex, int playerNumber, CustomImageView thisImageView) {
		log.debug("chooseCardFromPlayer");
		PlayerControllerGraphicsOriented player = this.players.get(playerNumber);
		log.debug("  //BEFORE : " + player.getNumberOfCardsInHand());
		Card card = player.playCard(cardIndex,thisImageView);
		log.debug("  //AFTER : " + player.getNumberOfCardsInHand());
		return card;
	}
	
	public List<CustomImageView> addCardToPlayer(int playerIndex, Collection<Card> cardsDrawn, CardsModelBean references) {
		Preconditions.checkArgument(playerIndex >= 0 && playerIndex < this.players.size(),"[ERROR] player number is invalid");
		PlayerControllerGraphicsOriented currentPlayer = this.players.get(playerIndex);
		log.debug("Cards given to " + currentPlayer.getAlias());
		currentPlayer.pickUpCards(cardsDrawn);
		return getDisplayableCardsFromPlayer(playerIndex,references);
	}
	
	public List<CustomImageView> addCardToPlayer(int playerIndex, Card cardDrawn, CardsModelBean references) {
		Preconditions.checkArgument(playerIndex >= 0 && playerIndex < this.players.size(),"[ERROR] player number is invalid");
		PlayerControllerGraphicsOriented currentPlayer = this.players.get(playerIndex);
		currentPlayer.pickUpOneCard(cardDrawn);
		return getDisplayableCardsFromPlayer(playerIndex,references);
	}
	
	public void addCardToPlayer(int playerIndex, Card firstCardDrawn, CustomImageView imageView) {
		log.debug("addCardToPlayer");
		Preconditions.checkArgument(playerIndex >= 0 && playerIndex < this.players.size(),"[ERROR] player number is invalid");
		PlayerControllerGraphicsOriented currentPlayer = this.players.get(playerIndex);
		log.debug("  //BEFORE : " + currentPlayer.getNumberOfCardsInHand());
		currentPlayer.pickUpOneCard(firstCardDrawn);
		log.debug("  //AFTER : " + currentPlayer.getNumberOfCardsInHand());
		currentPlayer.addCustomImageView(imageView);
	}


	public int findNextCardIndex(int playerNumber) {
		Preconditions.checkArgument(playerNumber >= 0 && playerNumber < this.players.size(),"[ERROR] player number is invalid");
		PlayerControllerGraphicsOriented currentPlayer = this.players.get(playerNumber);
		return currentPlayer.getNumberOfCardsInHand();
	}
	
	/* ========================================= UTILS ========================================= */
	
	public List<CustomImageView> getDisplayableCardsFromPlayer(int playerIndex, CardsModelBean references) {
		Preconditions.checkArgument(playerIndex >= 0 && playerIndex < this.players.size(),"[ERROR] player number is invalid");
		List<CustomImageView> displayableCards = new ArrayList<CustomImageView>();
		int currentCardIndex = 0;
		PlayerControllerGraphicsOriented currentPlayer = this.players.get(playerIndex);
		for(Card card : currentPlayer.getCardsInHand()) {
			displayableCards.add(new CustomImageView(card, currentCardIndex, references.isCompatibleWith(card),this.fxmlControllerGameScreen));
			currentCardIndex++;
		}
		currentPlayer.setDisplayableCards(displayableCards);
		return displayableCards;
	}
	
	public SequentialTransition generateEffectFromActivePlayer() {
		return createCardAnimationFromPlayer(this.indexFromActivePlayer);
	}
	
	public SequentialTransition generateEffectFromPlayer(int playerIndex) {
		Preconditions.checkArgument(playerIndex >= 0 && playerIndex < this.players.size(),"[ERROR] player number is invalid");
		return createCardAnimationFromPlayer(playerIndex);
	}

	private SequentialTransition createCardAnimationFromPlayer(int playerIndex) {
		Preconditions.checkArgument(playerIndex >= 0 && playerIndex < this.players.size(),"[ERROR] player number is invalid");
		PlayerControllerGraphicsOriented currentPlayer = this.players.get(playerIndex);
		return currentPlayer.generateEffectFromDisplayableCards();
	}

	public boolean findIfActivePlayerStillHasCards() {
		PlayerControllerGraphicsOriented player = this.players.get(indexFromActivePlayer);
		return player.stillHasCards();
	}
	
	public boolean deservesTheRightToAnnounceUno() {
		PlayerControllerGraphicsOriented player = this.players.get(indexFromActivePlayer);
		return player.deservesTheRightToAnnounceUno();
	}
	
	public int getIndexFromActivePlayer() {
		return this.indexFromActivePlayer;
	}
}
