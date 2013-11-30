package main.java.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import com.google.common.base.CharMatcher;

import main.java.console.model.ConsoleException;
import main.java.gameContext.controller.GameController;
import main.java.gameContext.controller.TurnController;

public class Server {
	private static Server server;
	private static TurnController turnController;
	private static PrintStream outputStream;
	private static BufferedReader inputReader;

	private Server() {
		Server.turnController = new TurnController();
		new GameController();
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
		giveAllPlayers7Cards();
	}

	private static int askForPlayerNumber() {
		String answer = "";
		outputStream.println("How many players? [expected : 2-7]");
		answer = readAnotherLine();
		return validateNumberUsingRegexOrAskNewOne(answer);
	}
	
	private static int validateNumberUsingRegexOrAskNewOne(String answer) {
		int playerNumber = getNumberFromString(answer);
		while(playerNumber < 2 || playerNumber > 7) {
			outputStream.println("[ERROR] There can only 2 to 7 players");
			outputStream.println("Please enter a valid player number");
			playerNumber = getNumberFromString(readAnotherLine());
		}
		return playerNumber;
	}

	private static int getNumberFromString(String answer) {
		String answerToTest = answer;
		while(CharMatcher.DIGIT.countIn(answerToTest) < 1) {
			outputStream.println("[ERROR] Only numbers are allowed");
			outputStream.println("Please enter a VALID player number, any invalid characters will be removed");
			answerToTest = readAnotherLine();
		}
		String digitsFromAnswer = CharMatcher.DIGIT.retainFrom(answerToTest);
		return Integer.parseInt(digitsFromAnswer);
	}
	
	private static void askForPlayerNames(int playerNumber) {
		String playerNameFromInput = "";
		outputStream.println("you successfully chose [" + playerNumber + "] players, please enter their name (one at a time -multi word aliases allowed)");
		Collection<String> playerNames = new ArrayList<String>();
		for(int i=0; i<playerNumber; i++) {
			playerNameFromInput = readAnotherLine();
			while(playerNames.contains(playerNameFromInput)) {
				outputStream.println("[ERROR] There already is a player with this name");
				outputStream.println("Please pick another one");
				playerNameFromInput = readAnotherLine();
			}
			playerNames.add(playerNameFromInput);
			outputStream.println("Player [" + playerNameFromInput + "] created");
		}
		outputStream.println("Player creation complete, " + playerNames + " will compete against each other");
		Server.turnController.createPlayersFrom(playerNames);
	}

	private static String readAnotherLine() {
		try {
			return inputReader.readLine();
		} catch (IOException e) {
			throw new ConsoleException("[ERROR] Something went wrong while reading line from console input");
		}
	}

	private static void giveAllPlayers7Cards() {
		//TODO: [URGENT] finish that shit
		/*
		for(PlayerController player : Server.players) {
			outputStream.print(player.getAlias() + ", ");
			Collection<Carte> cardsDrawn = Server.gameController.drawCards(7);
			player.pickUpCards(cardsDrawn);
		}*/
	}
}
