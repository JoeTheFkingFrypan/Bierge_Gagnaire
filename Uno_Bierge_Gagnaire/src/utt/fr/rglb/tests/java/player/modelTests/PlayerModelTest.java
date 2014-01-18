package utt.fr.rglb.tests.java.player.modelTests;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.CardSpecial;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.cards.model.basics.Effect;
import utt.fr.rglb.main.java.player.model.PlayerModel;

/**
 * Classe de tests unitaires validant le comportement des méthodes de la classe PlayerModel
 * </br>Utilisation de simulacres pour la vue, les effets des cartes spéciales (Mockito)
 * @see PlayerModel
 */
public class PlayerModelTest {
	private int initialScore;
	private String p1Name;
	private String p2Name;
	private PlayerModel p1;
	private PlayerModel p2;
	private Card c1;
	private Card c2;
	private CardSpecial c3;
	private CardSpecial c4;
	private Effect mockedEffect;
	private Collection<Card> emptyCollection;
	private Collection<Card> collectionOfJustOneCard;
	private Collection<Card> collectionOfMultipleCards;
	private int whateverPath;
	
	@Before
	public void setup() {
		//Création des attributs liés aux joueurs
		initializePlayers();
		//Création des attributs qui serviront dans les collections de cartes
		initializeCards();
		//Création des collection de cartes
		initializeCardCollections();
	}
	
	private void initializePlayers() {
		this.initialScore = 0;
		this.p1Name = ("joueur1");
		this.p2Name = ("joueur2");
		this.p1 = new PlayerModel(this.p1Name);
		this.p2 = new PlayerModel(this.p2Name);
	}
	
	private void initializeCards() {
		this.mockedEffect = mock(Effect.class);
		this.whateverPath = 0;
		this.c1 = new Card(0,Color.BLUE,this.whateverPath);
		this.c2 = new Card(7,Color.GREEN,this.whateverPath);
		this.c3 = new CardSpecial(20, Color.RED, mockedEffect,this.whateverPath);
		this.c4 = new CardSpecial(50, Color.JOKER, mockedEffect,this.whateverPath);
	}

	private void initializeCardCollections() {
		this.emptyCollection = new ArrayList<Card>();
		this.collectionOfJustOneCard = new ArrayList<Card>();
		this.collectionOfJustOneCard.add(this.c1);
		this.collectionOfMultipleCards = new ArrayList<Card>();
		this.collectionOfMultipleCards.add(this.c1);
		this.collectionOfMultipleCards.add(this.c2);
		this.collectionOfMultipleCards.add(this.c3);
		this.collectionOfMultipleCards.add(this.c4);
	}
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	@Test(expected=NullPointerException.class)
	public void failToCreatePlayerNullAlias() {
		PlayerModel nullAlias = new PlayerModel(null);
		nullAlias.toString();
	}
	
	/* ========================================= CARD PICKUP ========================================= */
	
	@Test
	public void testPickupCardsUsingCollectionOfMultipleCards() {
		this.p1.pickUpCards(this.collectionOfMultipleCards);
		assertEquals(4,this.p1.getNumberOfCardsInHand());
		this.p2.pickUpCards(this.collectionOfJustOneCard);
		assertEquals(1,this.p2.getNumberOfCardsInHand());
	}
	
	@Test(expected=NullPointerException.class)
	public void testFailToPickupCardsDueToNullCollection() {
		this.p1.pickUpCards(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFailToPickupCardsDueToEmptyCollection() {
		this.p1.pickUpCards(this.emptyCollection);
	}
	
	/* ========================================= CARD PLAY ========================================= */
	
	@Test
	public void testPlayCardAssumingPlayerHasAtLeastOne() {
		//Ajout d'une carte dans la main du joueur
		this.p1.pickUpCards(this.collectionOfJustOneCard);
		assertEquals(1,this.p1.getNumberOfCardsInHand());
		//Défausse de cette carte
		Card playedCard = this.p1.playCard(0);
		assertEquals(this.c1,playedCard);
		assertEquals(0,this.p1.getNumberOfCardsInHand());
	}
	
	@Test(expected=IllegalStateException.class)
	public void failToPlayCardBecausePlayerHasNone() {
		this.p1.playCard(0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void failToPlayCardDueToInvalidIndex() {
		this.p1.pickUpCards(this.collectionOfMultipleCards);
		this.p1.playCard(999);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void failToPlayCardDueToNegativeIndex() {
		this.p1.pickUpCards(this.collectionOfMultipleCards);
		this.p1.playCard(-999);
	}
	
	/* ========================================= GETTERS & UTILS ========================================= */
	
	@Test
	public void testGetNumberOfCardsInHand() {
		assertEquals(0,this.p1.getNumberOfCardsInHand());
		assertEquals(0,this.p2.getNumberOfCardsInHand());
	}
	
	@Test
	public void testGetScore() {
		assertEquals(this.initialScore, this.p1.getScore());
		assertEquals(this.initialScore, this.p2.getScore());
	}
	
	@Test
	public void testToString() {
		assertEquals("joueur1", this.p1.toString());
		assertEquals("joueur2", this.p2.toString());
	}
}
