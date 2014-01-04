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
import utt.fr.rglb.main.java.view.AbstractView;

/**
 * Classe de tests unitaires validant le comportement des méthodes de la classe CardsModelBean
 * </br>Utilisation de simulacres pour la vue et l'effet des cartes spéciales (Mockito)
 * @see CardsModelBean
 */
public class CardsModelBeanTest {
	private CardsModelBean cardsModelBean;
	private Card lastCardPlayed;
	private Color globalColor;
	private AbstractView consoleView;
	private Effect mockedEffect;
	private CardSpecial lastCardPlayedInOrderToSetGlobalColor;
	private CardsModelBean cardsModelBeanWithGlobalColorSet;
	private int whateverPath;

	@Before
	public void setup() {
		this.whateverPath = 0;
		this.lastCardPlayed = new Card(2,Color.RED,this.whateverPath);
		this.globalColor = Color.JOKER;
		this.consoleView = mock(AbstractView.class);
		this.mockedEffect = mock(Effect.class);
		this.lastCardPlayedInOrderToSetGlobalColor = new CardSpecial(50, Color.JOKER, this.mockedEffect,this.whateverPath);
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
		assertTrue(this.cardsModelBean.isCompatibleWith(new Card(5,Color.RED,this.whateverPath)));
		assertTrue(this.cardsModelBean.isCompatibleWith(new Card(2,Color.BLUE,this.whateverPath)));
		assertFalse(this.cardsModelBean.isCompatibleWith(new Card(9,Color.GREEN,this.whateverPath)));
		assertTrue(this.cardsModelBean.isCompatibleWith(new CardSpecial(50, Color.JOKER, this.mockedEffect,this.whateverPath)));
		assertTrue(this.cardsModelBean.isCompatibleWith(new CardSpecial(25, Color.RED, this.mockedEffect,this.whateverPath)));
		assertFalse(this.cardsModelBean.isCompatibleWith(new CardSpecial(25, Color.BLUE, this.mockedEffect,this.whateverPath)));
	}

	@Test
	public void testIsCompatibleWithSingleCardWhenGlobalColorIsSet() {
		assertFalse(this.cardsModelBeanWithGlobalColorSet.isCompatibleWith(new Card(5,Color.RED,this.whateverPath)));
		assertFalse(this.cardsModelBeanWithGlobalColorSet.isCompatibleWith(new Card(2,Color.BLUE,this.whateverPath)));
		assertFalse(this.cardsModelBeanWithGlobalColorSet.isCompatibleWith(new Card(9,Color.GREEN,this.whateverPath)));
		assertTrue(this.cardsModelBeanWithGlobalColorSet.isCompatibleWith(new Card(3,Color.YELLOW,this.whateverPath)));
		assertTrue(this.cardsModelBeanWithGlobalColorSet.isCompatibleWith(new Card(9,Color.YELLOW,this.whateverPath)));

		Effect effectDifferentFromReferenceCard = mock(Effect.class);
		when(this.mockedEffect.getDescription()).thenReturn("whatever");
		when(effectDifferentFromReferenceCard.getDescription()).thenReturn("doesntmatter");
		Card compatibleJokerCard = new CardSpecial(50, Color.JOKER, effectDifferentFromReferenceCard,this.whateverPath);
		Card compatibleSpecialCardDueToSameEffect = new CardSpecial(25, Color.GREEN, this.mockedEffect,this.whateverPath);
		Card compatibleSpecialCardDueToSameColor = new CardSpecial(25, Color.YELLOW, effectDifferentFromReferenceCard,this.whateverPath);
		Card incompatibleSpecialCard = new CardSpecial(50, Color.RED, effectDifferentFromReferenceCard,this.whateverPath);
		assertTrue(this.cardsModelBeanWithGlobalColorSet.isCompatibleWith(compatibleJokerCard));
		assertTrue(this.cardsModelBeanWithGlobalColorSet.isCompatibleWith(compatibleSpecialCardDueToSameEffect));
		assertTrue(this.cardsModelBeanWithGlobalColorSet.isCompatibleWith(compatibleSpecialCardDueToSameColor));
		assertFalse(this.cardsModelBeanWithGlobalColorSet.isCompatibleWith(incompatibleSpecialCard));
	}

	@Test
	public void testIsCompatibleWithCardCollection() {
		//Cartes de bases
		Effect mockedEffect = mock(Effect.class);
		Card compatibleJokerCard = new CardSpecial(50, Color.JOKER, mockedEffect,this.whateverPath);
		Card incomptibleCard01 = new Card(7,Color.BLUE,this.whateverPath);
		Card incomptibleCard02 = new Card(9,Color.GREEN,this.whateverPath);
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
		Collection<Card> compatibleCollection = Arrays.asList(
				new Card(0,Color.RED,this.whateverPath),
				new Card(1,Color.YELLOW,this.whateverPath),
				new Card(2,Color.GREEN,this.whateverPath),
				new Card(3,Color.BLUE,this.whateverPath),
				new Card(4,Color.GREEN,this.whateverPath),
				new Card(5,Color.YELLOW,this.whateverPath),
				new CardSpecial(25, Color.JOKER, this.mockedEffect,this.whateverPath));
		Collection<Integer> expectedResults = Arrays.asList(0,2,6);
		assertTrue(expectedResults.containsAll(this.cardsModelBean.findPlayableCardsFrom(compatibleCollection)));
		assertTrue(emptyCollection.containsAll(this.cardsModelBean.findPlayableCardsFrom(emptyCollection)));
	}

	@Test
	public void testFindPlayableCardsFromWhenGlobalColorIsSet() {
		Collection<Card> emptyCollection = new ArrayList<Card>();
		Collection<Card> compatibleCollection = Arrays.asList(
				new Card(0,Color.RED,this.whateverPath),
				new Card(1,Color.YELLOW,this.whateverPath),
				new Card(2,Color.GREEN,this.whateverPath),
				new Card(3,Color.BLUE,this.whateverPath),
				new Card(4,Color.GREEN,this.whateverPath),
				new Card(5,Color.YELLOW,this.whateverPath),
				new CardSpecial(25, Color.JOKER, this.mockedEffect,this.whateverPath));
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
