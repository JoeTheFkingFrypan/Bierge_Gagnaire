package utt.fr.rglb.main.java.turns.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.console.view.View;
import utt.fr.rglb.main.java.player.controller.PlayerController;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;
import com.google.common.base.Preconditions;

/**
 * Classe comprenant toutes les donn�es en provanance des joueurs, et du passage au joueur suivant
 */
public class TurnModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<PlayerController> players;
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
		this.players = new ArrayList<PlayerController>();
		this.currentPlayerIndex = -1;
	}
	
	/* ========================================= RESET ========================================= */
	
	/**
	 * M�thode permettant de r�-initialiser l'index du joueur en cours --en d�but de partie (fonction du sens de jeu)
	 */
	public void resetPlayerIndex() {
		if(indicatesDefaultTurnOrder()) {
			this.currentPlayerIndex = -1;
		} else {
			this.currentPlayerIndex = this.players.size();
		}
	}

	/**
	 * M�thode permettant de r�-initialiser les mains de tous les joueurs (supression de toutes leurs cartes)
	 */
	public void resetAllHands() {
		for(PlayerController currentPlayer : this.players) {
			currentPlayer.resetHand();
		}
	}
	
	/**
	 * M�thode permettant de remettre le sens de jeu � sa valeur par d�faut et de supprimer tous les joueurs
	 */
	public void resetTurn() {
		this.turnOrder = TurnOrder.Clockwise;
		this.players.clear();
		resetPlayerIndex();
	}
	
	/* ========================================= PLAYER CREATION ========================================= */

	/**
	 * M�thode permettant de cr�er tous les joueurs � partir de leur nom et de leur attribuer un ordre al�atoire
	 * @param playersAwaitingCreation Collection contenant tous les noms des diff�rents joueurs
	 * @param consoleView Vue qui sera utilis�e dans le controlleur de joueurs
	 */
	public void createPlayersFrom(PlayersToCreate playersAwaitingCreation, View consoleView) {
		Preconditions.checkNotNull(playersAwaitingCreation,"[ERROR] Couldn't create players from their names : provided name collection is null");
		Preconditions.checkNotNull(consoleView,"[ERROR] Couldn't create players from their names : provided view is null");
		this.players = playersAwaitingCreation.createAllPlayersFromTheirRespectiveData(consoleView);
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

	/* ========================================= PLAYER CYCLING ========================================= */
	
	/**
	 * M�thode permettant de trouver le joueur suivant (fonction du sens de jeu)
	 * @return Joueur suivant
	 */
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
	
	/**
	 * M�thode permettant de trouver le prochain joueur devant jouer son tour (sans changer l'index en cours)
	 * @return Le controlleur de joueur associ� au joueur dont le tour est venu
	 */
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
	
	/* ========================================= SCORE ========================================= */
	
	/**
	 * M�thode permettant d'additioner les valeurs des cartes des diff�rents joueurs
	 * @return int correspondant au score obtenu
	 */
	public int sumAllPlayerScore() {
		int sumPlayerScore = 0;
		for(PlayerController currentPlayer : this.players) {
			sumPlayerScore += currentPlayer.getPointsFromCardsInHand();
		}
		return sumPlayerScore;
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
	 * M�thode permettant de r�cup�rer l'index du joueur actuel
	 * @return index du joueur actuel
	 */
	public int getCurrentPlayerIndex() {
		return this.currentPlayerIndex;
	}
	
	/**
	 * M�thode permettant de changer l'index en cours pour celui du joueur suivant
	 * @param newPlayerIndex index du joueur suivant
	 */
	public void moveOnToCurrentPlayer(int newPlayerIndex) {
		Preconditions.checkArgument(newPlayerIndex >= 0, "[ERROR] Current index cannot be under 0");
		this.currentPlayerIndex = newPlayerIndex;
	}

	/**
	 * M�thode permettant de r�cup�rer tous les joueurs de la partie
	 * @return Collection de controlleurs de joueurs
	 */
	public Collection<PlayerController> getAllPlayers() {
		return this.players;
	}
}
