package utt.fr.rglb.main.java.game.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.game.model.GameFlag;
import utt.fr.rglb.main.java.game.model.GameModelGraphicsOriented;
import utt.fr.rglb.main.java.game.model.GameRule;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.controller.PlayerControllerGraphicsOriented;
import utt.fr.rglb.main.java.player.model.PlayerTeam;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;
import utt.fr.rglb.main.java.view.graphics.GraphicsView;
import utt.fr.rglb.main.java.view.graphics.fxml.FXMLControllerException;
import utt.fr.rglb.main.java.view.graphics.fxml.FXMLControllerGameScreen;

/**
 * Classe permettant de gérer l'ensemble de la partie </br>
 * Version graphique
 */
public class GameControllerGraphicsOriented extends AbstractGameController {
	private static final Logger log = LoggerFactory.getLogger(GameControllerGraphicsOriented.class);
	private static final long serialVersionUID = 1L;
	protected GameModelGraphicsOriented gameModel;
	protected FXMLControllerGameScreen fxmlController;
	protected GraphicsView view;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur de GameControllerGraphicsOriented
	 * @param view Vue permettant l'affichage d'informations
	 */
	public GameControllerGraphicsOriented(GraphicsView view) {
		this.view = view;
		this.gameModel = new GameModelGraphicsOriented(view);
	}

	/**
	 * Méthode permettant de définir quel est le gestionnaire FXML utilisé, après qu'il ait été créé
	 * @param fxmlController Gestionnaire FXML associé
	 */
	public void setCurrentFXMLController(FXMLControllerGameScreen fxmlController) {
		this.fxmlController = fxmlController;
		this.gameModel.setCurrentFXMLController(fxmlController);
	}

	/* ========================================= INITIALIZING ========================================= */

	@Override
	public void startAnotherGame() {
		this.view.displayWelcomeScreen();
	}

	/**
	 * Méthode permettant de créer tous les jouers à partir des données fournies
	 * @param choosenRules Règles choisies
	 * @param playersToCreate Objet encapsulant les données de tous les joueurs
	 * @param scene Scene de la vue graphique (permet le changement de scene)
	 */
	public void createGameFrom(GameRule choosenRules, PlayersToCreate playersToCreate, Scene scene) {
		this.gameModel.initializeGameSettings(choosenRules,playersToCreate,scene);
		log.info("Game successfully initialized, setting up first round");
		startNewRound();
	}

	/**
	 Méthode permettant de démarrer une partie, en définissant quel est le gestionnaire FXML utilisé pour la coordination avec l'interface graphique
	 * @param fxmlControllerGameScreen Gestionnaire FXML associé
	 */
	public void startGame(FXMLControllerGameScreen fxmlControllerGameScreen) {
		setCurrentFXMLController(fxmlControllerGameScreen);
		PlayerControllerBean winningPlayer = cycleUntilSomeoneWins();
		handleWinEvent(winningPlayer);
	}

	/**
	 * Méthode permettant de récupérer les données de tous les joueurs depuis le fichier de configuration
	 * @return
	 */
	public PlayersToCreate retrievePlayerDataFromFile() {
		return this.gameModel.retrievePlayerDataFromFile();
	}

	/**
	 * Méthode permettant de récupérer toutes les équipes de joueurs créées
	 * @return Une collection contenant toutes les équipes
	 */
	public Map<Integer, PlayerTeam> getAllTeams() {
		return this.gameModel.getAllTeams();		
	}	

	/* ========================================= GAME LOGIC ========================================= */

	@Override
	protected PlayerControllerBean cycleUntilSomeoneWins() {
		PlayerControllerBean gameWinner = new PlayerControllerBean();
		//TODO Unfinished METHOD
		gameWinner = playOneRound();
		return gameWinner;
	}

	@Override
	protected PlayerControllerBean playOneRound() {
		startNewRound();
		PlayerControllerBean currentPlayer = new PlayerControllerBean();
		//TODO Unfinished METHOD
		return currentPlayer;
	}

	@Override
	protected void startNewRound() {
		log.info("--- Another round starting now ---");
		this.gameModel.initializeCardsAndHands();
	}

	@Override
	protected void handleMissingUnoAnnoucement(PlayerControllerBean roundWinner) {
		//TODO Unfinished METHOD
	}

	@Override
	protected void handleUnoAnnoucement(PlayerControllerBean roundWinner) {
		//TODO Unfinished METHOD
	}

	@Override
	protected void handleWinEvent(PlayerControllerBean winningPlayer) {
		//TODO Unfinished METHOD
	}

	/**
	 * Méthode permettant de récupérer la dernière carte ayant été jouée
	 * @return Card correspondant à la dernière carte ayant été jouée
	 */
	public Card showLastCardPlayed() {
		return this.gameModel.showLastCardPlayed();
	}

	/**
	 * Méthode permettant de récupérer l'ensemble des cartes en main de chaque joueur
	 * @return Une map contenant les collections de cartes associées à chaque joueur
	 */
	public Map<String, Collection<Card>> getAllCardsFromPlayers() {
		return this.gameModel.getAllCardsFromPlayers();
	}

	/**
	 * Méthode permettant de récupérer l'ensemble des joueurs
	 * @return Collection contenant l'ensemble des joueurs
	 */
	public List<PlayerControllerGraphicsOriented> getAllPlayers() {
		return this.gameModel.getAllPlayers();
	}

	/**
	 * Méthode permettant de supprimer les cartes de tous les joueurs
	 */
	public void removeCardsFromPlayers() {
		this.gameModel.removeCardsFromPlayers();
	}

	/**
	 * Méthode permettant de récupérer toutes les références associées au tour d'un joueur
	 * @return CardsModelBean contenant toutes les références
	 * @see CardsModelBean
	 */
	public CardsModelBean getReferences() {
		return this.gameModel.getReferences();
	}

	/**
	 * Méthode permettant de piocher une carte
	 * @return Carte ayant été piochée
	 */
	public Card drawOneCard() {
		return this.gameModel.drawOneCard();
	}

	/**
	 * Méthode permettant de jouer une carte </br>
	 * Affiche les scores si le joueur vient de jouer sa dernière carte
	 * @param chosenCard Carte jouée
	 * @param stillHasCards Booléen valant <code>TRUE</code> si le joueur a encore des cartes en main, <code>FALSE</code> sinon
	 * @param hasAnnouncedUno Booléen valant <code>TRUE</code> si le joueur a annoncé UNO, <code>FALSE</code> sinon
	 * @return L'état du jeu correspondant à l'éventuel déclechement d'effet
	 * @see GameFlag
	 */
	public GameFlag activePlayerChose(Card chosenCard, boolean stillHasCards, boolean hasAnnouncedUno) {
		if(stillHasCards) {
			return this.gameModel.activePlayerChose(chosenCard);
		} else {
			return this.fxmlController.displayScores();
		}
	}

	/**
	 * Méthode permetttant de gérer l'éventualité d'un joueur ne pouvant pas jouer de carte durant son tour
	 */
	public void activePlayerCannotPlay() {
		this.gameModel.playOneTurn(true);
	}

	/**
	 * Méthode permettant de démarrer un nouveau tour
	 * @param needsCardFlipping Booleén valant <code>TRUE</code> si les cartes du joueur ont besoin d'être retournées, <code>FALSE</code> sinon 
	 */
	public void playOneTurn(boolean needsCardFlipping) {
		this.gameModel.playOneTurn(needsCardFlipping);
	}

	/**
	 * Méthode permettant au joueur de choisir une couleur parmi celles disponibles
	 * @param color Couleur choisie par le joueur
	 */
	public void activePlayerChoseColor(Color color) {
		this.gameModel.activePlayerChoseColor(color);
	}

	/**
	 * Méthode permettant de récupérer l'index du joueur précédent
	 * @return int correspondant à l'index souhaité
	 */
	public int getIndexFromPreviousPlayer() {
		int index = this.gameModel.getIndexFromPreviousPlayer();
		return index;
	}

	/**
	 * Méthode permettant de récupérer l'effet de la 1ere carte (celle en provenance de la pioche) afin de l'appliquer plus tard
	 * @return L'état du jeu correspondant à l'éventuel déclechement d'effet
	 */
	public GameFlag retrieveEffectFromFirstCard() {
		return this.gameModel.triggerEffectFromFirstCard();
	}

	/**
	 * Méthode permettant d'appliquer l'effet en provenance de la 1ère carte
	 * @param gameFlag L'état du jeu correspondant à l'éventuel déclechement d'effet
	 */
	public void applyEffectFromFirstCard(GameFlag gameFlag) {
		this.gameModel.applyEffectFromFirstCard(gameFlag);
	}

	/**
	 * Méthode permettant l'affichage des scores individuels
	 * @return Map correspondant à l'ensemble des scores des différents joueurs
	 */
	public Map<String, Integer> displayIndividualTotalScore() {
		return this.gameModel.displayIndividualTotalScore();
	}

	/**
	 * Méthode permettant de ré-initialiser toutes les cartes et de démarrer un nouveau round
	 * @param scene Scene associée à la vue
	 */
	public void resetAllCardsAndStartNewRound(Scene scene) {
		this.gameModel.resetCards();
		startNewRound();
		try {
			log.info("Loading JavaFX setup screen from file : \"game2players.fxml\"");
			Parent root= FXMLLoader.load(getClass().getResource("/utt/fr/rglb/main/ressources/fxml/game2players.fxml"));
			scene.setRoot(root);
		} catch (IOException e1) {
			throw new FXMLControllerException("[ERROR] While trying to load screen from \"game2players.fxml\"",e1);
		}
	}
}
