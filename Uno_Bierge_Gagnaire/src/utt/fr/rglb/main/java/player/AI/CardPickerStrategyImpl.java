package utt.fr.rglb.main.java.player.AI;

import com.google.common.base.Preconditions;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;

public abstract class CardPickerStrategyImpl implements CardPickerStrategy {
	
	/**
	 * M�thode permettant d'obtenir l'index de la carte la plus appropri�e, bas� sur le type de strat�gie
	 * @param playableIndexes
	 * @param cardCollection
	 * @return
	 */
	protected abstract int findBestCardToPlay(Collection<Integer> playableIndexes, Collection<Card> cardCollection);

	@Override
	public int chooseCardFrom(Collection<Integer> playableIndexes, Collection<Card> cardCollection) {
		Preconditions.checkNotNull(playableIndexes,"[ERROR] Cannot chose card : indexes collection is null");
		Preconditions.checkNotNull(cardCollection,"[ERROR] Cannot chose card : card collection is null");
		Preconditions.checkArgument(playableIndexes.size() > 0,"[ERROR] Cannot chose card : indexes collection is empty");
		Preconditions.checkArgument(cardCollection.size() > 0,"[ERROR] Cannot chose card : card collection is empty");		
		return findBestCardToPlay(playableIndexes,cardCollection);
	}
	
	 @Override
	 public Color chooseBestColor(Collection<Card> cardCollection) {
		 ColorPicker colorPicker = new ColorPicker(cardCollection);
		 return colorPicker.findBestSuitableColor();
	 }
}
