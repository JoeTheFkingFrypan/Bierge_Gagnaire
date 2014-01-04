package utt.fr.rglb.main.java.view.graphics.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.dialog.Dialogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utt.fr.rglb.main.java.dao.ConfigFileDaoException;
import utt.fr.rglb.main.java.game.controller.GameControllerGraphicsOriented;
import utt.fr.rglb.main.java.main.Server;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class FXMLControllerWelcomeScreen extends AbstractFXMLController {
	private static final Logger log = LoggerFactory.getLogger(FXMLControllerWelcomeScreen.class);
	private GameControllerGraphicsOriented gameController;

	@FXML private GridPane mainGrid;
	@FXML private GridPane headerGrid;
	@FXML private GridPane buttonGrid;
	@FXML private Button acceptButton;
	@FXML private Button declineButton;

	public FXMLControllerWelcomeScreen() {
		this.gameController = Server.getGameController();
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		this.gameController.backgroundLoadImages();
		Text header = createSwaggifiedHeader("Welcome to our UNO");
		headerGrid.add(header, 0, 0);
	}

	@FXML protected void handleDeclineButtonAction(ActionEvent event) {
		try {
			this.gameController.backgroundLoadImages();
			Scene scene = headerGrid.getScene();
			log.info("User not using configuration file, setup screen will now be displayed");
			log.info("Loading JavaFX setup screen from file : \"setup.fxml\"");
			Parent root = FXMLLoader.load(getClass().getResource("/utt/fr/rglb/main/ressources/fxml/setup.fxml"));
			scene.setRoot(root);
		} catch (IOException e) {
			throw new FXMLControllerException("[ERROR] While trying to load screen from \"setup.fxml\"",e);
		}
	}

	@FXML protected void handleAcceptButtonAction(ActionEvent event) {
		Scene scene = headerGrid.getScene();
		try {
			@SuppressWarnings("unused") PlayersToCreate playersToCreate = this.gameController.retrievePlayerDataFromFile();
			try {
				scene = declineButton.getScene();
				log.info("User using configuration file, data now be loaded if correct");
				log.info("Loading JavaFX summary screen from file : \"summary.fxml\"");
				Parent root = FXMLLoader.load(getClass().getResource("/utt/fr/rglb/main/ressources/fxml/summary.fxml"));
				scene.setRoot(root);
			} catch (IOException e) {
				throw new FXMLControllerException("[ERROR] While trying to load screen from \"summary.fxml\"",e);
			}
		} catch (ConfigFileDaoException e) {
			if(System.getProperty("java.runtime.version").startsWith("1.8.")) {
				handleErrorJavaFX8Style(scene, e);
			} else {
				handleErrorJavaFX2Style(scene, e);
			}
		}
	}

	private void handleErrorJavaFX8Style(Scene scene, ConfigFileDaoException e) {
		Dialogs.create().lightweight().nativeTitleBar()
		.title("Something went wrong while retrieving data from configuration file")
		.masthead("Program will now load setup screen in order to create the game")
		.showException(e);
		try {
			log.info("User not using configuration file, setup screen will now be displayed");
			log.info("Loading JavaFX setup screen from file : \"setup.fxml\"");
			log.warn(getClass().getResource("/utt/fr/rglb/main/ressources/fxml/setup.fxml").toExternalForm());
			Parent root = FXMLLoader.load(getClass().getResource("/utt/fr/rglb/main/ressources/fxml/setup.fxml"));
			scene.setRoot(root);
		} catch (IOException e1) {
			throw new FXMLControllerException("[ERROR] While trying to load screen from \"setup.fxml\"",e1);
		}
	}
	
	private void handleErrorJavaFX2Style(Scene scene, ConfigFileDaoException e) {
		acceptButton.setDisable(true);
		declineButton.setDisable(true);
		Label exceptionHeader = new Label("Impossible to finish loading data from configuration file");
		exceptionHeader.setId("errorMessage");
		mainGrid.add(exceptionHeader,0,2);
		String initialMessageFromException = e.getMessage();
		Label messageFromException = new Label(initialMessageFromException.replaceFirst("[ERROR] ", ""));
		messageFromException.setId("errorMessage");
		messageFromException.setWrapText(true);
		mainGrid.add(messageFromException,0,3);
		Button goForIt = createErrorButton(mainGrid.getScene(),"setup.fxml");
		mainGrid.add(goForIt,0,4);
	}
}
