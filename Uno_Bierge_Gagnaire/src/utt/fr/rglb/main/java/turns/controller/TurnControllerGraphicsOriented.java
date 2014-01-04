package utt.fr.rglb.main.java.turns.controller;

import java.util.Collection;
import java.util.Map;

import com.google.common.base.Preconditions;

import javafx.scene.Scene;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.player.controller.PlayerControllerBean;
import utt.fr.rglb.main.java.player.controller.PlayerControllerGraphicsOriented;
import utt.fr.rglb.main.java.player.model.PlayerTeam;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;
import utt.fr.rglb.main.java.turns.model.TurnModelGraphicsOriented;
import utt.fr.rglb.main.java.view.graphics.GraphicsView;

public class TurnControllerGraphicsOriented extends AbstractTurnController {
	private static final long serialVersionUID = 1L;
	private TurnModelGraphicsOriented turnModel;
	private GraphicsView view;

	/* ========================================= CONSTRUCTOR ========================================= */

	public TurnControllerGraphicsOriented(GraphicsView view) {
		this.turnModel = new TurnModelGraphicsOriented();
		this.view = view;
	}

	/* ========================================= RESET ========================================= */

	@Override
	public void resetPlayerIndex() {
		this.turnModel.resetPlayerIndex();
	}

	@Override
	public void resetTurn() {
		this.turnModel.resetTurn();
	}

	/* ========================================= PLAYER CREATION ========================================= */

	public void createPlayersFrom(PlayersToCreate playersToCreate) {
		this.turnModel.createPlayersFrom(playersToCreate,this.view);
	}

	@Override
	public void splitPlayersIntoTeams() {
		this.turnModel.splitPlayersIntoTeams();
	}

	/* ========================================= TURN ORDER ========================================= */

	@Override
	public void reverseCurrentOrder() {
		// TODO Auto-generated method stub
	}

	@Override
	public PlayerControllerGraphicsOriented findCurrentPlayer() {
		return this.turnModel.findCurrentPlayer();
	}

	@Override
	public PlayerControllerGraphicsOriented findNextPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlayerControllerGraphicsOriented findNextPlayerWithoutChangingCurrentPlayer() {
		return this.turnModel.cycleThroughPlayersWithoutChangingCurrentPlayer();
	}

	/* ========================================= EFFECT RELATED ========================================= */

	@Override
	public void skipNextPlayer() {
		// TODO Auto-generated method stub
	}

	@Override
	public void cycleSilently() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void giveCardsToNextPlayer(Collection<Card> cards) {
		Preconditions.checkNotNull(cards,"[ERROR] Provided card collection cannot be null");
		this.turnModel.giveCardsToNextPlayer(cards);
	}

	/* ========================================= ROUND WIN EVENT HANDLING ========================================= */

	@Override
	public boolean computeIndividualEndOfTurn(PlayerControllerBean gameWinner) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean computeTeamEndOfTurn(PlayerControllerBean gameWinner) {
		// TODO Auto-generated method stub
		return false;
	}

	/* ========================================= TEAM PLAY ========================================= */

	@Override
	public PlayerTeam findWinningTeam(PlayerControllerBean winningPlayer) {
		Preconditions.checkNotNull(winningPlayer,"[ERROR] Impossible to find winning team : provided PlayerControllerBean cannot be null");
		return this.turnModel.findWinningTeam(winningPlayer);
	}
	
	public void displayTeams(Scene scene) {
		this.view.displayTeamScreen(scene);
	}

	public Map<Integer, PlayerTeam> getAllTeams() {
		return this.turnModel.getAllTeams();
	}
	

	/* ========================================= SCORE ========================================= */

	@Override
	public void displayIndividualTotalScore() {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayTeamTotalScore() {
		// TODO Auto-generated method stub
	}

	/* ========================================= GETTERS & DISPLAY ========================================= */

	@Override
	public int getNumberOfPlayers() {
		return this.turnModel.getNumberOfPlayers();
	}
	
	@Override
	public void displayTeams() {
		// TODO Auto-generated method stub

	}

	public Map<String, Collection<Card>> getAllCardsFromPlayers() {
		return this.turnModel.getAllCardsFromPlayers();
	}

}
