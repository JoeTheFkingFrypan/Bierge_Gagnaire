package tests.java.cards.model.stockTests;

import java.util.LinkedList;
import java.util.Queue;

import main.java.cards.model.basics.Carte;
import main.java.cards.model.basics.CarteSpeciale;
import main.java.cards.model.basics.Couleur;
import main.java.cards.model.basics.EffetChangerSens;
import main.java.cards.model.basics.EffetJoker;
import main.java.cards.model.basics.EffetPiocherCarte;
import main.java.cards.model.stock.Pioche;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import org.powermock.api.mockito.PowerMockito;

public class PiocheTest {
	private Pioche pioche;
	private Pioche mockedPioche;
	private Queue<Carte> baseQueue;
	private Carte carte01;
	private Carte carte02;
	private Carte carte03;
	private Carte carte04;
	private Carte carte05;
	
	@Before
	public void setup() throws Exception {
		//Cr�ation de 5 cartes
		this.carte01 = new Carte(8,Couleur.ROUGE);
		this.carte02 = new Carte(0,Couleur.BLEUE);
		this.carte03 = new CarteSpeciale(20, Couleur.VERTE, new EffetChangerSens());
		this.carte04 = new CarteSpeciale(20, Couleur.JAUNE, new EffetPiocherCarte(2));
		this.carte05 = new CarteSpeciale(50, Couleur.JOKER, new EffetJoker());
		//Cr�ation des pioches
		this.pioche = new Pioche();
		this.baseQueue = fillCardsInsideQueue();
		//Sp�cifications du comportement des objets mock�s
		this.mockedPioche = PowerMockito.spy(new Pioche());
		defineBehaviourForMockedObjects();
	}
	
	private Queue<Carte> fillCardsInsideQueue() {
		Queue<Carte> baseQueue = new LinkedList<Carte>();
		baseQueue.add(this.carte01);
		baseQueue.add(this.carte02);
		baseQueue.add(this.carte03);
		baseQueue.add(this.carte04);
		baseQueue.add(this.carte05);
		return baseQueue;
	}
	
	private void defineBehaviourForMockedObjects() throws Exception {
		PowerMockito.doReturn(this.baseQueue.size()).when(this.mockedPioche,"size");
		PowerMockito.doReturn(this.baseQueue.contains(this.carte01)).when(mockedPioche,"contains",this.carte01);
		PowerMockito.doReturn(this.baseQueue.contains(this.carte02)).when(mockedPioche,"contains",this.carte02);
		PowerMockito.doReturn(this.baseQueue.contains(this.carte03)).when(mockedPioche,"contains",this.carte03);
		PowerMockito.doReturn(this.baseQueue.contains(this.carte04)).when(mockedPioche,"contains",this.carte04);
		PowerMockito.doReturn(this.baseQueue.contains(this.carte05)).when(mockedPioche,"contains",this.carte05);
		//this.mockedPioche = mock(Pioche.class);
		//when(this.mockedPioche.size()).thenReturn(this.baseQueue.size());
		//when(this.mockedPioche.contains((Carte)any())).thenReturn(this.baseQueue.contains((Carte)any()));
	}
	
	@Test
	public void testCardCount() {
		assertEquals(108,this.pioche.size());
		assertEquals(5,this.mockedPioche.size());
	}
	
	@Test
	public void testCardsInsidePioche() throws Exception {
		//Carte01
		assertTrue(this.pioche.contains(this.carte01));
		assertTrue(this.mockedPioche.contains(this.carte01));
		//Carte02
		assertTrue(this.pioche.contains(this.carte02));
		assertTrue(this.mockedPioche.contains(this.carte02));
		//Carte03
		assertTrue(this.pioche.contains(this.carte03));
		assertTrue(this.mockedPioche.contains(this.carte03));
		//Carte04
		assertTrue(this.pioche.contains(this.carte04));
		assertTrue(this.mockedPioche.contains(this.carte04));
		//Carte05
		assertTrue(this.pioche.contains(this.carte05));
		assertTrue(this.mockedPioche.contains(this.carte05));
		//N'importe quelle autre carte (valide)
		Carte carte06 = new Carte(9,Couleur.JAUNE);
		PowerMockito.doReturn(this.baseQueue.contains(carte06)).when(mockedPioche,"contains",carte06);
		assertTrue(this.pioche.contains(carte06));
		assertFalse(this.mockedPioche.contains(carte06));
	}
}