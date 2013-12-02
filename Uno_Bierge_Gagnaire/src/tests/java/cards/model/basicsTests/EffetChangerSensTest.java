package tests.java.cards.model.basicsTests;

import static org.junit.Assert.assertEquals;
import main.java.cards.model.basics.EffetChangerSens;
import main.java.gameContext.model.GameFlags;

import org.junit.Before;
import org.junit.Test;

public class EffetChangerSensTest {
	private EffetChangerSens effetChangerSens;
	private String expectedDescription;
	private String expectedString;
	private GameFlags expectedFlag;
	
	@Before
	public void setup() {
		this.effetChangerSens = new EffetChangerSens();
		this.expectedDescription = "Le sens de jeu est inversé";
		this.expectedString = "Inversion";
		this.expectedFlag = GameFlags.INVERSION;
	}
	
	@Test
	public void testDeclencherEffet() {
		assertEquals(this.expectedFlag,this.effetChangerSens.declencherEffet());
	}
	
	@Test
	public void testToString() {
		assertEquals(this.expectedString, this.effetChangerSens.toString());
	}
	
	@Test
	public void testAfficherDescription() {
		assertEquals(this.expectedDescription, this.effetChangerSens.afficherDescription());
	}
}
