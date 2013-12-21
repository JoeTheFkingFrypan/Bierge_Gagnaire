package utt.fr.rglb.main.java.turns.model;

import com.google.common.base.Preconditions;

import java.io.BufferedReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.console.view.View;
import utt.fr.rglb.main.java.main.ServerException;
import utt.fr.rglb.main.java.player.controller.PlayerController;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.model.PlayerTeam;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;

/**
 * Classe comprenant toutes les données en provanance des joueurs, et du passage au joueur suivant
 */
public class TurnModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private Map<Integer, PlayerTeam> teams;
	private List<PlayerController> players;
	private TurnOrder turnOrder;
	private int currentPlayerIndex;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur de TurnModel
	 * Initialise l'index du joueur en cours
	 * Initialise également le sens de jeu par défaut (sens des aiguilles d'une montre)
	 */
	public TurnModel() {
		this.turnOrder = TurnOrder.CLOCKWISE;
		this.players = new ArrayList<PlayerController>();
		this.currentPlayerIndex = -1;
		this.teams = null;
	}

	/* ========================================= RESET ========================================= */

	/**
	 * Méthode permettant de ré-initialiser l'index du joueur en cours --en début de partie (fonction du sens de jeu)
	 */
	public void resetPlayerIndex() {
		if(indicatesDefaultTurnOrder()) {
			this.currentPlayerIndex = -1;
		} else {
			this.currentPlayerIndex = this.players.size();
		}
	}

	/**
	 * Méthode permettant de ré-initialiser les mains de tous les joueurs (supression de toutes leurs cartes)
	 */
	public void resetAllHands() {
		for(PlayerController currentPlayer : this.players) {
			currentPlayer.resetHand();
		}
	}

	/**
	 * Méthode permettant de remettre le sens de jeu à sa valeur par défaut et de supprimer tous les joueurs
	 */
	public void resetTurn() {
		this.turnOrder = TurnOrder.CLOCKWISE;
		this.players.clear();
		this.teams.clear();
		resetPlayerIndex();
	}

	/* ========================================= PLAYER CREATION ========================================= */

	/**
	 * Méthode permettant de créer tous les joueurs à partir de leur nom et de leur attribuer un ordre aléatoire
	 * @param playersAwaitingCreation Collection contenant tous les noms des différents joueurs
	 * @param consoleView Vue qui sera utilisée dans le controlleur de joueurs
	 */
	public void createPlayersFrom(PlayersToCreate playersAwaitingCreation, View consoleView, BufferedReader inputStream) {
		Preconditions.checkNotNull(playersAwaitingCreation,"[ERROR] Couldn't create players from their names : provided name collection is null");
		Preconditions.checkNotNull(consoleView,"[ERROR] Couldn't create players from their names : provided view is null");
		this.players = playersAwaitingCreation.createAllPlayersFromTheirRespectiveData(consoleView,inputStream);
		scramblePlayers();
	}

	/**
	 * Méthode permettant de créer tous les joueurs à partir de leur nom SANS leur attribuer un ordre aléatoire
	 * @param playerNames Collection contenant tous les noms des différents joueurs
	 * @param consoleView Vue qui sera utilisée dans le controlleur de joueurs
	 */
	public void createPlayersWithoutScramblingFrom(Collection<String> playerNames, View consoleView, BufferedReader inputStream) {
		Preconditions.checkNotNull(playerNames,"[ERROR] Couldn't create players from their names : provided name collection is null");
		Preconditions.checkNotNull(consoleView,"[ERROR] Couldn't create players from their names : provided view is null");
		for(String name: playerNames) {
			this.players.add(new PlayerController(name,consoleView,inputStream));
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
			this.turnOrder = TurnOrder.COUNTER_CLOCKWISE;
		} else {
			this.turnOrder = TurnOrder.CLOCKWISE;
		}
	}

	/**
	 * Méthode permettant de vérifier si le sens de jeu est normal ou inversé
	 * @return <code>TRUE</code> si le sens est celui par défaut (sens des aiguilles d'une montre), <code>FALSE</code> sinon
	 */
	public boolean indicatesDefaultTurnOrder() {
		return this.turnOrder.equals(TurnOrder.CLOCKWISE);
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

	/* ========================================= PLAYER CYCLING ========================================= */

	/**
	 * Méthode permettant de trouver le joueur suivant (fonction du sens de jeu)
	 * @return Joueur suivant
	 */
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

	/**
	 * Méthode permettant de trouver le prochain joueur devant jouer son tour (sans changer l'index en cours)
	 * @return Le controlleur de joueur associé au joueur dont le tour est venu
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
	 * Méthode permettant de permettre au même joueur de jouer plusieurs cartes de suite (cas d'un jeu à 2 joueurs)
	 */
	public void cycleSilentlyThroughPlayers() {
		if(this.indicatesDefaultTurnOrder()) {
			findPreviousPlayerIndex();
		} else {
			findNextPlayerIndex();
		}
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

	/* ========================================= SCORE ========================================= */

	/**
	 * Méthode permettant d'additioner les valeurs des cartes des différents joueurs
	 * @return int correspondant au score obtenu
	 */
	public int sumAllIndividualPlayerScore() {
		int sumPlayerScore = 0;
		for(PlayerController currentPlayer : this.players) {
			sumPlayerScore += currentPlayer.getPointsFromCardsInHand();
		}
		return sumPlayerScore;
	}

	/**
	 * Méthode permettant de trouver l'équipe comportant le joueur donné
	 * @param winningPlayer Joueur dont on cherche à connaitre l'équipe
	 * @return L'équipe à laquelle appartient le joueur
	 */
	public PlayerTeam findWinningTeam(PlayerControllerBean winningPlayer) {
		Preconditions.checkNotNull(winningPlayer,"[ERROR] Impossible to find winning team : provided PlayerControllerBean cannot be null");
		for(PlayerTeam team : this.teams.values()) {
			if(team.contains(winningPlayer)) {
				return team;
			}
		}
		throw new ServerException("[ERROR] Winning player does not belong to any teams, something went wrong during teams creation");
	}
	
	/**
	 * Méthode permettant d'addition les valeurs des cartes des différentes équipes
	 * @param winningTeam Joueur ayant remporté le round
	 * @return int correspondant au score obtenu
	 */
	public int sumAllTeamScore(PlayerTeam winningTeam) {
		Preconditions.checkNotNull(winningTeam,"[ERROR] Impossible to increase team score : provided PlayerControllerBean cannot be null");
		int sumPlayerScore = 0;
		for(PlayerTeam team : this.teams.values()) {
			if(!team.equals(winningTeam)) {
				sumPlayerScore += team.getScoreFromBothHands();
			} 
		}
		return sumPlayerScore;
	}

	/**
	 * Méthode permettant de déterminer si l'équipe venant de marquer des points a remporté la partie (score total > 500)
	 * @param winningTeam Equipe venant de remporter le round
	 * @param pointsReceived Points à ajouter au score de l'équipe
	 * @return <code>TRUE</code> si le score de l'équipe est supérieur à 500 (après avoir incrémenter le score), <code>FALSE</code> sinon
	 */
	public boolean increaseScoreOfTheWinningTeam(PlayerTeam winningTeam, Integer pointsReceived) {
		Preconditions.checkNotNull(winningTeam,"[ERROR] Impossible to increase team score : provided PlayerControllerBean cannot be null");
		Preconditions.checkNotNull(pointsReceived,"[ERROR] Impossible to increase team score : provided amount cannot be null");
		return winningTeam.increaseScore(pointsReceived);
	}
	
	/* ========================================= TEAMS ========================================= */
	
	/**
	 * Méthode permettant de diviser la liste des joueurs en équipes de deux joueurs
	 */
	public void splitPlayersIntoTeams() {
		this.teams = new TreeMap<Integer,PlayerTeam>();
		Integer numberOfTeams = (this.players.size()/2);
		if(numberOfTeams == 2) {
			this.splitPlayersIntoTwoTeams(numberOfTeams);
		} else {
			this.splitPlayersIntoThreeTeams(numberOfTeams);
		}
	}

	/* ========================================= TEAMS - 4 PLAYERS GAME ========================================= */
	
	/**
	 * Méthode privée permettant de diviser les joueurs en 2 équipes distinctes (cas d'une partie avec 4 joueurs)
	 * @param numberOfTeams Nombre d'équipes à créer
	 */
	private void splitPlayersIntoTwoTeams(Integer numberOfTeams) {
		Preconditions.checkNotNull(numberOfTeams,"[ERROR] Impossible to split players into different teams : provided number of teams to create is null");
		ListIterator<PlayerController> iterator = this.players.listIterator();
		for(int i=0; i<numberOfTeams; i++) {
			PlayerTeam currentTeam = this.createOneTeamWithFourPlayersTotal(iterator);
			this.teams.put(i, currentTeam);
		}
	}
	
	/**
	 * Méthode privée permettant de créer une équipe (couple de 2 joueurs) à partir de la collection des joueurs
	 * @param iterator Position actuelle de l'itérateur sur la collection de joueurs
	 * @return Une équipe comprenant 2 joueurs distints
	 */
	private PlayerTeam createOneTeamWithFourPlayersTotal(ListIterator<PlayerController> iterator) {
		Preconditions.checkNotNull(iterator,"[ERROR] Impossible to split players into different teams : provided iterator is null");
		PlayerTeam currentTeam = new PlayerTeam();
		if(iterator.hasPrevious()) {
			iterator.previous();
			currentTeam.addFirstPlayer(iterator.previous());
			iterator.next();
			currentTeam.addSecondPlayer(iterator.next());
		} else {
			currentTeam.addFirstPlayer(iterator.next());
			iterator.next();
			currentTeam.addSecondPlayer(iterator.next());
		}
		return currentTeam;
	}
	
	/* ========================================= TEAMS - 6 PLAYERS GAME ========================================= */
	
	/**
	 * Méthode permettant de diviser les joueurs en 3 équipes distinctes (cas d'une partie avec 6 joueurs)
	 * @param numberOfTeams Nombre d'équipes à créer
	 */
	private void splitPlayersIntoThreeTeams(Integer numberOfTeams) {
		Preconditions.checkNotNull(numberOfTeams,"[ERROR] Impossible to split players into different teams : provided number of teams to create is null");
		ListIterator<PlayerController> iterator = this.players.listIterator();
		for(int i=0; i<numberOfTeams; i++) {
			PlayerTeam currentTeam = this.createOneTeamWithSixPlayersTotal(iterator);
			this.teams.put(i, currentTeam);
		}
	}

	/**
	 * Méthode privée permettant de créer une équipe (couple de 2 joueurs) à partir de la collection des joueurs
	 * @param iterator Position actuelle de l'itérateur sur la collection de joueurs
	 * @return Une équipe comprenant 2 joueurs distints
	 */
	private PlayerTeam createOneTeamWithSixPlayersTotal(ListIterator<PlayerController> iterator) {
		Preconditions.checkNotNull(iterator,"[ERROR] Impossible to split players into different teams : provided iterator is null");
		PlayerTeam currentTeam = new PlayerTeam();
		if(iterator.hasPrevious()) {
			iterator.previous();
			iterator.previous();
			currentTeam.addFirstPlayer(iterator.previous());
			iterator.next();
			iterator.next();
			iterator.next();
			currentTeam.addSecondPlayer(iterator.next());
		} else {
			currentTeam.addFirstPlayer(iterator.next());
			iterator.next();
			iterator.next();
			currentTeam.addSecondPlayer(iterator.next());
		}
		return currentTeam;
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
	 * Méthode permettant de récupérer l'index du joueur actuel
	 * @return index du joueur actuel
	 */
	public int getCurrentPlayerIndex() {
		return this.currentPlayerIndex;
	}

	/**
	 * Méthode permettant de changer l'index en cours pour celui du joueur suivant
	 * @param newPlayerIndex index du joueur suivant
	 */
	public void moveOnToCurrentPlayer(int newPlayerIndex) {
		Preconditions.checkArgument(newPlayerIndex >= 0, "[ERROR] Current index cannot be under 0");
		this.currentPlayerIndex = newPlayerIndex;
	}

	/**
	 * Méthode permettant de récupérer tous les joueurs de la partie
	 * @return Collection de controlleurs de joueurs
	 */
	public Collection<PlayerController> getAllPlayers() {
		return this.players;
	}

	/**
	 * Méthode permettant de récupérer toutes les équipes de la partie
	 * @return Collection d'équipes
	 */
	public Map<Integer, PlayerTeam> getAllTeams() {
		return this.teams;
	}
}
