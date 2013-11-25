package tests.carteModeleTest;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import main.modele.carteModele.Carte;
import main.modele.carteModele.CarteSpeciale;
import main.modele.carteModele.Couleur;
import main.modele.carteModele.Effet;

public class CouleurTest {
	private Couleur couleurBleue;
	private Couleur couleurVerte;
	private Couleur couleurJaune;
	private Couleur couleurRouge;
	private Couleur couleurJoker;
	private Carte carteBleue;
	private Carte carteVerte;
	private Carte carteJaune;
	private Carte carteRouge;
	private Carte carteJoker;
	private int randomValue;
	private Effet mockedEffet;
	
	@Before
	public void setup() {
		this.randomValue = 8;
		this.couleurBleue = Couleur.BLEUE;
		this.couleurVerte = Couleur.VERTE;
		this.couleurJaune = Couleur.JAUNE;
		this.couleurRouge = Couleur.ROUGE;
		this.couleurJoker = Couleur.JOKER;
		this.mockedEffet = mock(Effet.class);
	}
	
	@Test
	public void testGetCouleurAvecCarteBleue() {
		this.carteBleue = new Carte(this.randomValue, couleurBleue);
		assertEquals(this.couleurBleue, this.carteBleue.getCouleur());
	}
	
	@Test
	public void testGetCouleurAvecCarteVerte() {
		this.carteJaune = new Carte(this.randomValue, couleurVerte);
		assertEquals(this.couleurVerte, this.carteJaune.getCouleur());
	}
	
	@Test
	public void testGetCouleurAvecCarteJaune() {
		this.carteVerte = new Carte(this.randomValue, couleurJaune);
		assertEquals(this.couleurJaune, this.carteVerte.getCouleur());
	}
	
	@Test
	public void testGetCouleurAvecCarteRouge() {
		this.carteRouge = new Carte(this.randomValue, couleurRouge);
		assertEquals(this.couleurRouge, this.carteRouge.getCouleur());
	}
	
	@Test
	public void testGetCouleurAvecCarteJoker() {
		this.carteJoker = new CarteSpeciale(this.randomValue, this.couleurJoker, this.mockedEffet);
		assertEquals(this.couleurJoker, this.carteJoker.getCouleur());
	}
}
