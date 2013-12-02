package main.java.main;

import java.util.Collection;

import main.java.cards.model.basics.Carte;
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
	private static View consoleView;

	/* ========================================= CONSTRUCTOR ========================================= */
	
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
			Collection<Carte> cardsDrawn = Server.gameController.drawCards(7);
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
			Carte currentCard = Server.gameController.showLastCardPlayed();
			PlayerController currentPlayer = Server.turnController.findNextPlayer();
			playOneTurn(currentCard, currentPlayer);
		}
	}

	/**
	 * Méthode privée permettant de permettre à un joueur de jouer son tour (en lui permettant de piocher si besoin, ou de passer son tour s'il n'a pas de cartes jouables)
	 * @param currentCard Carte dernièrement jouée (celle sur le talon, donc carte de référence)
	 * @param currentPlayer Joueur actuel
	 */
	private void playOneTurn(Carte currentCard, PlayerController currentPlayer) {
		if(currentPlayer.hasAtLeastOnePlayableCard(currentCard)) {
			chooseCardAndPlayIt(currentCard, currentPlayer);
		} else {
			Carte cardDrawn = Server.gameController.drawOneCard();
			currentPlayer.pickUpOneCard(cardDrawn);
			if(currentPlayer.hasAtLeastOnePlayableCard(currentCard)) {
				System.out.println("WIN");
				chooseCardAndPlayIt(currentCard, currentPlayer);
			} else {
				System.out.println("NOPE");
				currentPlayer.unableToPlayThisTurn(currentCard);
			}
		}
	}

	/**
	 * Méthode privée permettant à un joueur de choisir une carte depuis sa main
	 * @param currentCard Carte dernièrement jouée (celle sur le talon, donc carte de référence)
	 * @param currentPlayer Joueur actuel
	 */
	private void chooseCardAndPlayIt(Carte currentCard, PlayerController currentPlayer) {
		Carte cardChosen = currentPlayer.startTurn(inputReader,currentCard);
		GameFlags effect = Server.gameController.playCard(cardChosen);
		applyEffects(effect);
	}

	/**
	 * Méthode privée permettant d'appliquer les effets des cartes spéciales sur la partie
	 * @param effect Effet provenant de la carte spéciale dernièrement jouée
	 */
	//TODO: finish applyEffects
	private void applyEffects(GameFlags effect) {
		if(effect.equals(GameFlags.INVERSION)) {
			Server.turnController.reverseCurrentOrder();
		} else if(effect.equals(GameFlags.INVERSION)) {

		}
	}
}
