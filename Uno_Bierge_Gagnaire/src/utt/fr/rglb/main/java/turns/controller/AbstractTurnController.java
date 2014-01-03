package utt.fr.rglb.main.java.turns.controller;

import java.io.Serializable;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.player.controller.AbstractPlayerController;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.model.PlayerTeam;

public abstract class AbstractTurnController implements Serializable {
	private static final long serialVersionUID = 1L;

	/* ========================================= RESET ========================================= */

	/**
	 * Méthode permettant de ré-initialiser l'index du joueur devant commencer la partie (fonction du sens de jeu)
	 */
	public abstract void resetPlayerIndex();

	/**
	 * Méthode permettant de ré-initialiser le système de tour de jeu (remise par défaut du sens de jeu, suppression de tous les joueurs)
	 */
	public abstract void resetTurn();
	
	/* ========================================= PLAYER CREATION ========================================= */
	
	/**
	 * Méthode permettant de séparer les jouers en différentes équipes de 2 joueurs
	 */
	public abstract void splitPlayersIntoTeams(); 
	
	/* ========================================= TURN ORDER ========================================= */
	
	/**
	 * Méthode permettant d'inverser le sens dans lequel est choisi le joueur suivant
	 */
	public abstract void reverseCurrentOrder();
	
	/**
	 * Méthode permettant de trouver le joueur suivant (en fonction du sens du jeu)
	 * @return Le controlleur associé au joueur suivant
	 */
	public abstract AbstractPlayerController findCurrentPlayer();
	
	/**
	 * Méthode permettant de trouver le joueur suivant
	 * @return Le prochain joueur qui doit jouer
	 */
	public abstract AbstractPlayerController findNextPlayer();
	
	/**
	 * Méthode permettant de trouver le joueur suivant (sans changer l'index du joueur actuel)
	 * @return Le prochain joueur qui doit jouer
	 */
	public abstract AbstractPlayerController findNextPlayerWithoutChangingCurrentPlayer();
	
	/* ========================================= EFFECT RELATED ========================================= */
	
	/**
	 * Méthode permettant d'empêcher le joueur suivant de jouer
	 */
	public abstract void skipNextPlayer();

	public abstract void cycleSilently();
	

	/**
	 * Méthode permettant d'initialiser la main des joueurs en leur donnant 7 cartes chacun
	 * @param cards Collection initiale de 7 cartes
	 */
	public abstract void giveCardsToNextPlayer(Collection<Card> cards);
	
	/* ========================================= ROUND WIN EVENT HANDLING ========================================= */
	
	/**
	 * Méthode permettant de calculer le score du joueur ayant gagné
	 * @param gameWinner Joueur ayant remporté le round
	 * @return <code>TRUE</code> si le joueur a gagné (score > 500 points), <code>FALSE</code> sinon
	 */
	public abstract boolean computeIndividualEndOfTurn(PlayerControllerBean gameWinner);
	
	/**
	 * Méthode permettant de calculer le score de l'équipe ayant gagné
	 * @param gameWinner Joueur ayant remporté le round
	 * @return <code>TRUE</code> si l'équipe a gagné (score > 500 points), <code>FALSE</code> sinon
	 */
	public abstract boolean computeTeamEndOfTurn(PlayerControllerBean gameWinner);
	
	/* ========================================= TEAM PLAY ========================================= */

	/**
	 * Méthode permettant de récuperer l'équipe ayant gagné (qui contient le joueur donné)
	 * @param winningPlayer Joueur victorieux dont on souhaite connaitre l'équipe
	 * @return L'équipe à laquelle appartient le joueur donné
	 */
	public abstract PlayerTeam findWinningTeam(PlayerControllerBean winningPlayer);
	
	/* ========================================= SCORE ========================================= */

	/**
	 * Méthode permettant d'afficher les scores de tous les joueurs
	 */
	public abstract void displayIndividualTotalScore();

	/**
	 * Méthode permettant d'afficher les scores de toutes les équipes
	 */
	public abstract void displayTeamTotalScore();
	
	/* ========================================= GETTERS & DISPLAY ========================================= */

	/**
	 * Méthode permettant de récupérer le nombre de joueurs
	 * @return int correspondant au nombre de joueurs
	 */
	public abstract int getNumberOfPlayers();
	
	/**
	 * Méthode permettant d'afficher la composition de toutes les équipes
	 */
	public abstract void displayTeams();
}
