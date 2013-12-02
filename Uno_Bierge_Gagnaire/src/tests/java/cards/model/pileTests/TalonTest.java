package tests.java.cards.model.pileTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import org.junit.Before;
import org.junit.Test;

import main.java.cards.model.basics.Carte;
import main.java.cards.model.basics.CarteSpeciale;
import main.java.cards.model.basics.Couleur;
import main.java.cards.model.basics.EffetJoker;
import main.java.cards.model.pile.Talon;

public class TalonTest {
	private Talon emptyStack;
	private Talon filledStack;
	private Carte oneCard;
	private Carte compatibleCard;
	private Carte yetAnotherCard;
	private Carte incompatibleCard;
	
	@Before
	public void setup() {
		this.oneCard = new Carte(7,Couleur.ROUGE);
		this.compatibleCard = new Carte(7,Couleur.BLEUE);
		this.incompatibleCard = new Carte(2,Couleur.JAUNE);
		this.yetAnotherCard = new CarteSpeciale(20,Couleur.JOKER,new EffetJoker());
		this.emptyStack = new Talon();
		this.filledStack = fillStackWithCards();
	}
	
	private Talon fillStackWithCards() {
		Talon stack = new Talon();
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
	
	@Test
	public void testAcceptCard() {
		this.emptyStack.receiveCard(this.oneCard);
		assertTrue(this.emptyStack.accept(this.compatibleCard));
		assertFalse(this.emptyStack.accept(this.incompatibleCard));
	}
	
	@Test(expected=NullPointerException.class)
	public void testFailToAcceptDueToNullCard() {
		this.emptyStack.accept(null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testFailToAcceptDueToLackOfCards() {
		this.emptyStack.accept(this.oneCard);
	}

	/* ========================================= EMPTYING (USED TO REFILL) ========================================= */
	
	@Test
	public void testEmptyPile() {
		Collection<Carte> cardsFromFilledStack = this.filledStack.emptyPile();
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