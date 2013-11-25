package carteModeleTest;

import static org.junit.Assert.assertEquals;
import main.modele.carteModele.EffetPiocherCarte;

import org.junit.Before;
import org.junit.Test;


public class EffetPiocherCarteTest {
	private EffetPiocherCarte effetPiocher2Cartes;
	private EffetPiocherCarte effetPiocher4Cartes;
	private String description2cartes;
	private String description4cartes;

	@Before
	public void setup() {
		this.effetPiocher2Cartes = new EffetPiocherCarte(2);
		this.effetPiocher4Cartes = new EffetPiocherCarte(4);
		this.description2cartes = "Cette carte permet de faire piocher 2 cartes à l'adversaire";
		this.description4cartes = "Cette carte permet de faire piocher 4 cartes à l'adversaire";
	}

	@Test
	public void testDeclencherEffet() {
		this.effetPiocher2Cartes.declencherEffet();
		this.effetPiocher4Cartes.declencherEffet();
	}

	@Test
	public void testToString() {
		assertEquals(description2cartes, this.effetPiocher2Cartes.toString());
		assertEquals(description4cartes, this.effetPiocher4Cartes.toString());
	}

	@Test
	public void testAfficherDescription() {
		assertEquals(description2cartes, this.effetPiocher2Cartes.afficherDescription());
		assertEquals(description4cartes, this.effetPiocher4Cartes.afficherDescription());
	}
}
