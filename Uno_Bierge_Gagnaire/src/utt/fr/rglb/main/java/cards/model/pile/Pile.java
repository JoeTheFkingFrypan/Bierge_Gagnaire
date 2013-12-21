package utt.fr.rglb.main.java.cards.model.pile;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Collection;
import java.util.Stack;

import utt.fr.rglb.main.java.cards.model.basics.Card;

/**
 * Classe permettant de r�ception les cartes jou�es (avec comparaison par rapport � la derni�re re�ue).
 * </br>Sert �galement � reconstituer la pioche lorsque cette derni�re est vide.
 */
public class Pile implements Serializable {
	private static final long serialVersionUID = 1L;
	private final Stack<Card> talon;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur de talon
	 */
	public Pile() {
		this.talon = new Stack<Card>();
	}

	/* ========================================= PLAY CARD ========================================= */
	
	/**
	 * M�thode permettant de jouer une carte
	 * @param card Carte � jouer
	 */
	public void receiveCard(Card card) {
		Preconditions.checkNotNull(card,"[ERROR] Cannot play card : provided card is null");
		talon.push(card);
	}

	/* ========================================= EMPTYING (USED TO REFILL) ========================================= */

	/**
	 * M�thode permettant de vider le talon de ses cartes (sauf de la derni�re jou�e) et de les transf�rer
	 * @return L'ensemble des cartes (except�e la derni�re jou�e) provenant du talon
	 */
	public Collection<Card> emptyPile() {
		Preconditions.checkState(size() > 0, "[ERROR] Impossible to refill stock : pile is empty");
		Collection<Card> allCardsExceptLastPlayed = new Stack<Card>();
		Card lastCardPlayed = this.talon.pop();
		allCardsExceptLastPlayed.addAll(this.talon);
		this.talon.clear();
		this.talon.add(lastCardPlayed);
		return allCardsExceptLastPlayed;
	}

	/* ========================================= RESET ========================================= */
	
	/**
	 * M�thode permettant de r�-initialiser le talon (suppression de toutes les cartes)
	 */
	public void resetCards() {
		this.talon.clear();
	}
	
	/* ========================================= GETTERS & DISPLAY ========================================= */

	/**
	 * M�thode permettant de visionner la derni�re carte jou�e
	 * @return Carte derni�rement jou�e
	 */
	public Card showLastCardPlayed() {
		return this.talon.peek();
	}
	
	/**
	 * M�thode permettant de r�cuperer le nombre de cartes contenues dans le talon
	 * @return Nombre de cartes pr�sentes dans le talon
	 */
	public int size() {
		return this.talon.size();
	}
	
	/**
	 * M�thode permettant sp�cifiant la fa�on dont s'affiche le talon
	 */
	@Override
	public String toString() {
		return "[Talon] " + talon.size() + " cartes ont �t� jou�es";
	}
}
