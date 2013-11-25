package talonModeleTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import org.junit.Before;
import org.junit.Test;

import main.modele.carteModele.Carte;
import main.modele.carteModele.CarteSpeciale;
import main.modele.carteModele.Couleur;
import main.modele.carteModele.EffetJoker;
import main.modele.talonModele.Talon;

public class TalonTest {
	private Talon emptyStack;
	private Talon filledStack;
	private Carte oneCard;
	private Carte anotherCard;
	private Carte yetAnotherCard;
	
	@Before
	public void setup() {
		this.oneCard = new Carte(7,Couleur.ROUGE);
		this.anotherCard = new Carte(1,Couleur.BLEUE);
		this.yetAnotherCard = new CarteSpeciale(20,Couleur.JOKER,new EffetJoker());
		this.emptyStack = new Talon();
		this.filledStack = fillStackWithCards();
	}
	
	private Talon fillStackWithCards() {
		Talon stack = new Talon();
		stack.playCard(this.oneCard);
		stack.playCard(this.anotherCard);
		stack.playCard(this.yetAnotherCard);
		return stack;	
	}

	@Test
	public void testSize() {
		assertEquals(0,this.emptyStack.size());
		assertEquals(3,this.filledStack.size());
	}
	
	@Test
	public void testPlayCard() {
		this.emptyStack.playCard(this.oneCard);
		assertEquals(1,this.emptyStack.size());
		this.emptyStack.playCard(this.anotherCard);
		assertEquals(2,this.emptyStack.size());
		this.emptyStack.playCard(this.yetAnotherCard);
		assertEquals(3,this.emptyStack.size());
	}
	
	@Test
	public void testEmptyPile() {
		Collection<Carte> cardsFromEmptyStack = this.emptyStack.emptyPile();
		Collection<Carte> cardsFromFilledStack = this.filledStack.emptyPile();
		//Verification du nombre de carte dans les 2 talons
		assertEquals(0,this.emptyStack.size());
		assertEquals(0,this.filledStack.size());
		//Verification du nombre de carte dans les collections reçues
		assertEquals(0,cardsFromEmptyStack.size());
		assertEquals(3,cardsFromFilledStack.size());
		//Verification des cartes presentes dans la collection où des cartes existent
		assertTrue(cardsFromFilledStack.contains(this.yetAnotherCard));
		assertTrue(cardsFromFilledStack.contains(this.oneCard));
		assertTrue(cardsFromFilledStack.contains(this.anotherCard));
	}
	
	@Test
	public void testToString() {
		assertEquals("[Talon] 0 cartes ont été jouées",this.emptyStack.toString());
		assertEquals("[Talon] 3 cartes ont été jouées",this.filledStack.toString());
	}
}