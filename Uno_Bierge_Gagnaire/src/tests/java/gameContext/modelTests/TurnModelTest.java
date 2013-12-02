package tests.java.gameContext.modelTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Collection;

import main.java.console.view.View;
import main.java.gameContext.model.TurnModel;
import main.java.player.controller.PlayerController;

import org.junit.Before;
import org.junit.Test;

public class TurnModelTest {
	private View mockedView;
	private TurnModel turnModel;
	private TurnModel turnModelWithNoPlayer;
	private Collection<String> playerNames;
	
	@Before
	public void setup() {
		this.mockedView = mock(View.class);
		this.playerNames = fillPlayerCollection();
		this.turnModel = new TurnModel();
		this.turnModel.createPlayersFrom(this.playerNames,this.mockedView);
		this.turnModelWithNoPlayer = new TurnModel();
	}
	
	private Collection<String> fillPlayerCollection() {
		Collection<String> playersNames = new ArrayList<String>();
		playersNames.add("player1");
		playersNames.add("player2");
		playersNames.add("player3");
		return playersNames;
	}
	
	/* ========================================= PLAYER CREATION ========================================= */
	
	@Test
	public void testCreatePlayersFrom() {
		assertEquals(0,this.turnModelWithNoPlayer.getNumberOfPlayers());
		this.turnModelWithNoPlayer.createPlayersFrom(this.playerNames,this.mockedView);
		assertEquals(3,this.turnModelWithNoPlayer.getNumberOfPlayers());
	}

	/* ========================================= TURN ORDER ========================================= */
	
	@Test
	public void testReverseTurnOrder() {
		assertTrue(this.turnModel.indicatesDefaultTurnOrder());
		assertTrue(this.turnModelWithNoPlayer.indicatesDefaultTurnOrder());
		this.turnModel.reverseCurrentOrder();
		this.turnModelWithNoPlayer.reverseCurrentOrder();
		assertFalse(this.turnModel.indicatesDefaultTurnOrder());
		assertFalse(this.turnModelWithNoPlayer.indicatesDefaultTurnOrder());
		this.turnModel.reverseCurrentOrder();
		this.turnModelWithNoPlayer.reverseCurrentOrder();
		assertTrue(this.turnModel.indicatesDefaultTurnOrder());
		assertTrue(this.turnModelWithNoPlayer.indicatesDefaultTurnOrder());
	}
	
	@Test
	public void testIndicatesDefaultTurnOrder() {
		assertTrue(this.turnModel.indicatesDefaultTurnOrder());
		assertTrue(this.turnModelWithNoPlayer.indicatesDefaultTurnOrder());
	}
	
	/* ========================================= PLAYER CYCLING ========================================= */
	
	@Test
	public void testCycleThroughPlayersWithDefaultTurnOrder() {
		this.turnModelWithNoPlayer.createPlayersWithoutScramblingFrom(this.playerNames,this.mockedView);
		
		PlayerController currentPlayer = this.turnModelWithNoPlayer.cycleThroughPlayers();
		assertEquals("player1",currentPlayer.getAlias());
		
		currentPlayer = this.turnModelWithNoPlayer.cycleThroughPlayers();
		assertEquals("player2",currentPlayer.getAlias());
		
		currentPlayer = this.turnModelWithNoPlayer.cycleThroughPlayers();
		assertEquals("player3",currentPlayer.getAlias());
		
		currentPlayer = this.turnModelWithNoPlayer.cycleThroughPlayers();
		assertEquals("player1",currentPlayer.getAlias());
	}
	
	@Test
	public void testCycleThroughPlayersWithReversedTurnOrder() {
		this.turnModelWithNoPlayer.createPlayersWithoutScramblingFrom(this.playerNames,this.mockedView);
		this.turnModelWithNoPlayer.reverseCurrentOrder();

		PlayerController currentPlayer = this.turnModelWithNoPlayer.cycleThroughPlayers();
		assertEquals("player3",currentPlayer.getAlias());

		currentPlayer = this.turnModelWithNoPlayer.cycleThroughPlayers();
		assertEquals("player2",currentPlayer.getAlias());
		
		currentPlayer = this.turnModelWithNoPlayer.cycleThroughPlayers();
		assertEquals("player1",currentPlayer.getAlias());
		
		currentPlayer = this.turnModelWithNoPlayer.cycleThroughPlayers();
		assertEquals("player3",currentPlayer.getAlias());
	}
	
	/* ========================================= GETTERS & UTILS ========================================= */
	
	@Test
	public void testGetNumberOfPlayers() {
		assertEquals(3,this.turnModel.getNumberOfPlayers());
		assertEquals(0,this.turnModelWithNoPlayer.getNumberOfPlayers());
	}
}
