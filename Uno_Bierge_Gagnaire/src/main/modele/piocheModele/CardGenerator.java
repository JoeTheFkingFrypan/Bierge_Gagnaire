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

class CardGenerator {
	public Queue<Carte> generateCards() {
		Queue<Carte> queuedCards = new LinkedList<Carte>();
		List<Carte> cards = generateShuffledCards();
		for(Carte c : cards) {
			queuedCards.add(c);
		}
		return queuedCards;
	}
	
	private void shuffleCards(List<Carte> cards) {
		long seed = System.nanoTime();
		Collections.shuffle(cards, new Random(seed));
	}
	
	private List<Carte> generateShuffledCards() {
		List<Carte> cards = new ArrayList<Carte>();
		cards.addAll(createAllNonSpecialCards());
		cards.addAll(createAllSpecialCards());
		shuffleCards(cards);
		return cards;
	}
	
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
	
	private List<Carte> createAllNonSpecialCards() {
		List<Carte> nonSpecialCards = new ArrayList<Carte>();
		nonSpecialCards.addAll(createAllCardsWithSpecificColor(Couleur.ROUGE));
		nonSpecialCards.addAll(createAllCardsWithSpecificColor(Couleur.BLEUE));
		nonSpecialCards.addAll(createAllCardsWithSpecificColor(Couleur.JAUNE));
		nonSpecialCards.addAll(createAllCardsWithSpecificColor(Couleur.VERTE));
		return nonSpecialCards;
	}
	
	private List<Carte> createAllCardsWithSpecificColor(Couleur color) {
		List<Carte> cardsWithSpecificColor = new ArrayList<Carte>();
		addAmountOfCardsOf(1, cardsWithSpecificColor, 0, color);
		addAllOtherColoredNumberedCards(color, cardsWithSpecificColor);
		return cardsWithSpecificColor;
	}
	
	private void addAllOtherColoredNumberedCards(Couleur color, List<Carte> currentCards) {
		for(int number=1; number<=9; number++) {
			addAmountOfCardsOf(2, currentCards, number, color);
		}
	}
	
	private void addAmountOfCardsOf(int amount, List<Carte> currentCards, int number, Couleur color) {
		for(int i=0; i<amount; i++) {
			currentCards.add(new Carte(number,color));
		}
	}
	
	/*=============== CARTES SPECIALES ===============*/
	
	private List<Carte> createAllSpecialCards() {
		List<Carte> specialCards = new ArrayList<Carte>();
		specialCards.addAll(createAllColoredSpecialCards());
		specialCards.addAll(createAllColorlessSpecialCards());
		return specialCards;
	}

	private List<Carte> createAllColoredSpecialCards() {
		List<Carte> coloredSpecialCards = new ArrayList<Carte>();
		coloredSpecialCards.addAll(createAllSpecialCardsWtihSpecificColor(Couleur.ROUGE));
		coloredSpecialCards.addAll(createAllSpecialCardsWtihSpecificColor(Couleur.BLEUE));
		coloredSpecialCards.addAll(createAllSpecialCardsWtihSpecificColor(Couleur.JAUNE));
		coloredSpecialCards.addAll(createAllSpecialCardsWtihSpecificColor(Couleur.VERTE));
		return coloredSpecialCards;
	}

	private List<Carte> createAllSpecialCardsWtihSpecificColor(Couleur color) {
		List<Carte> coloredSpecialCards = new ArrayList<Carte>();
		addAmountOfSpecialCardsOf(2, coloredSpecialCards, 20, color, new EffetPiocherCarte(2));
		addAmountOfSpecialCardsOf(2, coloredSpecialCards, 20, color, new EffetChangerSens());
		addAmountOfSpecialCardsOf(2, coloredSpecialCards, 20, color, new EffetPasserTour());
		return coloredSpecialCards;
	}
	
	private List<Carte> createAllColorlessSpecialCards() {
		List<Carte> colorlessSpecialCards = new ArrayList<Carte>();
		addAmountOfSpecialCardsOf(4, colorlessSpecialCards, 50, Couleur.JOKER, new EffetJoker());
		addAmountOfSpecialCardsOf(4, colorlessSpecialCards, 50, Couleur.JOKER, new EffetPlus4());
		return colorlessSpecialCards;
	}
	
	private void addAmountOfSpecialCardsOf(int amount, List<Carte> currentCards, int pointsValue, Couleur color, Effet effect) {
		for(int i=0; i<amount; i++) {
			currentCards.add(new CarteSpeciale(pointsValue,color,effect));
		}
	}	
}