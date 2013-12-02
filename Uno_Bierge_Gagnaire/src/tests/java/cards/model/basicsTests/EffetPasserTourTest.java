package tests.java.cards.model.basicsTests;

import static org.junit.Assert.assertEquals;
import main.java.cards.model.basics.EffetPasserTour;
import main.java.gameContext.model.GameFlags;

import org.junit.Before;
import org.junit.Test;


public class EffetPasserTourTest {
	private EffetPasserTour effetPasserTour;
	private String expectedDescription;
	private String expectedString;
	private GameFlags expectedFlag;

	@Before
	public void setup() {
		this.effetPasserTour = new EffetPasserTour();
		this.expectedDescription = "Le joueur suivant devra passer son tour";
		this.expectedString = "Passe";
		this.expectedFlag = GameFlags.INTERDICTION;
	}

	@Test
	public void testDeclencherEffet() {
		assertEquals(this.expectedFlag,this.effetPasserTour.declencherEffet());
	}

	@Test
	public void testToString() {
		assertEquals(this.expectedString, this.effetPasserTour.toString());
	}

	@Test
	public void testAfficherDescription() {
		assertEquals(this.expectedDescription, this.effetPasserTour.afficherDescription());
	}
}
