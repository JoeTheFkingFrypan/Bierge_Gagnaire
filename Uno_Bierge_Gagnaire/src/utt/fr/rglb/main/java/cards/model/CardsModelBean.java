package utt.fr.rglb.main.java.cards.model;

import com.google.common.base.Preconditions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.CardSpecial;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.view.AbstractView;

/**
 * Classe dont le rôle est de permettre un test de compatibilité entre la carte choisie par l'utilisateur et la carte de référence de manière plus aisée
 * </br>Elle comprend également la couleur globale définie lors du jeu d'un joker.
 * </br>Elle centralise donc tous les critères de validité lors du choix d'une carte
 */
public class CardsModelBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Card lastCardPlayed;
	private Color globalColor;
	private AbstractView consoleView;

	/* ========================================= CONSTRUCTOR ========================================= */

	/**
	 * Constructeur de GameModelBean
	 * @param lastCardPlayed Carte dernièrement jouée (carte visible du talon)
	 * @param globalColor Couleur globale ayant éventuellement été choisie
	 * @param consoleView Vue permettant d'afficher des informations dans l'interface
	 */
	public CardsModelBean(Card lastCardPlayed, Color globalColor, AbstractView consoleView) {
		Preconditions.checkNotNull(lastCardPlayed,"[ERROR] provided card was null");
		Preconditions.checkNotNull(globalColor,"[ERROR] provided global color was null");
		Preconditions.checkNotNull(consoleView,"[ERROR] provided view was null");
		this.lastCardPlayed = lastCardPlayed;
		this.globalColor = globalColor;
		this.consoleView = consoleView;
	}

	/* ========================================= COMPARAISON - HIGH LEVEL ========================================= */

	/**
	 * Méthode permettant de s'assurer que la collection de cartes passée en paramètre a au moins une carte compatible avec la référence/couleur globale
	 * @param cardsFromPlayer Collection de cartes
	 * @return <code>TRUE</code> si la collection contient au moins une carte compatible, <code>FALSE</code> sinon
	 */
	public boolean isCompatibleWith(Collection<Card> cardsFromPlayer) {
		Preconditions.checkNotNull(cardsFromPlayer,"[ERROR] Could not check compatibility : provided card collection is null");
		for(Card currentCard : cardsFromPlayer) {
			if(isCompatibleWith(currentCard)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Méthode permettant de s'assurer que la carte passée en paramètre est compatible avec la référence/couleur globale
	 * @param cardFromPlayer Carte dont on souhaite connaitre la compatibilité
	 * @return <code>TRUE</code> si la carte est compatible, <code>FALSE</code> sinon
	 */
	public boolean isCompatibleWith(Card cardFromPlayer) {
		Preconditions.checkNotNull(cardFromPlayer,"[ERROR] Could not check compatibility : provided card is null");
		if(cardFromPlayer.isSpecial()) {
			CardSpecial explicitConversion = (CardSpecial)cardFromPlayer;
			return compatibilityCheckWithSpecialCard(explicitConversion);
		} else {
			return compatibilityCheckWithNumberedCard(cardFromPlayer);
		}
	}

	/**
	 * Méthode privée permettant de procéder à la comparaison entre la carte de référence et la carte SPECIALE passée en paramètre
	 * @param specialCardFromPlayer Carte dont on souhaite connaitre la compatibilité
	 * @return <code>TRUE</code> si la carte est compatible, <code>FALSE</code> sinon
	 */
	private boolean compatibilityCheckWithSpecialCard(CardSpecial specialCardFromPlayer) {
		Preconditions.checkNotNull(specialCardFromPlayer,"[ERROR] Could not check compatibility : provided special card is null");
		Preconditions.checkArgument(specialCardFromPlayer.isSpecial(),"[ERROR] Provided card is NOT special");
		if(globalComparaisonIsEnough(specialCardFromPlayer) || specialCardComparaisonIsEnough(specialCardFromPlayer)) {
			return true;
		}
		return false;
	}

	/**
	 * Méthode privée permettant de procéder à la comparaison entre la carte de référence et la carte passée en paramètre
	 * @param cardFromPlayer Carte dont on souhaite connaitre la compatibilité
	 * @return <code>TRUE</code> si la carte est compatible, <code>FALSE</code> sinon
	 */
	private boolean compatibilityCheckWithNumberedCard(Card cardFromPlayer) {
		Preconditions.checkNotNull(cardFromPlayer,"[ERROR] Could not check compatibility : provided card is null");
		Preconditions.checkArgument(!cardFromPlayer.isSpecial(),"[ERROR] Provided card IS special");
		if(globalComparaisonIsEnough(cardFromPlayer) || numberedCardComparaisonIsEnough(cardFromPlayer) ) {
			return true;
		}
		return false;
	}

	/* ========================================= COMPARAISON - UTILS ========================================= */

	/**
	 * Méthode privée permettant de vérifier si une "simple" comparaison est suffisante pour attester de sa compatibilité avec la référence
	 * @param currentCard Carte dont on souhaite connaitre la compatibilité
	 */
	private boolean numberedCardComparaisonIsEnough(Card currentCard) {
		Preconditions.checkNotNull(currentCard,"[ERROR] Could not check compatibility : provided card is null");
		if(getLastCardPlayed().isCompatibleWith(currentCard)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Méthode privée permettant de vérifier si une "simple" comparaison est suffisante pour attester de sa compatibilité avec la référence
	 * @param currentCard Carte dont on souhaite connaitre la compatibilité
	 * @return <code>TRUE</code> si la carte est compatible (couleur identique, effet identique) 
	 */
	private boolean specialCardComparaisonIsEnough(CardSpecial currentCard) {
		Preconditions.checkNotNull(currentCard,"[ERROR] Could not check compatibility : provided special card is null");
		if(getLastCardPlayed().isCompatibleWith(currentCard)) {
			return true;
		}
		return false;
	}

	/**
	 * Méthode privée permettant de vérifier si une comparaison de couleur entre la couleur globale et celle de la carte passée en paramètre est suffisante pour attester de sa compatibilité
	 * @param currentCard Carte dont on souhaite connaitre la compatibilité
	 * @return <code>TRUE</code> si la carte est compatible (couleur identique) 
	 */
	private boolean globalComparaisonIsEnough(Card currentCard) {
		Preconditions.checkNotNull(currentCard,"[ERROR] Cannot compare card to global color : provided card is null");
		if(globalColorIsSet()) {
			if(currentCard.getColor().equals(this.globalColor)) {
				return true;
			}
		}
		return false;
	}

	/* ========================================= GETTERS ========================================= */

	/**
	 * Méthode permettant d'obtenir les index des cartes jouables en main
	 * @param cardCollection Cartes actuellement en main
	 * @return Liste d'index correspondant aux cartes jouables
	 */
	public Collection<Integer> findPlayableCardsFrom(Collection<Card> cardCollection) {
		Preconditions.checkNotNull(cardCollection,"[ERROR] Cannot find playable card from collection : provided one is null");
		Collection<Integer> playableIndexes = new ArrayList<Integer>();
		int currentIndex = 0;
		for(Card currentCard : cardCollection) {
			if(isCompatibleWith(currentCard)) {
				playableIndexes.add(currentIndex);
			}
			currentIndex++;
		}
		return playableIndexes;
	}
	
	/**
	 * Méhode permettant de déterminer si l'utilisateur peut jouer d'autres cartes qu'un +4
	 * @param cardCollection Cartes actuellement en main
	 * @return <code>TRUE</code> si le joueur a d'autres cartes jouables qui ne sont pas des +4, <code>FALSE</code> sinon
	 */
	public boolean findIfPlayerHasPlayableCardsAsideFromPlusFour(Collection<Card> cardCollection) {
		Preconditions.checkNotNull(cardCollection,"[ERROR] Cannot find playable card from collection : provided one is null");
		int numberOfPlayableCardsThatAreNotPlusFour = 0;
		for(Card currentCard : cardCollection) {
			if(isCompatibleWith(currentCard)) {
				if(!currentCard.isPlusFour()) {
					numberOfPlayableCardsThatAreNotPlusFour++;
				}
			}
		}
		return numberOfPlayableCardsThatAreNotPlusFour >= 1;
	}
	
	/**
	 * Méthode permettant de savoir si une couleur globale est définie (si un joker/+4 a été précédement joué)
	 * @return <code>TRUE</code> si une couleur globale est définie, <code>FALSE</code> sinon
	 */
	public boolean globalColorIsSet() {
		return ! this.globalColor.equals(Color.JOKER);
	}

	/**
	 * Méthode permettant de récuperer la dernière carte ayant été jouée (sans la retirer)
	 * @return La dernière carte ayant été jouée
	 */
	public Card getLastCardPlayed() {
		return this.lastCardPlayed;
	}

	/* ========================================= DISPLAY ========================================= */

	/**
	 * Méthode permettant d'ajouter la couleur globale dans l'interface (colorée de manière adéquate)
	 */
	public void appendGlobalColorIfItIsSet() {
		if(globalColorIsSet()) {
			this.consoleView.displayTextBasedOnItsColor("* And global color is set to ",this.globalColor,"[RED]","[BLUE]","[GREEN]","[YELLOW]");		
		}
	}

	@Override
	public String toString() {
		return this.globalColor.toString() + " & " + this.lastCardPlayed.toString();
	}
}