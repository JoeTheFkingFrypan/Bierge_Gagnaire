package utt.fr.rglb.main.java.game.model;

import java.util.Collection;
import java.util.Map;

import javafx.scene.Scene;
import utt.fr.rglb.main.java.cards.controller.CardsControllerGraphicsOriented;
import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.dao.ConfigurationReader;
import utt.fr.rglb.main.java.main.ServerException;
import utt.fr.rglb.main.java.player.controller.AbstractPlayerController;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.controller.PlayerControllerGraphicsOriented;
import utt.fr.rglb.main.java.player.model.PlayerTeam;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;
import utt.fr.rglb.main.java.turns.controller.TurnControllerGraphicsOriented;
import utt.fr.rglb.main.java.view.graphics.GraphicsView;

public class GameModelGraphicsOriented extends AbstractGameModel {
	private static final long serialVersionUID = 1L;
	private TurnControllerGraphicsOriented turnController;
	private CardsControllerGraphicsOriented cardsController;
	protected GameRule gameRule;
	@SuppressWarnings("unused") private GraphicsView view;

	/* ========================================= CONSTRUCTOR ========================================= */
	
	public GameModelGraphicsOriented(GraphicsView view) {
		this.turnController = new TurnControllerGraphicsOriented(view);
		this.cardsController = new CardsControllerGraphicsOriented(view);
		this.view = view;
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
	}
	
	/* ========================================= GAME LOGIC ========================================= */
	
	@Override
	public PlayerControllerBean playOneTurn() {
		CardsModelBean references = this.cardsController.getRequiredReferences();
		PlayerControllerGraphicsOriented currentPlayer = this.turnController.findNextPlayer();
		if(currentPlayer.hasAtLeastOnePlayableCard(references)) {
			chooseCardAndPlayIt(references, currentPlayer);
		} else {
			Card cardDrawn = this.cardsController.drawOneCard();
			currentPlayer.pickUpOneCard(cardDrawn);
			if(currentPlayer.hasAtLeastOnePlayableCard(references)) {
				chooseCardAndPlayIt(references, currentPlayer);
			} else {
				currentPlayer.unableToPlayThisTurn(references);
			}
		}
		triggerEffect(currentPlayer);
		return new PlayerControllerBean(currentPlayer);
	}
	
	@Override
	protected void chooseCardAndPlayIt(CardsModelBean gameModelbean, AbstractPlayerController currentPlayer) {
		//TODO
	}
	
	/* ========================================= EFFECTS ========================================= */
	
	@Override
	public void drawFirstCardAndApplyItsEffect() {
		GameFlag effect = this.cardsController.applyEffectFromAnotherFirstCard();
		triggerEffectFromFirstCard(effect);
	}
	
	@Override
	protected void triggerEffectFromFirstCard(GameFlag effectFromFirstCard) {
		if(effectFromFirstCard.equals(GameFlag.PLUS_FOUR)) {
			drawFirstCardAndApplyItsEffect();
		} else {
			PlayerControllerGraphicsOriented nextPlayer = this.turnController.findNextPlayerWithoutChangingCurrentPlayer();
			triggerEffect(nextPlayer);
		}
		this.turnController.resetPlayerIndex();
	}
	
	@Override
	protected void triggerEffect(AbstractPlayerController currentPlayer) {
		if(this.gameRule.indicatesTwoPlayersMode()) {
			triggerEffectWithOnlyTwoPlayers(currentPlayer);
		} else {
			triggerEffectWithMoreThanTwoPlayers(currentPlayer);
		}
	}
	
	@Override
	protected void triggerEffectWithOnlyTwoPlayers(AbstractPlayerController currentPlayer) {
		if(this.gameRule.shouldReverseTurn()) {
			this.triggerCycleSilently();
		} else {
			triggerEffect(currentPlayer);
		}
	}
	
	@Override
	protected void triggerEffectWithMoreThanTwoPlayers(AbstractPlayerController currentPlayer) {
		if(this.gameRule.shouldReverseTurn()) {
			triggerReverseCurrentOrder();
		} else if(this.gameRule.shouldSkipNextPlayerTurn()) {
			triggerSkipNextPlayer();
		} else if(this.gameRule.shouldPickColor()) {
			triggerColorPicking(currentPlayer,false);
		} else if(this.gameRule.shouldGivePlus2CardPenalty()) {
			PlayerControllerGraphicsOriented nextPlayer = this.turnController.findNextPlayerWithoutChangingCurrentPlayer();
			triggerPlusX(2,nextPlayer);
		} else if(this.gameRule.shouldGivePlus4CardPenalty()) {
			PlayerControllerGraphicsOriented nextPlayer = this.turnController.findNextPlayerWithoutChangingCurrentPlayer();
			boolean wantsToCheck = triggerBluffing(nextPlayer);
			if(wantsToCheck) {
				triggerPlusX(2,nextPlayer,true);
			}
			triggerPlusX(4,nextPlayer);
			triggerColorPicking(currentPlayer,true);
		} else if(this.gameRule.shouldGivePlus4CardPenaltyWhileBluffing()) {
			PlayerControllerGraphicsOriented nextPlayer = this.turnController.findNextPlayerWithoutChangingCurrentPlayer();
			boolean wantsToCheck = triggerBluffing(nextPlayer);
			if(wantsToCheck) {
				triggerPlusX(2,currentPlayer,false);
				triggerPlusX(4,currentPlayer);
			} else {
				triggerPlusX(4,nextPlayer);
			}
			triggerColorPicking(currentPlayer,true);
		}
	}
	
	/* ========================================= EFFECTS - BASIS ========================================= */

	@Override
	protected void triggerReverseCurrentOrder() {
		this.turnController.reverseCurrentOrder();
	}
	
	@Override
	protected void triggerCycleSilently() {
		this.turnController.cycleSilently();
	}

	@Override
	protected void triggerSkipNextPlayer() {
		this.turnController.skipNextPlayer();
	}
	
	@Override
	protected void triggerColorPicking(AbstractPlayerController currentPlayer, boolean isRelatedToPlus4) {
		//TODO
	}
	
	@Override
	protected void triggerPlusX(int cardsToDraw, AbstractPlayerController targetedPlayer) {
		Collection<Card> cards = this.cardsController.drawCards(cardsToDraw);
		targetedPlayer.isForcedToPickUpCards(cards);
	}
	
	@Override
	protected void triggerPlusX(int cardsToDraw, AbstractPlayerController targetedPlayer, boolean wasLegit) {
		Collection<Card> cards = this.cardsController.drawCards(cardsToDraw);
		if(wasLegit) {
			targetedPlayer.isForcedToPickUpCardsLegitCase(cards);
		} else {
			targetedPlayer.isForcedToPickUpCardsBluffCase(cards);
		}
	}

	@Override
	protected boolean triggerBluffing(AbstractPlayerController nextPlayer) {
		//TODO
		return false;
	}

	@Override
	public void giveCardPenaltyTo(AbstractPlayerController currentPlayer, int cardCount) {
		Collection<Card> cardPenalty = this.cardsController.drawCards(cardCount);
		currentPlayer.isForcedToPickUpCards(cardPenalty);
	}
	
	@Override
	public void giveCardPenaltyTo(PlayerControllerBean player, int cardCount) {
		Collection<Card> cardPenalty = this.cardsController.drawCards(cardCount);
		player.isForcedToPickUpCards(cardPenalty);
	}
	
	@Override
	public int getValidChoiceAnswer() {
		//TODO
		return 0;
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

	public void backgroundLoadImages() {
		this.cardsController.backgroundLoadImages();	
	}

	public Map<String, Collection<Card>> getAllCardsFromPlayers() {
		return this.turnController.getAllCardsFromPlayers();
	}
}
