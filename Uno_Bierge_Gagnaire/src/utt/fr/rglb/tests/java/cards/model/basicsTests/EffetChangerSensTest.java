package utt.fr.rglb.tests.java.cards.model.basicsTests;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import utt.fr.rglb.main.java.cards.model.basics.EffectReverse;
import utt.fr.rglb.main.java.game.model.GameFlag;

/**
 * Classe de tests unitaires validant le comportement des méthodes de la classe EffectReverse
 * @see EffectReverse
 */
public class EffetChangerSensTest {
	private EffectReverse effetChangerSens;
	private String expectedDescription;
	private String expectedString;
	private GameFlag expectedFlag;
	
	@Before
	public void setup() {
		this.effetChangerSens = new EffectReverse();
		this.expectedDescription = "Inversion";
		this.expectedString = "Le sens de jeu est inversé";
		this.expectedFlag = GameFlag.REVERSE;
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
