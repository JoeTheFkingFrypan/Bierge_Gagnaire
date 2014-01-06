package utt.fr.rglb.main.java.view.graphics.fxml;

import utt.fr.rglb.main.java.dao.ImageCardAssociator;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class CustomImageView extends ImageView {
	private ScaleTransition scaleTransition;
	private DoubleProperty expandToMaxProperty;
	private Image idleImage;
	private Image activeImage;
	private String cssClass;
	private boolean belongToActivePlayer;
	private boolean belongToReferenceCard;
	private boolean isCompatibleWithReferenceCard;
	private FXMLControllerGameScreen controller;
	private int cardIndex;

	public CustomImageView(Image img, FXMLControllerGameScreen fxmlControllerGameScreen) {
		super(ImageCardAssociator.retrieveIdleImage());
		this.controller = fxmlControllerGameScreen;
		this.activeImage = img;
		this.cardIndex = 0;
		this.belongToActivePlayer = false;
		this.belongToReferenceCard = true;
		this.isCompatibleWithReferenceCard = false;
		this.idleImage = ImageCardAssociator.retrieveIdleImage();

		//Animation Related
		this.expandToMaxProperty = new SimpleDoubleProperty(1.2);
		this.scaleTransition = new ScaleTransition(Duration.millis(200), this);
		this.scaleTransition.setCycleCount(1);
		this.scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
		setAnimations();
	}

	public CustomImageView(Image img, int cardIndex, final boolean isCompatibleWithReferenceCard, FXMLControllerGameScreen fxmlControllerGameScreen) {
		super(ImageCardAssociator.retrieveIdleImage());
		this.controller = fxmlControllerGameScreen;
		//Images Related
		this.activeImage = img;
		this.cardIndex = cardIndex;
		this.belongToActivePlayer = false;
		this.belongToReferenceCard = false;
		this.isCompatibleWithReferenceCard = isCompatibleWithReferenceCard;
		this.idleImage = ImageCardAssociator.retrieveIdleImage();

		//Animation Related
		this.expandToMaxProperty = new SimpleDoubleProperty(1.2);
		this.scaleTransition = new ScaleTransition(Duration.millis(200), this);
		this.scaleTransition.setCycleCount(1);
		this.scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
		setAnimations();
	}

	private void setAnimations() {
		this.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				getStyleClass().add(cssClass);
				scaleTransition.setFromX(getScaleX());
				scaleTransition.setFromY(getScaleY());
				scaleTransition.setToX(expandToMaxProperty.get());
				scaleTransition.setToY(expandToMaxProperty.get());
				scaleTransition.playFromStart();
			}
		});
		this.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				getStyleClass().remove(cssClass);
				scaleTransition.setFromX(getScaleX());
				scaleTransition.setFromY(getScaleY());
				scaleTransition.setToX(1);
				scaleTransition.setToY(1);
				scaleTransition.playFromStart();
			}
		});
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				if(!belongToActivePlayer) {
					controller.displayMessage("Your cards are below");
				} else {
					if(!isCompatibleWithReferenceCard) {
						controller.displayMessage("Card cannot be played");
					} else {
						controller.chooseThisCardAndPlayIt(cardIndex);
					}	
				}		
			}
		});
	}

	DoubleProperty expandToMaxProperty() {
		return expandToMaxProperty;
	}

	private void foldCard() {
		cssClass = "inactiveImageView";
		belongToActivePlayer = false;
		this.setImage(this.idleImage);
	}

	private void showCard() {
		if(!belongToReferenceCard) {
			if(isCompatibleWithReferenceCard) {
				cssClass = "activeCompatibleImageView";
			} else {
				cssClass = "activeIncompatibleImageView";
			}
		}
		belongToActivePlayer = true;
		this.setImage(this.activeImage);
	}

	public SequentialTransition generateEffect() {
		RotateTransition rotatorPart1 = new RotateTransition(Duration.millis(75), this);
		rotatorPart1.setAxis(Rotate.Y_AXIS);
		rotatorPart1.setFromAngle(0);
		rotatorPart1.setToAngle(90);
		rotatorPart1.setInterpolator(Interpolator.LINEAR);
		rotatorPart1.setCycleCount(1);
		rotatorPart1.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(belongToActivePlayer) {
					foldCard();
				} else {
					showCard();
				}
			}
		});
		/*TranslateTransition tt = new TranslateTransition(Duration.millis(2000), thisImageView);
		tt.setToX(0);
		tt.setToY(0);
		tt.setCycleCount(1);
		tt.setAutoReverse(true);
		tt.play();*/
		RotateTransition rotatorPart2 = new RotateTransition(Duration.millis(75), this);
		rotatorPart2.setAxis(Rotate.Y_AXIS);
		rotatorPart2.setFromAngle(-90);
		rotatorPart2.setToAngle(0);
		rotatorPart2.setInterpolator(Interpolator.LINEAR);
		rotatorPart2.setCycleCount(1);
		return new SequentialTransition(rotatorPart1,rotatorPart2);
	}
}
