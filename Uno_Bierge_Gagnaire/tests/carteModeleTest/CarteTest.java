package carteModeleTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import carteModele.Carte;
import carteModele.Couleur;

public class CarteTest {
	private Carte c;
	
	@Before
	public void setup() {
		this.c = new Carte(0, Couleur.JAUNE);
	}

	@Test
	public void testEstSpeciale() {
		assertFalse(c.estSpeciale());
	}

	@Test
	public void testGetValeur() {
		assertEquals(0, c.getValeur());
	}
	
	@Test
	public void testGetNombre() {
		assertEquals(0, c.getNombre());
	}
	
	@Test
	public void testGetCouleur() {
		Couleur expectedColor = Couleur.JAUNE;
		assertEquals(expectedColor, c.getCouleur());
	}
	
	@Test
	public void testAffichage() {
		c.afficherCouleur();
		c.afficherNombre();
		c.afficherValeur();
	}
}
