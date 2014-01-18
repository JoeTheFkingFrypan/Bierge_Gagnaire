package utt.fr.rglb.tests.java.cards.model.basicsTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import utt.fr.rglb.main.java.cards.model.basics.Color;

/**
 * Classe de tests unitaires validant le comportement des méthodes de la classe Color
 * </br>Utilisation de simulacres pour l'effet des cartes spéciales (Mockito)
 * @see Color
 */
public class ColorTest {
	private Color redColor;
	private Color blueColor;
	private Color greenColor;
	private Color yellowColor;
	private Color jokerColor;
	
	@Before
	public void setup() {
		this.redColor = Color.RED;
		this.blueColor = Color.BLUE;
		this.greenColor = Color.GREEN;
		this.yellowColor = Color.YELLOW;
		this.jokerColor = Color.JOKER;
	}
	
	@Test
	public void testCompareToRed() {
		//RED
		assertEquals(-4,this.redColor.compareTo(this.jokerColor));
		assertEquals(-3,this.redColor.compareTo(this.yellowColor));
		assertEquals(-2,this.redColor.compareTo(this.greenColor));
		assertEquals(-1,this.redColor.compareTo(this.blueColor));
		assertEquals(0,this.redColor.compareTo(this.redColor));
		//BLUE
		assertEquals(-3,this.blueColor.compareTo(this.jokerColor));
		assertEquals(-2,this.blueColor.compareTo(this.yellowColor));
		assertEquals(-1,this.blueColor.compareTo(this.greenColor));
		assertEquals(0,this.blueColor.compareTo(this.blueColor));
		assertEquals(1,this.blueColor.compareTo(this.redColor));
		//GREEN
		assertEquals(-2,this.greenColor.compareTo(this.jokerColor));
		assertEquals(-1,this.greenColor.compareTo(this.yellowColor));
		assertEquals(0,this.greenColor.compareTo(this.greenColor));
		assertEquals(1,this.greenColor.compareTo(this.blueColor));
		assertEquals(2,this.greenColor.compareTo(this.redColor));
		//YELLOW
		assertEquals(-1,this.yellowColor.compareTo(this.jokerColor));
		assertEquals(0,this.yellowColor.compareTo(this.yellowColor));
		assertEquals(1,this.yellowColor.compareTo(this.greenColor));
		assertEquals(2,this.yellowColor.compareTo(this.blueColor));
		assertEquals(3,this.yellowColor.compareTo(this.redColor));
		//JOKER
		assertEquals(0,this.jokerColor.compareTo(this.jokerColor));
		assertEquals(1,this.jokerColor.compareTo(this.yellowColor));
		assertEquals(2,this.jokerColor.compareTo(this.greenColor));
		assertEquals(3,this.jokerColor.compareTo(this.blueColor));
		assertEquals(4,this.jokerColor.compareTo(this.redColor));
	}
	
	@Test(expected=NullPointerException.class)
	public void failToCompareToDueToNullColor() {
		this.redColor.compareTo(null);
	}
}
