package utt.fr.rglb.main.java.game.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.Scene;
import utt.fr.rglb.main.java.cards.controller.CardsControllerGraphicsOriented;
import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.dao.ConfigurationReader;
import utt.fr.rglb.main.java.main.ServerException;
import utt.fr.rglb.main.java.player.controller.AbstractPlayerController;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.controller.PlayerControllerGraphicsOriented;
import utt.fr.rglb.main.java.player.model.PlayerTeam;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;
import utt.fr.rglb.main.java.turns.controller.TurnControllerGraphicsOriented;
import utt.fr.rglb.main.java.view.graphics.GraphicsReferences;
import utt.fr.rglb.main.java.view.graphics.GraphicsView;
import utt.fr.rglb.main.java.view.graphics.fxml.FXMLControllerGameScreen;

/**
 * Classe dont le rôle est de gérer le jeu en faisant appel à des méthodes de haut niveau </br>
 * Version graphique
 */
public class GameModelGraphicsOriented extends AbstractGameModel {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(GameModelGraphicsOriented.class);
	private TurnControllerGraphicsOriented turnController;
	private CardsControllerGraphicsOriented cardsController;
	private FXMLControllerGameScreen fxmlController;
	protected GameRule gameRule;

	/* ========================================= CONSTRUCTOR ========================================= */

	public GameModelGraphicsOriented(GraphicsView view) {
		this.turnController = new TurnControllerGraphicsOriented(view);
		this.cardsController = new CardsControllerGraphicsOriented(view);
	}

	/**
	 * Méthode permettant d'associer le nouveau gestionnaire FXML à utiliser pour la coordination avec la vue
	 * @param fxmlController Nouveau gestionnaire FXML à utiliser
	 */
	public void setCurrentFXMLController(FXMLControllerGameScreen fxmlController) {
		this.fxmlController = fxmlController;
	}

	/* ========================================= INITIALIZING ========================================= */

	/**
	 * Méthode permettant d'initialiser les paramètres (nombre de joueurs, nom de chacun des joueurs)
	 */
	public void initializeGameSettings(GameRule choosenRules, PlayersToCreate playersToCreate, Scene scene) {
		this.gameRule = choosenRules;
		this.turnController.createPlayersFrom(playersToCreate);
		if(this.gameRule.indicatesTeamPlayScoring()) {
			this.turnController.splitPlayersIntoTeams();
			this.turnController.displayTeams(scene);
		}
	}

	/**
	 * Méthode permettant de récupérer tous les joueurs à créer depuis le fichier de configuration
	 * @return PlayersToCreate Informations concernant les différents joueurs à créer 
	 */
	public PlayersToCreate retrievePlayerDataFromFile() {
		ConfigurationReader configurationReader = new ConfigurationReader();
		return configurationReader.readConfigurationAt("dist/config.ini");
	}

	@Override
	public void initializeCardsAndHands() {
		this.cardsController.resetCards();
		for(int i=0; i<this.turnController.getNumberOfPlayers(); i++) {
			Collection<Card> cardsDrawn = this.cardsController.drawCards(7);
			this.turnController.giveCardsToNextPlayer(cardsDrawn);
		}
	}

	@Override
	public void resetEverything() {
		this.cardsController.resetCards();
		this.turnController.resetTurn();
		this.gameRule.resetFlag();
	}

	@Override
	public void resetCards() {
		this.cardsController.resetCards();
		this.turnController.removeCardsFromPlayers();
		this.turnController.resetPlayerIndex();
	}

	/* ========================================= GAME LOGIC ========================================= */

	/**
	 * Méthode permettant de jouer un nouveau tour
	 * @param needsCardFlipping Booléen valant <code>TRUE</code> si les cartes doivent être retournées, <code>FALSE</code> sinon
	 */
	public void playOneTurn(boolean needsCardFlipping) {
		CardsModelBean references = this.cardsController.getReferences();
		PlayerControllerGraphicsOriented currentPlayer = this.turnController.findNextPlayer();
		GraphicsReferences graphicsReferences = new GraphicsReferences(references,this.turnController.getIndexFromActivePlayer());

		if(!currentPlayer.hasAtLeastOnePlayableCard(references)) {
			Card firstCardDrawn = this.cardsController.drawOneCard();
			if(references.isCompatibleWith(firstCardDrawn)) {
				graphicsReferences.setNeedOfDrawingOneTime(firstCardDrawn);
			} else {
				Card secondCardDrawn = this.cardsController.drawOneCard();
				graphicsReferences.setNeedOfDrawingTwoTimes(firstCardDrawn,secondCardDrawn);
			}
		}
		this.fxmlController.playOneTurn(graphicsReferences,needsCardFlipping);
	}

	@Override
	protected void chooseCardAndPlayIt(CardsModelBean gameModelbean, AbstractPlayerController currentPlayer) {}

	/* ========================================= EFFECTS ========================================= */

	/**
	 * Méthode permettant d'appliquer l'effet de la 1ère carte (ou de tirer une nouvelle carte, s'il s'agit d'un +4)
	 * @return GameFlag
	 */
	public GameFlag triggerEffectFromFirstCard() {
		GameFlag effectFromFirstCard = this.cardsController.applyEffectFromAnotherFirstCard();
		while(effectFromFirstCard.equals(GameFlag.PLUS_FOUR) || effectFromFirstCard.equals(GameFlag.PLUS_FOUR_BLUFFING)) {
			log.info("First card was a +4, drawing another one as starter");
			effectFromFirstCard = this.cardsController.applyEffectFromAnotherFirstCard();
		}
		return effectFromFirstCard;
	}

	/**
	 * Méthode permettant d'appliquer VISUELLEMENT l'effet en provenance de la 1ère carte
	 * @param effectFromFirstCard Effet en provenance de la 1ère carte
	 */
	public void applyEffectFromFirstCard(GameFlag effectFromFirstCard) {
		if(effectFromFirstCard.equals(GameFlag.SKIP)) {
			this.turnController.skipNextPlayer();
			int playerIndex = this.turnController.getIndexFromActivePlayer();
			this.fxmlController.displayMessage("First player skipped");
			this.fxmlController.setActivePlayer(playerIndex);
		} else if(effectFromFirstCard.equals(GameFlag.REVERSE)) {
			this.fxmlController.displayMessage("Turn order inverted");
		} else if(effectFromFirstCard.equals(GameFlag.COLOR_PICK)) {
			this.fxmlController.triggerColorPicking(false, false);
		} else if(effectFromFirstCard.equals(GameFlag.PLUS_TWO)) {
			Collection<Card> cards = this.cardsController.drawCards(2);
			this.fxmlController.displayMessage("Forced to draw 2 cards");
			this.fxmlController.addCardToPlayer(this.turnController.getIndexFromActivePlayer(),cards);
		}
	}

	/**
	 * Méthode permettant d'appliquer un effet
	 * @param gameFlag L'etat actuel du jeu suite au déclenchement d'un effet
	 */
	protected void triggerEffect(GameFlag gameFlag) {
		if(this.gameRule.indicatesTwoPlayersMode()) {
			triggerEffectWithOnlyTwoPlayers(gameFlag);
		} else {
			triggerEffectWithMoreThanTwoPlayers(gameFlag);
		}
	}

	/**
	 * Méthode permettant d'appliquer les effets différement s'il n'y a que 2 joueurs
	 * @param gameFlag L'etat actuel du jeu suite au déclenchement d'un effet
	 */
	protected void triggerEffectWithOnlyTwoPlayers(GameFlag gameFlag) {
		if(gameFlag.equals(GameFlag.REVERSE)) {
			triggerSkipNextPlayer("Your turn again");
		} else {
			triggerEffectWithMoreThanTwoPlayers(gameFlag);
		}
	}

	/**
	 * Méthode générale permettant d'appliquer les effets différement s'il y a 3 joueurs ou plus
	 * @param gameFlag L'etat actuel du jeu suite au déclenchement d'un effet
	 */
	protected void triggerEffectWithMoreThanTwoPlayers(GameFlag gameFlag) {
		if(gameFlag.equals(GameFlag.REVERSE)) {
			triggerReverseCurrentOrder();
		} else if(gameFlag.equals(GameFlag.SKIP)) {
			triggerSkipNextPlayer("Next player skipped");
		} else if(gameFlag.equals(GameFlag.COLOR_PICK)) {
			triggerColorPicking(false);
		} else if(gameFlag.equals(GameFlag.PLUS_TWO)) {
			triggerPlusX(2);
		} else if(gameFlag.equals(GameFlag.PLUS_FOUR)) {
			triggerBluffing(4,false,true);
		} else if(gameFlag.equals(GameFlag.PLUS_FOUR_BLUFFING)) {
			triggerBluffing(4,true,true);
		}
	}

	/* ========================================= EFFECTS - BASIS ========================================= */

	@Override
	protected void triggerReverseCurrentOrder() {
		this.turnController.reverseCurrentOrder();
		this.fxmlController.triggerReverseCurrentOrder("Turn order inverted");
	}

	@Override
	protected void triggerCycleSilently() {
		this.turnController.cycleSilently();
	}

	/**
	 * Méthode permettant d'empêcher le joueur suivant de jouer (avec affichage d'un message)
	 * @param message String correspondant au message à afficher
	 */
	protected void triggerSkipNextPlayer(String message) {
		this.turnController.skipNextPlayer();
		this.fxmlController.triggerSkipNextPlayer(message);
	}
	
	/**
	 * Méthode permettant au joueur de selectionner une couleur
	 * @param isRelatedToPlus4 Booléen valant <code>TRUE</code> si le choix est lié au jeu d'un +4, <code>FALSE</code> sinon
	 */
	protected void triggerColorPicking(boolean isRelatedToPlus4) {
		this.fxmlController.triggerColorPicking(isRelatedToPlus4,true);
	}

	/**
	 * Méthode permettant de forcer un joueur à piocher des cartes
	 * @param cardsToDraw Nombre de cartes à piocher
	 */
	protected void triggerPlusX(int cardsToDraw) {
		Collection<Card> cards = this.cardsController.drawCards(cardsToDraw);
		this.fxmlController.triggerPlusX(cards);
	}

	/**
	 * Méthode permettant de demander à un joueur s'il souhaite accuser le précédent joueur de bluff
	 * @param cardsToDraw Nombre de cartes à piocher
	 * @param isBluffing <code>TRUE</code> en cas de bluff, <code>FALSE</code> sinon
	 * @param applyPenaltyToNextPlayer <code>TRUE</code> si la pénalité est à appliquer au joueur suivant, <code>FALSE</code> sinon
	 */
	protected void triggerBluffing(int cardsToDraw, boolean isBluffing, boolean applyPenaltyToNextPlayer) {
		Collection<Card> cards = this.cardsController.drawCards(cardsToDraw);
		this.fxmlController.triggerBluffing(cards,isBluffing,applyPenaltyToNextPlayer);
	}

	/**
	 * Méthode permettant de donner une pénalité au joueur
	 * @param currentPlayer Joueur actuel
	 * @param cardCount Nombre de cartes à piocher
	 */
	public void giveCardPenaltyTo(AbstractPlayerController currentPlayer, int cardCount) {
		Collection<Card> cardPenalty = this.cardsController.drawCards(cardCount);
		currentPlayer.isForcedToPickUpCards(cardPenalty);
	}
	
	/**
	 * Méthode permettant de donner une pénalité à un joueur (surcharge)
	 * @param player Joueur actuel
	 * @param cardCount Nombre de cartes à piocher
	 */
	public void giveCardPenaltyTo(PlayerControllerBean player, int cardCount) {
		Collection<Card> cardPenalty = this.cardsController.drawCards(cardCount);
		player.isForcedToPickUpCards(cardPenalty);
	}

	/* ========================================= SCORE ========================================= */

	@Override
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

	@Override
	public boolean computeIndividualScore(PlayerControllerBean gameWinner) {
		boolean playerWon = this.turnController.computeIndividualEndOfTurn(gameWinner);
		this.turnController.displayIndividualTotalScore();
		waitForScoreDisplay();
		return playerWon;
	}

	@Override
	public boolean computeTeamScore(PlayerControllerBean gameWinner) {
		boolean playerWon = this.turnController.computeTeamEndOfTurn(gameWinner);
		this.turnController.displayTeamTotalScore();
		waitForScoreDisplay();
		return playerWon;
	}

	@Override
	protected  void waitForScoreDisplay() {
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			throw new ServerException("[ERROR] Something went wrong while waiting during score display",e);
		}
	}

	@Override
	public boolean indicatesTeamPlayScoring() {
		return this.gameRule.indicatesTeamPlayScoring();
	}

	@Override
	public PlayerTeam findWinningTeam(PlayerControllerBean winningPlayer) {
		return this.turnController.findWinningTeam(winningPlayer);
	}

	/**
	 * Méthode permettant de récupérer toutes les équipes de joueurs
	 * @return Map correspondant à l'ensemble des équipes
	 */
	public Map<Integer, PlayerTeam> getAllTeams() {
		return this.turnController.getAllTeams();
	}

	/**
	 * Méthode permettant de récupérer la dernière carte ayant été jouée
	 * @return Card correspondant à la dernière carte ayant été jouée
	 */
	public Card showLastCardPlayed() {
		return this.cardsController.showLastCardPlayed();
	}

	/**
	 * Méthode permettant de récupérer l'ensemble des cartes en main des joueurs
	 * @return Map correspondant à l'ensemble des cartes actuellement dans les mains des différents joueurs
	 */
	public Map<String, Collection<Card>> getAllCardsFromPlayers() {
		return this.turnController.getAllCardsFromPlayers();
	}

	/**
	 * Méthode permtettant de récupérer l'ensemble des joueurs de la partie
	 * @return Collection contenant l'ensemble des joueurs
	 */
	public List<PlayerControllerGraphicsOriented> getAllPlayers() {
		return this.turnController.getAllPlayers();
	}

	/**
	 * Méthode permettant de supprimer les cartes en main de chaque joueur
	 */
	public void removeCardsFromPlayers() {
		this.turnController.removeCardsFromPlayers();
	}

	/**
	 * Méthode permettant de récupérer les références associées au tour d'un joueur 
	 * @return CardsModelBean références associées au tour d'un joueur 
	 */
	public CardsModelBean getReferences() {
		return this.cardsController.getReferences();
	}

	/**
	 * Méthode permettant de piocher une carte
	 * @return Card piochée
	 */
	public Card drawOneCard() {
		return this.cardsController.drawOneCard();
	}

	/**
	 * Méthode permettant de jouer une carte </br>
	 * @param chosenCard Carte jouée
	 */
	public GameFlag activePlayerChose(Card chosenCard) {
		GameFlag gameFlag = this.cardsController.playCard(chosenCard);
		triggerEffect(gameFlag);
		return gameFlag;
	}

	/**
	 * Méthode permettant au joueur de choisir une couleur parmi celles disponibles
	 * @param color Couleur choisie par le joueur
	 */
	public void activePlayerChoseColor(Color color) {
		this.cardsController.setGlobalColor(color);
	}

	/**
	 * Méthode permettant de récupérer l'index du joueur précédent
	 * @return int correspondant à l'index souhaité
	 */
	public int getIndexFromPreviousPlayer() {
		return this.turnController.getIndexFromPreviousPlayer();
	}

	/**
	 * Méthode permettant d'afficher les scores individuels
	 * @return Map correspondant au score total de chaque joueur
	 */
	public Map<String, Integer> displayIndividualTotalScore() {
		return this.turnController.displayIndividualTotalScore();
	}
}
