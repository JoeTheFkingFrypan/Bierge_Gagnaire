package utt.fr.rglb.main.java.cards.model;

import com.google.common.base.Preconditions;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.cards.model.pile.Pile;
import utt.fr.rglb.main.java.cards.model.stock.Stock;
import utt.fr.rglb.main.java.console.model.AbstractModel;

/**
 * Classe comprenant les classes composant le jeu (pioche et talon) et leurs donn�es
 */
public class CardsModel extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private Color globalColor;
	private Stock stock;
	private Pile pile;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur de GameModel (cr�e en interne la pioche et la pile) g�n�rant l'ensemble des 108 cartes
	 * Initialise �galement le sens de rotation par d�faut
	 * Tire �galement la toute premi�re carte depuis la pioche pour former le talon
	 */
	public CardsModel () {
		this.globalColor = Color.JOKER;
		this.stock = new Stock();
		this.pile = new Pile();
	}

	/**
	 * M�thode priv�e permettant d'initialiser le talon (en tirant la premi�re carte de la pioche)
	 * @return La premi�re carte tir�e depuis la pioche
	 */
	public Card drawStarterCard() {
		Card starterCard = drawOneCard();
		this.pile.receiveCard(starterCard);
		return starterCard;
	}

	/* ========================================= CARD DRAW ========================================= */

	/**
	 * M�thode permettant de tirer une unique carte (avec gestion en cas de pioche vide)
	 * @return La premi�re carte de la pioche
	 */
	public Card drawOneCard() {
		refillStockIfNeeded(1);
		return this.stock.drawOneCard();
	}

	/**
	 * M�thode permettant de tirer les n premi�res cartes de la pioche (avec gestion en cas de pioche vide)
	 * @param count Nombre de cartes � tirer
	 * @return Les n premi�res carte de la pioche
	 */
	public Collection<Card> drawCards(int count) {
		Preconditions.checkArgument(count > 0,"[ERROR] Invalid card amount, must not be negative");
		refillStockIfNeeded(count);
		return this.stock.drawCards(count);
	}

	/**
	 * M�thode permettant de remplir la pioche si jamais le nombre d ecartes n'est pas suffisant pour piocher
	 * @param count Nombre de cartes souhait�
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
	 * M�thode permettant de jouer une carte
	 * @param chosenCard Carte choisie pour l'utilisateur
	 */
	public void playCard(Card chosenCard) {
		Preconditions.checkNotNull(chosenCard,"[ERROR] Impossible to play card : provided one is null");
		this.pile.receiveCard(chosenCard);
		resetGlobalColor();
	}

	/**
	 * M�thode permettant de r�cuperer la derni�re carte ayant �t� jou�e (sans la retirer du talon)
	 * @return La derni�re carte ayant �t� jou�e
	 */
	public Card showLastCardPlayed() {
		return this.pile.showLastCardPlayed();
	}

	/* ========================================= GLOBAL COLOR ========================================= */
	
	/**
	 * M�thode permettant de d�finir la couleur globale (couleur sp�cifi�e par l'utilisateur)
	 * @param chosenColor Couleur choisie par l'utilisateur
	 */
	public void setGlobalColor(Color chosenColor) {
		Preconditions.checkNotNull(chosenColor,"[ERROR] Impossible to set global color : provided one is null");
		Preconditions.checkArgument(!chosenColor.equals(Color.JOKER),"[ERROR] Impossible to set global color : JOKER is not a valid global color");
		this.globalColor = chosenColor;
	}

	/**
	 * M�thode permettant de savoir si une couleur globale est d�finie (si un joker/+4 a �t� pr�c�dement jou�)
	 * @return TRUE si une couleur globale est d�finie, FALSE sinon
	 */
	public boolean globalColorIsSet() {
		if(Color.JOKER.equals(getGlobalColor())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * M�thode permettant de r�cup�rer la couleur globale d�finie
	 * @return La couleur associ�e
	 */
	public Color getGlobalColor() {
		return this.globalColor;
	}

	/* ========================================= UTILS ========================================= */

	/**
	 * M�thode permettant de r�cupere le nombre de cartes contenues dans la pioche
	 * @return La taille de la pioche
	 */
	public int getStockSize() {
		return this.stock.size();
	}

	/**
	 * M�thode permettant de r�cupere le nombre de cartes contenues dans le talon
	 * @return La taille de le talon
	 */
	public int getPileSize() {
		return this.pile.size();
	}
	
	/**
	 * M�thode priv�e permettant de r�-initialiser la couleur globale apr�s qu'une carte ait �t� jou�e
	 */
	private void resetGlobalColor() {
		this.globalColor = Color.JOKER;
	}
	
	/* ========================================= RESET ========================================= */
	
	/**
	 * M�thode permettant de r�-initialiser les collections de cartes (talon et pioche)
	 */
	public void resetCards() {
		this.pile.resetCards();
		this.stock.resetCards();
	}
}