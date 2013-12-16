package utt.fr.rglb.main.java.cards.controller;

import com.google.common.base.Preconditions;

import java.io.Serializable;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.CardsModel;
import utt.fr.rglb.main.java.cards.model.GameModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.CardSpecial;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.console.view.View;
import utt.fr.rglb.main.java.turns.model.GameFlag;

/**
 * Classe dont le rôle est de gérer tout ce qui est associé aux cartes (compatibilité, pioche, jeu, etc)
 */
public class CardsController implements Serializable {
	private static final long serialVersionUID = 1L;
	private CardsModel cardsModel;
	private View consoleView;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	/**
	 * Constructeur de gameControlleur
	 * @param consoleView Vue permettant d'afficher les données dans l'interface
	 */
	public CardsController(View consoleView) {
		Preconditions.checkNotNull(consoleView,"[ERROR] Impossible to create game controller : provided view is null");
		this.cardsModel = new CardsModel();
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
		return this.cardsModel.drawCards(count);
	}

	/**
	 * Méthode permettant de tirer une unique carte
	 * @return La première carte de la pioche
	 */
	public Card drawOneCard() {
		return this.cardsModel.drawOneCard();
	}
	
	/* ========================================= PLAY CARD ========================================= */

	/**
	 * Méthode permettant d'avoir un apperçu de la dernière carte jouée (sans la retirer du talon)
	 * @return La première carte du talon (la dernière ayant été jouée)
	 */
	public Card showLastCardPlayed() {
		return cardsModel.showLastCardPlayed();
	}
	
	/**
	 * Méthode permettant de jouer une carte et de renvoyer l'effet s'étant éventuellement déclenché
	 * @param chosenCard Carte choisie par le joueur
	 * @return Une valeur d'énumération correspondant à l'effet qui s'est délenché (ou NORMAL, s'il n'y en a pas eu)
	 */
	public GameFlag playCard(Card chosenCard) {
		Preconditions.checkNotNull(chosenCard, "[ERROR] Card to play cannot be null");
		this.cardsModel.playCard(chosenCard);
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
			return explicitSpecialCard.triggerEffect();
		} else {
			return GameFlag.NORMAL;
		}
	}

	/**
	 * Méthode permettant de déclencher l'effet de la 1ère carte du talon
	 * @return L'effet associé à la 1ère carte (ou NORMAL, si la carte n'a pas d'effet)
	 */
	public GameFlag drawFirstCardAndApplyItsEffect() {
		Card firstCard = this.cardsModel.drawStarterCard();
		return applyEffectFromCardIfITHasOne(firstCard);
	}
	
	/**
	 * Méthode permettant de déclencher l'effet de la 1ère carte du talon (dans le cas où un +4 a été tiré dès le départ)
	 * @return L'effet associé à la 1ère carte (ou NORMAL, si la carte n'a pas d'effet)
	 */
	public GameFlag applyEffectFromAnotherFirstCard() {
		Card c = this.cardsModel.drawOneCard();
		this.cardsModel.playCard(c);
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
	public GameModelBean getRequiredReferences() {
		return new GameModelBean(showLastCardPlayed(), getGlobalColor(), this.consoleView);
	}
	
	/**
	 * Méthode permettant de définir la couleur globale (Couleur choisie par l'utilisateur après utilisation d'un joker)
	 * @param chosenColor Couleur choisie par l'utilisateur
	 */
	public void setGlobalColor(Color chosenColor) {
		Preconditions.checkNotNull(chosenColor,"[ERROR] Impossible to set global color : provided color is null");
		Preconditions.checkArgument(!chosenColor.equals(Color.JOKER),"[ERROR] Impossible to set global color : JOKER is not a valid global color");
		String colorName = chosenColor.toString();
		this.consoleView.displayTextBasedOnItsColor("Color is now ",chosenColor,colorName,colorName,colorName,colorName);		
		this.cardsModel.setGlobalColor(chosenColor);
	}
	
	/**
	 * Méthode privée permettant de récupérer la couleur globale (Couleur choisie par l'utilisateur après utilisation d'un joker)
	 * @return Couleur choisie par l'utilisateur
	 */
	public Color getGlobalColor() {
		return this.cardsModel.getGlobalColor();
	}
	
	/**
	 * Méthode permettant de ré-initialiser les cartes (talon et pioche)
	 */
	public void resetCards() {
		this.cardsModel.resetCards();
	}
}