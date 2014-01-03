package utt.fr.rglb.main.java.player.controller;

import java.util.Collection;

import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.console.model.InputReader;
import utt.fr.rglb.main.java.player.model.PlayerModel;
import utt.fr.rglb.main.java.view.AbstractView;

//FIXME
public class PlayerControllerGraphicsOriented extends AbstractPlayerController {
	private static final long serialVersionUID = 1L;

	public PlayerControllerGraphicsOriented(String name, AbstractView consoleView) {
		Preconditions.checkNotNull(name,"[ERROR] name cannot be null");
		Preconditions.checkNotNull(consoleView,"[ERROR] view cannot be null");
		this.player = new PlayerModel(name);
		this.consoleView = consoleView;
	}
	
	@Override
	public void pickUpCards(Collection<Card> cards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pickUpOneCard(Card card) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void isForcedToPickUpCards(Collection<Card> cards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void isForcedToPickUpCardsLegitCase(Collection<Card> cards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void isForcedToPickUpCardsBluffCase(Collection<Card> cards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Card playCard(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasAtLeastOnePlayableCard(CardsModelBean gameModelBean) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected Collection<Card> getCardsInHand() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card startTurn(InputReader inputReader, CardsModelBean gameModelBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean findIfBluffIsNeeded(InputReader inputReader,
			CardsModelBean gameModelBean, Card choosenCard,
			boolean wantsToPlayAnotherCard) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unableToPlayThisTurn(CardsModelBean gameModelbean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color hasToChooseColor(boolean isRelatedToPlus4,
			InputReader inputReader) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean askIfHeWantsToCheckIfItsLegit(InputReader inputReader) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void chillForTwoSec(String stringToDisplay) {
		// TODO Auto-generated method stub
		
	}

}
