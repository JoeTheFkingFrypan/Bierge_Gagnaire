package utt.fr.rglb.main.java.game.model;

import java.io.Serializable;
import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.player.controller.AbstractPlayerController;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.model.PlayerTeam;

public abstract class AbstractGameModel implements Serializable {
	protected static final long serialVersionUID = 1L;
	
	/* ========================================= INITIALIZING ========================================= */

	/**
	 * Méthode permettant d'initialiser la main de chaque joueur (leur donne tous 7 cartes)
	 */
	public abstract void initializeCardsAndHands();

	/**
	 * Méthode permettant de tout ré-initialiser (en cas de démarrage d'une nouvelle PARTIE)
	 */
	public abstract void resetEverything();

	/**
	 * Méthode permettant de réinitialiser les cartes (en cas de démarrage d'un nouveau round)
	 */
	public abstract void resetCards();

	/* ========================================= GAME LOGIC ========================================= */

	/**
	 * Méthode permettant à un joueur de choisir une carte depuis sa main
	 * @param gameModelbean Carte dernièrement jouée (celle sur le talon, donc carte de référence)
	 * @param currentPlayer Joueur actuel
	 */
	protected abstract void chooseCardAndPlayIt(CardsModelBean gameModelbean, AbstractPlayerController currentPlayer);

	/* ========================================= EFFECTS - BASIS ========================================= */

	/**
	 * Méthode permettant de changer l'ordre du jeu
	 */
	protected abstract void triggerReverseCurrentOrder();

	/**
	 * Méthode permettant à un même joueur de jouer plusieurs cartes d'affilé
	 */
	protected abstract void triggerCycleSilently();

	/**
	 * Méthode permettant au joueur en paramètre de choisir une couleur
	 * @param currentPlayer Joueur devant désigner une couleur
	 * @param isRelatedToPlus4 <code>TRUE</code> si selection d'une couleur est due au jeu d'un plus 4, <code>FALSE</code> sinon
	 */
	//protected abstract void triggerColorPicking(AbstractPlayerController currentPlayer, boolean isRelatedToPlus4);

	/**
	 * Méthode permettant de forcer un joueur à piocher un nombre donné de cartes
	 * @param cardsToDraw int représentant le nombre de cartes à piocher
	 * @param targetedPlayer Joueur devant piocher lesdites cartes
	 */
	//protected abstract void triggerPlusX(int cardsToDraw, AbstractPlayerController targetedPlayer);

	/**
	 * Méthode permettant de forcer un joueur à piocher un nombre donné de cartes avec gestion du bluff par couleurs appropriées
	 * @param cardsToDraw int représentant le nombre de cartes à piocher
	 * @param targetedPlayer Joueur devant piocher lesdites cartes
	 * @param wasLegit <code>TRUE</code> si le jeu de la carte était légitime, <code>FALSE</code> sinon
	 */
	//protected abstract void triggerPlusX(int cardsToDraw, AbstractPlayerController targetedPlayer, boolean wasLegit);

	/**
	 * Méthode permettant de donner au joueur suivant l'opportunité de contrer un éventuel bluff
	 * @param nextPlayer Joueur suivant 
	 * @return <code>TRUE</code> si le joueur suivant souhaite vérifier le jeu de l'adversaire, <code>FALSE</code> sinon
	 */
	//protected abstract boolean triggerBluffing(AbstractPlayerController nextPlayer);

	/**
	 * Méthode permettant de donner une pénalité au joueur donné
	 * @param currentPlayer Joueur devant être pénalisé
	 * @param cardCount Nombre de cartes devant être piochées
	 */
	//public abstract void giveCardPenaltyTo(AbstractPlayerController currentPlayer, int cardCount);

	/**
	 * Méthode permettant de donner une pénalité au joueur donné
	 * @param player Joueur devant être pénalisé (encapsulée dans un PlayerControllerBean)
	 * @param cardCount Nombre de cartes devant être piochées
	 */
	//public abstract void giveCardPenaltyTo(PlayerControllerBean player, int cardCount);

	/**
	 * Méthode permettant d'obtenir une réponse valide
	 * @return La réponse de l'utilisateur
	 */
	//public abstract int getValidChoiceAnswer();

	/* ========================================= SCORE ========================================= */

	/**
	 * Méthode permettant de calculer les scores pour le(s) joueur(s) victorieux
	 * @param gameWinner Joueur ayant remporté le round
	 * @return <code>TRUE</code> si le joueur a gagné la partie, <code>FALSE</code> sinon
	 */
	public abstract boolean computeScores(PlayerControllerBean gameWinner);

	/**
	 * Méthode permettant de calculer le score du joueur gagnant à partir des cartes des autres participants
	 * @param gameWinner Joueur venant de remporter le round
	 * @return <code>TRUE</code> si le joueur a gagné la partie (s'il a plus de 500 points), <code>FALSE</code> sinon
	 */
	public abstract boolean computeIndividualScore(PlayerControllerBean gameWinner);

	/**
	 * Méthode permettant de calculer le score de l'équipe gagnante à partir des cartes des participants des autres équipes
	 * @param gameWinner Joueur venant de remporter le round
	 * @return <code>TRUE</code> si le l'équipe a gagné la partie (si elle a plus de 500 points), <code>FALSE</code> sinon
	 */
	public abstract boolean computeTeamScore(PlayerControllerBean gameWinner);

	/**
	 * Méthode permettant de stopper le fonctionnement du programme pendant 2.5 secondes
	 */
	protected abstract void waitForScoreDisplay();

	/**
	 * Méthode permettant de déterminer si le mode de jeu choisi est celui à deux joueurs
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public abstract boolean indicatesTeamPlayScoring();

	/**
	 * Méthode permettant de récuperer l'équipe à laquelle appartient le joueur donné
	 * @param winningPlayer Joueur victorieux dont on souhaite connaitre l'équipe
	 * @return L'équipe à laquelle appartient le joueur
	 */
	public abstract PlayerTeam findWinningTeam(PlayerControllerBean winningPlayer);
}
