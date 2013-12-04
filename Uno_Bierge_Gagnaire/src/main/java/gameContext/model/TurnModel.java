package main.java.gameContext.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Card;
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

	/**
	 * Constructeur de TurnModel
	 * Initialise l'index du joueur en cours
	 * Initialise également le sens de jeu par défaut (sens des aiguilles d'une montre)
	 */
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
		Preconditions.checkNotNull(playerNames,"[ERROR] Couldn't create players from their names : provided name collection is null");
		Preconditions.checkNotNull(consoleView,"[ERROR] Couldn't create players from their names : provided view is null");
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
		Preconditions.checkNotNull(playerNames,"[ERROR] Couldn't create players from their names : provided name collection is null");
		Preconditions.checkNotNull(consoleView,"[ERROR] Couldn't create players from their names : provided view is null");
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
	
	/* ========================================= CARD DEAL ========================================= */
	
	/**
	 * Méthode permettant d'intialiser la main d'un joueur
	 * @param cards Cartes à ajouter dans la main du joueur
	 */
	public void giveCardsToNextPlayer(Collection<Card> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Couldn't give cards : provided card collection is null");
		PlayerController currentPlayer = cycleThroughPlayers();
		currentPlayer.pickUpCards(cards);
	}

	public void giveCardPenaltyToNextPlayer(Collection<Card> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Couldn't give card penalty : provided card collection is null");
		PlayerController currentPlayer = cycleThroughPlayersWithoutChangingCurrentPlayer();
		currentPlayer.isForcedToPickUpCards(cards);
	}

	/* ========================================= PLAYER CYCLING ========================================= */
	
	public PlayerController findCurrentPlayer() {
		return this.players.get(this.currentPlayerIndex);
	}
	
	/**
	 * Méthode permettant de trouver le prochain joueur devant jouer son tour
	 * @return Le controlleur de joueur associé au joueur dont le tour est venu
	 */
	public PlayerController cycleThroughPlayers() {
		int playerIndex;
		if(this.indicatesDefaultTurnOrder()) {
			playerIndex = findNextPlayerIndex();
		} else {
			playerIndex = findPreviousPlayerIndex();
		}
		moveOnToCurrentPlayer(playerIndex);
		return this.players.get(playerIndex);
	}
	
	public PlayerController cycleThroughPlayersWithoutChangingCurrentPlayer() {
		int playerIndex;
		if(this.indicatesDefaultTurnOrder()) {
			playerIndex = findNextPlayerIndex();
		} else {
			playerIndex = findPreviousPlayerIndex();
		}
		return this.players.get(playerIndex);
	}

	/**
	 * Méthode privée permettant de trouver le joueur suivant --sens des aiguilles d'une montre
	 * @return Le controlleur de joueur associé au joueur dont le tour est venu
	 */
	private int findNextPlayerIndex() {
		int index = getCurrentPlayerIndex();
		index = index + 1;
		if(index >= this.players.size()) {
			return 0;
		}
		return index;
	}

	/**
	 * Méthode privée permettant de trouver le joueur précédent --sens contraire des aiguilles d'une montre
	 * @return Le controlleur de joueur associé au joueur dont le tour est venu
	 */
	private int findPreviousPlayerIndex() {
		int index = getCurrentPlayerIndex();
		index = index - 1;
		if(index < 0) {
			return this.players.size() - 1;
		}
		return index;
	}	
	
	/* ========================================= GETTERS & UTILS ========================================= */

	/**
	 * Méthode permettant de connaitre le nombre de cartes actuellement en main
	 * @return int correspondant au nombre de cartes en main
	 */
	public int getNumberOfPlayers() {
		return this.players.size();
	}
	
	public int getCurrentPlayerIndex() {
		return this.currentPlayerIndex;
	}
	
	public void moveOnToCurrentPlayer(int newPlayerIndex) {
		Preconditions.checkArgument(newPlayerIndex >= 0, "[ERROR] Current index cannot be under 0");
		this.currentPlayerIndex = newPlayerIndex;
	}

	public void resetPlayerIndex() {
		if(indicatesDefaultTurnOrder()) {
			this.currentPlayerIndex = -1;
		} else {
			this.currentPlayerIndex = this.players.size() + 1;
		}
	}

	public int sumAllPlayerScore() {
		int sumPlayerScore = 0;
		for(PlayerController currentPlayer : this.players) {
			sumPlayerScore += currentPlayer.getPointsFromCardsInHand();
		}
		return sumPlayerScore;
	}

	public void resetAllHands() {
		for(PlayerController currentPlayer : this.players) {
			currentPlayer.resetHand();
		}
	}
}
