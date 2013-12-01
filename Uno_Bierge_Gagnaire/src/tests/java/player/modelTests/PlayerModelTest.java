package tests.java.player.modelTests;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import main.java.cards.model.basics.Carte;
import main.java.cards.model.basics.CarteSpeciale;
import main.java.cards.model.basics.Couleur;
import main.java.cards.model.basics.Effet;
import main.java.player.model.PlayerModel;

public class PlayerModelTest {
	private int initialScore;
	private String p1Name;
	private String p2Name;
	private PlayerModel p1;
	private PlayerModel p2;
	private Carte c1;
	private Carte c2;
	private CarteSpeciale c3;
	private CarteSpeciale c4;
	private Effet mockedEffect;
	private Collection<Carte> emptyCollection;
	private Collection<Carte> collectionOfJustOneCard;
	private Collection<Carte> collectionOfMultipleCards;
	
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
		this.mockedEffect = mock(Effet.class);
		this.c1 = new Carte(0,Couleur.BLEUE);
		this.c2 = new Carte(7,Couleur.VERTE);
		this.c3 = new CarteSpeciale(20, Couleur.ROUGE, mockedEffect);
		this.c4 = new CarteSpeciale(50, Couleur.JOKER, mockedEffect);
	}

	private void initializeCardCollections() {
		this.emptyCollection = new ArrayList<Carte>();
		this.collectionOfJustOneCard = new ArrayList<Carte>();
		this.collectionOfJustOneCard.add(this.c1);
		this.collectionOfMultipleCards = new ArrayList<Carte>();
		this.collectionOfMultipleCards.add(this.c1);
		this.collectionOfMultipleCards.add(this.c2);
		this.collectionOfMultipleCards.add(this.c3);
		this.collectionOfMultipleCards.add(this.c4);
	}
	
	@Test(expected=NullPointerException.class)
	public void failToCreatePlayerNullAlias() {
		PlayerModel nullAlias = new PlayerModel(null);
		nullAlias.getAlias();
	}
	
	@Test
	public void testGetAlias() {
		assertEquals(this.p1Name, this.p1.getAlias());
		assertEquals(this.p2Name, this.p2.getAlias());
	}
	
	@Test
	public void testGetScore() {
		assertEquals(this.initialScore, this.p1.getScore());
		assertEquals(this.initialScore, this.p2.getScore());
	}
	
	@Test
	public void testGetNumberOfCardsInHand() {
		assertEquals(0,this.p1.getNumberOfCardsInHand());
		assertEquals(0,this.p2.getNumberOfCardsInHand());
	}
	
	@Test
	public void testToString() {
		assertEquals("[JOUEUR] joueur1 a 0 points. Il lui reste 0 cartes en main", this.p1.toString());
		assertEquals("[JOUEUR] joueur2 a 0 points. Il lui reste 0 cartes en main", this.p2.toString());
	}
	
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
	
	@Test
	public void testPlayCardAssumingPlayerHasAtLeastOne() {
		//Ajout d'une carte dans la main du joueur
		this.p1.pickUpCards(this.collectionOfJustOneCard);
		assertEquals(1,this.p1.getNumberOfCardsInHand());
		//Défausse de cette carte
		Carte playedCard = this.p1.playCard(0);
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
}
