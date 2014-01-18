package utt.fr.rglb.main.java.player.AI;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;

/**
 * Classe abstraite correspondant à l'implémentation de l'interface CardPickerStrategy
 */
public abstract class CardPickerStrategyImpl implements CardPickerStrategy, Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Méthode permettant d'obtenir l'index de la carte la plus appropriée, basé sur le type de stratégie
	 * @param playableIndexes Collection de numéro corrspondant aux indices des cartes jouables
	 * @param cardCollection Collection de cartes en main
	 * @return int correspondant à la carte choisie
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
		 Preconditions.checkNotNull(cardCollection,"[ERROR] Cannot chose best color : provided card collection is null");
		 ColorPicker colorPicker = new ColorPicker(cardCollection);
		 return colorPicker.findBestSuitableColor();
	 }
}
