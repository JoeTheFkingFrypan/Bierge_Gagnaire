package main.java.cards.model;

import java.util.Collection;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Card;
import main.java.cards.model.basics.Color;
import main.java.cards.model.pile.Pile;
import main.java.cards.model.stock.Stock;
import main.java.console.model.AbstractModel;

/**
 * Classe comprenant les classes composant le jeu (pioche et talon) et leurs donn�es
 */
public class GameModel extends AbstractModel {
	private Color globalColor;
	private Stock pioche;
	private Pile talon;

	/* ========================================= CONSTRUCTOR ========================================= */

	public GameModel () {
		this.globalColor = Color.JOKER;
		this.pioche = new Stock();
		this.talon = new Pile();
		drawStarterCard();
	}

	/**
	 * M�thode priv�e permettant d'initialiser le talon (en tirant la premi�re carte de la pioche)
	 */
	//TODO: handle starting events
	private void drawStarterCard() {
		this.talon.receiveCard(drawOneCard());
	}

	/* ========================================= CARD DRAW ========================================= */

	/**
	 * M�thode permettant de tirer une unique carte (avec gestion en cas de pioche vide)
	 * @return La premi�re carte de la pioche
	 */
	public Card drawOneCard() {
		refillStockIfNeeded(1);
		return this.pioche.drawOneCard();
	}

	/**
	 * M�thode permettant de tirer les n premi�res cartes de la pioche (avec gestion en cas de pioche vide)
	 * @param count Nombre de cartes � tirer
	 * @return Les n premi�res carte de la pioche
	 */
	public Collection<Card> drawCards(int count) {
		Preconditions.checkArgument(count>0,"[ERROR] Invalid card amount, must not be negative");
		refillStockIfNeeded(count);
		return this.pioche.drawCards(count);
	}

	/**
	 * M�thode permettant de remplir la pioche si jamais le nombre d ecartes n'est pas suffisant pour piocher
	 * @param count Nombre de cartes souhait�
	 */
	private void refillStockIfNeeded(int count) {
		Preconditions.checkArgument(count>0,"[ERROR] Invalid card amount, must not be negative");
		if(this.pioche.hasNotEnoughCards(count)) {
			Collection<Card> cardsFromPile = talon.emptyPile();
			System.out.println("RESTOCKED : " + cardsFromPile.size());
			this.pioche.refill(cardsFromPile);
		}
	}

	/* ========================================= PLAY CARD ========================================= */

	/**
	 * M�thode permettant de jouer une carte
	 * @param chosenCard Carte choisie pour l'utilisateur
	 */
	public void playCard(Card chosenCard) {
		Preconditions.checkNotNull(chosenCard,"[ERROR] Impossible to play card : provided one is null");
		this.talon.receiveCard(chosenCard);
		resetGlobalColor();
	}

	/**
	 * M�thode permettant de r�cuperer la derni�re carte ayant �t� jou�e (sans la retirer du talon)
	 * @return La derni�re carte ayant �t� jou�e
	 */
	public Card showLastCardPlayed() {
		return this.talon.showLastCardPlayed();
	}

	/* ========================================= UTILS ========================================= */

	/**
	 * M�thode permettant de r�cupere le nombre de cartes contenues dans la pioche
	 * @return La taille de la pioche
	 */
	public int getStockSize() {
		return this.pioche.size();
	}

	/**
	 * M�thode permettant de r�cupere le nombre de cartes contenues dans le talon
	 * @return La taille de le talon
	 */
	public int getPileSize() {
		return this.talon.size();
	}

	public void setGlobalColor(Color chosenColor) {
		Preconditions.checkNotNull(chosenColor,"[ERROR] Impossible to set global color : provided one is null");
		this.globalColor = chosenColor;
	}

	public boolean globalColorIsSet() {
		if(Color.JOKER.equals(getGlobalColor())) {
			return false;
		} else {
			return true;
		}
	}

	public Color getGlobalColor() {
		return this.globalColor;
	}

	private void resetGlobalColor() {
		System.out.println("RESET :D");
		this.globalColor = Color.JOKER;
	}
}
