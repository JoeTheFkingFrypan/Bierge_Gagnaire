package utt.fr.rglb.tests.java.cards.model;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.CardSpecial;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.cards.model.basics.Effect;
import utt.fr.rglb.main.java.console.view.View;

/**
 * Classe de tests unitaires validant le comportement des méthodes de la classe CardsModelBean
 * </br>Utilisation de simulacres pour la vue et l'effet des cartes spéciales (Mockito)
 * @see CardsModelBean
 */
public class CardsModelBeanTest {
	private CardsModelBean cardsModelBean;
	private Card lastCardPlayed;
	private Color globalColor;
	private View consoleView;
	private Effect mockedEffect;
	private CardSpecial lastCardPlayedInOrderToSetGlobalColor;
	private CardsModelBean cardsModelBeanWithGlobalColorSet;
	
	@Before
	public void setup() {
		this.lastCardPlayed = new Card(2,Color.RED);
		this.globalColor = Color.JOKER;
		this.consoleView = mock(View.class);
		this.mockedEffect = mock(Effect.class);
		this.lastCardPlayedInOrderToSetGlobalColor = new CardSpecial(50, Color.JOKER, this.mockedEffect);
		this.cardsModelBean = new CardsModelBean(this.lastCardPlayed, this.globalColor, this.consoleView);
		this.cardsModelBeanWithGlobalColorSet = new CardsModelBean(this.lastCardPlayedInOrderToSetGlobalColor, Color.YELLOW, this.consoleView);
	}
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	@Test(expected=NullPointerException.class)
	public void testFailToCreateCardsModelBeanDueToNullCard() {
		this.cardsModelBean = new CardsModelBean(null, this.globalColor, this.consoleView);
	}
	
	@Test(expected=NullPointerException.class)
	public void testFailToCreateCardsModelBeanDueToNullGlobalColor() {
		this.cardsModelBean = new CardsModelBean(this.lastCardPlayed, null, this.consoleView);
	}
	
	@Test(expected=NullPointerException.class)
	public void testFailToCreateCardsModelBeanDueToNullView() {
		this.cardsModelBean = new CardsModelBean(this.lastCardPlayed, this.globalColor, null);
	}
	
	/* ========================================= COMPARAISON - HIGH LEVEL ========================================= */
	
	@Test
	public void testIsCompatibleWithSingleCard() {
		assertTrue(this.cardsModelBean.isCompatibleWith(new Card(5,Color.RED)));
		assertTrue(this.cardsModelBean.isCompatibleWith(new Card(2,Color.BLUE)));
		assertFalse(this.cardsModelBean.isCompatibleWith(new Card(9,Color.GREEN)));
		assertTrue(this.cardsModelBean.isCompatibleWith(new CardSpecial(50, Color.JOKER, this.mockedEffect)));
		assertTrue(this.cardsModelBean.isCompatibleWith(new CardSpecial(25, Color.RED, this.mockedEffect)));
		assertFalse(this.cardsModelBean.isCompatibleWith(new CardSpecial(25, Color.BLUE, this.mockedEffect)));
	}
	
	@Test
	public void testIsCompatibleWithSingleCardWhenGlobalColorIsSet() {
		assertFalse(this.cardsModelBeanWithGlobalColorSet.isCompatibleWith(new Card(5,Color.RED)));
		assertFalse(this.cardsModelBeanWithGlobalColorSet.isCompatibleWith(new Card(2,Color.BLUE)));
		assertFalse(this.cardsModelBeanWithGlobalColorSet.isCompatibleWith(new Card(9,Color.GREEN)));
		assertTrue(this.cardsModelBeanWithGlobalColorSet.isCompatibleWith(new Card(3,Color.YELLOW)));
		assertTrue(this.cardsModelBeanWithGlobalColorSet.isCompatibleWith(new Card(9,Color.YELLOW)));

		Effect effectDifferentFromReferenceCard = mock(Effect.class);
		when(this.mockedEffect.getDescription()).thenReturn("whatever");
		when(effectDifferentFromReferenceCard.getDescription()).thenReturn("doesntmatter");
		Card compatibleJokerCard = new CardSpecial(50, Color.JOKER, effectDifferentFromReferenceCard);
		Card compatibleSpecialCardDueToSameEffect = new CardSpecial(25, Color.GREEN, this.mockedEffect);
		Card compatibleSpecialCardDueToSameColor = new CardSpecial(25, Color.YELLOW, effectDifferentFromReferenceCard);
		Card incompatibleSpecialCard = new CardSpecial(50, Color.RED, effectDifferentFromReferenceCard);
		assertTrue(this.cardsModelBeanWithGlobalColorSet.isCompatibleWith(compatibleJokerCard));
		assertTrue(this.cardsModelBeanWithGlobalColorSet.isCompatibleWith(compatibleSpecialCardDueToSameEffect));
		assertTrue(this.cardsModelBeanWithGlobalColorSet.isCompatibleWith(compatibleSpecialCardDueToSameColor));
		assertFalse(this.cardsModelBeanWithGlobalColorSet.isCompatibleWith(incompatibleSpecialCard));
	}
	
	@Test
	public void testIsCompatibleWithCardCollection() {
		//Cartes de bases
		Effect mockedEffect = mock(Effect.class);
		Card compatibleJokerCard = new CardSpecial(50, Color.JOKER, mockedEffect);
		Card incomptibleCard01 = new Card(7,Color.BLUE);
		Card incomptibleCard02 = new Card(9,Color.GREEN);
		//Conception des collections
		Collection<Card> incompatibleCardCollection = Arrays.asList(incomptibleCard01,incomptibleCard02);
		Collection<Card> compatibleCardCollection = Arrays.asList(incomptibleCard01,incomptibleCard02,compatibleJokerCard);
		//Tests
		assertFalse(this.cardsModelBean.isCompatibleWith(incompatibleCardCollection));
		assertTrue(this.cardsModelBean.isCompatibleWith(compatibleCardCollection));
	}
	
	/* ========================================= GETTERS ========================================= */
	
	@Test
	public void testFindPlayableCardsFrom() {
		Collection<Card> emptyCollection = new ArrayList<Card>();
		Collection<Card> compatibleCollection = Arrays.asList(new Card(0,Color.RED),new Card(1,Color.YELLOW),new Card(2,Color.GREEN),new Card(3,Color.BLUE),new Card(4,Color.GREEN),new Card(5,Color.YELLOW),new CardSpecial(25, Color.JOKER, this.mockedEffect));
		Collection<Integer> expectedResults = Arrays.asList(0,2,6);
		assertTrue(expectedResults.containsAll(this.cardsModelBean.findPlayableCardsFrom(compatibleCollection)));
		assertTrue(emptyCollection.containsAll(this.cardsModelBean.findPlayableCardsFrom(emptyCollection)));
	}
	
	@Test
	public void testFindPlayableCardsFromWhenGlobalColorIsSet() {
		Collection<Card> emptyCollection = new ArrayList<Card>();
		Collection<Card> compatibleCollection = Arrays.asList(new Card(0,Color.RED),new Card(1,Color.YELLOW),new Card(2,Color.GREEN),new Card(3,Color.BLUE),new Card(4,Color.GREEN),new Card(5,Color.YELLOW),new CardSpecial(25, Color.JOKER, this.mockedEffect));
		Collection<Integer> expectedResults = Arrays.asList(1,5,6);
		assertTrue(expectedResults.containsAll(this.cardsModelBeanWithGlobalColorSet.findPlayableCardsFrom(compatibleCollection)));
		assertTrue(emptyCollection.containsAll(this.cardsModelBeanWithGlobalColorSet.findPlayableCardsFrom(emptyCollection)));
	}
	
	/* ========================================= DISPLAY ========================================= */
	
	@Test
	public void testAppendGlobalColorIfItIsSet() {
		this.cardsModelBeanWithGlobalColorSet.appendGlobalColorIfItIsSet();
		this.cardsModelBean.appendGlobalColorIfItIsSet();
	}
}
