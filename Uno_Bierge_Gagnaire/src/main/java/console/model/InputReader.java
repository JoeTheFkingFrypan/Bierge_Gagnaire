package main.java.console.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;

import main.java.cards.model.GameModelBean;
import main.java.cards.model.basics.Card;
import main.java.cards.model.basics.Color;
import main.java.console.view.View;

/**
 * Classe permettant de demander à l'utilisateur d'entrer des informations au clavier, et de les valider
 */
public class InputReader {
	private BufferedReader inputReader;
	private View consoleView;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur d'Input Reader
	 * @param consoleView Vue permettant d'afficher des informations dans l'interface
	 */
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
		this.consoleView.insertBlankLine();
		this.consoleView.displayBoldText("How many players? [expected : 2-7]");
		String answer = readAnotherLine();
		int playerNumber = getNumberFromString(answer);
		while(playerNumber < 2 || playerNumber > 7) {
			this.consoleView.insertBlankLine();
			this.consoleView.displayErrorText("[ERROR] There can only 2 to 7 players (" + playerNumber + " is not allowed)");
			this.consoleView.displayErrorText("Please enter a valid player number");
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
	public int getNumberFromString(String answer) {
		Preconditions.checkNotNull(answer,"[ERROR] Couldn't read answer : provided one was null");
		while(CharMatcher.DIGIT.countIn(answer) < 1) {
			this.consoleView.insertBlankLine();
			this.consoleView.displayErrorText("[ERROR] Only numbers are allowed");
			this.consoleView.displayErrorText("Please enter a VALID player number, any invalid characters will be removed");
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
		Preconditions.checkArgument(playerNumber > 0, "[ERROR] Impossible to get all player names : player count is invalid");
		this.consoleView.insertBlankLine();
		this.consoleView.displaySuccessText("You successfully chose [" + playerNumber + "] players"); 
		this.consoleView.displayBoldText("Please enter their name, one at a time (multi word aliases allowed)");
		Collection<String> playerNames = new ArrayList<String>();
		for(int i=0; i<playerNumber; i++) {
			addValidNameFromInput(playerNames);
		}
		this.consoleView.insertBlankLine();
		this.consoleView.appendBoldJokerText("Player creation complete, " + playerNames + " will compete against each other");
		this.consoleView.insertBlankLine();
		return playerNames;
	}

	/**
	 * Méthode privée permettant de récupérer un pseudo à partir du clavier en s'assurant qu'il n'existe pas déjà
	 * @param playerNames Une collection contenant le nom de chaque joueur
	 */
	private void addValidNameFromInput(Collection<String> playerNames) {
		Preconditions.checkNotNull(playerNames, "[ERROR] Impossible to add another name : provided collection is null");
		String playerNameFromInput = getValidAlias();
		while(playerNames.contains(playerNameFromInput)) {
			this.consoleView.insertBlankLine();
			this.consoleView.displayErrorText("[ERROR] There already is a player with this name");
			this.consoleView.displayErrorText("Please pick another one");
			playerNameFromInput = getValidAlias();
		}
		playerNames.add(playerNameFromInput);
		this.consoleView.insertBlankLine();
		this.consoleView.displaySuccessText("Player [" + playerNameFromInput + "] created");
	}

	/**
	 * Méthode privée de récupérer un pseudo valide (non vide) à partir du clavier
	 * @return String correspondant au pseudo choisi
	 */
	private String getValidAlias() {
		String playerNameFromInput = readAnotherLine();
		while(playerNameFromInput.length() == 0) {
			this.consoleView.insertBlankLine();
			this.consoleView.displayErrorText("[ERROR] An alias must have at least one letter/number");
			this.consoleView.displayErrorText("Please pick one");
			playerNameFromInput = readAnotherLine();
		}
		return playerNameFromInput;
	}

	/* ========================================= CARD INDEX FROM HAND ========================================= */

	public String getValidAnswer(String alias, Collection<Card> cardCollection, GameModelBean gameModelbean) {
		Preconditions.checkNotNull(alias, "[ERROR] Impossible to get card index from player's cards : provided alias is null");
		Preconditions.checkNotNull(cardCollection, "[ERROR] Impossible to get card index from player's cards : provided collection is null");
		Preconditions.checkNotNull(gameModelbean, "[ERROR] Impossible to get card index from player's cards : provided gameModelBean is null");
		displayCardsInfo(cardCollection, gameModelbean);
		return getValidAnswerDisplayingInfo(cardCollection,gameModelbean);
	}
	
	public String getAnotherValidIndexFromInputDueToIncompatibleCard(String alias, Collection<Card> cardCollection, GameModelBean gameModelbean) {
        this.consoleView.insertBlankLine();
        this.consoleView.displayErrorText("[ERROR] Choosen card is not compatible, please pick another one");
        return getValidAnswer(alias,cardCollection,gameModelbean);
	}

	private String getValidAnswerDisplayingInfo(Collection<Card> cardCollection, GameModelBean gameModelbean) {
		Preconditions.checkNotNull(cardCollection, "[ERROR] Impossible to get card index from player's cards : provided collection is null");
		Preconditions.checkNotNull(gameModelbean, "[ERROR] Impossible to get card index from player's cards : provided gameModelBean is null");
		int boundLimit = cardCollection.size();
		String answer = readAnotherLine();
		int choosenIndex = getNumberFromStringDisplayingCardInfo(answer,cardCollection,gameModelbean);
		while(choosenIndex < 0 || choosenIndex >= boundLimit) {
			this.consoleView.insertBlankLine();
			this.consoleView.displayErrorText("[ERROR] Invalid card number (Expected: number from 0 to " + (boundLimit-1) + ")");
			this.consoleView.displayErrorText("Please enter a VALID card number");
			displayCardsInfo(cardCollection,gameModelbean);
			answer = readAnotherLine();
			choosenIndex = getNumberFromString(answer);
		}
		return answer;
	}

	private int getNumberFromStringDisplayingCardInfo(String answer, Collection<Card> cardCollection, GameModelBean gameModelbean) {
		while(CharMatcher.DIGIT.countIn(answer) < 1) {
			this.consoleView.insertBlankLine();
			this.consoleView.displayErrorText("[ERROR] Only numbers are allowed --[NOTE] UNO word might also be used here");
			this.consoleView.displayErrorText("Please enter a VALID player number, any other invalid characters will be removed");
			displayCardsInfo(cardCollection, gameModelbean);
			answer = readAnotherLine();
		}
		String digitsFromAnswer = CharMatcher.DIGIT.retainFrom(answer);
		return Integer.parseInt(digitsFromAnswer);
	}

	private void displayCardsInfo(Collection<Card> cardCollection, GameModelBean gameModelbean) {
		Preconditions.checkNotNull(cardCollection, "[ERROR] Impossible to get card index from player's cards : provided collection is null");
		Preconditions.checkNotNull(gameModelbean, "[ERROR] Impossible to get card index from player's cards : provided gameModelBean is null");
		this.consoleView.appendBoldText("* Your cards are : "); 
		this.consoleView.displayCardCollection(cardCollection);
		this.consoleView.appendBoldText("* The last card play was : ");
		this.consoleView.displayCard(gameModelbean.getLastCardPlayed());
		gameModelbean.appendGlobalColorIfItIsSet();
		this.consoleView.displayBoldText("Please choose a card to play, remember to say UNO if you play your 2nd last card");
	}

	/* ========================================= COLOR PICKING ========================================= */

	public Color getValidColor() {
		this.consoleView.appendBoldText("Available Colors are : ");
		this.consoleView.appendBoldRedText("0:Red ");
		this.consoleView.appendBoldBlueText("1:Blue ");
		this.consoleView.appendBoldGreenText("2:Green ");
		this.consoleView.appendBoldYellowText("3:Yellow ");
		this.consoleView.insertBlankLine();
		this.consoleView.displayBoldText("Please pick one using number");

		String answer = readAnotherLine();
		int choosenIndex = getNumberFromString(answer);
		while(choosenIndex < 0 || choosenIndex >= 4) {
			this.consoleView.insertBlankLine();
			this.consoleView.displayErrorText("[ERROR] Invalid number (Expected: number from 0 to 3");
			this.consoleView.displayErrorText("Please enter a VALID card number");
			answer = readAnotherLine();
			choosenIndex = getNumberFromString(answer);
		}
		return findColorUsingItsNumber(choosenIndex);
	}

	private Color findColorUsingItsNumber(int colorNumber) {
		switch(colorNumber) {
			case 0:
				return Color.RED;
			case 1:
				return Color.BLUE;
			case 2:
				return Color.GREEN;
			case 3:
				return Color.YELLOW;
			default:
				this.consoleView.displayErrorText("[ERROR] Something went terribly wrong with indexes, please pick another one");
				return getValidColor();
		}
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

	public int getValidAnswer() {
		int index = getNumberFromString(readAnotherLine());
		while(index != 0 && index != 1) {
			this.consoleView.insertBlankLine();
			this.consoleView.displayErrorText("[ERROR] Only [0] and [1] are correct answers // " + index + " is not allowed");
			this.consoleView.displayErrorText("Please enter a VALID number, any invalid characters will be removed");
			index = getNumberFromString(readAnotherLine());
		}
		return index;
	}

	public boolean findIfUnoHasBeenAnnounced(String answer) {
		Preconditions.checkNotNull(answer,"[ERROR] Cannot check if UNO has been announced : provided answer is null");
		Preconditions.checkArgument(answer.length() > 0, "[ERROR] Cannot check if UNO has been announced : provided answer is empty");
		String answerWithoutNumbers = CharMatcher.DIGIT.removeFrom(answer);
		String pattern = "^( )*(U|u)( )*(N|n)( )*(O|o)( )*$";
		return answerWithoutNumbers.matches(pattern);
	}
}
