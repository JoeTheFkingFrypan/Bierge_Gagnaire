package utt.fr.rglb.tests.java.cards.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import org.junit.Before;
import org.junit.Test;

import utt.fr.rglb.main.java.cards.model.CardsModel;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.CardSpecial;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.cards.model.basics.Effect;

import java.util.Collection;

/**
 * Classe de tests unitaires validant le comportement des méthodes de la classe CardsController
 * </br>Utilisation de simulacres pour l'effet des cartes spéciales (Mockito)
 * @see CardsModel
 */
public class CardsModelTest {
	private CardsModel cardsModel;
	private Effect mockedEffect;

	@Before
	public void setup() {
		this.cardsModel = new CardsModel();
		this.cardsModel.resetCards();
		this.mockedEffect = mock(Effect.class);
	}
	
	@Test
	public void testDrawStarterCard() {
		//L'ordre de cartes étant aléatoires, il est impossible de postuler sur la valeur et/ou la couleur de la 1ère carte qui sera tirée
		assertNotNull(this.cardsModel.drawStarterCard());
	}

	/* ========================================= CARD DRAW ========================================= */
	
	@Test
	public void testDrawOneCard() {
		assertEquals(0,this.cardsModel.getPileSize());
		assertEquals(108,this.cardsModel.getStockSize());
		Collection<Card> cardsDrawn = this.cardsModel.drawCards(1);
		assertEquals(1,cardsDrawn.size());
		assertEquals(0,this.cardsModel.getPileSize());
		assertEquals(107,this.cardsModel.getStockSize());
	}

	@Test
	public void testDrawSevenCards() {
		assertEquals(0,this.cardsModel.getPileSize());
		assertEquals(108,this.cardsModel.getStockSize());
		Collection<Card> cardsDrawn = this.cardsModel.drawCards(7);
		assertEquals(7,cardsDrawn.size());
		assertEquals(0,this.cardsModel.getPileSize());
		assertEquals(101,this.cardsModel.getStockSize());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testImpossibleToDrawCardsDueToNullAmount() {
		this.cardsModel.drawCards(-2);
	}
	
	/* ========================================= PLAY CARD ========================================= */
	
	@Test
	public void testPlayCard() {
		Card referenceCard = new Card (5,Color.RED);
		Card randomCard = new Card(7,Color.BLUE);
		Card anotherRandomCard = new Card(5,Color.BLUE);
		Card yetAnotherRandomCard = new Card(2,Color.RED);

		int expectedPileSize = 1;
		this.cardsModel.playCard(referenceCard);
		
		referenceCard = this.cardsModel.showLastCardPlayed();
		expectedPileSize = playOneCardAndAssertBasedOnPlayability(expectedPileSize,randomCard,referenceCard);

		referenceCard = this.cardsModel.showLastCardPlayed();
		expectedPileSize = playOneCardAndAssertBasedOnPlayability(expectedPileSize,anotherRandomCard,referenceCard);

		referenceCard = this.cardsModel.showLastCardPlayed();
		expectedPileSize = playOneCardAndAssertBasedOnPlayability(expectedPileSize,yetAnotherRandomCard,referenceCard);
	}

	private int playOneCardAndAssertBasedOnPlayability(int currentPileSize, Card cardToPlay, Card referenceCard) {
		if(cardToPlay.isCompatibleWith(referenceCard)) {
			int increasedCurrentPileSize = currentPileSize + 1;
			this.cardsModel.playCard(cardToPlay);
			assertEquals(cardToPlay,this.cardsModel.showLastCardPlayed());
			assertEquals(increasedCurrentPileSize,this.cardsModel.getPileSize());
			return increasedCurrentPileSize;
		} else {
			assertEquals(referenceCard,this.cardsModel.showLastCardPlayed());
			assertEquals(currentPileSize,this.cardsModel.getPileSize());
			return currentPileSize;
		}
	}

	@Test
	public void testRefillCards() {
		Collection<Card> oneHundredAndSevenCards = this.cardsModel.drawCards(107);
		playAsMuchCardsAsPossibleFrom(oneHundredAndSevenCards);
		this.cardsModel.drawCards(10);
	}

	private void playAsMuchCardsAsPossibleFrom(Collection<Card> cardsToPlay) {
		for(Card c : cardsToPlay) {
			this.cardsModel.playCard(c);
		}
	}
	
	/* ========================================= GLOBAL COLOR ========================================= */
	
	@Test
	public void testGlobalColorIsSet() {
		assertFalse(this.cardsModel.globalColorIsSet());
	}
	
	@Test
	public void testSetGlobalColor() {
		this.cardsModel.setGlobalColor(Color.RED);
		assertTrue(this.cardsModel.globalColorIsSet());
		assertEquals(Color.RED,this.cardsModel.getGlobalColor());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailToSetGlobalColorDueToInvalidColor() {
		this.cardsModel.setGlobalColor(Color.JOKER);
	}
	
	/* ========================================= UTILS ========================================= */
	
	@Test
	public void testGetStockSize() {
		assertEquals(108,this.cardsModel.getStockSize());
		this.cardsModel.drawOneCard();
		assertEquals(107,this.cardsModel.getStockSize());
		this.cardsModel.drawCards(7);
		assertEquals(100,this.cardsModel.getStockSize());
	}
	
	@Test
	public void testGetPileSize() {
		assertEquals(0,this.cardsModel.getPileSize());
		this.cardsModel.playCard(new Card(2,Color.RED));
		assertEquals(1,this.cardsModel.getPileSize());
		Card firstCard = this.cardsModel.showLastCardPlayed();
		this.cardsModel.playCard(generateCompatibleCardFrom(firstCard));
	}
	
	private Card generateCompatibleCardFrom(Card firstCard) {
		if(firstCard.isSpecial()) {
			return new CardSpecial(20,firstCard.getColor(),this.mockedEffect);
		} else {
			return new Card(2,firstCard.getColor());
		}
	}
	
}
