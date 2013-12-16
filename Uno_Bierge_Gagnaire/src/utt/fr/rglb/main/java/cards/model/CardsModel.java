package utt.fr.rglb.main.java.cards.model;

import com.google.common.base.Preconditions;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.cards.model.pile.Pile;
import utt.fr.rglb.main.java.cards.model.stock.Stock;
import utt.fr.rglb.main.java.console.model.AbstractModel;

/**
 * Classe comprenant les classes composant le jeu (pioche et talon) et leurs données
 */
public class CardsModel extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private Color globalColor;
	private Stock stock;
	private Pile pile;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur de GameModel (crée en interne la pioche et la pile) générant l'ensemble des 108 cartes
	 * Initialise également le sens de rotation par défaut
	 * Tire également la toute première carte depuis la pioche pour former le talon
	 */
	public CardsModel () {
		this.globalColor = Color.JOKER;
		this.stock = new Stock();
		this.pile = new Pile();
	}

	/**
	 * Méthode privée permettant d'initialiser le talon (en tirant la première carte de la pioche)
	 * @return La première carte tirée depuis la pioche
	 */
	public Card drawStarterCard() {
		Card starterCard = drawOneCard();
		this.pile.receiveCard(starterCard);
		return starterCard;
	}

	/* ========================================= CARD DRAW ========================================= */

	/**
	 * Méthode permettant de tirer une unique carte (avec gestion en cas de pioche vide)
	 * @return La première carte de la pioche
	 */
	public Card drawOneCard() {
		refillStockIfNeeded(1);
		return this.stock.drawOneCard();
	}

	/**
	 * Méthode permettant de tirer les n premières cartes de la pioche (avec gestion en cas de pioche vide)
	 * @param count Nombre de cartes à tirer
	 * @return Les n premières carte de la pioche
	 */
	public Collection<Card> drawCards(int count) {
		Preconditions.checkArgument(count > 0,"[ERROR] Invalid card amount, must not be negative");
		refillStockIfNeeded(count);
		return this.stock.drawCards(count);
	}

	/**
	 * Méthode permettant de remplir la pioche si jamais le nombre d ecartes n'est pas suffisant pour piocher
	 * @param count Nombre de cartes souhaité
	 */
	private void refillStockIfNeeded(int count) {
		Preconditions.checkArgument(count > 0,"[ERROR] Invalid card amount, must not be negative");
		if(this.stock.hasNotEnoughCards(count)) {
			Collection<Card> cardsFromPile = pile.emptyPile();
			this.stock.refill(cardsFromPile);
		}
	}

	/* ========================================= PLAY CARD ========================================= */

	/**
	 * Méthode permettant de jouer une carte
	 * @param chosenCard Carte choisie pour l'utilisateur
	 */
	public void playCard(Card chosenCard) {
		Preconditions.checkNotNull(chosenCard,"[ERROR] Impossible to play card : provided one is null");
		this.pile.receiveCard(chosenCard);
		resetGlobalColor();
	}

	/**
	 * Méthode permettant de récuperer la dernière carte ayant été jouée (sans la retirer du talon)
	 * @return La dernière carte ayant été jouée
	 */
	public Card showLastCardPlayed() {
		return this.pile.showLastCardPlayed();
	}

	/* ========================================= GLOBAL COLOR ========================================= */
	
	/**
	 * Méthode permettant de définir la couleur globale (couleur spécifiée par l'utilisateur)
	 * @param chosenColor Couleur choisie par l'utilisateur
	 */
	public void setGlobalColor(Color chosenColor) {
		Preconditions.checkNotNull(chosenColor,"[ERROR] Impossible to set global color : provided one is null");
		Preconditions.checkArgument(!chosenColor.equals(Color.JOKER),"[ERROR] Impossible to set global color : JOKER is not a valid global color");
		this.globalColor = chosenColor;
	}

	/**
	 * Méthode permettant de savoir si une couleur globale est définie (si un joker/+4 a été précédement joué)
	 * @return TRUE si une couleur globale est définie, FALSE sinon
	 */
	public boolean globalColorIsSet() {
		if(Color.JOKER.equals(getGlobalColor())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Méthode permettant de récupérer la couleur globale définie
	 * @return La couleur associée
	 */
	public Color getGlobalColor() {
		return this.globalColor;
	}

	/* ========================================= UTILS ========================================= */

	/**
	 * Méthode permettant de récupere le nombre de cartes contenues dans la pioche
	 * @return La taille de la pioche
	 */
	public int getStockSize() {
		return this.stock.size();
	}

	/**
	 * Méthode permettant de récupere le nombre de cartes contenues dans le talon
	 * @return La taille de le talon
	 */
	public int getPileSize() {
		return this.pile.size();
	}
	
	/**
	 * Méthode privée permettant de ré-initialiser la couleur globale après qu'une carte ait été jouée
	 */
	private void resetGlobalColor() {
		this.globalColor = Color.JOKER;
	}
	
	/* ========================================= RESET ========================================= */
	
	/**
	 * Méthode permettant de ré-initialiser les collections de cartes (talon et pioche)
	 */
	public void resetCards() {
		this.pile.resetCards();
		this.stock.resetCards();
	}
}