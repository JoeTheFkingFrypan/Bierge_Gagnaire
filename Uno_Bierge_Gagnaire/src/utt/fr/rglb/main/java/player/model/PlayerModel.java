package utt.fr.rglb.main.java.player.model;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.CardSorter;

/**
 * Classe correspondant aux donn�es d'un joueur
 */
public class PlayerModel {
	private boolean unoAnnoucement;
	private final String alias;
	private List<Card> cardsInHand;
	private int score;

	/* ========================================= CONSTRUCTOR ========================================= */
	
	/**
	 * Constructeur de PlayerModel
	 * Initialise �galement le score � 0
	 * @param alias Nom du joueur
	 */
	public PlayerModel(String alias) {
		Preconditions.checkNotNull(alias);
		this.cardsInHand = new ArrayList<Card>();
		this.unoAnnoucement = false;
		this.alias = alias;
		this.score = 0;
	}

	/* ========================================= CARD PICKUP ========================================= */

	/**
	 * M�thode permettant de r�cup�rer une collection de cartes et de toutes les ajouter dans la main du joueur
	 * @param cards Collection de cartes � ajouter dans la main
	 */
	public void pickUpCards(Collection<Card> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Cannot pickup cards : provided collection is null");
		Preconditions.checkArgument(cards.size() > 0, "[ERROR] Cannot pickup cards : provided collection is empty");
		this.cardsInHand.addAll(cards);
		Collections.sort(this.cardsInHand,new CardSorter());
	}
	
	/**
	 * M�thode permettant de r�cup�rer une unique carte et de l'ajouter dans la main du joueur
	 * @param cards Carte � ajouter dans la main
	 */
	public void pickUpOneCard(Card card) {
		Preconditions.checkNotNull(card,"[ERROR] Cannot pickup card : provided card is null");
		this.cardsInHand.add(card);
		Collections.sort(this.cardsInHand,new CardSorter());
	}
	
	/* ========================================= CARD PLAY ========================================= */

	/**
	 * M�thode permettant de r�cup�rer la carte choisie par l'utilisateur, sans la retirer de sa main
	 * @param index Index de la carte choisie
	 * @return Carte choisie par l'utilsateur
	 */
	public Card peekAtCard(int index) {
		Preconditions.checkState(this.cardsInHand.size() > 0, "[ERROR] Cannot play that card : player has no card");
		Preconditions.checkArgument(index >= 0, "[ERROR] Cannot play that card : provided index must not be negative");
		Preconditions.checkArgument(index < this.cardsInHand.size(), "[ERROR] Cannot play that card : provided index is too high (doesn't exists for this player)");
		Card cardToPlay = this.cardsInHand.get(index);
		return cardToPlay;
	}
	
	/**
	 * M�thode permettant de r�cup�rer la carte choisie par l'utilisateur et de la retirer de sa main
	 * @param index Index de la carte choisie
	 * @return Carte choisie par l'utilsateur
	 */
	public Card playCard(int index) {
		Preconditions.checkState(this.cardsInHand.size() > 0, "[ERROR] Cannot play that card : player has no card");
		Preconditions.checkArgument(index >= 0, "[ERROR] Cannot play that card : provided index must not be negative");
		Preconditions.checkArgument(index < this.cardsInHand.size(), "[ERROR] Cannot play that card : provided index is too high (doesn't exists for this player)");
		Card cardToPlay = this.cardsInHand.get(index);
		this.cardsInHand.remove(index);
		return cardToPlay;
	}
	
	/**
	 * M�thode permettant de r�cuperer les cartes dans la main du joueur
	 * @return Collection comprenant les cartes en main
	 */
	public Collection<Card> getCardsInHand() {
		Collection<Card> cardsInHand = this.cardsInHand;
		return cardsInHand;
	}
	
	/* ========================================= GETTERS & UTILS ========================================= */

	/**
	 * M�thode permettant de r�cuperer le nombre de cartes actuel du joueur
	 * @return int correspondant au nombre de cartes en main
	 */ 
	public int getNumberOfCardsInHand() {
		return this.cardsInHand.size();
	}

	/**
	 * M�thode permettant de r�cuperer le score du joueur
	 * @return int correspondant au score du joueur
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * M�thode d�finissant la fa�on dont s'affiche les instances de cette classe
	 */
	@Override
	public String toString() {
		return this.alias;
	}

	/**
	 * M�thode permettant de r�-initialiser la main du joueur
	 */
	public void resetHand() {
		this.cardsInHand.clear();		
	}

	/**
	 * M�thode permettant d'incr�menter le score du joueur
	 * @param playerScore Nombre � ajouter au score actuel
	 */
	public void increaseScoreBy(Integer playerScore) {
		Preconditions.checkNotNull(playerScore,"[ERROR] Impossible to set score, provided number is null");
		Preconditions.checkArgument(playerScore > 0,"[ERROR] Impossible to set score, provided number must be positive");
		this.score += playerScore;
	}

	/* ========================================= UNO ANNOUCEMENT ========================================= */
	
	/**
	 * M�thode permettant d'indique que le joueur a annonc� UNO
	 */
	public void setUnoAnnoucement() {
		this.unoAnnoucement = true;
	}
	
	/**
	 * M�thode permettant de r�-initaliser l'annonce de UNO
	 */
	public void resetUnoAnnoucement() {
		this.unoAnnoucement = false;
	}
	
	/**
	 * M�thode permettant de v�rifier si le joueur a annonc� UNO ou non
	 * @return
	 */
	public boolean hasAnnouncedUno() {
		return this.unoAnnoucement;
	}
}