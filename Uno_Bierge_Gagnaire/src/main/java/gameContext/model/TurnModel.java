package main.java.gameContext.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import main.java.cards.model.basics.Carte;
import main.java.console.view.View;
import main.java.player.controller.PlayerController;

/**
 * Classe comprenant toutes les données en provanance des joueurs, et du passage au joueur suivant
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
	 * Méthode permettant de créer tous les joueurs à partir de leur nom et de leur attribuer un ordre aléatoire
	 * @param playerNames Collection contenant tous les noms des différents joueurs
	 * @param consoleView Vue qui sera utilisée dans le controlleur de joueurs
	 */
	public void createPlayersFrom(Collection<String> playerNames, View consoleView) {
		for(String name: playerNames) {
			this.players.add(new PlayerController(name,consoleView));
		}
		scramblePlayers();
	}

	/**
	 * Méthode permettant de créer tous les joueurs à partir de leur nom SANS leur attribuer un ordre aléatoire
	 * @param playerNames Collection contenant tous les noms des différents joueurs
	 * @param consoleView Vue qui sera utilisée dans le controlleur de joueurs
	 */
	public void createPlayersWithoutScramblingFrom(Collection<String> playerNames, View consoleView) {
		for(String name: playerNames) {
			this.players.add(new PlayerController(name,consoleView));
		}
	}

	/**
	 * Méthode privée permettant d'attribuer un ordre aléatoire aux joueurs
	 */
	private void scramblePlayers() {
		Collections.shuffle(this.players);
	}

	/* ========================================= TURN ORDER ========================================= */
	
	/**
	 * Méthode permettant de changer le sens de jeu (par défaut : sens des aiguilles d'une montre)
	 */
	public void reverseCurrentOrder() {
		if(indicatesDefaultTurnOrder()) {
			this.turnOrder = TurnOrder.CounterClockwise;
		} else {
			this.turnOrder = TurnOrder.Clockwise;
		}
	}

	/**
	 * Méthode permettant de vérifier si le sens de jeu est normal ou inversé
	 * @return TRUE si le sens est celui par défaut (sens des aiguilles d'une montre), FALSE sino
	 */
	public boolean indicatesDefaultTurnOrder() {
		return this.turnOrder.equals(TurnOrder.Clockwise);
	}

	/* ========================================= PLAYER CYCLING ========================================= */
	
	/**
	 * Méthode permettant de trouver le prochain joueur devant jouer son tour
	 * @return Le controlleur de joueur associé au joueur dont le tour est venu
	 */
	public PlayerController cycleThroughPlayers() {
		if(this.indicatesDefaultTurnOrder()) {
			return this.players.get(findNextPlayerIndex());
		} else {
			return this.players.get(findPreviousPlayerIndex());
		}
	}

	/**
	 * Méthode privée permettant de trouver le joueur suivant --sens des aiguilles d'une montre
	 * @return Le controlleur de joueur associé au joueur dont le tour est venu
	 */
	private int findNextPlayerIndex() {
		++(this.currentPlayerIndex);
		if(this.currentPlayerIndex >= this.players.size()) {
			this.currentPlayerIndex = 0;
		}
		return this.currentPlayerIndex;
	}

	/**
	 * Méthode privée permettant de trouver le joueur précédent --sens contraire des aiguilles d'une montre
	 * @return Le controlleur de joueur associé au joueur dont le tour est venu
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
	 * Méthode permettant de connaitre le nombre de cartes actuellement en main
	 * @return int correspondant au nombre de cartes en main
	 */
	public int getNumberOfPlayers() {
		return this.players.size();
	}

	/**
	 * Méthode permettant d'intialiser la main d'un joueur
	 * @param cards Cartes à ajouter dans la main du joueur
	 */
	public void giveCardsToNextPlayer(Collection<Carte> cards) {
		PlayerController currentPlayer = cycleThroughPlayers();
		currentPlayer.pickUpCards(cards);
	}
}
