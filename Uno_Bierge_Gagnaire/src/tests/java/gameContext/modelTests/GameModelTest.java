package tests.java.gameContext.modelTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import main.java.cards.model.basics.Carte;
import main.java.cards.model.basics.Couleur;
import main.java.cards.model.GameModel;

public class GameModelTest {
	private GameModel gameModel;

	@Before
	public void setup() {
		this.gameModel = new GameModel();
	}

	@Test
	public void testDrawOneCard() {
		assertEquals(1,this.gameModel.getPileSize());
		assertEquals(107,this.gameModel.getStockSize());
		Collection<Carte> cardsDrawn = this.gameModel.drawCards(1);
		assertEquals(1,cardsDrawn.size());
		assertEquals(1,this.gameModel.getPileSize());
		assertEquals(106,this.gameModel.getStockSize());
	}

	@Test
	public void testDrawSevenCards() {
		assertEquals(1,this.gameModel.getPileSize());
		assertEquals(107,this.gameModel.getStockSize());
		Collection<Carte> cardsDrawn = this.gameModel.drawCards(7);
		assertEquals(7,cardsDrawn.size());
		assertEquals(1,this.gameModel.getPileSize());
		assertEquals(100,this.gameModel.getStockSize());
	}

	@Test
	public void testPlayCard() {
		Carte randomCard = new Carte(7,Couleur.BLEUE);
		Carte anotherRandomCard = new Carte(5,Couleur.BLEUE);
		Carte yetAnotherRandomCard = new Carte(2,Couleur.ROUGE);

		int expectedPileSize = 1;

		Carte referenceCard = this.gameModel.showLastCardPlayed();
		expectedPileSize = playOneCardAndAssertBasedOnPlayability(expectedPileSize,randomCard,referenceCard);

		referenceCard = this.gameModel.showLastCardPlayed();
		expectedPileSize = playOneCardAndAssertBasedOnPlayability(expectedPileSize,anotherRandomCard,referenceCard);

		referenceCard = this.gameModel.showLastCardPlayed();
		expectedPileSize = playOneCardAndAssertBasedOnPlayability(expectedPileSize,yetAnotherRandomCard,referenceCard);
	}

	private int playOneCardAndAssertBasedOnPlayability(int currentPileSize, Carte cardToPlay, Carte referenceCard) {
		//TODO: fix it
		/*
		if(cardToPlay.isCompatibleWith(referenceCard)) {
			int increasedCurrentPileSize = currentPileSize + 1;
			assertTrue(this.gameModel.playCard(cardToPlay));
			assertEquals(increasedCurrentPileSize,this.gameModel.getPileSize());
			return increasedCurrentPileSize;
		} else {
			assertFalse(this.gameModel.playCard(cardToPlay));
			assertEquals(currentPileSize,this.gameModel.getPileSize());
			return currentPileSize;
		}
		*/
		return 0;
	}

	@Test
	public void testRefillCards() {
		Collection<Carte> oneHundredAndSevenCards = this.gameModel.drawCards(107);
		playAsMuchCardsAsPossibleFrom(oneHundredAndSevenCards);
		this.gameModel.drawCards(10);
	}

	private void playAsMuchCardsAsPossibleFrom(Collection<Carte> cardsToPlay) {
		for(Carte c : cardsToPlay) {
			this.gameModel.playCard(c);
		}
	}
}
