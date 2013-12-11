package utt.fr.rglb.tests.java.gameContext.controllerTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.console.view.View;
import utt.fr.rglb.main.java.player.controller.PlayerController;
import utt.fr.rglb.main.java.turns.controller.TurnController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class TurnControllerTest {
	private View mockedView;
	private TurnController turnControllerWithoutScramble;
	private TurnController turnControllerWithScrambledPlayers;
	
	@Before
	public void setup() {
		this.mockedView = mock(View.class);
		this.turnControllerWithoutScramble = new TurnController(this.mockedView);
		this.turnControllerWithScrambledPlayers = new TurnController(this.mockedView);
	}
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	@Test(expected=NullPointerException.class)
	public void testFailToCreateTurnControllerDueToNullView() {
		this.turnControllerWithoutScramble = new TurnController(null);
	}
	
	/* ========================================= PLAYER CREATION ========================================= */

	//TODO: [TurnControllerTest] fix it
	@Test
	public void testCreatePlayers() {	
		Collection<String> playerNames =generatePlayers();
		this.turnControllerWithoutScramble.createPlayersWithoutScamblingFrom(playerNames);
		//this.turnControllerWithScrambledPlayers.createPlayersFrom(playerNames);
	}
	
	@Test(expected=NullPointerException.class)
	public void testFailToCreatePlayersDueToNullCollection() {
		this.turnControllerWithScrambledPlayers.createPlayersFrom(null);
	}
	
	//TODO: [TurnControllerTest] fix it
	@Test(expected=IllegalArgumentException.class)
	public void testFailToCreatePlayersDueToIncorrectPlayerNumber() {
		@SuppressWarnings("unused")
		Collection<String> emptyNameCollection = new ArrayList<String>();
		//this.turnControllerWithScrambledPlayers.createPlayersFrom(emptyNameCollection);
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
	public void testGiveCardsToNextPlayer() {
		//TODO: [TurnControllerTest] fix it
		Card c1 = new Card(1,Color.BLUE);
		Card c2 = new Card(2,Color.BLUE);
		Card c3 = new Card(3,Color.BLUE);
		Collection<Card> cards = Arrays.asList(c1,c2,c3);
		Collection<String> playerNames =generatePlayers();
		this.turnControllerWithoutScramble.createPlayersWithoutScamblingFrom(playerNames);
		PlayerController currentPlayer = this.turnControllerWithoutScramble.findNextPlayerWithoutChangingCurrentPlayer();
		this.turnControllerWithoutScramble.giveCardsToNextPlayer(cards);
		assertEquals(3,currentPlayer.getNumberOfCardsInHand());
	}
	
	/* ========================================= TURN ORDER ========================================= */
	
	@Test
	public void reverseCurrentOrder() {
		this.turnControllerWithoutScramble.reverseCurrentOrder();
		this.turnControllerWithScrambledPlayers.reverseCurrentOrder();
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
}