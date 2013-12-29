package utt.fr.rglb.main.java.main;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utt.fr.rglb.main.java.graphics.view.Basics;

/**
 * Classe contenant le point d'entree du programme
 */
public class Main {
	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		try {
			DOMConfigurator.configure("./src/utt/fr/rglb/main/ressources/log4j.xml");
			BasicConfigurator.configure();
			Basics b = new Basics();
			b.runGraphicsView(args);
			//a();
		} catch (Exception e) {
			log.error("====================================");
			log.error("[ERROR] An unexpected error happened");
			log.error("Class involved : " + e.getClass());
			log.error("");
			log.error("Message from Exception : " + e.getMessage());
			log.error("");
			log.error("------ STACK TRACE ------");
			log.error( "failed!", e );
			log.error("====================================");
			log.error("        [PROGRAM TERMINATED]        ");
			log.error("");
		}
	}

	@SuppressWarnings("unused")
	private static void a() {
		System.setProperty("jansi.passthrough", "true");
		Server server = Server.getInstance();
		server.startPlaying();
	}
}