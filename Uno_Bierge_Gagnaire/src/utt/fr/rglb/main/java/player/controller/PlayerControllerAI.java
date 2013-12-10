package utt.fr.rglb.main.java.player.controller;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.cards.model.GameModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.console.model.InputReader;
import utt.fr.rglb.main.java.console.view.View;
import utt.fr.rglb.main.java.player.AI.CardPickerStrategy;

public class PlayerControllerAI extends PlayerController {
	private CardPickerStrategy cardPickerStrategy;
	
	public PlayerControllerAI(String name,View consoleView, CardPickerStrategy cardPickerStrategy) {
		super(name,consoleView);
		this.cardPickerStrategy = cardPickerStrategy;
	}
	
	@Override
	public Card startTurn(InputReader inputReader, GameModelBean gameModelBean) {
		Preconditions.checkNotNull(inputReader,"[ERROR] Impossible to start turn, inputReader is null");
		Preconditions.checkNotNull(gameModelBean,"[ERROR] Impossible to start turn, gameModelbean is null");
		String alias = this.player.getAlias();
		Collection<Card> cardCollection = this.getCardsInHand();
		Integer cardsLeft = cardCollection.size();
		List<Integer> playableIndexes = gameModelBean.findPlayableCardsFrom(cardCollection);
		int bestIndex = cardPickerStrategy.chooseCardFrom(playableIndexes,cardCollection);
		decideIfAnnouncingUnoIsNecessary(alias);
		Card suitableCard = this.player.playCard(bestIndex);
		this.consoleView.displayJokerEmphasisUsingPlaceholders("[IA] ", alias, " played : ", suitableCard);
		this.consoleView.displayJokerEmphasisUsingPlaceholders("He has ", cardsLeft.toString(), " cards remaining");
		return suitableCard;
	}
	
	/**
	 * Méthode permettant à l'IA de déterminer si l'annonce de UNO doit être faite 
	 * @param alias 
	 */
	public void decideIfAnnouncingUnoIsNecessary(String alias) {
		if(this.player.getNumberOfCardsInHand() == 2) {
			this.player.setUnoAnnoucement();
		}
	}
	
	@Override
	public Color hasToChooseColor(InputReader inputReader) {
		Collection<Card> cardCollection = this.getCardsInHand();
		return this.cardPickerStrategy.chooseBestColor(cardCollection);
	}
}
