package main.java.main;

/**
 * Classe contenant le point d'entrée du programme
 */
public class Main {
	public static void main(String[] args) {
		Server server = Server.getInstance();
		server.cycleForever();
	}
}