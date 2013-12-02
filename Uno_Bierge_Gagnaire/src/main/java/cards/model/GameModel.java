package main.java.cards.model;

import java.util.Collection;

import main.java.cards.model.basics.Carte;
import main.java.cards.model.pile.Talon;
import main.java.cards.model.stock.Pioche;
import main.java.console.model.AbstractModel;

/**
 * Classe comprenant les classes composant le jeu (pioche et talon) et leurs donn�es
 */
public class GameModel extends AbstractModel {
	private Pioche pioche;
	private Talon talon;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	public GameModel () {
		this.pioche = new Pioche();
		this.talon = new Talon();
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
	public Carte drawOneCard() {
		refillStockIfNeeded(1);
		return this.pioche.drawOneCard();
	}
	
	/**
	 * M�thode permettant de tirer les n premi�res cartes de la pioche (avec gestion en cas de pioche vide)
	 * @param count Nombre de cartes � tirer
	 * @return Les n premi�res carte de la pioche
	 */
	public Collection<Carte> drawCards(int count) {
		refillStockIfNeeded(count);
		return this.pioche.drawCards(count);
	}
	
	/**
	 * M�thode permettant de remplir la pioche si jamais le nombre d ecartes n'est pas suffisant pour piocher
	 * @param count Nombre de cartes souhait�
	 */
	private void refillStockIfNeeded(int count) {
		if(this.pioche.hasNotEnoughCards(count)) {
			Collection<Carte> cardsFromPile = talon.emptyPile();
			System.out.println("RESTOCKED : " + cardsFromPile.size());
			this.pioche.refill(cardsFromPile);
		}
	}
	
	/* ========================================= PLAY CARD ========================================= */
	
	/**
	 * M�thode permettant de jouer une carte
	 * @param chosenCard Carte choisie pour l'utilisateur
	 */
	public void playCard(Carte chosenCard) {
		if(this.talon.accept(chosenCard)) {
			this.talon.receiveCard(chosenCard);
		}
	}
	
	/**
	 * M�thode permettant de r�cuperer la derni�re carte ayant �t� jou�e (sans la retirer du talon)
	 * @return La derni�re carte ayant �t� jou�e
	 */
	public Carte showLastCardPlayed() {
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
}
