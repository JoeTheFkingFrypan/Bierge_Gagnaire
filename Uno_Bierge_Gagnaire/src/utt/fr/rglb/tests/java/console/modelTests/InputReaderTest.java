package utt.fr.rglb.tests.java.console.modelTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.console.model.InputReader;
import utt.fr.rglb.main.java.console.view.ConsoleView;
import utt.fr.rglb.main.java.console.view.View;
import utt.fr.rglb.main.java.game.model.GameMode;

/**
 * Classe de tests unitaires validant le comportement des m�thodes de la classe InputReader
 * </br>Utilisation de simulacres pour la vue et le lecteur bufferis� --injection de d�pendance permettant d'�muler une entr�e au clavier (Mockito)
 * @see InputReader
 */
public class InputReaderTest {
	private InputReader inputReader;
	private View consoleView;
	private BufferedReader inputStream;
	private CardsModelBean gameModelBean;

	@Before
	public void setup() {
		this.inputStream = mock(BufferedReader.class);
		this.consoleView = mock(ConsoleView.class);
		this.inputReader = new InputReader(consoleView);
		this.gameModelBean = mock(CardsModelBean.class);
		when(this.gameModelBean.getLastCardPlayed()).thenReturn(new Card(9,Color.YELLOW));
		when(this.gameModelBean.globalColorIsSet()).thenReturn(false);
	}

	/* ========================================= CONSTRUCTOR ========================================= */

	@Test(expected=NullPointerException.class)
	public void testFailToCreateInputReaderDueToNullView() {
		this.inputReader = new InputReader(null);
	}

	/* ========================================= PLAYER NUMBER ========================================= */

	@Test
	public void testGetValidPlayer() throws IOException {
		when(this.inputStream.readLine()).thenReturn(
				"qskdlqksdmlk", 	//[ECHEC] - Pas de nombre dans la r�ponse 
				"0", 				//[ECHEC] - Nombre non compris entre 2 et 7
				"120",				//[ECHEC] - Nombre non compris entre 2 et 7
				"4");				//[SUCCES]
		assertEquals(4,this.inputReader.getValidPlayerNumber(this.inputStream));
	}

	@Test
	public void testGetNumberFromString() throws IOException {
		String invalidAnswer = "qskdlqksdmlk";
		when(this.inputStream.readLine()).thenReturn("qskd1lqk2sdm0lk"); //La deuxi�me string sera interpr�t�e comme "120" --suppression de tous les caract�res qui ne sont pas des nombres
		assertEquals(120,this.inputReader.getNumberFromString(invalidAnswer, this.inputStream));
		String validAnswer = "8";
		assertEquals(8,this.inputReader.getNumberFromString(validAnswer, this.inputStream));
	}

	/* ========================================= PLAYER ALIAS ========================================= */
	
	@Test
	public void testGetAllPlayerNames() throws IOException {
		when(this.inputStream.readLine()).thenReturn(
				"p1","0",			//[SUCCES] - Joueur humain nomm� "p1"
				"p2","0",			//[SUCCES] - Joueur humain nomm� "p2"
				"p2",				//[ECHEC] - Nom d�j� pr�sent
				"p3","1","0",		//[SUCCES] - Joueur IA nomm� "p3" avec la premi�re strat�gie
				"p4","1","1",		//[SUCCES] - Joueur IA nomm� "p4" avec la deuxi�me strat�gie
				"p5","1","2"		//[SUCCES] - Joueur IA nomm� "p5" avec la troisi�me strat�gie
				);
		assertEquals("[p1, p2, p3, p4, p5]",this.inputReader.getAllPlayerNames(5, this.inputStream).toString());
	}
	
	@Test(expected=NullPointerException.class)
	public void testFailToGetAllPlayerNamesDueToNullPlayerNumber() {
		this.inputReader.getAllPlayerNames(null, this.inputStream);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailToGetAllPlayerNamesDueToInvalidPlayerNumber() {
		this.inputReader.getAllPlayerNames(-9, this.inputStream);
	}

	/* ========================================= CARD INDEX FROM HAND ========================================= */

	@Test
	public void testGetValidAnswer() throws IOException {
		when(this.inputStream.readLine()).thenReturn(
				"invalid",			//[ECHEC] - Pas de nombre
				"-1",				//[ECHEC] - index invalide (doit �tre compris entre 0 et le nombre de cartes - 1, soit 3 dans ce cas l�)
				"999",				//[ECHEC] - index invalide (doit �tre compris entre 0 et le nombre de cartes - 1, soit 3 dans ce cas l�)
				"1"					//[SUCCES]
				);
		Collection<Card> cardCollection = fillCardCollection();
		assertEquals("1",this.inputReader.getValidAnswer("test", cardCollection, this.gameModelBean, inputStream));
	}
	
	@Test
	public void testGetAnotherValidIndexFromInputDueToIncompatibleCard() throws IOException {
		when(this.inputStream.readLine()).thenReturn(
				"invalid",			//[ECHEC] - Pas de nombre
				"-1",				//[ECHEC] - index invalide (doit �tre compris entre 0 et le nombre de cartes - 1, soit 3 dans ce cas l�)
				"999",				//[ECHEC] - index invalide (doit �tre compris entre 0 et le nombre de cartes - 1, soit 3 dans ce cas l�)
				"1"					//[SUCCES]
				);
		Collection<Card> cardCollection = fillCardCollection();
		assertEquals("1",this.inputReader.getAnotherValidAnswerFromInputDueToIncompatibleCard("test", cardCollection, this.gameModelBean, inputStream));
	}
	
	private Collection<Card> fillCardCollection() {
		Collection<Card> cardCollection = new ArrayList<Card>();
		cardCollection.add(new Card(3,Color.RED));
		cardCollection.add(new Card(9,Color.BLUE));
		cardCollection.add(new Card(2,Color.GREEN));
		return cardCollection;
	}
	
	/* ========================================= COLOR PICKING ========================================= */
	
	@Test
	public void testGetValidColor() throws IOException {
		when(this.inputStream.readLine()).thenReturn(
				"invalid",			//[ECHEC] - Pas de nombre
				"-1",				//[ECHEC] - index invalide (doit �tre compris entre 0 et le nombre de cartes - 1, soit 3 dans ce cas l�)
				"999",				//[ECHEC] - index invalide (doit �tre compris entre 0 et le nombre de cartes - 1, soit 3 dans ce cas l�)
				"0"					//[SUCCES]
				);
		assertEquals(Color.RED,this.inputReader.getValidColor(this.inputStream));
		when(this.inputStream.readLine()).thenReturn("1");
		assertEquals(Color.BLUE,this.inputReader.getValidColor(this.inputStream));
		when(this.inputStream.readLine()).thenReturn("2");
		assertEquals(Color.GREEN,this.inputReader.getValidColor(this.inputStream));
		when(this.inputStream.readLine()).thenReturn("3");
		assertEquals(Color.YELLOW,this.inputReader.getValidColor(this.inputStream));
	}
	
	/* ========================================= UTILS ========================================= */
	
	@Test
	public void testGetValidAnswerFromDualChoice() throws IOException {
		when(this.inputStream.readLine()).thenReturn(
				"invalid",			//[ECHEC] - Pas de nombre
				"-1",				//[ECHEC] - index invalide (doit �tre compris entre 0 et 1)
				"999",				//[ECHEC] - index invalide (doit �tre compris entre 0 et 1)
				"1"					//[SUCCES]
				);
		assertEquals(1,this.inputReader.getValidAnswerFromDualChoice(this.inputStream));
	}
	
	@Test
	public void testGetValidAnswerFromTripleChoice() throws IOException {
		when(this.inputStream.readLine()).thenReturn(
				"invalid",			//[ECHEC] - Pas de nombre
				"-1",				//[ECHEC] - index invalide (doit �tre compris entre 0 et 1)
				"999",				//[ECHEC] - index invalide (doit �tre compris entre 0 et 1)
				"2"					//[SUCCES]
				);
		assertEquals(2,this.inputReader.getValidAnswerFromTripleChoice(this.inputStream));
	}
	
	@Test
	public void testFindIfUnoHasBeenAnnounced() {
		assertFalse(this.inputReader.findIfUnoHasBeenAnnounced("NOPE"));
		assertTrue(this.inputReader.findIfUnoHasBeenAnnounced("1 UNO"));
		assertTrue(this.inputReader.findIfUnoHasBeenAnnounced("1 uno"));
		assertTrue(this.inputReader.findIfUnoHasBeenAnnounced("UNO 23"));
		assertTrue(this.inputReader.findIfUnoHasBeenAnnounced("UnO 23"));
		assertFalse(this.inputReader.findIfUnoHasBeenAnnounced("UN_O 23"));
	}
	
	@Test(expected=NullPointerException.class)
	public void testFailToFindIfUnoHasBeenAnnoucedDueToNullAnswer() {
		this.inputReader.findIfUnoHasBeenAnnounced(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailToFindIfUnoHasBeenAnnoucedDueToEmptyAnswer() {
		assertFalse(this.inputReader.findIfUnoHasBeenAnnounced(""));
	}
	
	@Test
	public void testAskForConfigurationFileUsage() throws IOException {
		when(this.inputStream.readLine()).thenReturn(
				"invalid",			//[ECHEC] - Pas de nombre
				"-1",				//[ECHEC] - index invalide (doit �tre compris entre 0 et 1)
				"3",				//[ECHEC] - index invalide (doit �tre compris entre 0 et 1)
				"0"					//[SUCCES]
				);
		assertTrue(this.inputReader.askForConfigurationFileUsage(this.inputStream));
	}
	
	@Test
	public void testAskForGameMode() throws IOException {
		//Cas avec 2 joueurs
		when(this.inputStream.readLine()).thenReturn("0");
		assertEquals(GameMode.TWO_PLAYERS,this.inputReader.askForGameMode(2, this.inputStream).getGameMode());
		when(this.inputStream.readLine()).thenReturn("1");
		assertEquals(GameMode.NORMAL,this.inputReader.askForGameMode(2, this.inputStream).getGameMode());
		//Cas avec 4 ou 6 joueurs (nombre pair --sauf 2)
		when(this.inputStream.readLine()).thenReturn("0");
		assertEquals(GameMode.NORMAL,this.inputReader.askForGameMode(6, this.inputStream).getGameMode());
		when(this.inputStream.readLine()).thenReturn("1");
		assertEquals(GameMode.UNO_CHALLENGE,this.inputReader.askForGameMode(6, this.inputStream).getGameMode());
		when(this.inputStream.readLine()).thenReturn("2");
		assertEquals(GameMode.TEAM_PLAY,this.inputReader.askForGameMode(6, this.inputStream).getGameMode());
		//Tous les autres cas
		when(this.inputStream.readLine()).thenReturn(
				"invalid",			//[ECHEC] - Pas de nombre
				"-1",				//[ECHEC] - index invalide (doit �tre compris entre 0 et 1)
				"3",				//[ECHEC] - index invalide (doit �tre compris entre 0 et 1)
				"0"					//[SUCCES]
				);
		assertEquals(GameMode.NORMAL,this.inputReader.askForGameMode(5, this.inputStream).getGameMode());
		when(this.inputStream.readLine()).thenReturn("1");
		assertEquals(GameMode.UNO_CHALLENGE,this.inputReader.askForGameMode(5, this.inputStream).getGameMode());
	}
	
	//@Test(expected=NullPointerException.class)
	public void testFailToAskForGameModeDueToNullSize() {
		this.inputReader.askForGameMode(null, this.inputStream);
	}
	
	//@Test(expected=IllegalArgumentException.class)
	public void testFailToAskForGameModeDueToInvalidSize() {
		this.inputReader.askForGameMode(-99, this.inputStream);
	}
}
