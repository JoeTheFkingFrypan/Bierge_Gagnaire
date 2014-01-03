package utt.fr.rglb.tests.java.player.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.player.controller.PlayerControllerConsoleOriented;
import utt.fr.rglb.main.java.view.AbstractView;

/**
 * Classe de tests unitaires validant le comportement des méthodes de la classe PlayerController
 * </br>Utilisation de simulacres pour la vue et le lecteur bufferisé --injection de dépendance permettant d'émuler une entrée au clavier (Mockito)
 * @see PlayerControllerConsoleOriented
 */
public class PlayerControllerTest {
	private String referenceName;
	private PlayerControllerConsoleOriented playerController;
	private ArrayList<Card> cardsToPickUp;
	private Card firstCard;
	private Card secondCard;
	private Card thirdCard;
	private AbstractView mockedView;
	private BufferedReader inputStream;
	
	@Before
	public void setup() {
		this.mockedView = mock(AbstractView.class);
		this.inputStream = mock(BufferedReader.class);
		this.referenceName = "player1";
		this.playerController = new PlayerControllerConsoleOriented(this.referenceName,this.mockedView,this.inputStream);
		this.cardsToPickUp = new ArrayList<Card>();
		this.firstCard = new Card(1,Color.RED);
		this.secondCard = new Card(1,Color.BLUE);
		this.thirdCard = new Card(2,Color.GREEN);
		this.cardsToPickUp.add(firstCard);
		this.cardsToPickUp.add(secondCard);
		this.cardsToPickUp.add(thirdCard);
	}
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	@Test(expected=NullPointerException.class)
	public void testFailToCreatePlayerControllerDueToNullName() {
		this.playerController = new PlayerControllerConsoleOriented(null, this.mockedView,this.inputStream);
	}
	
	@Test(expected=NullPointerException.class)
	public void testFailToCreatePlayerControllerDueToNullView() {
		this.playerController = new PlayerControllerConsoleOriented(this.referenceName, null,this.inputStream);
	}
	
	/* ========================================= CARD PICKUP ========================================= */
	
	@Test
	public void testPickupCards() {
		assertEquals(0,this.playerController.getNumberOfCardsInHand());
		this.playerController.pickUpCards(cardsToPickUp);
		assertEquals(3,this.playerController.getNumberOfCardsInHand());
	}
	
	@Test(expected=NullPointerException.class)
	public void testFailPickupCardsDueToNullCollection() {
		this.playerController.pickUpCards(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailPickupCardsDueToEmptyCollection() {
		Collection<Card> noCards = new ArrayList<Card>();
		this.playerController.pickUpCards(noCards);
	}
	
	@Test
	public void testPickUpOneCard() {
		assertEquals(0,this.playerController.getNumberOfCardsInHand());
		this.playerController.pickUpOneCard(this.firstCard);
		assertEquals(1,this.playerController.getNumberOfCardsInHand());
	}
	
	/* ========================================= CARD PLAY ========================================= */
	
	@Test
	public void testPlayCard() {
		Card cardChosen;
		
		//Picking up cards one by one
		this.playerController.pickUpCards(this.cardsToPickUp);
		
		cardChosen = this.playerController.playCard(0);
		assertEquals(2,this.playerController.getNumberOfCardsInHand());
		assertEquals(this.firstCard,cardChosen);
		
		cardChosen = this.playerController.playCard(0);
		assertEquals(1,this.playerController.getNumberOfCardsInHand());
		assertEquals(this.secondCard,cardChosen);
		
		cardChosen = this.playerController.playCard(0);
		assertEquals(0,this.playerController.getNumberOfCardsInHand());
		assertEquals(this.thirdCard,cardChosen);
		
		//Picking up cards at specified index
		this.playerController.pickUpCards(this.cardsToPickUp);
		
		cardChosen = this.playerController.playCard(2);
		assertEquals(2,this.playerController.getNumberOfCardsInHand());
		assertEquals(this.thirdCard,cardChosen);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testFailPlayCardDueToLackOfCards() {
		this.playerController.playCard(0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailPlayCardDueToNegativeIndex() {
		this.playerController.pickUpCards(this.cardsToPickUp);
		this.playerController.playCard(-99);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailPlayCardDueToInexistingIndex() {
		this.playerController.pickUpCards(this.cardsToPickUp);
		this.playerController.playCard(999);
	}
	
	/* ========================================= TURN HANDLING ========================================= */
	
	//TODO: [PlayerControllerTest] Do it
	
	/* ========================================= GETTERS & UTILS ========================================= */
	
	@Test
	public void testGetNumberOfCardsInHands() {
		assertEquals(0,this.playerController.getNumberOfCardsInHand());
		this.playerController.pickUpCards(this.cardsToPickUp);
		assertEquals(3,this.playerController.getNumberOfCardsInHand());
		this.playerController.pickUpCards(this.cardsToPickUp);
		assertEquals(6,this.playerController.getNumberOfCardsInHand());
	}
	
	@Test
	public void testGetAlias() {
		assertEquals(this.referenceName,this.playerController.getAlias());
	}
	
	@Test
	public void testGetScore() {
		assertEquals(0,this.playerController.getScore());
	}
	
	@Test
	public void testToString() {
		assertEquals("player1",this.playerController.toString());
	}
}
