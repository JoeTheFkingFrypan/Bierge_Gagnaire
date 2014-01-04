package utt.fr.rglb.main.java.view.graphics.fxml;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class CustomImageView extends ImageView {
	private ScaleTransition scaleTransition;
	private DoubleProperty expandToMaxProperty;
	private String cssClass;

	public CustomImageView(Image img, boolean isCompatibleWithReferenceCard) {
		super(img);
		
		this.expandToMaxProperty = new SimpleDoubleProperty(1.2);
		this.scaleTransition = new ScaleTransition(Duration.millis(200), this);
		this.scaleTransition.setCycleCount(1);
		this.scaleTransition.setInterpolator(Interpolator.EASE_BOTH);
		
		if(isCompatibleWithReferenceCard) {
			this.cssClass = "activeCompatibleImageView";
		} else {
			this.cssClass = "activeIncompatibleImageView";
		}
		
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
	}
	
	DoubleProperty expandToMaxProperty() {
        return expandToMaxProperty;
	}
}
