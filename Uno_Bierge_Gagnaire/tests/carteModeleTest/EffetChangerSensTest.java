package carteModeleTest;

import static org.junit.Assert.assertEquals;
import main.modele.carteModele.EffetChangerSens;

import org.junit.Before;
import org.junit.Test;


public class EffetChangerSensTest {
	private EffetChangerSens effetChangerSens;
	private String description;
	
	@Before
	public void setup() {
		this.effetChangerSens = new EffetChangerSens();
		this.description = "Cette carte permet d'inverser le sens du jeu";
	}
	
	@Test
	public void testDeclencherEffet() {
		//this.effetChangerSens.declencherEffet();
	}
	
	@Test
	public void testToString() {
		assertEquals(description, this.effetChangerSens.toString());
	}
	
	@Test
	public void testAfficherDescription() {
		assertEquals(description, this.effetChangerSens.afficherDescription());
	}
}
