package utt.fr.rglb.main.java.console.model;

import java.io.Serializable;

/**
 * Classe contenant une énumération statique de codes ANSI </br>
 * Permet l'affichage en couleur dans la console Windows, Linux & Eclipse
 */
public class ConsoleCodesAnsi implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//Styles
	public static final String ANSI_BOLD = "\u001b[1m";					//Bold
	public static final String ANSI_CLS = "\u001b[2J";					//Clear console
	public static final String ANSI_HOME = "\u001b[H";					//Top of display
	public static final String ANSI_NORMAL = "\u001b[0m";				//Normal attributes
	public static final String ANSI_AT55 = "\u001b[10;10H";				//Paragraph-ish
	public static final String ANSI_REVERSEON = "\u001b[7m";			//No freaking idea
	public static final String ANSI_WHITEONBLUE = "\u001b[37;44m";		//White Color

	//Colors
	public static final String COLOR_RED = "\u001B[0;31m";				//Texte rouge
	public static final String COLOR_BLUE = "\u001B[0;34m";				//Texte bleu
	public static final String COLOR_CYAN = "\u001B[0;36m";				//Texte cyan
	public static final String COLOR_WHITE = "\u001B[0;37m";			//Texte blanc
	public static final String COLOR_BLACK = "\u001B[0;30m";			//Texte noir
	public static final String COLOR_GREEN = "\u001B[0;32m";			//Texte vert
	public static final String COLOR_YELLOW = "\u001B[0;33m";			//Texte jaune
	public static final String COLOR_MAGENTA = "\u001B[0;35m";			//Texte violet
}