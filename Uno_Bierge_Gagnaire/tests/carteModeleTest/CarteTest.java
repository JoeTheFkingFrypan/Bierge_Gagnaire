package carteModeleTest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import main.modele.carteModele.Carte;
import main.modele.carteModele.Couleur;
import main.modele.carteModele.CarteModeleException;

public class CarteTest {
	private Carte c;
	private Integer expectedValue;
	
	@Before
	public void setup() {
		this.expectedValue = new Integer(0);
		this.c = new Carte(this.expectedValue, Couleur.JAUNE);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void failToCreateInexistingCard() {
		Carte impossibleCombinaisonCard = new Carte(-9999999,Couleur.ROUGE);
	}

	@Test
	public void testEstSpeciale() {
		assertFalse(c.estSpeciale());
	}

	@Test
	public void testGetValeur() {
		assertEquals(this.expectedValue, c.getValeur());
	}
	
	@Test
	public void testGetNombre() {
		assertEquals(this.expectedValue, c.getNombre());
	}
	
	@Test
	public void testGetCouleur() {
		Couleur expectedColor = Couleur.JAUNE;
		assertEquals(expectedColor, c.getCouleur());
	}
	
	@Test
	public void testAffichage() {
		assertEquals("[CARTE NUMEROTEE] Numero=0, Valeur=0, Couleur=JAUNE",this.c.toString());
	}
}
