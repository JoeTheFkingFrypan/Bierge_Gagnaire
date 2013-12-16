package utt.fr.rglb.main.java.player.controller;

import java.io.Serializable;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.basics.Card;

/**
 * Classe encapsulant toutes les informations permettant d'acc�der � un joueur, � ses cartes et toutes les informations necessaires � la terminaison d'un round et d'une partie
 */
public class PlayerControllerBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean stillHasCards;
	private boolean hasAnnouncedUno;
	private boolean hasNotWonTheGame;
	private PlayerController currentPlayer;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	public PlayerControllerBean() {
		this.currentPlayer = null;
		this.stillHasCards = true;
		this.hasNotWonTheGame = true;
		this.hasAnnouncedUno = false;
	}	
	
	public PlayerControllerBean(PlayerController currentPlayer) {
		this.currentPlayer = currentPlayer;
		this.stillHasCards = currentPlayer.stillHasCards();
		this.hasNotWonTheGame = this.currentPlayer.getScore() < 500;
		this.hasAnnouncedUno = currentPlayer.hasAnnouncedUno();
	}
	
	/* ========================================= GETTERS ========================================= */
	
	/**
	 * M�thode permettant de v�rifier si le joueur encapsul� poss�de encore des cartes
	 * @return <code>TRUE</code> s'il lui reste encore des cartes, <code>FALSE</code> sinon
	 */
	public boolean stillHasCards() {
		return this.stillHasCards;
	}
	
	/**
	 * M�thode permettant de v�rifier si le joueur encapsul� a annonc� UNO
	 * @return <code>TRUE</code> s'il a annonc� UNO, <code>FALSE</code> sinon
	 */
	public boolean hasAnnouncedUno() {
		return this.hasAnnouncedUno;
	}
	
	/**
	 * M�thode permettant de v�rifier si le joueur encapsul� a remport� la partie
	 * @return <code>TRUE</code> s'il a remport� la partie, <code>FALSE</code> sinon
	 */
	public boolean hasNotWonTheGame() {
		return this.hasNotWonTheGame;
	}
	
	/**
	 * M�thode permettant de v�rifier si le joueur encapsul� a le droit d'annoncer UNO
	 * @return <code>TRUE</code> s'il a le droit d'annoncer UNO, <code>FALSE</code> sinon
	 */
	public boolean deservesTheRightToAnnounceUno() {
		return this.currentPlayer.deservesTheRightToAnnounceUno();
	}
	
	/**
	 * M�thode permettant de v�rifier si le joueur a jou� sa derni�re carte en ayant oubli� d'annoncer UNO
	 * @return <code>TRUE</code> s'il a jou� sa derni�re carte en ayant oubli� d'annoncer UNO, <code>FALSE</code> sinon
	 */
	public boolean hasNoCardAndForgotToAnnounceUno() {
		return this.currentPlayer.hasNoCardAndForgotToAnnounceUno();
	}

	/**
	 * M�thode permettant de r�cup�rer le joueur encapsul�
	 * @return Le joueur encapsul�
	 */
	public PlayerController getPlayer() {
		return this.currentPlayer;
	}
		
	/**
	 * M�thode permettant de r�cup�rer le pseudo du joueur
	 * @return <code>TRUE</code> s'il lui reste encore des cartes, <code>FALSE</code> sinon
	 */
	public String getAlias() {
		return this.currentPlayer.getAlias();
	}

	/* ========================================= LOGIC ========================================= */
	
	/**
	 * M�thode permettant forcer un joueur � ajouter des cartes dans sa main
	 * @param cardPenalty Collection de cartes correspondat � la p�nalit� re�ue
	 */
	public void isForcedToPickUpCards(Collection<Card> cardPenalty) {
		this.currentPlayer.isForcedToPickUpCards(cardPenalty);
		this.stillHasCards = this.currentPlayer.stillHasCards();
	}

	/**
	 * M�thode permettant d'incr�menter le score actuel du joueur, en v�rifiant si le joueur a remport� la partie ou non
	 * @param pointsReceived Points re�us
	 * @return <code>TRUE</code> si son score est inf�rieur � 500, <code>FALSE</code> sinon
	 */
	public boolean increaseScoreBy(Integer pointsReceived) {
		this.currentPlayer.increaseScoreBy(pointsReceived);
		this.hasNotWonTheGame = this.currentPlayer.getScore() < 500;
		return this.hasNotWonTheGame;
	}
}
