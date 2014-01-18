package utt.fr.rglb.main.java.player.model;

import com.google.common.base.Preconditions;

import java.io.Serializable;

import utt.fr.rglb.main.java.player.controller.AbstractPlayerController;
import utt.fr.rglb.main.java.player.controller.PlayerControllerConsoleOriented;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;

/**
 * Classe dont le rôle est de gérer une équipe de 2 joueurs
 */
public class PlayerTeam implements Serializable {
	private static final long serialVersionUID = 1L;
	private AbstractPlayerController playerOne;
	private AbstractPlayerController playerTwo;
	private Integer score;

	/* ========================================= CONSTRUCTOR ========================================= */
	
	public PlayerTeam() {
		this.playerOne = null;
		this.playerTwo = null;
		this.score = 0;
	}

	/* ========================================= PLAYER ADDITION ========================================= */
	
	/**
	 * Méthode permettant d'ajouter le 1er joueur à l'équipe
	 * @param abstractPlayerController Joueur à ajouter à l'équipe
	 */
	public void addFirstPlayer(AbstractPlayerController abstractPlayerController) {
		Preconditions.checkNotNull(abstractPlayerController,"[ERROR] Impossible to add first player : provided player is null");
		Preconditions.checkState(this.playerOne == null,"[ERROR] to add first player : it has already been added");
		this.playerOne = abstractPlayerController;
	}

	/**
	 * Méthode permettant d'ajouter le 2ème joueur à l'équipe
	 * @param abstractPlayerController Joueur à ajouter à l'équipe
	 */
	public void addSecondPlayer(AbstractPlayerController abstractPlayerController) {
		Preconditions.checkNotNull(abstractPlayerController,"[ERROR] Impossible to add second player : provided player is null");
		Preconditions.checkState(this.playerTwo == null,"[ERROR] to add second player : it has already been added");
		this.playerTwo = abstractPlayerController;
	}

	/* ========================================= SCORING ========================================= */
	
	/**
	 * Méthode permettant de récupérer la valeur des mains des 2 joueurs
	 * @return int correspondant à la somme des points provenant des cartes en main des 2 joueurs
	 */
	public int getScoreFromBothHands() {
		int totalScore = 0;
		totalScore += this.playerOne.getPointsFromCardsInHand();
		totalScore += this.playerTwo.getPointsFromCardsInHand();
		return totalScore;
	}

	/**
	 * Méthode permettant de récupérer le score de l'équipe
	 * @return int correspondant au score de l'équipe
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * Méthode permettant d'incrémenter le score de l'équipe et de vérifier si l'équipe a remporté la partie
	 * @param sumPlayerScore Nombre à ajouter au score actuel de l'équipe
	 * @return <code>TRUE<code> si l'équipe a gagné la partie (score > 500), <code>FALSE</code> sinon
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
	 * Méthode permettant de déterminier si le joueur donné appartient à cette équipe
	 * @param winningPlayer Joueur dont la présence est à vérifier
	 * @return <code>TRUE</code> si le joueur donné est dans l'équipe, <code>FALSE</code> sinon
	 */
	public boolean contains(PlayerControllerBean winningPlayer) {
		Preconditions.checkNotNull(winningPlayer,"[ERROR] Impossible find if player is contained inside this team : provided player is null");
		PlayerControllerConsoleOriented playerControllerFromWinningPlayer = (PlayerControllerConsoleOriented) winningPlayer.getPlayer();
		return this.playerOne.equals(playerControllerFromWinningPlayer) || this.playerTwo.equals(playerControllerFromWinningPlayer);
	}
	
	/**
	 * Méthode permettant de récupérer le nom du premier joueur
	 * @return String correspondant à son nom
	 */
	public String getNameFromFirstPlayer() {
		return this.playerOne.toString();
	}
	
	/**
	 * Méthode permettant de récupérer le nom du deuxième joueur
	 * @return String correspondant à son nom
	 */
	public String getNameFromSecondPlayer() {
		return this.playerTwo.toString();
	}
}