package main.modele.talonModele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import main.modele.carteModele.Carte;

/**
 * Classe permettant de réception les cartes jouées (avec comparaison par rapport à la dernière reçue).
 * Sert également à reconstituer la pioche lorsque cette dernière est vide.
 */
public class Talon {
	private final LinkedList<Carte> talon;
	
	public Talon() {
		this.talon = new LinkedList<Carte>();
	}
	
	/**
	 * Méthode permettant de jouer une carte
	 * @param c Carte à jouer
	 */
	public void playCard(Carte c) {
		//TODO: handle card playability
		//System.out.println("[CARTE JOUEE] " + c);
		talon.addFirst(c);
	}
	
	/**
	 * Méthode permettant de vider le talon de ses cartes (sauf de la dernière jouée) et de les transférer
	 * @return L'ensemble des cartes (exceptée la dernière jouée) provenant du talon
	 */
	public Collection<Carte> emptyPile() {
		List<Carte> cards = new ArrayList<Carte>(this.talon);
		this.talon.clear();
		return cards;
	}
	
	/**
	 * Méthode permettant spécifiant la façon dont s'affiche le talon
	 */
	@Override
	public String toString() {
		return "[Talon] " + talon.size() + " cartes ont été jouées";
	}

	/**
	 * Méthode permettant de récuperer le nombre de cartes contenues dans le talon
	 * @return Nombre de cartes présentes dans le talon
	 */
	public int size() {
		return this.talon.size();
	}
}
