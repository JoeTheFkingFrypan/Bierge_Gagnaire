package utt.fr.rglb.main.java.turns.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Preconditions;

import javafx.scene.Scene;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.player.controller.AbstractPlayerController;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.controller.PlayerControllerGraphicsOriented;
import utt.fr.rglb.main.java.player.model.PlayerTeam;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;
import utt.fr.rglb.main.java.turns.model.TurnModelGraphicsOriented;
import utt.fr.rglb.main.java.view.graphics.GraphicsView;

public class TurnControllerGraphicsOriented extends AbstractTurnController {
	private static final long serialVersionUID = 1L;
	private TurnModelGraphicsOriented turnModel;
	private GraphicsView view;

	/* ========================================= CONSTRUCTOR ========================================= */

	public TurnControllerGraphicsOriented(GraphicsView view) {
		this.turnModel = new TurnModelGraphicsOriented();
		this.view = view;
	}

	/* ========================================= RESET ========================================= */

	@Override
	public void resetPlayerIndex() {
		this.turnModel.resetPlayerIndex();
	}

	@Override
	public void resetTurn() {
		this.turnModel.resetTurn();
	}

	/* ========================================= PLAYER CREATION ========================================= */

	public void createPlayersFrom(PlayersToCreate playersToCreate) {
		this.turnModel.createPlayersFrom(playersToCreate,this.view);
	}

	@Override
	public void splitPlayersIntoTeams() {
		this.turnModel.splitPlayersIntoTeams();
	}

	/* ========================================= TURN ORDER ========================================= */

	@Override
	public void reverseCurrentOrder() {
		this.turnModel.reverseCurrentOrder();
	}

	@Override
	public PlayerControllerGraphicsOriented findCurrentPlayer() {
		return this.turnModel.findCurrentPlayer();
	}

	@Override
	public PlayerControllerGraphicsOriented findNextPlayer() {
		return this.turnModel.cycleThroughPlayers();
	}


	@Override
	public PlayerControllerGraphicsOriented findNextPlayerWithoutChangingCurrentPlayer() {
		return this.turnModel.cycleThroughPlayersWithoutChangingCurrentPlayer();
	}

	/* ========================================= EFFECT RELATED ========================================= */

	@Override
	public void skipNextPlayer() {
		this.turnModel.cycleThroughPlayers();
	}

	@Override
	public void cycleSilently() {
		this.turnModel.cycleSilentlyThroughPlayers();
	}

	@Override
	public void giveCardsToNextPlayer(Collection<Card> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Provided card collection cannot be null");
		this.turnModel.giveCardsToNextPlayer(cards);
	}

	/* ========================================= ROUND WIN EVENT HANDLING ========================================= */

	@Override
	public boolean computeIndividualEndOfTurn(PlayerControllerBean gameWinner) {
		Preconditions.checkNotNull(gameWinner,"[ERROR] Impossible to compute individual score : provided PlayerControllerBean cannot be null");
		Integer pointsReceived = this.turnModel.sumAllIndividualPlayerScore();
		boolean hasWon = gameWinner.increaseScoreBy(pointsReceived);
		this.turnModel.resetAllHands();
		//TODO display stuff
		return hasWon;
	}

	@Override
	public boolean computeTeamEndOfTurn(PlayerControllerBean gameWinner) {
		Preconditions.checkNotNull(gameWinner,"[ERROR] Impossible to compute team score : provided PlayerControllerBean cannot be null");
		PlayerTeam winningTeam = this.turnModel.findWinningTeam(gameWinner);
		Integer pointsReceived = this.turnModel.sumAllTeamScore(winningTeam);
		boolean hasWon = this.turnModel.increaseScoreOfTheWinningTeam(winningTeam,pointsReceived);
		this.turnModel.resetAllHands();
		//TODO display stuff
		return hasWon;
	}

	/* ========================================= TEAM PLAY ========================================= */

	@Override
	public PlayerTeam findWinningTeam(PlayerControllerBean winningPlayer) {
		Preconditions.checkNotNull(winningPlayer,"[ERROR] Impossible to find winning team : provided PlayerControllerBean cannot be null");
		return this.turnModel.findWinningTeam(winningPlayer);
	}

	public void displayTeams(Scene scene) {
		this.view.displayTeamScreen(scene);
	}

	public Map<Integer, PlayerTeam> getAllTeams() {
		return this.turnModel.getAllTeams();
	}


	/* ========================================= SCORE ========================================= */

	@Override
	public void displayIndividualTotalScore() {
		Collection<PlayerControllerGraphicsOriented> players = this.turnModel.getAllPlayers();
		for(AbstractPlayerController currentPlayer : players) {
			Integer currentScore = currentPlayer.getScore();
			Integer pointsNeededToWin = 500 - currentScore;
		}
	}

	@Override
	public void displayTeamTotalScore() {
		Map<Integer, PlayerTeam> teams = this.turnModel.getAllTeams();
		for(Entry<Integer, PlayerTeam> teamEntry : teams.entrySet()) {
			PlayerTeam currentTeam = teamEntry.getValue();
			Integer currentScore = currentTeam.getScore();
			Integer pointsNeededToWin = 500 - currentScore;	
		}
	}

	/* ========================================= GETTERS & DISPLAY ========================================= */

	@Override
	public int getNumberOfPlayers() {
		return this.turnModel.getNumberOfPlayers();
	}

	@Override
	public void displayTeams() {
		// TODO Auto-generated method stub

	}

	public Map<String, Collection<Card>> getAllCardsFromPlayers() {
		return this.turnModel.getAllCardsFromPlayers();
	}

	public List<PlayerControllerGraphicsOriented> getAllPlayers() {
		return this.turnModel.getAllPlayers();
	}

	public void removeCardsFromPlayers() {
		this.turnModel.removeCardsFromPlayers();
	}

	public int getIndexFromActivePlayer() {
		return this.turnModel.getIndexFromActivePlayer();
	}

	public int getIndexFromPreviousPlayer() {
		return this.turnModel.getIndexFromPreviousPlayer();
	}

	public int getIndexFromNextPlayer() {
		return this.turnModel.getIndexFromNextPlayer();
	}

}
