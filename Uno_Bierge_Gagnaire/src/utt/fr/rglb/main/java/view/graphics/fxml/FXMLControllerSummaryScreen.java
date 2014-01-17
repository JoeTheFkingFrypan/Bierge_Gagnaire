package utt.fr.rglb.main.java.view.graphics.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utt.fr.rglb.main.java.dao.ConfigFileDaoException;
import utt.fr.rglb.main.java.game.controller.GameControllerGraphicsOriented;
import utt.fr.rglb.main.java.game.model.GameMode;
import utt.fr.rglb.main.java.game.model.GameRule;
import utt.fr.rglb.main.java.main.Server;
import utt.fr.rglb.main.java.player.model.PlayerStatus;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;

public class FXMLControllerSummaryScreen extends AbstractFXMLController {
	private static final Logger log = LoggerFactory.getLogger(FXMLControllerTeamDisplayScreen.class);
	private GameControllerGraphicsOriented gameController;

	/* ========================================= FXML ========================================= */
	
	@FXML private Label numberLabel;
	@FXML private Label modeLabel;
	@FXML private GridPane headerGrid;
	@FXML private GridPane mainGrid;
	@FXML private GridPane players;
	
	/* ========================================= CONSTRUCTOR ========================================= */

	public FXMLControllerSummaryScreen() {
		this.gameController = Server.getGameController();
	}

	/* ========================================= EVENT HANDLING ========================================= */
	
	/**
	 * Méthode appelée par le FXMLLoader quand l'initialisation de tous les éléments est terminée
	 * Permet d'ajouter/retirer dynamiquement des élements dans la fenêtre en réponse à la selection du nombre de joueurs par l'utilisateur
	 */
	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle ressources) {
		try {
			Text header = createSwaggifiedHeader("Loading data from file \"config.ini\"");
			headerGrid.add(header, 0, 0);
			PlayersToCreate playersToCreate = this.gameController.retrievePlayerDataFromFile();
			Integer playerCount = playersToCreate.size();
			Label playerNumber = new Label(playerCount.toString());
			mainGrid.add(playerNumber,2,0);
			ObservableList<String> gameModes = FXCollections.observableArrayList("Classic");
			if(playerCount.equals(2)) {
				gameModes.add("2 Players");
			} else {
				gameModes.add("Last-man Standing");
				if(playerCount.equals(4) || playerCount.equals(6)) {
					gameModes.add("Teamplay");
				}	
			}
			ChoiceBox<String> gameMode = new ChoiceBox<String>(gameModes);
			gameMode.setId("GameModeChoice");
			gameMode.setTooltip(new Tooltip("Select one available game mode"));
			gameMode.getSelectionModel().selectFirst();
			mainGrid.add(gameMode,2,1);
			int currentIndex = 0;
			for(PlayerStatus player : playersToCreate.getAllPlayers()) {
				//Fixed Labels
				players.add(new Label("Player name:"), 0, currentIndex);
				players.add(new Label("Type:"), 2, currentIndex);
				//Variable Labels
				players.add(new Label(player.getAlias()), 1, currentIndex);
				players.add(new Label(player.getType()), 3, currentIndex);
				currentIndex++;
			}
			Button goForIt = createValidationButton(playersToCreate);
			players.add(goForIt,0,playerCount+4,4,1);
		} catch (ConfigFileDaoException e) {
			mainGrid.getChildren().remove(numberLabel);
			ObservableList<RowConstraints> rowConstraints = mainGrid.getRowConstraints();
			int rowNumber = 0;
			for(RowConstraints currentRowConstraints : rowConstraints) {
				if(rowNumber == 0 || rowNumber == 1) {
					currentRowConstraints.setMinHeight(100);
				}
				rowNumber++;
			}
			numberLabel = new Label("Impossible to finish loading data from configuration file");
			numberLabel.setId("errorMessage");
			numberLabel.setMinWidth(460);
			mainGrid.add(numberLabel,0,0,3,1);
			mainGrid.getChildren().remove(modeLabel);
			modeLabel = new Label(e.getMessage());
			modeLabel.setId("errorMessage");
			modeLabel.setWrapText(true);
			mainGrid.add(modeLabel,0,1,3,1);
			Button goForIt = createErrorButton(mainGrid.getScene(),"setup.fxml");
			mainGrid.add(goForIt,0,2,3,1);
		}
	}

	/* ========================================= ELEMENT CREATION ========================================= */
	
	/**
	 * Méthode permettant de créer le bouton qui sert à passer à l'écran suivant<br/>
	 * Quand l'utilisateur clique sur ce bouton, des vérifications sont faites pour s'assurer que les données entrées sont valides<br/>
	 * Tant que les données ne sont pas valides, l'utilisateur ne quitte pas cet écran
	 * @return Un bouton (JavaFX)
	 */
	@SuppressWarnings("unchecked")
	private Button createValidationButton(final PlayersToCreate playersToCreate) {
		Button goForIt = new Button("Continue with these settings");
		goForIt.getStyleClass().add("acceptButton");
		goForIt.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				Scene scene= mainGrid.getScene();
				ChoiceBox<String> gameMode = (ChoiceBox<String>) scene.lookup("#GameModeChoice");
				String chosenGameMode = gameMode.getValue();
				GameRule choosenRules = null;
				switch(chosenGameMode) {
				case "2 Players":
					choosenRules = new GameRule(GameMode.TWO_PLAYERS);
					break;
				case "Last-man Standing":
					log.warn("Game mode \"Last-man Standing\" is not implemented yet, switching to \"Classic\" game mode instead");
					choosenRules = new GameRule(GameMode.NORMAL);
					break;
				case "Teamplay":
					choosenRules = new GameRule(GameMode.TEAM_PLAY);
					break;
				default:
					choosenRules = new GameRule(GameMode.NORMAL);
					break;
				}
				log.info("Info about the game gathered : \"" + chosenGameMode + "\" mode activated");
				log.info("Info about all " + playersToCreate.size() + " players successfully gathered");
				log.info("Preparing to create " + playersToCreate.toString());
				gameController.createGameFrom(choosenRules,playersToCreate,scene);
				try {
					log.info("Loading JavaFX setup screen from file : \"game2players.fxml\"");
					Parent root= FXMLLoader.load(getClass().getResource("/utt/fr/rglb/main/ressources/fxml/game2players.fxml"));
					scene.setRoot(root);
				} catch (IOException e1) {
					throw new FXMLControllerException("[ERROR] While trying to load screen from \"game2players.fxml\"",e1);
				}
			}
		});
		return goForIt;
	}
}
