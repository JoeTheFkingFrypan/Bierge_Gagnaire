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
import main.java.gameContext.model.GameFlags;
import main.java.player.controller.PlayerController;

/**
 * Classe correspondant au serveur de jeu comprenant tous les élements necessaire au fonctionnement d'une partie
 */
public class Server {
	private static Server server;
	private static TurnController turnController;
	private static GameController gameController;
	private static InputReader inputReader;
	private static GameFlags gameFlag;
	private static View consoleView;

	/* ========================================= CONSTRUCTOR ========================================= */
	
	private Server() {
		Server.consoleView = new ConsoleView();
		Server.turnController = new TurnController(consoleView);
		Server.gameController = new GameController(consoleView);
		Server.inputReader = new InputReader(consoleView);
		initializeGameSettings();
		applyEffectFromFirstCardIfITHasOne();
	}

	private void applyEffectFromFirstCardIfITHasOne() {
		GameFlags effectFromFirstCard = Server.gameController.applyEffectFromFirstCard();
		applyFirstEffect(effectFromFirstCard);
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
		Server.gameFlag = GameFlags.NORMAL;
		Server.consoleView.displayTitle("SETTINGS");
		int playerNumber = askForPlayerNumber();
		askForPlayerNames(playerNumber);
		giveAllPlayers7Cards(playerNumber);
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
	
	/**
	 * Méthode permettant de boucler eternellement sur les joueurs
	 */
	//TODO: clean that crap "cycleForever"
	public void cycleForever() {
		Server.consoleView.displayTitle("GAME STARTING");
		while(true) {
			GameModelBean requiredGameInfo = Server.gameController.getRequiredGameInfo();
			PlayerController currentPlayer = Server.turnController.findNextPlayer();
			playOneTurn(requiredGameInfo, currentPlayer);
			applyEffects(currentPlayer);
		}
	}
	
	/**
	 * Méthode privée permettant de permettre à un joueur de jouer son tour (en lui permettant de piocher si besoin, ou de passer son tour s'il n'a pas de cartes jouables)
	 * @param gameModelbean Carte dernièrement jouée (celle sur le talon, donc carte de référence)
	 * @param currentPlayer Joueur actuel
	 * @param currentColor Couleur globale définie lorsqu'une carte JOKER est jouée (et qu'une couleur est choisie par un joueur)
	 */
	private void playOneTurn(GameModelBean gameModelbean, PlayerController currentPlayer) {
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
	}
	
	/**
	 * Méthode privée permettant d'appliquer les effets des cartes spéciales sur la partie
	 * @param currentPlayer 
	 */
	private void applyEffects(PlayerController currentPlayer) {
		if(Server.gameFlag.equals(GameFlags.REVERSE)) {
			Server.turnController.reverseCurrentOrder();
		} else if(Server.gameFlag.equals(GameFlags.SKIP)) {
			Server.turnController.skipNextPlayer();
		} else if(Server.gameFlag.equals(GameFlags.PLUS_TWO)) {
			Collection<Card> cards = Server.gameController.drawCards(2);
			Server.turnController.giveCardPenaltyToNextPlayer(cards);
		} else if(Server.gameFlag.equals(GameFlags.COLOR_PICK)) {
			Color chosenColor = currentPlayer.hasToChooseColor(Server.inputReader);
			Server.gameController.setGlobalColor(chosenColor);
		} else if(Server.gameFlag.equals(GameFlags.PLUS_FOUR)) {
			Collection<Card> cards = Server.gameController.drawCards(4);
			Server.turnController.giveCardPenaltyToNextPlayer(cards);
			Color chosenColor = currentPlayer.hasToChooseColor(Server.inputReader);
			Server.gameController.setGlobalColor(chosenColor);
		} 
		Server.gameFlag = GameFlags.NORMAL;
	}
	
	private void applyFirstEffect(GameFlags effectFromFirstCard) {
		if(effectFromFirstCard.equals(GameFlags.REVERSE)) {
			Server.turnController.reverseCurrentOrder();
			Server.turnController.resetPlayerIndex();
		} else if(effectFromFirstCard.equals(GameFlags.SKIP)) {
			Server.turnController.skipNextPlayer();
		} else if(effectFromFirstCard.equals(GameFlags.PLUS_TWO)) {
			Server.turnController.findNextPlayer();
			Collection<Card> cards = Server.gameController.drawCards(2);
			Server.turnController.giveCardPenaltyToNextPlayer(cards);
		} else if(effectFromFirstCard.equals(GameFlags.COLOR_PICK)) {
			PlayerController currentPlayer = Server.turnController.findNextPlayer();
			Color chosenColor = currentPlayer.hasToChooseColor(Server.inputReader);
			Server.gameController.setGlobalColor(chosenColor);
			Server.turnController.resetPlayerIndex();
		} else if(effectFromFirstCard.equals(GameFlags.PLUS_FOUR)) {
			GameFlags gameFlag = Server.gameController.applyEffectFromAnotherFirstCard();
			applyFirstEffect(gameFlag);
		} 
		Server.gameFlag = GameFlags.NORMAL;
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
