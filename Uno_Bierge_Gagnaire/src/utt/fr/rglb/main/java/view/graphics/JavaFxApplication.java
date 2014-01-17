package utt.fr.rglb.main.java.view.graphics;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.view.graphics.fxml.FXMLControllerException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe correspondant à l'application JavaFX </br>
 * NOTE: Il est impossible en Java7 (et inférieur) de récupérer les Exceptions en provenance de l'application JavaFX </br>
 * Ce bug est résolu dans Java 8
 */
public class JavaFxApplication extends Application {
	private static final Logger log = LoggerFactory.getLogger(JavaFxApplication.class);
	private boolean applicationStillStopped;
	private Scene scene;
	private int width;
	private int height;

	/* ========================================= CONSTRUCTOR ========================================= */

	public JavaFxApplication() {
		this.applicationStillStopped = true;
		this.width = 1200;
		this.height = 800;
	}

	/* ========================================= INITIALIZING APP/VIEW ========================================= */

	/**
	 * Méthode permettant démarrer l'interface graphique
	 * @param args arguments à passer à l'application
	 */
	public void runGraphicsView(String[] args) {
		this.applicationStillStopped = false;
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		try {
			log.info("Loading JavaFX welcome screen from file : \"welcome.fxml\"");
			Parent root = FXMLLoader.load(getClass().getResource("/utt/fr/rglb/main/ressources/fxml/welcome.fxml"));
			this.scene = new Scene(root, this.width, this.height);
			stage.setTitle("[LO02-UNO] Gagnaire / Bierge");
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			throw new FXMLControllerException("[ERROR] While trying to load screen from \"welcome.fxml\"",e);
		}
	}

	/* ========================================= SCREEN MANAGEMENT ========================================= */

	/**
	 * Méthode permettant de retourner à l'écran d'acceuil
	 */
	public void goBackToWelcomeView() {
		Preconditions.checkState(this.scene != null,"[ERROR] Impossible to change displayed screen : current scene is null");
		try {
			log.info("Switching back to JavaFX welcome screen from file : \"welcome.fxml\"");
			Parent root = FXMLLoader.load(getClass().getResource("/utt/fr/rglb/main/ressources/fxml/welcome.fxml"));
			this.scene.setRoot(root);
		} catch (IOException e) {
			throw new FXMLControllerException("[ERROR] While trying to load screen from \"welcome.fxml\"",e);
		}
	}

	/**
	 * Méthode permettant continuer vers l'affichage des scores par équipe
	 * @param scene Scene correpondant à la vue actuelle
	 */
	public void continueToTeamDisplay(Scene scene) {
		Preconditions.checkNotNull(scene,"[ERROR] Impossible to change displayed screen : current scene is null");
		try {
			this.scene = scene;
			log.info("Loading JavaFX team display screen from file : \"teamDisplay.fxml\"");
			Parent root = FXMLLoader.load(getClass().getResource("/utt/fr/rglb/main/ressources/fxml/teamDisplay.fxml"));
			this.scene.setRoot(root);
		} catch (IOException e) {
			throw new FXMLControllerException("[ERROR] While trying to load screen from \"teamDisplay.fxml\"",e);
		}
	}

	/* ========================================= UTILS ========================================= */

	/**
	 * Méthode permettant de déterminer si l'application a été lancée ou non
	 * @return <code>TRUE</code> si elle a été démarrée, <code>FALSE</code> sinon
	 */
	public boolean hasNotBeenStartedYet() {
		return this.applicationStillStopped;
	}
}
