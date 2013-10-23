package carteModeleTest;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import carteModele.Carte;
import carteModele.CarteSpeciale;
import carteModele.Couleur;
import carteModele.Effet;

public class CarteSpecialeTest {
	private Carte c;
	private Couleur couleur;
	private CarteSpeciale cs;
	private Effet effet;
	
	@Before
	public void setup() {
		this.effet = mock(Effet.class);
		this.couleur = Couleur.BLEUE;
		this.c = new CarteSpeciale(0, this.couleur, this.effet);
		this.cs = new CarteSpeciale(0, this.couleur, this.effet);
	}
	
	@Test
	public void testGetValeur() {
		assertEquals(0,this.c.getValeur());
		assertEquals(0,this.cs.getValeur());
	}
	
	@Test
	public void testGetCouleur() {
		Couleur expectedColor = Couleur.BLEUE;
		assertEquals(expectedColor, this.c.getCouleur());
		assertEquals(expectedColor, this.cs.getCouleur());
	}
	
	@Test
	public void testEstSpeciale() {
		assertTrue(this.c.estSpeciale());
		assertTrue(this.cs.estSpeciale());
	}
	
	@Test
	public void testDeclencherEffet() {
		//Pour rappel une seules les cartes spéciales ont un effet (pas les cartes classiques)
		this.cs.declencherEffet();
	}
	
	@Test
	public void testAffichage() {
		this.c.afficherCouleur();
		this.c.afficherNombre();
		this.c.afficherValeur();
		this.cs.afficherCouleur();
		this.cs.afficherNombre();
		this.cs.afficherValeur();
	}
}
