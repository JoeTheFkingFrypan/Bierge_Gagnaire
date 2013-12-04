package tests.java.cards.model.basicsTests;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import main.java.cards.model.basics.CardSpecial;
import main.java.cards.model.basics.Color;
import main.java.cards.model.basics.Effect;

public class CarteSpecialeTest {
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
	
	@Before
	public void setup() {
		initializeAttributesAndDefineBehaviourForMockedObjects();		
		this.referenceCard = new CardSpecial(this.referenceValue, this.referenceColor, this.referenceMockedEffect);
		this.compatibleCardSameEffect = new CardSpecial(this.completelyDifferentValue, this.completelyDifferentColor, this.referenceMockedEffect);
		this.compatibleCardSameColor = new CardSpecial(this.completelyDifferentValue, this.referenceColor, this.completelyDifferentMockedEffect);
		this.incompatibleCard = new CardSpecial(this.referenceValue, this.completelyDifferentColor, this.completelyDifferentMockedEffect);
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
		when(this.completelyDifferentMockedEffect.toString()).thenReturn("Super effet special totalement different du premier");
		when(this.completelyDifferentMockedEffect.getDescription()).thenReturn("Super effet special totalement different du premier");
	}
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	@Test(expected=IllegalArgumentException.class) 
	public void failToCreateCardValueTooLow() {
		CardSpecial wayTooLowValue = new CardSpecial(-999,Color.RED,this.referenceMockedEffect);
		wayTooLowValue.isSpecial();
	}
	
	@Test(expected=NullPointerException.class) 
	public void failToCreateCardValueNullColor() {
		CardSpecial wayTooLowValue = new CardSpecial(7,null,this.referenceMockedEffect);
		wayTooLowValue.isSpecial();
	}
	
	@Test(expected=NullPointerException.class) 
	public void failToCreateCardValueNullEffect() {
		CardSpecial wayTooLowValue = new CardSpecial(7,Color.RED,null);
		wayTooLowValue.isSpecial();
	}
	
	/* ========================================= EFFECT ========================================= */
	
	@Test
	public void testDeclencherEffet() {
		//Pour rappel une seules les cartes spéciales ont un effet (pas les cartes classiques)
		this.compatibleCardSameEffect.declencherEffet();
	}
	
	/* ========================================= ADVANCED COMPARAISON ========================================= */
	
	@Test
	public void testIsCompatibleWith() {
		assertTrue(this.referenceCard.isCompatibleWith(this.compatibleCardSameColor));
		assertTrue(this.referenceCard.isCompatibleWith(this.compatibleCardSameEffect));
		assertFalse(this.referenceCard.isCompatibleWith(this.incompatibleCard));
	}
	
	/* ========================================= BASIC COMPARAISON ========================================= */
	
	@Test
	public void testEquals() {
		//For the following objects, names were picked based as follows
		//Same Value = sv -- Different Value = DV
		//Same Color = sc -- Different Color = DC
		//Same Effect = se -- Different Effect = DE
		CardSpecial reference = new CardSpecial(50, Color.BLUE, this.referenceMockedEffect);
		
		//No difference
		CardSpecial sv_sc_se = new CardSpecial(50, Color.BLUE, this.referenceMockedEffect);
		assertTrue(reference.equals(sv_sc_se));
		
		//One difference
		CardSpecial DV_sc_se = new CardSpecial(20, Color.BLUE, this.referenceMockedEffect);
		CardSpecial sv_DC_se = new CardSpecial(50, Color.RED, this.referenceMockedEffect);
		CardSpecial sv_sc_DE = new CardSpecial(50, Color.BLUE, this.completelyDifferentMockedEffect);
		assertFalse(reference.equals(DV_sc_se));
		assertFalse(reference.equals(sv_DC_se));
		assertFalse(reference.equals(sv_sc_DE));
		
		//Combinaison of 2 differences
		CardSpecial DV_DC_se = new CardSpecial(20, Color.RED, this.referenceMockedEffect);
		CardSpecial DV_sc_DE = new CardSpecial(20, Color.BLUE, this.completelyDifferentMockedEffect);
		CardSpecial sv_DC_DE = new CardSpecial(50, Color.RED, this.completelyDifferentMockedEffect);
		assertFalse(reference.equals(DV_DC_se));
		assertFalse(reference.equals(DV_sc_DE));
		assertFalse(reference.equals(sv_DC_DE));
		
		//Everything different
		CardSpecial DV_DC_DE = new CardSpecial(20, Color.RED, this.completelyDifferentMockedEffect);
		assertFalse(reference.equals(DV_DC_DE));
	}
	
	@Test
	public void testCompareTo() {
		int comparaison = this.referenceCard.compareTo(this.compatibleCardSameColor);
		assertTrue(comparaison > 0);
		comparaison = this.referenceCard.compareTo(this.compatibleCardSameEffect);
		assertTrue(comparaison > 0);
		comparaison = this.referenceCard.compareTo(this.incompatibleCard);
		assertTrue(comparaison > 0);
	}
	
	/* ========================================= GETTERS ========================================= */
	
	@Test
	public void testGetEffet() {
		assertEquals("Super effet special",this.referenceCard.getEffet());
		assertEquals("Super effet special totalement different du premier",this.compatibleCardSameColor.getEffet());
		assertEquals("Super effet special",this.compatibleCardSameEffect.getEffet());
		assertEquals("Super effet special totalement different du premier",this.incompatibleCard.getEffet());
	}
	
	@Test
	public void testGetValeur() {
		assertEquals(this.referenceValue,this.referenceCard.getValeur());
		assertEquals(this.completelyDifferentValue,this.compatibleCardSameEffect.getValeur());
		assertEquals(this.completelyDifferentValue,this.compatibleCardSameColor.getValeur());
		assertEquals(this.referenceValue,this.incompatibleCard.getValeur());
	}
	
	@Test
	public void testGetCouleur() {
		assertEquals(this.referenceColor, this.referenceCard.getCouleur());
		assertEquals(this.completelyDifferentColor, this.compatibleCardSameEffect.getCouleur());
		assertEquals(this.referenceColor,this.compatibleCardSameColor.getCouleur());
		assertEquals(this.completelyDifferentColor,this.incompatibleCard.getCouleur());
	}
	
	@Test
	public void testEstSpeciale() {
		assertTrue(this.referenceCard.isSpecial());
		assertTrue(this.compatibleCardSameEffect.isSpecial());
		assertTrue(this.compatibleCardSameColor.isSpecial());
		assertTrue(this.incompatibleCard.isSpecial());
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
