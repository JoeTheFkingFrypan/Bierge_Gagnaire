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
 * Classe dont le r�le est de g�rer le jeu en faisant appel � des m�thodes de haut niveau
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
	 * M�thode permettant d'initialiser les param�tres (nombre de joueurs, nom de chacun des joueurs)
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
	 * M�thode permettant d'initialiser la main de chaque joueur (leur donne tous 7 cartes)
	 */
	public void initializeCardsAndHands() {
		this.cardsController.resetCards();
		for(int i=0; i<this.turnController.getNumberOfPlayers(); i++) {
			Collection<Card> cardsDrawn = this.cardsController.drawCards(7);
			this.turnController.giveCardsToNextPlayer(cardsDrawn);
		}
	}
	
	/**
	 * M�thode permettant de tout r�-initialiser (en cas de d�marrage d'une nouvelle PARTIE)
	 */
	public void resetEverything() {
		this.cardsController.resetCards();
		this.turnController.resetTurn();
		this.gameRule.resetFlag();
	}
	
	/**
	 * M�thode permettant de r�initialiser les cartes (en cas de d�marrage d'un nouveau round)
	 */
	public void resetCards() {
		this.cardsController.resetCards();
	}
	
	/* ========================================= GAME LOGIC ========================================= */
	
	/**
	 * M�thode permettant de permettre � un joueur de jouer son tour (en lui permettant de piocher si besoin, ou de passer son tour s'il n'a pas de cartes jouables)
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
	 * M�thode priv�e permettant � un joueur de choisir une carte depuis sa main
	 * @param gameModelbean Carte derni�rement jou�e (celle sur le talon, donc carte de r�f�rence)
	 * @param currentPlayer Joueur actuel
	 */
	private void chooseCardAndPlayIt(GameModelBean gameModelbean, PlayerController currentPlayer) {
		Card cardChosen = currentPlayer.startTurn(inputReader,gameModelbean);
		GameFlag flag = this.cardsController.playCard(cardChosen);
		this.gameRule.setFlag(flag);
	}

	/* ========================================= EFFECTS ========================================= */
	
	/**
	 * M�thode permettant de tirer la 1�re carte de la pioche et d'appliquer son effet
	 */
	public void drawFirstCardAndApplyItsEffect() {
		GameFlag effect = this.cardsController.applyEffectFromAnotherFirstCard();
		triggerEffectFromFirstCard(effect);
	}
	
	/**
	 * M�thode priv�e permettant d'appliquer l'effet en provenance de la 1�re carte retourn�e du talon
	 * Le comportement est l�g�rement diff�rent : si la 1�re carte tir�e est un +4, une nouvelle carte est tir�e
	 * Dans tous les autres cas, l'effet est appliqu� normalement
	 * @param effectFromFirstCard Effet provenant de la 1�re carte (initialisation)
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
	 * M�thode priv�e permettant d'appliquer l'effet en provenance de la carte jou�e
	 * Cette m�thode prend en compte le mode de jeu, d�finissant des comportements particuliers au besoin 
	 * @param currentPlayer Joueur venant de poser la carte sp�ciale
	 */
	private void triggerEffect(PlayerController currentPlayer) {
		if(this.gameRule.indicatesTwoPlayersMode()) {
			triggerEffectWithOnlyTwoPlayers(currentPlayer);
		} else {
			triggerEffectWithMoreThanTwoPlayers(currentPlayer);
		}
	}
	
	/**
	 * M�thode priv�e permettant d'appliquer les effets des cartes sp�ciales sur la partie --Cas d'une partie � 2 joueurs
	 * @param currentPlayer Joueur en cours (celui devant �ventuellement choisir une couleur)
	 */
	private void triggerEffectWithOnlyTwoPlayers(PlayerController currentPlayer) {
		if(this.gameRule.shouldReverseTurn()) {
			this.triggerCycleSilently();
		} else {
			triggerEffect(currentPlayer);
		}
	}

	/**
	 * M�thode priv�e permettant d'appliquer les effets des cartes sp�ciales sur la partie --Cas d'une partie classique
	 * @param currentPlayer Joueur en cours (celui devant �ventuellement choisir une couleur)
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
	 * M�thode priv�e permettant de changer l'ordre du jeu
	 */
	private void triggerReverseCurrentOrder() {
		this.turnController.reverseCurrentOrder();
	}
	
	/**
	 * M�thode permettant � un m�me joueur de jouer plusieurs cartes d'affil�
	 */
	private void triggerCycleSilently() {
		this.turnController.cycleSilently();
	}
	
	/**
	 * M�thode priv�e permettant d'emp�cher le joueur suivant de jouer son tour
	 */
	private void triggerSkipNextPlayer() {
		this.turnController.skipNextPlayer();
	}
	
	/**
	 * M�thode priv�e permettant au joueur en param�tre de choisir une couleur
	 * @param currentPlayer Joueur devant d�signer une couleur
	 */
	private void triggerColorPicking(PlayerController currentPlayer) {
		Color chosenColor = currentPlayer.hasToChooseColor(this.inputReader);
		this.cardsController.setGlobalColor(chosenColor);
	}
	
	/**
	 * M�thode priv�e permettant de forcer un joueur � piocher un nombre donn� de cartes
	 * @param cardsToDraw int repr�sentant le nombre de cartes � piocher
	 * @param targetedPlayer Joueur devant piocher lesdites cartes
	 */
	private void triggerPlusX(int cardsToDraw, PlayerController targetedPlayer) {
		Collection<Card> cards = this.cardsController.drawCards(cardsToDraw);
		targetedPlayer.isForcedToPickUpCards(cards);
	}

	/**
	 * M�thode permettant de donner une p�nalit� au joueur donn�
	 * @param currentPlayer Joueur devant �tre p�nalis�
	 * @param cardCount Nombre de cartes devant �tre pioch�es
	 */
	public void giveCardPenaltyTo(PlayerController currentPlayer, int cardCount) {
		Collection<Card> cardPenalty = this.cardsController.drawCards(cardCount);
		currentPlayer.isForcedToPickUpCards(cardPenalty);
	}

	/**
	 * M�thode permettant de donner une p�nalit� au joueur donn�
	 * @param player Joueur devant �tre p�nalis� (encapsul�e dans un PlayerControllerBean)
	 * @param cardCount Nombre de cartes devant �tre pioch�es
	 */
 	public void giveCardPenaltyTo(PlayerControllerBean player, int cardCount) {
		Collection<Card> cardPenalty = this.cardsController.drawCards(cardCount);
		player.isForcedToPickUpCards(cardPenalty);
	}
	
	/**
	 * M�thode permettant d'obtenir une r�ponse valide 
	 * @return La r�ponse de l'utilisateur
	 */
	public int getValidChoiceAnswer() {
		return this.inputReader.getValidAnswerFromDualChoice();
	}
	
	/* ========================================= SCORE ========================================= */
	
	/**
	 * M�thode permettant de calculer les scores pour le(s) joueur(s) victorieux
	 * @param gameWinner Joueur ayant remport� le round
	 * @return <code>TRUE</code> si le joueur a gagn� la partie, <code>FALSE</code> sinon
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
	 * M�thode permettant de calculer le score du joueur gagnant � partir des cartes des autres participants
	 * @param gameWinner Joueur venant de remporter le round
	 * @return <code>TRUE</code> si le joueur a gagn� la partie (s'il a plus de 500 points), <code>FALSE</code> sinon
	 */
	public boolean computeIndividualScore(PlayerControllerBean gameWinner) {
		boolean playerWon = this.turnController.computeIndividualEndOfTurn(gameWinner);
		this.turnController.displayIndividualTotalScore();
		waitForScoreDisplay();
		return playerWon;
	}

	/**
	 * M�thode permettant de calculer le score de l'�quipe gagnante � partir des cartes des participants des autres �quipes
	 * @param gameWinner Joueur venant de remporter le round
	 * @return <code>TRUE</code> si le l'�quipe a gagn� la partie (si elle a plus de 500 points), <code>FALSE</code> sinon
	 */
	public boolean computeTeamScore(PlayerControllerBean gameWinner) {
		boolean playerWon = this.turnController.computeTeamEndOfTurn(gameWinner);
		this.turnController.displayTeamTotalScore();
		waitForScoreDisplay();
		return playerWon;
	}
	
	/**
	 * M�thode permettant de stopper le fonctionnement du programme pendant 2.5 secondes
	 */
	private void waitForScoreDisplay() {
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			throw new ServerException("[ERROR] Something went wrong while waiting during score display",e);
		}
	}
	
	/**
	 * M�thode permettant de d�terminer si le mode de jeu choisi est celui � deux joueurs
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean indicatesTeamPlayScoring() {
		return this.gameRule.indicatesTeamPlayScoring();
	}

	/**
	 * M�thode permettant de r�cuperer l'�quipe � laquelle appartient le joueur donn�
	 * @param winningPlayer Joueur victorieux dont on souhaite connaitre l'�quipe
	 * @return L'�quipe � laquelle appartient le joueur
	 */
	public PlayerTeam findWinningTeam(PlayerControllerBean winningPlayer) {
		return this.turnController.findWinningTeam(winningPlayer);
	}
}