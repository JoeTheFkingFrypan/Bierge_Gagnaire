package utt.fr.rglb.main.java.turns.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.player.controller.AbstractPlayerController;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.model.PlayerTeam;

public abstract class AbstractTurnModel implements Serializable {
	protected static final long serialVersionUID = 1L;
	
	/* ========================================= RESET ========================================= */

	/**
	 * Méthode permettant de ré-initialiser l'index du joueur en cours --en début de partie (fonction du sens de jeu)
	 */
	public abstract void resetPlayerIndex();

	/**
	 * Méthode permettant de ré-initialiser les mains de tous les joueurs (supression de toutes leurs cartes)
	 */
	public abstract void resetAllHands();

	/**
	 * Méthode permettant de remettre le sens de jeu à sa valeur par défaut et de supprimer tous les joueurs
	 */
	public abstract void resetTurn();

	/* ========================================= PLAYER CREATION ========================================= */
	
	/**
	 * Méthode permettant d'attribuer un ordre aléatoire aux joueurs
	 */
	protected abstract void scramblePlayers();
	
	/* ========================================= TURN ORDER ========================================= */

	/**
	 * Méthode permettant de changer le sens de jeu (par défaut : sens des aiguilles d'une montre)
	 */
	public abstract void reverseCurrentOrder();

	/**
	 * Méthode permettant de vérifier si le sens de jeu est normal ou inversé
	 * @return <code>TRUE</code> si le sens est celui par défaut (sens des aiguilles d'une montre), <code>FALSE</code> sinon
	 */
	public abstract boolean indicatesDefaultTurnOrder();

	/* ========================================= CARD DEAL ========================================= */

	/**
	 * Méthode permettant d'intialiser la main d'un joueur
	 * @param cards Cartes à ajouter dans la main du joueur
	 */
	public abstract void giveCardsToNextPlayer(Collection<Card> cards);

	/* ========================================= PLAYER CYCLING ========================================= */

	/**
	 * Méthode permettant de trouver le joueur suivant (fonction du sens de jeu)
	 * @return Joueur suivant
	 */
	public abstract AbstractPlayerController findCurrentPlayer();

	/**
	 * Méthode permettant de trouver le prochain joueur devant jouer son tour
	 * @return Le controlleur de joueur associé au joueur dont le tour est venu
	 */
	public abstract AbstractPlayerController cycleThroughPlayers();

	/**
	 * Méthode permettant de trouver le prochain joueur devant jouer son tour (sans changer l'index en cours)
	 * @return Le controlleur de joueur associé au joueur dont le tour est venu
	 */
	public abstract AbstractPlayerController cycleThroughPlayersWithoutChangingCurrentPlayer();

	/**
	 * Méthode permettant de permettre au même joueur de jouer plusieurs cartes de suite (cas d'un jeu à 2 joueurs)
	 */
	public abstract void cycleSilentlyThroughPlayers();

	/**
	 * Méthode privée permettant de trouver le joueur suivant --sens des aiguilles d'une montre
	 * @return Le controlleur de joueur associé au joueur dont le tour est venu
	 */
	protected abstract int findNextPlayerIndex();

	/**
	 * Méthode privée permettant de trouver le joueur précédent --sens contraire des aiguilles d'une montre
	 * @return Le controlleur de joueur associé au joueur dont le tour est venu
	 */
	protected abstract int findPreviousPlayerIndex();

	/* ========================================= SCORE ========================================= */

	/**
	 * Méthode permettant d'additioner les valeurs des cartes des différents joueurs
	 * @return int correspondant au score obtenu
	 */
	public abstract int sumAllIndividualPlayerScore();

	/**
	 * Méthode permettant de trouver l'équipe comportant le joueur donné
	 * @param winningPlayer Joueur dont on cherche à connaitre l'équipe
	 * @return L'équipe à laquelle appartient le joueur
	 */
	public abstract PlayerTeam findWinningTeam(PlayerControllerBean winningPlayer);
	
	/**
	 * Méthode permettant d'addition les valeurs des cartes des différentes équipes
	 * @param winningTeam Joueur ayant remporté le round
	 * @return int correspondant au score obtenu
	 */
	public abstract int sumAllTeamScore(PlayerTeam winningTeam);

	/**
	 * Méthode permettant de déterminer si l'équipe venant de marquer des points a remporté la partie (score total > 500)
	 * @param winningTeam Equipe venant de remporter le round
	 * @param pointsReceived Points à ajouter au score de l'équipe
	 * @return <code>TRUE</code> si le score de l'équipe est supérieur à 500 (après avoir incrémenter le score), <code>FALSE</code> sinon
	 */
	public abstract boolean increaseScoreOfTheWinningTeam(PlayerTeam winningTeam, Integer pointsReceived);
	
	/* ========================================= TEAMS ========================================= */
	
	/**
	 * Méthode permettant de diviser la liste des joueurs en équipes de deux joueurs
	 */
	public abstract void splitPlayersIntoTeams();

	/* ========================================= TEAMS - 4 PLAYERS GAME ========================================= */
	
	/**
	 * Méthode privée permettant de diviser les joueurs en 2 équipes distinctes (cas d'une partie avec 4 joueurs)
	 * @param numberOfTeams Nombre d'équipes à créer
	 */
	protected abstract void splitPlayersIntoTwoTeams(Integer numberOfTeams);
	
	/**
	 * Méthode privée permettant de créer une équipe (couple de 2 joueurs) à partir de la collection des joueurs
	 * @param iterator Position actuelle de l'itérateur sur la collection de joueurs
	 * @return Une équipe comprenant 2 joueurs distints
	 */
	protected abstract PlayerTeam createOneTeamWithFourPlayersTotal(ListIterator<? extends AbstractPlayerController> iterator);
	
	/* ========================================= TEAMS - 6 PLAYERS GAME ========================================= */
	
	/**
	 * Méthode permettant de diviser les joueurs en 3 équipes distinctes (cas d'une partie avec 6 joueurs)
	 * @param numberOfTeams Nombre d'équipes à créer
	 */
	protected abstract void splitPlayersIntoThreeTeams(Integer numberOfTeams);

	/**
	 * Méthode privée permettant de créer une équipe (couple de 2 joueurs) à partir de la collection des joueurs
	 * @param iterator Position actuelle de l'itérateur sur la collection de joueurs
	 * @return Une équipe comprenant 2 joueurs distints
	 */
	protected abstract PlayerTeam createOneTeamWithSixPlayersTotal(ListIterator<? extends AbstractPlayerController> iterator);
	
	/* ========================================= GETTERS & UTILS ========================================= */
	
	/**
	 * Méthode permettant de connaitre le nombre de cartes actuellement en main
	 * @return int correspondant au nombre de cartes en main
	 */
	public abstract int getNumberOfPlayers();

	/**
	 * Méthode permettant de récupérer l'index du joueur actuel
	 * @return index du joueur actuel
	 */
	public abstract int getCurrentPlayerIndex();

	/**
	 * Méthode permettant de changer l'index en cours pour celui du joueur suivant
	 * @param newPlayerIndex index du joueur suivant
	 */
	public abstract void moveOnToCurrentPlayer(int newPlayerIndex);

	/**
	 * Méthode permettant de récupérer tous les joueurs de la partie
	 * @return Collection de controlleurs de joueurs
	 */
	public abstract List<? extends AbstractPlayerController> getAllPlayers();

	/**
	 * Méthode permettant de récupérer toutes les équipes de la partie
	 * @return Collection d'équipes
	 */
	public abstract Map<Integer, PlayerTeam> getAllTeams();
}
