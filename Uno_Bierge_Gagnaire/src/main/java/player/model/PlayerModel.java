package main.java.player.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Card;

/**
 * Classe correspondant aux donn�es d'un joueur
 */
public class PlayerModel {
	private boolean unoAnnoucement;
	private final String alias;
	private List<Card> main;
	private int score;

	/* ========================================= CONSTRUCTOR ========================================= */
	
	/**
	 * Constructeur de PlayerModel
	 * Initialise �galement le score � 0
	 * @param alias Nom du joueur
	 */
	public PlayerModel(String alias) {
		Preconditions.checkNotNull(alias);
		this.main = new ArrayList<Card>();
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
		this.main.addAll(cards);
	}
	
	/**
	 * M�thode permettant de r�cup�rer une unique carte et de l'ajouter dans la main du joueur
	 * @param cards Carte � ajouter dans la main
	 */
	public void pickUpOneCard(Card card) {
		Preconditions.checkNotNull(card,"[ERROR] Cannot pickup card : provided card is null");
		this.main.add(card);
	}
	
	/* ========================================= CARD PLAY ========================================= */

	/**
	 * M�thode permettant de r�cup�rer la carte choisie par l'utilisateur, sans la retirer de sa main
	 * @param index Index de la carte choisie
	 * @return Carte choisie par l'utilsateur
	 */
	public Card peekAtCard(int index) {
		Preconditions.checkState(this.main.size() > 0, "[ERROR] Cannot play that card : player has no card");
		Preconditions.checkArgument(index >= 0, "[ERROR] Cannot play that card : provided index must not be negative");
		Preconditions.checkArgument(index < this.main.size(), "[ERROR] Cannot play that card : provided index is too high (doesn't exists for this player)");
		Card cardToPlay = this.main.get(index);
		return cardToPlay;
	}
	
	/**
	 * M�thode permettant de r�cup�rer la carte choisie par l'utilisateur et de la retirer de sa main
	 * @param index Index de la carte choisie
	 * @return Carte choisie par l'utilsateur
	 */
	public Card playCard(int index) {
		Preconditions.checkState(this.main.size() > 0, "[ERROR] Cannot play that card : player has no card");
		Preconditions.checkArgument(index >= 0, "[ERROR] Cannot play that card : provided index must not be negative");
		Preconditions.checkArgument(index < this.main.size(), "[ERROR] Cannot play that card : provided index is too high (doesn't exists for this player)");
		Card cardToPlay = this.main.get(index);
		this.main.remove(index);
		return cardToPlay;
	}
	
	/**
	 * M�thode permettant de r�cuperer les cartes dans la main du joueur
	 * @return Collection comprenant les cartes en main
	 */
	public Collection<Card> getCardsInHand() {
		Collection<Card> cardsInHand = this.main;
		return cardsInHand;
	}
	
	/* ========================================= GETTERS & UTILS ========================================= */

	/**
	 * M�thode permettant de r�cuperer le nombre de cartes actuel du joueur
	 * @return int correspondant au nombre de cartes en main
	 */ 
	public int getNumberOfCardsInHand() {
		return this.main.size();
	}
	
	/**
	 * M�thode permettant de r�cuperer le pseudo du joueur
	 * @return String correspondant au pseudo du joueur
	 */
	public String getAlias() {
		return this.alias;
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
		return "[JOUEUR] " + getAlias() + " a " + getScore() + " points. Il lui reste " + this.main.size() + " cartes en main";
	}

	public void resetHand() {
		this.main.clear();		
	}

	public void increaseScoreBy(Integer playerScore) {
		Preconditions.checkNotNull(playerScore,"[ERROR] Impossible to set score, provided number is null");
		Preconditions.checkArgument(playerScore > 0,"[ERROR] Impossible to set score, provided number must be positive");
		this.score += playerScore;
	}

	public void setUnoAnnoucement() {
		this.unoAnnoucement = true;
	}
	
	public void resetUnoAnnoucement() {
		this.unoAnnoucement = false;
	}
	
	public boolean hasAnnouncedUno() {
		return this.unoAnnoucement;
	}
}