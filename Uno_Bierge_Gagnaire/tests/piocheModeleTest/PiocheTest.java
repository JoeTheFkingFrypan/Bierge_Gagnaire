package piocheModeleTest;

import java.util.LinkedList;
import java.util.Queue;

import main.modele.carteModele.CarteSpeciale;
import main.modele.carteModele.Carte;
import main.modele.carteModele.Couleur;
import main.modele.piocheModele.Pioche;
import main.modele.carteModele.EffetJoker;
import main.modele.carteModele.EffetChangerSens;
import main.modele.carteModele.EffetPiocherCarte;

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
		//Création de 5 cartes
		this.carte01 = new Carte(8,Couleur.ROUGE);
		this.carte02 = new Carte(0,Couleur.BLEUE);
		this.carte03 = new CarteSpeciale(25, Couleur.VERTE, new EffetChangerSens());
		this.carte04 = new CarteSpeciale(25, Couleur.JAUNE, new EffetPiocherCarte(2));
		this.carte05 = new CarteSpeciale(50, Couleur.JOKER, new EffetJoker());
		//Création des pioches
		this.pioche = new Pioche();
		this.baseQueue = fillCardsInsideQueue();
		//Spécifications du comportement des objets mockés
		this.mockedPioche = PowerMockito.spy(new Pioche());
		PowerMockito.doReturn(baseQueue.size()).when(mockedPioche,"size");
		PowerMockito.doReturn(baseQueue.contains(carte01)).when(mockedPioche,"contains",this.carte01);
		PowerMockito.doReturn(baseQueue.contains(carte02)).when(mockedPioche,"contains",this.carte02);
		PowerMockito.doReturn(baseQueue.contains(carte03)).when(mockedPioche,"contains",this.carte03);
		PowerMockito.doReturn(baseQueue.contains(carte04)).when(mockedPioche,"contains",this.carte04);
		PowerMockito.doReturn(baseQueue.contains(carte05)).when(mockedPioche,"contains",this.carte05);
		//this.mockedPioche = mock(Pioche.class);
		//when(this.mockedPioche.size()).thenReturn(this.baseQueue.size());
		//when(this.mockedPioche.contains((Carte)any())).thenReturn(this.baseQueue.contains((Carte)any()));
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
	
	@Test
	public void testCardCount() {
		assertEquals(108,this.pioche.size());
		assertEquals(5,this.mockedPioche.size());
	}
	
	@Test
	public void testCardsInsidePioche() throws Exception {
		//Carte01
		System.out.println(this.carte01);
		pioche.displayAllCards();
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