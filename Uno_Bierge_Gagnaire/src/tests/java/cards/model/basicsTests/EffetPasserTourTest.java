package tests.java.cards.model.basicsTests;

import static org.junit.Assert.assertEquals;
import main.java.cards.model.basics.EffectSkip;
import main.java.gameContext.model.GameFlags;

import org.junit.Before;
import org.junit.Test;


public class EffetPasserTourTest {
	private EffectSkip effetPasserTour;
	private String expectedDescription;
	private String expectedString;
	private GameFlags expectedFlag;

	@Before
	public void setup() {
		this.effetPasserTour = new EffectSkip();
		this.expectedString = "Le joueur suivant devra passer son tour";
		this.expectedDescription = "Passe";
		this.expectedFlag = GameFlags.SKIP;
	}

	@Test
	public void testDeclencherEffet() {
		assertEquals(this.expectedFlag,this.effetPasserTour.triggerEffect());
	}

	@Test
	public void testToString() {
		assertEquals(this.expectedString, this.effetPasserTour.toString());
	}

	@Test
	public void testAfficherDescription() {
		assertEquals(this.expectedDescription, this.effetPasserTour.getDescription());
	}
}
