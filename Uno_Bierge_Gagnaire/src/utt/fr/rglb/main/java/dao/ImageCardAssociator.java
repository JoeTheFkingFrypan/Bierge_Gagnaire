package utt.fr.rglb.main.java.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.cards.model.basics.Color;
import javafx.scene.image.Image;

public class ImageCardAssociator  {
	private static final Logger log = LoggerFactory.getLogger(ImageCardAssociator.class);
	private String path;
	private String extension;
	private double height;
	private double width;
	private static Image[] imagesFromCards;
	
	public ImageCardAssociator() {
		this.path = "/utt/fr/rglb/main/ressources/images/cards/";
		this.extension = ".jpg";
		this.width = 80;
		this.height = 125;
		ImageCardAssociator.imagesFromCards = null;
	}
	
	public void backgroundLoadImages() {
		log.info("Background loading of all 63 images from cards");
		ImageCardAssociator.imagesFromCards = new Image[63];
		loadAllImagesFromCards();
		log.info("Loading complete (100%)");
	}
	
	public static Image retrieveImageFromIndex(int imageIndex) {
		Preconditions.checkState(ImageCardAssociator.imagesFromCards != null,"[ERROR] Images must be loaded before displaying any cards");
		return ImageCardAssociator.imagesFromCards[imageIndex];
	}
	
	private void loadAllImagesFromCards() {
		int imagesAlreadyLoaded = 0;
		String currentColor = Color.RED.toString().toLowerCase();
		imagesAlreadyLoaded = loadImagesFromCardsWithSpecificColor(currentColor, imagesAlreadyLoaded);
		log.info("    |--  Loading progress (" + ((imagesAlreadyLoaded*100)/63) +  "%)");
		currentColor = Color.BLUE.toString().toLowerCase();
		imagesAlreadyLoaded = loadImagesFromCardsWithSpecificColor(currentColor, imagesAlreadyLoaded);
		log.info("    |--  Loading progress (" + ((imagesAlreadyLoaded*100)/63) +  "%)");
		currentColor = Color.YELLOW.toString().toLowerCase();
		imagesAlreadyLoaded = loadImagesFromCardsWithSpecificColor(currentColor, imagesAlreadyLoaded);
		log.info("    |--  Loading progress (" + ((imagesAlreadyLoaded*100)/63) +  "%)");
		currentColor = Color.GREEN.toString().toLowerCase();
		imagesAlreadyLoaded = loadImagesFromCardsWithSpecificColor(currentColor, imagesAlreadyLoaded);
		log.info("    |--  Loading progress (" + ((imagesAlreadyLoaded*100)/63) +  "%)");
		currentColor = Color.JOKER.toString().toLowerCase();
		ImageCardAssociator.imagesFromCards[imagesAlreadyLoaded++] = new Image(this.path + currentColor + "Joker" + this.extension, this.width,this.height,true,true,true);
		ImageCardAssociator.imagesFromCards[imagesAlreadyLoaded++] = new Image(this.path + currentColor + "+4" + this.extension, this.width,this.height,true,true,true);
		ImageCardAssociator.imagesFromCards[imagesAlreadyLoaded++] = new Image(this.path + "backOfCard" + this.extension, this.width,this.height,true,true,true);
		ImageCardAssociator.imagesFromCards[imagesAlreadyLoaded++] = new Image(this.path + currentColor + "Joker_Red" + this.extension, this.width,this.height,true,true,true);
		ImageCardAssociator.imagesFromCards[imagesAlreadyLoaded++] = new Image(this.path + currentColor + "Joker_Blue" + this.extension, this.width,this.height,true,true,true);
		ImageCardAssociator.imagesFromCards[imagesAlreadyLoaded++] = new Image(this.path + currentColor + "Joker_Green" + this.extension, this.width,this.height,true,true,true);
		ImageCardAssociator.imagesFromCards[imagesAlreadyLoaded++] = new Image(this.path + currentColor + "Joker_Yellow" + this.extension, this.width,this.height,true,true,true);
		ImageCardAssociator.imagesFromCards[imagesAlreadyLoaded++] = new Image(this.path + currentColor + "+4_Red" + this.extension, this.width,this.height,true,true,true);
		ImageCardAssociator.imagesFromCards[imagesAlreadyLoaded++] = new Image(this.path + currentColor + "+4_Blue" + this.extension, this.width,this.height,true,true,true);
		ImageCardAssociator.imagesFromCards[imagesAlreadyLoaded++] = new Image(this.path + currentColor + "+4_Green" + this.extension, this.width,this.height,true,true,true);
		ImageCardAssociator.imagesFromCards[imagesAlreadyLoaded++] = new Image(this.path + currentColor + "+4_Yellow" + this.extension, this.width,this.height,true,true,true);
	}
	
	private int loadImagesFromCardsWithSpecificColor(String currentColor, int imagesAlreadyLoaded) {
		String path = this.path;
		String extension = this.extension;
		for(int i=0; i<10; i++) {
			ImageCardAssociator.imagesFromCards[imagesAlreadyLoaded++] = new Image(path + currentColor + i + extension, this.width,this.height,true,true,true);
		}
		ImageCardAssociator.imagesFromCards[imagesAlreadyLoaded++] = new Image(path + currentColor + "+2" + extension, this.width,this.height,true,true,true);
		ImageCardAssociator.imagesFromCards[imagesAlreadyLoaded++] = new Image(path + currentColor + "Inversion" + extension, this.width,this.height,true,true,true);
		ImageCardAssociator.imagesFromCards[imagesAlreadyLoaded++] = new Image(path + currentColor + "Passe" + extension, this.width,this.height,true,true,true);
		return imagesAlreadyLoaded;
	}

	public static Image retrieveIdleImage() {
		return ImageCardAssociator.imagesFromCards[54];
	}
	
	public static Image retrieveCustomJokerImage(Color color) {
		if(color.equals(Color.RED)) {
			return ImageCardAssociator.imagesFromCards[55];
		} else if(color.equals(Color.BLUE)) {
			return ImageCardAssociator.imagesFromCards[56];
		} else if(color.equals(Color.GREEN)) {
			return ImageCardAssociator.imagesFromCards[57];
		} else {
			return ImageCardAssociator.imagesFromCards[58];
		}
	}
	
	public static Image retrieveCustomPlusFourImage(Color color) {
		if(color.equals(Color.RED)) {
			return ImageCardAssociator.imagesFromCards[59];
		} else if(color.equals(Color.BLUE)) {
			return ImageCardAssociator.imagesFromCards[60];
		} else if(color.equals(Color.GREEN)) {
			return ImageCardAssociator.imagesFromCards[61];
		} else {
			return ImageCardAssociator.imagesFromCards[62];
		}
	}
}
