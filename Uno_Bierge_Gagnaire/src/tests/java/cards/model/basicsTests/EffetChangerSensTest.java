package tests.java.cards.model.basicsTests;

import static org.junit.Assert.assertEquals;
import main.java.cards.model.basics.EffectReverse;
import main.java.gameContext.model.GameFlags;

import org.junit.Before;
import org.junit.Test;

public class EffetChangerSensTest {
	private EffectReverse effetChangerSens;
	private String expectedDescription;
	private String expectedString;
	private GameFlags expectedFlag;
	
	@Before
	public void setup() {
		this.effetChangerSens = new EffectReverse();
		this.expectedDescription = "Inversion";
		this.expectedString = "Le sens de jeu est inversé";
		this.expectedFlag = GameFlags.REVERSE;
	}
	
	@Test
	public void testDeclencherEffet() {
		assertEquals(this.expectedFlag,this.effetChangerSens.triggerEffect());
	}
	
	@Test
	public void testToString() {
		assertEquals(this.expectedString, this.effetChangerSens.toString());
	}
	
	@Test
	public void testAfficherDescription() {
		assertEquals(this.expectedDescription, this.effetChangerSens.getDescription());
	}
}
