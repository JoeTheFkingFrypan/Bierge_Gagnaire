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

public class FXMLControllerTeamDisplayScreen  extends AbstractFXMLController {
	private static final Logger log = LoggerFactory.getLogger(FXMLControllerTeamDisplayScreen.class);
	private GameControllerGraphicsOriented gameController;

	@FXML private Button acceptButton;
	@FXML private TableView<DataModel> teamDisplay;
	@FXML private TableColumn<DataModel, String> teamName;
	@FXML private TableColumn<DataModel, String> firstPlayer;
	@FXML private TableColumn<DataModel, String> secondPlayer;
	
	public FXMLControllerTeamDisplayScreen() {
		this.gameController = Server.getGameController();
	}

	@FXML protected void handleContinueButtonAction(ActionEvent event) {
		//try {
			//Scene scene = acceptButton.getScene();
			log.info("Loading JavaFX main game screen from file : \"game.fxml\"");
			//Parent root = FXMLLoader.load(getClass().getResource("/utt/fr/rglb/main/ressources/fxml/setup.fxml"));
			//scene.setRoot(root);
		//} catch (IOException e) {
			//throw new FXMLControllerException("[ERROR] While trying to load screen from \"setup.fxml\"",e);
		//}
	}
	
	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		Map<Integer, PlayerTeam> teams = this.gameController.getAllTeams();
		ObservableList<DataModel> dataFromCurrentTeam = FXCollections.observableArrayList();
		for(Entry<Integer, PlayerTeam> currentTeamEntry : teams.entrySet()) {
			dataFromCurrentTeam.add(new DataModel(currentTeamEntry));
		}
		if(teamName == null) {
			log.error("teamName is null");
		}
		if(firstPlayer == null) {
			log.error("firstPlayer is null");
		}
		if(secondPlayer == null) {
			log.error("secondPlayer is null");
		}
		if(teamDisplay == null) {
			log.error("teamDisplay is null");
		}
		teamName.setCellValueFactory(new PropertyValueFactory<DataModel,String>("teamName"));
		firstPlayer.setCellValueFactory(new PropertyValueFactory<DataModel,String>("nameFromFirstPlayer"));
		secondPlayer.setCellValueFactory(new PropertyValueFactory<DataModel,String>("nameFromSecondPlayer"));
		teamDisplay.setItems(dataFromCurrentTeam);
	}
	
	 public static class DataModel {
		private final SimpleStringProperty teamName;
		private final SimpleStringProperty nameFromFirstPlayer;
	    private final SimpleStringProperty nameFromSecondPlayer;
	    
	    public DataModel(Entry<Integer, PlayerTeam> teamEntry) {
	    	this.teamName = new SimpleStringProperty("Team " + teamEntry.getKey());
	    	this.nameFromFirstPlayer = new SimpleStringProperty(teamEntry.getValue().getNameFromFirstPlayer());
	    	this.nameFromSecondPlayer = new SimpleStringProperty(teamEntry.getValue().getNameFromSecondPlayer());
	    }
		
	    public String getTeamName() {
	    	return teamName.get();
	    }
	    
	    public String getNameFromFirstPlayer() {
	    	return nameFromFirstPlayer.get();
	    }
	    
	    public String getNameFromSecondPlayer() {
	    	return nameFromSecondPlayer.get();
	    }
	}
}
