package tests.java.cards.model.basicsTests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import main.java.cards.model.basics.Carte;
import main.java.cards.model.basics.Couleur;

public class CarteTest {
	private Integer referenceValue;
	private Couleur referenceColor;
	private Carte referenceCard;
	private Carte incompatibleCard;
	private Carte compatibleCardSameNumber;
	private Carte compatibleCardSameColor;
	
	@Before
	public void setup() {
		this.referenceValue = new Integer(0);
		this.referenceColor = Couleur.JAUNE;
		this.referenceCard = new Carte(this.referenceValue, this.referenceColor);
		this.compatibleCardSameNumber = new Carte(this.referenceValue, Couleur.BLEUE);
		this.compatibleCardSameColor = new Carte(9, this.referenceColor);
		this.incompatibleCard = new Carte(7, Couleur.ROUGE);
	}
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	@Test(expected=IllegalArgumentException.class)
	public void failToCreateCardValueTooLow() {
		Carte wayTooLowValue = new Carte(-999,Couleur.ROUGE);
		wayTooLowValue.isSpecial();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailToCreateCardValueTooHigh() {
		Carte wayTooHighValue = new Carte(999,Couleur.ROUGE);
		wayTooHighValue.isSpecial();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailToCreateInexistingCardIncorrectColor() {
		Carte incorrectColor = new Carte(7,Couleur.JOKER);
		incorrectColor.isSpecial();
	}
	
	@Test(expected=NullPointerException.class)
	public void testFailToCreateInexistingCardNullColor() {
		Carte incorrectColor = new Carte(7,null);
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
		Carte sameCardAsReferenceCard = new Carte(this.referenceValue, this.referenceColor);
		assertTrue(this.referenceCard.equals(sameCardAsReferenceCard));
	}
	
	@Test
	public void testCompareTo() {
		Carte reference = new Carte(7,Couleur.ROUGE);
		Carte lowerCard = new Carte(2,Couleur.ROUGE);
		Carte higherCard = new Carte(9,Couleur.ROUGE);
		Carte differentColorCardBlue = new Carte(7,Couleur.BLEUE);
		Carte differentColorCardGreen = new Carte(7,Couleur.VERTE);
		Carte differentColorCardYellow = new Carte(7,Couleur.JAUNE);
		assertTrue((reference.compareTo(lowerCard)) > 0);
		assertTrue((reference.compareTo(higherCard)) < 0);
		assertTrue((reference.compareTo(differentColorCardBlue)) < 0);
		assertTrue((reference.compareTo(differentColorCardGreen)) < 0);
		assertTrue((reference.compareTo(differentColorCardYellow)) < 0);
	}
	
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
		assertEquals(Couleur.BLEUE, this.compatibleCardSameNumber.getCouleur());
		assertEquals(Couleur.ROUGE, this.incompatibleCard.getCouleur());
	}
	
	/* ========================================= DISPLAY ========================================= */
	
	@Test
	public void testAffichage() {
		assertEquals("[CARTE NUMEROTEE] Numero=0, Couleur=JAUNE",this.referenceCard.toString());
		assertEquals("[CARTE NUMEROTEE] Numero=0, Couleur=BLEUE",this.compatibleCardSameNumber.toString());
		assertEquals("[CARTE NUMEROTEE] Numero=9, Couleur=JAUNE",this.compatibleCardSameColor.toString());
		assertEquals("[CARTE NUMEROTEE] Numero=7, Couleur=ROUGE",this.incompatibleCard.toString());
	}
}
