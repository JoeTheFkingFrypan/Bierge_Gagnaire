package tests.java.player.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import main.java.cards.model.basics.Card;
import main.java.cards.model.basics.Color;
import main.java.console.view.View;
import main.java.player.controller.PlayerController;

public class PlayerControllerTest {
	private String referenceName;
	private PlayerController playerController;
	private ArrayList<Card> cardsToPickUp;
	private Card firstCard;
	private Card secondCard;
	private Card thirdCard;
	private View mockedView;
	
	@Before
	public void setup() {
		this.mockedView = mock(View.class);
		this.referenceName = "player1";
		this.playerController = new PlayerController(this.referenceName,this.mockedView);
		this.cardsToPickUp = new ArrayList<Card>();
		this.firstCard = new Card(1,Color.BLUE);
		this.secondCard = new Card(2,Color.GREEN);
		this.thirdCard = new Card(1,Color.RED);
		this.cardsToPickUp.add(firstCard);
		this.cardsToPickUp.add(secondCard);
		this.cardsToPickUp.add(thirdCard);
	}
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	@Test(expected=NullPointerException.class)
	public void testFailToCreatePlayerControllerDueToNullName() {
		this.playerController = new PlayerController(null, this.mockedView);
	}
	
	@Test(expected=NullPointerException.class)
	public void testFailToCreatePlayerControllerDueToNullView() {
		this.playerController = new PlayerController(this.referenceName, null);
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
	
	//TODO: do it
	
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
		assertEquals("[JOUEUR] player1 a 0 points. Il lui reste 0 cartes en main",this.playerController.toString());
	}
}
