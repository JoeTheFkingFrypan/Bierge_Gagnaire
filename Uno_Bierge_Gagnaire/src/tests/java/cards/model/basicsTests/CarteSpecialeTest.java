package tests.java.cards.model.basicsTests;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import main.java.cards.model.basics.CarteSpeciale;
import main.java.cards.model.basics.Couleur;
import main.java.cards.model.basics.Effet;

public class CarteSpecialeTest {
	//Elements composant les différentes cartes
	private Integer referenceValue;
	private Integer completelyDifferentValue;
	private Couleur referenceColor;
	private Couleur completelyDifferentColor;
	private Effet referenceMockedEffect;
	private Effet completelyDifferentMockedEffect;
	//Cartes
	private CarteSpeciale referenceCard;
	private CarteSpeciale compatibleCardSameEffect;
	private CarteSpeciale compatibleCardSameColor;
	private CarteSpeciale incompatibleCard;
	
	@Before
	public void setup() {
		initializeAttributesAndDefineBehaviourForMockedObjects();		
		this.referenceCard = new CarteSpeciale(this.referenceValue, this.referenceColor, this.referenceMockedEffect);
		this.compatibleCardSameEffect = new CarteSpeciale(this.completelyDifferentValue, this.completelyDifferentColor, this.referenceMockedEffect);
		this.compatibleCardSameColor = new CarteSpeciale(this.completelyDifferentValue, this.referenceColor, this.completelyDifferentMockedEffect);
		this.incompatibleCard = new CarteSpeciale(this.referenceValue, this.completelyDifferentColor, this.completelyDifferentMockedEffect);
	}

	private void initializeAttributesAndDefineBehaviourForMockedObjects() {
		//Valeurs
		this.referenceValue = new Integer(50);
		this.completelyDifferentValue = new Integer(20);
		//Couleurs
		this.referenceColor = Couleur.BLEUE;
		this.completelyDifferentColor = Couleur.ROUGE;
		//Effets
		this.referenceMockedEffect = mock(Effet.class);
		this.completelyDifferentMockedEffect = mock(Effet.class);
		when(this.referenceMockedEffect.toString()).thenReturn("Super effet special");
		when(this.referenceMockedEffect.afficherDescription()).thenReturn("Super effet special");
		when(this.completelyDifferentMockedEffect.toString()).thenReturn("Super effet special totalement different du premier");
		when(this.completelyDifferentMockedEffect.afficherDescription()).thenReturn("Super effet special totalement different du premier");
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void failToCreateCardValueTooLow() {
		CarteSpeciale wayTooLowValue = new CarteSpeciale(-999,Couleur.ROUGE,this.referenceMockedEffect);
		wayTooLowValue.isSpecial();
	}
	
	@Test(expected=NullPointerException.class) 
	public void failToCreateCardValueNullColor() {
		CarteSpeciale wayTooLowValue = new CarteSpeciale(7,null,this.referenceMockedEffect);
		wayTooLowValue.isSpecial();
	}
	
	@Test(expected=NullPointerException.class) 
	public void failToCreateCardValueNullEffect() {
		CarteSpeciale wayTooLowValue = new CarteSpeciale(7,Couleur.ROUGE,null);
		wayTooLowValue.isSpecial();
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
	
	@Test
	public void testDeclencherEffet() {
		//Pour rappel une seules les cartes spéciales ont un effet (pas les cartes classiques)
		this.compatibleCardSameEffect.declencherEffet();
	}
	
	@Test
	public void testAffichageCarte() {
		assertEquals("[CARTE SPECIALE] Valeur=50, Couleur=BLEUE, Effet=Super effet special",this.referenceCard.toString());
		assertEquals("[CARTE SPECIALE] Valeur=20, Couleur=ROUGE, Effet=Super effet special",this.compatibleCardSameEffect.toString());
		assertEquals("[CARTE SPECIALE] Valeur=20, Couleur=BLEUE, Effet=Super effet special totalement different du premier",this.compatibleCardSameColor.toString());
		assertEquals("[CARTE SPECIALE] Valeur=50, Couleur=ROUGE, Effet=Super effet special totalement different du premier",this.incompatibleCard.toString());
	}
	
	@Test
	public void testIsCompatibleWith() {
		assertTrue(this.referenceCard.isCompatibleWith(this.compatibleCardSameColor));
		assertTrue(this.referenceCard.isCompatibleWith(this.compatibleCardSameEffect));
		assertFalse(this.referenceCard.isCompatibleWith(this.incompatibleCard));
	}
	
	@Test
	public void testEquals() {
		//For the following objects, names were picked based as follows
		//Same Value = sv -- Different Value = DV
		//Same Color = sc -- Different Color = DC
		//Same Effect = se -- Different Effect = DE
		CarteSpeciale reference = new CarteSpeciale(50, Couleur.BLEUE, this.referenceMockedEffect);
		
		//No difference
		CarteSpeciale sv_sc_se = new CarteSpeciale(50, Couleur.BLEUE, this.referenceMockedEffect);
		assertTrue(reference.equals(sv_sc_se));
		
		//One difference
		CarteSpeciale DV_sc_se = new CarteSpeciale(20, Couleur.BLEUE, this.referenceMockedEffect);
		CarteSpeciale sv_DC_se = new CarteSpeciale(50, Couleur.ROUGE, this.referenceMockedEffect);
		CarteSpeciale sv_sc_DE = new CarteSpeciale(50, Couleur.BLEUE, this.completelyDifferentMockedEffect);
		assertFalse(reference.equals(DV_sc_se));
		assertFalse(reference.equals(sv_DC_se));
		assertFalse(reference.equals(sv_sc_DE));
		
		//Combinaison of 2 differences
		CarteSpeciale DV_DC_se = new CarteSpeciale(20, Couleur.ROUGE, this.referenceMockedEffect);
		CarteSpeciale DV_sc_DE = new CarteSpeciale(20, Couleur.BLEUE, this.completelyDifferentMockedEffect);
		CarteSpeciale sv_DC_DE = new CarteSpeciale(50, Couleur.ROUGE, this.completelyDifferentMockedEffect);
		assertFalse(reference.equals(DV_DC_se));
		assertFalse(reference.equals(DV_sc_DE));
		assertFalse(reference.equals(sv_DC_DE));
		
		//Everything different
		CarteSpeciale DV_DC_DE = new CarteSpeciale(20, Couleur.ROUGE, this.completelyDifferentMockedEffect);
		assertFalse(reference.equals(DV_DC_DE));
	}
}
