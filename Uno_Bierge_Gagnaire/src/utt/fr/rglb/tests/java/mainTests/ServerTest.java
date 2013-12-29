package utt.fr.rglb.tests.java.mainTests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import utt.fr.rglb.main.java.main.Server;

/**
 * Classe de tests unitaires validant le comportement des méthodes de la classe Server
 * @see Server
 */
public class ServerTest {
	private Server referenceServer;

	@Before
	public void setup() {
		this.referenceServer = Server.getInstance();
	}
	
	@Test
	public void testSingleton() {
		Server anotherServer = Server.getInstance();
		Server yetAnotherServer = Server.getInstance();
		assertEquals(this.referenceServer, anotherServer);
		assertEquals(this.referenceServer, yetAnotherServer);
	}
}
