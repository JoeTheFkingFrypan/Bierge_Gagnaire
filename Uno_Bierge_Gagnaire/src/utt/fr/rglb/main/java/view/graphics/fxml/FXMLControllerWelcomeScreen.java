package utt.fr.rglb.main.java.view.graphics.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class FXMLControllerWelcomeScreen extends AbstractFXMLController {
	private static final Logger log = LoggerFactory.getLogger(FXMLControllerWelcomeScreen.class);

	@FXML private GridPane headerGrid;
	@FXML private Button acceptButton;
	@FXML private Button declineButton;

	@FXML protected void handleDeclineButtonAction(ActionEvent event) {
		try {
			Scene scene = declineButton.getScene();
			log.info("User not using configuration file, setup screen will now be displayed");
			log.info("Loading JavaFX setup screen from file : \"setup.fxml\"");
			Parent root = FXMLLoader.load(getClass().getResource("/utt/fr/rglb/main/ressources/fxml/setup.fxml"));
			scene.setRoot(root);
		} catch (IOException e) {
			throw new FXMLControllerException("[ERROR] While trying to load screen from \"setup.fxml\"",e);
		}
	}

	@FXML protected void handleAcceptButtonAction(ActionEvent event) {
		try {
			Scene scene = declineButton.getScene();
			log.info("User using configuration file, data now be loaded if correct");
			log.info("Loading JavaFX summary screen from file : \"summary.fxml\"");
			Parent root = FXMLLoader.load(getClass().getResource("/utt/fr/rglb/main/ressources/fxml/summary.fxml"));
			scene.setRoot(root);
		} catch (IOException e) {
			throw new FXMLControllerException("[ERROR] While trying to load screen from \"summary.fxml\"",e);
		}
	}
	
	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		Text header = createSwaggifiedHeader("Welcome to our UNO");
		headerGrid.add(header, 0, 0);
	}
}
