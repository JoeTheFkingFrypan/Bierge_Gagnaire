package main.java.player.controller;

import java.util.Collection;

import com.google.common.base.Preconditions;

import main.java.cards.model.GameModelBean;
import main.java.cards.model.basics.Card;
import main.java.cards.model.basics.Color;
import main.java.console.model.InputReader;
import main.java.console.view.View;
import main.java.player.model.PlayerModel;

/**
 * Classe dont le rôle est de gérer tout ce qui touche à un joueur
 */
public class PlayerController {
	private PlayerModel player;
	private View consoleView;

	/* ========================================= CONSTRUCTOR ========================================= */
	
	public PlayerController(String name, View consoleView) {
		Preconditions.checkNotNull(name,"[ERROR] name cannot be null");
		Preconditions.checkNotNull(consoleView,"[ERROR] view cannot be null");
		this.player = new PlayerModel(name);
		this.consoleView = consoleView;
	}

	/* ========================================= CARD PICKUP ========================================= */
	
	/**
	 * Méthode permettant d'ajouter une collection de cartes à la main du joueur
	 * @param cards Collection de cartes devant être ajoutées
	 */
	public void pickUpCards(Collection<Card> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Card collection picked up cannot be null");
		Preconditions.checkArgument(cards.size()>0, "[ERROR] Card collection picked cannot be empty");
		this.player.pickUpCards(cards);
	}

	/**
	 * Méthode permettant d'ajouter une unique carte à la main du joueur
	 * @param card Carte devant être ajoutée
	 */
	public void pickUpOneCard(Card card) {
		Preconditions.checkNotNull(card,"[ERROR] Card picked up cannot be null");
		this.consoleView.appendBoldJokerText("You had no playable cards in you hand, you've drawn : ");
		this.consoleView.displayCard(card);
		this.consoleView.insertBlankLine();
		this.player.pickUpOneCard(card);
	}

	/**
	 * Méthiode permettant de forcer un joueur à piocher (avec affichage d'un message)
	 * @param cards Collection de cartes à ajouter à sa main
	 */
	public void isForcedToPickUpCards(Collection<Card> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Card collection picked up cannot be null");
		Preconditions.checkArgument(cards.size()>0, "[ERROR] Card collection picked cannot be empty");
		this.consoleView.insertBlankLine();
		this.consoleView.appendBoldJokerText("Player [" + getAlias() + "] was forced to draw " + cards.size() + " cards");
		this.consoleView.insertBlankLine();
		this.player.pickUpCards(cards);
	}
	
	/* ========================================= CARD PLAY ========================================= */
	
	/**
	 * Méthode permettant de jouer une carte
	 * @param index Index de la carte
	 * @return Carte selectionnée
	 */
	public Card playCard(int index) {
		Preconditions.checkState(this.player.getNumberOfCardsInHand() > 0, "[ERROR] Impossible to play a card : player has none");
		Preconditions.checkArgument(index >= 0 && index < this.player.getNumberOfCardsInHand(),"[ERROR] Incorrect index : must be > 0 (tried = " + index + ", but max is = " + this.player.getNumberOfCardsInHand());
		return this.player.playCard(index);
	}
	
	/**
	 * Méthode permettant de savoir si le joueur a en sa possession au moins une carte compatible avec celle de référence
	 * @param referenceCard Carte constituant le talon actuel
	 * @return TRUE si le joueur en a au moins une, FALSE sinon
	 */
	public boolean hasAtLeastOnePlayableCard(GameModelBean gameModelBean) {
		Preconditions.checkNotNull(gameModelBean,"[ERROR] gameModelBean cannot be null");
		return gameModelBean.isCompatibleWith(this.getCardsInHand());
	}
	
	/**
	 * Méthode privée permettant de récuperer les cartes en main pour pouvoir les afficher
	 * @return Collection de cartes en main
	 */
	private Collection<Card> getCardsInHand() {
		return this.player.getCardsInHand();
	}
	
	/* ========================================= TURN HANDLING ========================================= */
	
	/**
	 * Méthode permettant de gérer le tour d'un joueur
	 * @param inputReader Objet permettant de recevoir l'index entré par l'utilisateur
 	 * @param gameModelbean Carte du talon (carte de référence)
	 * @return La carte choisie par l'utilisateur (qui est nécessairement compatible avec le talon)
	 */
	public Card startTurn(InputReader inputReader, GameModelBean gameModelbean) {
		Preconditions.checkNotNull(inputReader,"[ERROR] Impossible to start turn, inputReader is null");
		Preconditions.checkNotNull(gameModelbean,"[ERROR] Impossible to start turn, gameModelbean is null");
		String alias = this.player.getAlias();
		Collection<Card> cardCollection = this.getCardsInHand();
		int index = inputReader.getFirstValidIndexFromInput(alias,cardCollection,gameModelbean);
		Card choosenCard = this.player.peekAtCard(index);
		while(!gameModelbean.isCompatibleWith(choosenCard)) {
			index = inputReader.getAnotherValidIndexFromInputDueToIncompatibleCard(alias,cardCollection,gameModelbean);
			choosenCard = this.player.peekAtCard(index);
		}
		return this.player.playCard(index);
	}
	
	/**
	 * Méthode privée permettant de gérer le cas où le joueur est dans l'incapacité de jouer son tour
	 * @param gameModelbean Carte du talon (carte de référence)
	 */
	public void unableToPlayThisTurn(GameModelBean gameModelbean) {
		Preconditions.checkNotNull(gameModelbean,"[ERROR] Impossible to start turn, gameModelbean is null");
		Collection<Card> cardsInHand = this.player.getCardsInHand();
		this.consoleView.appendBoldText("You now have : ");
		this.consoleView.displayCardCollection(cardsInHand);
		this.consoleView.appendBoldText("The last card play was : ");
		this.consoleView.displayCard(gameModelbean.getLastCardPlayed());
		gameModelbean.appendGlobalColorIfItIsSet();
		this.consoleView.appendBoldJokerText("Sadly, even after picking a new card, you didn't have any playable");
		this.consoleView.insertBlankLine();
		this.consoleView.appendBoldJokerText("Your turn will now automatically end");
		this.consoleView.insertBlankLine();
	}
	
	/* ========================================= EFFECTS RELATED ========================================= */
	
	/**
	 * Méthode permettant au joueur de choisir la couleur après avoir joué un joker (ou +4)
	 * @param inputReader 
	 * @return 
	 */
	public Color hasToChooseColor(InputReader inputReader) {
		Preconditions.checkNotNull(inputReader,"[ERROR] Impossible to start turn, inputReader is null");
		consoleView.insertBlankLine();
		consoleView.appendBoldJokerText("You played a Joker, please choose a color");
		this.consoleView.insertBlankLine();
		return inputReader.getValidColor();
	}

	/* ========================================= GETTERS & UTILS ========================================= */
	
	/**
	 * Méthode permettant de récuperer le pseudo du joueur
	 * @return String correspondant à son pseudo
	 */
	public String getAlias() {
		return this.player.getAlias();
	}

	/**
	 * Méthode permettant de récuperer le score du joueur
	 * @return int correspondant à son score
	 */
	public int getScore() {
		return this.player.getScore();
	}

	/**
	 * Méthode permettant de récuperer le nombre de cartes en main
	 * @return int correspondant au nombre de cartes en main
	 */
	public int getNumberOfCardsInHand() {
		return this.player.getNumberOfCardsInHand();
	}

	/**
	 * Méthode permettant de savoir si le joueur possède encore des cartes dans sa main
	 * @return
	 */
	public boolean stillHasCards() {
		return getNumberOfCardsInHand() > 0;
	}
	
	/**
	 * Méthode defissant comment les objets de cette classe s'affiche
	 */
	@Override
	public String toString() {
		return this.player.toString();
	}

	public int getPointsFromCardsInHand() {
		int pointsFromCards = 0;
		for(Card currentCard : this.player.getCardsInHand()) {
			pointsFromCards += currentCard.getValeur();
			System.out.println("SUM = " + pointsFromCards);
		}
		return pointsFromCards;
	}

	public void resetHand() {
		this.player.resetHand();	
	}

	public void increaseScoreBy(Integer playerScore) {
		Preconditions.checkNotNull(playerScore,"[ERROR] Impossible to set score, provided number is null");
		Preconditions.checkArgument(playerScore > 0,"[ERROR] Impossible to set score, provided number must be positive");
		this.player.increaseScoreBy(playerScore);
	}	
}