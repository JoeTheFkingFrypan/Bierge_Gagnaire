package utt.fr.rglb.main.java.game.controller;

import java.util.Map;

import javafx.scene.Scene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utt.fr.rglb.main.java.game.model.GameModelGraphicsOriented;
import utt.fr.rglb.main.java.game.model.GameRule;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.model.PlayerTeam;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;
import utt.fr.rglb.main.java.view.graphics.GraphicsView;

public class GameControllerGraphicsOriented extends AbstractGameController {
	private static final Logger log = LoggerFactory.getLogger(GameControllerGraphicsOriented.class);
	private static final long serialVersionUID = 1L;
	private GameModelGraphicsOriented gameModel;
	private GraphicsView view;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	/**
	 * Constructeur de GameControllerGraphicsOriented
	 * @param view Vue permettant l'affichage d'informations
	 */
	public GameControllerGraphicsOriented(GraphicsView view) {
		this.view = view;
		this.gameModel = new GameModelGraphicsOriented(view);
	}
	
	/* ========================================= INITIALIZING ========================================= */
	
	@Override
	public void startAnotherGame() {
		this.view.displayWelcomeScreen();
	}
	
	public void createGameFrom(GameRule choosenRules, PlayersToCreate playersToCreate, Scene scene) {
		this.gameModel.initializeGameSettings(choosenRules,playersToCreate,scene);
		log.info("Required java objects successfully created");
		//PlayerControllerBean winningPlayer = cycleUntilSomeoneWins();
		//handleWinEvent(winningPlayer);
	}
	
	public PlayersToCreate retrievePlayerDataFromFile() {
		return this.gameModel.retrievePlayerDataFromFile();
	}
	
	public Map<Integer, PlayerTeam> getAllTeams() {
		return this.gameModel.getAllTeams();		
	}
	
	@Override
	protected void initializeGameSettings() {}

	

	@Override
	protected void resetEverything() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected PlayerControllerBean cycleUntilSomeoneWins() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PlayerControllerBean playOneRound() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void startNewRound() {
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
}
