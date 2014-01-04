package utt.fr.rglb.main.java.view.graphics.fxml;

import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.dao.ImageCardAssociator;
import utt.fr.rglb.main.java.game.controller.GameControllerGraphicsOriented;
import utt.fr.rglb.main.java.main.Server;

public class FXMLControllerGameScreen extends AbstractFXMLController {
	private static final Logger log = LoggerFactory.getLogger(FXMLControllerGameScreen.class);
	private GameControllerGraphicsOriented gameController;

	@FXML private GridPane playerTwoCardsGrid;
	@FXML private Label playerTwoName;
	@FXML private ImageView lastCardPlayed;
	@FXML private Label playerOneName;
	@FXML private GridPane playerOneCardsGrid;

	public FXMLControllerGameScreen() {
		this.gameController = Server.getGameController();
		this.gameController.setCurrentFXMLController(this);
	}

	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		Card referenceCard = this.gameController.retrieveImageFromLastCardPlayed();
		Image imageFromReferenceCard = ImageCardAssociator.retrieveImageFromIndex(referenceCard.getImageIndex());
		lastCardPlayed.setImage(imageFromReferenceCard);
		Map<String,Collection<Card>> cardsFromPlayers = this.gameController.getAllCardsFromPlayers();

		int playerIndex = 0;
		for(Entry<String, Collection<Card>> playerEntry : cardsFromPlayers.entrySet()) {
			if(playerIndex == 0) {
				playerOneName.setText(playerEntry.getKey());;
				int currentCardIndex = 0;
				for(Card card : playerEntry.getValue()) {
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
				playerTwoName.setText(playerEntry.getKey());;
				int currentCardIndex = 0;
				for(Card card : playerEntry.getValue()) {
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
}