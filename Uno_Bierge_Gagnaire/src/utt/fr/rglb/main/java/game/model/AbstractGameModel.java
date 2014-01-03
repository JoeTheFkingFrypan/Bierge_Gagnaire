package utt.fr.rglb.main.java.game.model;

import java.io.Serializable;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.controller.CardsController;
import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.player.controller.AbstractPlayerController;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.model.PlayerTeam;
import utt.fr.rglb.main.java.view.AbstractView;

public abstract class AbstractGameModel implements Serializable {
	protected static final long serialVersionUID = 1L;
	protected CardsController cardsController;
	protected GameRule gameRule;

	public AbstractGameModel(AbstractView view) {
		this.cardsController = new CardsController(view);
	}
	
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
	public void resetCards() {
		this.cardsController.resetCards();
	}

	/* ========================================= GAME LOGIC ========================================= */

	/**
	 * Méthode permettant de permettre à un joueur de jouer son tour (en lui permettant de piocher si besoin, ou de passer son tour s'il n'a pas de cartes jouables)
	 * @return PlayerControllerBean Objet encapsulant le joueur en cours
	 */
	public abstract PlayerControllerBean playOneTurn();

	/**
	 * Méthode permettant à un joueur de choisir une carte depuis sa main
	 * @param gameModelbean Carte dernièrement jouée (celle sur le talon, donc carte de référence)
	 * @param currentPlayer Joueur actuel
	 */
	protected abstract void chooseCardAndPlayIt(CardsModelBean gameModelbean, AbstractPlayerController currentPlayer);

	/* ========================================= EFFECTS ========================================= */

	/**
	 * Méthode permettant de tirer la 1ère carte de la pioche et d'appliquer son effet
	 */
	public void drawFirstCardAndApplyItsEffect() {
		GameFlag effect = this.cardsController.applyEffectFromAnotherFirstCard();
		triggerEffectFromFirstCard(effect);
	}

	/**
	 * Méthode permettant d'appliquer l'effet en provenance de la 1ère carte retournée du talon
	 * Le comportement est légérement différent : si la 1ère carte tirée est un +4, une nouvelle carte est tirée
	 * Dans tous les autres cas, l'effet est appliqué normalement
	 * @param effectFromFirstCard Effet provenant de la 1ère carte (initialisation)
	 */
	protected abstract void triggerEffectFromFirstCard(GameFlag effectFromFirstCard);

	/**
	 * Méthode permettant d'appliquer l'effet en provenance de la carte jouée
	 * Cette méthode prend en compte le mode de jeu, définissant des comportements particuliers au besoin
	 * @param currentPlayer Joueur venant de poser la carte spéciale
	 */
	protected void triggerEffect(AbstractPlayerController currentPlayer) {
		if(this.gameRule.indicatesTwoPlayersMode()) {
			triggerEffectWithOnlyTwoPlayers(currentPlayer);
		} else {
			triggerEffectWithMoreThanTwoPlayers(currentPlayer);
		}
	}

	/**
	 * Méthode permettant d'appliquer les effets des cartes spéciales sur la partie --Cas d'une partie à 2 joueurs
	 * @param currentPlayer Joueur en cours (celui devant éventuellement choisir une couleur)
	 */
	protected void triggerEffectWithOnlyTwoPlayers(AbstractPlayerController currentPlayer){
		if(this.gameRule.shouldReverseTurn()) {
			this.triggerCycleSilently();
		} else {
			triggerEffect(currentPlayer);
		}
	}

	/**
	 * Méthode permettant d'appliquer les effets des cartes spéciales sur la partie --Cas d'une partie classique
	 * @param currentPlayer Joueur en cours (celui devant éventuellement choisir une couleur)
	 */
	protected abstract void triggerEffectWithMoreThanTwoPlayers(AbstractPlayerController currentPlayer);

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
	 * Méthode permettant d'empêcher le joueur suivant de jouer son tour
	 */
	protected abstract void triggerSkipNextPlayer();

	/**
	 * Méthode permettant au joueur en paramètre de choisir une couleur
	 * @param currentPlayer Joueur devant désigner une couleur
	 * @param isRelatedToPlus4 <code>TRUE</code> si selection d'une couleur est due au jeu d'un plus 4, <code>FALSE</code> sinon
	 */
	protected abstract void triggerColorPicking(AbstractPlayerController currentPlayer, boolean isRelatedToPlus4);

	/**
	 * Méthode permettant de forcer un joueur à piocher un nombre donné de cartes
	 * @param cardsToDraw int représentant le nombre de cartes à piocher
	 * @param targetedPlayer Joueur devant piocher lesdites cartes
	 */
	protected void triggerPlusX(int cardsToDraw, AbstractPlayerController targetedPlayer) {
		Collection<Card> cards = this.cardsController.drawCards(cardsToDraw);
		targetedPlayer.isForcedToPickUpCards(cards);
	}

	/**
	 * Méthode permettant de forcer un joueur à piocher un nombre donné de cartes avec gestion du bluff par couleurs appropriées
	 * @param cardsToDraw int représentant le nombre de cartes à piocher
	 * @param targetedPlayer Joueur devant piocher lesdites cartes
	 * @param wasLegit <code>TRUE</code> si le jeu de la carte était légitime, <code>FALSE</code> sinon
	 */
	protected void triggerPlusX(int cardsToDraw, AbstractPlayerController targetedPlayer, boolean wasLegit) {
		Collection<Card> cards = this.cardsController.drawCards(cardsToDraw);
		if(wasLegit) {
			targetedPlayer.isForcedToPickUpCardsLegitCase(cards);
		} else {
			targetedPlayer.isForcedToPickUpCardsBluffCase(cards);
		}
	}

	/**
	 * Méthode permettant de donner au joueur suivant l'opportunité de contrer un éventuel bluff
	 * @param nextPlayer Joueur suivant 
	 * @return <code>TRUE</code> si le joueur suivant souhaite vérifier le jeu de l'adversaire, <code>FALSE</code> sinon
	 */
	protected abstract boolean triggerBluffing(AbstractPlayerController nextPlayer);

	/**
	 * Méthode permettant de donner une pénalité au joueur donné
	 * @param currentPlayer Joueur devant être pénalisé
	 * @param cardCount Nombre de cartes devant être piochées
	 */
	public void giveCardPenaltyTo(AbstractPlayerController currentPlayer, int cardCount) {
		Collection<Card> cardPenalty = this.cardsController.drawCards(cardCount);
		currentPlayer.isForcedToPickUpCards(cardPenalty);
	}

	/**
	 * Méthode permettant de donner une pénalité au joueur donné
	 * @param player Joueur devant être pénalisé (encapsulée dans un PlayerControllerBean)
	 * @param cardCount Nombre de cartes devant être piochées
	 */
	public void giveCardPenaltyTo(PlayerControllerBean player, int cardCount) {
		Collection<Card> cardPenalty = this.cardsController.drawCards(cardCount);
		player.isForcedToPickUpCards(cardPenalty);
	}

	/**
	 * Méthode permettant d'obtenir une réponse valide
	 * @return La réponse de l'utilisateur
	 */
	public abstract int getValidChoiceAnswer();

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
	public boolean indicatesTeamPlayScoring() {
		return this.gameRule.indicatesTeamPlayScoring();
	}

	/**
	 * Méthode permettant de récuperer l'équipe à laquelle appartient le joueur donné
	 * @param winningPlayer Joueur victorieux dont on souhaite connaitre l'équipe
	 * @return L'équipe à laquelle appartient le joueur
	 */
	public abstract PlayerTeam findWinningTeam(PlayerControllerBean winningPlayer);
}
