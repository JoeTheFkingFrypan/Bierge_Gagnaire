package utt.fr.rglb.tests.java.cards.model.basicsTests;

import org.junit.Before;
import org.junit.Test;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import static org.junit.Assert.*;


public class CarteTest {
	private Integer referenceValue;
	private Color referenceColor;
	private Card referenceCard;
	private Card incompatibleCard;
	private Card compatibleCardSameNumber;
	private Card compatibleCardSameColor;
	
	@Before
	public void setup() {
		this.referenceValue = new Integer(0);
		this.referenceColor = Color.YELLOW;
		this.referenceCard = new Card(this.referenceValue, this.referenceColor);
		this.compatibleCardSameNumber = new Card(this.referenceValue, Color.BLUE);
		this.compatibleCardSameColor = new Card(9, this.referenceColor);
		this.incompatibleCard = new Card(7, Color.RED);
	}
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	@Test(expected=IllegalArgumentException.class)
	public void failToCreateCardValueTooLow() {
		Card wayTooLowValue = new Card(-999,Color.RED);
		wayTooLowValue.isSpecial();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailToCreateCardValueTooHigh() {
		Card wayTooHighValue = new Card(999,Color.RED);
		wayTooHighValue.isSpecial();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailToCreateInexistingCardIncorrectColor() {
		Card incorrectColor = new Card(7,Color.JOKER);
		incorrectColor.isSpecial();
	}
	
	@Test(expected=NullPointerException.class)
	public void testFailToCreateInexistingCardNullColor() {
		Card incorrectColor = new Card(7,null);
		incorrectColor.isSpecial();
	}

	/* ========================================= ADVANCED COMPARAISON ========================================= */
	
	@Test
	public void testCardsCompatible() {
		assertTrue(this.referenceCard.isCompatibleWith(this.compatibleCardSameColor));
		assertTrue(this.referenceCard.isCompatibleWith(this.compatibleCardSameNumber));
		assertFalse(this.referenceCard.isCompatibleWith(this.incompatibleCard));
	}
	
	/* ========================================= BASIC COMPARAISON ========================================= */
	
	@Test
	public void testEquals() {
		Integer anyObjectFromDifferentClass = new Integer("2");
		assertFalse(this.referenceCard.equals(anyObjectFromDifferentClass));
		assertFalse(this.referenceCard.equals(compatibleCardSameColor));
		assertFalse(this.referenceCard.equals(compatibleCardSameColor));
		assertFalse(this.referenceCard.equals(compatibleCardSameColor));
		Card sameCardAsReferenceCard = new Card(this.referenceValue, this.referenceColor);
		assertTrue(this.referenceCard.equals(sameCardAsReferenceCard));
	}
	
	//TODO: [CarteTest] fix it
	/*@Test
	public void testCompareTo() {
		Card reference = new Card(7,Color.RED);
		Card lowerCard = new Card(2,Color.RED);
		Card higherCard = new Card(9,Color.RED);
		Card differentColorCardBlue = new Card(7,Color.BLUE);
		Card differentColorCardGreen = new Card(7,Color.GREEN);
		Card differentColorCardYellow = new Card(7,Color.YELLOW);
		assertTrue((reference.compareTo(lowerCard)) > 0);
		assertTrue((reference.compareTo(higherCard)) < 0);
		assertTrue((reference.compareTo(differentColorCardBlue)) < 0);
		assertTrue((reference.compareTo(differentColorCardGreen)) < 0);
		assertTrue((reference.compareTo(differentColorCardYellow)) < 0);
	}*/
	
	/* ========================================= GETTERS ========================================= */
	
	@Test
	public void testEstSpeciale() {
		assertFalse(this.referenceCard.isSpecial());
		assertFalse(this.incompatibleCard.isSpecial());
		assertFalse(this.compatibleCardSameColor.isSpecial());
		assertFalse(this.compatibleCardSameNumber.isSpecial());
	}

	@Test
	public void testGetValeur() {
		assertEquals(this.referenceValue, this.referenceCard.getValeur());
		assertEquals(this.referenceValue, this.compatibleCardSameNumber.getValeur());
		assertEquals(new Integer(7), this.incompatibleCard.getValeur());
		assertEquals(new Integer(9), this.compatibleCardSameColor.getValeur());
	}
	
	@Test
	public void testGetCouleur() {
		assertEquals(this.referenceColor, this.referenceCard.getCouleur());
		assertEquals(this.referenceColor, this.compatibleCardSameColor.getCouleur());
		assertEquals(Color.BLUE, this.compatibleCardSameNumber.getCouleur());
		assertEquals(Color.RED, this.incompatibleCard.getCouleur());
	}
	
	/* ========================================= DISPLAY ========================================= */
	
	@Test
	public void testAffichage() {
		assertEquals("[CARTE NUMEROTEE] Numero=0, Couleur=YELLOW",this.referenceCard.toString());
		assertEquals("[CARTE NUMEROTEE] Numero=0, Couleur=BLUE",this.compatibleCardSameNumber.toString());
		assertEquals("[CARTE NUMEROTEE] Numero=9, Couleur=YELLOW",this.compatibleCardSameColor.toString());
		assertEquals("[CARTE NUMEROTEE] Numero=7, Couleur=RED",this.incompatibleCard.toString());
	}
}
