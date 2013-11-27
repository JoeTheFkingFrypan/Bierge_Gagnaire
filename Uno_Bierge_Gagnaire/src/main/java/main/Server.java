package main.java.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import main.java.console.model.ConsoleException;
import main.java.player.model.Joueur;

public class Server {
	private PrintStream outputStream;
	private BufferedReader inputReader;
	private List<Joueur> players;

	public Server() {
		this.outputStream = System.out; //TODO: move it to VIEW
		this.inputReader = new BufferedReader(new InputStreamReader(System.in));
		this.players = new ArrayList<Joueur>();
		initializeGameSettings();
	}

	//TODO: settings in a .ini file
	private void initializeGameSettings() {
		int playerNumber = askForPlayerNumber();
		askForPlayerNames(playerNumber);
		scramblePlayers();
	}

	private int askForPlayerNumber() {
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
	
	private void askForPlayerNames(int playerNumber) {
		//TODO: handle same alias for multiple players
		String answer = "";
		outputStream.println("you successfully chose [" + playerNumber + "] players, please enter their name (one at a time -multi word aliases allowed)");
		for(int i=0; i<playerNumber; i++) {
			answer = readAnotherLine();
			this.players.add(new Joueur(answer));
			outputStream.println("Player [" + answer + "] created");
		}
		outputStream.println("Player creation complete, these " + playerNumber + " will compete against each other");
	}
	
	private String readAnotherLine() {
		try {
			return inputReader.readLine();
		} catch (IOException e) {
			throw new ConsoleException("[ERROR] Something went wrong while reading line from console input");
		}
	}
	
	private void scramblePlayers() {
		outputStream.println("=== SCRAMBLING PLAYERS to DETERMINE FIRST TO PLAY ===");
		Collections.shuffle(this.players);
		outputStream.print("Current order is : ");
		for(Joueur j : this.players) {
			outputStream.print(j.getAlias() + ", ");
		}
	}
}
