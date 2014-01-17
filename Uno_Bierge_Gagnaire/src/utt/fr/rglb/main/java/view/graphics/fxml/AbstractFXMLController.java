package utt.fr.rglb.main.java.view.graphics.fxml;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public abstract class AbstractFXMLController implements Initializable {
	private static final Logger log = LoggerFactory.getLogger(AbstractFXMLController.class);

	/* ========================================= HEADER 1 ========================================= */
	
	/**
	 * Méthode permettant de créer un message avec effets spéciaux
	 * @param text Message à afficher
	 * @return Text correspondant au texte + effets
	 */
	protected Text createSwaggifiedHeader(String text) {
		Text fancyText = new Text(text);
		fancyText.getStyleClass().add("fancyText");
		Blend mainBlend = new Blend();
		mainBlend.setMode(BlendMode.MULTIPLY);
		setButtomInput(mainBlend);
		setTopInput(mainBlend);
		fancyText.setEffect(mainBlend);
		return fancyText;
	}

	private void setButtomInput(Blend mainBlend) {
		DropShadow dropShadowsBottomInput = new DropShadow();
		dropShadowsBottomInput.setColor(Color.rgb(254, 235, 66, 0.3));
		dropShadowsBottomInput.setOffsetX(5);
		dropShadowsBottomInput.setOffsetY(5);
		dropShadowsBottomInput.setRadius(5);
		dropShadowsBottomInput.setSpread(0.2);

		mainBlend.setBottomInput(dropShadowsBottomInput);
	}

	private void setTopInput(Blend mainBlend) {
		Blend topBlend = new Blend();
		topBlend.setMode(BlendMode.MULTIPLY);

		setInnerBottomInput(topBlend);
		setInnerTopInput(topBlend);

		mainBlend.setTopInput(topBlend);
	}

	private void setInnerBottomInput(Blend topBlend) {
		DropShadow ds1 = new DropShadow();
		ds1.setColor(Color.web("#f13a00"));
		ds1.setRadius(20);
		ds1.setSpread(0.2);
		topBlend.setBottomInput(ds1);
	}

	private void setInnerTopInput(Blend topBlend) {
		Blend innerTopBlend = new Blend();
		innerTopBlend.setMode(BlendMode.MULTIPLY);

		InnerShadow is = new InnerShadow();
		is.setColor(Color.web("#feeb42"));
		is.setRadius(9);
		is.setChoke(0.8);
		innerTopBlend.setBottomInput(is);

		InnerShadow is1 = new InnerShadow();
		is1.setColor(Color.web("#f13a00"));
		is1.setRadius(5);
		is1.setChoke(0.4);
		innerTopBlend.setTopInput(is1);

		topBlend.setTopInput(innerTopBlend);
	}

	/* ========================================= ERROR BUTTON ========================================= */
	
	protected Button createErrorButton(final Scene scene, final String ressourceToLoad) {
		Button goForIt = new Button("Continue to setup screen");
		goForIt.getStyleClass().add("declineButton");
		goForIt.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				try {
					log.info("Loading JavaFX setup screen from file : \"" + ressourceToLoad + "\"");
					Parent root= FXMLLoader.load(getClass().getResource("/utt/fr/rglb/main/ressources/fxml/" + ressourceToLoad));
					scene.setRoot(root);
				} catch (IOException e1) {
					throw new FXMLControllerException("[ERROR] While trying to load screen from \"" + ressourceToLoad + "\"",e1);
				}
			}
		});
		return goForIt;
	}

	/* ========================================= HEADER 2 ========================================= */
	
	protected Blend createSwaggifiedAnnoucementEffect() {

		Blend mainBlend = new Blend();
		mainBlend.setMode(BlendMode.SRC_OVER);

		DropShadow bottomInputFirstLayer = new DropShadow();
		bottomInputFirstLayer.setColor(Color.rgb(0,0,0,0.1));
		bottomInputFirstLayer.setOffsetX(8);
		bottomInputFirstLayer.setOffsetY(8);
		bottomInputFirstLayer.setRadius(2);

		Blend topBlendFirstLayer = new Blend();
		topBlendFirstLayer.setMode(BlendMode.SRC_OVER);

		DropShadow bottomInputSecondLayer = new DropShadow();
		bottomInputSecondLayer.setColor(Color.web("#21233f"));
		bottomInputSecondLayer.setRadius(4);
		bottomInputSecondLayer.setSpread(0.9);

		Blend topBlendSecondLayer = new Blend();
		topBlendSecondLayer.setMode(BlendMode.DARKEN);

		InnerShadow bottomInputThirdLayer = new InnerShadow();
		bottomInputThirdLayer.setColor(Color.web("#cee3f4"));
		bottomInputThirdLayer.setRadius(5);
		bottomInputThirdLayer.setChoke(0.7);
		bottomInputThirdLayer.setOffsetX(-4);
		bottomInputThirdLayer.setOffsetY(-4);

		Blend topBlendThirdLayer = new Blend();
		topBlendThirdLayer.setMode(BlendMode.MULTIPLY);

		InnerShadow bottomInputForthLayer = new InnerShadow();
		bottomInputForthLayer.setColor(Color.web("#6c7fee"));
		bottomInputForthLayer.setRadius(7);
		bottomInputForthLayer.setChoke(0.2);
		bottomInputForthLayer.setOffsetX(2);
		bottomInputForthLayer.setOffsetY(2);

		InnerShadow topInputForthLayer = new InnerShadow();
		topInputForthLayer.setColor(Color.web("#a5ebff"));
		topInputForthLayer.setRadius(4);
		topInputForthLayer.setOffsetX(-2);
		topInputForthLayer.setOffsetY(-2);

		topBlendThirdLayer.setBottomInput(bottomInputForthLayer);
		topBlendThirdLayer.setTopInput(topInputForthLayer);
		topBlendSecondLayer.setBottomInput(bottomInputThirdLayer);
		topBlendSecondLayer.setTopInput(topBlendThirdLayer);
		topBlendFirstLayer.setBottomInput(bottomInputSecondLayer);
		topBlendFirstLayer.setTopInput(topBlendSecondLayer);
		mainBlend.setBottomInput(bottomInputFirstLayer);
		mainBlend.setTopInput(topBlendFirstLayer);
		
		return mainBlend;
	}
}
