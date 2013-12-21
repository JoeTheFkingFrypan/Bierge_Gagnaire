package utt.fr.rglb.tests.java.console.modelTests;

import org.junit.Test;

import utt.fr.rglb.main.java.console.model.ConsoleException;

/**
 * Classe de tests unitaires validant le comportement des constructeurs de ConsoleException
 * @see ConsoleException
 */
public class ConsoleExceptionTest {
	
	@Test(expected=ConsoleException.class)
	public void testCreateExceptionWithMessage() {
		throw new ConsoleException("test");
	}
	
	@Test(expected=ConsoleException.class)
	public void testCreateExceptionPropagationAnotherException() {
		try { 
			throw new Exception();
		} catch(Exception e) {
			throw new ConsoleException(e);
		}
	}
	
	@Test(expected=ConsoleException.class)
	public void testCreateExceptionPropagationAnotherExceptionWithMessage() {
		try { 
			throw new Exception();
		} catch(Exception e) {
			throw new ConsoleException("test",e);
		}
	}
}
