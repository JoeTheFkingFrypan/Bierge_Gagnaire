package utt.fr.rglb.main.java.game.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.Scene;
import utt.fr.rglb.main.java.cards.controller.CardsControllerGraphicsOriented;
import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.dao.ConfigurationReader;
import utt.fr.rglb.main.java.main.ServerException;
import utt.fr.rglb.main.java.player.controller.AbstractPlayerController;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.controller.PlayerControllerGraphicsOriented;
import utt.fr.rglb.main.java.player.model.PlayerTeam;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;
import utt.fr.rglb.main.java.turns.controller.TurnControllerGraphicsOriented;
import utt.fr.rglb.main.java.view.graphics.GraphicsReferences;
import utt.fr.rglb.main.java.view.graphics.GraphicsView;
import utt.fr.rglb.main.java.view.graphics.fxml.FXMLControllerGameScreen;

public class GameModelGraphicsOriented extends AbstractGameModel {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(GameModelGraphicsOriented.class);
	private TurnControllerGraphicsOriented turnController;
	private CardsControllerGraphicsOriented cardsController;
	protected GameRule gameRule;
	@SuppressWarnings("unused") private GraphicsView view;
	private FXMLControllerGameScreen fxmlController;

	/* ========================================= CONSTRUCTOR ========================================= */

	public GameModelGraphicsOriented(GraphicsView view) {
		this.turnController = new TurnControllerGraphicsOriented(view);
		this.cardsController = new CardsControllerGraphicsOriented(view);
		this.view = view;
	}

	public void setCurrentFXMLController(FXMLControllerGameScreen fxmlController) {
		this.fxmlController = fxmlController;
	}

	/* ========================================= INITIALIZING ========================================= */

	/**
	 * Méthode permettant d'initialiser les paramètres (nombre de joueurs, nom de chacun des joueurs)
	 */
	public void initializeGameSettings(GameRule choosenRules, PlayersToCreate playersToCreate, Scene scene) {
		this.gameRule = choosenRules;
		this.turnController.createPlayersFrom(playersToCreate);
		if(this.gameRule.indicatesTeamPlayScoring()) {
			this.turnController.splitPlayersIntoTeams();
			this.turnController.displayTeams(scene);
		}
	}

	public PlayersToCreate retrievePlayerDataFromFile() {
		ConfigurationReader configurationReader = new ConfigurationReader();
		return configurationReader.readConfigurationAt("dist/config.ini");
	}

	@Override
	public void initializeCardsAndHands() {
		this.cardsController.resetCards();
		for(int i=0; i<this.turnController.getNumberOfPlayers(); i++) {
			Collection<Card> cardsDrawn = this.cardsController.drawCards(7);
			this.turnController.giveCardsToNextPlayer(cardsDrawn);
		}
	}

	@Override
	public void resetEverything() {
		this.cardsController.resetCards();
		this.turnController.resetTurn();
		this.gameRule.resetFlag();
	}

	@Override
	public void resetCards() {
		this.cardsController.resetCards();
		this.turnController.removeCardsFromPlayers();
		this.turnController.resetPlayerIndex();
	}

	/* ========================================= GAME LOGIC ========================================= */

	public void playOneTurn(boolean needsCardFlipping) {
		CardsModelBean references = this.cardsController.getReferences();
		PlayerControllerGraphicsOriented currentPlayer = this.turnController.findNextPlayer();
		GraphicsReferences graphicsReferences = new GraphicsReferences(references,this.turnController.getIndexFromActivePlayer());

		if(!currentPlayer.hasAtLeastOnePlayableCard(references)) {
			Card firstCardDrawn = this.cardsController.drawOneCard();
			if(references.isCompatibleWith(firstCardDrawn)) {
				graphicsReferences.setNeedOfDrawingOneTime(firstCardDrawn);
			} else {
				Card secondCardDrawn = this.cardsController.drawOneCard();
				graphicsReferences.setNeedOfDrawingTwoTimes(firstCardDrawn,secondCardDrawn);
			}
		}
		this.fxmlController.playOneTurn(graphicsReferences,needsCardFlipping);
	}

	@Override
	protected void chooseCardAndPlayIt(CardsModelBean gameModelbean, AbstractPlayerController currentPlayer) {}

	/* ========================================= EFFECTS ========================================= */

	public GameFlag triggerEffectFromFirstCard() {
		GameFlag effectFromFirstCard = this.cardsController.applyEffectFromAnotherFirstCard();
		while(effectFromFirstCard.equals(GameFlag.PLUS_FOUR) || effectFromFirstCard.equals(GameFlag.PLUS_FOUR_BLUFFING)) {
			log.info("First card was a +4, drawing another one as starter");
			effectFromFirstCard = this.cardsController.applyEffectFromAnotherFirstCard();
		}
		return effectFromFirstCard;
	}

	public void applyEffectFromFirstCard(GameFlag effectFromFirstCard) {
		if(effectFromFirstCard.equals(GameFlag.SKIP)) {
			this.turnController.skipNextPlayer();
			int playerIndex = this.turnController.getIndexFromActivePlayer();
			this.fxmlController.displayMessage("First player skipped");
			this.fxmlController.setActivePlayer(playerIndex);
		} else if(effectFromFirstCard.equals(GameFlag.REVERSE)) {
			this.fxmlController.displayMessage("Turn order inverted");
		} else if(effectFromFirstCard.equals(GameFlag.COLOR_PICK)) {
			this.fxmlController.triggerColorPicking(false, false);
		} else if(effectFromFirstCard.equals(GameFlag.PLUS_TWO)) {
			Collection<Card> cards = this.cardsController.drawCards(2);
			this.fxmlController.displayMessage("Forced to draw 2 cards");
			this.fxmlController.addCardToPlayer(this.turnController.getIndexFromActivePlayer(),cards);
		}
	}

	protected void triggerEffect(GameFlag gameFlag) {
		if(this.gameRule.indicatesTwoPlayersMode()) {
			triggerEffectWithOnlyTwoPlayers(gameFlag);
		} else {
			triggerEffectWithMoreThanTwoPlayers(gameFlag);
		}
	}

	protected void triggerEffectWithOnlyTwoPlayers(GameFlag gameFlag) {
		if(gameFlag.equals(GameFlag.REVERSE)) {
			triggerSkipNextPlayer("Your turn again");
		} else {
			triggerEffectWithMoreThanTwoPlayers(gameFlag);
		}
	}

	protected void triggerEffectWithMoreThanTwoPlayers(GameFlag gameFlag) {
		if(gameFlag.equals(GameFlag.REVERSE)) {
			triggerReverseCurrentOrder();
		} else if(gameFlag.equals(GameFlag.SKIP)) {
			triggerSkipNextPlayer("Next player skipped");
		} else if(gameFlag.equals(GameFlag.COLOR_PICK)) {
			triggerColorPicking(false);
		} else if(gameFlag.equals(GameFlag.PLUS_TWO)) {
			triggerPlusX(2);
		} else if(gameFlag.equals(GameFlag.PLUS_FOUR)) {
			triggerBluffing(4,false,true);
		} else if(gameFlag.equals(GameFlag.PLUS_FOUR_BLUFFING)) {
			triggerBluffing(4,true,true);
		}
	}

	/* ========================================= EFFECTS - BASIS ========================================= */

	@Override
	protected void triggerReverseCurrentOrder() {
		this.turnController.reverseCurrentOrder();
		this.fxmlController.triggerReverseCurrentOrder("Turn order inverted");
	}

	@Override
	protected void triggerCycleSilently() {
		this.turnController.cycleSilently();
	}

	protected void triggerSkipNextPlayer(String message) {
		this.turnController.skipNextPlayer();
		this.fxmlController.triggerSkipNextPlayer(message);
	}

	protected void triggerColorPicking(boolean isRelatedToPlus4) {
		this.fxmlController.triggerColorPicking(isRelatedToPlus4,true);
	}

	protected void triggerPlusX(int cardsToDraw) {
		Collection<Card> cards = this.cardsController.drawCards(cardsToDraw);
		this.fxmlController.triggerPlusX(cards);
	}

	protected void triggerBluffing(int cardsToDraw, boolean isBluffing, boolean applyPenaltyToNextPlayer) {
		Collection<Card> cards = this.cardsController.drawCards(cardsToDraw);
		this.fxmlController.triggerBluffing(cards,isBluffing,applyPenaltyToNextPlayer);
	}

	public void giveCardPenaltyTo(AbstractPlayerController currentPlayer, int cardCount) {
		Collection<Card> cardPenalty = this.cardsController.drawCards(cardCount);
		currentPlayer.isForcedToPickUpCards(cardPenalty);
	}

	public void giveCardPenaltyTo(PlayerControllerBean player, int cardCount) {
		Collection<Card> cardPenalty = this.cardsController.drawCards(cardCount);
		player.isForcedToPickUpCards(cardPenalty);
	}

	/* ========================================= SCORE ========================================= */

	@Override
	public boolean computeScores(PlayerControllerBean gameWinner) {
		boolean someoneOrOneTeamWon = false;
		if(this.gameRule.indicatesTeamPlayScoring()) {
			someoneOrOneTeamWon = this.turnController.computeTeamEndOfTurn(gameWinner);
			this.turnController.displayTeamTotalScore();
		} else {
			someoneOrOneTeamWon = this.turnController.computeIndividualEndOfTurn(gameWinner);
			this.turnController.displayIndividualTotalScore();
		}
		waitForScoreDisplay();
		return someoneOrOneTeamWon;
	}

	@Override
	public boolean computeIndividualScore(PlayerControllerBean gameWinner) {
		boolean playerWon = this.turnController.computeIndividualEndOfTurn(gameWinner);
		this.turnController.displayIndividualTotalScore();
		waitForScoreDisplay();
		return playerWon;
	}

	@Override
	public boolean computeTeamScore(PlayerControllerBean gameWinner) {
		boolean playerWon = this.turnController.computeTeamEndOfTurn(gameWinner);
		this.turnController.displayTeamTotalScore();
		waitForScoreDisplay();
		return playerWon;
	}

	@Override
	protected  void waitForScoreDisplay() {
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			throw new ServerException("[ERROR] Something went wrong while waiting during score display",e);
		}
	}

	@Override
	public boolean indicatesTeamPlayScoring() {
		return this.gameRule.indicatesTeamPlayScoring();
	}

	@Override
	public PlayerTeam findWinningTeam(PlayerControllerBean winningPlayer) {
		return this.turnController.findWinningTeam(winningPlayer);
	}


	public Map<Integer, PlayerTeam> getAllTeams() {
		return this.turnController.getAllTeams();
	}

	public Card retrieveImageFromLastCardPlayed() {
		return this.cardsController.retrieveImageFromLastCardPlayed();
	}

	public Map<String, Collection<Card>> getAllCardsFromPlayers() {
		return this.turnController.getAllCardsFromPlayers();
	}

	public List<PlayerControllerGraphicsOriented> getAllPlayers() {
		return this.turnController.getAllPlayers();
	}

	public void removeCardsFromPlayers() {
		this.turnController.removeCardsFromPlayers();
	}

	public CardsModelBean getReferences() {
		return this.cardsController.getReferences();
	}

	public Card drawOneCard() {
		return this.cardsController.drawOneCard();
	}

	public GameFlag activePlayerChose(Card chosenCard) {
		GameFlag gameFlag = this.cardsController.playCard(chosenCard);
		triggerEffect(gameFlag);
		return gameFlag;
	}

	public void activePlayerChoseColor(Color color) {
		this.cardsController.setGlobalColor(color);
	}

	public int getIndexFromPreviousPlayer() {
		return this.turnController.getIndexFromPreviousPlayer();
	}

	public Map<String, Integer> displayIndividualTotalScore() {
		return this.turnController.displayIndividualTotalScore();
	}
}
