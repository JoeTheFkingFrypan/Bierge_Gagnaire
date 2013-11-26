package main.modele.talonModele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import main.modele.carteModele.Carte;

/**
 * Classe permettant de r�ception les cartes jou�es (avec comparaison par rapport � la derni�re re�ue).
 * Sert �galement � reconstituer la pioche lorsque cette derni�re est vide.
 */
public class Talon {
	private final LinkedList<Carte> talon;
	
	public Talon() {
		this.talon = new LinkedList<Carte>();
	}
	
	/**
	 * M�thode permettant de jouer une carte
	 * @param c Carte � jouer
	 */
	public void playCard(Carte c) {
		//TODO: handle card playability
		//System.out.println("[CARTE JOUEE] " + c);
		talon.addFirst(c);
	}
	
	/**
	 * M�thode permettant de vider le talon de ses cartes (sauf de la derni�re jou�e) et de les transf�rer
	 * @return L'ensemble des cartes (except�e la derni�re jou�e) provenant du talon
	 */
	public Collection<Carte> emptyPile() {
		List<Carte> cards = new ArrayList<Carte>(this.talon);
		this.talon.clear();
		return cards;
	}
	
	/**
	 * M�thode permettant sp�cifiant la fa�on dont s'affiche le talon
	 */
	@Override
	public String toString() {
		return "[Talon] " + talon.size() + " cartes ont �t� jou�es";
	}

	/**
	 * M�thode permettant de r�cuperer le nombre de cartes contenues dans le talon
	 * @return Nombre de cartes pr�sentes dans le talon
	 */
	public int size() {
		return this.talon.size();
	}
}
