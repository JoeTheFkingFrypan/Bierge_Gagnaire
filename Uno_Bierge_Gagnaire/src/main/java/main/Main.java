package main.java.main;

import java.io.IOException;

import main.java.cards.model.pile.Talon;
import main.java.cards.model.stock.Pioche;
import main.java.player.model.PlayerModel;

/**
 * Classe contenant le point d'entrée du programme
 */
@SuppressWarnings("unused") // TODO: temporary
public class Main {
	public static void main(String[] args) throws IOException {
		//oldTests();
		Server server = Server.getInstance();
	}

	private static void oldTests() {
		Pioche pioche = new Pioche();
		Talon talon = new Talon();
		PlayerModel p1 = new PlayerModel("p1");
		PlayerModel p2 = new PlayerModel("p2");

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