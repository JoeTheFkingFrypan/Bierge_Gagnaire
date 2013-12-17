package utt.fr.rglb.main.java.player.model;

import com.google.common.base.Preconditions;
import java.io.Serializable;

import utt.fr.rglb.main.java.player.controller.PlayerController;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;

/**
 * Classe dont le r�le est de g�rer une �quipe de 2 joueurs
 */
public class PlayerTeam implements Serializable {
	private static final long serialVersionUID = 1L;
	private PlayerController playerOne;
	private PlayerController playerTwo;
	private Integer score;

	/* ========================================= CONSTRUCTOR ========================================= */
	
	public PlayerTeam() {
		this.playerOne = null;
		this.playerTwo = null;
		this.score = 0;
	}

	/* ========================================= PLAYER ADDITION ========================================= */
	
	/**
	 * M�thode permettant d'ajouter le 1er joueur � l'�quipe
	 * @param player Joueur � ajouter � l'�quipe
	 */
	public void addFirstPlayer(PlayerController player) {
		Preconditions.checkNotNull(player,"[ERROR] Impossible to add first player : provided player is null");
		Preconditions.checkState(this.playerOne == null,"[ERROR] to add first player : it has already been added");
		this.playerOne = player;
	}

	/**
	 * M�thode permettant d'ajouter le 2�me joueur � l'�quipe
	 * @param player Joueur � ajouter � l'�quipe
	 */
	public void addSecondPlayer(PlayerController player) {
		Preconditions.checkNotNull(player,"[ERROR] Impossible to add second player : provided player is null");
		Preconditions.checkState(this.playerOne == null,"[ERROR] to add second player : it has already been added");
		this.playerTwo = player;
	}

	/* ========================================= SCORING ========================================= */
	
	/**
	 * M�thode permettant de r�cup�rer la valeur des mains des 2 joueurs
	 * @return int correspondant � la somme des points provenant des cartes en main des 2 joueurs
	 */
	public int getScoreFromBothHands() {
		int totalScore = 0;
		totalScore += this.playerOne.getPointsFromCardsInHand();
		totalScore += this.playerTwo.getPointsFromCardsInHand();
		return totalScore;
	}

	/**
	 * M�thode permettant de r�cup�rer le score de l'�quipe
	 * @return int correspondant au score de l'�quipe
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * M�thode permettant d'incr�menter le score de l'�quipe et de v�rifier si l'�quipe a remport� la partie
	 * @param sumPlayerScore Nombre � ajouter au score actuel de l'�quipe
	 * @return <code>TRUE<code> si l'�quipe a gagn� la partie (score > 500), <code>FALSE</code> sinon
	 */
	public boolean increaseScore(int sumPlayerScore) {
		Preconditions.checkNotNull(sumPlayerScore,"[ERROR] Impossible to increase team score : provided amount is null");
		this.score += sumPlayerScore;
		return this.score > 500;
	}
	
	/* ========================================= UTILS ========================================= */
	
	@Override
	public String toString() {
		return playerOne.toString() + ", " + playerTwo.toString();
	}

	/**
	 * M�thode permettant de d�terminier si le joueur donn� appartient � cette �quipe
	 * @param winningPlayer Joueur dont la pr�sence est � v�rifier
	 * @return <code>TRUE</code> si le joueur donn� est dans l'�quipe, <code>FALSE</code> sinon
	 */
	public boolean contains(PlayerControllerBean winningPlayer) {
		Preconditions.checkNotNull(winningPlayer,"[ERROR] Impossible find if player is contained inside this team : provided player is null");
		PlayerController playerControllerFromWinningPlayer = winningPlayer.getPlayer();
		return this.playerOne.equals(playerControllerFromWinningPlayer) || this.playerTwo.equals(playerControllerFromWinningPlayer);
	}
}