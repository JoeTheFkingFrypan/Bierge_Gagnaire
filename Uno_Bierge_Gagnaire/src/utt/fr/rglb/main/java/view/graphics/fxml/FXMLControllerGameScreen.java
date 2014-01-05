package utt.fr.rglb.main.java.view.graphics.fxml;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.dao.ImageCardAssociator;
import utt.fr.rglb.main.java.game.controller.GameControllerGraphicsOriented;
import utt.fr.rglb.main.java.main.Server;
import utt.fr.rglb.main.java.player.controller.PlayerControllerGraphicsOriented;

public class FXMLControllerGameScreen extends AbstractFXMLController {
	private static final Logger log = LoggerFactory.getLogger(FXMLControllerGameScreen.class);
	private GameControllerGraphicsOriented gameController;
	private List<PlayerControllerGraphicsOriented> players;

	@FXML private GridPane mainGrid;
	@FXML private GridPane playerTwoCardsGrid;
	@FXML private Label playerTwoName;
	@FXML private ImageView lastCardPlayed;
	@FXML private Text eventsAnnouncer;
	@FXML private Label playerOneName;
	@FXML private GridPane playerOneCardsGrid;

	public FXMLControllerGameScreen() {
		this.gameController = Server.getGameController();
		this.gameController.setCurrentFXMLController(this);
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		eventsAnnouncer.setEffect(createSwaggifiedAnnoucementEffect());
		annouceMessage("New round starting !");
		this.players = this.gameController.getAllPlayers();

		Card referenceCard = this.gameController.retrieveImageFromLastCardPlayed();
		Image imageFromReferenceCard = ImageCardAssociator.retrieveImageFromIndex(referenceCard.getImageIndex());
		lastCardPlayed.setImage(imageFromReferenceCard);

		int playerIndex = 0;
		for(PlayerControllerGraphicsOriented player : this.players) {
			if(playerIndex == 0) {
				playerOneName.setText(player.getAlias());;
				int currentCardIndex = 0;
				for(Card card : player.getCardsInHand()) {
					Image imageFromCard = ImageCardAssociator.retrieveImageFromIndex(card.getImageIndex());
					CustomImageView currentImageView = null;
					if(referenceCard.isCompatibleWith(card)) {
						currentImageView = new CustomImageView(imageFromCard,true);
					} else {
						currentImageView = new CustomImageView(imageFromCard,false);
					}
					playerOneCardsGrid.add(currentImageView, currentCardIndex++, 0);
				}
			} else if(playerIndex == 1) {
				playerTwoName.setText(player.getAlias());;
				int currentCardIndex = 0;
				for(Card card : player.getCardsInHand()) {
					Image imageFromCard = ImageCardAssociator.retrieveImageFromIndex(card.getImageIndex());
					CustomImageView currentImageView = null;
					if(referenceCard.isCompatibleWith(card)) {
						currentImageView = new CustomImageView(imageFromCard,true);
					} else {
						currentImageView = new CustomImageView(imageFromCard,false);
					}
					playerTwoCardsGrid.add(currentImageView, currentCardIndex++, 0);
				}
			}
			playerIndex++;
		}
	}

	private void annouceMessage(final String message) {

		FadeTransition displayAnimation = new FadeTransition(Duration.millis(3000), this.eventsAnnouncer);
		displayAnimation.setFromValue(0.0);
		displayAnimation.setToValue(1.0);
		displayAnimation.setCycleCount(1);
		displayAnimation.setAutoReverse(false);
		
		/*final Transition displayAnimation = new Transition() {
			{
				setCycleDuration(Duration.millis(1000));
			}
			protected void interpolate(double frac) {
				final int length = message.length();
				final int n = Math.round(length * (float) frac);
				eventsAnnouncer.setText(message.substring(0, n));
			}
		};*/

		FadeTransition hideAnimation = new FadeTransition(Duration.millis(3000), this.eventsAnnouncer);
		hideAnimation.setFromValue(1.0);
		hideAnimation.setToValue(0.0);
		hideAnimation.setCycleCount(1);
		hideAnimation.setAutoReverse(false);

		PauseTransition pt = new PauseTransition(Duration.millis(1000));
		SequentialTransition sequentialTransition = new SequentialTransition(displayAnimation,pt,hideAnimation);
		sequentialTransition.play();

		this.eventsAnnouncer.setText(message);
	}
}