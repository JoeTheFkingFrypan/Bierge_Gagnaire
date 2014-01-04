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

/**
 * Classe de tests unitaires validant le comportement des méthodes de la classe Stock
 * </br>Utilisation de simulacres pour l'effet des cartes spéciales (Mockito)
 * @see Stock
 */
public class StockTest {
	private Stock stock;
	private Stock mockedPioche;
	private Queue<Card> baseQueue;
	private Card carte01;
	private Card carte02;
	private Card carte03;
	private Card carte04;
	private Card carte05;
	private int whateverPath;
	
	@Before
	public void setup() throws Exception {
		this.whateverPath = 0;
		//Création de 5 cartes
		this.carte01 = new Card(8,Color.RED,this.whateverPath);
		this.carte02 = new Card(0,Color.BLUE,this.whateverPath);
		this.carte03 = new CardSpecial(20, Color.GREEN, new EffectReverse(),this.whateverPath);
		this.carte04 = new CardSpecial(20, Color.YELLOW, new EffectPlus2(2),this.whateverPath);
		this.carte05 = new CardSpecial(50, Color.JOKER, new EffetJoker(),this.whateverPath);
		//Création des pioches
		this.stock = new Stock();
		this.stock.resetCards();
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
	
	
	@Test
	public void testReset() {
		assertEquals(108,this.stock.size());
		this.stock.drawCards(100);
		assertEquals(8,this.stock.size());
		this.stock.resetCards();
		assertEquals(108,this.stock.size());
	}
	
	/* ========================================= CARD CREATION & REFILL ========================================= */
	
	@Test
	public void testHasNotEnoughCards() {
		assertFalse(this.stock.hasNotEnoughCards(1));
		assertFalse(this.stock.hasNotEnoughCards(107));
		assertTrue(this.stock.hasNotEnoughCards(999));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailToHasNotEnoughCards() {
		this.stock.hasNotEnoughCards(-999);
	}
	
	@Test
	public void testRefillCards() {
		Collection<Card> givenCards = new ArrayList<Card>();
		Card firstCard = new Card(1,Color.YELLOW,this.whateverPath);
		Card secondCard = new Card(2,Color.YELLOW,this.whateverPath);
		Card thirdCard = new Card(3,Color.YELLOW,this.whateverPath);
		givenCards.add(firstCard);
		givenCards.add(secondCard);
		givenCards.add(thirdCard);
		this.stock.refill(givenCards);
		assertEquals(3,this.stock.size());
		assertTrue(this.stock.contains(firstCard));
		assertTrue(this.stock.contains(secondCard));
		assertTrue(this.stock.contains(thirdCard));
	}
	
	@Test(expected=NullPointerException.class)
	public void testFailToRefillCardsDueToNullCollection() {
		this.stock.refill(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailToRefillCardsDueToEmptyCollection() {
		Collection<Card> noCards = new ArrayList<Card>();
		this.stock.refill(noCards);
	}
	
	/* ========================================= DRAW CARD ========================================= */
	
	@Test
	public void testDrawCard() {
		Collection<Card> cardsDrawn;
		cardsDrawn = this.stock.drawCards(1);
		assertEquals(1,cardsDrawn.size());
		cardsDrawn = this.stock.drawCards(7);
		assertEquals(7,cardsDrawn.size());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailToDrawCardDueToNotEnoughCards() {
		this.stock.drawCards(999);
	}
	
	@Test
	public void testDrawOneCard() {
		Card cardDrawn = this.stock.drawOneCard();
		assertNotNull(cardDrawn);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testFailToDrawOneCard() {
		this.stock.drawCards(108);
		this.stock.drawOneCard();
	}
	
	/* ========================================= GETTERS & UTILS ========================================= */
	
	@Test
	public void testCardCount() {
		assertEquals(108,this.stock.size());
		assertEquals(5,this.mockedPioche.size());
	}
	
	@Test
	public void testCardsInsidePioche() throws Exception {
		//Carte01
		assertTrue(this.stock.contains(this.carte01));
		assertTrue(this.mockedPioche.contains(this.carte01));
		//Carte02
		assertTrue(this.stock.contains(this.carte02));
		assertTrue(this.mockedPioche.contains(this.carte02));
		//Carte03
		assertTrue(this.stock.contains(this.carte03));
		assertTrue(this.mockedPioche.contains(this.carte03));
		//Carte04
		assertTrue(this.stock.contains(this.carte04));
		assertTrue(this.mockedPioche.contains(this.carte04));
		//Carte05
		assertTrue(this.stock.contains(this.carte05));
		assertTrue(this.mockedPioche.contains(this.carte05));
		//N'importe quelle autre carte (valide)
		Card carte06 = new Card(9,Color.YELLOW,this.whateverPath);
		PowerMockito.doReturn(this.baseQueue.contains(carte06)).when(mockedPioche,"contains",carte06);
		assertTrue(this.stock.contains(carte06));
		assertFalse(this.mockedPioche.contains(carte06));
	}
	
	@Test
	public void testToString() {
		assertEquals("[Pioche] Contient actuellement 108 cartes",this.stock.toString());
	}
}