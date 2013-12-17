package utt.fr.rglb.main.java.game.model;

import java.io.Serializable;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.controller.CardsController;
import utt.fr.rglb.main.java.cards.model.GameModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.console.model.InputReader;
import utt.fr.rglb.main.java.console.view.View;
import utt.fr.rglb.main.java.dao.ConfigurationReader;
import utt.fr.rglb.main.java.main.ServerException;
import utt.fr.rglb.main.java.player.controller.PlayerController;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.model.PlayerTeam;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;
import utt.fr.rglb.main.java.turns.controller.TurnController;


/**
 * Classe dont le rôle est de gérer le jeu en faisant appel à des méthodes de haut niveau
 */
public class GameModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private TurnController turnController;
	private CardsController cardsController;
	private InputReader inputReader;
	private GameRule gameRule;
	
	/**
	 * Constructeur de GameModel
	 * @param consoleView Vue permettant d'afficher des informations
	 */
	public GameModel(View consoleView) {
		this.turnController = new TurnController(consoleView);
		this.cardsController = new CardsController(consoleView);
		this.inputReader = new InputReader(consoleView);
	}

	/* ========================================= INITIALIZING ========================================= */

	/**
	 * Méthode permettant d'initialiser les paramètres (nombre de joueurs, nom de chacun des joueurs)
	 */
	public void initializeGameSettings() {
		boolean configurationFileUsageRequired = this.inputReader.askForConfigurationFileUsage();
		PlayersToCreate playersAwaitingCreation = null;
		if(configurationFileUsageRequired) {
			ConfigurationReader configurationReader = new ConfigurationReader();
			playersAwaitingCreation = configurationReader.readConfigurationAt("dist/config.ini");
		} else {
			int playerNumber = this.inputReader.getValidPlayerNumber();
			playersAwaitingCreation = this.inputReader.getAllPlayerNames(playerNumber);
		}
		this.turnController.createPlayersFrom(playersAwaitingCreation);
		this.gameRule = this.inputReader.askForGameMode(playersAwaitingCreation.size());
		if(this.gameRule.indicatesTeamPlayScoring()) {
			this.turnController.splitPlayersIntoTeams();
			this.turnController.displayTeams();
		}
		
	}

	/**
	 * Méthode permettant d'initialiser la main de chaque joueur (leur donne tous 7 cartes)
	 */
	public void initializeCardsAndHands() {
		this.cardsController.resetCards();
		for(int i=0; i<this.turnController.getNumberOfPlayers(); i++) {
			Collection<Card> cardsDrawn = this.cardsController.drawCards(7);
			this.turnController.giveCardsToNextPlayer(cardsDrawn);
		}
	}
	
	/**
	 * Méthode permettant de tout ré-initialiser (en cas de démarrage d'une nouvelle PARTIE)
	 */
	public void resetEverything() {
		this.cardsController.resetCards();
		this.turnController.resetTurn();
		this.gameRule.resetFlag();
	}
	
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
	public PlayerControllerBean playOneTurn() {
		GameModelBean references = this.cardsController.getRequiredReferences();
		PlayerController currentPlayer = this.turnController.findNextPlayer();
		if(currentPlayer.hasAtLeastOnePlayableCard(references)) {
			chooseCardAndPlayIt(references, currentPlayer);
		} else {
			Card cardDrawn = this.cardsController.drawOneCard();
			currentPlayer.pickUpOneCard(cardDrawn);
			if(currentPlayer.hasAtLeastOnePlayableCard(references)) {
				chooseCardAndPlayIt(references, currentPlayer);
			} else {
				currentPlayer.unableToPlayThisTurn(references);
			}
		}
		triggerEffect(currentPlayer);
		return new PlayerControllerBean(currentPlayer);
	}

	/**
	 * Méthode privée permettant à un joueur de choisir une carte depuis sa main
	 * @param gameModelbean Carte dernièrement jouée (celle sur le talon, donc carte de référence)
	 * @param currentPlayer Joueur actuel
	 */
	private void chooseCardAndPlayIt(GameModelBean gameModelbean, PlayerController currentPlayer) {
		Card cardChosen = currentPlayer.startTurn(inputReader,gameModelbean);
		GameFlag flag = this.cardsController.playCard(cardChosen);
		this.gameRule.setFlag(flag);
	}

	/* ========================================= EFFECTS ========================================= */
	
	/**
	 * Méthode permettant de tirer la 1ère carte de la pioche et d'appliquer son effet
	 */
	public void drawFirstCardAndApplyItsEffect() {
		GameFlag effect = this.cardsController.applyEffectFromAnotherFirstCard();
		triggerEffectFromFirstCard(effect);
	}
	
	/**
	 * Méthode privée permettant d'appliquer l'effet en provenance de la 1ère carte retournée du talon
	 * Le comportement est légérement différent : si la 1ère carte tirée est un +4, une nouvelle carte est tirée
	 * Dans tous les autres cas, l'effet est appliqué normalement
	 * @param effectFromFirstCard Effet provenant de la 1ère carte (initialisation)
	 */
	private void triggerEffectFromFirstCard(GameFlag effectFromFirstCard) {
		if(effectFromFirstCard.equals(GameFlag.PLUS_FOUR)) {
			drawFirstCardAndApplyItsEffect();
		} else {
			PlayerController nextPlayer = this.turnController.findNextPlayerWithoutChangingCurrentPlayer();
			triggerEffect(nextPlayer);
		}
		this.turnController.resetPlayerIndex();
	}
	
	/**
	 * Méthode privée permettant d'appliquer l'effet en provenance de la carte jouée
	 * Cette méthode prend en compte le mode de jeu, définissant des comportements particuliers au besoin 
	 * @param currentPlayer Joueur venant de poser la carte spéciale
	 */
	private void triggerEffect(PlayerController currentPlayer) {
		if(this.gameRule.indicatesTwoPlayersMode()) {
			triggerEffectWithOnlyTwoPlayers(currentPlayer);
		} else {
			triggerEffectWithMoreThanTwoPlayers(currentPlayer);
		}
	}
	
	/**
	 * Méthode privée permettant d'appliquer les effets des cartes spéciales sur la partie --Cas d'une partie à 2 joueurs
	 * @param currentPlayer Joueur en cours (celui devant éventuellement choisir une couleur)
	 */
	private void triggerEffectWithOnlyTwoPlayers(PlayerController currentPlayer) {
		if(this.gameRule.shouldReverseTurn()) {
			this.triggerCycleSilently();
		} else {
			triggerEffect(currentPlayer);
		}
	}

	/**
	 * Méthode privée permettant d'appliquer les effets des cartes spéciales sur la partie --Cas d'une partie classique
	 * @param currentPlayer Joueur en cours (celui devant éventuellement choisir une couleur)
	 */
	private void triggerEffectWithMoreThanTwoPlayers(PlayerController currentPlayer) {
		if(this.gameRule.shouldReverseTurn()) {
			triggerReverseCurrentOrder();
		} else if(this.gameRule.shouldSkipNextPlayerTurn()) {
			triggerSkipNextPlayer();
		} else if(this.gameRule.shouldPickColor()) {
			triggerColorPicking(currentPlayer);
		} else if(this.gameRule.shouldGivePlus2CardPenalty()) {
			PlayerController nextPlayer = this.turnController.findNextPlayerWithoutChangingCurrentPlayer();
			triggerPlusX(2,nextPlayer);
		} else if(this.gameRule.shouldGivePlus4CardPenalty()) {
			PlayerController nextPlayer = this.turnController.findNextPlayerWithoutChangingCurrentPlayer();
			triggerPlusX(4,nextPlayer);
			triggerColorPicking(currentPlayer);
		} 
	}
	
	/* ========================================= EFFECTS - BASIS ========================================= */
	
	/**
	 * Méthode privée permettant de changer l'ordre du jeu
	 */
	private void triggerReverseCurrentOrder() {
		this.turnController.reverseCurrentOrder();
	}
	
	/**
	 * Méthode permettant à un même joueur de jouer plusieurs cartes d'affilé
	 */
	private void triggerCycleSilently() {
		this.turnController.cycleSilently();
	}
	
	/**
	 * Méthode privée permettant d'empêcher le joueur suivant de jouer son tour
	 */
	private void triggerSkipNextPlayer() {
		this.turnController.skipNextPlayer();
	}
	
	/**
	 * Méthode privée permettant au joueur en paramètre de choisir une couleur
	 * @param currentPlayer Joueur devant désigner une couleur
	 */
	private void triggerColorPicking(PlayerController currentPlayer) {
		Color chosenColor = currentPlayer.hasToChooseColor(this.inputReader);
		this.cardsController.setGlobalColor(chosenColor);
	}
	
	/**
	 * Méthode privée permettant de forcer un joueur à piocher un nombre donné de cartes
	 * @param cardsToDraw int représentant le nombre de cartes à piocher
	 * @param targetedPlayer Joueur devant piocher lesdites cartes
	 */
	private void triggerPlusX(int cardsToDraw, PlayerController targetedPlayer) {
		Collection<Card> cards = this.cardsController.drawCards(cardsToDraw);
		targetedPlayer.isForcedToPickUpCards(cards);
	}

	/**
	 * Méthode permettant de donner une pénalité au joueur donné
	 * @param currentPlayer Joueur devant être pénalisé
	 * @param cardCount Nombre de cartes devant être piochées
	 */
	public void giveCardPenaltyTo(PlayerController currentPlayer, int cardCount) {
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
	public int getValidChoiceAnswer() {
		return this.inputReader.getValidAnswerFromDualChoice();
	}
	
	/* ========================================= SCORE ========================================= */
	
	/**
	 * Méthode permettant de calculer les scores pour le(s) joueur(s) victorieux
	 * @param gameWinner Joueur ayant remporté le round
	 * @return <code>TRUE</code> si le joueur a gagné la partie, <code>FALSE</code> sinon
	 */
	public boolean computeScores(PlayerControllerBean gameWinner) {
		boolean someoneOrOneTeamWon = false;
		if(this.gameRule.indicatesTeamPlayScoring()) {
			someoneOrOneTeamWon = this.turnController.computeTeamEndOfTurn(gameWinner);
			this.turnController.displayTeamTotalScore();
		} else {
			someoneOrOneTeamWon = this.turnController.computeIndividualEndOfTurn(gameWinner);
			this.turnController.displayIndividualTotalScore();
		}
		waitForScoreDisplay();
		return someoneOrOneTeamWon;
	}
	
	/**
	 * Méthode permettant de calculer le score du joueur gagnant à partir des cartes des autres participants
	 * @param gameWinner Joueur venant de remporter le round
	 * @return <code>TRUE</code> si le joueur a gagné la partie (s'il a plus de 500 points), <code>FALSE</code> sinon
	 */
	public boolean computeIndividualScore(PlayerControllerBean gameWinner) {
		boolean playerWon = this.turnController.computeIndividualEndOfTurn(gameWinner);
		this.turnController.displayIndividualTotalScore();
		waitForScoreDisplay();
		return playerWon;
	}

	/**
	 * Méthode permettant de calculer le score de l'équipe gagnante à partir des cartes des participants des autres équipes
	 * @param gameWinner Joueur venant de remporter le round
	 * @return <code>TRUE</code> si le l'équipe a gagné la partie (si elle a plus de 500 points), <code>FALSE</code> sinon
	 */
	public boolean computeTeamScore(PlayerControllerBean gameWinner) {
		boolean playerWon = this.turnController.computeTeamEndOfTurn(gameWinner);
		this.turnController.displayTeamTotalScore();
		waitForScoreDisplay();
		return playerWon;
	}
	
	/**
	 * Méthode permettant de stopper le fonctionnement du programme pendant 2.5 secondes
	 */
	private void waitForScoreDisplay() {
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			throw new ServerException("[ERROR] Something went wrong while waiting during score display",e);
		}
	}
	
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
	public PlayerTeam findWinningTeam(PlayerControllerBean winningPlayer) {
		return this.turnController.findWinningTeam(winningPlayer);
	}
}