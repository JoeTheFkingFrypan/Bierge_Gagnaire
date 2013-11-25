package carteModeleTest;

import static org.junit.Assert.assertEquals;
import main.modele.carteModele.EffetPasserTour;

import org.junit.Before;
import org.junit.Test;


public class EffetPasserTourTest {
	private EffetPasserTour effetPasserTour;
	private String description;

	@Before
	public void setup() {
		this.effetPasserTour = new EffetPasserTour();
		this.description = "Cette carte permet d'empecher le joueur suivant de jouer son tour";
	}

	@Test
	public void testDeclencherEffet() {
		//this.effetPasserTour.declencherEffet();
	}

	@Test
	public void testToString() {
		assertEquals(description, this.effetPasserTour.toString());
	}

	@Test
	public void testAfficherDescription() {
		assertEquals(description, this.effetPasserTour.afficherDescription());
	}
}
