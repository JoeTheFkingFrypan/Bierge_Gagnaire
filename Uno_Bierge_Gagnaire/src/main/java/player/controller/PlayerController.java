package main.java.player.controller;

import java.util.Collection;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Carte;
import main.java.console.model.InputReader;
import main.java.console.view.View;
import main.java.player.model.PlayerModel;

/**
 * Classe dont le r�le est de g�rer tout ce qui touche � un joueur
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
	 * M�thode permettant d'ajouter une collection de cartes � la main du joueur
	 * @param cards Collection de cartes devant �tre ajout�es
	 */
	public void pickUpCards(Collection<Carte> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Card collection picked up cannot be null");
		Preconditions.checkArgument(cards.size()>0, "[ERROR] Card collection picked cannot be empty");
		this.player.pickUpCards(cards);
	}

	/**
	 * M�thode permettant d'ajouter une unique carte � la main du joueur
	 * @param card Carte devant �tre ajout�e
	 */
	public void pickUpOneCard(Carte card) {
		Preconditions.checkNotNull(card,"[ERROR] Card picked up cannot be null");
		this.consoleView.appendBoldText("You had no playable cards in you hand, you've drawn : ");
		this.consoleView.displayCard(card);
		this.player.pickUpOneCard(card);
	}

	/* ========================================= CARD PLAY ========================================= */
	
	/**
	 * M�thode permettant de jouer une carte
	 * @param index Index de la carte
	 * @return Carte selectionn�e
	 */
	public Carte playCard(int index) {
		Preconditions.checkState(this.player.getNumberOfCardsInHand() > 0, "[ERROR] Impossible to play a card : player has none");
		Preconditions.checkArgument(index >= 0 && index < this.player.getNumberOfCardsInHand(),"[ERROR] Incorrect index : must be > 0 (tried = " + index + ", but max is = " + this.player.getNumberOfCardsInHand());
		return this.player.playCard(index);
	}
	
	/**
	 * M�thode permettant de savoir si le joueur a en sa possession au moins une carte compatible avec celle de r�f�rence
	 * @param referenceCard Carte constituant le talon actuel
	 * @return TRUE si le joueur en a au moins une, FALSE sinon
	 */
	public boolean hasAtLeastOnePlayableCard(Carte referenceCard) {
		Collection<Carte> cardCollection = this.getDisplayableCardCollection();
		for(Carte currentCard : cardCollection) {
			if(referenceCard.isCompatibleWith(currentCard)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * M�thode priv�e permettant de r�cuperer les cartes en main pour pouvoir les afficher
	 * @return Collection de cartes en main
	 */
	private Collection<Carte> getDisplayableCardCollection() {
		return this.player.getCardsInHand();
	}
	
	/* ========================================= TURN HANDLING ========================================= */
	
	/**
	 * M�thode permettant de g�rer le tour d'un joueur
	 * @param inputReader Objet permettant de recevoir l'index entr� par l'utilisateur
 	 * @param referenceCard Carte du talon (carte de r�f�rence)
	 * @return La carte choisie par l'utilisateur (qui est n�cessairement compatible avec le talon)
	 */
	public Carte startTurn(InputReader inputReader, Carte referenceCard) {
		String alias = this.player.getAlias();
		Collection<Carte> cardCollection = this.getDisplayableCardCollection();
		int index = inputReader.getFirstValidIndexFromInput(alias,cardCollection,referenceCard);
		Carte choosenCard = this.player.peekAtCard(index);
		while(!choosenCard.isCompatibleWith(referenceCard)) {
			index = inputReader.getAnotherValidIndexFromInputDueToIncompatibleCard(alias,cardCollection,referenceCard);
			choosenCard = this.player.peekAtCard(index);
		}
		return this.player.playCard(index);
	}
	
	/**
	 * M�thode priv�e permettant de g�rer le cas o� le joueur est dans l'incapacit� de jouer son tour
	 * @param currentCard Carte du talon (carte de r�f�rence)
	 */
	public void unableToPlayThisTurn(Carte currentCard) {
		Collection<Carte> cardsInHand = this.player.getCardsInHand();
		this.consoleView.appendBoldText("You now have : ");
		this.consoleView.displayCardCollection(cardsInHand);
		this.consoleView.appendBoldText("The last card play was : ");
		this.consoleView.displayCard(currentCard);
		this.consoleView.displayBoldText("Sadly, even after picking a new card, you didn't have any playable");
		this.consoleView.displayBoldText("Your turn will now automatically end");
	}

	/* ========================================= GETTERS & UTILS ========================================= */
	
	/**
	 * M�thode permettant de r�cuperer le pseudo du joueur
	 * @return String correspondant � son pseudo
	 */
	public String getAlias() {
		return this.player.getAlias();
	}

	/**
	 * M�thode permettant de r�cuperer le score du joueur
	 * @return int correspondant � son score
	 */
	public int getScore() {
		return this.player.getScore();
	}

	/**
	 * M�thode permettant de r�cuperer le nombre de cartes en main
	 * @return int correspondant au nombre de cartes en main
	 */
	public int getNumberOfCardsInHand() {
		return this.player.getNumberOfCardsInHand();
	}

	/**
	 * M�thode defissant comment les objets de cette classe s'affiche
	 */
	@Override
	public String toString() {
		return this.player.toString();
	}
}
