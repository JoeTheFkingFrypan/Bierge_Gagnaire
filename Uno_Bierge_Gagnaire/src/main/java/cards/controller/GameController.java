package main.java.cards.controller;

import java.util.Collection;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Card;
import main.java.cards.model.basics.CardSpecial;
import main.java.cards.model.basics.Color;
import main.java.cards.model.GameModel;
import main.java.cards.model.GameModelBean;
import main.java.console.view.View;
import main.java.gameContext.model.GameFlag;

/**
 * Classe dont le rôle est de gérer tout ce qui est associé aux cartes (compatibilité, pioche, jeu, etc)
 */
public class GameController {
	private GameModel gameModel;
	private View consoleView;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	/**
	 * Constructeur de gameControlleur
	 * @param consoleView Vue permettant d'afficher les données dans l'interface
	 */
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
	
	/* ========================================= PLAY CARD ========================================= */

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
	public GameFlag playCard(Card chosenCard) {
		Preconditions.checkNotNull(chosenCard, "[ERROR] Card to play cannot be null");
		this.gameModel.playCard(chosenCard);
		return triggerItsEffectIfItHasOne(chosenCard);
	}
	
	/* ========================================= EFFECTS RELATED ========================================= */

	/**
	 * Méthode privée permettant de récupérer les effets ayant éventuellement été déclenché lors du jeu de la carte choisie
	 * @param chosenCard Carte choisie par le joueur
	 * @return Une valeur d'énumération correspondant à l'effet qui s'est délenché (ou NORMAL, s'il n'y en a pas eu)
	 */
	private GameFlag triggerItsEffectIfItHasOne(Card chosenCard) {
		if(chosenCard.isSpecial()) {
			CardSpecial explicitSpecialCard = (CardSpecial)chosenCard;
			return explicitSpecialCard.declencherEffet();
		} else {
			return GameFlag.NORMAL;
		}
	}

	/**
	 * Méthode permettant de déclencher l'effet de la 1ère carte du talon
	 * @return L'effet associé à la 1ère carte (ou NORMAL, si la carte n'a pas d'effet)
	 */
	public GameFlag applyEffectFromFirstCard() {
		Card firstCard = showLastCardPlayed();
		return applyEffectFromCardIfITHasOne(firstCard);
	}
	
	/**
	 * Méthode permettant de déclencher l'effet de la 1ère carte du talon (dans le cas où un +4 a été tiré dès le départ)
	 * @return L'effet associé à la 1ère carte (ou NORMAL, si la carte n'a pas d'effet)
	 */
	public GameFlag applyEffectFromAnotherFirstCard() {
		Card c = this.gameModel.drawOneCard();
		this.gameModel.playCard(c);
		return applyEffectFromCardIfITHasOne(c);
	}
	
	/**
	 * Méthode permettant d'appliquer un effet à partir d'une carte donnée
	 * @param firstCard Carte dont l'effet doit être appliqué
	 * @return L'effet associé
	 */
	public GameFlag applyEffectFromCardIfITHasOne(Card firstCard) {
		Preconditions.checkNotNull(firstCard,"[ERROR] Impossible to apply effect from first card : provided card is null");
		return triggerItsEffectIfItHasOne(firstCard);
	}
	
	/* ========================================= GLOBAL COLOR ========================================= */
	
	/**
	 * Méthode permettant de récupérer les informations nécessaires à la vérification de la compatibilité d'une carte
	 * @return Un bean comportant la carte dernièrement jouée (carte de référence) et la couleur globale
	 */
	public GameModelBean getRequiredGameInfo() {
		return new GameModelBean(showLastCardPlayed(), getGlobalColor(), this.consoleView);
	}
	
	/**
	 * Méthode permettant de définir la couleur globale (Couleur choisie par l'utilisateur après utilisation d'un joker)
	 * @param chosenColor Couleur choisie par l'utilisateur
	 */
	public void setGlobalColor(Color chosenColor) {
		Preconditions.checkNotNull(chosenColor,"[ERROR] Impossible to set global color : provided color is null");
		Preconditions.checkArgument(!chosenColor.equals(Color.JOKER),"[ERROR] Impossible to set global color : JOKER is not a valid global color");
		this.consoleView.appendJokerText("Color is now ");
		appendCorrectColorText(chosenColor);
		this.gameModel.setGlobalColor(chosenColor);
		this.consoleView.insertBlankLine();
	}
	
	/**
	 * Méthode privée permettant de récupérer la couleur globale (Couleur choisie par l'utilisateur après utilisation d'un joker)
	 * @return Couleur choisie par l'utilisateur
	 */
	public Color getGlobalColor() {
		return this.gameModel.getGlobalColor();
	}
	
	/* ========================================= DISPLAY ========================================= */
	
	/**
	 * Méthode privée permttant d'afficher du texte le nom de la couleur choisie dans la console (avec leur couleur correspondante)
	 * @param chosenColor Couleur choisie par l'utilisateur
	 */
	private void appendCorrectColorText(Color chosenColor) {
		Preconditions.checkNotNull(chosenColor,"[ERROR] Impossible to set global color : provided color is null");
		Preconditions.checkArgument(!chosenColor.equals(Color.JOKER),"[ERROR] Impossible to set global color : JOKER is not a valid global color");
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

	public void resetCards() {
		this.gameModel.resetCards();
	}
}
