package utt.fr.rglb.main.java.view.graphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.animation.SequentialTransition;
import com.google.common.base.Preconditions;

import utt.fr.rglb.main.java.cards.model.CardsModelBean;
import utt.fr.rglb.main.java.cards.model.basics.Card;
import utt.fr.rglb.main.java.player.controller.PlayerControllerGraphicsOriented;
import utt.fr.rglb.main.java.view.graphics.fxml.FXMLControllerGameScreen;

/**
 * Classe englobant tous les joueurs, le gestionnaire FXML, et l'index correspondant au joueur actuel
 */
public class GraphicsPlayers {
	private FXMLControllerGameScreen fxmlControllerGameScreen;
	private List<PlayerControllerGraphicsOriented> players;
	private int indexFromActivePlayer; 
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	public GraphicsPlayers(List<PlayerControllerGraphicsOriented> players, FXMLControllerGameScreen fxmlControllerGameScreen) {
		this.players = players;
		this.indexFromActivePlayer = 0;
		this.fxmlControllerGameScreen = fxmlControllerGameScreen;
	}
	
	/* ========================================= PLAYER ========================================= */
	
	/**
	 * Méthode permettant de récupérer le pseudo du joueur à l'index spécifié
	 * @param playerNumber Index du joueur
	 * @return String correspondant à son pseudo
	 */
	public String getAliasFromPlayerNumber(int playerNumber) {
		Preconditions.checkArgument(playerNumber >= 0 && playerNumber < this.players.size(),"[ERROR] player number is invalid");
		return this.players.get(playerNumber).getAlias();
	}
	
	/**
	 * Méthode permettant de récupérer le pseudo du joueur actif
	 * @return String correspondant à son pseudo
	 */
	public String getAliasFromActivePlayer() {
		return this.players.get(this.indexFromActivePlayer).getAlias();
	}
	
	/**
	 * Méthode permettant de définir quel est le joueur actif
	 * @param indexFromActivePlayer Index correspondant au joueur actif
	 */
	public void setActivePlayer(int indexFromActivePlayer) {
		Preconditions.checkArgument(indexFromActivePlayer >= 0 && indexFromActivePlayer < this.players.size(),"[ERROR] player number is invalid");
		this.indexFromActivePlayer = indexFromActivePlayer;
	}
	
	/* ========================================= FIRST TURN ========================================= */

	/**
	 * Méthode permettant de déterminer si le joueur INITIAL doit piocher
	 * @param references Reférences de jeu (Carte retournée + éventuelle couleur globale)
	 * @return <code>TRUE</code> si le joueur doit piocher, <code>FALSE</code> sinon
	 */
	public boolean initialPlayerNeedsToDraw(CardsModelBean references) {
		Collection<Card> cardsFromPlayer = this.players.get(this.indexFromActivePlayer).getCardsInHand();
		return !references.isCompatibleWith(cardsFromPlayer);
	}
	
	/* ========================================= CARDS ========================================= */
	
	/**
	 * Méthode permettant à un joueur de jouer une carte, en mettant à jour la compatibilité des autres cartes de tous les joueurs
	 * @param cardIndex Index correspondant à la carte choisie
	 * @param thisImageView ImageView ayant été sélectionnée
	 * @return Card correspondant à la carte choisie visuellement
	 */
	public Card chooseCardFromActivePlayerAt(int cardIndex, CustomImageView thisImageView) {
		Card choosenCard = chooseCardFromPlayer(cardIndex,this.indexFromActivePlayer, thisImageView);
		for(PlayerControllerGraphicsOriented currentPlayer : this.players) {
			currentPlayer.updateCardsCompatibilityAndIndex(choosenCard);
		}
		return choosenCard;
	}

	/**
	 * Méthode permettant à un joueur de jouer une carte
	 * @param cardIndex Index correspondant à la carte choisie
	 * @param playerNumber Index correspondant au joueur 
	 * @param thisImageView ImageView ayant été sélectionnée
	 * @return Card carte choisie
	 */
	private Card chooseCardFromPlayer(int cardIndex, int playerNumber, CustomImageView thisImageView) {
		PlayerControllerGraphicsOriented player = this.players.get(playerNumber);
		Card card = player.playCard(cardIndex,thisImageView);
		return card;
	}
	
	/**
	 * Méthode permettant d'ajouter une collection de cartes à un joueur
	 * @param playerIndex Index correspondant au joueur 
	 * @param cardsDrawn Collection de cartes piochées
	 * @param references Données de références
	 * @return Collection de CustomImageView associée aux images piochées
	 */
	public List<CustomImageView> addCardToPlayer(int playerIndex, Collection<Card> cardsDrawn, CardsModelBean references) {
		Preconditions.checkArgument(playerIndex >= 0 && playerIndex < this.players.size(),"[ERROR] player number is invalid");
		PlayerControllerGraphicsOriented currentPlayer = this.players.get(playerIndex);
		currentPlayer.pickUpCards(cardsDrawn);
		return getDisplayableCardsFromPlayer(playerIndex,references);
	}
	
	/**
	 * Méthode permettant d'ajouter une carte à un joueur
	 * @param playerIndex Index correspondant au joueur 
	 * @param cardDrawn carte piochée
	 * @param references Données de références
	 * @return Collection de CustomImageView associée aux images piochées
	 */
	public List<CustomImageView> addCardToPlayer(int playerIndex, Card cardDrawn, CardsModelBean references) {
		Preconditions.checkArgument(playerIndex >= 0 && playerIndex < this.players.size(),"[ERROR] player number is invalid");
		PlayerControllerGraphicsOriented currentPlayer = this.players.get(playerIndex);
		currentPlayer.pickUpOneCard(cardDrawn);
		return getDisplayableCardsFromPlayer(playerIndex,references);
	}
	
	/**
	 * Méthode permettant d'ajouter une carte à un joueur
	 * @param playerIndex Index correspondant au joueur 
	 * @param firstCardDrawn Carte piochée
	 * @param imageView Image correspondant à la carte piochée
	 */
	public void addCardToPlayer(int playerIndex, Card firstCardDrawn, CustomImageView imageView) {
		Preconditions.checkArgument(playerIndex >= 0 && playerIndex < this.players.size(),"[ERROR] player number is invalid");
		PlayerControllerGraphicsOriented currentPlayer = this.players.get(playerIndex);
		currentPlayer.pickUpOneCard(firstCardDrawn);
		currentPlayer.addCustomImageView(imageView);
	}
	
	/**
	 * Méthode permettant de récupérer l'idex de la prochaine carte
	 * @param playerNumber Index du joueur
	 * @return int Numéro correspondant
	 */
	public int findNextCardIndex(int playerNumber) {
		Preconditions.checkArgument(playerNumber >= 0 && playerNumber < this.players.size(),"[ERROR] player number is invalid");
		PlayerControllerGraphicsOriented currentPlayer = this.players.get(playerNumber);
		return currentPlayer.getNumberOfCardsInHand();
	}
	
	/* ========================================= UTILS ========================================= */
	
	/**
	 * Méthode de récupérer l'ensemble des images associées aux cartes du joueur
	 * @param playerIndex Numéro du joueur
	 * @param references Références de jeu 
	 * @return Collection de CustomImageView correspondante
	 */
	public List<CustomImageView> getDisplayableCardsFromPlayer(int playerIndex, CardsModelBean references) {
		Preconditions.checkArgument(playerIndex >= 0 && playerIndex < this.players.size(),"[ERROR] player number is invalid");
		List<CustomImageView> displayableCards = new ArrayList<CustomImageView>();
		int currentCardIndex = 0;
		PlayerControllerGraphicsOriented currentPlayer = this.players.get(playerIndex);
		for(Card card : currentPlayer.getCardsInHand()) {
			displayableCards.add(new CustomImageView(card, currentCardIndex, references.isCompatibleWith(card),this.fxmlControllerGameScreen));
			currentCardIndex++;
		}
		currentPlayer.setDisplayableCards(displayableCards);
		return displayableCards;
	}
	
	/**
	 * Méthode permettant de créer l'animation séquentielle basée sur toutes les cartes en main du joueur actif
	 * @return Animation séquentielle
	 */
	public SequentialTransition generateEffectFromActivePlayer() {
		return createCardAnimationFromPlayer(this.indexFromActivePlayer);
	}
	
	/**
	 * Méthode permettant de créer l'animation séquentielle basée sur toutes les cartes en main
	 * @param playerIndex Numéro du joueur
	 * @return Animation séquentielle associée
	 */
	public SequentialTransition generateEffectFromPlayer(int playerIndex) {
		Preconditions.checkArgument(playerIndex >= 0 && playerIndex < this.players.size(),"[ERROR] player number is invalid");
		return createCardAnimationFromPlayer(playerIndex);
	}

	/**
	 * Méthode permettant de créer l'animation séquentielle basée sur toutes les cartes en main
	 * @param playerIndex Numéro du joueur
	 * @return Animation séquentielle associée
	 */
	private SequentialTransition createCardAnimationFromPlayer(int playerIndex) {
		Preconditions.checkArgument(playerIndex >= 0 && playerIndex < this.players.size(),"[ERROR] player number is invalid");
		PlayerControllerGraphicsOriented currentPlayer = this.players.get(playerIndex);
		return currentPlayer.generateEffectFromDisplayableCards();
	}

	/**
	 * Méthode permettant de déterminer si le joueur actif possède encore des cartes jouables
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code>
	 */
	public boolean findIfActivePlayerStillHasCards() {
		PlayerControllerGraphicsOriented player = this.players.get(indexFromActivePlayer);
		return player.stillHasCards();
	}
	
	/**
	 * Méthode permettant de déterminer si le joueur avait le droit d'annoncer UNO
	 * @return <code>TRUE</code> si c'est le cas, <code>FALSE</code>
	 */
	public boolean deservesTheRightToAnnounceUno() {
		PlayerControllerGraphicsOriented player = this.players.get(indexFromActivePlayer);
		return player.deservesTheRightToAnnounceUno();
	}
	
	/**
	 * Méthode permettant de récupérer l'index du joueur actif
	 * @return int correspondat à l'index du joueur actif
	 */
	public int getIndexFromActivePlayer() {
		return this.indexFromActivePlayer;
	}
}
