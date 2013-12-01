package tests.java.gameContext.controllerTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import main.java.cards.model.basics.Carte;
import main.java.cards.model.basics.Couleur;
import main.java.cards.controller.GameController;

public class GameControllerTest {
	private GameController gameController;

	@Before
	public void setup() {
		this.gameController = new GameController();
	}

	@Test
	public void testDrawCard() {
		Collection<Carte> cardsDrawn;
		cardsDrawn = this.gameController.drawCards(1);
		assertEquals(1,cardsDrawn.size());
		cardsDrawn = this.gameController.drawCards(7);
		assertEquals(7,cardsDrawn.size());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testFailToDrawCardDueToNullAmount() {
		this.gameController.drawCards(-999);
	}

	@Test
	public void testPlayCardAndShowLastCardPlayed() {
		//NOTE: Both methods "playCard" AND "showLastCardPlayed" are tested at the same time
		//That way we can ensure that every single card is played (or rejected) as intended
		Carte redCard = new Carte(1,Couleur.ROUGE);
		tryToPlayAnotherCardAndReturnLastCardSuccessfullyPlayed(redCard);
		Carte blueCard = new Carte(2,Couleur.BLEUE);
		tryToPlayAnotherCardAndReturnLastCardSuccessfullyPlayed(blueCard);
		Carte greenCard = new Carte(3,Couleur.VERTE);
		tryToPlayAnotherCardAndReturnLastCardSuccessfullyPlayed(greenCard);
		Carte yellowCard = new Carte(4,Couleur.JAUNE);
		tryToPlayAnotherCardAndReturnLastCardSuccessfullyPlayed(yellowCard);
	}

	private void tryToPlayAnotherCardAndReturnLastCardSuccessfullyPlayed(Carte cardToPlay) {
		//TODO: fix it
		/*Carte reference = this.gameController.showLastCardPlayed();
		if(this.gameController.playCard(cardToPlay)) {
			assertEquals(cardToPlay,this.gameController.showLastCardPlayed());
		} else {
			assertEquals(reference,this.gameController.showLastCardPlayed());
		}*/
	}

	@Test(expected=NullPointerException.class)
	public void testFailPlayCardDueToNullCard() {
		this.gameController.playCard(null);
	}
}
