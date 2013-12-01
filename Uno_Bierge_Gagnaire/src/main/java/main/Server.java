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

public class Server {
	private static Server server;
	private static TurnController turnController;
	private static GameController gameController;
	private static InputReader inputReader;
	private static View consoleView;
	
	private Server() {
		Server.consoleView = new ConsoleView();
		Server.turnController = new TurnController();
		Server.gameController = new GameController();
		Server.inputReader = new InputReader(consoleView);
		initializeGameSettings();
	}

	private static Server buildServer() {
		Server.server = new Server();
		return Server.server;
	}

	public static Server getInstance() {
		if(Server.server == null) {
			return buildServer();
		} else {
			return Server.server;
		}
	}

	private static void initializeGameSettings() {
		//TODO: settings in a .ini file
		Server.consoleView.displayTitle("SETTINGS");
		int playerNumber = askForPlayerNumber();
		askForPlayerNames(playerNumber);
		giveAllPlayers7Cards(playerNumber);
	}

	private static int askForPlayerNumber() {
		return Server.inputReader.getValidPlayerNumber();
	}


	private static void askForPlayerNames(int playerNumber) {
		Collection<String> playerNames = Server.inputReader.getAllPlayerNames(playerNumber);
		Server.turnController.createPlayersFrom(playerNames);
	}

	private static void giveAllPlayers7Cards(int playerNumber) {
		for(int i=0; i<playerNumber; i++) {
			Collection<Carte> cardsDrawn = Server.gameController.drawCards(7);
			Server.turnController.giveCardsToNextPlayer(cardsDrawn);
		}
	}

	public void cycleForever() {
		Server.consoleView.displayTitle("GAME STARTING");
		while(true) {
			Carte currentCard = Server.gameController.showLastCardPlayed();
			PlayerController currentPlayer = Server.turnController.findNextPlayer();
			Carte cardChosen = currentPlayer.startTurn(inputReader,currentCard);
			GameFlags effect = Server.gameController.playCard(cardChosen);
			applyEffects(effect);
		}
	}
	
	private void applyEffects(GameFlags effect) {
		if(effect.equals(GameFlags.INVERSION)) {
			Server.turnController.reverseCurrentOrder();
		} else if(effect.equals(GameFlags.INVERSION)) {
			
		}
	}
}
