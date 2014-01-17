package utt.fr.rglb.main.java.turns.model;

import com.google.common.base.Preconditions;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.main.ServerException;
import utt.fr.rglb.main.java.player.controller.AbstractPlayerController;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.controller.PlayerControllerConsoleOriented;
import utt.fr.rglb.main.java.player.model.PlayerTeam;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;
import utt.fr.rglb.main.java.view.AbstractView;

/**
 * Classe comprenant toutes les données en provanance des joueurs, et du passage au joueur suivant </br>
 * Version console
 */
public class TurnModelConsoleOriented extends AbstractTurnModel {
	private static final long serialVersionUID = 1L;
	protected Map<Integer, PlayerTeam> teams;
	protected List<PlayerControllerConsoleOriented> players;
	protected TurnOrder turnOrder;
	protected int currentPlayerIndex;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur de TurnModelConsoleOriented
	 * Initialise l'index du joueur en cours
	 * Initialise également le sens de jeu par défaut (sens des aiguilles d'une montre)
	 */
	public TurnModelConsoleOriented() {
		this.turnOrder = TurnOrder.CLOCKWISE;
		this.players = new ArrayList<PlayerControllerConsoleOriented>();
		this.currentPlayerIndex = -1;
		this.teams = null;
	}

	/* ========================================= RESET ========================================= */

	@Override
	public void resetPlayerIndex() {
		if(indicatesDefaultTurnOrder()) {
			this.currentPlayerIndex = -1;
		} else {
			this.currentPlayerIndex = this.players.size();
		}
	}

	@Override
	public void resetAllHands() {
		for(PlayerControllerConsoleOriented currentPlayer : this.players) {
			currentPlayer.resetHand();
		}
	}

	@Override
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
	 * @param view Vue qui sera utilisée dans le controlleur de joueurs
	 */
	public void createPlayersFrom(PlayersToCreate playersAwaitingCreation, AbstractView view, BufferedReader inputStream) {
		Preconditions.checkNotNull(playersAwaitingCreation,"[ERROR] Couldn't create players from their names : provided name collection is null");
		Preconditions.checkNotNull(view,"[ERROR] Couldn't create players from their names : provided view is null");
		this.players = playersAwaitingCreation.createAllConsolePlayersFromTheirRespectiveData(view,inputStream);
		scramblePlayers();
	}

	/**
	 * Méthode permettant de créer tous les joueurs à partir de leur nom SANS leur attribuer un ordre aléatoire
	 * @param playerNames Collection contenant tous les noms des différents joueurs
	 * @param consoleView Vue qui sera utilisée dans le controlleur de joueurs
	 */
	public void createPlayersWithoutScramblingFrom(Collection<String> playerNames, AbstractView consoleView, BufferedReader inputStream) {
		Preconditions.checkNotNull(playerNames,"[ERROR] Couldn't create players from their names : provided name collection is null");
		Preconditions.checkNotNull(consoleView,"[ERROR] Couldn't create players from their names : provided view is null");
		for(String name: playerNames) {
			this.players.add(new PlayerControllerConsoleOriented(name,consoleView,inputStream));
		}
	}

	@Override
	protected void scramblePlayers() {
		Collections.shuffle(this.players);
	}

	/* ========================================= TURN ORDER ========================================= */

	@Override
	public void reverseCurrentOrder() {
		if(indicatesDefaultTurnOrder()) {
			this.turnOrder = TurnOrder.COUNTER_CLOCKWISE;
		} else {
			this.turnOrder = TurnOrder.CLOCKWISE;
		}
	}

	@Override
	public boolean indicatesDefaultTurnOrder() {
		return this.turnOrder.equals(TurnOrder.CLOCKWISE);
	}

	/* ========================================= CARD DEAL ========================================= */

	/**
	 * Méthode permettant d'intialiser la main d'un joueur
	 * @param cards Cartes à ajouter dans la main du joueur
	 */
	@Override
	public void giveCardsToNextPlayer(Collection<Card> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Couldn't give cards : provided card collection is null");
		PlayerControllerConsoleOriented currentPlayer = cycleThroughPlayers();
		currentPlayer.pickUpCards(cards);
	}

	/* ========================================= PLAYER CYCLING ========================================= */

	/**
	 * Méthode permettant de trouver le joueur suivant (fonction du sens de jeu)
	 * @return Joueur suivant
	 */
	@Override
	public PlayerControllerConsoleOriented findCurrentPlayer() {
		return this.players.get(this.currentPlayerIndex);
	}

	/**
	 * Méthode permettant de trouver le prochain joueur devant jouer son tour
	 * @return Le controlleur de joueur associé au joueur dont le tour est venu
	 */
	@Override
	public PlayerControllerConsoleOriented cycleThroughPlayers() {
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
	@Override
	public PlayerControllerConsoleOriented cycleThroughPlayersWithoutChangingCurrentPlayer() {
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
	@Override
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
	@Override
	protected int findNextPlayerIndex() {
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
	@Override
	protected int findPreviousPlayerIndex() {
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
	@Override
	public int sumAllIndividualPlayerScore() {
		int sumPlayerScore = 0;
		for(PlayerControllerConsoleOriented currentPlayer : this.players) {
			sumPlayerScore += currentPlayer.getPointsFromCardsInHand();
		}
		return sumPlayerScore;
	}

	/**
	 * Méthode permettant de trouver l'équipe comportant le joueur donné
	 * @param winningPlayer Joueur dont on cherche à connaitre l'équipe
	 * @return L'équipe à laquelle appartient le joueur
	 */
	@Override
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
	@Override
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
	@Override
	public boolean increaseScoreOfTheWinningTeam(PlayerTeam winningTeam, Integer pointsReceived) {
		Preconditions.checkNotNull(winningTeam,"[ERROR] Impossible to increase team score : provided PlayerControllerBean cannot be null");
		Preconditions.checkNotNull(pointsReceived,"[ERROR] Impossible to increase team score : provided amount cannot be null");
		return winningTeam.increaseScore(pointsReceived);
	}

	/* ========================================= TEAMS ========================================= */

	/**
	 * Méthode permettant de diviser la liste des joueurs en équipes de deux joueurs
	 */
	@Override
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
	@Override
	protected void splitPlayersIntoTwoTeams(Integer numberOfTeams) {
		Preconditions.checkNotNull(numberOfTeams,"[ERROR] Impossible to split players into different teams : provided number of teams to create is null");
		ListIterator<PlayerControllerConsoleOriented> iterator = this.players.listIterator();
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
	@Override
	protected PlayerTeam createOneTeamWithFourPlayersTotal(ListIterator<? extends AbstractPlayerController> iterator) {
		Preconditions.checkNotNull(iterator,"[ERROR] Impossible to split players into different teams : provided iterator is null");
		PlayerTeam currentTeam = new PlayerTeam();
		if(iterator.hasPrevious()) {
			AbstractPlayerController current = iterator.previous();
			current = iterator.previous();
			currentTeam.addFirstPlayer(current);
			current = iterator.next();
			current = iterator.next();
			current = iterator.next();
			currentTeam.addSecondPlayer(current);
		} else {
			AbstractPlayerController current = iterator.next();
			currentTeam.addFirstPlayer(current);
			current = iterator.next();
			current = iterator.next();
			currentTeam.addSecondPlayer(current);
		}
		return currentTeam;
	}

	/* ========================================= TEAMS - 6 PLAYERS GAME ========================================= */

	/**
	 * Méthode permettant de diviser les joueurs en 3 équipes distinctes (cas d'une partie avec 6 joueurs)
	 * @param numberOfTeams Nombre d'équipes à créer
	 */
	@Override
	protected void splitPlayersIntoThreeTeams(Integer numberOfTeams) {
		Preconditions.checkNotNull(numberOfTeams,"[ERROR] Impossible to split players into different teams : provided number of teams to create is null");
		ListIterator<PlayerControllerConsoleOriented> iterator = this.players.listIterator();
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
	@Override
	protected PlayerTeam createOneTeamWithSixPlayersTotal(ListIterator<? extends AbstractPlayerController> iterator) {
		Preconditions.checkNotNull(iterator,"[ERROR] Impossible to split players into different teams : provided iterator is null");
		PlayerTeam currentTeam = new PlayerTeam();
		if(iterator.hasPrevious()) {
			AbstractPlayerController current = iterator.previous();
			current = iterator.previous();
			current = iterator.previous();
			currentTeam.addFirstPlayer(current);
			current = iterator.next();
			current = iterator.next();
			current = iterator.next();
			current = iterator.next();
			currentTeam.addSecondPlayer(current);
		} else {
			AbstractPlayerController current = iterator.next();
			currentTeam.addFirstPlayer(current);
			current = iterator.next();
			current = iterator.next();
			current = iterator.next();
			currentTeam.addSecondPlayer(current);
		}
		return currentTeam;
	}

	/* ========================================= GETTERS & UTILS ========================================= */

	/**
	 * Méthode permettant de connaitre le nombre de cartes actuellement en main
	 * @return int correspondant au nombre de cartes en main
	 */
	@Override
	public int getNumberOfPlayers() {
		return this.players.size();
	}

	/**
	 * Méthode permettant de récupérer l'index du joueur actuel
	 * @return index du joueur actuel
	 */
	@Override
	public int getCurrentPlayerIndex() {
		return this.currentPlayerIndex;
	}

	/**
	 * Méthode permettant de changer l'index en cours pour celui du joueur suivant
	 * @param newPlayerIndex index du joueur suivant
	 */
	@Override
	public void moveOnToCurrentPlayer(int newPlayerIndex) {
		Preconditions.checkArgument(newPlayerIndex >= 0, "[ERROR] Current index cannot be under 0");
		this.currentPlayerIndex = newPlayerIndex;
	}

	/**
	 * Méthode permettant de récupérer tous les joueurs de la partie
	 * @return Collection de controlleurs de joueurs
	 */
	@Override
	public List<PlayerControllerConsoleOriented> getAllPlayers() {
		return this.players;
	}

	/**
	 * Méthode permettant de récupérer toutes les équipes de la partie
	 * @return Collection d'équipes
	 */
	@Override
	public Map<Integer, PlayerTeam> getAllTeams() {
		return this.teams;
	}
}
