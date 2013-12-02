package main.java.gameContext.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import main.java.cards.model.basics.Carte;
import main.java.console.view.View;
import main.java.player.controller.PlayerController;

/**
 * Classe comprenant toutes les donn�es en provanance des joueurs, et du passage au joueur suivant
 */
public class TurnModel {
	private LinkedList<PlayerController> players;
	private TurnOrder turnOrder;
	private int currentPlayerIndex;
	
	/* ========================================= CONSTRUCTOR ========================================= */

	public TurnModel() {
		this.turnOrder = TurnOrder.Clockwise;
		this.players = new LinkedList<PlayerController>();
		this.currentPlayerIndex = -1;
	}
	
	/* ========================================= PLAYER CREATION ========================================= */

	/**
	 * M�thode permettant de cr�er tous les joueurs � partir de leur nom et de leur attribuer un ordre al�atoire
	 * @param playerNames Collection contenant tous les noms des diff�rents joueurs
	 * @param consoleView Vue qui sera utilis�e dans le controlleur de joueurs
	 */
	public void createPlayersFrom(Collection<String> playerNames, View consoleView) {
		for(String name: playerNames) {
			this.players.add(new PlayerController(name,consoleView));
		}
		scramblePlayers();
	}

	/**
	 * M�thode permettant de cr�er tous les joueurs � partir de leur nom SANS leur attribuer un ordre al�atoire
	 * @param playerNames Collection contenant tous les noms des diff�rents joueurs
	 * @param consoleView Vue qui sera utilis�e dans le controlleur de joueurs
	 */
	public void createPlayersWithoutScramblingFrom(Collection<String> playerNames, View consoleView) {
		for(String name: playerNames) {
			this.players.add(new PlayerController(name,consoleView));
		}
	}

	/**
	 * M�thode priv�e permettant d'attribuer un ordre al�atoire aux joueurs
	 */
	private void scramblePlayers() {
		Collections.shuffle(this.players);
	}

	/* ========================================= TURN ORDER ========================================= */
	
	/**
	 * M�thode permettant de changer le sens de jeu (par d�faut : sens des aiguilles d'une montre)
	 */
	public void reverseCurrentOrder() {
		if(indicatesDefaultTurnOrder()) {
			this.turnOrder = TurnOrder.CounterClockwise;
		} else {
			this.turnOrder = TurnOrder.Clockwise;
		}
	}

	/**
	 * M�thode permettant de v�rifier si le sens de jeu est normal ou invers�
	 * @return TRUE si le sens est celui par d�faut (sens des aiguilles d'une montre), FALSE sino
	 */
	public boolean indicatesDefaultTurnOrder() {
		return this.turnOrder.equals(TurnOrder.Clockwise);
	}

	/* ========================================= PLAYER CYCLING ========================================= */
	
	/**
	 * M�thode permettant de trouver le prochain joueur devant jouer son tour
	 * @return Le controlleur de joueur associ� au joueur dont le tour est venu
	 */
	public PlayerController cycleThroughPlayers() {
		if(this.indicatesDefaultTurnOrder()) {
			return this.players.get(findNextPlayerIndex());
		} else {
			return this.players.get(findPreviousPlayerIndex());
		}
	}

	/**
	 * M�thode priv�e permettant de trouver le joueur suivant --sens des aiguilles d'une montre
	 * @return Le controlleur de joueur associ� au joueur dont le tour est venu
	 */
	private int findNextPlayerIndex() {
		++(this.currentPlayerIndex);
		if(this.currentPlayerIndex >= this.players.size()) {
			this.currentPlayerIndex = 0;
		}
		return this.currentPlayerIndex;
	}

	/**
	 * M�thode priv�e permettant de trouver le joueur pr�c�dent --sens contraire des aiguilles d'une montre
	 * @return Le controlleur de joueur associ� au joueur dont le tour est venu
	 */
	private int findPreviousPlayerIndex() {
		--(this.currentPlayerIndex);
		if(this.currentPlayerIndex < 0) {
			this.currentPlayerIndex = this.players.size() - 1;
		}
		return this.currentPlayerIndex;
	}
	
	/* ========================================= GETTERS & UTILS ========================================= */

	/**
	 * M�thode permettant de connaitre le nombre de cartes actuellement en main
	 * @return int correspondant au nombre de cartes en main
	 */
	public int getNumberOfPlayers() {
		return this.players.size();
	}

	/**
	 * M�thode permettant d'intialiser la main d'un joueur
	 * @param cards Cartes � ajouter dans la main du joueur
	 */
	public void giveCardsToNextPlayer(Collection<Carte> cards) {
		PlayerController currentPlayer = cycleThroughPlayers();
		currentPlayer.pickUpCards(cards);
	}
}
