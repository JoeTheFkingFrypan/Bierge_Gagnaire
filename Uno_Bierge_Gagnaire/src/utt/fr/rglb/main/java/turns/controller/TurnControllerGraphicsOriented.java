package utt.fr.rglb.main.java.turns.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.google.common.base.Preconditions;

import javafx.scene.Scene;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.controller.PlayerControllerGraphicsOriented;
import utt.fr.rglb.main.java.player.model.PlayerTeam;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;
import utt.fr.rglb.main.java.turns.model.TurnModelGraphicsOriented;
import utt.fr.rglb.main.java.view.graphics.GraphicsView;

/**
 * Classe dont le rôle est de gérer tout ce qui touche aux joueurs, et passage au joueur suivant </br>
 * Version graphique
 */
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

	/**
	 * Méthode permettant la création de tous les joueurs à partir de leurs données respectives
	 * @param playersToCreate Objet englobant les informations de tous les joueurs à créer
	 */
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
		return hasWon;
	}

	@Override
	public boolean computeTeamEndOfTurn(PlayerControllerBean gameWinner) {
		Preconditions.checkNotNull(gameWinner,"[ERROR] Impossible to compute team score : provided PlayerControllerBean cannot be null");
		PlayerTeam winningTeam = this.turnModel.findWinningTeam(gameWinner);
		Integer pointsReceived = this.turnModel.sumAllTeamScore(winningTeam);
		boolean hasWon = this.turnModel.increaseScoreOfTheWinningTeam(winningTeam,pointsReceived);
		this.turnModel.resetAllHands();
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

	public Map<String, Integer> displayIndividualTotalScore() {
		Map<String,Integer> scores = new TreeMap<String,Integer>();
		for(PlayerControllerGraphicsOriented currentPlayer : this.turnModel.getAllPlayers()) {
			scores.put(currentPlayer.getAlias(), currentPlayer.getScore());
		}
		return scores;
	}

	public void displayTeamTotalScore() {
		Map<Integer, PlayerTeam> teams = this.turnModel.getAllTeams();
		for(Entry<Integer, PlayerTeam> teamEntry : teams.entrySet()) {
			PlayerTeam currentTeam = teamEntry.getValue();
			Integer currentScore = currentTeam.getScore();
			@SuppressWarnings("unused")Integer pointsNeededToWin = 500 - currentScore;
		}
	}

	/* ========================================= GETTERS & DISPLAY ========================================= */

	@Override
	public int getNumberOfPlayers() {
		return this.turnModel.getNumberOfPlayers();
	}

	@Override
	public void displayTeams() {
		//TODO Unfinished method
	}

	/**
	 * Méthode permettant de récupérer l'ensemble des cartes en main des joueurs
	 * @return Map correspondant à l'ensemble des cartes actuellement dans les mains des différents joueurs
	 */
	public Map<String, Collection<Card>> getAllCardsFromPlayers() {
		return this.turnModel.getAllCardsFromPlayers();
	}

	/**
	 * Méthode permtettant de récupérer l'ensemble des joueurs de la partie
	 * @return Collection contenant l'ensemble des joueurs
	 */
	public List<PlayerControllerGraphicsOriented> getAllPlayers() {
		return this.turnModel.getAllPlayers();
	}

	/**
	 * Méthode permettant de supprimer les cartes en main de chaque joueur
	 */
	public void removeCardsFromPlayers() {
		this.turnModel.removeCardsFromPlayers();
	}

	/**
	 * Méthode permettant de récupérer l'index correspondant au joueur actif
	 * @return int correspondant au joueur désiré
	 */
	public int getIndexFromActivePlayer() {
		return this.turnModel.getIndexFromActivePlayer();
	}

	/**
	 * Méthode permettant de récupérer l'index correspondant au joueur précédent
	 * @return int correspondant au joueur désiré
	 */
	public int getIndexFromPreviousPlayer() {
		return this.turnModel.getIndexFromPreviousPlayer();
	}

	/**
	 * Méthode permettant de récupérer l'index correspondant au joueur suivant
	 * @return int correspondant au joueur désiré
	 */
	public int getIndexFromNextPlayer() {
		return this.turnModel.getIndexFromNextPlayer();
	}
}
