package utt.fr.rglb.main.java.view.graphics.fxml;

import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utt.fr.rglb.main.java.game.controller.GameControllerGraphicsOriented;
import utt.fr.rglb.main.java.main.Server;
import utt.fr.rglb.main.java.player.model.PlayerTeam;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLControllerTeamDisplayScreen extends AbstractFXMLController {
	private static final Logger log = LoggerFactory.getLogger(FXMLControllerTeamDisplayScreen.class);
	private GameControllerGraphicsOriented gameController;

	/* ========================================= FXML ========================================= */
	
	@FXML private Button acceptButton;
	@FXML private TableView<TeamModel> teamDisplay;
	@FXML private TableColumn<TeamModel, String> teamName;
	@FXML private TableColumn<TeamModel, String> firstPlayer;
	@FXML private TableColumn<TeamModel, String> secondPlayer;

	/* ========================================= CONSTRUCTOR ========================================= */
	
	public FXMLControllerTeamDisplayScreen() {
		this.gameController = Server.getGameController();
	}

	/* ========================================= EVENT HANDLING ========================================= */
	
	/**
	 * Méthode appelée par le FXMLLoader quand l'initialisation de tous les éléments est terminée
	 * Permet d'ajouter/retirer dynamiquement des élements dans la fenêtre en réponse à la selection du nombre de joueurs par l'utilisateur
	 */
	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		Map<Integer, PlayerTeam> teams = this.gameController.getAllTeams();
		ObservableList<TeamModel> dataFromCurrentTeam = FXCollections.observableArrayList();
		for(Entry<Integer, PlayerTeam> currentTeamEntry : teams.entrySet()) {
			dataFromCurrentTeam.add(new TeamModel(currentTeamEntry));
		}
		teamName.setCellValueFactory(new PropertyValueFactory<TeamModel,String>("teamName"));
		firstPlayer.setCellValueFactory(new PropertyValueFactory<TeamModel,String>("nameFromFirstPlayer"));
		secondPlayer.setCellValueFactory(new PropertyValueFactory<TeamModel,String>("nameFromSecondPlayer"));
		teamDisplay.setItems(dataFromCurrentTeam);
	}
	
	/**
	 * Méthode permettant d'afficher un message d'erreur dans les labels (compatible Java7 et inférieur)
	 */
	@FXML protected void handleContinueButtonAction(ActionEvent event) {
		//try {
		//Scene scene = acceptButton.getScene();
		log.info("Loading JavaFX main game screen from file : \"game.fxml\"");
		log.warn("Sorry, feature not yet implemented :(");
		//Parent root = FXMLLoader.load(getClass().getResource("/utt/fr/rglb/main/ressources/fxml/setup.fxml"));
		//scene.setRoot(root);
		//} catch (IOException e) {
		//throw new FXMLControllerException("[ERROR] While trying to load screen from \"setup.fxml\"",e);
		//}
	}

	/**
	 * Classe statique permettant de remplir la TableView
	 */
	public static class TeamModel {
		private final SimpleStringProperty teamName;
		private final SimpleStringProperty nameFromFirstPlayer;
		private final SimpleStringProperty nameFromSecondPlayer;

		/**
		 * Constructeur de TeamModel
		 * @param teamEntry Entry correspondant à une équipe (numéro d'équipe et équipe)
		 */
		public TeamModel(Entry<Integer, PlayerTeam> teamEntry) {
			this.teamName = new SimpleStringProperty("Team " + teamEntry.getKey());
			this.nameFromFirstPlayer = new SimpleStringProperty(teamEntry.getValue().getNameFromFirstPlayer());
			this.nameFromSecondPlayer = new SimpleStringProperty(teamEntry.getValue().getNameFromSecondPlayer());
		}

		/**
		 * Getter de nom d'équipe
		 * @return String correspondant
		 */
		public String getTeamName() {
			return teamName.get();
		}

		/**
		 * Getter de nom du 1er joueur
		 * @return String correspondant
		 */
		public String getNameFromFirstPlayer() {
			return nameFromFirstPlayer.get();
		}

		/**
		 * Getter de nom du 2eme joueur
		 * @return String correspondant
		 */
		public String getNameFromSecondPlayer() {
			return nameFromSecondPlayer.get();
		}
	}
}
