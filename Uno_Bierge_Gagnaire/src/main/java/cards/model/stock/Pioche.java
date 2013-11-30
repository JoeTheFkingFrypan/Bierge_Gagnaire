package main.java.cards.model.stock;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import com.google.common.base.Preconditions;

import main.java.cards.model.basics.Carte;
import main.java.console.model.AbstractModel;

/**
 * Classe permettant de piocher des cartes, comprenant initialement l'ensemble des 108 cartes de jeu
 */
public class Pioche extends AbstractModel {
	private CardGenerator generator;
	private Queue<Carte> pioche;
	
	public Pioche() {
		this.generator = new CardGenerator();
		this.pioche = generateShuffledCards();
	}
	
	/**
	 * M�thode priv�e permettant d'initialiser la pioche (cr�ation des 108 cartes dans un ordre al�atoire)
	 * @return Une Queue contenant toutes les cartes dans un ordre al�atoire
	 */
	private Queue<Carte> generateShuffledCards() {
		return this.generator.generateCards();
	}
	
	public Carte drawOneCard() {
		Preconditions.checkState(this.pioche.size() >= 1,"[ERROR] Cannont draw [1] card : not enough cards");
		return pioche.poll();
	}
	
	/**
	 * M�thode permettant de tirer une (ou plusieurs) carte(s) depuis la pioche
	 * @param count Nombre de cartes � tirer
	 * @return Une collection contenant le nombre de cartes donn�
	 */
	public Collection<Carte> drawCards(int count) {
		Preconditions.checkArgument(this.pioche.size() >= count,"[ERROR] Cannont draw [" + count + "] cards : not enough cards");
		Collection<Carte> cardsToDeal = new LinkedList<Carte>();
		for(int i=0; i<count; i++) {
			cardsToDeal.add(pioche.poll());
		}
		return cardsToDeal;
	}
		
	/**
	 * M�thode permettant de remplir la pioche si jamais il n'y a plus suffisament de cartes
	 * @param givenCards Collection de cartes � utiliser pour reconstituer la pioche
	 */
	public void refill(Collection<Carte> givenCards) {
		Preconditions.checkNotNull(givenCards,"[ERROR] Cannot refill cards : given card collection is null");
		Preconditions.checkArgument(givenCards.size()>0,"[ERROR] Cannot refill cards : no cards provided");
		this.pioche = generator.refillCardsFrom(givenCards);
	}
	
	/**
	 * M�thode permettant sp�cifiant la fa�on dont s'affiche la pioche
	 */
	@Override
	public String toString() {
		return "[Pioche] Contient actuellement " + this.size() + " cartes";
	}
	
	/**
	 * M�thode permettant de r�cuperer le nombre de cartes contenues dans la pioche
	 * @return Nombre de cartes pr�sentes dans la pioche
	 */
	public int size() {
		return this.pioche.size();
	}
	
	public boolean hasNotEnoughCards(int cardCountToBeDrawn) {
		Preconditions.checkArgument(cardCountToBeDrawn>0,"[ERROR] Amount of cards drawn must be stricly higher than 0 (Expected : 1+)");
		return cardCountToBeDrawn > this.size();
	}
	
	/**
	 * M�thode permettant de s'assurer la pr�sence d'une carte donn�e dans la pioche
	 * @param c Carte dont la pr�sence est � tester
	 * @return TRUE si la carte est contenue, FALSE sinon
	 */
	public Boolean contains(Carte c) {
		Preconditions.checkNotNull(c,"[ERROR] Cannot verfify if stock contains card, because provided reference is null");
		return this.pioche.contains(c);
	}
}
