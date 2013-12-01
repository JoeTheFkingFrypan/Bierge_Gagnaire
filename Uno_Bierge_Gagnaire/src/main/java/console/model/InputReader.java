package main.java.console.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import com.google.common.base.CharMatcher;

import main.java.cards.model.basics.Carte;
import main.java.console.view.View;

public class InputReader {
	private BufferedReader inputReader;
	private View consoleView;
	
	public InputReader(View consoleView) {
		this.inputReader = new BufferedReader(new InputStreamReader(System.in));
		this.consoleView = consoleView;
	}
	
	public int getValidPlayerNumber() {
		consoleView.insertBlankLine();
		consoleView.displayBoldText("How many players? [expected : 2-7]");
		String answer = readAnotherLine();
		int playerNumber = getNumberFromString(answer);
		while(playerNumber < 2 || playerNumber > 7) {
			consoleView.insertBlankLine();
			consoleView.displayErrorText("[ERROR] There can only 2 to 7 players (" + playerNumber + " is not allowed)");
			consoleView.displayErrorText("Please enter a valid player number");
			playerNumber = getNumberFromString(readAnotherLine());
		}
		return playerNumber;
	}
	
	public int getFirstValidIndexFromInput(String alias, Collection<Carte> cardCollection, Carte currentCard) {
		consoleView.insertBlankLine();
		consoleView.displayBoldText("========== Your turn, " + alias + "==========");
		consoleView.displayBoldText("* Your cards are : "); 
		this.consoleView.displayCardCollection(cardCollection);
		consoleView.displayBoldText("* The last card play was : ");
		this.consoleView.displayCard(currentCard);
		consoleView.displayBoldText("Please choose a card to play");
		return getValidIndex(cardCollection.size());
	}
	
	public int getAnotherValidIndexFromInputDueToIncompatibleCard(String alias, Collection<Carte> cardCollection, Carte currentCard) {
		consoleView.insertBlankLine();
		consoleView.displayErrorText("[ERROR] Choosen card is not compatible, please pick another one");
		consoleView.displayBoldText("* Your cards are : "); 
		this.consoleView.displayCardCollection(cardCollection);
		consoleView.displayBoldText("* The last card play was : ");
		this.consoleView.displayCard(currentCard);
		consoleView.displayBoldText("Please choose a card to play");
		return getValidIndex(cardCollection.size());
	}

	private int getValidIndex(int maximumChoosableIndex) {
		String answer = readAnotherLine();
		int choosenIndex = getNumberFromString(answer);
		while(choosenIndex < 0 || choosenIndex >= maximumChoosableIndex) {
			consoleView.insertBlankLine();
			consoleView.displayErrorText("[ERROR] Invalid card number (Expected: number from \"0\" to " + maximumChoosableIndex +")");
			consoleView.displayErrorText("Please enter a VALID card number");
			answer = readAnotherLine();
			choosenIndex = getNumberFromString(answer);
		}
		return choosenIndex;
	}
	
	private int getNumberFromString(String answer) {
		while(CharMatcher.DIGIT.countIn(answer) < 1) {
			consoleView.insertBlankLine();
			consoleView.displayErrorText("[ERROR] Only numbers are allowed");
			consoleView.displayErrorText("Please enter a VALID player number, any invalid characters will be removed");
			answer = readAnotherLine();
		}
		String digitsFromAnswer = CharMatcher.DIGIT.retainFrom(answer);
		return Integer.parseInt(digitsFromAnswer);
	}
	
	public Collection<String> getAllPlayerNames(int playerNumber) {
		consoleView.insertBlankLine();
		consoleView.displaySuccessText("You successfully chose [" + playerNumber + "] players"); 
		consoleView.displayBoldText("Please enter their name, one at a time (multi word aliases allowed)");
		Collection<String> playerNames = new ArrayList<String>();
		for(int i=0; i<playerNumber; i++) {
			addValidNameFromInput(playerNames);
		}
		consoleView.insertBlankLine();
		consoleView.displaySuccessText("Player creation complete, " + playerNames + " will compete against each other");
		return playerNames;
	}
	
	private void addValidNameFromInput(Collection<String> playerNames) {
		String playerNameFromInput = readAnotherLine();
		while(playerNames.contains(playerNameFromInput)) {
			consoleView.insertBlankLine();
			consoleView.displayErrorText("[ERROR] There already is a player with this name");
			consoleView.displayErrorText("Please pick another one");
			playerNameFromInput = readAnotherLine();
		}
		playerNames.add(playerNameFromInput);
		consoleView.insertBlankLine();
		consoleView.displaySuccessText("Player [" + playerNameFromInput + "] created");
	}
	
	private String readAnotherLine() {
		try {
			return this.inputReader.readLine();
		} catch (IOException e) {
			throw new ConsoleException("[ERROR] Something went wrong while reading line from console input");
		}
	}
}
