package utt.fr.rglb.main.java.main;

import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.view.AbstractView;
import utt.fr.rglb.main.java.view.console.ConsoleView;
import utt.fr.rglb.main.java.view.graphics.GraphicsView;
import utt.fr.rglb.main.java.view.graphics.JavaFxApplication;

/**
 * Classe contenant le point d'entree du programme
 */
@SuppressWarnings("unused")
public class Main {
	private static final Logger log = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) {
		DOMConfigurator.configure("./src/utt/fr/rglb/main/ressources/config/log4j.xml");
		BasicConfigurator.configure();
		log.info("=========== STARTING UNO APP v.3.0 ===========");
		boolean graphicsGameWanted = parseProgramArgument(args);
		try {
			if(graphicsGameWanted) {
				displayWarningIfNotUsingJavaFX8();
				GraphicsView graphicsView = new GraphicsView();
				Server server = Server.getInstance(graphicsView);
				server.startPlaying();
			} else {
				System.setProperty("jansi.passthrough", "true");
				ConsoleView consoleView = new ConsoleView();
				Server server = Server.getInstance(consoleView);
				server.startPlaying();
			}
		} catch (UnsupportedClassVersionError e) {
			handleLibraryConflict();
		} catch (Exception e) {
			handleException(e);
		}
	}

	private static void displayWarningIfNotUsingJavaFX8() {
		if(!System.getProperty("java.runtime.version").startsWith("1.8.")) {
			log.warn("It seems you're not using JavaFX/JDK 8, nodal messages won't display");
			log.warn("    |--  Detected : java.specification.version : " + "\"" + System.getProperty("java.specification.version") + "\"");
			log.warn("    |--  Detected : java.version : " + "\"" + System.getProperty("java.version") + "\"");
			log.warn("    |--  Detected : java.runtime.version : " + "\"" + System.getProperty("java.runtime.version") + "\"");
		}
	}

	private static void handleLibraryConflict() {
		log.error("It appears you're not using a correct version of \"jfxrt.jar\"");
		log.error("Please fix your java build path in order to run the program");
		log.error("    |--  Detected : java.specification.version : \"" + System.getProperty("java.specification.version") + "\"");
		log.error("    |--  Detected : java.version : \"" + System.getProperty("java.version") + "\"");
		log.error("    |--  Detected : java.runtime.version : \"" + System.getProperty("java.runtime.version") + "\"");
		log.error("    |--  Detected : sun.arch.data.model : \"" + System.getProperty("sun.arch.data.model") + " bits\"");
		log.error("        [PROGRAM TERMINATED]        ");
		log.error("");
	}

	private static void handleException(Exception e) {
		log.error("/!\\ ==== [ERROR] An unexpected error happened ==== /!\\");
		log.error("Class involved : " + e.getClass());
		log.error("Message from Exception : " + e.getMessage());
		log.error("------ STACK TRACE ------");
		log.error( "failed!", e );
		log.error("/!\\ =========== [PROGRAM TERMINATED] =========== /!\\");
		log.error("");
	}

	private static boolean parseProgramArgument(String[] args) {
		try {
			String arg = args[0];
			if(arg.equals("-console")) {
				return false;
			} else if(arg.equals("-fancy")) {
				return true;
			} else {
				throw new IllegalArgumentException();
			}
		} catch (Exception e) {
			log.error("[ERROR] Provided program argument is invalid : must be either -console or -fancy");
			log.error("Launching fancy (JavaFX graphics) view by default");
			log.error("Fix your program argument to make this message not appear again");
			return true;
		}
	}
}