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
 * Classe (à visibilité réduite) à qui a été délégué le rôle de la création des cartes (et de leur mélange)
 */
//FIXME : create factory
class CardGenerator implements Serializable {
	private static final long serialVersionUID = 1L;
	private final int starterRedIndex = 0;
	private final int starterBlueIndex = 13;
	private final int starterYellowIndex = 26;
	private final int starterGreenIndex = 39;
	private final int starterSpecialIndex = 52;
	
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
	 * @param i 
	 * @return Liste comprenant toutes les cartes sus-citées (1 carte Zéro et 2 cartes par numéro de 1 à 9 pour chaque couleur)
	 */
	private List<Card> createAllNonSpecialCards() {
		List<Card> nonSpecialCards = new ArrayList<Card>();
		nonSpecialCards.addAll(createAllCardsWithSpecificColor(Color.RED, this.starterRedIndex));
		nonSpecialCards.addAll(createAllCardsWithSpecificColor(Color.BLUE, this.starterBlueIndex));
		nonSpecialCards.addAll(createAllCardsWithSpecificColor(Color.YELLOW, this.starterYellowIndex));
		nonSpecialCards.addAll(createAllCardsWithSpecificColor(Color.GREEN, this.starterGreenIndex));
		return nonSpecialCards;
	}
	
	/**
	 * Méthode privée permettant de créer toutes les cartes numérotée d'une couleur donnée
	 * @param color Couleur commune des cartes
	 * @return Un ensemble de 19 cartes (1 carte Zéro et 2 cartes par numéro de 1 à 9) de même couleur
	 */
	private List<Card> createAllCardsWithSpecificColor(Color color, int currentImageIndex) {
		List<Card> cardsWithSpecificColor = new ArrayList<Card>();
		addAmountOfCardsOf(1, cardsWithSpecificColor, 0, color, currentImageIndex++);
		for(int number=1; number<=9; number++) {
			addAmountOfCardsOf(2, cardsWithSpecificColor, number, color,currentImageIndex++);
		}
		addAmountOfSpecialCardsOf(2, cardsWithSpecificColor, 20, color, new EffectPlus2(2),currentImageIndex++);
		addAmountOfSpecialCardsOf(2, cardsWithSpecificColor, 20, color, new EffectReverse(),currentImageIndex++);
		addAmountOfSpecialCardsOf(2, cardsWithSpecificColor, 20, color, new EffectSkip(),currentImageIndex++);
		return cardsWithSpecificColor;
	}
	
	/**
	 * Méthode privée permettant d'ajouter un nombre défini de cartes identiques (même numéro, même couleur) à la collection
	 * @param amount Nombre de cartes à ajouter à la collection
	 * @param currentCards Collection de cartes à remplir
	 * @param number Numéro de la carte
	 * @param color Couleur de la carte
	 */
	private void addAmountOfCardsOf(int amount, List<Card> currentCards, int number, Color color, int imageIndex) {
		for(int i=0; i<amount; i++) {
			currentCards.add(new Card(number,color,imageIndex));
		}
	}
	
	/* ========================================= SPECIAL CARD CREATION ========================================= */
	
	/**
	 * Méthode privée permettant de créer toutes les cartes spéciales (colorées, ou joker)
	 * @param i 
	 * @return Une collection de cartes contenant uniquement les cartes spéciales
	 */
	private List<Card> createAllSpecialCards() {
		List<Card> specialCards = new ArrayList<Card>();
		int currentImageIndex = this.starterSpecialIndex;
		addAmountOfSpecialCardsOf(4, specialCards, 50, Color.JOKER, new EffetJoker(),currentImageIndex++);
		addAmountOfSpecialCardsOf(4, specialCards, 50, Color.JOKER, new EffectPlus4(),currentImageIndex++);
		return specialCards;
	}
	
	/**
	 * Méthode privée permettant d'ajouter un nombre défini de cartes spéciales identiques (même numéro, même couleur, même effet) à la collection
	 * @param amount Nombre de cartes à ajouter à la collection
	 * @param currentCards Collection de cartes à remplir
	 * @param pointsValue Numéro de la carte
	 * @param color Couleur de la carte
	 * @param effect Effet de la carte
	 */
	private void addAmountOfSpecialCardsOf(int amount, List<Card> currentCards, int pointsValue, Color color, Effect effect, int currentImageIndex) {
		for(int i=0; i<amount; i++) {
			currentCards.add(new CardSpecial(pointsValue,color,effect,currentImageIndex));
		}
	}
}