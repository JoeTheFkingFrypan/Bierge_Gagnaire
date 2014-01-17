package utt.fr.rglb.main.java.game.controller;

import java.io.Serializable;

import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;

public abstract class AbstractGameController implements Serializable {
	private static final long serialVersionUID = 1L;

	/* ========================================= GAME LOGIC ========================================= */

	/**
	 * Méthode permettant de lancer une nouvelle partie de UNO
	 * A chaque demarrage sera demandé un certain nombre d'informations (nombre de joueurs, leurs noms)
	 */
	public abstract void startAnotherGame();

	/**
	 * Méthode privée permettant de lancer un nombre infini de "round" jusqu'à ce qu'un joueur ait plus de 500 points
	 * @return PlayerControllerBean Objet englobant un joueur, permettant d'avoir accès à des méthodes de haut niveau facilement
	 */
	protected abstract PlayerControllerBean cycleUntilSomeoneWins();

	/**
	 * Méthode privée permettant de lancer un nouveau round, faisant jouer tous les participants à tour de rôle jusqu'à ce que l'un d'eux n'ait plus de cartes
	 * @return PlayerControllerBean Objet englobant un joueur, permettant d'avoir accès à des méthodes de haut niveau facilement
	 */
	protected abstract PlayerControllerBean playOneRound();

	/**
	 * Méthode privée permettant de réinitialiser les cartes des joueurs/pioche/talon et de tirer la 1ère carte du jeu au début de chaque round
	 */
	protected abstract void startNewRound();
	
	/* ========================================= UNO HANDLING ========================================= */
	
	/**
	 * Méthode privée permettant de gérer l'oubli de l'annonce UNO par un joueur ayant joué sa dernière carte
	 * @param roundWinner PlayerControllerBean Objet englobant le joueur en cours, permettant d'avoir accès à des méthodes de haut niveau facilement
	 */
	protected abstract void handleMissingUnoAnnoucement(PlayerControllerBean roundWinner);

	/**
	 * Méthode privée permettant de gérer l'annonce (abusive ou non) de UNO
	 * @param roundWinner PlayerControllerBean Objet englobant le joueur en cours, permettant d'avoir accès à des méthodes de haut niveau facilement
	 */
	protected abstract void handleUnoAnnoucement(PlayerControllerBean roundWinner);

	/* ========================================= WIN EVENT ========================================= */

	/**
	 * Méthode privée permettant de gérer l'évènement de victoire d'un des joueurs
	 * @param winningPlayer PlayerControllerBean Objet englobant le joueur victorieux, permettant d'avoir accès à des méthodes de haut niveau facilement
	 */
	protected abstract void handleWinEvent(PlayerControllerBean winningPlayer);
}
