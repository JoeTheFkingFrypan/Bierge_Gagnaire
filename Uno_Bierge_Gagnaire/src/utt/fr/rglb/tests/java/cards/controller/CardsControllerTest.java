package utt.fr.rglb.tests.java.cards.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import org.junit.Before;
import org.junit.Test;

import utt.fr.rglb.main.java.cards.controller.CardsController;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.console.view.View;

import java.util.Collection;

/**
 * Classe de tests unitaires validant le comportement des méthodes de la classe CardsController
 * </br>Utilisation de simulacres pour la vue (Mockito)
 * @see CardsController
 */
public class CardsControllerTest {
	private CardsController gameController;
	private View mockedView;
	
	@Before
	public void setup() {
		this.mockedView = mock(View.class);
		this.gameController = new CardsController(this.mockedView);
		this.gameController.resetCards();
	}

	/* ========================================= CONSTRUCTOR ========================================= */
	
	@Test(expected=NullPointerException.class) 
	public void testFailToCreateGameControllerDueToNullView() {
		this.gameController = new CardsController(null);
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
	//TODO: [GameControllerTest] fix it
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
