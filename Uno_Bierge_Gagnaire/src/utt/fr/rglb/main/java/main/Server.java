package utt.fr.rglb.main.java.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utt.fr.rglb.main.java.game.controller.AbstractGameController;
import utt.fr.rglb.main.java.game.controller.GameControllerConsoleOriented;
import utt.fr.rglb.main.java.game.controller.GameControllerGraphicsOriented;
import utt.fr.rglb.main.java.view.console.ConsoleView;
import utt.fr.rglb.main.java.view.graphics.GraphicsView;

/**
 * Classe correspondant au serveur de jeu comprenant tous les élements necessaire au fonctionnement d'une partie
 */
public class Server implements Serializable {
	private static final Logger log = LoggerFactory.getLogger(Server.class);
	private static final long serialVersionUID = 1L;
	private static AbstractGameController gameController;
	private static Server server;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur privé de serveur
	 */
	private Server(ConsoleView view) {
		log.info("Server created with CONSOLE view (using System.in as default input)");
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		Server.gameController = new GameControllerConsoleOriented(view,bufferedReader);
	}
	
	/**
	 * Constructeur privé de serveur
	 */
	private Server(GraphicsView view) {
		log.info("Server created with JavaFX GRAPHICS view");
		Server.gameController = new GameControllerGraphicsOriented(view);
	}

	/**
	 * Méthode perrmetant de récupérer l'unique instance de serveur
	 * @return L'instance unique de serveur
	 */
	public static Server getInstance(ConsoleView view) {
		if(Server.server == null) {
			Server.server = new Server(view);
			return Server.server;
		} else {
			return Server.server;
		}
	}
	
	/**
	 * Méthode perrmetant de récupérer l'unique instance de serveur
	 * @return L'instance unique de serveur
	 */
	public static Server getInstance(GraphicsView view) {
		if(Server.server == null) {
			Server.server = new Server(view);
			return Server.server;
		} else {
			return Server.server;
		}
	}
	
	public static GameControllerGraphicsOriented getGameController() {
		return (GameControllerGraphicsOriented)Server.gameController;
	}

	/* ========================================= GAME LOGIC ========================================= */

	/**
	 * Méthode permetant de lancer une partie de UNO
	 */
	public void startPlaying() {
		log.info("New game started");
		Server.gameController.startAnotherGame();
	}
}
