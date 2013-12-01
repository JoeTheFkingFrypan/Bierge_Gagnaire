package main.java.console.model;



public class ConsoleCodesAnsi {
	//Styles
	public static final String ANSI_BOLD = "\u001b[1m";					//Bold
	public static final String ANSI_CLS = "\u001b[2J";					//Clear console
	public static final String ANSI_HOME = "\u001b[H";					//Top of display
	public static final String ANSI_NORMAL = "\u001b[0m";				//Normal attributes
	public static final String ANSI_AT55 = "\u001b[10;10H";				//Paragraph-ish
	public static final String ANSI_REVERSEON = "\u001b[7m";			//No freaking idea
	public static final String ANSI_WHITEONBLUE = "\u001b[37;44m";		//White Color

	//Colors
	public static final String COLOR_RED = "\u001B[0;31m";
	public static final String COLOR_BLUE = "\u001B[0;34m";
	public static final String COLOR_CYAN = "\u001B[0;36m";
	public static final String COLOR_WHITE = "\u001B[0;37m";
	public static final String COLOR_BLACK = "\u001B[0;30m";
	public static final String COLOR_GREEN = "\u001B[0;32m";
	public static final String COLOR_YELLOW = "\u001B[0;33m";
	public static final String COLOR_MAGENTA = "\u001B[0;35m";
}
