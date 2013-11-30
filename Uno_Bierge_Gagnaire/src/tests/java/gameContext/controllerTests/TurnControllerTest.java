package tests.java.gameContext.controllerTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import main.java.gameContext.controller.TurnController;
import main.java.player.controller.PlayerController;

public class TurnControllerTest {
	private TurnController turnControllerWithoutScramble;
	private TurnController turnControllerWithScrambledPlayers;
	
	@Before
	public void setup() {
		this.turnControllerWithoutScramble = new TurnController();
		this.turnControllerWithScrambledPlayers = new TurnController();
	}
	
	@Test(expected=NullPointerException.class)
	public void testFailToCreatePlayersDueToNullCollection() {
		this.turnControllerWithScrambledPlayers.createPlayersFrom(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailToCreatePlayersDueToIncorrectPlayerNumber() {
		Collection<String> emptyNameCollection = new ArrayList<String>();
		this.turnControllerWithScrambledPlayers.createPlayersFrom(emptyNameCollection);
	}
	
	@Test(expected=NullPointerException.class)
	public void testFailToCreatePlayersWithoutScramblingDueToNullCollection() {
		this.turnControllerWithoutScramble.createPlayersWithoutScamblingFrom(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailToCreatePlayersWithoutScramblingDueToIncorrectPlayerNumber() {
		Collection<String> emptyNameCollection = new ArrayList<String>();
		this.turnControllerWithoutScramble.createPlayersWithoutScamblingFrom(emptyNameCollection);
	}
	
	@Test
	public void testCreatePlayers() {	
		Collection<String> playerNames =generatePlayers();
		this.turnControllerWithoutScramble.createPlayersWithoutScamblingFrom(playerNames);
		this.turnControllerWithScrambledPlayers.createPlayersFrom(playerNames);
	}
	
	@Test
	public void testFindNextPlayerWithDefaultTurnOrder() {
		Collection<String> playerNames =generatePlayers();
		this.turnControllerWithoutScramble.createPlayersWithoutScamblingFrom(playerNames);
		
		PlayerController currentPlayer = this.turnControllerWithoutScramble.findNextPlayer();
		assertEquals("player1",currentPlayer.getAlias());
		
		currentPlayer = this.turnControllerWithoutScramble.findNextPlayer();
		assertEquals("player2",currentPlayer.getAlias());

		currentPlayer = this.turnControllerWithoutScramble.findNextPlayer();
		assertEquals("player1",currentPlayer.getAlias());
	}
	
	@Test
	public void testFindNextPlayerWithReversedTurnOrder() {
		Collection<String> playerNames =generatePlayers();
		this.turnControllerWithoutScramble.createPlayersWithoutScamblingFrom(playerNames);
		this.turnControllerWithoutScramble.reverseCurrentOrder();
		
		PlayerController currentPlayer = this.turnControllerWithoutScramble.findNextPlayer();
		assertEquals("player2",currentPlayer.getAlias());
		
		currentPlayer = this.turnControllerWithoutScramble.findNextPlayer();
		assertEquals("player1",currentPlayer.getAlias());

		currentPlayer = this.turnControllerWithoutScramble.findNextPlayer();
		assertEquals("player2",currentPlayer.getAlias());
	}
	
	private Collection<String> generatePlayers() {
		Collection<String> playerNames = new ArrayList<String>();
		playerNames.add("player1");
		playerNames.add("player2");
		return playerNames;
	}
	
	@Test
	public void reverseCurrentOrder() {
		this.turnControllerWithoutScramble.reverseCurrentOrder();
		this.turnControllerWithScrambledPlayers.reverseCurrentOrder();
	}
}
