package utt.fr.rglb.main.java.game.controller;

import java.util.Collection;
import java.util.Map;

import javafx.scene.Scene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.game.model.GameModelGraphicsOriented;
import utt.fr.rglb.main.java.game.model.GameRule;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.model.PlayerTeam;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;
import utt.fr.rglb.main.java.view.graphics.GraphicsView;
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
		log.info("Players successfully created");
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
		PlayerControllerBean roundWinner = new PlayerControllerBean();
		/*while(roundWinner.stillHasCards()) {
			roundWinner = this.gameModel.playOneTurn();
			if(roundWinner.hasAnnouncedUno()) {
				handleUnoAnnoucement(roundWinner);
			} else if(roundWinner.hasNoCardAndForgotToAnnounceUno()) {
				handleMissingUnoAnnoucement(roundWinner);
			}
		}*/
		return roundWinner;
	}

	@Override
	protected void startNewRound() {
		log.info("--- Another round starting now ---");
		this.gameModel.initializeCardsAndHands();
		this.gameModel.drawFirstCardAndApplyItsEffect();
		// TODO Auto-generated method stub
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

	public void backgroundLoadImages() {
		this.gameModel.backgroundLoadImages();
	}

	public Map<String, Collection<Card>> getAllCardsFromPlayers() {
		return this.gameModel.getAllCardsFromPlayers();
	}
}
