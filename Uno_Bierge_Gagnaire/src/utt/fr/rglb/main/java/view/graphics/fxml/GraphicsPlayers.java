package utt.fr.rglb.main.java.view.graphics.fxml;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.animation.SequentialTransition;
import javafx.scene.image.Image;
import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.dao.ImageCardAssociator;
import utt.fr.rglb.main.java.player.controller.PlayerControllerGraphicsOriented;

public class GraphicsPlayers {
	private static final Logger log = LoggerFactory.getLogger(GraphicsPlayers.class);
	private FXMLControllerGameScreen fxmlControllerGameScreen;
	private List<PlayerControllerGraphicsOriented> players;
	private int indexFromActivePlayer; 
	
	public GraphicsPlayers(List<PlayerControllerGraphicsOriented> players, FXMLControllerGameScreen fxmlControllerGameScreen) {
		this.players = players;
		this.indexFromActivePlayer = 0;
		this.fxmlControllerGameScreen = fxmlControllerGameScreen;
	}
	
	public String getAliasFromPlayerNumber(int playerNumber) {
		Preconditions.checkArgument(playerNumber >= 0 && playerNumber < this.players.size(),"[ERROR] player number is invalid");
		return this.players.get(playerNumber).getAlias();
	}
	
	public String getAliasFromActivePlayer() {
		return this.players.get(this.indexFromActivePlayer).getAlias();
	}
	
	public List<CustomImageView> getDisplayableCardsFromPlayer(int playerNumber, CardsModelBean references) {
		Preconditions.checkArgument(playerNumber >= 0 && playerNumber < this.players.size(),"[ERROR] player number is invalid");
		List<CustomImageView> displayableCards = new ArrayList<CustomImageView>();
		int currentCardIndex = 0;
		PlayerControllerGraphicsOriented currentPlayer = this.players.get(playerNumber);
		for(Card card : currentPlayer.getCardsInHand()) {
			Image imageFromCard = ImageCardAssociator.retrieveImageFromIndex(card.getImageIndex());
			displayableCards.add(new CustomImageView(imageFromCard, currentCardIndex, references.isCompatibleWith(card),this.fxmlControllerGameScreen));
			currentCardIndex++;
		}
		currentPlayer.setDisplayableCards(displayableCards);
		return displayableCards;
	}
	
	public Card chooseCardFromActivePlayerAt(int cardIndex) {	
		return chooseCardFromPlayer(cardIndex,this.indexFromActivePlayer);
	}

	private Card chooseCardFromPlayer(int cardIndex, int playerNumber) {
		return this.players.get(playerNumber).peekAtCard(cardIndex);
		
	}
	public SequentialTransition generateEffectFromActivePlayer() {
		return createCardAnimationFromPlayer(this.indexFromActivePlayer);
	}

	private SequentialTransition createCardAnimationFromPlayer(int playerNumber) {
		Preconditions.checkArgument(playerNumber >= 0 && playerNumber < this.players.size(),"[ERROR] player number is invalid");
		PlayerControllerGraphicsOriented currentPlayer = this.players.get(playerNumber);
		return currentPlayer.generateEffectFromDisplayableCards();
	}

	public void setActivePlayer(int indexFromActivePlayer) {
		Preconditions.checkArgument(indexFromActivePlayer >= 0 && indexFromActivePlayer < this.players.size(),"[ERROR] player number is invalid");
		this.indexFromActivePlayer = indexFromActivePlayer;
	}

	public int addCardToPlayer(int playerNumber, Card firstCardDrawn) {
		Preconditions.checkArgument(playerNumber >= 0 && playerNumber < this.players.size(),"[ERROR] player number is invalid");
		PlayerControllerGraphicsOriented currentPlayer = this.players.get(playerNumber);
		int cardIndex = currentPlayer.getNumberOfCardsInHand();
		currentPlayer.pickUpOneCard(firstCardDrawn);
		return cardIndex;
	}
	
}
