package utt.fr.rglb.main.java.cards.model.stock;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.CardSpecial;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.cards.model.basics.Effect;
import utt.fr.rglb.main.java.cards.model.basics.EffectPlus2;
import utt.fr.rglb.main.java.cards.model.basics.EffectPlus4;
import utt.fr.rglb.main.java.cards.model.basics.EffectReverse;
import utt.fr.rglb.main.java.cards.model.basics.EffectSkip;
import utt.fr.rglb.main.java.cards.model.basics.EffetJoker;


/**
 * Classe � qui a �t� d�l�gu� le r�le de la cr�ation des cartes (et de leur m�lange)
 */
class CardGenerator implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* ========================================= COLLECTIONS CREATION ========================================= */

	/**
	 * M�thode permettant d'initialiser la pioche en g�n�rant les 108 cartes de d�part dans un ordre al�atoire
	 * @return Une Queue contenant les 108 cartes
	 */
	public Queue<Card> generateCards() {
		Queue<Card> queuedCards = new LinkedList<Card>();
		List<Card> cards = generateShuffledCards();
		for(Card c : cards) {
			queuedCards.add(c);
		}
		return queuedCards;
	}
	
	/**
	 * M�thode priv�e permettant de g�n�rer les 108 cartes dans un ordre al�atoire
	 * @return Une List de carte m�lang�es
	 */
	private List<Card> generateShuffledCards() {
		List<Card> cards = new ArrayList<Card>();
		cards.addAll(createAllNonSpecialCards());
		cards.addAll(createAllSpecialCards());
		shuffleCards(cards);
		return cards;
	}
	
	/**
	 * M�thode priv�e permettant de m�langer une liste de cartes
	 * @param cards Liste de cartes � m�langer
	 */
	private void shuffleCards(List<Card> cards) {
		long seed = System.nanoTime();
		Collections.shuffle(cards, new Random(seed));
	}
	
	/* ========================================= REFILL ========================================= */
	
	/**
	 * M�thode permettant de renvoyer une collection de cartes m�lang�es � partir d'une collection fournie
	 * @param givenCards Collection de cartes � m�langer
	 * @return Une Queue contenant les cartes m�lang�es 
	 */
	public Queue<Card> refillCardsFrom(Collection<Card> givenCards) {
		List<Card> cardsToShuffle = new ArrayList<Card>(givenCards);
		Queue<Card> finalCards = new LinkedList<Card>();
		shuffleCards(cardsToShuffle);
		for(Card c : cardsToShuffle) {
			finalCards.add(c);
		}
		return finalCards;
	}
	
	/* ========================================= NUMBERED CARD CREATION ========================================= */
	
	/**
	 * M�thode permettant de cr�er toutes les cartes "non-sp�ciales", donc toutes les cartes num�rot�es
	 * @return Liste comprenant toutes les cartes sus-cit�es (1 carte Z�ro et 2 cartes par num�ro de 1 � 9 pour chaque couleur)
	 */
	private List<Card> createAllNonSpecialCards() {
		List<Card> nonSpecialCards = new ArrayList<Card>();
		nonSpecialCards.addAll(createAllCardsWithSpecificColor(Color.RED));
		nonSpecialCards.addAll(createAllCardsWithSpecificColor(Color.BLUE));
		nonSpecialCards.addAll(createAllCardsWithSpecificColor(Color.YELLOW));
		nonSpecialCards.addAll(createAllCardsWithSpecificColor(Color.GREEN));
		return nonSpecialCards;
	}
	
	/**
	 * M�thode priv�e permettant de cr�er toutes les cartes num�rot�e d'une couleur donn�e
	 * @param color Couleur commune des cartes
	 * @return Un ensemble de 19 cartes (1 carte Z�ro et 2 cartes par num�ro de 1 � 9) de m�me couleur
	 */
	private List<Card> createAllCardsWithSpecificColor(Color color) {
		List<Card> cardsWithSpecificColor = new ArrayList<Card>();
		addAmountOfCardsOf(1, cardsWithSpecificColor, 0, color);
		addAllOtherColoredNumberedCards(color, cardsWithSpecificColor);
		return cardsWithSpecificColor;
	}
	
	/**
	 * M�thode priv�e permettant de cr�er toutes les paires de cartes num�rot�es
	 * @param color Couleur commune des cartes
	 * @param currentCards Collection de cartes � remplir
	 */
	private void addAllOtherColoredNumberedCards(Color color, List<Card> currentCards) {
		for(int number=1; number<=9; number++) {
			addAmountOfCardsOf(2, currentCards, number, color);
		}
	}
	
	/**
	 * M�thode priv�e permettant d'ajouter un nombre d�fini de cartes identiques (m�me num�ro, m�me couleur) � la collection
	 * @param amount Nombre de cartes � ajouter � la collection
	 * @param currentCards Collection de cartes � remplir
	 * @param number Num�ro de la carte
	 * @param color Couleur de la carte
	 */
	private void addAmountOfCardsOf(int amount, List<Card> currentCards, int number, Color color) {
		for(int i=0; i<amount; i++) {
			currentCards.add(new Card(number,color));
		}
	}
	
	/* ========================================= SPECIAL CARD CREATION ========================================= */
	
	/**
	 * M�thode priv�e permettant de cr�er toutes les cartes sp�ciales (color�es, ou joker)
	 * @return Une collection de cartes contenant uniquement les cartes sp�ciales
	 */
	private List<Card> createAllSpecialCards() {
		List<Card> specialCards = new ArrayList<Card>();
		specialCards.addAll(createAllColoredSpecialCards());
		specialCards.addAll(createAllColorlessSpecialCards());
		return specialCards;
	}

	/**
	 * M�thode priv�e permettant de cr�er toutes les cartes sp�ciales color�es (6 par couleur)
	 * @return Une collection de cartes contenant uniquement les cartes sp�ciales color�es
	 */
	private List<Card> createAllColoredSpecialCards() {
		List<Card> coloredSpecialCards = new ArrayList<Card>();
		coloredSpecialCards.addAll(createAllSpecialCardsWtihSpecificColor(Color.RED));
		coloredSpecialCards.addAll(createAllSpecialCardsWtihSpecificColor(Color.BLUE));
		coloredSpecialCards.addAll(createAllSpecialCardsWtihSpecificColor(Color.YELLOW));
		coloredSpecialCards.addAll(createAllSpecialCardsWtihSpecificColor(Color.GREEN));
		return coloredSpecialCards;
	}

	/**
	 * M�thode priv�e permettant de cr�er toutes les cartes sp�ciales color�es d'une couleur d�finie
	 * @param color Couleur commune � toutes les cartes sp�ciales
	 * @return Une Collection de cartes contenant uniquement les cartes sp�ciales num�rot�es d'une couleur donn�e
	 */
	private List<Card> createAllSpecialCardsWtihSpecificColor(Color color) {
		List<Card> coloredSpecialCards = new ArrayList<Card>();
		addAmountOfSpecialCardsOf(2, coloredSpecialCards, 20, color, new EffectPlus2(2));
		addAmountOfSpecialCardsOf(2, coloredSpecialCards, 20, color, new EffectReverse());
		addAmountOfSpecialCardsOf(2, coloredSpecialCards, 20, color, new EffectSkip());
		return coloredSpecialCards;
	}
	
	/**
	 * M�thode priv�e permettant de cr�er les cartes sp�ciales non color�es (Joker)
	 * @return Une collection de cartes comprenant uniquement les cartes joker
	 */
	private List<Card> createAllColorlessSpecialCards() {
		List<Card> colorlessSpecialCards = new ArrayList<Card>();
		addAmountOfSpecialCardsOf(4, colorlessSpecialCards, 50, Color.JOKER, new EffetJoker());
		addAmountOfSpecialCardsOf(4, colorlessSpecialCards, 50, Color.JOKER, new EffectPlus4());
		return colorlessSpecialCards;
	}
	
	/**
	 * M�thode priv�e permettant d'ajouter un nombre d�fini de cartes sp�ciales identiques (m�me num�ro, m�me couleur, m�me effet) � la collection
	 * @param amount Nombre de cartes � ajouter � la collection
	 * @param currentCards Collection de cartes � remplir
	 * @param pointsValue Num�ro de la carte
	 * @param color Couleur de la carte
	 * @param effect Effet de la carte
	 */
	private void addAmountOfSpecialCardsOf(int amount, List<Card> currentCards, int pointsValue, Color color, Effect effect) {
		for(int i=0; i<amount; i++) {
			currentCards.add(new CardSpecial(pointsValue,color,effect));
		}
	}
}