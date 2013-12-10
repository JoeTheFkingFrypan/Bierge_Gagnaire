package utt.fr.rglb.main.java.player.controller;

import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.basics.Card;


public class PlayerControllerBean {
	private boolean stillHasCards;
	private boolean hasAnnouncedUno;
	private boolean hasNotWonTheGame;
	private PlayerController currentPlayer;
	
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
	
	public boolean stillHasCards() {
		return this.stillHasCards;
	}
	
	public boolean hasAnnouncedUno() {
		return this.hasAnnouncedUno;
	}
	
	public PlayerController getPlayer() {
		return this.currentPlayer;
	}

	public boolean hasNotWonTheGame() {
		return this.hasNotWonTheGame;
	}
	
	public boolean hasNoCardAndForgotToAnnounceUno() {
		return this.currentPlayer.hasNoCardAndForgotToAnnounceUno();
	}

	public void isForcedToPickUpCards(Collection<Card> cardPenalty) {
		this.currentPlayer.isForcedToPickUpCards(cardPenalty);
		this.stillHasCards = this.currentPlayer.stillHasCards();
	}

	public boolean increaseScoreBy(Integer pointsReceived) {
		this.currentPlayer.increaseScoreBy(pointsReceived);
		this.hasNotWonTheGame = this.currentPlayer.getScore() < 500;
		return this.hasNotWonTheGame;
	}
	
	public String getAlias() {
		return this.currentPlayer.getAlias();
	}

	public boolean deservesTheRightToAnnounceUno() {
		return this.currentPlayer.deservesTheRightToAnnounceUno();
	}
}
