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

	@Override
	protected void resetEverything() {
		// TODO Auto-generated method stub
	}
	
	/* ========================================= GAME LOGIC ========================================= */

	@Override
	protected PlayerControllerBean cycleUntilSomeoneWins() {
		PlayerControllerBean gameWinner = new PlayerControllerBean();
		//while(gameWinner.hasNotWonTheGame()) {
			gameWinner = playOneRound();
			//this.gameModel.computeScores(gameWinner);
		//}
		return gameWinner;
	}

	@Override
	protected PlayerControllerBean playOneRound() {
		startNewRound();
		PlayerControllerBean currentPlayer = new PlayerControllerBean();
		//while(currentPlayer.stillHasCards()) {
			//this.gameModel.playOneTurn();
			/*if(currentPlayer.hasAnnouncedUno()) {
				handleUnoAnnoucement(currentPlayer);
			} else if(currentPlayer.hasNoCardAndForgotToAnnounceUno()) {
				handleMissingUnoAnnoucement(currentPlayer);
			}*/
		//}
		return currentPlayer;
	}
	
	@Override
	protected void startNewRound() {
		log.info("--- Another round starting now ---");
		this.gameModel.initializeCardsAndHands();
	}

	@Override
	protected void handleMissingUnoAnnoucement(PlayerControllerBean roundWinner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleUnoAnnoucement(PlayerControllerBean roundWinner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleWinEvent(PlayerControllerBean winningPlayer) {
		// TODO Auto-generated method stub
		
	}

	public Card retrieveImageFromLastCardPlayed() {
		return this.gameModel.retrieveImageFromLastCardPlayed();
	}

	public Map<String, Collection<Card>> getAllCardsFromPlayers() {
		return this.gameModel.getAllCardsFromPlayers();
	}

	public List<PlayerControllerGraphicsOriented> getAllPlayers() {
		return this.gameModel.getAllPlayers();
	}

	public void removeCardsFromPlayers() {
		this.gameModel.removeCardsFromPlayers();
	}

	public CardsModelBean getReferences() {
		return this.gameModel.getReferences();
	}

	public Card drawOneCard() {
		return this.gameModel.drawOneCard();
	}

	public GameFlag activePlayerChose(Card chosenCard, boolean stillHasCards, boolean hasAnnouncedUno) {
		if(stillHasCards) {
			return this.fxmlController.displayScores();
		} else {
			return this.gameModel.activePlayerChose(chosenCard);
		}
	}

	public void activePlayerCannotPlay() {
		this.gameModel.playOneTurn(true);
	}
	
	public void playOneTurn(boolean needsCardFlipping) {
		this.gameModel.playOneTurn(needsCardFlipping);
	}

	public void activePlayerChoseColor(Color color) {
		this.gameModel.activePlayerChoseColor(color);
	}
	
	public int getIndexFromPreviousPlayer() {
		int index = this.gameModel.getIndexFromPreviousPlayer();
		return index;
	}

	public GameFlag triggerEffectFromFirstCard() {
		return this.gameModel.triggerEffectFromFirstCard();
	}

	public void applyEffectFromFirstCard(GameFlag gameFlag) {
		this.gameModel.applyEffectFromFirstCard(gameFlag);
	}

	public Map<String, Integer> displayIndividualTotalScore() {
		return this.gameModel.displayIndividualTotalScore();
	}

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
