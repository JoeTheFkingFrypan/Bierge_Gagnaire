package utt.fr.rglb.main.java.turns.controller;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import utt.fr.rglb.main.java.turns.model.TurnModelConsoleOriented;
import utt.fr.rglb.main.java.view.AbstractView;
import utt.fr.rglb.main.java.view.console.ConsoleView;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.player.controller.AbstractPlayerController;
import utt.fr.rglb.main.java.player.controller.PlayerControllerConsoleOriented;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.model.PlayerTeam;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;

import com.google.common.base.Preconditions;

/**
 * Classe dont le rôle est de gérer tout ce qui touche aux joueurs, et passage au joueur suivant </br>
 * Version console
 */
public class TurnControllerConsoleOriented extends AbstractTurnController {
	private static final long serialVersionUID = 1L;
	private TurnModelConsoleOriented turnModel;
	private ConsoleView view;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur de TurnController
	 * @param consoleView Vue permettant d'afficher des informations dans l'interface
	 */
	public TurnControllerConsoleOriented(AbstractView view) {
		Preconditions.checkNotNull(view,"[ERROR] Impossible to create turn controller : provided view is null");
		this.turnModel = new TurnModelConsoleOriented();
		this.view = (ConsoleView) view;
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

	public void createPlayersFrom(PlayersToCreate playersAwaitingCreation, BufferedReader inputStream) {
		Preconditions.checkNotNull(playersAwaitingCreation,"[ERROR] Provided played names cannot be null");
		this.turnModel.createPlayersFrom(playersAwaitingCreation,this.view,inputStream);
	}

	public void createPlayersWithoutScamblingFrom(Collection<String> playerNames, BufferedReader inputStream) {
		Preconditions.checkNotNull(playerNames,"[ERROR] Provided played names cannot be null");
		Preconditions.checkArgument(playerNames.size() >= 2,"[ERROR] There must be at least two players in the game");
		this.turnModel.createPlayersWithoutScramblingFrom(playerNames,this.view,inputStream);
	}
	
	@Override
	public void splitPlayersIntoTeams() {
		this.turnModel.splitPlayersIntoTeams();
	}

	/* ========================================= TURN ORDER ========================================= */

	@Override
	public void reverseCurrentOrder() {
		this.view.displayOneLineOfRedText("Turn order has been inverted");
		this.turnModel.reverseCurrentOrder();
	}

	@Override
	public PlayerControllerConsoleOriented findCurrentPlayer() {
		return this.turnModel.findCurrentPlayer();
	}
	
	@Override
	public PlayerControllerConsoleOriented findNextPlayer() {
		PlayerControllerConsoleOriented currentPlayer = (PlayerControllerConsoleOriented)this.turnModel.cycleThroughPlayers();
		this.view.displaySeparationText("========== Your turn, " + currentPlayer.getAlias() + " ==========");
		return currentPlayer;
	}
	
	@Override
	public PlayerControllerConsoleOriented findNextPlayerWithoutChangingCurrentPlayer() {
		return this.turnModel.cycleThroughPlayersWithoutChangingCurrentPlayer();
	}

	/* ========================================= EFFECT RELATED ========================================= */

	@Override
	public void skipNextPlayer() {
		PlayerControllerConsoleOriented currentPlayer = (PlayerControllerConsoleOriented)this.turnModel.cycleThroughPlayers();
		this.view.displaySeparationText("========== Your turn, " + currentPlayer.getAlias() + " ==========");
		this.view.displayErrorMessage("Sadly, you are not allowed to play this turn","Previous player used a Skip card");
	}

	@Override
	public void cycleSilently() {
		this.view.displayGreenEmphasisUsingPlaceholders("Thanks to your previous card", "it's your turn to play", "again");
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
		this.view.displayGreenEmphasisUsingPlaceholders("Player [",gameWinner.getAlias(),"] has won this round, congratulations !");
		this.view.displayGreenEmphasisUsingPlaceholders("He successfully scored ",pointsReceived.toString()," points");
		return hasWon;
	}

	@Override
	public boolean computeTeamEndOfTurn(PlayerControllerBean gameWinner) {
		Preconditions.checkNotNull(gameWinner,"[ERROR] Impossible to compute team score : provided PlayerControllerBean cannot be null");
		PlayerTeam winningTeam = this.turnModel.findWinningTeam(gameWinner);
		Integer pointsReceived = this.turnModel.sumAllTeamScore(winningTeam);
		boolean hasWon = this.turnModel.increaseScoreOfTheWinningTeam(winningTeam,pointsReceived);
		this.turnModel.resetAllHands();
		this.view.displayGreenEmphasisUsingPlaceholders("Team [",winningTeam.toString(),"] has won this round, congratulations !");
		this.view.displayGreenEmphasisUsingPlaceholders("They successfully scored ",pointsReceived.toString()," points total");
		return hasWon;
	}

	/* ========================================= TEAM PLAY ========================================= */
	
	@Override
	public PlayerTeam findWinningTeam(PlayerControllerBean winningPlayer) {
		Preconditions.checkNotNull(winningPlayer,"[ERROR] Impossible to find winning team : provided PlayerControllerBean cannot be null");
		return this.turnModel.findWinningTeam(winningPlayer);
	}
	
	/* ========================================= SCORE ========================================= */

	/**
	 * Méthode permettant l'affichage des scores individuels
	 */
	public void displayIndividualTotalScore() {
		Collection<PlayerControllerConsoleOriented> players = this.turnModel.getAllPlayers();
		this.view.displayOneLineOfJokerText("Scores are now : ");
		for(AbstractPlayerController currentPlayer : players) {
			Integer currentScore = currentPlayer.getScore();
			Integer pointsNeededToWin = 500 - currentScore;
			this.view.displayJokerEmphasisUsingPlaceholders(" * [", currentPlayer.getAlias(), "] : ", currentScore.toString(), " => ", pointsNeededToWin.toString(), " more points need to win the game");
		}
	}

	/**
	 * Méthode permettant l'affichage des scores par équipe
	 */
	public void displayTeamTotalScore() {
		Map<Integer, PlayerTeam> teams = this.turnModel.getAllTeams();
		this.view.displayOneLineOfJokerText("Scores are now : ");
		for(Entry<Integer, PlayerTeam> teamEntry : teams.entrySet()) {
			PlayerTeam currentTeam = teamEntry.getValue();
			Integer currentScore = currentTeam.getScore();
			Integer pointsNeededToWin = 500 - currentScore;	
			this.view.displayJokerEmphasisUsingPlaceholders(" * TEAM " + teamEntry.getKey() + " [", currentTeam.toString(), "] : ", currentScore.toString(), " => ", pointsNeededToWin.toString(), " more points need to win the game");	
		}
	}

	/* ========================================= GETTERS & DISPLAY ========================================= */

	@Override
	public int getNumberOfPlayers() {
		return this.turnModel.getNumberOfPlayers();
	}
	
	@Override
	public void displayTeams() {
		Map<Integer, PlayerTeam> teams = this.turnModel.getAllTeams();
		this.view.AppendOneLineOfBoldText("Teams are now : ");
		for(Entry<Integer, PlayerTeam> teamEntry : teams.entrySet()) {
			this.view.displayJokerEmphasisUsingPlaceholders(" * TEAM " + teamEntry.getKey().toString() + " [ ", teamEntry.getValue().toString(), " ]");
		}
	}
}