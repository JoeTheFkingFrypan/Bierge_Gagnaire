package utt.fr.rglb.main.java.cards.model.stock;

import com.google.common.base.Preconditions;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.console.model.AbstractModel;

/**
 * Classe permettant de piocher des cartes, comprenant initialement l'ensemble des 108 cartes de jeu
 */
public class Stock extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private CardGenerator generator;
	private Queue<Card> stock;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	/**
	 * Constructeur de pioche (génére les 108 cartes)
	 */
	public Stock() {
		this.generator = new CardGenerator();
		this.stock = null;
	}
	
	/**
	 * Méthode permettant de ré-initialiser le talon (génération de 108 cartes mélangées)
	 */
	public void resetCards() {
		this.stock = generateShuffledCards();
	}
	
	/* ========================================= CARD CREATION & REFILL ========================================= */
	
	/**
	 * Méthode privée permettant d'initialiser la pioche (création des 108 cartes dans un ordre aléatoire)
	 * @return Une Queue contenant toutes les cartes dans un ordre aléatoire
	 */
	private Queue<Card> generateShuffledCards() {
		return this.generator.generateCards();
	}
	
	/**
	 * Méthode permettant de s'assurer que la pioche a suffisament de cartes pour satisfaire les besoins des joueurs
	 * @param cardCountToBeDrawn Nombre de cartes devant être piochées
	 * @return <code>TRUE</code> s'il y a suffisament de cartes, <code>FALSE</code> sinon
	 */
	public boolean hasNotEnoughCards(int cardCountToBeDrawn) {
		Preconditions.checkArgument(cardCountToBeDrawn > 0,"[ERROR] Amount of cards drawn must be stricly higher than 0 (Expected : 1+)");
		return cardCountToBeDrawn > this.size();
	}
	
	/**
	 * Méthode permettant de remplir la pioche si jamais il n'y a plus suffisament de cartes
	 * @param givenCards Collection de cartes à utiliser pour reconstituer la pioche
	 */
	public void refill(Collection<Card> givenCards) {
		Preconditions.checkNotNull(givenCards,"[ERROR] Cannot refill cards : given card collection is null");
		Preconditions.checkArgument(givenCards.size() > 0,"[ERROR] Cannot refill cards : no cards provided");
		this.stock = generator.refillCardsFrom(givenCards);
	}
	
	/* ========================================= DRAW CARD ========================================= */
	
	/**
	 * Méthode permettant de tirer une unique carte depuis la pioche
	 * @return Première carte de la pioche
	 */
	public Card drawOneCard() {
		Preconditions.checkState(this.stock.size() >= 1,"[ERROR] Cannont draw [1] card : not enough cards");
		return stock.poll();
	}
	
	/**
	 * Méthode permettant de tirer une (ou plusieurs) carte(s) depuis la pioche
	 * @param count Nombre de cartes à tirer
	 * @return Une collection contenant le nombre de cartes donné
	 */
	public Collection<Card> drawCards(int count) {
		Preconditions.checkArgument(this.stock.size() >= count,"[ERROR] Cannont draw [" + count + "] cards : not enough cards");
		Collection<Card> cardsToDeal = new LinkedList<Card>();
		for(int i=0; i<count; i++) {
			cardsToDeal.add(stock.poll());
		}
		return cardsToDeal;
	}
		
	/* ========================================= GETTERS & UTILS ========================================= */
	
	/**
	 * Méthode permettant de récuperer le nombre de cartes contenues dans la pioche
	 * @return Nombre de cartes présentes dans la pioche
	 */
	public int size() {
		return this.stock.size();
	}
	
	/**
	 * Méthode permettant de s'assurer la présence d'une carte donnée dans la pioche
	 * @param card Carte dont la présence est à tester
	 * @return <code>TRUE</code> si la carte est contenue, <code>FALSE</code> sinon
	 */
	public Boolean contains(Card card) {
		Preconditions.checkNotNull(card,"[ERROR] Cannot verfify if stock contains card, because provided reference is null");
		return this.stock.contains(card);
	}
	
	/**
	 * Méthode permettant spécifiant la façon dont s'affiche la pioche
	 */
	@Override
	public String toString() {
		return "[Pioche] Contient actuellement " + this.size() + " cartes";
	}
}
