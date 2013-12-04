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
	 * M�thode permettant de tirer un nombre d�fini de cartes
	 * @param count Nombre de carte � tirer
	 * @return Une collection de cartes contenant les n premi�res cartes de la pioche
	 */
	public Collection<Card> drawCards(int count) {
		Preconditions.checkArgument(count>0, "[ERROR] Amount of cards drawn must be strictly higher than 0 (Expected : 1+)");
		return this.gameModel.drawCards(count);
	}

	/**
	 * M�thode permettant de tirer une unique carte
	 * @return La premi�re carte de la pioche
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
	 * M�thode permettant d'avoir un apper�u de la derni�re carte jou�e (sans la retirer du talon)
	 * @return La premi�re carte du talon (la derni�re ayant �t� jou�e)
	 */
	public Card showLastCardPlayed() {
		return gameModel.showLastCardPlayed();
	}
	
	/**
	 * M�thode permettant de jouer une carte et de renvoyer l'effet s'�tant �ventuellement d�clench�
	 * @param chosenCard Carte choisie par le joueur
	 * @return Une valeur d'�num�ration correspondant � l'effet qui s'est d�lench� (ou NORMAL, s'il n'y en a pas eu)
	 */
	public GameFlags playCard(Card chosenCard) {
		Preconditions.checkNotNull(chosenCard, "[ERROR] Card to play cannot be null");
		this.gameModel.playCard(chosenCard);
		return triggerItsEffectIfItHasOne(chosenCard);
	}

	/**
	 * M�thode priv�e permettant de r�cup�rer les effets ayant �ventuellement �t� d�clench� lors du jeu de la carte choisie
	 * @param chosenCard Carte choisie par le joueur
	 * @return Une valeur d'�num�ration correspondant � l'effet qui s'est d�lench� (ou NORMAL, s'il n'y en a pas eu)
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
