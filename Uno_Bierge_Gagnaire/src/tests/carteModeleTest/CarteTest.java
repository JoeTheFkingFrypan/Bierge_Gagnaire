package tests.carteModeleTest;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import main.modele.carteModele.Carte;
import main.modele.carteModele.Couleur;

public class CarteTest {
	private Carte c;
	private Integer expectedValue;
	
	@Before
	public void setup() {
		this.expectedValue = new Integer(0);
		this.c = new Carte(this.expectedValue, Couleur.JAUNE);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void failToCreateCardValueTooLow() {
		Carte wayTooLowValue = new Carte(-999,Couleur.ROUGE);
		wayTooLowValue.estSpeciale();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void failToCreateCardValueTooHigh() {
		Carte wayTooHighValue = new Carte(999,Couleur.ROUGE);
		wayTooHighValue.estSpeciale();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void failToCreateInexistingCardIncorrectColor() {
		Carte incorrectColor = new Carte(7,Couleur.JOKER);
		incorrectColor.estSpeciale();
	}
	
	@Test(expected=NullPointerException.class)
	public void failToCreateInexistingCardNullColor() {
		Carte incorrectColor = new Carte(7,null);
		incorrectColor.estSpeciale();
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
	public void testGetCouleur() {
		Couleur expectedColor = Couleur.JAUNE;
		assertEquals(expectedColor, c.getCouleur());
	}
	
	@Test
	public void testAffichage() {
		assertEquals("[CARTE NUMEROTEE] Numero=0, Couleur=JAUNE",this.c.toString());
	}
}
