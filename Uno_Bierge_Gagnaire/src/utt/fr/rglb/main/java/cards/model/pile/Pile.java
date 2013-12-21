package utt.fr.rglb.main.java.cards.model.pile;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Collection;
import java.util.Stack;

import utt.fr.rglb.main.java.cards.model.basics.Card;

/**
 * Classe permettant de réception les cartes jouées (avec comparaison par rapport à la dernière reçue).
 * </br>Sert également à reconstituer la pioche lorsque cette dernière est vide.
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
	 * Méthode permettant de jouer une carte
	 * @param card Carte à jouer
	 */
	public void receiveCard(Card card) {
		Preconditions.checkNotNull(card,"[ERROR] Cannot play card : provided card is null");
		talon.push(card);
	}

	/* ========================================= EMPTYING (USED TO REFILL) ========================================= */

	/**
	 * Méthode permettant de vider le talon de ses cartes (sauf de la dernière jouée) et de les transférer
	 * @return L'ensemble des cartes (exceptée la dernière jouée) provenant du talon
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
	 * Méthode permettant de ré-initialiser le talon (suppression de toutes les cartes)
	 */
	public void resetCards() {
		this.talon.clear();
	}
	
	/* ========================================= GETTERS & DISPLAY ========================================= */

	/**
	 * Méthode permettant de visionner la dernière carte jouée
	 * @return Carte dernièrement jouée
	 */
	public Card showLastCardPlayed() {
		return this.talon.peek();
	}
	
	/**
	 * Méthode permettant de récuperer le nombre de cartes contenues dans le talon
	 * @return Nombre de cartes présentes dans le talon
	 */
	public int size() {
		return this.talon.size();
	}
	
	/**
	 * Méthode permettant spécifiant la façon dont s'affiche le talon
	 */
	@Override
	public String toString() {
		return "[Talon] " + talon.size() + " cartes ont été jouées";
	}
}
