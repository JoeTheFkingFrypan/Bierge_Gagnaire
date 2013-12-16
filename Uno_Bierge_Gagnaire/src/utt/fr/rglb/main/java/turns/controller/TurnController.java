package utt.fr.rglb.main.java.turns.controller;

import java.io.Serializable;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.console.view.View;
import utt.fr.rglb.main.java.player.controller.PlayerController;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;
import utt.fr.rglb.main.java.turns.model.TurnModel;

import com.google.common.base.Preconditions;

/**
 * Classe dont le r�le est de g�rer tout ce qui touche aux joueurs, et passage au joueur suivant
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
	 * M�thode permettant de r�-initialiser l'index du joueur devant commencer la partie (fonction du sens de jeu)
	 */
	public void resetPlayerIndex() {
		this.turnModel.resetPlayerIndex();
	}
	
	/**
	 * M�thode permettant de r�-initialiser le syst�me de tour de jeu (remise par d�faut du sens de jeu, suppression de tous les joueurs)
	 */
	public void resetTurn() {
		this.turnModel.resetTurn();
	}
	
	/* ========================================= PLAYER CREATION ========================================= */

	/**
	 * M�thode permettant de cr�er tous les joueurs � partir de leurs noms, et de leur donner un ordre al�atoire
	 * @param playersAwaitingCreation Collection contenant le nom de tous les joueurs
	 */
	public void createPlayersFrom(PlayersToCreate playersAwaitingCreation) {
		Preconditions.checkNotNull(playersAwaitingCreation,"[ERROR] Provided played names cannot be null");
		this.turnModel.createPlayersFrom(playersAwaitingCreation,consoleView);
	}
	
	/**
	 * M�thode permettant de cr�er tous les joueurs � partir de leurs noms, sans leur donner un ordre al�atoire
	 * @param playerNames Collection contenant le nom de tous les joueurs
	 */
	public void createPlayersWithoutScamblingFrom(Collection<String> playerNames) {
		Preconditions.checkNotNull(playerNames,"[ERROR] Provided played names cannot be null");
		Preconditions.checkArgument(playerNames.size() >= 2,"[ERROR] There must be at least two players in the game");
		this.turnModel.createPlayersWithoutScramblingFrom(playerNames,consoleView);
	}

	/**
	 * M�thode permettant d'initialiser la main des joueurs en leur donnant 7 cartes chacun
	 * @param cards Collection initiale de 7 cartes
	 */
	public void giveCardsToNextPlayer(Collection<Card> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Provided card collection cannot be null");
		this.turnModel.giveCardsToNextPlayer(cards);
	}

	/* ========================================= TURN ORDER ========================================= */

	/**
	 * M�thode permettant d'inverser le sens dans lequel est choisi le joueur suivant
	 */
	public void reverseCurrentOrder() {
		this.consoleView.displayOneLineOfRedText("Turn order has been inverted");
		this.turnModel.reverseCurrentOrder();
	}

	/**
	 * M�thode permettant de trouver le joueur suivant (en fonction du sens du jeu)
	 * @return Le controlleur associ� au joueur suivant
	 */
	public PlayerController findCurrentPlayer() {
		return this.turnModel.findCurrentPlayer();
	}

	/**
	 * M�thode permettant de trouver le joueur suivant
	 * @return Le prochain joueur qui doit jouer
	 */
	public PlayerController findNextPlayer() {
		PlayerController currentPlayer = this.turnModel.cycleThroughPlayers();
		consoleView.displaySeparationText("========== Your turn, " + currentPlayer.getAlias() + " ==========");
		return currentPlayer;
	}

	/**
	 * M�thode permettant de trouver le joueur suivant (sans changer l'index du joueur actuel)
	 * @return Le prochain joueur qui doit jouer
	 */
	public PlayerController findNextPlayerWithoutChangingCurrentPlayer() {
		return this.turnModel.cycleThroughPlayersWithoutChangingCurrentPlayer();
	}

	/* ========================================= EFFECT RELATED ========================================= */
	
	/**
	 * M�thode permettant d'emp�cher le joueur suivant de jouer
	 */
	public void skipNextPlayer() {
		PlayerController currentPlayer = this.turnModel.cycleThroughPlayers();
		consoleView.displaySeparationText("========== Your turn, " + currentPlayer.getAlias() + " ==========");
		consoleView.displayErrorMessage("Sadly, you are not allowed to play this turn","Previous player used a Skip card");
	}
	
	/* ========================================= ROUND WIN EVENT HANDLING ========================================= */
	
	/**
	 * M�thode permettant de calculer le score du joueur ayant gagn�
	 * @param gameWinner Joueur ayant remport� le round
	 * @return TRUE si le joueur a gagn� (score > 500 points), FALSE sinon
	 */
	public boolean computeEndOfTurn(PlayerControllerBean gameWinner) {
		Integer pointsReceived = this.turnModel.sumAllPlayerScore();
		boolean hasWon = gameWinner.increaseScoreBy(pointsReceived);
		this.turnModel.resetAllHands();
		consoleView.displayGreenEmphasisUsingPlaceholders("Player [",gameWinner.getAlias(),"] has won this round, congratulations !");
		consoleView.displayGreenEmphasisUsingPlaceholders("He successfully scored ",pointsReceived.toString()," points");
		return hasWon;
	}

	/* ========================================= GETTERS & DISPLAY ========================================= */
	
	/**
	 * M�thode permettant de r�cup�rer le nombre de joueurs
	 * @return int correspondant au nombre de joueurs
	 */
	public int getNumberOfPlayers() {
		return this.turnModel.getNumberOfPlayers();
	}

	/**
	 * M�thode permettant d'afficher les scores de tous les joueurs
	 */
	public void displayTotalScore() {
		Collection<PlayerController> players = this.turnModel.getAllPlayers();
		consoleView.displayOneLineOfJokerText("Scores are now : ");
		for(PlayerController currentPlayer : players) {
			Integer currentScore = currentPlayer.getScore();
			Integer pointsNeededToWin = 500 - currentScore;
			consoleView.displayJokerEmphasisUsingPlaceholders(" * [", currentPlayer.getAlias(), "] : ", currentScore.toString(), " => ", pointsNeededToWin.toString(), " more points need to win the game");
		}
	}
}