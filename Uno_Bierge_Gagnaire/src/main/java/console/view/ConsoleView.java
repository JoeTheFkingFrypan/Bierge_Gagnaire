package main.java.console.view;

public class ConsoleView extends View {
	public ConsoleView() {
		super();
		clearDisplay();
		displayHeader();
		displayAuthors();
	}
	
	private void displayHeader() {
		this.consoleDisplay.generateRedEmphasis("  UUUUUUUU     UUUUUUUU  NNNNNNNN        NNNNNNNN       OOOOOOOOO     ");
		this.consoleDisplay.generateRedEmphasis("  U::::::U     U::::::U  N:::::::N       N::::::N     OO:::::::::OO   ");
		this.consoleDisplay.generateRedEmphasis("  U::::::U     U::::::U  N::::::::N      N::::::N   OO:::::::::::::OO "); 
		this.consoleDisplay.generateRedEmphasis("  UU:::::U     U:::::UU  N:::::::::N     N::::::N  O:::::::OOO:::::::O"); 
		this.consoleDisplay.generateRedEmphasis("   U:::::U     U:::::U   N::::::::::N    N::::::N  O::::::O   O::::::O");  
		this.consoleDisplay.generateRedEmphasis("   U:::::D     D:::::U   N:::::::::::N   N::::::N  O:::::O     O:::::O");
		this.consoleDisplay.generateRedEmphasis("   U:::::D     D:::::U   N:::::::N::::N  N::::::N  O:::::O     O:::::O");
		this.consoleDisplay.generateRedEmphasis("   U:::::D     D:::::U   N::::::N N::::N N::::::N  O:::::O     O:::::O");
		this.consoleDisplay.generateRedEmphasis("   U:::::D     D:::::U   N::::::N  N::::N:::::::N  O:::::O     O:::::O");
		this.consoleDisplay.generateRedEmphasis("   U:::::D     D:::::U   N::::::N   N:::::::::::N  O:::::O     O:::::O");  
		this.consoleDisplay.generateRedEmphasis("   U:::::D     D:::::U   N::::::N    N::::::::::N  O:::::O     O:::::O");        
		this.consoleDisplay.generateRedEmphasis("   U::::::U   U::::::U   N::::::N     N:::::::::N  O::::::O   O::::::O");          
		this.consoleDisplay.generateRedEmphasis("   U:::::::UUU:::::::U   N::::::N      N::::::::N  O:::::::OOO:::::::O");
		this.consoleDisplay.generateRedEmphasis("    UU:::::::::::::UU    N::::::N       N:::::::N   OO:::::::::::::OO ");
		this.consoleDisplay.generateRedEmphasis("      UU:::::::::UU      N::::::N        N::::::N     OO:::::::::OO   ");
		this.consoleDisplay.generateRedEmphasis("        UUUUUUUUU        NNNNNNNN         NNNNNNN       OOOOOOOOO     ");
		this.consoleDisplay.generateRedEmphasis("");
	}
	
	private void displayAuthors() {
		this.consoleDisplay.generateGreenEmphasis("  ___  _ ____ ____ ____ ____      /    ____ ____ ____ _  _ ____ _ ____ ____");
		this.consoleDisplay.generateGreenEmphasis("  |__] | |___ |__/ | __ |___     /     | __ |__| | __ |\\ | |__| | |__/ |___");
		this.consoleDisplay.generateGreenEmphasis("  |__] | |___ |  \\ |__] |___    /      |__] |  | |__] | \\| |  | | |  \\ |___");    
		this.consoleDisplay.generateRedEmphasis("");                                                                
	}
}
