package tests.java.cards.model.basicsTests;

import static org.junit.Assert.assertEquals;
import main.java.cards.model.basics.EffectPlus4;
import main.java.gameContext.model.GameFlags;

import org.junit.Before;
import org.junit.Test;

public class EffetPlus4Test {
	private EffectPlus4 effetPlus4;
	private String expectedDescription;
	private String expectedString;
	private GameFlags expectedFlag;

	@Before
	public void setup() {
		this.effetPlus4 = new EffectPlus4();
		this.expectedDescription = "+4";
		this.expectedString = "Le joueur actuel choisit une couleur et le joueur suivant devra piocher 4 cartes";
		this.expectedFlag = GameFlags.PLUS_FOUR;
	}

	@Test
	public void testDeclencherEffet() {
		assertEquals(this.expectedFlag,this.effetPlus4.triggerEffect());
	}

	@Test
	public void testToString() {
		assertEquals(this.expectedString, this.effetPlus4.toString());
	}

	@Test
	public void testAfficherDescription() {
		assertEquals(this.expectedDescription, this.effetPlus4.getDescription());
	}
}
