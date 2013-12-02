package main.java.console.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Carte;
import main.java.console.view.View;

/**
 * Classe permettant de demander à l'utilisateur d'entrer des informations au clavier, et de les valider
 */
public class InputReader {
	private BufferedReader inputReader;
	private View consoleView;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	public InputReader(View consoleView) {
		Preconditions.checkNotNull(consoleView,"[ERROR] Impossible to create input reader : provided view is null");
		this.inputReader = new BufferedReader(new InputStreamReader(System.in));
		this.consoleView = consoleView;
	}
	
	/* ========================================= PLAYER NUMBER ========================================= */
	
	/**
	 * Méthode permettant de récuperer un nombre de joueur valide
	 * @return Un int correspondant au nombre de joueurs (entre 2 et 7)
	 */
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
	
	/**
	 * Méthode privée permettant de recuperer un nombre à partir d'une chaine de caractères entrée au clavier
	 * Cette méthode ne garde que les nombres et retire tous les autres caractères non désirés.
	 * Par exemple si l'utilisateur entre "sjqd2lkfjq qsdjqjd 3", la méthode renverra "23"
	 * Tant que l'utilisateur n'entre pas au moins un chiffre, la méthode boucle, lui intimant de recommencer
	 * @param answer String contenant la chaine entrée au clavier
	 * @return 
	 */
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
	
	/* ========================================= PLAYER ALIAS ========================================= */
	
	/**
	 * Méthode permettant de récuperer le nom de tous les joueurs (avec pseudos tous différents)
	 * @param playerNumber Nombre de joueurs dans la partie
	 * @return Une collection contenant le nom de chaque joueur
	 */
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
	
	/**
	 * Méthode privée permettant de récupérer un pseudo à partir du clavier en s'assurant qu'il n'existe pas déjà
	 * @param playerNames Une collection contenant le nom de chaque joueur
	 */
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

	/* ========================================= CARD INDEX FROM HAND ========================================= */
	
	/**
	 * Méthode permettant de récuperer le choix de carte de l'utilisateur
	 * @param alias Pseudo du joueur
	 * @param cardCollection Cartes en sa possession
	 * @param currentCard Carte de référence (celle sur le talon)
	 * @return Un int correpondant au numéro de la carte choisie
	 */
	public int getFirstValidIndexFromInput(String alias, Collection<Carte> cardCollection, Carte currentCard) {
		consoleView.appendBoldText("* Your cards are : "); 
		this.consoleView.displayCardCollection(cardCollection);
		consoleView.appendBoldText("* The last card play was : ");
		this.consoleView.displayCard(currentCard);
		consoleView.displayBoldText("Please choose a card to play");
		return getValidIndex(cardCollection.size());
	}
	
	/**
	 * Méthode permettant de récuperer le choix de carte de l'utilisateur (dans le cas où il a préalablement sélectionné une carte non compatible)
	 * @param alias Pseudo du joueur
	 * @param cardCollection Cartes en sa possession
	 * @param currentCard Carte de référence (celle sur le talon)
	 * @return Un int correpondant au numéro de la carte choisie
	 */
	public int getAnotherValidIndexFromInputDueToIncompatibleCard(String alias, Collection<Carte> cardCollection, Carte currentCard) {
		consoleView.insertBlankLine();
		consoleView.displayErrorText("[ERROR] Choosen card is not compatible, please pick another one");
		consoleView.appendBoldText("* Your cards are : "); 
		this.consoleView.displayCardCollection(cardCollection);
		consoleView.appendBoldText("* The last card play was : ");
		this.consoleView.displayCard(currentCard);
		consoleView.displayBoldText("Please choose a card to play");
		return getValidIndex(cardCollection.size());
	}
	
	/**
	 * Méthode privée permettant de récuperer une index valide par rapport à ceux possibles
	 * @param maximumChoosableIndex Index maximal accessible
	 * @return Un int correspondant à l'index choisi
	 */
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
	
	/* ========================================= UTILS ========================================= */
	
	/**
	 * Méthode privée permettant de lire une chaine de caractère à partir du clavier
	 * @return String correspondant à ce qui a été tapé au clavier
	 */
	private String readAnotherLine() {
		try {
			return this.inputReader.readLine();
		} catch (IOException e) {
			throw new ConsoleException("[ERROR] Something went wrong while reading line from console input");
		}
	}
}
