package utt.fr.rglb.main.java.view.graphics.fxml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private static final Logger log = LoggerFactory.getLogger(CustomImageView.class);
	private ScaleTransition scaleTransition;
	private DoubleProperty expandToMaxProperty;
	private Image idleImage;
	private Image activeImage;
	private String cssClass;
	private boolean belongToActivePlayer;
	private boolean isCompatibleWithReferenceCard;
	private CustomImageView thisCustomImageView;

	public CustomImageView(Image img, final boolean isCompatibleWithReferenceCard) {
		super(ImageCardAssociator.retrieveIdleImage());
		this.thisCustomImageView = this;
		//Images
		this.belongToActivePlayer = false;
		this.activeImage = img;
		this.isCompatibleWithReferenceCard = isCompatibleWithReferenceCard;
		this.idleImage = ImageCardAssociator.retrieveIdleImage();
		
		//Animation
		this.expandToMaxProperty = new SimpleDoubleProperty(1.2);
		this.scaleTransition = new ScaleTransition(Duration.millis(200), this);
		this.scaleTransition.setCycleCount(1);
		this.scaleTransition.setInterpolator(Interpolator.EASE_BOTH);

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
				
				RotateTransition rotatorPart1 = new RotateTransition(Duration.millis(100), thisCustomImageView);
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
							belongToActivePlayer = false;
						} else {
							showCard();
							belongToActivePlayer = true;
						}
				    }
				});
		        RotateTransition rotatorPart2 = new RotateTransition(Duration.millis(100), thisCustomImageView);
		        rotatorPart2.setAxis(Rotate.Y_AXIS);
		        rotatorPart2.setFromAngle(-90);
		        rotatorPart2.setToAngle(0);
		        rotatorPart2.setInterpolator(Interpolator.LINEAR);
		        rotatorPart2.setCycleCount(1);
		        SequentialTransition sequentialTransition = new SequentialTransition(rotatorPart1,rotatorPart2);
		        sequentialTransition.play();
			}
		});
	}

	DoubleProperty expandToMaxProperty() {
		return expandToMaxProperty;
	}

	public void foldCard() {
		cssClass = "inactiveImageView";
		this.setImage(this.idleImage);
	}

	public void showCard() {
		if(isCompatibleWithReferenceCard) {
			cssClass = "activeCompatibleImageView";
		} else {
			cssClass = "activeIncompatibleImageView";
		}
		this.setImage(this.activeImage);
	}
}
