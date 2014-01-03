package utt.fr.rglb.main.java.view.graphics.fxml;

import javafx.fxml.Initializable;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public abstract class AbstractFXMLController implements Initializable {
	
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
}
