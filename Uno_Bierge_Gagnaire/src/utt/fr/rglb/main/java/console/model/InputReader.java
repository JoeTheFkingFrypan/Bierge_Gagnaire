package utt.fr.rglb.main.java.console.model;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.GameModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.console.view.View;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;

/**
 * Classe permettant de demander � l'utilisateur d'entrer des informations au clavier, et de les valider
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
	 * M�thode permettant de r�cuperer un nombre de joueur valide
	 * @return Un int correspondant au nombre de joueurs (entre 2 et 7)
	 */
	public int getValidPlayerNumber() {
		this.consoleView.displayOneLineOfBoldText("How many players? [expected : 2-7]");
		String answer = readAnotherLine();
		Integer playerNumber = getNumberFromString(answer);
		while(playerNumber < 2 || playerNumber > 7) {
			this.consoleView.displayErrorMessageUsingPlaceholders("[ERROR] There can only ","2 ","to ","7 ","players (",playerNumber.toString()," is not allowed)","Please enter a valid player number");
			playerNumber = getNumberFromString(readAnotherLine());
		}
		return playerNumber;
	}

	/**
	 * M�thode priv�e permettant de recuperer un nombre � partir d'une chaine de caract�res entr�e au clavier
	 * Cette m�thode ne garde que les nombres et retire tous les autres caract�res non d�sir�s.
	 * Par exemple si l'utilisateur entre "sjqd2lkfjq qsdjqjd 3", la m�thode renverra "23"
	 * Tant que l'utilisateur n'entre pas au moins un chiffre, la m�thode boucle, lui intimant de recommencer
	 * @param answer String contenant la chaine entr�e au clavier
	 * @return Le nombre entr� par l'utilisateur
	 */
	public int getNumberFromString(String answer) {
		Preconditions.checkNotNull(answer,"[ERROR] Couldn't read answer : provided one was null");
		while(CharMatcher.DIGIT.countIn(answer) < 1 || answer.length() == 0) {
			this.consoleView.displayErrorMessage("[ERROR] Only numbers are allowed","Please enter a VALID player number, any invalid characters will be removed");
			answer = readAnotherLine();
		}
		String digitsFromAnswer = CharMatcher.DIGIT.retainFrom(answer);
		return Integer.parseInt(digitsFromAnswer);
	}

	/* ========================================= PLAYER ALIAS ========================================= */

	/**
	 * M�thode permettant de r�cuperer le nom de tous les joueurs (avec pseudos tous diff�rents)
	 * @param playerNumber Nombre de joueurs dans la partie
	 * @return Une collection contenant le nom de chaque joueur
	 */
	//TODO: squeeze spaces
	public PlayersToCreate getAllPlayerNames(Integer playerNumber) {
		Preconditions.checkArgument(playerNumber > 0, "[ERROR] Impossible to get all player names : player count is invalid");
		this.consoleView.displayGreenEmphasisUsingPlaceholders("You successfully chose [",playerNumber.toString(),"] players");
		this.consoleView.displayOneLineOfBoldText("Please enter their name, one at a time (multi word aliases allowed)");
		PlayersToCreate playersAwaitingCreation = new PlayersToCreate();
		for(int i=0; i<playerNumber; i++) {
			boolean isTheLastOneToCreate = (i == (playerNumber-1));
			addValidNameFromInput(playersAwaitingCreation,isTheLastOneToCreate);
		}
		this.consoleView.displayJokerEmphasisUsingPlaceholders("Player creation complete, ",playersAwaitingCreation.toString()," will compete against each other");
		return playersAwaitingCreation;
	}

	/**
	 * M�thode priv�e permettant de r�cup�rer un pseudo � partir du clavier en s'assurant qu'il n'existe pas d�j�
	 * @param isTheLastOneToCreate 
	 * @param playerNames Une collection contenant le nom de chaque joueur
	 * @return 
	 */
	private void addValidNameFromInput(PlayersToCreate playersAwaitingCreation, boolean isTheLastOneToCreate) {
		Preconditions.checkNotNull(playersAwaitingCreation, "[ERROR] Impossible to add another name : provided collection is null");
		String playerNameFromInput = getValidAlias();
		while(playersAwaitingCreation.contains(playerNameFromInput)) {
			this.consoleView.displayErrorMessage("[ERROR] There already is a player with this name", "Please pick another one");
			playerNameFromInput = getValidAlias();
		}
		this.consoleView.displayChoice("Is it a real player or an AI?","0:Real Player ", "1:AI ");
		int answer = this.getValidAnswerFromDualChoice();
		if(answer == 0) {
			playersAwaitingCreation.addHumanPlayer(playerNameFromInput);
		} else {
			this.consoleView.displayChoice("What kind of behavior do you want him to have?","0:Naive ", "1:Demophobia ", "2:Joker O.C.D. ");
			answer = this.getValidAnswerFromTripleChoice();
			playersAwaitingCreation.addIAPlayerProvidingStrategyIndex(playerNameFromInput,answer);
			
		}
		this.consoleView.displayGreenEmphasisUsingPlaceholders("Player [",playerNameFromInput,"] created");
		if(!isTheLastOneToCreate) {
			this.consoleView.displayOneLineOfBoldText("Please enter another player name");
		}
	}
	
	/**
	 * M�thode priv�e de r�cup�rer un pseudo valide (non vide) � partir du clavier
	 * @return String correspondant au pseudo choisi
	 */
	private String getValidAlias() {
		String playerNameFromInput = readAnotherLine();
		String nameWithoutSpace = CharMatcher.WHITESPACE.removeFrom(playerNameFromInput);
		while(playerNameFromInput.length() == 0 || nameWithoutSpace.length() == 0) {
			this.consoleView.displayErrorMessage("[ERROR] An alias must have at least one letter/number","Please pick one");
			playerNameFromInput = readAnotherLine();
		}
		return playerNameFromInput;
	}

	/* ========================================= CARD INDEX FROM HAND ========================================= */

	/**
	 * M�thode permettant d'obtenir une r�ponse valide (index de la carte choisie et �venutuellement l'annonce de UNO)
	 * @param alias Pseudo du joueur
	 * @param cardCollection Cartes du joueur
	 * @param gameModelbean R�f�rences de jeu (derni�re carte du talon et couleur globale)
	 * @return String contenant la r�ponse
	 */
	public String getValidAnswer(String alias, Collection<Card> cardCollection, GameModelBean gameModelbean) {
		Preconditions.checkNotNull(alias, "[ERROR] Impossible to get card index from player's cards : provided alias is null");
		Preconditions.checkNotNull(cardCollection, "[ERROR] Impossible to get card index from player's cards : provided collection is null");
		Preconditions.checkNotNull(gameModelbean, "[ERROR] Impossible to get card index from player's cards : provided gameModelBean is null");
		displayCardsInfo(cardCollection, gameModelbean);
		return getValidAnswerDisplayingInfo(cardCollection,gameModelbean);
	}
	
	/**
	 * M�thode permettant d'afficher et message d'erreur et de demander une autre r�ponse valide (index de la carte choisie et �venutuellement l'annonce de UNO)
	 * @param alias Pseudo du joueur
	 * @param cardCollection Cartes du joueur
	 * @param gameModelbean R�f�rences de jeu (derni�re carte du talon et couleur globale)
	 * @return String contenant la r�ponse
	 */
	public String getAnotherValidIndexFromInputDueToIncompatibleCard(String alias, Collection<Card> cardCollection, GameModelBean gameModelbean) {
		this.consoleView.displayErrorMessage("[ERROR] Choosen card is not compatible","Please pick another one");
        return getValidAnswer(alias,cardCollection,gameModelbean);
	}

	/**
	 * M�thode priv�e permettant de r�cuperer une r�ponse valide en affichant les d�tails dans la vue
	 * @param cardCollection Cartes du joueur
	 * @param gameModelbean R�f�rences de jeu (derni�re carte du talon et couleur globale)
	 * @return String contenant la r�ponse
	 */
	private String getValidAnswerDisplayingInfo(Collection<Card> cardCollection, GameModelBean gameModelbean) {
		Preconditions.checkNotNull(cardCollection, "[ERROR] Impossible to get card index from player's cards : provided collection is null");
		Preconditions.checkNotNull(gameModelbean, "[ERROR] Impossible to get card index from player's cards : provided gameModelBean is null");
		Integer boundLimit = cardCollection.size() - 1;
		String answer = readAnotherLine();
		int choosenIndex = getNumberFromStringDisplayingCardInfo(answer,cardCollection,gameModelbean);
		while(choosenIndex < 0 || choosenIndex > boundLimit) {
			this.consoleView.displayErrorMessageUsingPlaceholders("[ERROR] Invalid card number (Expected: number from ", "0 ", "to ", boundLimit.toString(), ")", "Please enter a VALID card number");
			displayCardsInfo(cardCollection,gameModelbean);
			answer = readAnotherLine();
			choosenIndex = getNumberFromString(answer);
		}
		return answer;
	}

	/**
	 * M�thode priv�e permettant de r�cup�rer un nombre depuis une chaine de caract�res rentr�e au clavier
	 * @param answer String contenant la chaine de caract�res � analyser
	 * @param cardCollection Collection de carte du joueur
	 * @param gameModelbean R�f�rences de jeu (derni�re carte du talon et couleur globale)
	 * @return int correspondant � l'index choisi
	 */
	private int getNumberFromStringDisplayingCardInfo(String answer, Collection<Card> cardCollection, GameModelBean gameModelbean) {
		while(CharMatcher.DIGIT.countIn(answer) < 1 || answer.length() == 0) {
			this.consoleView.displayErrorMessage("[ERROR] Only numbers are allowed --[NOTE] UNO word might also be used here", "Please enter a VALID number, any other invalid characters will be removed");
			displayCardsInfo(cardCollection, gameModelbean);
			answer = readAnotherLine();
		}
		String digitsFromAnswer = CharMatcher.DIGIT.retainFrom(answer);
		return Integer.parseInt(digitsFromAnswer);
	}

	/**
	 * M�thode priv�e permettant d'afficher les infos sur les cartes dans la vue (cartes en main, derni�re carte jou�e, et �ventuellement la couleur globale ayant �t� choisie)
	 * @param cardCollection Cartes du joueur
	 * @param gameModelbean R�f�rences de jeu (derni�re carte du talon et couleur globale)
	 */
	private void displayCardsInfo(Collection<Card> cardCollection, GameModelBean gameModelbean) {
		Preconditions.checkNotNull(cardCollection, "[ERROR] Impossible to get card index from player's cards : provided collection is null");
		Preconditions.checkNotNull(gameModelbean, "[ERROR] Impossible to get card index from player's cards : provided gameModelBean is null");
		this.consoleView.displayCardCollection("* Your cards are : ", cardCollection);
		this.consoleView.displayCard("* The last card played was : ", gameModelbean.getLastCardPlayed());
		gameModelbean.appendGlobalColorIfItIsSet();
		this.consoleView.displayOneLineOfBoldText("Please choose a card to play, remember to say UNO if you play your 2nd last card");
	}

	/* ========================================= COLOR PICKING ========================================= */

	/**
	 * M�thode permettant de r�cup�rer une couleur valide
	 * @return La couleur choisie par l'utilisateur
	 */
	public Color getValidColor() {
		this.consoleView.displayAvailableColors();
		String answer = readAnotherLine();
		int choosenIndex = getNumberFromString(answer);
		while(choosenIndex < 0 || choosenIndex >= 4) {
			this.consoleView.displayErrorMessageUsingPlaceholders("[ERROR] Invalid number (Expected: number from ", "0 ", "to ", "3 ", ")", "Please enter a VALID card number");
			answer = readAnotherLine();
			choosenIndex = getNumberFromString(answer);
		}
		return findColorUsingItsNumber(choosenIndex);
	}

	/**
	 * M�thode priv�e permettant de r�cup�rer la couleur associ�e au choix de l'utilisateur
	 * @param colorNumber int correspondant � l'index choisi
	 * @return La color associ�e � l'index
	 */
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
				this.consoleView.displayErrorMessage("[ERROR] Something went terribly wrong with indexes", "Please pick another one");
				return getValidColor();
		}
	}

	/* ========================================= UTILS ========================================= */

	/**
	 * M�thode priv�e permettant de lire une chaine de caract�re � partir du clavier
	 * @return String correspondant � ce qui a �t� tap� au clavier
	 */
	private String readAnotherLine() {
		try {
			return this.inputReader.readLine();
		} catch (IOException e) {
			throw new ConsoleException("[ERROR] Something went wrong while reading line from console input");
		}
	}

	/**
	 * M�thode permettant d'obtenir un index valide (soit 0 soit 1) pour le choix donn� (choix proposant 2 r�ponses)
	 * @return int correspondant � l'index choisi
	 */
	public int getValidAnswerFromDualChoice() {
		int index = getNumberFromString(readAnotherLine());
		while(index != 0 && index != 1) {
			this.consoleView.displayErrorMessageUsingPlaceholders("[ERROR] Only ", "0 ", "and ", "1 ", "are correct answers // " + index + " is not allowed", "Please enter a VALID number, any invalid characters will be removed");
			index = getNumberFromString(readAnotherLine());
		}
		return index;
	}
	
	/**
	 * M�thode permettant d'obtenir un index valide (soit 0, 1 ou 2) pour le choix donn� (choix proposant 3 r�ponses)
	 * @return int correspondant � l'index choisi
	 */
	private int getValidAnswerFromTripleChoice() {
		int index = getNumberFromString(readAnotherLine());
		while(index != 0 && index != 1 && index != 2) {
			this.consoleView.displayErrorMessageUsingPlaceholders("[ERROR] Only ", "0 ", ", ", "1 ", "and ", "3", "are correct answers // " + index + " is not allowed", "Please enter a VALID number, any invalid characters will be removed");
			index = getNumberFromString(readAnotherLine());
		}
		return index;
	}

	/**
	 * M�thode permettant de v�rifier si le joueur a annonc� UNO lors de la selection de sa carte
	 * @param answer R�ponse de l'utilisateur � analyser
	 * @return TRUE si la chaine contient les lettres "u","n","o" sans caract�res parasites (les espaces sont accept�s), FALSE sinon 
	 */
	public boolean findIfUnoHasBeenAnnounced(String answer) {
		Preconditions.checkNotNull(answer,"[ERROR] Cannot check if UNO has been announced : provided answer is null");
		Preconditions.checkArgument(answer.length() > 0, "[ERROR] Cannot check if UNO has been announced : provided answer is empty");
		String answerWithoutNumbers = CharMatcher.DIGIT.removeFrom(answer);
		String pattern = "^( )*(U|u)( )*(N|n)( )*(O|o)( )*$";
		return answerWithoutNumbers.matches(pattern);
	}

	/**
	 * M�thode permettant de demander � l'utilisateur s'il souhaite charger le fichier de configuration (et r�cup�rer sa r�ponse)
	 * @return int correspondant � l'index de sa r�ponse <code>0</code> pour <code>OUI</code>, <code>1</code> pour <code>NON</code> 
	 */
	public boolean askForConfigurationFileUsage() {
		this.consoleView.displayChoice("Would you like to load game settings from configuration file?","0:YES ", "1:NO ");
		int choice = this.getValidAnswerFromDualChoice();
		if(choice == 0) {
			return true;
		} else {
			return false;
		}
	}
}
