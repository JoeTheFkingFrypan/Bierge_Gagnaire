package carteModeleTest;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import main.modele.carteModele.Carte;
import main.modele.carteModele.CarteSpeciale;
import main.modele.carteModele.Couleur;
import main.modele.carteModele.Effet;

import org.junit.Before;
import org.junit.Test;


public class CarteSpecialeTest {
	private Carte c;
	private Couleur couleur;
	private CarteSpeciale cs;
	private Effet effet;
	private Integer expectedLowValue;
	private Integer expectedHighValue;
	
	@Before
	public void setup() {
		this.effet = mock(Effet.class);
		this.couleur = Couleur.BLEUE;
		this.expectedLowValue = new Integer(0);
		this.expectedHighValue = new Integer(50);
		this.c = new CarteSpeciale(this.expectedLowValue, this.couleur, this.effet);
		this.cs = new CarteSpeciale(this.expectedHighValue, this.couleur, this.effet);
		when(this.effet.toString()).thenReturn("Super effet special");
	}
	
	@Test
	public void testGetValeur() {
		assertEquals(this.expectedLowValue,this.c.getValeur());
		assertEquals(this.expectedHighValue,this.cs.getValeur());
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
	public void testAffichageCarte() {		
		assertEquals("[CARTE SPECIALE] Pas de numero, Valeur=0, Couleur=BLEUE, Effet=Super effet special",this.c.toString());
		assertEquals("[CARTE SPECIALE] Pas de numero, Valeur=50, Couleur=BLEUE, Effet=Super effet special",this.cs.toString());
	}
}
