package utt.fr.rglb.main.java.cards.controller;

import java.util.Collection;

import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.cards.model.CardsModel;
import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.CardSpecial;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.game.model.GameFlag;
import utt.fr.rglb.main.java.view.AbstractView;
import utt.fr.rglb.main.java.view.graphics.GraphicsView;

public class CardsControllerGraphicsOriented extends AbstractCardsController {
	protected static final long serialVersionUID = 1L;
	protected CardsModel cardsModel;
	protected GraphicsView view;

	/* ========================================= CONSTRUCTOR ========================================= */
	/**
	 * Constructeur de gameControlleur
	 * @param view Vue permettant d'afficher les données dans l'interface
	 */
	public CardsControllerGraphicsOriented(AbstractView view) {
		Preconditions.checkNotNull(view,"[ERROR] Impossible to create game controller : provided view is null");
		this.cardsModel = new CardsModel();
		this.view = (GraphicsView)view;
	}

	/* ========================================= CARD DRAW ========================================= */

	@Override
	public Collection<Card> drawCards(int count) {
		Preconditions.checkArgument(count>0, "[ERROR] Amount of cards drawn must be strictly higher than 0 (Expected : 1+)");
		return this.cardsModel.drawCards(count);
	}

	@Override
	public Card drawOneCard() {
		return this.cardsModel.drawOneCard();
	}

	/* ========================================= PLAY CARD ========================================= */

	@Override
	public Card showLastCardPlayed() {
		return cardsModel.showLastCardPlayed();
	}

	@Override
	public Card retrieveImageFromLastCardPlayed() {
		return cardsModel.showLastCardPlayed();
	}

	@Override
	public GameFlag playCard(Card chosenCard) {
		Preconditions.checkNotNull(chosenCard, "[ERROR] Card to play cannot be null");
		this.cardsModel.playCard(chosenCard);
		return triggerItsEffectIfItHasOne(chosenCard);
	}

	/* ========================================= EFFECTS RELATED ========================================= */

	@Override
	protected GameFlag triggerItsEffectIfItHasOne(Card chosenCard) {
		Preconditions.checkNotNull(chosenCard,"[ERROR] Cannot trigger effect from card : provided card is null");
		if(chosenCard.isSpecial()) {
			CardSpecial explicitSpecialCard = (CardSpecial)chosenCard;
			return explicitSpecialCard.triggerEffect();
		} else {
			return GameFlag.NORMAL;
		}
	}
	@Override
	public GameFlag drawFirstCardAndApplyItsEffect() {
		Card firstCard = this.cardsModel.drawStarterCard();
		return applyEffectFromCardIfITHasOne(firstCard);
	}

	@Override
	public GameFlag applyEffectFromAnotherFirstCard() {
		Card c = this.cardsModel.drawOneCard();
		this.cardsModel.playCard(c);
		return applyEffectFromCardIfITHasOne(c);
	}

	@Override
	public GameFlag applyEffectFromCardIfITHasOne(Card firstCard) {
		Preconditions.checkNotNull(firstCard,"[ERROR] Impossible to apply effect from first card : provided card is null");
		return triggerItsEffectIfItHasOne(firstCard);
	}

	/* ========================================= GLOBAL COLOR ========================================= */

	@Override
	public CardsModelBean getReferences() {
		return new CardsModelBean(showLastCardPlayed(), getGlobalColor(), this.view);
	}

	@Override
	public void setGlobalColor(Color chosenColor) {
		Preconditions.checkNotNull(chosenColor,"[ERROR] Impossible to set global color : provided color is null");
		Preconditions.checkArgument(!chosenColor.equals(Color.JOKER),"[ERROR] Impossible to set global color : JOKER is not a valid global color");
		String colorName = chosenColor.toString();
		this.view.displayTextBasedOnItsColor("Color is now ",chosenColor,colorName);                
		this.cardsModel.setGlobalColor(chosenColor);
	}

	@Override
	public Color getGlobalColor() {
		return this.cardsModel.getGlobalColor();
	}

	@Override
	public void resetCards() {
		this.cardsModel.resetCards();
	}
}
