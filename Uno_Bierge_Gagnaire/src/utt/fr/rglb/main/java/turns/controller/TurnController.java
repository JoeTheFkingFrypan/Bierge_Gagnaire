package utt.fr.rglb.main.java.turns.controller;

import java.io.BufferedReader;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.console.view.View;
import utt.fr.rglb.main.java.turns.model.TurnModel;
import utt.fr.rglb.main.java.player.controller.PlayerController;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.model.PlayerTeam;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;

import com.google.common.base.Preconditions;

/**
 * Classe dont le rôle est de gérer tout ce qui touche aux joueurs, et passage au joueur suivant
 */
public class TurnController implements Serializable {
	private static final long serialVersionUID = 1L;
	private TurnModel turnModel;
	private View consoleView;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur de TurnController
	 * @param consoleView Vue permettant d'afficher des informations dans l'interface
	 */
	public TurnController(View consoleView) {
		Preconditions.checkNotNull(consoleView,"[ERROR] Impossible to create turn controller : provided view is null");
		this.turnModel = new TurnModel();
		this.consoleView = consoleView;
	}

/* ========================================= RESET ========================================= */
	
	/**
	 * Méthode permettant de ré-initialiser l'index du joueur devant commencer la partie (fonction du sens de jeu)
	 */
	public void resetPlayerIndex() {
		this.turnModel.resetPlayerIndex();
	}
	
	/**
	 * Méthode permettant de ré-initialiser le système de tour de jeu (remise par défaut du sens de jeu, suppression de tous les joueurs)
	 */
	public void resetTurn() {
		this.turnModel.resetTurn();
	}
	
	/* ========================================= PLAYER CREATION ========================================= */

	/**
	 * Méthode permettant de créer tous les joueurs à partir de leurs noms, et de leur donner un ordre aléatoire
	 * @param playersAwaitingCreation Collection contenant le nom de tous les joueurs
	 */
	public void createPlayersFrom(PlayersToCreate playersAwaitingCreation, BufferedReader inputStream) {
		Preconditions.checkNotNull(playersAwaitingCreation,"[ERROR] Provided played names cannot be null");
		this.turnModel.createPlayersFrom(playersAwaitingCreation,this.consoleView,inputStream);
	}
	
	/**
	 * Méthode permettant de créer tous les joueurs à partir de leurs noms, sans leur donner un ordre aléatoire
	 * @param playerNames Collection contenant le nom de tous les joueurs
	 */
	public void createPlayersWithoutScamblingFrom(Collection<String> playerNames, BufferedReader inputStream) {
		Preconditions.checkNotNull(playerNames,"[ERROR] Provided played names cannot be null");
		Preconditions.checkArgument(playerNames.size() >= 2,"[ERROR] There must be at least two players in the game");
		this.turnModel.createPlayersWithoutScramblingFrom(playerNames,this.consoleView,inputStream);
	}

	/**
	 * Méthode permettant d'initialiser la main des joueurs en leur donnant 7 cartes chacun
	 * @param cards Collection initiale de 7 cartes
	 */
	public void giveCardsToNextPlayer(Collection<Card> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Provided card collection cannot be null");
		this.turnModel.giveCardsToNextPlayer(cards);
	}
	
	/* ========================================= TURN ORDER ========================================= */

	/**
	 * Méthode permettant d'inverser le sens dans lequel est choisi le joueur suivant
	 */
	public void reverseCurrentOrder() {
		this.consoleView.displayOneLineOfRedText("Turn order has been inverted");
		this.turnModel.reverseCurrentOrder();
	}

	/**
	 * Méthode permettant de trouver le joueur suivant (en fonction du sens du jeu)
	 * @return Le controlleur associé au joueur suivant
	 */
	public PlayerController findCurrentPlayer() {
		return this.turnModel.findCurrentPlayer();
	}

	/**
	 * Méthode permettant de trouver le joueur suivant
	 * @return Le prochain joueur qui doit jouer
	 */
	public PlayerController findNextPlayer() {
		PlayerController currentPlayer = this.turnModel.cycleThroughPlayers();
		this.consoleView.displaySeparationText("========== Your turn, " + currentPlayer.getAlias() + " ==========");
		return currentPlayer;
	}

	/**
	 * Méthode permettant de trouver le joueur suivant (sans changer l'index du joueur actuel)
	 * @return Le prochain joueur qui doit jouer
	 */
	public PlayerController findNextPlayerWithoutChangingCurrentPlayer() {
		return this.turnModel.cycleThroughPlayersWithoutChangingCurrentPlayer();
	}

	/* ========================================= EFFECT RELATED ========================================= */
	
	/**
	 * Méthode permettant d'empêcher le joueur suivant de jouer
	 */
	public void skipNextPlayer() {
		PlayerController currentPlayer = this.turnModel.cycleThroughPlayers();
		this.consoleView.displaySeparationText("========== Your turn, " + currentPlayer.getAlias() + " ==========");
		this.consoleView.displayErrorMessage("Sadly, you are not allowed to play this turn","Previous player used a Skip card");
	}
	
	public void cycleSilently() {
		this.consoleView.displayGreenEmphasisUsingPlaceholders("Thanks to your previous card", "it's your turn to play", "again");
		this.turnModel.cycleSilentlyThroughPlayers();
	}
	
	/* ========================================= ROUND WIN EVENT HANDLING ========================================= */
	
	/**
	 * Méthode permettant de calculer le score du joueur ayant gagné
	 * @param gameWinner Joueur ayant remporté le round
	 * @return <code>TRUE</code> si le joueur a gagné (score > 500 points), <code>FALSE</code> sinon
	 */
	public boolean computeIndividualEndOfTurn(PlayerControllerBean gameWinner) {
		Preconditions.checkNotNull(gameWinner,"[ERROR] Impossible to compute individual score : provided PlayerControllerBean cannot be null");
		Integer pointsReceived = this.turnModel.sumAllIndividualPlayerScore();
		boolean hasWon = gameWinner.increaseScoreBy(pointsReceived);
		this.turnModel.resetAllHands();
		this.consoleView.displayGreenEmphasisUsingPlaceholders("Player [",gameWinner.getAlias(),"] has won this round, congratulations !");
		this.consoleView.displayGreenEmphasisUsingPlaceholders("He successfully scored ",pointsReceived.toString()," points");
		return hasWon;
	}
	
	/**
	 * Méthode permettant de calculer le score de l'équipe ayant gagné
	 * @param gameWinner Joueur ayant remporté le round
	 * @return <code>TRUE</code> si l'équipe a gagné (score > 500 points), <code>FALSE</code> sinon
	 */
	public boolean computeTeamEndOfTurn(PlayerControllerBean gameWinner) {
		Preconditions.checkNotNull(gameWinner,"[ERROR] Impossible to compute team score : provided PlayerControllerBean cannot be null");
		PlayerTeam winningTeam = this.turnModel.findWinningTeam(gameWinner);
		Integer pointsReceived = this.turnModel.sumAllTeamScore(winningTeam);
		boolean hasWon = this.turnModel.increaseScoreOfTheWinningTeam(winningTeam,pointsReceived);
		this.turnModel.resetAllHands();
		this.consoleView.displayGreenEmphasisUsingPlaceholders("Team [",winningTeam.toString(),"] has won this round, congratulations !");
		this.consoleView.displayGreenEmphasisUsingPlaceholders("They successfully scored ",pointsReceived.toString()," points total");
		return hasWon;
	}

	/* ========================================= TEAM PLAY ========================================= */
	
	/**
	 * Méthode permettant de séparer les jouers en différentes équipes de 2 joueurs
	 */
	public void splitPlayersIntoTeams() {
		this.turnModel.splitPlayersIntoTeams();
	}
	
	/**
	 * Méthode permettant de récuperer l'équipe ayant gagné (qui contient le joueur donné)
	 * @param winningPlayer Joueur victorieux dont on souhaite connaitre l'équipe
	 * @return L'équipe à laquelle appartient le joueur donné
	 */
	public PlayerTeam findWinningTeam(PlayerControllerBean winningPlayer) {
		Preconditions.checkNotNull(winningPlayer,"[ERROR] Impossible to find winning team : provided PlayerControllerBean cannot be null");
		return this.turnModel.findWinningTeam(winningPlayer);
	}
	
	/* ========================================= SCORE ========================================= */
	
	/**
	 * Méthode permettant d'afficher les scores de tous les joueurs
	 */
	public void displayIndividualTotalScore() {
		Collection<PlayerController> players = this.turnModel.getAllPlayers();
		this.consoleView.displayOneLineOfJokerText("Scores are now : ");
		for(PlayerController currentPlayer : players) {
			Integer currentScore = currentPlayer.getScore();
			Integer pointsNeededToWin = 500 - currentScore;
			this.consoleView.displayJokerEmphasisUsingPlaceholders(" * [", currentPlayer.getAlias(), "] : ", currentScore.toString(), " => ", pointsNeededToWin.toString(), " more points need to win the game");
		}
	}
	
	/**
	 * Méthode permettant d'afficher les scores de toutes les équipes
	 */
	public void displayTeamTotalScore() {
		Map<Integer, PlayerTeam> teams = this.turnModel.getAllTeams();
		this.consoleView.displayOneLineOfJokerText("Scores are now : ");
		for(Entry<Integer, PlayerTeam> teamEntry : teams.entrySet()) {
			PlayerTeam currentTeam = teamEntry.getValue();
			Integer currentScore = currentTeam.getScore();
			Integer pointsNeededToWin = 500 - currentScore;	
			this.consoleView.displayJokerEmphasisUsingPlaceholders(" * TEAM " + teamEntry.getKey() + " [", currentTeam.toString(), "] : ", currentScore.toString(), " => ", pointsNeededToWin.toString(), " more points need to win the game");	
		}
	}
	
	/* ========================================= GETTERS & DISPLAY ========================================= */
	
	/**
	 * Méthode permettant de récupérer le nombre de joueurs
	 * @return int correspondant au nombre de joueurs
	 */
	public int getNumberOfPlayers() {
		return this.turnModel.getNumberOfPlayers();
	}

	/**
	 * Méthode permettant d'afficher la composition de toutes les équipes
	 */
	public void displayTeams() {
		Map<Integer, PlayerTeam> teams = this.turnModel.getAllTeams();
		this.consoleView.AppendOneLineOfBoldText("Teams are now : ");
		for(Entry<Integer, PlayerTeam> teamEntry : teams.entrySet()) {
			this.consoleView.displayJokerEmphasisUsingPlaceholders(" * TEAM " + teamEntry.getKey().toString() + " [ ", teamEntry.getValue().toString(), " ]");
		}
		
	}
}