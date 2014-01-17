package utt.fr.rglb.main.java.view.graphics;

import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;

/**
 * Classe correspondant aux références requises pour l'accomplissement d'un tour
 */
public class GraphicsReferences {
	private int indexFromActivePlayer;
	private CardsModelBean cardReferences;
	private boolean hasDrawnOneTime;
	private boolean hasDrawnTwoTimes;
	private Card firstCardDrawn;
	private Card secondCardDrawn;

	/* ========================================= CONSTRUCTOR ========================================= */
	
	public GraphicsReferences(CardsModelBean cardReferences, int indexFromActivePlayer) {
		this.cardReferences = cardReferences;
		this.indexFromActivePlayer = indexFromActivePlayer;
		this.hasDrawnOneTime = false;
		this.hasDrawnTwoTimes = false;
	}

	/* ========================================= SETTERS ========================================= */
	
	/**
	 * Méthode permettant de définir si le joueur a eu besoin de piocher une fois
	 * @param firstCardDrawn Carte piochée cette fois là 
	 */
	public void setNeedOfDrawingOneTime(Card firstCardDrawn) {
		this.hasDrawnOneTime = true;
		this.firstCardDrawn = firstCardDrawn;
	}
	
	/**
	 * Méthode permettant de définir si le joueur a eu besoin de piocher une deuxième fois
	 * @param firstCardDrawn Carte piochée cette fois là 
	 */
	public void setNeedOfDrawingTwoTimes(Card firstCardDrawn, Card secondCardDrawn) {
		this.hasDrawnOneTime = true;
		this.hasDrawnTwoTimes = true;
		this.firstCardDrawn = firstCardDrawn;
		this.secondCardDrawn = secondCardDrawn;
	}

	/* ========================================= GETTERS ========================================= */
	
	/**
	 * Méthode permettant de récuperer l'index du joueur actuel
	 * @return int correspondant à l'index du joueur actuel 
	 */
	public int getIndexFromActivePlayer() {
		return indexFromActivePlayer;
	}

	/**
	 * Méthode permettant de déterminer si le joueur a piocher une fois
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean hasDrawnOneTime() {
		return this.hasDrawnOneTime;
	}

	/**
	 * Méthode permettant de récupérer la 1ère carte piochée
	 * @return Carte correspondante
	 */
	public Card getFirstCardDrawn() {
		return this.firstCardDrawn;
	}

	/**
	 * Méthode permettant de récupérer la compatibilité de la première carte piochée par rapport à la référence 
	 * @return <code>TRUE</code> si la carte est compatible, <code>FALSE</code> sinon
	 */
	public boolean getCompatibilityFromFirstCard() {
		return this.cardReferences.isCompatibleWith(this.firstCardDrawn);
	}
	
	/**
	 * Méthode permettant de déterminer si le joueur a piocher une deuxième fois
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean hasDrawnTwoTimes() {
		return this.hasDrawnTwoTimes;
	}

	/**
	 * Méthode permettant de récupérer la 2ème carte piochée
	 * @return Carte correspondante
	 */
	public Card getSecondCardDrawn() {
		return this.secondCardDrawn;
	}

	/**
	 * Méthode permettant de récupérer la compatibilité de la carte piochée par rapport à la référence
	 * @return <code>TRUE</code> si les 2 cartes sont compatibles, <code>FALSE</code> sinon
	 */
	public boolean getCompatibilityWith(Card cardDrawn) {
		return this.cardReferences.isCompatibleWith(cardDrawn);
	}

	/**
	 * Méthode permettant de déterminer si le joueur ne dispose pas de cartes jouables
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code> sinon
	 */
	public boolean hasNoPlayableCards() {
		return !this.cardReferences.isCompatibleWith(this.secondCardDrawn);
	}
}
