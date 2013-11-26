package main.mainPackage;

import main.modele.joueurModele.Joueur;
import main.modele.piocheModele.Pioche;
import main.modele.talonModele.Talon;

/**
 * Classe contenant le point d'entrée du programme
 */
public class Main {
	public static void main(String[] args) {
		
		Pioche pioche = new Pioche();
		Talon talon = new Talon();
		Joueur p1 = new Joueur("p1");
		Joueur p2 = new Joueur("p2");
		
		pioche.displayAllCards();
		
		p1.pickUpCards(pioche.drawCards(7));
		p2.pickUpCards(pioche.drawCards(7));

		System.out.println(p1);
		System.out.println(p2);
		talon.playCard(p1.playCard(0));
		talon.playCard(p2.playCard(1));
		System.out.println(p1);
		System.out.println(p2);
	}
	
	@SuppressWarnings("unused") //TODO: temporary
	private static void blankLine() {
		System.out.println("");
	}
}