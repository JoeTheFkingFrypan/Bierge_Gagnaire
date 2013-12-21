package utt.fr.rglb.tests.java.cards.model.basicsTests;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import utt.fr.rglb.main.java.cards.model.basics.EffetJoker;
import utt.fr.rglb.main.java.game.model.GameFlag;

/**
 * Classe de tests unitaires validant le comportement des méthodes de la classe EffetJoker
 * @see EffetJoker
 */
public class EffetJokerTest {
	private EffetJoker effetJoker;
	private String expectedDescription;
	private String expectedString;
	private GameFlag expectedFlag;

	@Before
	public void setup() {
		this.effetJoker = new EffetJoker();
		this.expectedString = "Le joueur actuel doit choisir une couleur";
		this.expectedDescription = "Joker";
		this.expectedFlag = GameFlag.COLOR_PICK;
	}

	@Test
	public void testDeclencherEffet() {
		assertEquals(this.expectedFlag,this.effetJoker.triggerEffect());
	}

	@Test
	public void testToString() {
		assertEquals(this.expectedString, this.effetJoker.toString());
	}

	@Test
	public void testAfficherDescription() {
		assertEquals(this.expectedDescription, this.effetJoker.getDescription());
	}
}
