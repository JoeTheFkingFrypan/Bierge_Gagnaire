package utt.fr.rglb.main.java.view.graphics.fxml;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.dao.ImageCardAssociator;
import utt.fr.rglb.main.java.game.controller.GameControllerGraphicsOriented;
import utt.fr.rglb.main.java.main.Server;

public class FXMLControllerGameScreen extends AbstractFXMLController {
	private static final Logger log = LoggerFactory.getLogger(FXMLControllerGameScreen.class);
	private GameControllerGraphicsOriented gameController;
	private GraphicsPlayers graphicsPlayers;
	private Text eventsAnnouncer;

	@FXML private GridPane mainGrid;
	@FXML private GridPane playerTwoCardsGrid;
	@FXML private Label playerTwoName;
	@FXML private Label playerOneName;
	@FXML private GridPane playerOneCardsGrid;

	public FXMLControllerGameScreen() {
		this.gameController = Server.getGameController();
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		this.eventsAnnouncer = new Text("");
		this.mainGrid.add(this.eventsAnnouncer, 0, 2);
		
		this.graphicsPlayers = new GraphicsPlayers(this.gameController.getAllPlayers(),this);
		this.gameController.startGame(this);
		
		CardsModelBean references = this.gameController.getReferences();
		Image imageFromCard = ImageCardAssociator.retrieveImageFromIndex(references.getLastCardPlayed().getImageIndex());
		CustomImageView referenceImageView = new CustomImageView(imageFromCard,this);
		mainGrid.add(referenceImageView, 0, 1);
		
		handlePlayerOne(references);
		handlePlayerTwo(references);
		uncoverInitialCards(referenceImageView);
	}

	private void uncoverInitialCards(CustomImageView referenceImageView) {
		this.eventsAnnouncer.getStyleClass().add("fancyText");
		this.eventsAnnouncer.setEffect(createSwaggifiedAnnoucementEffect());
		
		ParallelTransition startingAnimation = new ParallelTransition();
		SequentialTransition mainAnimation = new SequentialTransition();
		mainAnimation.getChildren().add(annouceMessage("New round starting !"));
		mainAnimation.getChildren().add(referenceImageView.generateEffect());
		mainAnimation.getChildren().addAll(this.graphicsPlayers.generateEffectFromActivePlayer());
		startingAnimation.getChildren().add(mainAnimation);
		startingAnimation.play();
	}

	private void handlePlayerOne(CardsModelBean references) {
		playerOneName.setText(this.graphicsPlayers.getAliasFromPlayerNumber(0));
		for(CustomImageView imageFromCard : this.graphicsPlayers.getDisplayableCardsFromPlayer(0, references)) {
			int currentCardIndex = playerOneCardsGrid.getChildren().size();
			playerOneCardsGrid.add(imageFromCard, currentCardIndex, 0);
		}
	}

	private void handlePlayerTwo(CardsModelBean references) {
		playerTwoName.setText(this.graphicsPlayers.getAliasFromPlayerNumber(1));
		for(CustomImageView imageFromCard : this.graphicsPlayers.getDisplayableCardsFromPlayer(1, references)) {
			int currentCardIndex = playerTwoCardsGrid.getChildren().size();
			playerTwoCardsGrid.add(imageFromCard, currentCardIndex, 0);
		}
	}

	private SequentialTransition annouceMessage(final String message) {
		if(this.eventsAnnouncer == null) {
			log.error("events null");
		}
		this.eventsAnnouncer.setText(message);
		FadeTransition displayAnimation = new FadeTransition(Duration.millis(750), this.eventsAnnouncer);
		displayAnimation.setFromValue(0.0);
		displayAnimation.setToValue(1.0);
		displayAnimation.setCycleCount(1);
		displayAnimation.setAutoReverse(false);

		PauseTransition pause = new PauseTransition(Duration.millis(1000));
		
		FadeTransition hideAnimation = new FadeTransition(Duration.millis(750), this.eventsAnnouncer);
		hideAnimation.setFromValue(1.0);
		hideAnimation.setToValue(0.0);
		hideAnimation.setCycleCount(1);
		hideAnimation.setAutoReverse(false);
		
		return new SequentialTransition(displayAnimation,pause,hideAnimation);
	}

	public void displayMessage(String message) {
		SequentialTransition coolMessage = annouceMessage(message);
		coolMessage.play();
	}

	public void chooseThisCardAndPlayIt(int cardIndex) {
		Card choosenCard = this.graphicsPlayers.chooseCardFromActivePlayerAt(cardIndex);
		log.debug(choosenCard.toString());
	}

	public void playOneTurn(final GraphicsReferences graphicsReferences) {
		final int playerIndex = graphicsReferences.getIndexFromActivePlayer();
		this.graphicsPlayers.setActivePlayer(playerIndex);
		String alias = this.graphicsPlayers.getAliasFromActivePlayer();
		
		SequentialTransition part01 = annouceMessage("Your turn, " + alias);
		part01.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(graphicsReferences.hasDrawnOneTime()) {
					SequentialTransition part01A = annouceMessage("You've drawn 1 card");
					SequentialTransition part01B = addCardToPlayer(playerIndex,graphicsReferences);
					ParallelTransition mainAnimation = new ParallelTransition(part01A,part01B);
					mainAnimation.play();
				}
			}
		});
		part01.play();
		//SequentialTransition part02 = annouceMessage("Your turn, " + alias);
	}
	
	private SequentialTransition addCardToPlayer(int playerIndex, GraphicsReferences graphicsReferences) {
		Card firstCardDrawn = graphicsReferences.getFirstCardDrawn();
		int cardIndex = this.graphicsPlayers.addCardToPlayer(playerIndex, firstCardDrawn);
		Image imageFromCard = ImageCardAssociator.retrieveImageFromIndex(firstCardDrawn.getImageIndex());
		CustomImageView imageView = new CustomImageView(imageFromCard, cardIndex, graphicsReferences.getCompatibilityFromFirstCard(),this);
		if(playerIndex == 0) {
			this.playerOneCardsGrid.add(imageView,this.playerOneCardsGrid.getChildren().size(),0);
		} else {
			this.playerTwoCardsGrid.add(imageView,this.playerOneCardsGrid.getChildren().size(),0);
		}
		SequentialTransition test = lol(imageView);
		return test;
	}
	
	private SequentialTransition lol(CustomImageView imageView) {
		FadeTransition displayAnimation = new FadeTransition(Duration.millis(50), imageView);
		displayAnimation.setFromValue(0.0);
		displayAnimation.setToValue(1.0);
		displayAnimation.setCycleCount(1);
		displayAnimation.setAutoReverse(false);

		PauseTransition pause = new PauseTransition(Duration.millis(1000));
		
		FadeTransition hideAnimation = new FadeTransition(Duration.millis(50), imageView);
		hideAnimation.setFromValue(1.0);
		hideAnimation.setToValue(0.0);
		hideAnimation.setCycleCount(1);
		hideAnimation.setAutoReverse(false);
		
		return new SequentialTransition(displayAnimation,pause,hideAnimation);
	}
}