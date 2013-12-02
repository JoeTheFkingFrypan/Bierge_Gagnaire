package main.java.cards.model;

import java.util.Collection;

import main.java.cards.model.basics.Carte;
import main.java.cards.model.pile.Talon;
import main.java.cards.model.stock.Pioche;
import main.java.console.model.AbstractModel;

/**
 * Classe comprenant les classes composant le jeu (pioche et talon) et leurs données
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
	 * Méthode privée permettant d'initialiser le talon (en tirant la première carte de la pioche)
	 */
	//TODO: handle starting events
	private void drawStarterCard() {
		this.talon.receiveCard(drawOneCard());
	}
	
	/* ========================================= CARD DRAW ========================================= */
	
	/**
	 * Méthode permettant de tirer une unique carte (avec gestion en cas de pioche vide)
	 * @return La première carte de la pioche
	 */
	public Carte drawOneCard() {
		refillStockIfNeeded(1);
		return this.pioche.drawOneCard();
	}
	
	/**
	 * Méthode permettant de tirer les n premières cartes de la pioche (avec gestion en cas de pioche vide)
	 * @param count Nombre de cartes à tirer
	 * @return Les n premières carte de la pioche
	 */
	public Collection<Carte> drawCards(int count) {
		refillStockIfNeeded(count);
		return this.pioche.drawCards(count);
	}
	
	/**
	 * Méthode permettant de remplir la pioche si jamais le nombre d ecartes n'est pas suffisant pour piocher
	 * @param count Nombre de cartes souhaité
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
	 * Méthode permettant de jouer une carte
	 * @param chosenCard Carte choisie pour l'utilisateur
	 */
	public void playCard(Carte chosenCard) {
		if(this.talon.accept(chosenCard)) {
			this.talon.receiveCard(chosenCard);
		}
	}
	
	/**
	 * Méthode permettant de récuperer la dernière carte ayant été jouée (sans la retirer du talon)
	 * @return La dernière carte ayant été jouée
	 */
	public Carte showLastCardPlayed() {
		return this.talon.showLastCardPlayed();
	}
	
	/* ========================================= UTILS ========================================= */
	
	/**
	 * Méthode permettant de récupere le nombre de cartes contenues dans la pioche
	 * @return La taille de la pioche
	 */
	public int getStockSize() {
		return this.pioche.size();
	}
	
	/**
	 * Méthode permettant de récupere le nombre de cartes contenues dans le talon
	 * @return La taille de le talon
	 */
	public int getPileSize() {
		return this.talon.size();
	}
}
