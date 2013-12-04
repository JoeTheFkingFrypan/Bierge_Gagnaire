package main.java.gameContext.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Card;
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

	/**
	 * Constructeur de TurnModel
	 * Initialise l'index du joueur en cours
	 * Initialise �galement le sens de jeu par d�faut (sens des aiguilles d'une montre)
	 */
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
		Preconditions.checkNotNull(playerNames,"[ERROR] Couldn't create players from their names : provided name collection is null");
		Preconditions.checkNotNull(consoleView,"[ERROR] Couldn't create players from their names : provided view is null");
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
		Preconditions.checkNotNull(playerNames,"[ERROR] Couldn't create players from their names : provided name collection is null");
		Preconditions.checkNotNull(consoleView,"[ERROR] Couldn't create players from their names : provided view is null");
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
	
	/* ========================================= CARD DEAL ========================================= */
	
	/**
	 * M�thode permettant d'intialiser la main d'un joueur
	 * @param cards Cartes � ajouter dans la main du joueur
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
	 * M�thode permettant de trouver le prochain joueur devant jouer son tour
	 * @return Le controlleur de joueur associ� au joueur dont le tour est venu
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
	 * M�thode priv�e permettant de trouver le joueur suivant --sens des aiguilles d'une montre
	 * @return Le controlleur de joueur associ� au joueur dont le tour est venu
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
	 * M�thode priv�e permettant de trouver le joueur pr�c�dent --sens contraire des aiguilles d'une montre
	 * @return Le controlleur de joueur associ� au joueur dont le tour est venu
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
	 * M�thode permettant de connaitre le nombre de cartes actuellement en main
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
