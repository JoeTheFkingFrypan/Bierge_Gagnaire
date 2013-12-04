package tests.java.cards.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import main.java.cards.model.basics.Card;
import main.java.cards.model.basics.Color;
import main.java.cards.controller.GameController;
import main.java.console.view.View;

public class GameControllerTest {
	private GameController gameController;
	private View mockedView;
	
	@Before
	public void setup() {
		this.mockedView = mock(View.class);
		this.gameController = new GameController(this.mockedView);
	}

	/* ========================================= CONSTRUCTOR ========================================= */
	
	@Test(expected=NullPointerException.class) 
	public void testFailToCreateGameControllerDueToNullView() {
		this.gameController = new GameController(null);
	}
	
	/* ========================================= CARD DRAW ========================================= */
	
	@Test
	public void testDrawCard() {
		Collection<Card> cardsDrawn;
		cardsDrawn = this.gameController.drawCards(1);
		assertEquals(1,cardsDrawn.size());
		cardsDrawn = this.gameController.drawCards(7);
		assertEquals(7,cardsDrawn.size());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testFailToDrawCardDueToNegativeAmount() {
		this.gameController.drawCards(-999);
	}
	
	@Test
	public void testDrawnOneCard() {
		assertNotNull(this.gameController.drawOneCard());
	}
	
	/* ========================================= PLAY CARD ========================================= */

	/*@Test
	//TODO: fix it
	public void testPlayCardAndShowLastCardPlayed() {
		//NOTE: Both methods "playCard" AND "showLastCardPlayed" are tested at the same time
		//That way we can ensure that every single card is played (or rejected) as intended
		Card redCard = new Card(1,Color.RED);
		tryToPlayAnotherCardAndReturnLastCardSuccessfullyPlayed(redCard);
		Card blueCard = new Card(2,Color.BLUE);
		tryToPlayAnotherCardAndReturnLastCardSuccessfullyPlayed(blueCard);
		Card greenCard = new Card(3,Color.GREEN);
		tryToPlayAnotherCardAndReturnLastCardSuccessfullyPlayed(greenCard);
		Card yellowCard = new Card(4,Color.YELLOW);
		tryToPlayAnotherCardAndReturnLastCardSuccessfullyPlayed(yellowCard);
	}

	private void tryToPlayAnotherCardAndReturnLastCardSuccessfullyPlayed(Card cardToPlay) {
		Card reference = this.gameController.showLastCardPlayed();
		this.gameController.playCard(cardToPlay);
		if(reference.isCompatibleWith(cardToPlay)) {
			assertEquals(cardToPlay,this.gameController.showLastCardPlayed());
		} else {
			assertEquals(reference,this.gameController.showLastCardPlayed());
		}
	}*/

	@Test(expected=NullPointerException.class)
	public void testFailPlayCardDueToNullCard() {
		this.gameController.playCard(null);
	}
}
