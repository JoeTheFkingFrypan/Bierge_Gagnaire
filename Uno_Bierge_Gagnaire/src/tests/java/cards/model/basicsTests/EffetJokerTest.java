package tests.java.cards.model.basicsTests;

import static org.junit.Assert.assertEquals;
import main.java.cards.model.basics.EffetJoker;
import main.java.gameContext.model.GameFlags;

import org.junit.Before;
import org.junit.Test;

public class EffetJokerTest {
	private EffetJoker effetJoker;
	private String expectedDescription;
	private String expectedString;
	private GameFlags expectedFlag;

	@Before
	public void setup() {
		this.effetJoker = new EffetJoker();
		this.expectedDescription = "Le joueur actuel doit choisir une couleur";
		this.expectedString = "Joker";
		this.expectedFlag = GameFlags.COLOR_PICK;
	}

	@Test
	public void testDeclencherEffet() {
		assertEquals(this.expectedFlag,this.effetJoker.declencherEffet());
	}

	@Test
	public void testToString() {
		assertEquals(this.expectedString, this.effetJoker.toString());
	}

	@Test
	public void testAfficherDescription() {
		assertEquals(this.expectedDescription, this.effetJoker.afficherDescription());
	}
}
