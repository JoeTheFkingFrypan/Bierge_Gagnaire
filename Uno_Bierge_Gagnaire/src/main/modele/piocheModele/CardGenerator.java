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
 * Classe a qui a �t� d�l�gu� le r�le de la cr�ation des cartes (et de leur m�lange)
 */
class CardGenerator {
	
	/**
	 * M�thode permettant d'initialiser la pioche en g�n�rant les 108 cartes de d�part dans un ordre al�atoire
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
	 * M�thode priv�e permettant de m�langer une liste de cartes
	 * @param cards Liste de cartes � m�langer
	 */
	private void shuffleCards(List<Carte> cards) {
		long seed = System.nanoTime();
		Collections.shuffle(cards, new Random(seed));
	}
	
	/**
	 * M�thode priv�e permettant de g�n�rer les 108 cartes dans un ordre al�atoire
	 * @return Une List de carte m�lang�es
	 */
	private List<Carte> generateShuffledCards() {
		List<Carte> cards = new ArrayList<Carte>();
		cards.addAll(createAllNonSpecialCards());
		cards.addAll(createAllSpecialCards());
		shuffleCards(cards);
		return cards;
	}
	
	/**
	 * M�thode permettant de renvoyer une collection de cartes m�lang�es � partir d'une collection fournie
	 * @param givenCards Collection de cartes � m�langer
	 * @return Une Queue contenant les cartes m�lang�es 
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
	 * M�thode permettant de cr�er toutes les cartes "non-sp�ciales", donc toutes les cartes num�rot�es
	 * @return Liste comprenant toutes les cartes sus-cit�es (1 carte Z�ro et 2 cartes par num�ro de 1 � 9 pour chaque couleur)
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
	 * M�thode priv�e permettant de cr�er toutes les cartes num�rot�e d'une couleur donn�e
	 * @param color Couleur commune des cartes
	 * @return Un ensemble de 19 cartes (1 carte Z�ro et 2 cartes par num�ro de 1 � 9) de m�me couleur
	 */
	private List<Carte> createAllCardsWithSpecificColor(Couleur color) {
		List<Carte> cardsWithSpecificColor = new ArrayList<Carte>();
		addAmountOfCardsOf(1, cardsWithSpecificColor, 0, color);
		addAllOtherColoredNumberedCards(color, cardsWithSpecificColor);
		return cardsWithSpecificColor;
	}
	
	/**
	 * M�thode priv�e permettant de cr�er toutes les paires de cartes num�rot�es
	 * @param color Couleur commune des cartes
	 * @param currentCards Collection de cartes � remplir
	 */
	private void addAllOtherColoredNumberedCards(Couleur color, List<Carte> currentCards) {
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
	private void addAmountOfCardsOf(int amount, List<Carte> currentCards, int number, Couleur color) {
		for(int i=0; i<amount; i++) {
			currentCards.add(new Carte(number,color));
		}
	}
	
	/*=============== CARTES SPECIALES ===============*/
	
	/**
	 * M�thode priv�e permettant de cr�er toutes les cartes sp�ciales (color�es, ou joker)
	 * @return Une collection de cartes contenant uniquement les cartes sp�ciales
	 */
	private List<Carte> createAllSpecialCards() {
		List<Carte> specialCards = new ArrayList<Carte>();
		specialCards.addAll(createAllColoredSpecialCards());
		specialCards.addAll(createAllColorlessSpecialCards());
		return specialCards;
	}

	/**
	 * M�thode priv�e permettant de cr�er toutes les cartes sp�ciales color�es (6 par couleur)
	 * @return Une collection de cartes contenant uniquement les cartes sp�ciales color�es
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
	 * M�thode priv�e permettant de cr�er toutes les cartes sp�ciales color�es d'une couleur d�finie
	 * @param color Couleur commune � toutes les cartes sp�ciales
	 * @return Une Collection de cartes contenant uniquement les cartes sp�ciales num�rot�es d'une couleur donn�e
	 */
	private List<Carte> createAllSpecialCardsWtihSpecificColor(Couleur color) {
		List<Carte> coloredSpecialCards = new ArrayList<Carte>();
		addAmountOfSpecialCardsOf(2, coloredSpecialCards, 20, color, new EffetPiocherCarte(2));
		addAmountOfSpecialCardsOf(2, coloredSpecialCards, 20, color, new EffetChangerSens());
		addAmountOfSpecialCardsOf(2, coloredSpecialCards, 20, color, new EffetPasserTour());
		return coloredSpecialCards;
	}
	
	/**
	 * M�thode priv�e permettant de cr�er les cartes sp�ciales non color�es (Joker)
	 * @return Une collection de cartes comprenant uniquement les cartes joker
	 */
	private List<Carte> createAllColorlessSpecialCards() {
		List<Carte> colorlessSpecialCards = new ArrayList<Carte>();
		addAmountOfSpecialCardsOf(4, colorlessSpecialCards, 50, Couleur.JOKER, new EffetJoker());
		addAmountOfSpecialCardsOf(4, colorlessSpecialCards, 50, Couleur.JOKER, new EffetPlus4());
		return colorlessSpecialCards;
	}
	
	/**
	 * M�thode priv�e permettant d'ajouter un nombre d�fini de cartes sp�ciales identiques (m�me num�ro, m�me couleur, m�me effet) � la collection
	 * @param amount Nombre de cartes � ajouter � la collection
	 * @param currentCards Collection de cartes � remplir
	 * @param number Num�ro de la carte
	 * @param color Couleur de la carte
	 * @param effect Effet de la carte
	 */
	private void addAmountOfSpecialCardsOf(int amount, List<Carte> currentCards, int pointsValue, Couleur color, Effet effect) {
		for(int i=0; i<amount; i++) {
			currentCards.add(new CarteSpeciale(pointsValue,color,effect));
		}
	}	
}