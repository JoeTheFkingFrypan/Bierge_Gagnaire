package main.java.gameContext.controller;

import java.util.Collection;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Card;
import main.java.console.view.View;
import main.java.gameContext.model.TurnModel;
import main.java.player.controller.PlayerController;

/**
 * Classe dont le rôle est de gérer tout ce qui touche aux joueurs, et passage au joueur suivant
 */
public class TurnController {
	private TurnModel turnModel;
	private View consoleView;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	public TurnController(View consoleView) {
		Preconditions.checkNotNull(consoleView,"[ERROR] Impossible to create turn controller : provided view is null");
		this.turnModel = new TurnModel();
		this.consoleView = consoleView;
	}
	
	/* ========================================= PLAYER CREATION ========================================= */
	
	/**
	 * Méthode permettant de créer tous les joueurs à partir de leurs noms, et de leur donner un ordre aléatoire
	 * @param playerNames Collection contenant le nom de tous les joueurs
	 */
	public void createPlayersFrom(Collection<String> playerNames) {
		Preconditions.checkNotNull(playerNames,"[ERROR] Provided played names cannot be null");
		Preconditions.checkArgument(playerNames.size() >= 2,"[ERROR] There must be at least two players in the game");
		this.turnModel.createPlayersFrom(playerNames,consoleView);
	}
	
	/**
	 * Méthode permettant de créer tous les joueurs à partir de leurs noms, sans leur donner un ordre aléatoire
	 * @param playerNames Collection contenant le nom de tous les joueurs
	 */
	public void createPlayersWithoutScamblingFrom(Collection<String> playerNames) {
		Preconditions.checkNotNull(playerNames,"[ERROR] Provided played names cannot be null");
		Preconditions.checkArgument(playerNames.size() >= 2,"[ERROR] There must be at least two players in the game");
		this.turnModel.createPlayersWithoutScramblingFrom(playerNames,consoleView);
	}
	
	/**
	 * Méthode permettant d'initialiser la main des joueurs en leur donnant 7 cartes chacun
	 * @param cards Collection initiale de 7 cartes
	 */
	public void giveCardsToNextPlayer(Collection<Card> cards) {
		this.turnModel.giveCardsToNextPlayer(cards);
	}
	
	public void giveCardPenaltyToNextPlayer(Collection<Card> cards) {
		this.turnModel.giveCardPenaltyToNextPlayer(cards);
	}
	
	/* ========================================= TURN ORDER ========================================= */
	
	/**
	 * Méthode permettant d'inverser le sens dans lequel est choisi le joueur suivant
	 */
	public void reverseCurrentOrder() {
		this.consoleView.appendJokerText("Turn order has been inverted");
		this.consoleView.insertBlankLine();
		this.turnModel.reverseCurrentOrder();
	}
	
	public PlayerController findCurrentPlayer() {
		return this.turnModel.findCurrentPlayer();
	}
	
	/**
	 * Méthode permettant de trouver le joueur suivant
	 * @return Le prochain joueur qui doit jouer
	 */
	public PlayerController findNextPlayer() {
		PlayerController currentPlayer = this.turnModel.cycleThroughPlayers();
		consoleView.insertBlankLine();
		consoleView.displaySeparationText("========== Your turn, " + currentPlayer.getAlias() + " ==========");
		return currentPlayer;
	}

	public void skipNextPlayer() {
		PlayerController currentPlayer = this.turnModel.cycleThroughPlayers();
		consoleView.insertBlankLine();
		consoleView.displaySeparationText("========== Your turn, " + currentPlayer.getAlias() + " ==========");
		consoleView.appendJokerText("Sadly, you are not allowed to play this turn");
		consoleView.insertBlankLine();
		consoleView.appendJokerText("Previous player used a Skip card");
		consoleView.insertBlankLine();
	}
}