package main.java.cards.controller;

import java.util.Collection;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Card;
import main.java.cards.model.basics.CardSpecial;
import main.java.cards.model.basics.Color;
import main.java.cards.model.GameModel;
import main.java.cards.model.GameModelBean;
import main.java.console.view.View;
import main.java.gameContext.model.GameFlags;

public class GameController {
	private GameModel gameModel;
	private View consoleView;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	public GameController(View consoleView) {
		Preconditions.checkNotNull(consoleView,"[ERROR] Impossible to create game controller : provided view is null");
		this.gameModel = new GameModel();
		this.consoleView = consoleView;
	}
	
	/* ========================================= CARD DRAW ========================================= */
	
	/**
	 * Méthode permettant de tirer un nombre défini de cartes
	 * @param count Nombre de carte à tirer
	 * @return Une collection de cartes contenant les n premières cartes de la pioche
	 */
	public Collection<Card> drawCards(int count) {
		Preconditions.checkArgument(count>0, "[ERROR] Amount of cards drawn must be strictly higher than 0 (Expected : 1+)");
		return this.gameModel.drawCards(count);
	}

	/**
	 * Méthode permettant de tirer une unique carte
	 * @return La première carte de la pioche
	 */
	public Card drawOneCard() {
		return this.gameModel.drawOneCard();
	}
	
	public GameFlags applyEffectFromFirstCard() {
		Card firstCard = showLastCardPlayed();
		return applyEffectFromCardIfITHasOne(firstCard);
	}
	
	public GameFlags applyEffectFromAnotherFirstCard() {
		Card c = this.gameModel.drawOneCard();
		this.gameModel.playCard(c);
		return applyEffectFromCardIfITHasOne(c);
	}
	
	/* ========================================= PLAY CARD ========================================= */
	
	public GameFlags applyEffectFromCardIfITHasOne(Card firstCard) {
		return triggerItsEffectIfItHasOne(firstCard);
	}
	
	/**
	 * Méthode permettant d'avoir un apperçu de la dernière carte jouée (sans la retirer du talon)
	 * @return La première carte du talon (la dernière ayant été jouée)
	 */
	public Card showLastCardPlayed() {
		return gameModel.showLastCardPlayed();
	}
	
	/**
	 * Méthode permettant de jouer une carte et de renvoyer l'effet s'étant éventuellement déclenché
	 * @param chosenCard Carte choisie par le joueur
	 * @return Une valeur d'énumération correspondant à l'effet qui s'est délenché (ou NORMAL, s'il n'y en a pas eu)
	 */
	public GameFlags playCard(Card chosenCard) {
		Preconditions.checkNotNull(chosenCard, "[ERROR] Card to play cannot be null");
		this.gameModel.playCard(chosenCard);
		return triggerItsEffectIfItHasOne(chosenCard);
	}

	/**
	 * Méthode privée permettant de récupérer les effets ayant éventuellement été déclenché lors du jeu de la carte choisie
	 * @param chosenCard Carte choisie par le joueur
	 * @return Une valeur d'énumération correspondant à l'effet qui s'est délenché (ou NORMAL, s'il n'y en a pas eu)
	 */
	private GameFlags triggerItsEffectIfItHasOne(Card chosenCard) {
		if(chosenCard.isSpecial()) {
			CardSpecial explicitSpecialCard = (CardSpecial)chosenCard;
			return explicitSpecialCard.declencherEffet();
		} else {
			return GameFlags.NORMAL;
		}
	}

	public void setGlobalColor(Color chosenColor) {
		this.consoleView.appendJokerText("Color is now ");
		appendCorrectColorText(chosenColor);
		this.gameModel.setGlobalColor(chosenColor);
		this.consoleView.insertBlankLine();
	}
	
	private void appendCorrectColorText(Color chosenColor) {
		if(chosenColor.equals(Color.BLUE)) {
			this.consoleView.appendBoldBlueText(chosenColor.toString());
		} else if(chosenColor.equals(Color.RED)) {
			this.consoleView.appendBoldRedText(chosenColor.toString());
		} else if(chosenColor.equals(Color.GREEN)) {
			this.consoleView.appendBoldGreenText(chosenColor.toString());
		} else if(chosenColor.equals(Color.YELLOW)) {
			this.consoleView.appendBoldYellowText(chosenColor.toString());
		}
	}

	public Color getGlobalColor() {
		return this.gameModel.getGlobalColor();
	}

	public GameModelBean getRequiredGameInfo() {
		return new GameModelBean(showLastCardPlayed(), getGlobalColor(), this.consoleView);
	}
}
