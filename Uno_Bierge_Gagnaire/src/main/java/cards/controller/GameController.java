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
	 * Méthode permettant de tirer un nombre défini de cartes
	 * @param count Nombre de carte à tirer
	 * @return Une collection de cartes contenant les n premières cartes de la pioche
	 */
	public Collection<Carte> drawCards(int count) {
		Preconditions.checkArgument(count>0, "[ERROR] Amount of cards drawn must be strictly higher than 0 (Expected : 1+)");
		return this.gameModel.drawCards(count);
	}

	/**
	 * Méthode permettant de tirer une unique carte
	 * @return La première carte de la pioche
	 */
	public Carte drawOneCard() {
		return this.gameModel.drawOneCard();
	}
	
	/* ========================================= PLAY CARD ========================================= */
	
	/**
	 * Méthode permettant d'avoir un apperçu de la dernière carte jouée (sans la retirer du talon)
	 * @return La première carte du talon (la dernière ayant été jouée)
	 */
	public Carte showLastCardPlayed() {
		return gameModel.showLastCardPlayed();
	}
	
	/**
	 * Méthode permettant de jouer une carte et de renvoyer l'effet s'étant éventuellement déclenché
	 * @param chosenCard Carte choisie par le joueur
	 * @return Une valeur d'énumération correspondant à l'effet qui s'est délenché (ou NORMAL, s'il n'y en a pas eu)
	 */
	public GameFlags playCard(Carte chosenCard) {
		Preconditions.checkNotNull(chosenCard, "[ERROR] Card to play cannot be null");
		this.gameModel.playCard(chosenCard);
		return triggerItsEffectIfItHasOne(chosenCard);
	}

	/**
	 * Méthode privée permettant de récupérer les effets ayant éventuellement été déclenché lors du jeu de la carte choisie
	 * @param chosenCard Carte choisie par le joueur
	 * @return Une valeur d'énumération correspondant à l'effet qui s'est délenché (ou NORMAL, s'il n'y en a pas eu)
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
