package utt.fr.rglb.tests.java.cards.model.stockTests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;


import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import org.powermock.api.mockito.PowerMockito;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.CardSpecial;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.cards.model.basics.EffectPlus2;
import utt.fr.rglb.main.java.cards.model.basics.EffectReverse;
import utt.fr.rglb.main.java.cards.model.basics.EffetJoker;
import utt.fr.rglb.main.java.cards.model.stock.Stock;

public class PiocheTest {
	private Stock pioche;
	private Stock mockedPioche;
	private Queue<Card> baseQueue;
	private Card carte01;
	private Card carte02;
	private Card carte03;
	private Card carte04;
	private Card carte05;
	
	@Before
	public void setup() throws Exception {
		//Création de 5 cartes
		this.carte01 = new Card(8,Color.RED);
		this.carte02 = new Card(0,Color.BLUE);
		this.carte03 = new CardSpecial(20, Color.GREEN, new EffectReverse());
		this.carte04 = new CardSpecial(20, Color.YELLOW, new EffectPlus2(2));
		this.carte05 = new CardSpecial(50, Color.JOKER, new EffetJoker());
		//Création des pioches
		this.pioche = new Stock();
		this.baseQueue = fillCardsInsideQueue();
		//Spécifications du comportement des objets mockés
		this.mockedPioche = PowerMockito.spy(new Stock());
		defineBehaviourForMockedObjects();
	}
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	private Queue<Card> fillCardsInsideQueue() {
		Queue<Card> baseQueue = new LinkedList<Card>();
		baseQueue.add(this.carte01);
		baseQueue.add(this.carte02);
		baseQueue.add(this.carte03);
		baseQueue.add(this.carte04);
		baseQueue.add(this.carte05);
		return baseQueue;
	}
	
	private void defineBehaviourForMockedObjects() throws Exception {
		PowerMockito.doReturn(this.baseQueue.size()).when(this.mockedPioche,"size");
		PowerMockito.doReturn(this.baseQueue.contains(this.carte01)).when(mockedPioche,"contains",this.carte01);
		PowerMockito.doReturn(this.baseQueue.contains(this.carte02)).when(mockedPioche,"contains",this.carte02);
		PowerMockito.doReturn(this.baseQueue.contains(this.carte03)).when(mockedPioche,"contains",this.carte03);
		PowerMockito.doReturn(this.baseQueue.contains(this.carte04)).when(mockedPioche,"contains",this.carte04);
		PowerMockito.doReturn(this.baseQueue.contains(this.carte05)).when(mockedPioche,"contains",this.carte05);
	}
	
	/* ========================================= CARD CREATION & REFILL ========================================= */
	
	@Test
	public void testHasNotEnoughCards() {
		assertFalse(this.pioche.hasNotEnoughCards(1));
		assertFalse(this.pioche.hasNotEnoughCards(107));
		assertTrue(this.pioche.hasNotEnoughCards(999));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailToHasNotEnoughCards() {
		this.pioche.hasNotEnoughCards(-999);
	}
	
	@Test
	public void testRefillCards() {
		Collection<Card> givenCards = new ArrayList<Card>();
		Card firstCard = new Card(1,Color.YELLOW);
		Card secondCard = new Card(2,Color.YELLOW);
		Card thirdCard = new Card(3,Color.YELLOW);
		givenCards.add(firstCard);
		givenCards.add(secondCard);
		givenCards.add(thirdCard);
		this.pioche.refill(givenCards);
		assertEquals(3,this.pioche.size());
		assertTrue(this.pioche.contains(firstCard));
		assertTrue(this.pioche.contains(secondCard));
		assertTrue(this.pioche.contains(thirdCard));
	}
	
	@Test(expected=NullPointerException.class)
	public void testFailToRefillCardsDueToNullCollection() {
		this.pioche.refill(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailToRefillCardsDueToEmptyCollection() {
		Collection<Card> noCards = new ArrayList<Card>();
		this.pioche.refill(noCards);
	}
	
	/* ========================================= DRAW CARD ========================================= */
	
	@Test
	public void testDrawCard() {
		Collection<Card> cardsDrawn;
		cardsDrawn = this.pioche.drawCards(1);
		assertEquals(1,cardsDrawn.size());
		cardsDrawn = this.pioche.drawCards(7);
		assertEquals(7,cardsDrawn.size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailToDrawCardDueToNotEnoughCards() {
		this.pioche.drawCards(999);
	}
	
	@Test
	public void testDrawOneCard() {
		Card cardDrawn = this.pioche.drawOneCard();
		assertNotNull(cardDrawn);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testFailToDrawOneCard() {
		this.pioche.drawCards(108);
		this.pioche.drawOneCard();
	}
	
	/* ========================================= GETTERS & UTILS ========================================= */
	
	@Test
	public void testCardCount() {
		assertEquals(108,this.pioche.size());
		assertEquals(5,this.mockedPioche.size());
	}
	
	@Test
	public void testCardsInsidePioche() throws Exception {
		//Carte01
		assertTrue(this.pioche.contains(this.carte01));
		assertTrue(this.mockedPioche.contains(this.carte01));
		//Carte02
		assertTrue(this.pioche.contains(this.carte02));
		assertTrue(this.mockedPioche.contains(this.carte02));
		//Carte03
		assertTrue(this.pioche.contains(this.carte03));
		assertTrue(this.mockedPioche.contains(this.carte03));
		//Carte04
		assertTrue(this.pioche.contains(this.carte04));
		assertTrue(this.mockedPioche.contains(this.carte04));
		//Carte05
		assertTrue(this.pioche.contains(this.carte05));
		assertTrue(this.mockedPioche.contains(this.carte05));
		//N'importe quelle autre carte (valide)
		Card carte06 = new Card(9,Color.YELLOW);
		PowerMockito.doReturn(this.baseQueue.contains(carte06)).when(mockedPioche,"contains",carte06);
		assertTrue(this.pioche.contains(carte06));
		assertFalse(this.mockedPioche.contains(carte06));
	}
	
	@Test
	public void testToString() {
		assertEquals("[Pioche] Contient actuellement 108 cartes",this.pioche.toString());
	}
}