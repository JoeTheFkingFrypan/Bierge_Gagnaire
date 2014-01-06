package utt.fr.rglb.main.java.cards.controller;

import java.io.Serializable;
import java.util.Collection;
import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.cards.model.basics.Color;
import utt.fr.rglb.main.java.game.model.GameFlag;

public abstract class AbstractCardsController implements Serializable {
	private static final long serialVersionUID = 1L;

	/* ========================================= CARD DRAW ========================================= */

	/**
	 * Méthode permettant de tirer un nombre défini de cartes
	 * @param count Nombre de carte à tirer
	 * @return Une collection de cartes contenant les n premières cartes de la pioche
	 */
	public abstract Collection<Card> drawCards(int count);

	/**
	 * Méthode permettant de tirer une unique carte
	 * @return La première carte de la pioche
	 */
	public abstract Card drawOneCard();

	/* ========================================= PLAY CARD ========================================= */

	/**
	 * Méthode permettant d'avoir un apperçu de la dernière carte jouée (sans la retirer du talon)
	 * @return La première carte du talon (la dernière ayant été jouée)
	 */
	public abstract Card showLastCardPlayed();

	/**
	 * Méthode permettant de jouer une carte et de renvoyer l'effet s'étant éventuellement déclenché
	 * @param chosenCard Carte choisie par le joueur
	 * @return Une valeur d'énumération correspondant à l'effet qui s'est délenché (ou NORMAL, s'il n'y en a pas eu)
	 */
	public abstract GameFlag playCard(Card chosenCard);

	/* ========================================= EFFECTS RELATED ========================================= */

	/**
	 * Méthode privée permettant de récupérer les effets ayant éventuellement été déclenché lors du jeu de la carte choisie
	 * @param chosenCard Carte choisie par le joueur
	 * @return Une valeur d'énumération correspondant à l'effet qui s'est délenché (ou NORMAL, s'il n'y en a pas eu)
	 */
	protected abstract GameFlag triggerItsEffectIfItHasOne(Card chosenCard);

	/**
	 * Méthode permettant de déclencher l'effet de la 1ère carte du talon
	 * @return L'effet associé à la 1ère carte (ou NORMAL, si la carte n'a pas d'effet)
	 */
	public abstract GameFlag drawFirstCardAndApplyItsEffect();

	/**
	 * Méthode permettant de déclencher l'effet de la 1ère carte du talon (dans le cas où un +4 a été tiré dès le départ)
	 * @return L'effet associé à la 1ère carte (ou NORMAL, si la carte n'a pas d'effet)
	 */
	public abstract GameFlag applyEffectFromAnotherFirstCard();

	/**
	 * Méthode permettant d'appliquer un effet à partir d'une carte donnée
	 * @param firstCard Carte dont l'effet doit être appliqué
	 * @return L'effet associé
	 */
	public abstract GameFlag applyEffectFromCardIfITHasOne(Card firstCard);

	/* ========================================= GLOBAL COLOR ========================================= */

	/**
	 * Méthode permettant de récupérer les informations nécessaires à la vérification de la compatibilité d'une carte
	 * @return Un bean comportant la carte dernièrement jouée (carte de référence) et la couleur globale
	 */
	public abstract CardsModelBean getReferences();

	/**
	 * Méthode permettant de définir la couleur globale (Couleur choisie par l'utilisateur après utilisation d'un joker)
	 * @param chosenColor Couleur choisie par l'utilisateur
	 */
	public abstract void setGlobalColor(Color chosenColor);

	/**
	 * Méthode privée permettant de récupérer la couleur globale (Couleur choisie par l'utilisateur après utilisation d'un joker)
	 * @return Couleur choisie par l'utilisateur
	 */
	public abstract Color getGlobalColor();

	/**
	 * Méthode permettant de ré-initialiser les cartes (talon et pioche)
	 */
	public abstract void resetCards();

	public abstract Card retrieveImageFromLastCardPlayed();

}