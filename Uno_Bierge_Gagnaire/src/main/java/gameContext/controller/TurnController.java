package main.java.gameContext.controller;

import java.util.Collection;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Carte;
import main.java.console.view.View;
import main.java.gameContext.model.TurnModel;
import main.java.player.controller.PlayerController;

/**
 * Classe dont le r�le est de g�rer tout ce qui touche aux joueurs, et passage au joueur suivant
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
	 * M�thode permettant de cr�er tous les joueurs � partir de leurs noms, et de leur donner un ordre al�atoire
	 * @param playerNames Collection contenant le nom de tous les joueurs
	 */
	public void createPlayersFrom(Collection<String> playerNames) {
		Preconditions.checkNotNull(playerNames,"[ERROR] Provided played names cannot be null");
		Preconditions.checkArgument(playerNames.size() >= 2,"[ERROR] There must be at least two players in the game");
		this.turnModel.createPlayersFrom(playerNames,consoleView);
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
	public void giveCardsToNextPlayer(Collection<Carte> cards) {
		this.turnModel.giveCardsToNextPlayer(cards);
	}
	
	/* ========================================= TURN ORDER ========================================= */
	
	/**
	 * M�thode permettant d'inverser le sens dans lequel est choisi le joueur suivant
	 */
	public void reverseCurrentOrder() {
		this.turnModel.reverseCurrentOrder();
	}
	
	/**
	 * M�thode permettant de trouver le joueur suivant
	 * @return Le prochain joueur qui doit jouer
	 */
	public PlayerController findNextPlayer() {
		PlayerController currentPlayer = this.turnModel.cycleThroughPlayers();
		consoleView.insertBlankLine();
		consoleView.displaySeparationText("========== Your turn, " + currentPlayer.getAlias() + " ==========");
		return currentPlayer;
	}
}