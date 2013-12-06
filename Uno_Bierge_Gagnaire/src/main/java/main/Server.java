package main.java.main;

import java.util.Collection;

import main.java.cards.model.GameModelBean;
import main.java.cards.model.basics.Card;
import main.java.cards.model.basics.Color;
import main.java.console.model.InputReader;
import main.java.console.view.ConsoleView;
import main.java.console.view.View;
import main.java.cards.controller.GameController;
import main.java.gameContext.controller.TurnController;
import main.java.gameContext.model.GameFlag;
import main.java.player.controller.PlayerController;

/**
 * Classe correspondant au serveur de jeu comprenant tous les élements necessaire au fonctionnement d'une partie
 */
public class Server {
	private static Server server;
	private static TurnController turnController;
	private static GameController gameController;
	private static InputReader inputReader;
	private static GameFlag gameFlag;
	private static View consoleView;

	/* ========================================= CONSTRUCTOR ========================================= */
	
	/**
	 * Constructeur privé de serveur
	 */
	private Server() {
		Server.consoleView = new ConsoleView();
		Server.turnController = new TurnController(consoleView);
		Server.gameController = new GameController(consoleView);
		Server.inputReader = new InputReader(consoleView);
		initializeGameSettings();
	}

	/**
	 * Méthode permettant une unique instance de serveur (singleton)
	 * Crée l'instance si elle n'existe pas, ou la retourne si elle existe déjà
	 * @return L'instance unique de serveur
	 */
	private static Server buildServer() {
		Server.server = new Server();
		return Server.server;
	}

	/**
	 * Méthode perrmetant de récupérer l'unique instance de serveur
	 * @return L'instance unique de serveur
	 */
	public static Server getInstance() {
		if(Server.server == null) {
			return buildServer();
		} else {
			return Server.server;
		}
	}

	/* ========================================= SETTINGS ========================================= */
	
	/**
	 * Méthode privée permettant d'initialiser les paramètres (nombre de joueurs, nom de chacun des joueurs)
	 */
	private static void initializeGameSettings() {
		//TODO: settings in a .ini file
		Server.gameFlag = GameFlag.NORMAL;
		Server.consoleView.displayTitle("SETTINGS");
		int playerNumber = askForPlayerNumber();
		askForPlayerNames(playerNumber);
	}

	/**
	 * Méthode privée permettant de récuperer le nombre de joueur
	 * @return Un entier correspondant au nombre de joueur (sera obligatoirement compris entre 2 et 7)
	 */
	private static int askForPlayerNumber() {
		return Server.inputReader.getValidPlayerNumber();
	}

	/**
	 * Méthode privée permettant de récuper le nom de chaque joueur
	 * @param playerNumber Nombre de joueurs dans la partie
	 */
	private static void askForPlayerNames(int playerNumber) {
		Collection<String> playerNames = Server.inputReader.getAllPlayerNames(playerNumber);
		Server.turnController.createPlayersFrom(playerNames);
	}

	/**
	 * Méthode privée permettant d'initialiser la main de chaque joueur (leur donne tous 7 cartes)
	 * @param playerNumber Nombre de joueurs dans la partie
	 */
	private static void giveAllPlayers7Cards(int playerNumber) {
		for(int i=0; i<playerNumber; i++) {
			Collection<Card> cardsDrawn = Server.gameController.drawCards(7);
			Server.turnController.giveCardsToNextPlayer(cardsDrawn);
		}
	}

	/* ========================================= GAME LOGIC ========================================= */

	//TODO: clean that crap "cycleForever"
	public void cycleUntilSomeoneWins() {
		PlayerController winningPlayer = null;
		boolean nooneWonTheGame = true;
		while(nooneWonTheGame) {
			giveAllPlayers7Cards(Server.turnController.getNumberOfPlayers());
			winningPlayer = cycleUntilOnePlayerWinsRound();
			Server.turnController.computeEndOfTurn(winningPlayer);
			Server.turnController.displayTotalScore();
			Server.gameController.resetCards();
			nooneWonTheGame = Server.turnController.findIfNooneWonTheGame();
		}
		handleWinEvent(winningPlayer);
	}
	
	private void handleWinEvent(PlayerController winner) {
		Server.consoleView.insertBlankLine();
		Server.consoleView.appendBoldJokerText("Player [");
		Server.consoleView.appendBoldText(winner.getAlias());
		Server.consoleView.appendBoldJokerText("] won the game !");
		Server.consoleView.insertBlankLine();
		Server.consoleView.displayJokerText("Would you like to start another game ?");
		Server.consoleView.insertBlankLine();
		Server.consoleView.appendBoldGreenText("0:YES ");
		Server.consoleView.appendBoldRedText("1:NO");
		Server.consoleView.insertBlankLine();
		int answer = Server.inputReader.getValidAnswer();
		if(answer == 0) {
			Server.consoleView.insertBlankLine();
			Server.consoleView.appendBoldGreenText("LET'S ROLL :D");
			Server.consoleView.insertBlankLine();
		} else {
			Server.consoleView.insertBlankLine();
			Server.consoleView.appendBoldRedText("YOU SUCKS :(");
			Server.consoleView.insertBlankLine();
		}
	}
	
	/**
	 * Méthode permettant de boucler eternellement sur les joueurs
	 * @return 
	 */
	private PlayerController cycleUntilOnePlayerWinsRound() {
		Server.consoleView.displayTitle("NEW ROUND STARTING");
		applyEffectFromFirstCardIfITHasOne();
		boolean playerStillHaveCards = true;
		PlayerController winningPlayer = null;
		while(playerStillHaveCards) {
			GameModelBean requiredGameInfo = Server.gameController.getRequiredGameInfo();
			winningPlayer = Server.turnController.findNextPlayer();
			playerStillHaveCards = playOneTurn(requiredGameInfo, winningPlayer);
			applyEffects(winningPlayer);
		}
		return winningPlayer;
	}
	
	/**
	 * Méthode privée permettant de permettre à un joueur de jouer son tour (en lui permettant de piocher si besoin, ou de passer son tour s'il n'a pas de cartes jouables)
	 * @param gameModelbean Carte dernièrement jouée (celle sur le talon, donc carte de référence)
	 * @param currentPlayer Joueur actuel
	 * @param currentColor Couleur globale définie lorsqu'une carte JOKER est jouée (et qu'une couleur est choisie par un joueur)
	 * @return 
	 */
	private boolean playOneTurn(GameModelBean gameModelbean, PlayerController currentPlayer) {
		if(currentPlayer.hasAtLeastOnePlayableCard(gameModelbean)) {
			chooseCardAndPlayIt(gameModelbean, currentPlayer);
		} else {
			Card cardDrawn = Server.gameController.drawOneCard();
			currentPlayer.pickUpOneCard(cardDrawn);
			if(currentPlayer.hasAtLeastOnePlayableCard(gameModelbean)) {
				chooseCardAndPlayIt(gameModelbean, currentPlayer);
			} else {
				currentPlayer.unableToPlayThisTurn(gameModelbean);
			}
		}
		return currentPlayer.stillHasCards();
	}
	
	/* ========================================= EFFECTS ========================================= */
	
	/**
	 * Méthode permettant d'appliquer l'effet de la première carte tirée depuis la pioche
	 */
	private void applyEffectFromFirstCardIfITHasOne() {
		GameFlag effectFromFirstCard = Server.gameController.drawFirstCardAndApplyItsEffect();
		applyFirstEffect(effectFromFirstCard);
	}
	
	/**
	 * Méthode privée permettant d'appliquer l'effet en provenance de la 1ère carte retournée du talon
	 * @param effectFromFirstCard Effet provenant de la 1ère carte (initialisation)
	 */
	private void applyFirstEffect(GameFlag effectFromFirstCard) {
		if(!effectFromFirstCard.equals(GameFlag.NORMAL)) {
			Server.consoleView.displayTitle("UNUSUAL START");
		}
		if(effectFromFirstCard.equals(GameFlag.REVERSE)) {
			Server.turnController.reverseCurrentOrderAndResetPlayerIndex();
		} else if(effectFromFirstCard.equals(GameFlag.SKIP)) {
			Server.turnController.skipNextPlayer();
		} else if(effectFromFirstCard.equals(GameFlag.PLUS_TWO)) {
			Server.turnController.findNextPlayer();
			Collection<Card> cards = Server.gameController.drawCards(2);
			Server.turnController.giveCardPenaltyToNextPlayer(cards);
		} else if(effectFromFirstCard.equals(GameFlag.COLOR_PICK)) {
			PlayerController currentPlayer = Server.turnController.findNextPlayer();
			Color chosenColor = currentPlayer.hasToChooseColor(Server.inputReader);
			Server.gameController.setGlobalColor(chosenColor);
			Server.turnController.resetPlayerIndex();
		} else if(effectFromFirstCard.equals(GameFlag.PLUS_FOUR)) {
			GameFlag gameFlag = Server.gameController.applyEffectFromAnotherFirstCard();
			applyFirstEffect(gameFlag);
		} 
		Server.gameFlag = GameFlag.NORMAL;
	}
	
	/**
	 * Méthode privée permettant d'appliquer les effets des cartes spéciales sur la partie
	 * @param currentPlayer 
	 */
	private void applyEffects(PlayerController currentPlayer) {
		if(Server.gameFlag.equals(GameFlag.REVERSE)) {
			Server.turnController.reverseCurrentOrder();
		} else if(Server.gameFlag.equals(GameFlag.SKIP)) {
			Server.turnController.skipNextPlayer();
		} else if(Server.gameFlag.equals(GameFlag.PLUS_TWO)) {
			Collection<Card> cards = Server.gameController.drawCards(2);
			Server.turnController.giveCardPenaltyToNextPlayer(cards);
		} else if(Server.gameFlag.equals(GameFlag.COLOR_PICK)) {
			Color chosenColor = currentPlayer.hasToChooseColor(Server.inputReader);
			Server.gameController.setGlobalColor(chosenColor);
		} else if(Server.gameFlag.equals(GameFlag.PLUS_FOUR)) {
			Collection<Card> cards = Server.gameController.drawCards(4);
			Server.turnController.giveCardPenaltyToNextPlayer(cards);
			Color chosenColor = currentPlayer.hasToChooseColor(Server.inputReader);
			Server.gameController.setGlobalColor(chosenColor);
		} 
		Server.gameFlag = GameFlag.NORMAL;
	}
	
	/**
	 * Méthode privée permettant à un joueur de choisir une carte depuis sa main
	 * @param gameModelbean Carte dernièrement jouée (celle sur le talon, donc carte de référence)
	 * @param currentPlayer Joueur actuel
	 */
	private void chooseCardAndPlayIt(GameModelBean gameModelbean, PlayerController currentPlayer) {
		Card cardChosen = currentPlayer.startTurn(inputReader,gameModelbean);
		Server.gameFlag = Server.gameController.playCard(cardChosen);
	}
}
