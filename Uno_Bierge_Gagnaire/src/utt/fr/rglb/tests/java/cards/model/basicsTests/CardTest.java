package utt.fr.rglb.tests.java.cards.model.basicsTests;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.CardSpecial;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.cards.model.basics.Effect;

/**
 * Classe de tests unitaires validant le comportement des méthodes de la classe Card
 * </br>Utilisation de simulacres pour l'effet des cartes spéciales (Mockito)
 * @see Card
 */
public class CardTest {
	private Integer referenceValue;
	private Color referenceColor;
	private Card referenceCard;
	private Card incompatibleCard;
	private Card compatibleCardSameNumber;
	private Card compatibleCardSameColor;
	private CardSpecial compatibleJokerCard;
	private Effect mockedEffect;
	
	@Before
	public void setup() {
		this.referenceValue = new Integer(0);
		this.referenceColor = Color.YELLOW;
		this.referenceCard = new Card(this.referenceValue, this.referenceColor);
		this.compatibleCardSameNumber = new Card(this.referenceValue, Color.BLUE);
		this.compatibleCardSameColor = new Card(9, this.referenceColor);
		this.incompatibleCard = new Card(7, Color.RED);
		this.mockedEffect = mock(Effect.class);
		this.compatibleJokerCard = new CardSpecial(25, Color.JOKER, mockedEffect);
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
		assertTrue(this.referenceCard.isCompatibleWith(this.compatibleJokerCard));
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
		assertEquals(this.referenceValue, this.referenceCard.getValue());
		assertEquals(this.referenceValue, this.compatibleCardSameNumber.getValue());
		assertEquals(new Integer(7), this.incompatibleCard.getValue());
		assertEquals(new Integer(9), this.compatibleCardSameColor.getValue());
	}
	
	@Test
	public void testGetCouleur() {
		assertEquals(this.referenceColor, this.referenceCard.getColor());
		assertEquals(this.referenceColor, this.compatibleCardSameColor.getColor());
		assertEquals(Color.BLUE, this.compatibleCardSameNumber.getColor());
		assertEquals(Color.RED, this.incompatibleCard.getColor());
	}
	
	@Test
	public void testIsColored() {
		assertTrue(this.referenceCard.isYellow());
		assertFalse(this.referenceCard.isRed());
		assertFalse(this.referenceCard.isBlue());
		assertFalse(this.referenceCard.isGreen());
		assertFalse(this.referenceCard.isJoker());
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
