package utt.fr.rglb.tests.java.cards.model.basicsTests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import utt.fr.rglb.main.java.cards.model.basics.EffectSkip;
import utt.fr.rglb.main.java.game.model.GameFlag;


public class EffetPasserTourTest {
	private EffectSkip effetPasserTour;
	private String expectedDescription;
	private String expectedString;
	private GameFlag expectedFlag;

	@Before
	public void setup() {
		this.effetPasserTour = new EffectSkip();
		this.expectedString = "Le joueur suivant devra passer son tour";
		this.expectedDescription = "Passe";
		this.expectedFlag = GameFlag.SKIP;
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
