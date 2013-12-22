package utt.fr.rglb.main.java.console.model;

import com.google.common.base.CharMatcher;
import com.google.common.base.Preconditions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.console.view.View;
import utt.fr.rglb.main.java.game.model.GameMode;
import utt.fr.rglb.main.java.game.model.GameRule;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;

/**
 * Classe permettant de demander � l'utilisateur d'entrer des informations au clavier, et de les valider
 * </br>Injection de d�pendance de sorte � pouvoir recevoir en entr�e un flux lisible. 
 * </br>De cette mani�re il est possible d'appeler les m�mes m�thodes en utilisant (ou non) le clavier
 */
public class InputReader implements Serializable {
	private static final long serialVersionUID = 1L;
	private View consoleView;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur d'Input Reader
	 * @param consoleView Vue permettant d'afficher des informations dans l'interface
	 */
	public InputReader(View consoleView) {
		Preconditions.checkNotNull(consoleView,"[ERROR] Impossible to create input reader : provided view is null");
		this.consoleView = consoleView;
	}

	/* ========================================= PLAYER NUMBER ========================================= */
	
	/**
	 * M�thode permettant de r�cuperer un nombre de joueur valide dans le cas o� la lecture du fichier de configuration a �chou�
	 * @param inputStream Flux d'entr�e
	 * @param errorMessage Message d'erreur en provenance de la lecture du fichier de config
	 * @return Un int correspondant au nombre de joueurs (entre 2 et 7)
	 */
	public int getValidPlayerNumberDueToInvalidConfigFile(BufferedReader inputStream, String errorMessage) {
		this.consoleView.displayOneLineOfRedText(errorMessage);
		return this.getValidPlayerNumber(inputStream);
	}
	
	/**
	 * M�thode permettant de r�cuperer un nombre de joueur valide
	 * @param inputStream Flux d'entr�e
	 * @return Un int correspondant au nombre de joueurs (entre 2 et 7)
	 */
	public int getValidPlayerNumber(BufferedReader inputStream) {
		this.consoleView.displayOneLineOfBoldText("How many players? [expected : 2-7]");
		String answer = readAnotherLine(inputStream);
		Integer playerNumber = getNumberFromString(answer,inputStream);
		while(playerNumber < 2 || playerNumber > 7) {
			this.consoleView.displayErrorMessageUsingPlaceholders("[ERROR] There can only ","2 ","to ","7 ","players (",playerNumber.toString()," is not allowed)","Please enter a valid player number");
			answer = readAnotherLine(inputStream);
			playerNumber = getNumberFromString(answer,inputStream);
		}
		return playerNumber;
	}
	
	/**
	 * M�thode priv�e permettant de recuperer un nombre � partir d'une chaine de caract�res entr�e au clavier
	 * Cette m�thode ne garde que les nombres et retire tous les autres caract�res non d�sir�s.
	 * Par exemple si l'utilisateur entre "sjqd2lkfjq qsdjqjd 3", la m�thode renverra "23"
	 * Tant que l'utilisateur n'entre pas au moins un chiffre, la m�thode boucle, lui intimant de recommencer
	 * @param answer String contenant la chaine entr�e au clavier
	 * @param inputStream Flux d'entr�e
	 * @return Le nombre entr� par l'utilisateur
	 */
	public int getNumberFromString(String answer, BufferedReader inputStream) {
		Preconditions.checkNotNull(answer,"[ERROR] Couldn't read answer : provided one was null");
		while(hasAnInvalidNumberFormat(answer)) {
			this.consoleView.displayErrorMessage("[ERROR] Only numbers are allowed : " + answer + " is forbidden","Please enter a VALID player number, any invalid characters will be removed");
			answer = readAnotherLine(inputStream);
		}
		String digitsFromAnswer = CharMatcher.inRange('0', '9').or(CharMatcher.is('-')).retainFrom(answer);
		return Integer.parseInt(digitsFromAnswer);
	}

	/**
	 * M�thode priv�e permettant de d�terminer si la r�ponse donn�e ne r�pond pas aux crit�res attendus
	 * </br>Par exemple une r�ponse non valide ne contiendra pas de num�ro, ou sera vide, ou sera un nombre inf�rieur strictement � 0
	 * @param answer String correspondant � la r�ponse � analyser
	 * @return <code>TRUE</code> si la r�ponse est invalide, <code>FALSE</code> sinon
	 */
	private boolean hasAnInvalidNumberFormat(String answer) {
		String digitsFromAnswer = CharMatcher.inRange('0', '9').or(CharMatcher.is('-')).retainFrom(answer);
		if(CharMatcher.DIGIT.countIn(digitsFromAnswer) < 0 || digitsFromAnswer.length() == 0) {
			return true;
		} else {
			int numberFromString = Integer.parseInt(digitsFromAnswer);
			if(numberFromString >= 0) {
				return false;
			}
		}
		return true;
	}
	
	/* ========================================= PLAYER ALIAS ========================================= */

	/**
	 * M�thode permettant de r�cuperer le nom de tous les joueurs (avec pseudos tous diff�rents)
	 * @param playerNumber Nombre de joueurs dans la partie
	 * @param inputStream Flux d'entr�e
	 * @return Une collection contenant le nom de chaque joueur
	 */
	//TODO: squeeze spaces ?
	public PlayersToCreate getAllPlayerNames(Integer playerNumber,BufferedReader inputStream) {
		Preconditions.checkNotNull(playerNumber, "[ERROR] Impossible to get all player names : player count is null");
		Preconditions.checkArgument(playerNumber > 0, "[ERROR] Impossible to get all player names : player count is invalid");
		this.consoleView.displayGreenEmphasisUsingPlaceholders("You successfully chose [",playerNumber.toString(),"] players");
		this.consoleView.displayOneLineOfBoldText("Please enter their name, one at a time (multi word aliases allowed)");
		PlayersToCreate playersAwaitingCreation = new PlayersToCreate();
		for(int i=0; i<playerNumber; i++) {
			boolean isTheLastOneToCreate = (i == (playerNumber-1));
			addValidNameFromInput(playersAwaitingCreation,isTheLastOneToCreate,inputStream);
		}
		this.consoleView.displayJokerEmphasisUsingPlaceholders("Player creation complete, ",playersAwaitingCreation.toString()," will compete against each other");
		return playersAwaitingCreation;
	}

	/**
	 * M�thode priv�e permettant de r�cup�rer un pseudo � partir du clavier en s'assurant qu'il n'existe pas d�j�
	 * @param playersAwaitingCreation Objet encapsulant les informations de tous les joueurs devant �tre cr��s
	 * @param isTheLastOneToCreate Bool�en indiquant s'il s'agit du dernier joueur � cr�er (permettant ainsi de ne pas afficher d'entrer un nouveau nom, si tous les noms ont �t� choisis) 
	 * @param inputStream Flux d'entr�e
	 */
	private void addValidNameFromInput(PlayersToCreate playersAwaitingCreation, boolean isTheLastOneToCreate,BufferedReader inputStream) {
		Preconditions.checkNotNull(playersAwaitingCreation, "[ERROR] Impossible to add another name : provided collection is null");
		String playerNameFromInput = getValidAlias(inputStream);
		while(playersAwaitingCreation.contains(playerNameFromInput)) {
			this.consoleView.displayErrorMessage("[ERROR] There already is a player with this name", "Please pick another one");
			playerNameFromInput = getValidAlias(inputStream);
		}
		this.consoleView.displayChoice("Is it a real player or an AI?","0:Real Player ", "1:AI ");
		int answer = this.getValidAnswerFromDualChoice(inputStream);
		if(answer == 0) {
			playersAwaitingCreation.addHumanPlayer(playerNameFromInput);
		} else {
			this.consoleView.displayChoice("What kind of behavior do you want him to have?","0:Naive ", "1:Demophobia ", "2:Joker O.C.D. ");
			answer = this.getValidAnswerFromTripleChoice(inputStream);
			playersAwaitingCreation.addIAPlayerProvidingStrategyIndex(playerNameFromInput,answer);

		}
		this.consoleView.displayGreenEmphasisUsingPlaceholders("Player [",playerNameFromInput,"] created");
		if(!isTheLastOneToCreate) {
			this.consoleView.displayOneLineOfBoldText("Please enter another player name");
		}
	}

	/**
	 * M�thode priv�e de r�cup�rer un pseudo valide (non vide) � partir du clavier
	 * @param inputStream Flux d'entr�e
	 * @return String correspondant au pseudo choisi
	 */
	private String getValidAlias(BufferedReader inputStream) {
		String playerNameFromInput = readAnotherLine(inputStream);
		String nameWithoutSpace = CharMatcher.WHITESPACE.removeFrom(playerNameFromInput);
		while(playerNameFromInput.length() == 0 || nameWithoutSpace.length() == 0) {
			this.consoleView.displayErrorMessage("[ERROR] An alias must have at least one letter/number","Please pick one");
			playerNameFromInput = readAnotherLine(inputStream);
		}
		return playerNameFromInput;
	}

	/* ========================================= CARD INDEX FROM HAND ========================================= */

	/**
	 * M�thode permettant d'obtenir une r�ponse valide (index de la carte choisie et �venutuellement l'annonce de UNO)
	 * @param alias Pseudo du joueur
	 * @param cardCollection Cartes du joueur
	 * @param gameModelbean R�f�rences de jeu (derni�re carte du talon et couleur globale)
	 * @param inputStream Flux d'entr�e
	 * @return String contenant la r�ponse
	 */
	public String getValidAnswer(String alias, Collection<Card> cardCollection, CardsModelBean gameModelbean,BufferedReader inputStream) {
		Preconditions.checkNotNull(alias, "[ERROR] Impossible to get card index from player's cards : provided alias is null");
		Preconditions.checkNotNull(cardCollection, "[ERROR] Impossible to get card index from player's cards : provided collection is null");
		Preconditions.checkNotNull(gameModelbean, "[ERROR] Impossible to get card index from player's cards : provided gameModelBean is null");
		displayCardsInfo(cardCollection, gameModelbean);
		return getValidAnswerDisplayingInfo(cardCollection,gameModelbean,inputStream);
	}

	/**
	 * M�thode permettant d'afficher et message d'erreur et de demander une autre r�ponse valide (index de la carte choisie et �venutuellement l'annonce de UNO)
	 * @param alias Pseudo du joueur
	 * @param cardCollection Cartes du joueur
	 * @param gameModelbean R�f�rences de jeu (derni�re carte du talon et couleur globale)
	 * @param inputStream Flux d'entr�e
	 * @return String contenant la r�ponse
	 */
	public String getAnotherValidAnswerFromInputDueToIncompatibleCard(String alias, Collection<Card> cardCollection, CardsModelBean gameModelbean, BufferedReader inputStream) {
		Preconditions.checkNotNull(alias,"[ERROR] Cannot get another valid index because provided alias is null");
		Preconditions.checkNotNull(cardCollection,"[ERROR] Cannot get another valid index because provided card collection is null");
		Preconditions.checkNotNull(gameModelbean,"[ERROR] Cannot get another valid index because provided gameModelBean is null");
		this.consoleView.displayErrorMessage("[ERROR] Choosen card is not compatible","Please pick another one");
		return getValidAnswer(alias,cardCollection,gameModelbean,inputStream);
	}

	/**
	 * M�thode priv�e permettant de r�cuperer une r�ponse valide en affichant les d�tails dans la vue
	 * @param cardCollection Cartes du joueur
	 * @param gameModelbean R�f�rences de jeu (derni�re carte du talon et couleur globale)
	 * @param inputStream Flux d'entr�e
	 * @return String contenant la r�ponse
	 */
	private String getValidAnswerDisplayingInfo(Collection<Card> cardCollection, CardsModelBean gameModelbean, BufferedReader inputStream) {
		Preconditions.checkNotNull(cardCollection, "[ERROR] Impossible to get card index from player's cards : provided collection is null");
		Preconditions.checkNotNull(gameModelbean, "[ERROR] Impossible to get card index from player's cards : provided gameModelBean is null");
		Integer boundLimit = cardCollection.size() - 1;
		String answer = readAnotherLine(inputStream);
		int choosenIndex = getNumberFromStringDisplayingCardInfo(answer,cardCollection,gameModelbean,inputStream);
		while(choosenIndex < 0 || choosenIndex > boundLimit) {
			this.consoleView.displayErrorMessageUsingPlaceholders("[ERROR] Invalid card number (Expected: number from ", "0 ", "to ", boundLimit.toString(), ")", "Please enter a VALID card number");
			displayCardsInfo(cardCollection,gameModelbean);
			answer = readAnotherLine(inputStream);
			choosenIndex = getNumberFromString(answer,inputStream);
		}
		return answer;
	}

	/**
	 * M�thode priv�e permettant de r�cup�rer un nombre depuis une chaine de caract�res rentr�e au clavier
	 * @param answer String contenant la chaine de caract�res � analyser
	 * @param cardCollection Collection de carte du joueur
	 * @param gameModelbean R�f�rences de jeu (derni�re carte du talon et couleur globale)
	 * @param inputStream Flux d'entr�e
	 * @return int correspondant � l'index choisi
	 */
	private int getNumberFromStringDisplayingCardInfo(String answer, Collection<Card> cardCollection, CardsModelBean gameModelbean,BufferedReader inputStream) {
		Preconditions.checkNotNull(answer,"[ERROR] Cannot get number from string because provided string is null");
		Preconditions.checkNotNull(cardCollection,"[ERROR] Cannot get number from string because provided card collection is null");
		Preconditions.checkNotNull(gameModelbean,"[ERROR] Cannot get number from string because provided gameModelBean is null");
		while(hasAnInvalidNumberFormat(answer)) {
			this.consoleView.displayErrorMessage("[ERROR] Only numbers are allowed --[NOTE] UNO word might also be used here", "Please enter a VALID number, any other invalid characters will be removed");
			displayCardsInfo(cardCollection, gameModelbean);
			answer = readAnotherLine(inputStream);
		}
		String digitsFromAnswer = CharMatcher.inRange('0', '9').or(CharMatcher.is('-')).retainFrom(answer);
		return Integer.parseInt(digitsFromAnswer);
	}

	/**
	 * M�thode priv�e permettant d'afficher les infos sur les cartes dans la vue (cartes en main, derni�re carte jou�e, et �ventuellement la couleur globale ayant �t� choisie)
	 * @param cardCollection Cartes du joueur
	 * @param gameModelbean R�f�rences de jeu (derni�re carte du talon et couleur globale)
	 */
	private void displayCardsInfo(Collection<Card> cardCollection, CardsModelBean gameModelbean) {
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
	 * @param inputStream Flux d'entr�e
	 * @return La couleur choisie par l'utilisateur
	 */
	public Color getValidColor(BufferedReader inputStream) {
		this.consoleView.displayAvailableColors();
		String answer = readAnotherLine(inputStream);
		int choosenIndex = getNumberFromString(answer,inputStream);
		while(choosenIndex < 0 || choosenIndex >= 4) {
			this.consoleView.displayErrorMessageUsingPlaceholders("[ERROR] Invalid number (Expected: number from ", "0 ", "to ", "3 ", ")", "Please enter a VALID card number");
			answer = readAnotherLine(inputStream);
			choosenIndex = getNumberFromString(answer,inputStream);
		}
		return findColorUsingItsNumber(choosenIndex,inputStream);
	}

	/**
	 * M�thode priv�e permettant de r�cup�rer la couleur associ�e au choix de l'utilisateur
	 * @param colorNumber int correspondant � l'index choisi
	 * @param inputStream Flux d'entr�e
	 * @return La color associ�e � l'index
	 */
	private Color findColorUsingItsNumber(int colorNumber,BufferedReader inputStream) {
		Preconditions.checkNotNull(colorNumber, "[ERROR] find color based on its index : provided index is null");
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
			return getValidColor(inputStream);
		}
	}

	/* ========================================= UTILS ========================================= */

	/**
	 * M�thode priv�e permettant de lire une chaine de caract�re � partir du clavier
	 * Injection de d�pendance de sorte � pouvoir effectuer des tests unitaires tr�s simplement
	 * @param inputStream Flux d'entr�e
	 * @return String correspondant � ce qui a �t� tap� au clavier
	 */
	private String readAnotherLine(BufferedReader inputStream) {
		try {
			return inputStream.readLine();
		} catch (IOException e) {
			throw new ConsoleException("[ERROR] Something went wrong while reading line from console input",e);
		}
	}

	/**
	 * M�thode permettant d'obtenir un index valide (soit 0 soit 1) pour le choix donn� (choix proposant 2 r�ponses)
	 * @param inputStream Flux d'entr�e
	 * @return int correspondant � l'index choisi
	 */
	public int getValidAnswerFromDualChoice(BufferedReader inputStream) {
		String answer = readAnotherLine(inputStream);
		int index = getNumberFromString(answer,inputStream);
		while(index != 0 && index != 1) {
			this.consoleView.displayErrorMessageUsingPlaceholders("[ERROR] Only ", "0 ", "and ", "1 ", "are correct answers // " + index + " is not allowed", "Please enter a VALID number, any invalid characters will be removed");
			answer = readAnotherLine(inputStream);
			index = getNumberFromString(answer,inputStream);
		}
		return index;
	}

	/**
	 * M�thode permettant d'obtenir un index valide (soit 0, 1 ou 2) pour le choix donn� (choix proposant 3 r�ponses)
	 * @param inputStream Flux d'entr�e
	 * @return int correspondant � l'index choisi
	 */
	public int getValidAnswerFromTripleChoice(BufferedReader inputStream) {
		String answer = readAnotherLine(inputStream);
		int index = getNumberFromString(answer,inputStream);
		while(index != 0 && index != 1 && index != 2) {
			this.consoleView.displayErrorMessageUsingPlaceholders("[ERROR] Only ", "0 ", ", ", "1 ", "and ", "3", "are correct answers // " + index + " is not allowed", "Please enter a VALID number, any invalid characters will be removed");
			answer = readAnotherLine(inputStream);
			index = getNumberFromString(answer,inputStream);
		}
		return index;
	}

	/**
	 * M�thode permettant de v�rifier si le joueur a annonc� UNO lors de la selection de sa carte
	 * @param answer R�ponse de l'utilisateur � analyser
	 * @return <code>TRUE</code> si la chaine contient les lettres "u","n","o" sans caract�res parasites (les espaces sont accept�s), <code>FALSE</code> sinon 
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
	 * @param inputStream Flux d'entr�e
	 * @return int correspondant � l'index de sa r�ponse <code>0</code> pour <code>OUI</code>, <code>1</code> pour <code>NON</code> 
	 */
	public boolean askForConfigurationFileUsage(BufferedReader inputStream) {
		this.consoleView.displayChoice("Would you like to load game settings from configuration file?","0:YES ", "1:NO ");
		int choice = this.getValidAnswerFromDualChoice(inputStream);
		if(choice == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * M�thode permettant de demander � l'utilisateur quelle variante de jeu utiliser (choix d�pendant du nombre de joueurs pr�c�demment donn�)
	 * @param size Nombre de joueurs
	 * @param inputStream Flux d'entr�e
	 * @return Objet englobant le mode de jeu choisi et l'�tat actuel de la partie
	 */
	public GameRule askForGameMode(Integer size,BufferedReader inputStream) {
		//Preconditions.checkNotNull(size, "[ERROR] Impossible find game mode based on its index : provided index is null");
		//Preconditions.checkArgument(size < 0, "[ERROR] Impossible find game mode based on its index : provided index is negative");
		if(size.equals(2)) {
			this.consoleView.displayChoice("Would you like to set game mode to TWO PLAYERS ONLY --Or play with classic rules?","0:YES ", "1:NO ");
			int choice = this.getValidAnswerFromDualChoice(inputStream);
			if(choice == 0) {
				return new GameRule(GameMode.TWO_PLAYERS);
			} else {
				return new GameRule(GameMode.NORMAL);
			}
		} else if((size%2) == 0){
			this.consoleView.displayChoice("What game mode would you like to choose?","0:CLASSIC ", "1:LAST-MAN STANDING ", "2:TEAM-PLAY ");
			int choice = this.getValidAnswerFromTripleChoice(inputStream);
			if(choice == 0) {
				return new GameRule(GameMode.NORMAL);
			} else if(choice == 1) {
				this.consoleView.displayOneLineOfYellowText("[FEATURE ", "NOT YET IMPLEMENTED ", "-> SWITCHING TO ", "CLASSIC ", "GAME MODE]");
				return new GameRule(GameMode.UNO_CHALLENGE);
			} else {
				return new GameRule(GameMode.TEAM_PLAY);
			}
		} else {
			this.consoleView.displayChoice("What game mode would you like to choose?","0:CLASSIC ", "1:LAST-MAN STANDING ");
			int choice = this.getValidAnswerFromDualChoice(inputStream);
			if(choice == 0) {
				return new GameRule(GameMode.NORMAL);
			} else {
				this.consoleView.displayOneLineOfYellowText("[FEATURE ", "NOT YET IMPLEMENTED ", "-> SWITCHING TO ", "CLASSIC ", "GAME MODE]");
				return new GameRule(GameMode.UNO_CHALLENGE);
			}
		}
	}

	/**
	 * M�thode permettant de savoir si le joueur tient r�element � bluffer ou non
	 * @param inputStream Flux d'entr�e
	 * @return <code>TRUE</code> si l'utilisateur souhaite jouer une nouvelle carte (s'il ne tente pas le bluff), <code>FALSE</code> sinon
	 */
	public boolean askIfHeWantsToPlayAnotherCard(BufferedReader inputStream) {
		this.consoleView.displayOneLineOfJokerText("You have other playable cards, are you sure that you want to play that one?");
		this.consoleView.displayChoice("--Note that you might receive penalty for bluffing", "0:Yes, I'm sure ", "1: No, nevermind ");
		int choice = this.getValidAnswerFromDualChoice(inputStream);
		if(choice == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * M�thode permettant de savoir si le joueur tient � accuser le pr�c�dent de bluffer sur un +4
	 * @param inputStream Flux d'entr�e
	 * @return <code>TRUE</code> si l'utilisateur souhaite accuser le joueur pr�c�dent, <code>FALSE</code> sinon
	 */
	public boolean askIfHeWantsToCheckIfItsLegit(BufferedReader inputStream) {
		this.consoleView.displayChoice("Would you like to accuse him of bluffing?", "0:Yes ", "1: Nope ");
		int choice = this.getValidAnswerFromDualChoice(inputStream);
		if(choice == 0) {
			return true;
		} else {
			return false;
		}
	}
}
