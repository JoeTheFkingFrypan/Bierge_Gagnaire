package utt.fr.rglb.main.java.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.cards.model.basics.Color;
import javafx.scene.image.Image;

/**
 * Classe permettant d'associer une image à chaque index de carte </br>
 * Le chargement des images est multi-threadé, de sorte à ne pas impacter les performances </br>
 * Utilisation d'un tableau de taille fixe afin de permettre un accès direct aux images
 */
public class ImageCardAssociator  {
	private static final Logger log = LoggerFactory.getLogger(ImageCardAssociator.class);
	private String path;
	private String extension;
	private double height;
	private double width;
	private static Image[] imagesFromCards;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	/**
	 * Constructeur de ImageCardAssociator </br>
	 * C'est ici qu'est défini la taille des images qui seront affichées (hauteur/largeur), leur chemin, et leur extension
	 */
	public ImageCardAssociator() {
		this.path = "/utt/fr/rglb/main/ressources/images/cards/";
		this.extension = ".jpg";
		this.width = 80;
		this.height = 125;
		ImageCardAssociator.imagesFromCards = null;
	}
	
	/* ========================================= IMAGE LOADING ========================================= */
	
	/**
	 * Méthode permettant le chargement des images depuis le disque dur </br>
	 * Affiche le pourcentage de complétion en utilisant les logs
	 */
	public void backgroundLoadImages() {
		log.info("Background loading of all 63 images from cards");
		ImageCardAssociator.imagesFromCards = new Image[63];
		loadAllImagesFromCards();
		log.info("Loading complete (100%)");
	}
	
	/**
	 * Méthode privée permettant le chargement des images à partir des fichiers du disque dur </br>
	 * Chaque possède un index distinct permettant un futur accès direct (total de 63 images)
	 */
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
	
	/**
	 * Méthode privée permettant de charger toutes les images d'une couleur donnée
	 * @param currentColor Couleur correspondant aux images à charger
	 * @param imagesAlreadyLoaded Index des cartes à charger
	 * @return Le nombre d'images ayant été chargées
	 */
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

	/* ========================================= IMAGE RETRIEVING ========================================= */
	
	/**
	 * Méthode permettant de récupérer l'image associée à l'index founi
	 * @param imageIndex Index correspondant à l'image de la carte
	 * @return Image associée à la carte
	 */
	public static Image retrieveImageFromIndex(int imageIndex) {
		Preconditions.checkState(ImageCardAssociator.imagesFromCards != null,"[ERROR] Images must be loaded before displaying any cards");
		return ImageCardAssociator.imagesFromCards[imageIndex];
	}
	
	/**
	 * Méthode permettant de récupérer l'image du dos de toutes les cartes de UNO
	 * @return Image du dos d'une carte de UNO
	 */
	public static Image retrieveIdleImage() {
		return ImageCardAssociator.imagesFromCards[54];
	}
	
	/**
	 * Méthode permettant de récupérer l'image PERSONNALISEE correspondant au choix d'une couleur après jeu d'un JOKER </br>
	 * Il s'agit de 8 images éditées afin de représenter graphiquement le choix d'une couleur après jeu d'un JOKER
	 * @param color Couleur choisie
	 * @return Image d'une carte JOKER associée au choix fait par l'utilisateur
	 */
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
	
	/**
	 * Méthode permettant de récupérer l'image PERSONNALISEE correspondant au choix d'une couleur après jeu d'un +4 </br>
	 * Il s'agit de 8 images éditées afin de représenter graphiquement le choix d'une couleur après jeu d'un +4
	 * @param color Couleur choisie
	 * @return Image d'une carte +4 associée au choix fait par l'utilisateur
	 */
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
