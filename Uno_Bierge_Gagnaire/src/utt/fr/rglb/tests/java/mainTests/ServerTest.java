package utt.fr.rglb.tests.java.mainTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

import utt.fr.rglb.main.java.main.Server;
import utt.fr.rglb.main.java.view.console.ConsoleView;

/**
 * Classe de tests unitaires validant le comportement des méthodes de la classe Server
 * @see Server
 */
public class ServerTest {
	private Server referenceServer;
	private ConsoleView mockedView;

	@Before
	public void setup() {
		this.mockedView = mock(ConsoleView.class);
		this.referenceServer = Server.getInstance(mockedView);
	}
	
	@Test
	public void testSingleton() {
		Server anotherServer = Server.getInstance(mockedView);
		Server yetAnotherServer = Server.getInstance(mockedView);
		assertEquals(this.referenceServer, anotherServer);
		assertEquals(this.referenceServer, yetAnotherServer);
	}
}
