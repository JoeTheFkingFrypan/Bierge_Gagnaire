package utt.fr.rglb.main.java.game.controller;

import java.io.BufferedReader;
import java.io.Serializable;

import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.console.view.View;
import utt.fr.rglb.main.java.game.model.GameModel;
import utt.fr.rglb.main.java.player.controller.PlayerController;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.model.PlayerTeam;

/**
 * Classe permettant de gérer l'ensemble de la partie
 */
public class GameController implements Serializable {
	private static final long serialVersionUID = 1L;
	private GameModel gameModel;
	private View consoleView;

	/* ========================================= CONSTRUCTOR ========================================= */
	
	/**
	 * Constructeur de GameController
	 * @param consoleView Vue permettant l'affichage d'informations
	 */
	public GameController(View consoleView, BufferedReader inputStream) {
		this.consoleView = consoleView;
		this.gameModel = new GameModel(consoleView,inputStream);
	}

	/* ========================================= INITIALIZING ========================================= */

	/**
	 * Méthode permettant d'initialiser les paramètres (nombre de joueurs, nom de chacun des joueurs)
	 */
	private void initializeGameSettings() {
		this.consoleView.displayTitle("SETTINGS");
		this.gameModel.initializeGameSettings();
	}

	/**
	 * Méthode permettant de tout ré-initialiser lors du demarrage d'une nouvelle partie
	 */
	private void resetEverything() {
		this.gameModel.resetEverything();
	}

	/* ========================================= GAME LOGIC ========================================= */

	/**
	 * Méthode permettant de lancer une nouvelle partie de UNO
	 * A chaque demarrage sera demandé un certain nombre d'informations (nombre de joueurs, leurs noms)
	 */
	public void startAnotherGame() {
		initializeGameSettings();
		PlayerControllerBean winningPlayer = cycleUntilSomeoneWins();
		handleWinEvent(winningPlayer);
	}

	/**
	 * Méthode privée permettant de lancer un nombre infini de "round" jusqu'à ce qu'un joueur ait plus de 500 points
	 * @return PlayerControllerBean Objet englobant un joueur, permettant d'avoir accès à des méthodes de haut niveau facilement
	 */
	private PlayerControllerBean cycleUntilSomeoneWins() {
		PlayerControllerBean gameWinner = new PlayerControllerBean();
		while(gameWinner.hasNotWonTheGame()) {
			gameWinner = playOneRound();
			this.gameModel.computeScores(gameWinner);
		}
		return gameWinner;
	}

	/**
	 * Méthode privée permettant de lancer un nouveau round, faisant jouer tous les participants à tour de rôle jusqu'à ce que l'un d'eux n'ait plus de cartes
	 * @return PlayerControllerBean Objet englobant un joueur, permettant d'avoir accès à des méthodes de haut niveau facilement
	 */
	private PlayerControllerBean playOneRound() {
		startNewRound();
		PlayerControllerBean roundWinner = new PlayerControllerBean();
		while(roundWinner.stillHasCards()) {
			roundWinner = this.gameModel.playOneTurn();
			if(roundWinner.hasAnnouncedUno()) {
				handleUnoAnnoucement(roundWinner);
			} else if(roundWinner.hasNoCardAndForgotToAnnounceUno()) {
				handleMissingUnoAnnoucement(roundWinner);
			}
		}
		return roundWinner;
	}

	/**
	 * Méthode privée permettant de réinitialiser les cartes des joueurs/pioche/talon et de tirer la 1ère carte du jeu au début de chaque round
	 */
	private void startNewRound() {
		this.consoleView.displayTitle("NEW ROUND STARTING");
		this.gameModel.initializeCardsAndHands();
		this.gameModel.drawFirstCardAndApplyItsEffect();
	}
	
	/* ========================================= UNO HANDLING ========================================= */
	
	/**
	 * Méthode privée permettant de gérer l'oubli de l'annonce UNO par un joueur ayant joué sa dernière carte
	 * @param roundWinner PlayerControllerBean Objet englobant le joueur en cours, permettant d'avoir accès à des méthodes de haut niveau facilement
	 */
	private void handleMissingUnoAnnoucement(PlayerControllerBean roundWinner) {
		Preconditions.checkNotNull(roundWinner,"[ERROR] Impossible to handle UNO annoucement : provided player is null");
		PlayerController currentPlayer = roundWinner.getPlayer();
		this.consoleView.displayJokerEmphasisUsingPlaceholders("Player [", currentPlayer.getAlias(), "] played his last card !");
		this.consoleView.displayErrorMessageUsingPlaceholders("But since he forgot to announce ","UNO",", we gladly offer him ", "2 more cards");
		this.gameModel.giveCardPenaltyTo(roundWinner,2);
	}

	/**
	 * Méthode privée permettant de gérer l'annonce (abusive ou non) de UNO
	 * @param roundWinner PlayerControllerBean Objet englobant le joueur en cours, permettant d'avoir accès à des méthodes de haut niveau facilement
	 */
	private void handleUnoAnnoucement(PlayerControllerBean roundWinner) {
		Preconditions.checkNotNull(roundWinner,"[ERROR] Impossible to handle UNO annoucement : provided player is null");
		this.consoleView.displayJokerEmphasisUsingPlaceholders("Player [", roundWinner.getAlias(), "] announced UNO");
		if(!roundWinner.deservesTheRightToAnnounceUno()) {
			this.consoleView.displayErrorMessage("Since that annoucement is irrelevant (he's not playing his 2nd last card)", "He receives a 2 cards penalty. Pretty dumb, right?");
			this.gameModel.giveCardPenaltyTo(roundWinner,2);
		}
	}

	/* ========================================= WIN EVENT ========================================= */

	/**
	 * Méthode privée permettant de gérer l'évènement de victoire d'un des joueurs
	 * @param winningPlayer PlayerControllerBean Objet englobant le joueur victorieux, permettant d'avoir accès à des méthodes de haut niveau facilement
	 */
	private void handleWinEvent(PlayerControllerBean winningPlayer) {
		Preconditions.checkNotNull(winningPlayer,"[ERROR] Impossible to handle winning player : provided player is null");
		if(this.gameModel.indicatesTeamPlayScoring()) {
			PlayerTeam winningTeam = this.gameModel.findWinningTeam(winningPlayer);
			this.consoleView.displayJokerEmphasisUsingPlaceholders("Player [",winningTeam.toString(),"] won the game !");
		} else {
			this.consoleView.displayJokerEmphasisUsingPlaceholders("Player [",winningPlayer.getAlias(),"] won the game !");
		}
		this.consoleView.displayChoice("Would you like to start another game ?","0:YES ","1:NO");
		int answer = this.gameModel.getValidChoiceAnswer();
		if(answer == 0) {
			this.consoleView.displayOneLineOfGreenText("LET'S ROLL :D");
			resetEverything();
			startAnotherGame();
		} else {
			this.consoleView.displayOneLineOfRedText("Your call. --You'll probably regret that decision");
		}
	}
	

}
