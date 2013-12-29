package utt.fr.rglb.main.java.player.controller;

import com.google.common.base.Preconditions;

import java.io.BufferedReader;
import java.io.Serializable;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.console.model.InputReader;
import utt.fr.rglb.main.java.console.view.View;
import utt.fr.rglb.main.java.main.ServerException;
import utt.fr.rglb.main.java.player.model.PlayerModel;

/**
 * Classe dont le rôle est de gérer tout ce qui touche à un joueur
 */
public class PlayerController implements Serializable {
	private static final long serialVersionUID = 1L;
	protected PlayerModel player;
	protected View consoleView;
	private BufferedReader inputStream;

	/* ========================================= CONSTRUCTOR ========================================= */

	public PlayerController(String name, View consoleView, BufferedReader inputStream) {
		Preconditions.checkNotNull(name,"[ERROR] name cannot be null");
		Preconditions.checkNotNull(consoleView,"[ERROR] view cannot be null");
		this.player = new PlayerModel(name);
		this.consoleView = consoleView;
		this.inputStream = inputStream;
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
		this.player.resetUnoAnnoucement();
	}

	/**
	 * Méthode permettant d'ajouter une unique carte à la main du joueur
	 * @param card Carte devant être ajoutée
	 */
	public void pickUpOneCard(Card card) {
		Preconditions.checkNotNull(card,"[ERROR] Card picked up cannot be null");
		this.consoleView.displayCard("You had no playable cards in you hand, you've drawn : ",card);
		this.player.pickUpOneCard(card);
		this.player.resetUnoAnnoucement();
	}

	/**
	 * Méthiode permettant de forcer un joueur à piocher (avec affichage d'un message)
	 * @param cards Collection de cartes à ajouter à sa main
	 */
	public void isForcedToPickUpCards(Collection<Card> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Card collection picked up cannot be null");
		Preconditions.checkArgument(cards.size()>0, "[ERROR] Card collection picked cannot be empty");
		Integer numberOfCards = cards.size();
		this.consoleView.displayErrorMessageUsingPlaceholders("Player [",getAlias(),"] was forced to draw ",numberOfCards.toString()," cards");
		this.player.pickUpCards(cards);
		this.player.resetUnoAnnoucement();
	}

	/**
	 * Méthiode permettant de forcer un joueur à piocher (avec affichage d'un message) --Cas spécifique du jeu d'un +4 sans bluff
	 * @param cards Collection de cartes à ajouter à sa main
	 */
	public void isForcedToPickUpCardsLegitCase(Collection<Card> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Card collection picked up cannot be null");
		Preconditions.checkArgument(cards.size()>0, "[ERROR] Card collection picked cannot be empty");
		Integer numberOfCards = cards.size();
		this.consoleView.displayErrorMessageUsingPlaceholders("Sadly for you, previous player ", "wasn't ", " bluffing");
		this.consoleView.displayErrorMessageUsingPlaceholders("Player [",getAlias(),"] receives ",numberOfCards.toString()," cards for accusing previous player of bluffing");
		this.player.pickUpCards(cards);
		this.player.resetUnoAnnoucement();
	}

	/**
	 * Méthiode permettant de forcer un joueur à piocher (avec affichage d'un message) --Cas spécifique du jeu d'un +4 avec bluff
	 * @param cards Collection de cartes à ajouter à sa main
	 */
	public void isForcedToPickUpCardsBluffCase(Collection<Card> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Card collection picked up cannot be null");
		Preconditions.checkArgument(cards.size()>0, "[ERROR] Card collection picked cannot be empty");
		Integer numberOfCards = cards.size();
		this.consoleView.displayGreenEmphasisUsingPlaceholders("Player [",getAlias(),"] got caught bluffing : first of all he receives ",numberOfCards.toString()," cards");
		this.player.pickUpCards(cards);
		this.player.resetUnoAnnoucement();
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
	 * @param gameModelBean Objet de référence encapsulant la dernière carte jouée et éventuellement la couleur globale
	 * @return <code>TRUE</code> si le joueur en a au moins une, <code>FALSE</code> sinon
	 */
	public boolean hasAtLeastOnePlayableCard(CardsModelBean gameModelBean) {
		Preconditions.checkNotNull(gameModelBean,"[ERROR] gameModelBean cannot be null");
		return gameModelBean.isCompatibleWith(this.getCardsInHand());
	}

	/**
	 * Méthode privée permettant de récuperer les cartes en main pour pouvoir les afficher
	 * @return Collection de cartes en main
	 */
	protected Collection<Card> getCardsInHand() {
		return this.player.getCardsInHand();
	}

	/* ========================================= TURN HANDLING ========================================= */

	/**
	 * Méthode permettant de gérer le tour d'un joueur
	 * @param inputReader Objet permettant de recevoir l'index entré par l'utilisateur
	 * @param gameModelBean Carte du talon (carte de référence)
	 * @return La carte choisie par l'utilisateur (qui est nécessairement compatible avec le talon)
	 */
	//FIXME: Shrink method startTurn
	public Card startTurn(InputReader inputReader, CardsModelBean gameModelBean) {
		Preconditions.checkNotNull(inputReader,"[ERROR] Impossible to start turn, inputReader is null");
		Preconditions.checkNotNull(gameModelBean,"[ERROR] Impossible to start turn, gameModelbean is null");
		String alias = this.player.toString();
		Collection<Card> cardCollection = this.getCardsInHand();
		String answer = inputReader.getValidAnswer(alias,cardCollection,gameModelBean,this.inputStream);
		int index = inputReader.getNumberFromString(answer,this.inputStream);
		boolean unoHasBeenAnnounced = inputReader.findIfUnoHasBeenAnnounced(answer);
		Card choosenCard = this.player.peekAtCard(index);
		boolean wantsToPlayAnotherCard = false;
		if(choosenCard.isPlusFour()) {
			wantsToPlayAnotherCard = findIfBluffIsNeeded(inputReader,gameModelBean, choosenCard, wantsToPlayAnotherCard);
		}
		while(!gameModelBean.isCompatibleWith(choosenCard) || wantsToPlayAnotherCard) {
			answer = inputReader.getAnotherValidAnswerFromInputDueToIncompatibleCard(alias,cardCollection,gameModelBean,this.inputStream);
			index = inputReader.getNumberFromString(answer,this.inputStream);
			unoHasBeenAnnounced = inputReader.findIfUnoHasBeenAnnounced(answer);
			choosenCard = this.player.peekAtCard(index);
			if(choosenCard.isPlusFour()) {
				wantsToPlayAnotherCard = findIfBluffIsNeeded(inputReader,gameModelBean, choosenCard, wantsToPlayAnotherCard);
			} else {
				wantsToPlayAnotherCard = false;
			}
		}
		if(unoHasBeenAnnounced) {
			this.player.setUnoAnnoucement();
		}
		return this.player.playCard(index);
	}

	/**
	 * Méthode privée permettant de déterminer si le joueur souhaite réelement jouer un +4 carte dans le cas où il a d'autres cartes jouables
	 * Cette demande ne sera faite que si le bluff est necessaire pour jouer cette carte
	 * @param inputReader Objet permettant de recevoir l'index entré par l'utilisateur
	 * @param gameModelBean Carte du talon (carte de référence)
	 * @param choosenCard Carte choisie
	 * @param wantsToPlayAnotherCard
	 * @return <code>TRUE</code> si le joueur souhaite selectionner une nouvelle carte, <code>FALSE</code> sinon
	 */
	private boolean findIfBluffIsNeeded(InputReader inputReader, CardsModelBean gameModelBean, Card choosenCard, boolean wantsToPlayAnotherCard) {
		boolean hasPlayableCardsAsideFromPlusFour = gameModelBean.findIfPlayerHasPlayableCardsAsideFromPlusFour(this.player.getCardsInHand());
		if(hasPlayableCardsAsideFromPlusFour) {
			wantsToPlayAnotherCard = inputReader.askIfHeWantsToPlayAnotherCard(this.inputStream);
			if(!wantsToPlayAnotherCard) {
				choosenCard.setBluffOn();
			}
		}
		return wantsToPlayAnotherCard;
	}

	/**
	 * Méthode privée permettant de gérer le cas où le joueur est dans l'incapacité de jouer son tour
	 * @param gameModelbean Carte du talon (carte de référence)
	 */
	public void unableToPlayThisTurn(CardsModelBean gameModelbean) {
		Preconditions.checkNotNull(gameModelbean,"[ERROR] Impossible to start turn, gameModelbean is null");
		Collection<Card> cardsInHand = this.player.getCardsInHand();
		this.consoleView.displayCardCollection("You now have : ",cardsInHand);
		this.consoleView.displayCard("The last card play was : ",gameModelbean.getLastCardPlayed());
		gameModelbean.appendGlobalColorIfItIsSet();
		this.consoleView.displayTwoLinesOfJokerText("Sadly, even after picking a new card, you didn't have any playable","Your turn will now automatically end");
		chillForTwoSec("");
	}

	/* ========================================= EFFECTS RELATED ========================================= */

	/**
	 * Méthode permettant au joueur de choisir la couleur après avoir joué un joker (ou +4)
	 * @param isRelatedToPlus4 <code>TRUE</code> pour afficher un message spécial indiquant que le joueur en cours a de nouveau la main (dans le cas du jeu d'un +4), <code>FALSE</code> sinon
	 * @param inputReader Objet permettant de lire (et valider) les données rentrées au clavier
	 * @return La couleur choisie par l'utilsateur
	 */
	public Color hasToChooseColor(boolean isRelatedToPlus4, InputReader inputReader) {
		Preconditions.checkNotNull(inputReader,"[ERROR] Impossible to start turn, inputReader is null");
		if(isRelatedToPlus4) {
			this.consoleView.displaySeparationText("---- Back to you, " + this.getAlias() + " ----");
		}
		this.consoleView.displayOneLineOfJokerText("You played a Joker, please choose a color");
		return inputReader.getValidColor(this.inputStream);
	}

	/**
	 * Méthode permettant de savoir si le joueur tient à accuser le précédent de bluffer sur un +4
	 * @param inputReader Objet permettant de lire (et valider) les données rentrées au clavier
	 * @return <code>TRUE</code> si l'utilisateur souhaite accuser le joueur précédent, <code>FALSE</code> sinon
	 */
	public boolean askIfHeWantsToCheckIfItsLegit(InputReader inputReader) {
		this.consoleView.displaySeparationText("---- Your call, " + this.getAlias() + " ----");
		Preconditions.checkNotNull(inputReader,"[ERROR] Impossible to start turn, inputReader is null");
		this.consoleView.displayOneLineOfJokerText("Previous player played a +4");
		return inputReader.askIfHeWantsToCheckIfItsLegit(this.inputStream);
	}
	
	/* ========================================= GETTERS & UTILS ========================================= */

	/**
	 * Méthode permettant de récuperer le pseudo du joueur
	 * @return String correspondant à son pseudo
	 */
	public String getAlias() {
		return this.player.toString();
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
	 * @return <code>TRUE</code> si le joueur a au moins une carte en main, <code>FALSE</code> sinon
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

	/**
	 * Méthode permettant de ré-initialiser la main du joueur (suppression de toutes ses cartes)
	 */
	public void resetHand() {
		this.player.resetHand();	
	}

	/**
	 * Méthode permettant de ralentir l'execution des décisions de l'IA en imposant un délai de 2 secondes (avec affichage de caractères répétés sur une même ligne typiquement "..." ou rien)
	 * @param stringToDisplay Message à répété, permettant de simuler une attente
	 */
	protected void chillForTwoSec(String stringToDisplay) {
		Preconditions.checkNotNull(stringToDisplay,"[ERROR] Impossible display message while waiting : provided message is null");
		try {
			for(int i=0; i<4; i++) {
				Thread.sleep(500);
				this.consoleView.AppendOneLineOfBoldText(stringToDisplay);
			}
		} catch (InterruptedException e) {
			throw new ServerException("[ERROR] Something went wrong while [IA] " + this.getAlias() + " was peacefully chilling",e);
		}
	}

	/* ========================================= POINTS ========================================= */

	/**
	 * Méthode permettant de récupérer le nombre de points des cartes en main
	 * @return La somme des points de toutes les cartes en main
	 */
	public int getPointsFromCardsInHand() {
		int pointsFromCards = 0;
		for(Card currentCard : this.player.getCardsInHand()) {
			pointsFromCards += currentCard.getValue();
		}
		return pointsFromCards;
	}

	/**
	 * Méthode permettant d'incrémenter le score du joueur
	 * @param playerScore Nombre à ajouter au score actuel
	 * @return <code>TRUE</code> si le joueur a atteint 500 points, <code>FALSE</code> sinon
	 */
	public boolean increaseScoreBy(Integer playerScore) {
		Preconditions.checkNotNull(playerScore,"[ERROR] Impossible to set score, provided number is null");
		Preconditions.checkArgument(playerScore > 0,"[ERROR] Impossible to set score, provided number must be positive");
		this.player.increaseScoreBy(playerScore);
		return this.player.getScore() > 500;
	}

	/* ========================================= UNO ANNOUNCEMENT ========================================= */

	/**
	 * Méthode permettant de vérifier si le joueur a précédement annoncé UNO
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean hasAnnouncedUno() {
		return this.player.hasAnnouncedUno();
	}

	/**
	 * Méthode permettant de vérifier si le joueur avait effectivement le droit d'annoncer UNO
	 * @return <code>TRUE</code> s'il reste au joueur 1 carte (annonce lors du jeu de l'avant dernière carte) OU 0 cartes (jeu de la dernière carte), <code>FALSE</code> sinon
	 */
	public boolean deservesTheRightToAnnounceUno() {
		return (this.player.getNumberOfCardsInHand() == 1) || (this.player.getNumberOfCardsInHand() == 0);
	}

	/**
	 * Méthode permettant de vérifier si le joueur a oublié d'annoncer UNO quand il joue sa dernière carte
	 * @return <code>TRUE</code> si le joueur n'a pas plus de cartes et a effectivement oublié d'annoncer UNO, <code>FALSE</code> sinon
	 */
	public boolean hasNoCardAndForgotToAnnounceUno() {
		boolean hasNoCard = this.player.getNumberOfCardsInHand() == 0;
		boolean forgotToAnnounceUno = ! (this.hasAnnouncedUno());
		return hasNoCard && forgotToAnnounceUno;
	}
}