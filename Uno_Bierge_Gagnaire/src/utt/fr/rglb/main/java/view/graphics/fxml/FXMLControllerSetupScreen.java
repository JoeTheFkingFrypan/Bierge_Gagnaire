package utt.fr.rglb.main.java.view.graphics.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utt.fr.rglb.main.java.game.controller.GameControllerGraphicsOriented;
import utt.fr.rglb.main.java.game.model.GameMode;
import utt.fr.rglb.main.java.game.model.GameRule;
import utt.fr.rglb.main.java.main.Server;
import utt.fr.rglb.main.java.player.model.PlayersToCreate;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


public class FXMLControllerSetupScreen extends AbstractFXMLController {
	private static final Logger log = LoggerFactory.getLogger(FXMLControllerSetupScreen.class);
	private GameControllerGraphicsOriented gameController;
	private boolean impossibleToForwardRequest;
	private Label errorMessageEmptyName;
	private Label errorMessageSameName;
	private Label gameModeLabel;
	private ChoiceBox<String> gameModeChoice;
	private float progressStep;
	private float currentProgress;
	
	/* ========================================= FMXL ========================================= */
	
	@FXML private GridPane headerGrid;
	@FXML private GridPane players;
	@FXML private GridPane mainGrid;
	@FXML private ProgressIndicator progress;
	@FXML private ChoiceBox<String> playerNumber;
	@FXML private Button chooseButton;

	/* ========================================= CONSTRUCTOR ========================================= */
	
	public FXMLControllerSetupScreen() {
		this.gameController = Server.getGameController();
		this.currentProgress = -1.0f;
	}

	/* ========================================= EVENT HANDLING ========================================= */
	
	/**
	 * Méthode appelée par le FXMLLoader quand l'initialisation de tous les éléments est terminée
	 * Permet d'ajouter/retirer dynamiquement des élements dans la fenêtre en réponse à la selection du nombre de joueurs par l'utilisateur
	 */
	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		Text header = createSwaggifiedHeader("Game Setup");
		headerGrid.add(header, 0, 0);
		progress.setProgress(this.currentProgress);
		playerNumber.setItems(FXCollections.observableArrayList("2","3","4","5","6","7"));
		playerNumber.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				Integer playerCount = Integer.parseInt(newValue);
				progressStep = 1.0f/(playerCount+1);
				currentProgress = progressStep;
				progress.setProgress(currentProgress);
				populateFirstRowWithGameModeChoice(playerCount);
				players.getChildren().clear();
				for(Integer i=2; i<=playerCount+1; i++) {
					populateRow(i);
				}
				Button goForIt = createValidationButton();
				players.add(goForIt,0,playerCount+4,4,1);
			}
		});
	}

	/**
	 * Méthode privée permettant de peupler la 1ère ligne de l'interface avec les élements requis pour récupérer le mode de jeu
	 * Les modes de jeu accessibles sont fonction du nombre de joueurs
	 * @param playerCount Nombre de joueurs selectionné
	 */
	private void populateFirstRowWithGameModeChoice(Integer playerCount) {
		if(gameModeLabel != null) {
			mainGrid.getChildren().remove(gameModeLabel);
		}
		if(gameModeChoice != null) {
			mainGrid.getChildren().remove(gameModeChoice);
		}
		//Label
		gameModeLabel = new Label("Game mode:");
		gameModeLabel.setId("gameModeLabel");
		gameModeLabel.getStyleClass().add("mainText");
		mainGrid.add(gameModeLabel, 0, 1);
		//ChoiceBox - GameMode
		ObservableList<String> gameModes = FXCollections.observableArrayList("Classic");
		if(playerCount.equals(2)) {
			gameModes.add("2 Players");
		} else {
			gameModes.add("Last-man Standing");
			if(playerCount.equals(4) || playerCount.equals(6)) {
				gameModes.add("Teamplay");
			}	
		}
		gameModeChoice = new ChoiceBox<String>(gameModes);
		gameModeChoice.setId("GameModeChoice");
		gameModeChoice.setTooltip(new Tooltip("Available game mode are based on player number"));
		gameModeChoice.getSelectionModel().selectFirst();
		mainGrid.add(gameModeChoice, 2, 1);
	}

	/**
	 * Méthode privée permettant de peupler toutes les lignes associées aux données d'un joueurs (nom, type)
	 * @param i Numéro de la ligne actuelle
	 */
	private void populateRow(Integer i) {
		//Labels
		Label playerName = new Label("Player name:");
		playerName.getStyleClass().add("mainText");
		players.add(playerName, 0, i);
		Label typeOfPlayer = new Label("Type of player:");
		typeOfPlayer.getStyleClass().add("mainText");
		players.add(typeOfPlayer, 2, i);
		//TextField
		TextField textField = new TextField();
		textField.setId("textField" + i.toString());
		textField.setPromptText("Enter an alias");
		textField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(oldValue.equals("") && !newValue.equals("")) {
					currentProgress += progressStep;
					progress.setProgress(currentProgress);
				} else if(!oldValue.equals("") && newValue.equals("")) {
					currentProgress -= progressStep;
					progress.setProgress(currentProgress);
				}
			}
		});

		players.add(textField, 1, i);
		//ChoiceBox - PlayerType
		ChoiceBox<String> playerTypeChoice = new ChoiceBox<String>(FXCollections.observableArrayList("Human", "AI - Weak","AI - Normal","AI - Strong"));
		playerTypeChoice.setId("playerTypeChoice" + i.toString());
		playerTypeChoice.setTooltip(new Tooltip("Select player type"));
		playerTypeChoice.getSelectionModel().selectFirst();
		players.add(playerTypeChoice, 3, i);
	}

	/**
	 * Méthode permettant de créer le bouton qui sert à passer à l'écran suivant<br/>
	 * Quand l'utilisateur clique sur ce bouton, des vérifications sont faites pour s'assurer que les données entrées sont valides<br/>
	 * Tant que les données ne sont pas valides, l'utilisateur ne quitte pas cet écran
	 * @return Un bouton (JavaFX)
	 */
	private Button createValidationButton() {
		Button goForIt = new Button("Continue with these settings");
		goForIt.getStyleClass().add("acceptButton");
		goForIt.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				impossibleToForwardRequest = false;
				if(errorMessageSameName != null) {
					players.getChildren().remove(errorMessageSameName);
				}
				if(errorMessageEmptyName != null) {
					players.getChildren().remove(errorMessageEmptyName);
				}
				Scene scene= playerNumber.getScene();
				Integer playerCount = Integer.parseInt(playerNumber.getValue());
				Collection<String> names = new ArrayList<String>();
				for(Integer i=2; i<=playerCount+1; i++) {
					iterateOverPlayerChoices(scene, playerCount, names, i);
				}
				if(!impossibleToForwardRequest) {
					createAllPlayersFromGatheredData(scene, playerCount);
				}
			}
		});
		return goForIt;
	}

	/**
	 * Méthode permettant de récuperer toutes les informations associés aux joueurs en itérant sur tous les champs associés
	 * @param scene Scene JavaFX
	 * @param playerCount Nombre de joueurs
	 * @param names Noms des joueurs (permet de vérifier la présence de doublons)
	 * @param i Numéro du joueur en cours
	 */
	private void iterateOverPlayerChoices(Scene scene, Integer playerCount, Collection<String> names, Integer i) {
		String textFieldId = "#textField" + i.toString();
		TextField textField = (TextField) scene.lookup(textFieldId);
		String playerName = textField.getText();
		boolean errorNullName = playerName.equals("");
		boolean errorSameName = false;
		if(errorNullName) {
			errorMessageEmptyName = new Label("[ERROR] Impossible to continue : at least 1 players has no name");
			errorMessageEmptyName.setId("errorMessage");
			players.add(errorMessageEmptyName,0,playerCount+5,4,1);
		} else {
			errorSameName = names.contains(playerName);
			if(errorSameName) {
				errorMessageSameName = new Label("[ERROR] Impossible to continue : at least 2 players have the same name");
				errorMessageSameName.setId("errorMessage");
				players.add(errorMessageSameName,0,playerCount+6,4,1);
			} else {
				names.add(playerName);
			}
		}
		impossibleToForwardRequest |= errorNullName;
		impossibleToForwardRequest |= errorSameName;
	}

	/**
	 * Méthode permettant de créer tous les joueurs et les règles du jeu, basé sur les informations précédement récupérées
	 * @param scene Scene JavaFX
	 * @param playerCount Nombre de joueurs
	 */
	@SuppressWarnings("unchecked")
	private void createAllPlayersFromGatheredData(Scene scene, Integer playerCount) {
		PlayersToCreate playersToCreate = new PlayersToCreate();
		for(Integer i=2; i<=playerCount+1; i++) {
			String textFieldId = "#textField" + i.toString();
			String choiceBoxId = "#playerTypeChoice" + i.toString();
			TextField textField = (TextField) scene.lookup(textFieldId);
			ChoiceBox<String> choiceBox = (ChoiceBox<String>) scene.lookup(choiceBoxId);
			String playerName = textField.getText();
			String playerType = choiceBox.getValue();
			switch(playerType) {
			case "AI - Weak":
				playersToCreate.addIAPlayerProvidingStrategyIndex(playerName, 0);
				break;
			case "AI - Normal":
				playersToCreate.addIAPlayerProvidingStrategyIndex(playerName, 1);
				break;
			case "AI - Strong":
				playersToCreate.addIAPlayerProvidingStrategyIndex(playerName, 2);
				break;
			default:
				playersToCreate.addHumanPlayer(playerName);
				break;
			}
		}
		ChoiceBox<String> gameModeChoice = (ChoiceBox<String>) scene.lookup("#GameModeChoice");
		String chosenGameMode = gameModeChoice.getValue();
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
}
