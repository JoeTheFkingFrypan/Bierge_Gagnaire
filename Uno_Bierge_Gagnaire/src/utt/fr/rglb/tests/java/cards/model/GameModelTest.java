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


public class GameModelTest {
	private CardsModel gameModel;
	private Effect mockedEffect;

	@Before
	public void setup() {
		this.gameModel = new CardsModel();
		this.mockedEffect = mock(Effect.class);
	}

	/* ========================================= CARD DRAW ========================================= */
	
	@Test
	public void testDrawOneCard() {
		assertEquals(1,this.gameModel.getPileSize());
		assertEquals(107,this.gameModel.getStockSize());
		Collection<Card> cardsDrawn = this.gameModel.drawCards(1);
		assertEquals(1,cardsDrawn.size());
		assertEquals(1,this.gameModel.getPileSize());
		assertEquals(106,this.gameModel.getStockSize());
	}

	@Test
	public void testDrawSevenCards() {
		assertEquals(1,this.gameModel.getPileSize());
		assertEquals(107,this.gameModel.getStockSize());
		Collection<Card> cardsDrawn = this.gameModel.drawCards(7);
		assertEquals(7,cardsDrawn.size());
		assertEquals(1,this.gameModel.getPileSize());
		assertEquals(100,this.gameModel.getStockSize());
	}

	/* ========================================= PLAY CARD ========================================= */
	
	@Test
	public void testPlayCard() {
		Card randomCard = new Card(7,Color.BLUE);
		Card anotherRandomCard = new Card(5,Color.BLUE);
		Card yetAnotherRandomCard = new Card(2,Color.RED);

		int expectedPileSize = 1;

		Card referenceCard = this.gameModel.showLastCardPlayed();
		expectedPileSize = playOneCardAndAssertBasedOnPlayability(expectedPileSize,randomCard,referenceCard);

		referenceCard = this.gameModel.showLastCardPlayed();
		expectedPileSize = playOneCardAndAssertBasedOnPlayability(expectedPileSize,anotherRandomCard,referenceCard);

		referenceCard = this.gameModel.showLastCardPlayed();
		expectedPileSize = playOneCardAndAssertBasedOnPlayability(expectedPileSize,yetAnotherRandomCard,referenceCard);
	}

	private int playOneCardAndAssertBasedOnPlayability(int currentPileSize, Card cardToPlay, Card referenceCard) {
		if(cardToPlay.isCompatibleWith(referenceCard)) {
			int increasedCurrentPileSize = currentPileSize + 1;
			this.gameModel.playCard(cardToPlay);
			assertEquals(cardToPlay,this.gameModel.showLastCardPlayed());
			assertEquals(increasedCurrentPileSize,this.gameModel.getPileSize());
			return increasedCurrentPileSize;
		} else {
			assertEquals(referenceCard,this.gameModel.showLastCardPlayed());
			assertEquals(currentPileSize,this.gameModel.getPileSize());
			return currentPileSize;
		}
	}

	@Test
	public void testRefillCards() {
		Collection<Card> oneHundredAndSevenCards = this.gameModel.drawCards(107);
		playAsMuchCardsAsPossibleFrom(oneHundredAndSevenCards);
		this.gameModel.drawCards(10);
	}

	private void playAsMuchCardsAsPossibleFrom(Collection<Card> cardsToPlay) {
		for(Card c : cardsToPlay) {
			this.gameModel.playCard(c);
		}
	}
	
	/* ========================================= UTILS ========================================= */
	
	@Test
	public void testGetStockSize() {
		assertEquals(107,this.gameModel.getStockSize());
		this.gameModel.drawOneCard();
		assertEquals(106,this.gameModel.getStockSize());
		this.gameModel.drawCards(6);
		assertEquals(100,this.gameModel.getStockSize());
	}
	
	@Test
	public void testGetPileSize() {
		assertEquals(1,this.gameModel.getPileSize());
		Card firstCard = this.gameModel.showLastCardPlayed();
		this.gameModel.playCard(generateCompatibleCardFrom(firstCard));
		assertEquals(2,this.gameModel.getPileSize());
	}
	
	private Card generateCompatibleCardFrom(Card firstCard) {
		if(firstCard.isSpecial()) {
			return new CardSpecial(20,firstCard.getCouleur(),this.mockedEffect);
		} else {
			return new Card(2,firstCard.getCouleur());
		}
	}
	
}
