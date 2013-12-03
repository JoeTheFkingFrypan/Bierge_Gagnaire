package tests.java.cards.model.basicsTests;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import main.java.cards.model.basics.Card;
import main.java.cards.model.basics.CardSpecial;
import main.java.cards.model.basics.Color;
import main.java.cards.model.basics.Effect;

public class CouleurTest {
	private Color couleurBleue;
	private Color couleurVerte;
	private Color couleurJaune;
	private Color couleurRouge;
	private Color couleurJoker;
	private Card carteBleue;
	private Card carteVerte;
	private Card carteJaune;
	private Card carteRouge;
	private Card carteJoker;
	private int randomValue;
	private Effect mockedEffet;
	
	@Before
	public void setup() {
		this.randomValue = 8;
		this.couleurBleue = Color.BLUE;
		this.couleurVerte = Color.GREEN;
		this.couleurJaune = Color.YELLOW;
		this.couleurRouge = Color.RED;
		this.couleurJoker = Color.JOKER;
		this.mockedEffet = mock(Effect.class);
	}
	
	@Test
	public void testGetCouleurAvecCarteBleue() {
		this.carteBleue = new Card(this.randomValue, couleurBleue);
		assertEquals(this.couleurBleue, this.carteBleue.getCouleur());
	}
	
	@Test
	public void testGetCouleurAvecCarteVerte() {
		this.carteJaune = new Card(this.randomValue, couleurVerte);
		assertEquals(this.couleurVerte, this.carteJaune.getCouleur());
	}
	
	@Test
	public void testGetCouleurAvecCarteJaune() {
		this.carteVerte = new Card(this.randomValue, couleurJaune);
		assertEquals(this.couleurJaune, this.carteVerte.getCouleur());
	}
	
	@Test
	public void testGetCouleurAvecCarteRouge() {
		this.carteRouge = new Card(this.randomValue, couleurRouge);
		assertEquals(this.couleurRouge, this.carteRouge.getCouleur());
	}
	
	@Test
	public void testGetCouleurAvecCarteJoker() {
		this.carteJoker = new CardSpecial(this.randomValue, this.couleurJoker, this.mockedEffet);
		assertEquals(this.couleurJoker, this.carteJoker.getCouleur());
	}
}
