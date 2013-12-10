package utt.fr.rglb.main.java.game.controller;

import utt.fr.rglb.main.java.console.view.View;
import utt.fr.rglb.main.java.game.model.GameModel;
import utt.fr.rglb.main.java.player.controller.PlayerController;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;

public class GameController {
	private GameModel gameModel;
	private View consoleView;

	public GameController(View consoleView) {
		this.consoleView = consoleView;
		this.gameModel = new GameModel(consoleView);
	}

	/* ========================================= INITIALIZING ========================================= */

	/**
	 * M�thode permettant d'initialiser les param�tres (nombre de joueurs, nom de chacun des joueurs)
	 */
	private void initializeGameSettings() {
		this.consoleView.displayTitle("SETTINGS");
		this.gameModel.initializeGameSettings();
	}

	/* ========================================= GAME LOGIC ========================================= */

	/**
	 * M�thode permettant de lancer une nouvelle partie de UNO
	 * A chaque demarrage sera demand� un certain nombre d'informations (nombre de joueurs, leurs noms)
	 */
	public void startAnotherGame() {
		initializeGameSettings();
		PlayerControllerBean winningPlayer = cycleUntilSomeoneWins();
		handleWinEvent(winningPlayer.getPlayer());
	}

	/**
	 * M�thode priv�e permettant de lancer un nombre infini de "round" jusqu'� ce qu'un joueur ait plus de 500 points
	 * @return PlayerControllerBean Objet englobant un joueur, permettant d'avoir acc�s � des m�thodes de haut niveau facilement
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
	 * M�thode priv�e permettant de lancer un nouveau round, faisant jouer tous les participants � tour de r�le jusqu'� ce que l'un d'eux n'ait plus de cartes
	 * @return PlayerControllerBean Objet englobant un joueur, permettant d'avoir acc�s � des m�thodes de haut niveau facilement
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
	 * M�thode priv�e permettant de r�initialiser les cartes des joueurs/pioche/talon et de tirer la 1�re carte du jeu au d�but de chaque round
	 */
	private void startNewRound() {
		this.consoleView.displayTitle("NEW ROUND STARTING");
		this.gameModel.initializeCardsAndHands();
		this.gameModel.drawFirstCardAndApplyItsEffect();
	}
	
	/**
	 * M�thode priv�e permettant de g�rer l'oubli de l'annonce UNO par un joueur ayant jou� sa derni�re carte
	 * @param roundWinner PlayerControllerBean Objet englobant le joueur en cours, permettant d'avoir acc�s � des m�thodes de haut niveau facilement
	 */
	private void handleMissingUnoAnnoucement(PlayerControllerBean roundWinner) {
		PlayerController currentPlayer = roundWinner.getPlayer();
		this.consoleView.displayJokerEmphasisUsingPlaceholders("Player [", currentPlayer.getAlias(), "] played his last card !");
		this.consoleView.displayErrorMessageUsingPlaceholders("But since he forgot to announce ","UNO",", we gladly offer him ", "2 more cards");
		this.gameModel.giveCardPenaltyTo(roundWinner,2);
	}

	/**
	 * M�thode priv�e permettant de g�rer l'annonce (abusive ou non) de UNO
	 * @param roundWinner PlayerControllerBean Objet englobant le joueur en cours, permettant d'avoir acc�s � des m�thodes de haut niveau facilement
	 */
	private void handleUnoAnnoucement(PlayerControllerBean roundWinner) {
		this.consoleView.displayJokerEmphasisUsingPlaceholders("Player [", roundWinner.getAlias(), "] announced UNO");
		if(!roundWinner.deservesTheRightToAnnounceUno()) {
			this.consoleView.displayErrorMessage("Since that annoucement is irrelevant (he's not playing his 2nd last card)", "He receives a 2 cards penalty. Pretty dumb, right?");
			this.gameModel.giveCardPenaltyTo(roundWinner,2);
		}
	}

	/**
	 * M�thode priv�e permettant de g�rer l'�v�nement de victoire d'un des joueurs
	 * @param winner PlayerControllerBean Objet englobant le joueur victorieux, permettant d'avoir acc�s � des m�thodes de haut niveau facilement
	 */
	private void handleWinEvent(PlayerController winner) {
		this.consoleView.displayJokerEmphasisUsingPlaceholders("Player [",winner.getAlias(),"] won the game !");
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

	private void resetEverything() {
		this.gameModel.resetEverything();
	}
}
