package utt.fr.rglb.main.java.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;

import utt.fr.rglb.main.java.console.view.ConsoleView;
import utt.fr.rglb.main.java.console.view.View;
import utt.fr.rglb.main.java.game.controller.GameController;

/**
 * Classe correspondant au serveur de jeu comprenant tous les élements necessaire au fonctionnement d'une partie
 */
public class Server implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Server server;
	private static GameController gameController;
	private static View consoleView;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur privé de serveur
	 */
	private Server() {
		Server.consoleView = new ConsoleView();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		Server.gameController = new GameController(consoleView,bufferedReader);
	}

	/**
	 * Méthode permettant une unique instance de serveur (singleton)
	 * Crée l'instance si elle n'existe pas, ou la retourne si elle existe déjà
	 * @return L'instance unique de serveur
	 */
	private static Server buildServer() {
		Server.server = new Server();
		return Server.server;
	}

	/**
	 * Méthode perrmetant de récupérer l'unique instance de serveur
	 * @return L'instance unique de serveur
	 */
	public static Server getInstance() {
		if(Server.server == null) {
			return buildServer();
		} else {
			return Server.server;
		}
	}

	/* ========================================= GAME LOGIC ========================================= */

	/**
	 * Méthode permetant de lancer une partie de UNO
	 */
	public void startPlaying() {
		Server.gameController.startAnotherGame();
	}
}
