package main.modele.piocheModele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import main.modele.carteModele.Carte;
import main.modele.carteModele.CarteSpeciale;
import main.modele.carteModele.Couleur;
import main.modele.carteModele.Effet;
import main.modele.carteModele.EffetChangerSens;
import main.modele.carteModele.EffetJoker;
import main.modele.carteModele.EffetPasserTour;
import main.modele.carteModele.EffetPiocherCarte;
import main.modele.carteModele.EffetPlus4;

/**
 * Classe a qui a été délégué le rôle de la création des cartes (et de leur mélange)
 */
class CardGenerator {
	
	/**
	 * Méthode permettant d'initialiser la pioche en générant les 108 cartes de départ dans un ordre aléatoire
	 * @return Une Queue contenant les 108 cartes
	 */
	public Queue<Carte> generateCards() {
		Queue<Carte> queuedCards = new LinkedList<Carte>();
		List<Carte> cards = generateShuffledCards();
		for(Carte c : cards) {
			queuedCards.add(c);
		}
		return queuedCards;
	}
	
	/**
	 * Méthode privée permettant de mélanger une liste de cartes
	 * @param cards Liste de cartes à mélanger
	 */
	private void shuffleCards(List<Carte> cards) {
		long seed = System.nanoTime();
		Collections.shuffle(cards, new Random(seed));
	}
	
	/**
	 * Méthode privée permettant de générer les 108 cartes dans un ordre aléatoire
	 * @return Une List de carte mélangées
	 */
	private List<Carte> generateShuffledCards() {
		List<Carte> cards = new ArrayList<Carte>();
		cards.addAll(createAllNonSpecialCards());
		cards.addAll(createAllSpecialCards());
		shuffleCards(cards);
		return cards;
	}
	
	/**
	 * Méthode permettant de renvoyer une collection de cartes mélangées à partir d'une collection fournie
	 * @param givenCards Collection de cartes à mélanger
	 * @return Une Queue contenant les cartes mélangées 
	 */
	public Queue<Carte> refillCardsFrom(Collection<Carte> givenCards) {
		List<Carte> cardsToShuffle = new ArrayList<Carte>(givenCards);
		Queue<Carte> finalCards = new LinkedList<Carte>();
		shuffleCards(cardsToShuffle);
		for(Carte c : cardsToShuffle) {
			finalCards.add(c);
		}
		return finalCards;
	}
	
	/*=============== CARTES NUMEROTEES ===============*/
	
	/**
	 * Méthode permettant de créer toutes les cartes "non-spéciales", donc toutes les cartes numérotées
	 * @return Liste comprenant toutes les cartes sus-citées (1 carte Zéro et 2 cartes par numéro de 1 à 9 pour chaque couleur)
	 */
	private List<Carte> createAllNonSpecialCards() {
		List<Carte> nonSpecialCards = new ArrayList<Carte>();
		nonSpecialCards.addAll(createAllCardsWithSpecificColor(Couleur.ROUGE));
		nonSpecialCards.addAll(createAllCardsWithSpecificColor(Couleur.BLEUE));
		nonSpecialCards.addAll(createAllCardsWithSpecificColor(Couleur.JAUNE));
		nonSpecialCards.addAll(createAllCardsWithSpecificColor(Couleur.VERTE));
		return nonSpecialCards;
	}
	
	/**
	 * Méthode privée permettant de créer toutes les cartes numérotée d'une couleur donnée
	 * @param color Couleur commune des cartes
	 * @return Un ensemble de 19 cartes (1 carte Zéro et 2 cartes par numéro de 1 à 9) de même couleur
	 */
	private List<Carte> createAllCardsWithSpecificColor(Couleur color) {
		List<Carte> cardsWithSpecificColor = new ArrayList<Carte>();
		addAmountOfCardsOf(1, cardsWithSpecificColor, 0, color);
		addAllOtherColoredNumberedCards(color, cardsWithSpecificColor);
		return cardsWithSpecificColor;
	}
	
	/**
	 * Méthode privée permettant de créer toutes les paires de cartes numérotées
	 * @param color Couleur commune des cartes
	 * @param currentCards Collection de cartes à remplir
	 */
	private void addAllOtherColoredNumberedCards(Couleur color, List<Carte> currentCards) {
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
	private void addAmountOfCardsOf(int amount, List<Carte> currentCards, int number, Couleur color) {
		for(int i=0; i<amount; i++) {
			currentCards.add(new Carte(number,color));
		}
	}
	
	/*=============== CARTES SPECIALES ===============*/
	
	/**
	 * Méthode privée permettant de créer toutes les cartes spéciales (colorées, ou joker)
	 * @return Une collection de cartes contenant uniquement les cartes spéciales
	 */
	private List<Carte> createAllSpecialCards() {
		List<Carte> specialCards = new ArrayList<Carte>();
		specialCards.addAll(createAllColoredSpecialCards());
		specialCards.addAll(createAllColorlessSpecialCards());
		return specialCards;
	}

	/**
	 * Méthode privée permettant de créer toutes les cartes spéciales colorées (6 par couleur)
	 * @return Une collection de cartes contenant uniquement les cartes spéciales colorées
	 */
	private List<Carte> createAllColoredSpecialCards() {
		List<Carte> coloredSpecialCards = new ArrayList<Carte>();
		coloredSpecialCards.addAll(createAllSpecialCardsWtihSpecificColor(Couleur.ROUGE));
		coloredSpecialCards.addAll(createAllSpecialCardsWtihSpecificColor(Couleur.BLEUE));
		coloredSpecialCards.addAll(createAllSpecialCardsWtihSpecificColor(Couleur.JAUNE));
		coloredSpecialCards.addAll(createAllSpecialCardsWtihSpecificColor(Couleur.VERTE));
		return coloredSpecialCards;
	}

	/**
	 * Méthode privée permettant de créer toutes les cartes spéciales colorées d'une couleur définie
	 * @param color Couleur commune à toutes les cartes spéciales
	 * @return Une Collection de cartes contenant uniquement les cartes spéciales numérotées d'une couleur donnée
	 */
	private List<Carte> createAllSpecialCardsWtihSpecificColor(Couleur color) {
		List<Carte> coloredSpecialCards = new ArrayList<Carte>();
		addAmountOfSpecialCardsOf(2, coloredSpecialCards, 20, color, new EffetPiocherCarte(2));
		addAmountOfSpecialCardsOf(2, coloredSpecialCards, 20, color, new EffetChangerSens());
		addAmountOfSpecialCardsOf(2, coloredSpecialCards, 20, color, new EffetPasserTour());
		return coloredSpecialCards;
	}
	
	/**
	 * Méthode privée permettant de créer les cartes spéciales non colorées (Joker)
	 * @return Une collection de cartes comprenant uniquement les cartes joker
	 */
	private List<Carte> createAllColorlessSpecialCards() {
		List<Carte> colorlessSpecialCards = new ArrayList<Carte>();
		addAmountOfSpecialCardsOf(4, colorlessSpecialCards, 50, Couleur.JOKER, new EffetJoker());
		addAmountOfSpecialCardsOf(4, colorlessSpecialCards, 50, Couleur.JOKER, new EffetPlus4());
		return colorlessSpecialCards;
	}
	
	/**
	 * Méthode privée permettant d'ajouter un nombre défini de cartes spéciales identiques (même numéro, même couleur, même effet) à la collection
	 * @param amount Nombre de cartes à ajouter à la collection
	 * @param currentCards Collection de cartes à remplir
	 * @param number Numéro de la carte
	 * @param color Couleur de la carte
	 * @param effect Effet de la carte
	 */
	private void addAmountOfSpecialCardsOf(int amount, List<Carte> currentCards, int pointsValue, Couleur color, Effet effect) {
		for(int i=0; i<amount; i++) {
			currentCards.add(new CarteSpeciale(pointsValue,color,effect));
		}
	}	
}