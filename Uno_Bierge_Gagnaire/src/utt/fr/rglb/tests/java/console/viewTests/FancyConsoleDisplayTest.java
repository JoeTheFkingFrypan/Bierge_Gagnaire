package utt.fr.rglb.tests.java.console.viewTests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utt.fr.rglb.main.java.view.console.FancyConsoleDisplay;

/**
 * Classe de tests unitaires validant le comportement des méthodes de la classe FancyConsoleDisplay
 * @see FancyConsoleDisplay
 */
public class FancyConsoleDisplayTest {
	private FancyConsoleDisplay consoleDisplay;
	private ByteArrayOutputStream outContent;

	@Before
	public void setup() {
		System.setProperty("jansi.passthrough", "true");
		this.consoleDisplay = new FancyConsoleDisplay();
		this.outContent  = new ByteArrayOutputStream();
		System.setOut(new PrintStream(this.outContent));
	}
	
	@After
	public void cleanUp() {
		System.setProperty("jansi.passthrough", "true");
		System.setOut(System.out);
	}
	
	/* ========================================= ONE LINE TEXT ========================================= */
	
	@Test(expected=NullPointerException.class)
	public void testFailToDisplayBoldText() {
		this.consoleDisplay.displayBoldText(null);
	}

	@Test(expected=NullPointerException.class)
	public void testFailToDisplayErrorText() {
		this.consoleDisplay.displayErrorText(null);
	}

	@Test(expected=NullPointerException.class)
	public void testFailToDisplaySucessText() {
		this.consoleDisplay.displaySuccessText(null);
	}

	/* ========================================= HEADER & EMPHASIS ========================================= */

	@Test(expected=NullPointerException.class)
	public void testFailToDisplaySeparationText() {
		this.consoleDisplay.displaySeparationText(null);
	}

	@Test(expected=NullPointerException.class)
	public void testFailToGenerateRedEmphasis() {
		this.consoleDisplay.generateRedEmphasis(null);
	}

	@Test(expected=NullPointerException.class)
	public void testFailToGenerateGreenEmphasis() {
		this.consoleDisplay.generateGreenEmphasis(null);
	}

	@Test(expected=NullPointerException.class)
	public void testFailToGenerateWhiteOnBlueEmphasis() {
		this.consoleDisplay.generateWhiteOnBlueEmphasis(null);
	}

	@Test(expected=NullPointerException.class)
	public void testFailToGenerateJokerEmphasis() {
		this.consoleDisplay.generateJokerEmphasis(null);
	}

	/* ========================================= APPEND TEXT ========================================= */

	@Test(expected=NullPointerException.class)
	public void testFailToAppendBoldText() {
		this.consoleDisplay.appendBoldText(null);
	}

	@Test(expected=NullPointerException.class)
	public void testFailToAppendBlueText() {
		this.consoleDisplay.appendBlueText(null);
	}

	@Test(expected=NullPointerException.class)
	public void testFailToAppendRedText() {
		this.consoleDisplay.appendRedText(null);
	}

	@Test(expected=NullPointerException.class)
	public void testFailToAppendGreenText() {
		this.consoleDisplay.appendGreenText(null);
	}

	@Test(expected=NullPointerException.class)
	public void testFailToAppendYellowText() {
		this.consoleDisplay.appendYellowText(null);
	}

	@Test(expected=NullPointerException.class)
	public void testFailToAppendJokerText() {
		this.consoleDisplay.appendJokerText(null);
	}

	/* ========================================= BASIC DISPLAYS ========================================= */

	@Test(expected=IllegalArgumentException.class)
	public void testFailToDisplaySeparationBarDueToInvalidLength() {
		this.consoleDisplay.displaySeparationBar(-9);
	}
	
	/* ========================================= ALL IN ONE ASSERT ========================================= */
	
	@Test
	public void test() throws IOException {
		//ONE LINE TEXT
		this.consoleDisplay.displayBoldText("test"); 
		assertEquals("[1mtest[0m\r\n",this.outContent.toString());
		this.outContent.reset();
		this.consoleDisplay.displayErrorText("test");
		assertEquals("[1m[0;31mtest[0;37m[0m\r\n",this.outContent.toString());
		this.outContent.reset();
		assertEquals("",this.outContent.toString());
		this.consoleDisplay.displaySuccessText("test");
		assertEquals("[1m[0;32mtest[0;37m[0m\r\n",this.outContent.toString());
		this.outContent.reset();
		
		//HEADER & EMPHASIS
		this.consoleDisplay.displaySeparationText("test"); 
		assertEquals("[37;44m[1mtest[0m[0m\r\n",this.outContent.toString());
		this.outContent.reset();
		this.consoleDisplay.generateRedEmphasis("test"); 
		assertEquals("[1m[0;31mtest[0;37m[0m\r\n",this.outContent.toString());
		this.outContent.reset();
		this.consoleDisplay.generateGreenEmphasis("test"); 
		assertEquals("[1m[0;32mtest[0;37m[0m\r\n",this.outContent.toString());
		this.outContent.reset();
		this.consoleDisplay.generateWhiteOnBlueEmphasis("test"); 
		assertEquals("[37;44m[1mtest[0m[0m\r\n",this.outContent.toString());
		this.outContent.reset();
		this.consoleDisplay.generateJokerEmphasis("test"); 
		assertEquals("[0;35m[1mtest[0m[0;37m\r\n",this.outContent.toString());
		this.outContent.reset();
		
		//APPEND TEXT
		this.consoleDisplay.appendBoldText("test");
		assertEquals("[1mtest[0m",this.outContent.toString());
		this.outContent.reset();
		this.consoleDisplay.appendBlueText("test"); 
		assertEquals("[0;36mtest[0;37m",this.outContent.toString());
		this.outContent.reset();
		this.consoleDisplay.appendRedText("test"); 
		assertEquals("[0;31mtest[0;37m",this.outContent.toString());
		this.outContent.reset();
		this.consoleDisplay.appendGreenText("test"); 
		assertEquals("[0;32mtest[0;37m",this.outContent.toString());
		this.outContent.reset();
		this.consoleDisplay.appendYellowText("test"); 
		assertEquals("[0;33mtest[0;37m",this.outContent.toString());
		this.outContent.reset();
		this.consoleDisplay.appendJokerText("test"); 
		assertEquals("[0;35mtest[0;37m",this.outContent.toString());
		this.outContent.reset();
		
		//BASIC DISPLAYS
		this.consoleDisplay.displaySeparationBar(5); 
		assertEquals("-----\r\n",this.outContent.toString());
		this.outContent.reset();
		this.consoleDisplay.displayBlankLine(); 
		assertEquals("\r\n",this.outContent.toString());
		this.outContent.reset();
		this.consoleDisplay.clearDisplay(); 
		assertEquals("[2J\r\n",this.outContent.toString());
		this.outContent.reset();
	}
}
