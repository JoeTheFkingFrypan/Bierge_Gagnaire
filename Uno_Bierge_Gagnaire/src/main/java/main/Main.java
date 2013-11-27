package main.java.main;

import java.io.IOException;

import main.java.cards.model.pile.Talon;
import main.java.cards.model.stock.Pioche;
import main.java.player.model.Joueur;

/**
 * Classe contenant le point d'entrée du programme
 */
@SuppressWarnings("unused") // TODO: temporary
public class Main {
	public static void main(String[] args) throws IOException {
		//oldTests();
		Server server = new Server();
	}

	private static void oldTests() {
		Pioche pioche = new Pioche();
		Talon talon = new Talon();
		Joueur p1 = new Joueur("p1");
		Joueur p2 = new Joueur("p2");

		p1.pickUpCards(pioche.drawCards(7));
		p2.pickUpCards(pioche.drawCards(7));

		System.out.println(p1);
		System.out.println(p2);
		talon.receiveCard(p1.playCard(0));
		talon.receiveCard(p2.playCard(1));
		System.out.println(p1);
		System.out.println(p2);
	}

	private static void blankLine() {
		System.out.println("");
	}
}