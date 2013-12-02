package main.java.cards.controller;

import java.util.Collection;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Carte;
import main.java.cards.model.basics.CarteSpeciale;
import main.java.cards.model.GameModel;
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
	public Collection<Carte> drawCards(int count) {
		Preconditions.checkArgument(count>0, "[ERROR] Amount of cards drawn must be strictly higher than 0 (Expected : 1+)");
		return this.gameModel.drawCards(count);
	}

	/**
	 * M�thode permettant de tirer une unique carte
	 * @return La premi�re carte de la pioche
	 */
	public Carte drawOneCard() {
		return this.gameModel.drawOneCard();
	}
	
	/* ========================================= PLAY CARD ========================================= */
	
	/**
	 * M�thode permettant d'avoir un apper�u de la derni�re carte jou�e (sans la retirer du talon)
	 * @return La premi�re carte du talon (la derni�re ayant �t� jou�e)
	 */
	public Carte showLastCardPlayed() {
		return gameModel.showLastCardPlayed();
	}
	
	/**
	 * M�thode permettant de jouer une carte et de renvoyer l'effet s'�tant �ventuellement d�clench�
	 * @param chosenCard Carte choisie par le joueur
	 * @return Une valeur d'�num�ration correspondant � l'effet qui s'est d�lench� (ou NORMAL, s'il n'y en a pas eu)
	 */
	public GameFlags playCard(Carte chosenCard) {
		Preconditions.checkNotNull(chosenCard, "[ERROR] Card to play cannot be null");
		this.gameModel.playCard(chosenCard);
		return triggerItsEffectIfItHasOne(chosenCard);
	}

	/**
	 * M�thode priv�e permettant de r�cup�rer les effets ayant �ventuellement �t� d�clench� lors du jeu de la carte choisie
	 * @param chosenCard Carte choisie par le joueur
	 * @return Une valeur d'�num�ration correspondant � l'effet qui s'est d�lench� (ou NORMAL, s'il n'y en a pas eu)
	 */
	private GameFlags triggerItsEffectIfItHasOne(Carte chosenCard) {
		if(chosenCard.isSpecial()) {
			CarteSpeciale explicitSpecialCard = (CarteSpeciale)chosenCard;
			return explicitSpecialCard.declencherEffet();
		} else {
			return GameFlags.NORMAL;
		}
	}
	
	public void test() {
		this.consoleView.clearDisplay();
	}
}
