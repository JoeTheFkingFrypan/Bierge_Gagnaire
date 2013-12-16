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
 * Classe dont le r�le est de g�rer tout ce qui est associ� aux cartes (compatibilit�, pioche, jeu, etc)
 */
public class CardsController implements Serializable {
	private static final long serialVersionUID = 1L;
	private CardsModel cardsModel;
	private View consoleView;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	/**
	 * Constructeur de gameControlleur
	 * @param consoleView Vue permettant d'afficher les donn�es dans l'interface
	 */
	public CardsController(View consoleView) {
		Preconditions.checkNotNull(consoleView,"[ERROR] Impossible to create game controller : provided view is null");
		this.cardsModel = new CardsModel();
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
		return this.cardsModel.drawCards(count);
	}

	/**
	 * M�thode permettant de tirer une unique carte
	 * @return La premi�re carte de la pioche
	 */
	public Card drawOneCard() {
		return this.cardsModel.drawOneCard();
	}
	
	/* ========================================= PLAY CARD ========================================= */

	/**
	 * M�thode permettant d'avoir un apper�u de la derni�re carte jou�e (sans la retirer du talon)
	 * @return La premi�re carte du talon (la derni�re ayant �t� jou�e)
	 */
	public Card showLastCardPlayed() {
		return cardsModel.showLastCardPlayed();
	}
	
	/**
	 * M�thode permettant de jouer une carte et de renvoyer l'effet s'�tant �ventuellement d�clench�
	 * @param chosenCard Carte choisie par le joueur
	 * @return Une valeur d'�num�ration correspondant � l'effet qui s'est d�lench� (ou NORMAL, s'il n'y en a pas eu)
	 */
	public GameFlag playCard(Card chosenCard) {
		Preconditions.checkNotNull(chosenCard, "[ERROR] Card to play cannot be null");
		this.cardsModel.playCard(chosenCard);
		return triggerItsEffectIfItHasOne(chosenCard);
	}
	
	/* ========================================= EFFECTS RELATED ========================================= */

	/**
	 * M�thode priv�e permettant de r�cup�rer les effets ayant �ventuellement �t� d�clench� lors du jeu de la carte choisie
	 * @param chosenCard Carte choisie par le joueur
	 * @return Une valeur d'�num�ration correspondant � l'effet qui s'est d�lench� (ou NORMAL, s'il n'y en a pas eu)
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
	 * M�thode permettant de d�clencher l'effet de la 1�re carte du talon
	 * @return L'effet associ� � la 1�re carte (ou NORMAL, si la carte n'a pas d'effet)
	 */
	public GameFlag drawFirstCardAndApplyItsEffect() {
		Card firstCard = this.cardsModel.drawStarterCard();
		return applyEffectFromCardIfITHasOne(firstCard);
	}
	
	/**
	 * M�thode permettant de d�clencher l'effet de la 1�re carte du talon (dans le cas o� un +4 a �t� tir� d�s le d�part)
	 * @return L'effet associ� � la 1�re carte (ou NORMAL, si la carte n'a pas d'effet)
	 */
	public GameFlag applyEffectFromAnotherFirstCard() {
		Card c = this.cardsModel.drawOneCard();
		this.cardsModel.playCard(c);
		return applyEffectFromCardIfITHasOne(c);
	}
	
	/**
	 * M�thode permettant d'appliquer un effet � partir d'une carte donn�e
	 * @param firstCard Carte dont l'effet doit �tre appliqu�
	 * @return L'effet associ�
	 */
	public GameFlag applyEffectFromCardIfITHasOne(Card firstCard) {
		Preconditions.checkNotNull(firstCard,"[ERROR] Impossible to apply effect from first card : provided card is null");
		return triggerItsEffectIfItHasOne(firstCard);
	}
	
	/* ========================================= GLOBAL COLOR ========================================= */
	
	/**
	 * M�thode permettant de r�cup�rer les informations n�cessaires � la v�rification de la compatibilit� d'une carte
	 * @return Un bean comportant la carte derni�rement jou�e (carte de r�f�rence) et la couleur globale
	 */
	public GameModelBean getRequiredReferences() {
		return new GameModelBean(showLastCardPlayed(), getGlobalColor(), this.consoleView);
	}
	
	/**
	 * M�thode permettant de d�finir la couleur globale (Couleur choisie par l'utilisateur apr�s utilisation d'un joker)
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
	 * M�thode priv�e permettant de r�cup�rer la couleur globale (Couleur choisie par l'utilisateur apr�s utilisation d'un joker)
	 * @return Couleur choisie par l'utilisateur
	 */
	public Color getGlobalColor() {
		return this.cardsModel.getGlobalColor();
	}
	
	/**
	 * M�thode permettant de r�-initialiser les cartes (talon et pioche)
	 */
	public void resetCards() {
		this.cardsModel.resetCards();
	}
}