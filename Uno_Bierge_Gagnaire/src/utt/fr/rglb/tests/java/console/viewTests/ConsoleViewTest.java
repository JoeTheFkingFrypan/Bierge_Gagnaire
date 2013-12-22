package utt.fr.rglb.tests.java.console.viewTests;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Test;

import utt.fr.rglb.main.java.console.view.ConsoleView;

/**
 * Classe de tests unitaires validant le comportement des méthodes de la classe ConsoleView
 * @see ConsoleView
 */
@SuppressWarnings("unused")
public class ConsoleViewTest {
	private ConsoleView consoleView;
	private ByteArrayOutputStream outContent;

	@After
	public void cleanUp() {
		System.setProperty("jansi.passthrough", "true");
		System.setOut(System.out);
	}
	
	@Test
	public void voidTestCreateConsoleView() {
		System.setProperty("jansi.passthrough", "true");
		this.outContent  = new ByteArrayOutputStream();
		System.setOut(new PrintStream(this.outContent));
		this.consoleView = new ConsoleView();
		assertEquals(generateExpectedResultString(),this.outContent.toString());
		this.outContent.reset();
	}
	
	private String generateExpectedResultString() {
		return "[2J\r\n[1m[0;31m  UUUUUUUU     UUUUUUUU  NNNNNNNN        NNNNNNNN       OOOOOOOOO     [0;37m[0m\r\n[1m[0;31m  U::::::U     U::::::U  N:::::::N       N::::::N     OO:::::::::OO   [0;37m[0m\r\n[1m[0;31m  U::::::U     U::::::U  N::::::::N      N::::::N   OO:::::::::::::OO [0;37m[0m\r\n[1m[0;31m  UU:::::U     U:::::UU  N:::::::::N     N::::::N  O:::::::OOO:::::::O[0;37m[0m\r\n[1m[0;31m   U:::::U     U:::::U   N::::::::::N    N::::::N  O::::::O   O::::::O[0;37m[0m\r\n[1m[0;31m   U:::::D     D:::::U   N:::::::::::N   N::::::N  O:::::O     O:::::O[0;37m[0m\r\n[1m[0;31m   U:::::D     D:::::U   N:::::::N::::N  N::::::N  O:::::O     O:::::O[0;37m[0m\r\n[1m[0;31m   U:::::D     D:::::U   N::::::N N::::N N::::::N  O:::::O     O:::::O[0;37m[0m\r\n[1m[0;31m   U:::::D     D:::::U   N::::::N  N::::N:::::::N  O:::::O     O:::::O[0;37m[0m\r\n[1m[0;31m   U:::::D     D:::::U   N::::::N   N:::::::::::N  O:::::O     O:::::O[0;37m[0m\r\n[1m[0;31m   U:::::D     D:::::U   N::::::N    N::::::::::N  O:::::O     O:::::O[0;37m[0m\r\n[1m[0;31m   U::::::U   U::::::U   N::::::N     N:::::::::N  O::::::O   O::::::O[0;37m[0m\r\n[1m[0;31m   U:::::::UUU:::::::U   N::::::N      N::::::::N  O:::::::OOO:::::::O[0;37m[0m\r\n[1m[0;31m    UU:::::::::::::UU    N::::::N       N:::::::N   OO:::::::::::::OO [0;37m[0m\r\n[1m[0;31m      UU:::::::::UU      N::::::N        N::::::N     OO:::::::::OO   [0;37m[0m\r\n[1m[0;31m        UUUUUUUUU        NNNNNNNN         NNNNNNN       OOOOOOOOO     [0;37m[0m\r\n[1m[0;31m" + "" + "[0;37m[0m\r\n[1m[0;32m  ___  _ ____ ____ ____ ____      /    ____ ____ ____ _  _ ____ _ ____ ____[0;37m[0m\r\n[1m[0;32m  |__] | |___ |__/ | __ |___     /     | __ |__| | __ |\\ | |__| | |__/ |___[0;37m[0m\r\n[1m[0;32m  |__] | |___ |  \\ |__] |___    /      |__] |  | |__] | \\| |  | | |  \\ |___[0;37m[0m\r\n[1m[0;31m[0;37m[0m\r\n";
	}
}
