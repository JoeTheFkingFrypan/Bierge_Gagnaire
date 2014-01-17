package utt.fr.rglb.main.java.game.controller;

import java.io.BufferedReader;

import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.game.model.GameModelConsoleOriented;
import utt.fr.rglb.main.java.player.controller.PlayerControllerConsoleOriented;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.model.PlayerTeam;
import utt.fr.rglb.main.java.view.AbstractView;
import utt.fr.rglb.main.java.view.console.ConsoleView;

/**
 * Classe permettant de gérer l'ensemble de la partie </br>
 * Version console
 */
public class GameControllerConsoleOriented extends AbstractGameController {
	private static final long serialVersionUID = 1L;
	protected GameModelConsoleOriented gameModel;
	protected ConsoleView view;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur de GameControllerConsoleOriented
	 * @param view Vue permettant l'affichage d'informations
	 * @param inputStream Flux d'entrée
	 */
	public GameControllerConsoleOriented(AbstractView view, BufferedReader inputStream) {
		this.view = (ConsoleView)view;
		this.gameModel = new GameModelConsoleOriented(view,inputStream);
	}

	/* ========================================= INITIALIZING ========================================= */

	/**
	 * Méthode permettant d'initialiser les paramètres de jeu
	 */
	protected void initializeGameSettings() {
		this.view.displayTitle("SETTINGS");
		this.gameModel.initializeGameSettings();
	}

	/**
	 * Méthode permettant de tout ré-initialiser lors du demarrage d'une nouvelle partie
	 */
	protected void resetEverything() {
		this.gameModel.resetEverything();
	}

	/* ========================================= GAME LOGIC ========================================= */

	@Override
	public void startAnotherGame() {
		initializeGameSettings();
		PlayerControllerBean winningPlayer = cycleUntilSomeoneWins();
		handleWinEvent(winningPlayer);
	}

	/**
	 * Méthode privée permettant de lancer un nombre infini de "round" jusqu'à ce qu'un joueur ait plus de 500 points
	 * @return PlayerControllerBean Objet englobant un joueur, permettant d'avoir accès à des méthodes de haut niveau facilement
	 */
	protected PlayerControllerBean cycleUntilSomeoneWins() {
		PlayerControllerBean gameWinner = new PlayerControllerBean();
		while(gameWinner.hasNotWonTheGame()) {
			gameWinner = playOneRound();
			this.gameModel.computeScores(gameWinner);
		}
		return gameWinner;
	}

	@Override
	protected PlayerControllerBean playOneRound() {
		startNewRound();
		PlayerControllerBean currentPlayer = new PlayerControllerBean();
		while(currentPlayer.stillHasCards()) {
			currentPlayer = this.gameModel.playOneTurn();
			if(currentPlayer.hasAnnouncedUno()) {
				handleUnoAnnoucement(currentPlayer);
			} else if(currentPlayer.hasNoCardAndForgotToAnnounceUno()) {
				handleMissingUnoAnnoucement(currentPlayer);
			}
		}
		return currentPlayer;
	}

	@Override
	protected void startNewRound() {
		this.view.displayTitle("NEW ROUND STARTING");
		this.gameModel.initializeCardsAndHands();
		this.gameModel.drawFirstCardAndApplyItsEffect();
	}

	/* ========================================= UNO HANDLING ========================================= */

	@Override
	protected void handleMissingUnoAnnoucement(PlayerControllerBean roundWinner) {
		Preconditions.checkNotNull(roundWinner,"[ERROR] Impossible to handle UNO annoucement : provided player is null");
		PlayerControllerConsoleOriented currentPlayer = (PlayerControllerConsoleOriented) roundWinner.getPlayer();
		this.view.displayJokerEmphasisUsingPlaceholders("Player [", currentPlayer.getAlias(), "] played his last card !");
		this.view.displayErrorMessageUsingPlaceholders("But since he forgot to announce ","UNO",", we gladly offer him ", "2 more cards");
		this.gameModel.giveCardPenaltyTo(roundWinner,2);
	}

	@Override
	protected void handleUnoAnnoucement(PlayerControllerBean roundWinner) {
		Preconditions.checkNotNull(roundWinner,"[ERROR] Impossible to handle UNO annoucement : provided player is null");
		this.view.displayJokerEmphasisUsingPlaceholders("Player [", roundWinner.getAlias(), "] announced UNO");
		if(!roundWinner.deservesTheRightToAnnounceUno()) {
			this.view.displayErrorMessage("Since that annoucement is irrelevant (he's not playing his 2nd last card)", "He receives a 2 cards penalty. Pretty dumb, right?");
			this.gameModel.giveCardPenaltyTo(roundWinner,2);
		}
	}

	/* ========================================= WIN EVENT ========================================= */

	@Override
	protected void handleWinEvent(PlayerControllerBean winningPlayer) {
		Preconditions.checkNotNull(winningPlayer,"[ERROR] Impossible to handle winning player : provided player is null");
		if(this.gameModel.indicatesTeamPlayScoring()) {
			PlayerTeam winningTeam = this.gameModel.findWinningTeam(winningPlayer);
			this.view.displayJokerEmphasisUsingPlaceholders("Player [",winningTeam.toString(),"] won the game !");
		} else {
			this.view.displayJokerEmphasisUsingPlaceholders("Player [",winningPlayer.getAlias(),"] won the game !");
		}
		this.view.displayChoice("Would you like to start another game ?","0:YES ","1:NO");
		int answer = this.gameModel.getValidChoiceAnswer();
		if(answer == 0) {
			this.view.displayOneLineOfGreenText("LET'S ROLL :D");
			resetEverything();
			startAnotherGame();
		} else {
			this.view.displayOneLineOfRedText("Your call. --You'll probably regret that decision");
		}
	}
}
