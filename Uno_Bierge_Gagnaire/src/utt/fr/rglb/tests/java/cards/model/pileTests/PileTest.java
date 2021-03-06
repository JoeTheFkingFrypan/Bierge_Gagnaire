package utt.fr.rglb.tests.java.cards.model.pileTests;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.CardSpecial;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.cards.model.basics.EffetJoker;
import utt.fr.rglb.main.java.cards.model.pile.Pile;

/**
 * Classe de tests unitaires validant le comportement des méthodes de la classe Pile
 * @see Pile
 */
public class PileTest {
	private Pile emptyStack;
	private Pile filledStack;
	private Card oneCard;
	private Card compatibleCard;
	private Card yetAnotherCard;
	private int whateverPath;
	
	@Before
	public void setup() {
		this.whateverPath = 0;
		this.oneCard = new Card(7,Color.RED,this.whateverPath);
		this.compatibleCard = new Card(7,Color.BLUE,this.whateverPath);
		this.yetAnotherCard = new CardSpecial(20,Color.JOKER,new EffetJoker(),this.whateverPath);
		this.emptyStack = new Pile();
		this.filledStack = fillStackWithCards();
	}
	
	private Pile fillStackWithCards() {
		Pile stack = new Pile();
		stack.receiveCard(this.oneCard);
		stack.receiveCard(this.compatibleCard);
		stack.receiveCard(this.yetAnotherCard);
		return stack;	
	}

	/* ========================================= PLAY CARD ========================================= */
	
	@Test
	public void testReceiveCard() {
		this.emptyStack.receiveCard(this.oneCard);
		assertEquals(1,this.emptyStack.size());
		this.emptyStack.receiveCard(this.compatibleCard);
		assertEquals(2,this.emptyStack.size());
		this.emptyStack.receiveCard(this.yetAnotherCard);
		assertEquals(3,this.emptyStack.size());
	}
	
	@Test(expected=NullPointerException.class)
	public void testFailToPlayCardDueToNullCard() {
		this.emptyStack.receiveCard(null);
	}

	/* ========================================= EMPTYING (USED TO REFILL) ========================================= */
	
	@Test
	public void testEmptyPile() {
		Collection<Card> cardsFromFilledStack = this.filledStack.emptyPile();
		assertEquals(1,this.filledStack.size());
		assertEquals(2,cardsFromFilledStack.size());
		//Verification des cartes presentes dans la collection où des cartes existent
		assertFalse(cardsFromFilledStack.contains(this.yetAnotherCard));
		assertTrue(cardsFromFilledStack.contains(this.oneCard));
		assertTrue(cardsFromFilledStack.contains(this.compatibleCard));
	}
	
	@Test(expected=IllegalStateException.class)
	public void testFailToEmptyPileDueToPileAlreadyEmpty() {
		this.emptyStack.emptyPile();
	}
	
	/* ========================================= RESET ========================================= */
	
	@Test
	public void testReset() {
		this.filledStack.resetCards();
		assertEquals(0,this.filledStack.size());
	}
	
	/* ========================================= GETTERS & DISPLAY ========================================= */
	
	@Test
	public void testShowLastCardPlayed() {
		this.emptyStack.receiveCard(this.oneCard);
		assertEquals(this.oneCard,this.emptyStack.showLastCardPlayed());
		this.emptyStack.receiveCard(this.compatibleCard);
		assertEquals(this.compatibleCard,this.emptyStack.showLastCardPlayed());
	}
	
	@Test
	public void testSize() {
		assertEquals(0,this.emptyStack.size());
		assertEquals(3,this.filledStack.size());
	}
	
	@Test
	public void testToString() {
		assertEquals("[Talon] 0 cartes ont été jouées",this.emptyStack.toString());
		assertEquals("[Talon] 3 cartes ont été jouées",this.filledStack.toString());
	}
}