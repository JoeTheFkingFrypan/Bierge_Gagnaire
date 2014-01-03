package utt.fr.rglb.main.java.player.controller;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.basics.Card;

/**
 * Classe encapsulant toutes les informations permettant d'accèder à un joueur, à ses cartes et toutes les informations necessaires à la terminaison d'un round et d'une partie
 */
public class PlayerControllerBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean stillHasCards;
	private boolean hasAnnouncedUno;
	private boolean hasNotWonTheGame;
	private AbstractPlayerController currentPlayer;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	public PlayerControllerBean() {
		this.currentPlayer = null;
		this.stillHasCards = true;
		this.hasNotWonTheGame = true;
		this.hasAnnouncedUno = false;
	}	
	
	public PlayerControllerBean(AbstractPlayerController currentPlayer) {
		Preconditions.checkNotNull(currentPlayer,"[ERROR] Impossible create PlayerControllerBean : provided player is null");
		this.currentPlayer = currentPlayer;
		this.stillHasCards = currentPlayer.stillHasCards();
		this.hasNotWonTheGame = this.currentPlayer.getScore() < 500;
		this.hasAnnouncedUno = currentPlayer.hasAnnouncedUno();
	}
	
	/* ========================================= GETTERS ========================================= */
	
	/**
	 * Méthode permettant de vérifier si le joueur encapsulé possède encore des cartes
	 * @return <code>TRUE</code> s'il lui reste encore des cartes, <code>FALSE</code> sinon
	 */
	public boolean stillHasCards() {
		return this.stillHasCards;
	}
	
	/**
	 * Méthode permettant de vérifier si le joueur encapsulé a annoncé UNO
	 * @return <code>TRUE</code> s'il a annoncé UNO, <code>FALSE</code> sinon
	 */
	public boolean hasAnnouncedUno() {
		return this.hasAnnouncedUno;
	}
	
	/**
	 * Méthode permettant de vérifier si le joueur encapsulé a remporté la partie
	 * @return <code>TRUE</code> s'il a remporté la partie, <code>FALSE</code> sinon
	 */
	public boolean hasNotWonTheGame() {
		return this.hasNotWonTheGame;
	}
	
	/**
	 * Méthode permettant de vérifier si le joueur encapsulé a le droit d'annoncer UNO
	 * @return <code>TRUE</code> s'il a le droit d'annoncer UNO, <code>FALSE</code> sinon
	 */
	public boolean deservesTheRightToAnnounceUno() {
		return this.currentPlayer.deservesTheRightToAnnounceUno();
	}
	
	/**
	 * Méthode permettant de vérifier si le joueur a joué sa dernière carte en ayant oublié d'annoncer UNO
	 * @return <code>TRUE</code> s'il a joué sa dernière carte en ayant oublié d'annoncer UNO, <code>FALSE</code> sinon
	 */
	public boolean hasNoCardAndForgotToAnnounceUno() {
		return this.currentPlayer.hasNoCardAndForgotToAnnounceUno();
	}

	/**
	 * Méthode permettant de récupérer le joueur encapsulé
	 * @return Le joueur encapsulé
	 */
	public AbstractPlayerController getPlayer() {
		return this.currentPlayer;
	}
		
	/**
	 * Méthode permettant de récupérer le pseudo du joueur
	 * @return <code>TRUE</code> s'il lui reste encore des cartes, <code>FALSE</code> sinon
	 */
	public String getAlias() {
		return this.currentPlayer.getAlias();
	}

	/* ========================================= LOGIC ========================================= */
	
	/**
	 * Méthode permettant forcer un joueur à ajouter des cartes dans sa main
	 * @param cardPenalty Collection de cartes correspondat à la pénalité reçue
	 */
	public void isForcedToPickUpCards(Collection<Card> cardPenalty) {
		Preconditions.checkNotNull(cardPenalty,"[ERROR] Impossible to give card penalty to player : provided card collection is null");
		this.currentPlayer.isForcedToPickUpCards(cardPenalty);
		this.stillHasCards = this.currentPlayer.stillHasCards();
	}

	/**
	 * Méthode permettant d'incrémenter le score actuel du joueur, en vérifiant si le joueur a remporté la partie ou non
	 * @param pointsReceived Points reçus
	 * @return <code>TRUE</code> si son score est inférieur à 500, <code>FALSE</code> sinon
	 */
	public boolean increaseScoreBy(Integer pointsReceived) {
		Preconditions.checkNotNull(pointsReceived,"[ERROR] Impossible to increase score : provided amount of points is null");
		this.currentPlayer.increaseScoreBy(pointsReceived);
		this.hasNotWonTheGame = this.currentPlayer.getScore() < 500;
		return this.hasNotWonTheGame;
	}
}
