package utt.fr.rglb.tests.java.gameContext.modelTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import utt.fr.rglb.main.java.console.view.View;
import utt.fr.rglb.main.java.turns.model.TurnModel;
import utt.fr.rglb.main.java.player.controller.PlayerController;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;

/**
 * Classe de tests unitaires validant le comportement des méthodes de la classe TurnModel
 * </br>Utilisation de simulacres pour la vue et le lecteur bufferisé --injection de dépendance permettant d'émuler une entrée au clavier (Mockito)
 * @see TurnModel
 */
public class TurnModelTest {
	private View mockedView;
	private TurnModel turnModel;
	private TurnModel turnModelWithNoPlayer;
	private Collection<String> playerNames;
	private BufferedReader mockedInputStream;
	private PlayersToCreate playersToCreate;
	
	@Before
	public void setup() {
		this.mockedView = mock(View.class);
		this.playerNames = fillPlayerCollection();
		this.turnModel = new TurnModel();
		this.playersToCreate = addAllReferencesPlayersToCreate();
		this.turnModel.createPlayersFrom(this.playersToCreate,this.mockedView,this.mockedInputStream);
		this.turnModelWithNoPlayer = new TurnModel();
		this.mockedInputStream = mock(BufferedReader.class);
	}
	
	private Collection<String> fillPlayerCollection() {
		Collection<String> playersNames = new ArrayList<String>();
		playersNames.add("player1");
		playersNames.add("player2");
		playersNames.add("player3");
		return playersNames;
	}
	
	private PlayersToCreate addAllReferencesPlayersToCreate() {
		PlayersToCreate playersToCreate = new PlayersToCreate();
		for(String name : this.playerNames) {
			playersToCreate.addHumanPlayer(name);
		}
		return playersToCreate;
	}
	
	/* ========================================= PLAYER CREATION ========================================= */
	
	@Test
	public void testCreatePlayersFrom() {
		assertEquals(0,this.turnModelWithNoPlayer.getNumberOfPlayers());
		this.turnModelWithNoPlayer.createPlayersFrom(this.playersToCreate,this.mockedView,this.mockedInputStream);
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
		this.turnModelWithNoPlayer.createPlayersWithoutScramblingFrom(this.playerNames,this.mockedView,this.mockedInputStream);
		
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
		this.turnModelWithNoPlayer.createPlayersWithoutScramblingFrom(this.playerNames,this.mockedView,this.mockedInputStream);
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
