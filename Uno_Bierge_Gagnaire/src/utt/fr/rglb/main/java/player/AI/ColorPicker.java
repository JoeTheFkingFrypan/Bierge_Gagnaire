package utt.fr.rglb.main.java.player.AI;

import com.google.common.base.Preconditions;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;

/**
 * Classe permettant � une IA quelle est la meilleure couleur � choisir lors du jeu d'un JOKER
 */
public class ColorPicker {
	private int amountOfRedCards;
	private int amountOfBlueCards;
	private int amountOfGreenCards;
	private int amountOfYellowCards;

	/* ========================================= CONSTRUCTEUR ========================================= */
	
	/**
	 * Constructeur de ColorPicker, qui va compter toutes les cartes color�es (par couleur) contenues dans la collection fournie
	 */
	public ColorPicker(Collection<Card> cardCollection) {
		Preconditions.checkNotNull(cardCollection,"[ERROR] Impossible to create a ColorPicker : provided card collection is null");
		this.amountOfRedCards = 0;
		this.amountOfBlueCards = 0;
		this.amountOfGreenCards = 0;
		this.amountOfYellowCards = 0;
		countCards(cardCollection);
	}

	/**
	 * M�thode permettant de compter les cartes par couleur � partir d'une collection donn�e
	 * @param cardCollection Collection de cartes
	 */
	private void countCards(Collection<Card> cardCollection) {
		Preconditions.checkNotNull(cardCollection,"[ERROR] Impossible to count cards : provided card collection is null");
		for(Card currentCard : cardCollection) {
			if(currentCard.isRed()) {
				amountOfRedCards++;
			} else if(currentCard.isBlue()) {
				amountOfBlueCards++;
			} else if(currentCard.isGreen()) {
				amountOfGreenCards++;
			} else if(currentCard.isYellow()) {
				amountOfYellowCards++;
			}
		}
	}
	
	/* ========================================= LOGIC ========================================= */
	
	/**
	 * M�thode permettant de trouver quelle est la couleur � choisir (celle la plus avantageuse)
	 * @return La meilleure couleur possible
	 */
	public Color findBestSuitableColor() {
		if(redCardMajority()) {
			return Color.RED;
		} else if(blueCardMajority()) {
			return Color.BLUE;
		} else if(greenCardMajority()) {
			return Color.GREEN;
		} else {
			return Color.YELLOW;
		}
	}
	
	/**
	 * M�thode priv�e permettant de d�terminer si les cartes de couleur rouge sont majoritaires
	 * @return <code>TRUE</code> si les cartes rouges sont effectivement majoritaires, <code>FALSE</code> sinon
	 */
	private boolean redCardMajority() {
		return this.amountOfRedCards >= this.amountOfBlueCards && this.amountOfRedCards >= this.amountOfGreenCards && this.amountOfRedCards >= this.amountOfYellowCards;
	}
	
	/**
	 * M�thode priv�e permettant de d�terminer si les cartes de couleur bleue sont majoritaires
	 * @return <code>TRUE</code> si les cartes bleues sont effectivement majoritaires, <code>FALSE</code> sinon
	 * [NOTE]: Cette m�thode �tant appel�e apr�s <code>redCardMajority</code>, il n'est pas utile de comparer la quantit� de cartes bleues avec celles rouges (on sait d�j� que les cartes rouges ne sont pas majoritaires)
	 */
	private boolean blueCardMajority() {
		return this.amountOfBlueCards >= this.amountOfGreenCards && this.amountOfBlueCards >= this.amountOfYellowCards;
	}
	
	/**
	 * M�thode priv�e permettant de d�terminer si les cartes de couleur verte sont majoritaires
	 * @return <code>TRUE</code> si les cartes vertes sont effectivement majoritaires, <code>FALSE</code> sinon
	 * [NOTE]: Cette m�thode �tant appel�e apr�s <code>blueCardMajority</code>, il n'est pas utile de comparer la quantit� de cartes vertes avec celles rouges et bleues (on sait d�j� que ni les cartes bleues, ni celles rouges ne sont pas majoritaires)
	 */
	private boolean greenCardMajority() {
		return this.amountOfGreenCards >= this.amountOfYellowCards;
	}
}
