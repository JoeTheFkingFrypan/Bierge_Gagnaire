package tests.java.player.controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import main.java.cards.model.basics.Carte;
import main.java.cards.model.basics.Couleur;
import main.java.player.controller.PlayerController;

public class PlayerControllerTest {
	/*private String referenceName;
	private PlayerController playerController;
	private ArrayList<Carte> cardsToPickUp;
	private Carte firstCard;
	private Carte secondCard;
	private Carte thirdCard;
	
	@Before
	public void setup() {
		this.referenceName = "player1";
		this.playerController = new PlayerController(this.referenceName);
		this.cardsToPickUp = new ArrayList<Carte>();
		this.firstCard = new Carte(1,Couleur.BLEUE);
		this.secondCard = new Carte(2,Couleur.VERTE);
		this.thirdCard = new Carte(1,Couleur.ROUGE);
		this.cardsToPickUp.add(firstCard);
		this.cardsToPickUp.add(secondCard);
		this.cardsToPickUp.add(thirdCard);
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
	
	@Test
	public void testPickupCards() {
		this.playerController.pickUpCards(cardsToPickUp);
		assertEquals(3,this.playerController.getNumberOfCardsInHand());
	}
	
	@Test(expected=NullPointerException.class)
	public void testFailPickupCardsDueToNullCollection() {
		this.playerController.pickUpCards(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailPickupCardsDueToEmptyCollection() {
		Collection<Carte> noCards = new ArrayList<Carte>();
		this.playerController.pickUpCards(noCards);
	}
	
	@Test
	public void testGetNumberOfCardsInHands() {
		assertEquals(0,this.playerController.getNumberOfCardsInHand());
		this.playerController.pickUpCards(this.cardsToPickUp);
		assertEquals(3,this.playerController.getNumberOfCardsInHand());
		this.playerController.pickUpCards(this.cardsToPickUp);
		assertEquals(6,this.playerController.getNumberOfCardsInHand());
	}
	
	@Test
	public void testPlayCard() {
		Carte cardChosen;
		
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
	}*/
}
