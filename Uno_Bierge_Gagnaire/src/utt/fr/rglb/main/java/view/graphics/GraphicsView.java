package utt.fr.rglb.main.java.view.graphics;

import java.util.Collection;

import javafx.scene.Scene;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.view.AbstractView;

/**
 * Classe correspondant à la vue graphique
 */
public class GraphicsView extends AbstractView {
	private static final long serialVersionUID = 1L;
	private JavaFxApplication javaFxApplication;

	/* ========================================= CONSTRUCTOR ========================================= */
	
	public GraphicsView() {
		this.javaFxApplication = new JavaFxApplication();
	}
	
	/* ========================================= SCREEN MANAGEMENT ========================================= */
	
	/**
	 * Méthode permettant de retourner à l'écran d'acceuil
	 */
	public void displayWelcomeScreen() {
		if(this.javaFxApplication.hasNotBeenStartedYet()) {
			javaFxApplication.runGraphicsView(null);
		} else {
			javaFxApplication.goBackToWelcomeView();
		}
	}

	/**
	 * Méthode permettant de continuer vers l'affichage des scores par équipe
	 */
	public void displayTeamScreen(Scene scene) {
		javaFxApplication.continueToTeamDisplay(scene);
	}

	/* ========================================= UNFINISHED METHODS ========================================= */
	
	@Override
	public void displayTitle(String title) {} //do nothing

	@Override
	public void displaySeparationText(String text) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayCard(String string, Card card) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayCardCollection(String string, Collection<Card> cards) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayOneCard(Card cardToDisplay) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayOneCard(Card cardToDisplay, int index) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayOneLineOfYellowText(String part01, String placeholder01, String part02, String placeholder02, String part03) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayOneLineOfGreenText(String string) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayGreenEmphasisUsingPlaceholders(String part01, String placeholder01, String part02) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayGreenEmphasisUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayOneLineOfJokerText(String string) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayTwoLinesOfJokerText(String string, String string2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayJokerEmphasisUsingPlaceholders(String part01, String placeholder01, String part02) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayJokerEmphasisUsingPlaceholders(String part01, Card card) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayJokerEmphasisUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayJokerEmphasisUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03, String placeholder03, String part04) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayOneLineOfBoldText(String string) {
		// TODO Auto-generated method stub
	}

	@Override
	public void StartOneLineOfBoldText(String part01, String placeholder01, String part02) {
		// TODO Auto-generated method stub
	}

	@Override
	public void AppendOneLineOfBoldText(String string) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayOneLineOfRedText(String string) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayErrorMessage(String string, String string2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayErrorMessageUsingPlaceholders(String part01, String placeholder01, String part02) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayErrorMessageUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayErrorMessageUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayErrorMessageUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03, String part04) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayErrorMessageUsingPlaceholders(String part01, String placeholder01, String part02, String placeholder02, String part03, String placeholder03, String part04, String part05) { 
		// TODO Auto-generated method stub
	}

	@Override
	public void displayTextBasedOnItsColor(String part01, Color globalColor, String redName, String BlueName, String GreenName, String YellowName) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayTextBasedOnItsColor(String string, Color chosenColor, String colorName) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayAvailableColors() {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayChoice(String question, String choice1, String choice2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void displayChoice(String question, String choice1, String choice2, String choice3) {
		// TODO Auto-generated method stub
	}

	@Override
	public void clearDisplay() {
		// TODO Auto-generated method stub
	}
}
