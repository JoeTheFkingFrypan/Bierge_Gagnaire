package tests.java.cards.model.basicsTests;

import static org.junit.Assert.assertEquals;
import main.java.cards.model.basics.EffetPiocherCarte;
import main.java.gameContext.model.GameFlags;

import org.junit.Before;
import org.junit.Test;


public class EffetPiocherCarteTest {
	private EffetPiocherCarte effetPiocher2Cartes;
	private EffetPiocherCarte effetPiocher7Cartes;
	private String expectedDescription2cards;
	private String expectedDescription7cards;
	private String expectedString2Cards;
	private String expectedString7Cards;
	private GameFlags expectedFlag;

	@Before
	public void setup() {
		this.effetPiocher2Cartes = new EffetPiocherCarte(2);
		this.effetPiocher7Cartes = new EffetPiocherCarte(7);
		this.expectedDescription2cards = "Le joueur suivant devra piocher 2 cartes";
		this.expectedDescription7cards = "Le joueur suivant devra piocher 7 cartes";
		this.expectedString2Cards = "+2";
		this.expectedString7Cards = "+7";
		this.expectedFlag = GameFlags.PLUS_TWO;
	}

	@Test
	public void testDeclencherEffet() {
		assertEquals(this.expectedFlag,this.effetPiocher2Cartes.declencherEffet());
		assertEquals(this.expectedFlag,this.effetPiocher7Cartes.declencherEffet());
	}

	@Test
	public void testToString() {
		assertEquals(this.expectedString2Cards, this.effetPiocher2Cartes.toString());
		assertEquals(this.expectedString7Cards, this.effetPiocher7Cartes.toString());
	}

	@Test
	public void testAfficherDescription() {
		assertEquals(this.expectedDescription2cards, this.effetPiocher2Cartes.afficherDescription());
		assertEquals(this.expectedDescription7cards, this.effetPiocher7Cartes.afficherDescription());
	}
}
