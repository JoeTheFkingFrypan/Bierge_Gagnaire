package utt.fr.rglb.tests.java.cards.model.basicsTests;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.CardSpecial;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.cards.model.basics.Effect;
import utt.fr.rglb.main.java.game.model.GameFlag;

/**
 * Classe de tests unitaires validant le comportement des méthodes de la classe CardSpecial
 * </br>Utilisation de simulacres pour l'effet des cartes spéciales (Mockito)
 * @see CardSpecial
 */
public class CardSpecialTest {
	//Elements composant les différentes cartes
	private Integer referenceValue;
	private Integer completelyDifferentValue;
	private Color referenceColor;
	private Color completelyDifferentColor;
	private Effect referenceMockedEffect;
	private Effect completelyDifferentMockedEffect;
	//Cartes
	private CardSpecial referenceCard;
	private CardSpecial compatibleCardSameEffect;
	private CardSpecial compatibleCardSameColor;
	private CardSpecial incompatibleCard;
	private CardSpecial jokerCard;
	private Card compatibleNumberedCard;
	private Card incompatibleNumberedCard;
	private int whateverIndex;
	
	@Before
	public void setup() {
		initializeAttributesAndDefineBehaviourForMockedObjects();
		this.whateverIndex = 0;
		this.referenceCard = new CardSpecial(this.referenceValue, this.referenceColor, this.referenceMockedEffect,this.whateverIndex);
		this.compatibleCardSameEffect = new CardSpecial(this.completelyDifferentValue, this.completelyDifferentColor, this.referenceMockedEffect,this.whateverIndex);
		this.compatibleCardSameColor = new CardSpecial(this.completelyDifferentValue, this.referenceColor, this.completelyDifferentMockedEffect,this.whateverIndex);
		this.incompatibleCard = new CardSpecial(this.referenceValue, this.completelyDifferentColor, this.completelyDifferentMockedEffect,this.whateverIndex);
		this.jokerCard = new CardSpecial(this.completelyDifferentValue, Color.JOKER, this.completelyDifferentMockedEffect,this.whateverIndex);
		this.compatibleNumberedCard = new Card(9,this.referenceColor,this.whateverIndex);
		this.incompatibleNumberedCard = new Card(9,this.completelyDifferentColor,this.whateverIndex);
	}

	private void initializeAttributesAndDefineBehaviourForMockedObjects() {
		//Valeurs
		this.referenceValue = new Integer(50);
		this.completelyDifferentValue = new Integer(20);
		//Couleurs
		this.referenceColor = Color.BLUE;
		this.completelyDifferentColor = Color.RED;
		//Effets
		this.referenceMockedEffect = mock(Effect.class);
		this.completelyDifferentMockedEffect = mock(Effect.class);
		when(this.referenceMockedEffect.toString()).thenReturn("Super effet special");
		when(this.referenceMockedEffect.getDescription()).thenReturn("Super effet special");
		when(this.referenceMockedEffect.triggerEffect()).thenReturn(GameFlag.SKIP);
		when(this.completelyDifferentMockedEffect.toString()).thenReturn("Super effet special totalement different du premier");
		when(this.completelyDifferentMockedEffect.getDescription()).thenReturn("Super effet special totalement different du premier");
		when(this.completelyDifferentMockedEffect.triggerEffect()).thenReturn(GameFlag.REVERSE);
	}
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	@Test(expected=IllegalArgumentException.class) 
	public void failToCreateCardValueTooLow() {
		CardSpecial wayTooLowValue = new CardSpecial(-999,Color.RED,this.referenceMockedEffect,this.whateverIndex);
		wayTooLowValue.isSpecial();
	}
	
	@Test(expected=NullPointerException.class) 
	public void failToCreateCardValueNullColor() {
		CardSpecial wayTooLowValue = new CardSpecial(7,null,this.referenceMockedEffect,this.whateverIndex);
		wayTooLowValue.isSpecial();
	}
	
	@Test(expected=NullPointerException.class) 
	public void failToCreateCardValueNullEffect() {
		CardSpecial wayTooLowValue = new CardSpecial(7,Color.RED,null,this.whateverIndex);
		wayTooLowValue.isSpecial();
	}
	
	/* ========================================= EFFECT ========================================= */
	
	@Test
	public void testTriggerEffect() {
		assertEquals(GameFlag.SKIP,this.compatibleCardSameEffect.triggerEffect());
	}
	
	/* ========================================= ADVANCED COMPARAISON ========================================= */
	
	@Test
	public void testIsCompatibleWith() {
		assertTrue(this.referenceCard.isCompatibleWith(this.compatibleCardSameColor));
		assertTrue(this.referenceCard.isCompatibleWith(this.compatibleCardSameEffect));
		assertFalse(this.referenceCard.isCompatibleWith(this.incompatibleCard));
		assertTrue(this.referenceCard.isCompatibleWith(this.jokerCard));
		assertFalse(this.referenceCard.isCompatibleWith(this.incompatibleNumberedCard));
		assertTrue(this.referenceCard.isCompatibleWith(this.compatibleNumberedCard));
	}
	
	/* ========================================= BASIC COMPARAISON ========================================= */
	
	@Test
	public void testEquals() {
		//For the following objects, names were picked based as follows
		//Same Value = sv -- Different Value = DV
		//Same Color = sc -- Different Color = DC
		//Same Effect = se -- Different Effect = DE
		CardSpecial reference = new CardSpecial(50, Color.BLUE, this.referenceMockedEffect,this.whateverIndex);
		
		//No difference
		CardSpecial sv_sc_se = new CardSpecial(50, Color.BLUE, this.referenceMockedEffect,this.whateverIndex);
		assertTrue(reference.equals(sv_sc_se));
		
		//One difference
		CardSpecial DV_sc_se = new CardSpecial(20, Color.BLUE, this.referenceMockedEffect,this.whateverIndex);
		CardSpecial sv_DC_se = new CardSpecial(50, Color.RED, this.referenceMockedEffect,this.whateverIndex);
		CardSpecial sv_sc_DE = new CardSpecial(50, Color.BLUE, this.completelyDifferentMockedEffect,this.whateverIndex);
		assertFalse(reference.equals(DV_sc_se));
		assertFalse(reference.equals(sv_DC_se));
		assertFalse(reference.equals(sv_sc_DE));
		
		//Combinaison of 2 differences
		CardSpecial DV_DC_se = new CardSpecial(20, Color.RED, this.referenceMockedEffect,this.whateverIndex);
		CardSpecial DV_sc_DE = new CardSpecial(20, Color.BLUE, this.completelyDifferentMockedEffect,this.whateverIndex);
		CardSpecial sv_DC_DE = new CardSpecial(50, Color.RED, this.completelyDifferentMockedEffect,this.whateverIndex);
		assertFalse(reference.equals(DV_DC_se));
		assertFalse(reference.equals(DV_sc_DE));
		assertFalse(reference.equals(sv_DC_DE));
		
		//Everything different
		CardSpecial DV_DC_DE = new CardSpecial(20, Color.RED, this.completelyDifferentMockedEffect,this.whateverIndex);
		assertFalse(reference.equals(DV_DC_DE));
	}
	
	/* ========================================= GETTERS ========================================= */
	
	@Test
	public void testGetEffect() {
		assertEquals("Super effet special",this.referenceCard.getEffect());
		assertEquals("Super effet special totalement different du premier",this.compatibleCardSameColor.getEffect());
		assertEquals("Super effet special",this.compatibleCardSameEffect.getEffect());
		assertEquals("Super effet special totalement different du premier",this.incompatibleCard.getEffect());
	}
	
	@Test
	public void testGetColor() {
		assertEquals(this.referenceColor, this.referenceCard.getColor());
		assertEquals(this.completelyDifferentColor, this.compatibleCardSameEffect.getColor());
		assertEquals(this.referenceColor,this.compatibleCardSameColor.getColor());
		assertEquals(this.completelyDifferentColor,this.incompatibleCard.getColor());
	}
	
	@Test
	public void testIsSpecial() {
		assertTrue(this.referenceCard.isSpecial());
		assertTrue(this.compatibleCardSameEffect.isSpecial());
		assertTrue(this.compatibleCardSameColor.isSpecial());
		assertTrue(this.incompatibleCard.isSpecial());
	}
	
	@Test
	public void testGetValue() {
		assertEquals(this.referenceValue,this.referenceCard.getValue());
		assertEquals(this.completelyDifferentValue,this.compatibleCardSameEffect.getValue());
	}
	
	/* ========================================= DISPLAY ========================================= */
	
	@Test
	public void testAffichageCarte() {
		assertEquals("[CARTE SPECIALE] Valeur=50, Couleur=BLUE, Effet=Super effet special",this.referenceCard.toString());
		assertEquals("[CARTE SPECIALE] Valeur=20, Couleur=RED, Effet=Super effet special",this.compatibleCardSameEffect.toString());
		assertEquals("[CARTE SPECIALE] Valeur=20, Couleur=BLUE, Effet=Super effet special totalement different du premier",this.compatibleCardSameColor.toString());
		assertEquals("[CARTE SPECIALE] Valeur=50, Couleur=RED, Effet=Super effet special totalement different du premier",this.incompatibleCard.toString());
	}
}
