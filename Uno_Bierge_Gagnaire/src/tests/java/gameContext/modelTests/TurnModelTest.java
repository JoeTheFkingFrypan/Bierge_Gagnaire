package tests.java.gameContext.modelTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import main.java.gameContext.model.TurnModel;
import main.java.player.controller.PlayerController;

import org.junit.Before;
import org.junit.Test;

public class TurnModelTest {
	/*private TurnModel turnModel;
	private TurnModel turnModelWithNoPlayer;
	private Collection<String> playerNames;
	
	@Before
	public void setup() {
		this.playerNames = fillPlayerCollection();
		this.turnModel = new TurnModel();
		this.turnModel.createPlayersFrom(this.playerNames);
		this.turnModelWithNoPlayer = new TurnModel();
	}
	
	private Collection<String> fillPlayerCollection() {
		Collection<String> playersNames = new ArrayList<String>();
		playersNames.add("player1");
		playersNames.add("player2");
		playersNames.add("player3");
		return playersNames;
	}
	
	@Test
	public void testGetNumberOfPlayers() {
		assertEquals(3,this.turnModel.getNumberOfPlayers());
		assertEquals(0,this.turnModelWithNoPlayer.getNumberOfPlayers());
	}
	
	@Test
	public void testCreatePlayersFrom() {
		assertEquals(0,this.turnModelWithNoPlayer.getNumberOfPlayers());
		this.turnModelWithNoPlayer.createPlayersFrom(this.playerNames);
		assertEquals(3,this.turnModelWithNoPlayer.getNumberOfPlayers());
	}
	
	@Test
	public void testIndicatesDefaultTurnOrder() {
		assertTrue(this.turnModel.indicatesDefaultTurnOrder());
		assertTrue(this.turnModelWithNoPlayer.indicatesDefaultTurnOrder());
	}
	
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
	public void testCycleThroughPlayersWithDefaultTurnOrder() {
		this.turnModelWithNoPlayer.createPlayersWithoutScramblingFrom(this.playerNames);
		
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
		this.turnModelWithNoPlayer.createPlayersWithoutScramblingFrom(this.playerNames);
		this.turnModelWithNoPlayer.reverseCurrentOrder();

		PlayerController currentPlayer = this.turnModelWithNoPlayer.cycleThroughPlayers();
		assertEquals("player3",currentPlayer.getAlias());

		currentPlayer = this.turnModelWithNoPlayer.cycleThroughPlayers();
		assertEquals("player2",currentPlayer.getAlias());
		
		currentPlayer = this.turnModelWithNoPlayer.cycleThroughPlayers();
		assertEquals("player1",currentPlayer.getAlias());
		
		currentPlayer = this.turnModelWithNoPlayer.cycleThroughPlayers();
		assertEquals("player3",currentPlayer.getAlias());
	}*/
}
