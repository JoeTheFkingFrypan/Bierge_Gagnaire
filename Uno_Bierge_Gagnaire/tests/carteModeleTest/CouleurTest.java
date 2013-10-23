package carteModeleTest;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import carteModele.Carte;
import carteModele.Couleur;

public class CouleurTest {
	private Couleur bleue;
	private Couleur verte;
	private Couleur jaune;
	private Couleur rouge;
	private Couleur joker;
	private Carte carteBleue;
	private Carte carteVerte;
	private Carte carteJaune;
	private Carte carteRouge;
	private Carte carteJoker;
	
	@Before
	public void setup() {
		final int valeur = 0;
		this.bleue = Couleur.BLEUE;
		this.verte = Couleur.VERTE;
		this.jaune = Couleur.JAUNE;
		this.rouge = Couleur.ROUGE;
		this.joker = Couleur.JOKER;
		this.carteBleue = new Carte(valeur, bleue);
		this.carteJaune = new Carte(valeur, verte);
		this.carteVerte = new Carte(valeur, jaune);
		this.carteRouge = new Carte(valeur, rouge);
		this.carteJoker = new Carte(valeur, joker);
	}
	
	@Test
	public void testGetCouleurAvecCarteBleue() {
		final Couleur expectedColor = Couleur.BLEUE;
		assertEquals(expectedColor, this.carteBleue.getCouleur());
	}
	
	@Test
	public void testGetCouleurAvecCarteVerte() {
		final Couleur expectedColor = Couleur.VERTE;
		assertEquals(expectedColor, this.carteJaune.getCouleur());
	}
	
	@Test
	public void testGetCouleurAvecCarteJaune() {
		final Couleur expectedColor = Couleur.JAUNE;
		assertEquals(expectedColor, this.carteVerte.getCouleur());
	}
	
	@Test
	public void testGetCouleurAvecCarteRouge() {
		final Couleur expectedColor = Couleur.ROUGE;
		assertEquals(expectedColor, this.carteRouge.getCouleur());
	}
	
	@Test
	public void testGetCouleurAvecCarteJoker() {
		final Couleur expectedColor = Couleur.JOKER;
		assertEquals(expectedColor, this.carteJoker.getCouleur());
	}
}
