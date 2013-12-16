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
 * Classe à qui a été délégué le rôle de la création des cartes (et de leur mélange)
 */
class CardGenerator implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* ========================================= COLLECTIONS CREATION ========================================= */

	/**
	 * Méthode permettant d'initialiser la pioche en générant les 108 cartes de départ dans un ordre aléatoire
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
	 * Méthode privée permettant de générer les 108 cartes dans un ordre aléatoire
	 * @return Une List de carte mélangées
	 */
	private List<Card> generateShuffledCards() {
		List<Card> cards = new ArrayList<Card>();
		cards.addAll(createAllNonSpecialCards());
		cards.addAll(createAllSpecialCards());
		shuffleCards(cards);
		return cards;
	}
	
	/**
	 * Méthode privée permettant de mélanger une liste de cartes
	 * @param cards Liste de cartes à mélanger
	 */
	private void shuffleCards(List<Card> cards) {
		long seed = System.nanoTime();
		Collections.shuffle(cards, new Random(seed));
	}
	
	/* ========================================= REFILL ========================================= */
	
	/**
	 * Méthode permettant de renvoyer une collection de cartes mélangées à partir d'une collection fournie
	 * @param givenCards Collection de cartes à mélanger
	 * @return Une Queue contenant les cartes mélangées 
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
	 * Méthode permettant de créer toutes les cartes "non-spéciales", donc toutes les cartes numérotées
	 * @return Liste comprenant toutes les cartes sus-citées (1 carte Zéro et 2 cartes par numéro de 1 à 9 pour chaque couleur)
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
	 * Méthode privée permettant de créer toutes les cartes numérotée d'une couleur donnée
	 * @param color Couleur commune des cartes
	 * @return Un ensemble de 19 cartes (1 carte Zéro et 2 cartes par numéro de 1 à 9) de même couleur
	 */
	private List<Card> createAllCardsWithSpecificColor(Color color) {
		List<Card> cardsWithSpecificColor = new ArrayList<Card>();
		addAmountOfCardsOf(1, cardsWithSpecificColor, 0, color);
		addAllOtherColoredNumberedCards(color, cardsWithSpecificColor);
		return cardsWithSpecificColor;
	}
	
	/**
	 * Méthode privée permettant de créer toutes les paires de cartes numérotées
	 * @param color Couleur commune des cartes
	 * @param currentCards Collection de cartes à remplir
	 */
	private void addAllOtherColoredNumberedCards(Color color, List<Card> currentCards) {
		for(int number=1; number<=9; number++) {
			addAmountOfCardsOf(2, currentCards, number, color);
		}
	}
	
	/**
	 * Méthode privée permettant d'ajouter un nombre défini de cartes identiques (même numéro, même couleur) à la collection
	 * @param amount Nombre de cartes à ajouter à la collection
	 * @param currentCards Collection de cartes à remplir
	 * @param number Numéro de la carte
	 * @param color Couleur de la carte
	 */
	private void addAmountOfCardsOf(int amount, List<Card> currentCards, int number, Color color) {
		for(int i=0; i<amount; i++) {
			currentCards.add(new Card(number,color));
		}
	}
	
	/* ========================================= SPECIAL CARD CREATION ========================================= */
	
	/**
	 * Méthode privée permettant de créer toutes les cartes spéciales (colorées, ou joker)
	 * @return Une collection de cartes contenant uniquement les cartes spéciales
	 */
	private List<Card> createAllSpecialCards() {
		List<Card> specialCards = new ArrayList<Card>();
		specialCards.addAll(createAllColoredSpecialCards());
		specialCards.addAll(createAllColorlessSpecialCards());
		return specialCards;
	}

	/**
	 * Méthode privée permettant de créer toutes les cartes spéciales colorées (6 par couleur)
	 * @return Une collection de cartes contenant uniquement les cartes spéciales colorées
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
	 * Méthode privée permettant de créer toutes les cartes spéciales colorées d'une couleur définie
	 * @param color Couleur commune à toutes les cartes spéciales
	 * @return Une Collection de cartes contenant uniquement les cartes spéciales numérotées d'une couleur donnée
	 */
	private List<Card> createAllSpecialCardsWtihSpecificColor(Color color) {
		List<Card> coloredSpecialCards = new ArrayList<Card>();
		addAmountOfSpecialCardsOf(2, coloredSpecialCards, 20, color, new EffectPlus2(2));
		addAmountOfSpecialCardsOf(2, coloredSpecialCards, 20, color, new EffectReverse());
		addAmountOfSpecialCardsOf(2, coloredSpecialCards, 20, color, new EffectSkip());
		return coloredSpecialCards;
	}
	
	/**
	 * Méthode privée permettant de créer les cartes spéciales non colorées (Joker)
	 * @return Une collection de cartes comprenant uniquement les cartes joker
	 */
	private List<Card> createAllColorlessSpecialCards() {
		List<Card> colorlessSpecialCards = new ArrayList<Card>();
		addAmountOfSpecialCardsOf(4, colorlessSpecialCards, 50, Color.JOKER, new EffetJoker());
		addAmountOfSpecialCardsOf(4, colorlessSpecialCards, 50, Color.JOKER, new EffectPlus4());
		return colorlessSpecialCards;
	}
	
	/**
	 * Méthode privée permettant d'ajouter un nombre défini de cartes spéciales identiques (même numéro, même couleur, même effet) à la collection
	 * @param amount Nombre de cartes à ajouter à la collection
	 * @param currentCards Collection de cartes à remplir
	 * @param pointsValue Numéro de la carte
	 * @param color Couleur de la carte
	 * @param effect Effet de la carte
	 */
	private void addAmountOfSpecialCardsOf(int amount, List<Card> currentCards, int pointsValue, Color color, Effect effect) {
		for(int i=0; i<amount; i++) {
			currentCards.add(new CardSpecial(pointsValue,color,effect));
		}
	}
}