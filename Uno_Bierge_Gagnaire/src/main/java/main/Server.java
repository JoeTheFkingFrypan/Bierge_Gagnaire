package main.java.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import main.java.cards.model.basics.Carte;
import main.java.console.model.ConsoleException;
import main.java.gameContext.controller.GameController;
import main.java.player.controller.PlayerController;
import main.java.player.model.PlayerModel;

public class Server {
	private static Server server;
	private static GameController gameController;
	private static List<PlayerController> players;
	private static PrintStream outputStream;
	private static BufferedReader inputReader;

	private Server() {
		Server.players = new ArrayList<PlayerController>();
		Server.gameController = new GameController();
		Server.outputStream = System.out;
		Server.inputReader = new BufferedReader(new InputStreamReader(System.in));
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
		int playerNumber = askForPlayerNumber();
		askForPlayerNames(playerNumber);
		scramblePlayersAndGiveThemCards();
	}

	private static int askForPlayerNumber() {
		String answer = "";
		outputStream.println("How many players? [expected : 2-7]");
		answer = readAnotherLine();
		int playerNumber = Integer.parseInt(answer);
		while(playerNumber < 2 || playerNumber > 7) {
			outputStream.println("[ERROR] There can only 2 to 7 players");
			outputStream.println("Please enter a current player number");
			playerNumber = Integer.parseInt(readAnotherLine());
		}
		return playerNumber;
	}
	
	private static void askForPlayerNames(int playerNumber) {
		//TODO: handle same alias for multiple players
		String answer = "";
		outputStream.println("you successfully chose [" + playerNumber + "] players, please enter their name (one at a time -multi word aliases allowed)");
		for(int i=0; i<playerNumber; i++) {
			answer = readAnotherLine();
			PlayerModel anotherPlayer = new PlayerModel(answer);
			Server.players.add(new PlayerController(anotherPlayer));
			outputStream.println("Player [" + answer + "] created");
		}
		outputStream.println("Player creation complete, these " + playerNumber + " will compete against each other");
	}
	
	private static String readAnotherLine() {
		try {
			return inputReader.readLine();
		} catch (IOException e) {
			throw new ConsoleException("[ERROR] Something went wrong while reading line from console input");
		}
	}
	
	private static void scramblePlayersAndGiveThemCards() {
		outputStream.println("=== SCRAMBLING PLAYERS to DETERMINE FIRST TO PLAY ===");
		Collections.shuffle(Server.players);
		outputStream.print("Current order is : ");
		for(PlayerController player : Server.players) {
			outputStream.print(player.getAlias() + ", ");
			Collection<Carte> cardsDrawn = Server.gameController.drawCards(7);
			player.pickUpCards(cardsDrawn);
		}
	}
	
	private static void allowNextPlayerToPlay() {
		if(Server.gameController.indicatesNormalTurnOrder()) {
			
		} else {
			
		}
	}
}
