package utt.fr.rglb.main.java.player.model;

import com.google.common.base.Preconditions;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import utt.fr.rglb.main.java.player.AI.DrawFirstCard;
import utt.fr.rglb.main.java.player.AI.DrawFromColorMajority;
import utt.fr.rglb.main.java.player.AI.DrawMostValuableCard;
import utt.fr.rglb.main.java.player.controller.PlayerControllerAIConsoleOriented;
import utt.fr.rglb.main.java.player.controller.PlayerControllerAIGraphicsOriented;
import utt.fr.rglb.main.java.player.controller.PlayerControllerConsoleOriented;
import utt.fr.rglb.main.java.player.controller.PlayerControllerGraphicsOriented;
import utt.fr.rglb.main.java.view.AbstractView;

/**
 * Classe englobant tous les joeuurs devant être créés et les informations qui leur sont associés
 */
public class PlayersToCreate {
	private List<PlayerStatus> playersAwaitingCreation;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	public PlayersToCreate() {
		this.playersAwaitingCreation = new ArrayList<PlayerStatus>();
	}
	
	/* ========================================= COLLECTION HANDLING ========================================= */
	
	/**
	 * Méthode permettant de vérifier si le pseudo founi est déjà présent dans les joueurs à créer
	 * @param alias Pseudo dont on souhaite tester la présence
	 * @return <code>TRUE</code> si le pseudo est présent, <code>FALSE</code> sinon
	 */
	public boolean contains(String alias) {
		Preconditions.checkNotNull(alias,"[ERROR] Impossible to verify if provided name has already been given : provided name is null");
		for(PlayerStatus ps : this.playersAwaitingCreation) {
			if(ps.getAlias().equals(alias)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Méthode permettant d'ajouter un joueur humain dans la collection de ceux à créer
	 * @param playerNameFromInput Nom du joueur
	 */
	public void addHumanPlayer(String playerNameFromInput) {
		Preconditions.checkNotNull(playerNameFromInput,"[ERROR] Impossible to add a human player : provided name is null");
		this.playersAwaitingCreation.add(new PlayerStatus(playerNameFromInput));
	}

	/**
	 * Méthode permettant d'ajouter un joueur IA dans la collection de ceux à créer
	 * @param playerNameFromInput Nom du joueur
	 * @param strategyIndex Index correspondant à la stratégie choisie
	 */
	public void addIAPlayerProvidingStrategyIndex(String playerNameFromInput, int strategyIndex) {
		Preconditions.checkNotNull(playerNameFromInput,"[ERROR] Impossible to create AI player : provided nickname is null");
		Preconditions.checkNotNull(strategyIndex,"[ERROR] Impossible to create AI player : provided difficulty is null");
		Preconditions.checkArgument(strategyIndex >= 0 && strategyIndex <= 2,"[ERROR] Impossible to create AI player : provided difficulty is invalid (must be between 0 and 2, was : " + strategyIndex + ")");
		if(strategyIndex == 0) {
			this.playersAwaitingCreation.add(new PlayerStatus(playerNameFromInput,new DrawFirstCard()));
		} else if(strategyIndex == 1) {
			this.playersAwaitingCreation.add(new PlayerStatus(playerNameFromInput,new DrawFromColorMajority()));
		} else {
			this.playersAwaitingCreation.add(new PlayerStatus(playerNameFromInput,new DrawMostValuableCard()));
		}
	}
	
	/* ========================================= PLAYER CREATION - CONSOLE VIEW ========================================= */
	
	/**
	 * Méthode permettant de créer tous les joueurs à partir de leurs informations associés 
	 * @param consoleView Vue permettant d'afficher des informatios
	 * @return Une Collection de PlayerController correspondant à tous les joueurs devant être créés
	 */
	public List<PlayerControllerConsoleOriented> createAllConsolePlayersFromTheirRespectiveData(AbstractView consoleView, BufferedReader inputStream) {
		Preconditions.checkNotNull(consoleView,"[ERROR] Impossible to create all players : provided view is null");
		List<PlayerControllerConsoleOriented> players = new ArrayList<PlayerControllerConsoleOriented>(); 
		for(PlayerStatus curentPlayer : this.playersAwaitingCreation) {
			players.add(createConsolePlayerFrom(curentPlayer,consoleView,inputStream));
		}
		return players;
	}

	/**
	 * Méthode permettant d'ajouter un nouveau joueur console à la liste des joueurs à créer
	 * @param curentPlayer Information du joueur
	 * @param consoleView Vue permettant d'afficher des informatios
	 * @return PlayerControllerGraphicsOriented Joueur console correspondant aux données fournies
	 */
	private PlayerControllerConsoleOriented createConsolePlayerFrom(PlayerStatus curentPlayer, AbstractView consoleView, BufferedReader inputStream) {
		Preconditions.checkNotNull(curentPlayer,"[ERROR] Impossible to create all players : provided player data is null");
		Preconditions.checkNotNull(consoleView,"[ERROR] Impossible to create all players : provided view is null");
		if(curentPlayer.isHuman()) {
			return new PlayerControllerConsoleOriented(curentPlayer.getAlias(), consoleView, inputStream);
		} else {
			return new PlayerControllerAIConsoleOriented(curentPlayer.getAlias(), consoleView, curentPlayer.getStrategy(),inputStream);
		}
	}
	
	/* ========================================= PLAYER CREATION - GRAPHICS VIEW ========================================= */
	
	/**
	 * Méthode permettant de créer tous les joueurs à partir de leurs informations associés 
	 * @param consoleView Vue permettant d'afficher des informatios
	 * @return Une Collection de PlayerController correspondant à tous les joueurs devant être créés
	 */
	public List<PlayerControllerGraphicsOriented> createAllGraphicsPlayersFromTheirRespectiveData(AbstractView consoleView) {
		Preconditions.checkNotNull(consoleView,"[ERROR] Impossible to create all players : provided view is null");
		List<PlayerControllerGraphicsOriented> players = new ArrayList<PlayerControllerGraphicsOriented>(); 
		for(PlayerStatus curentPlayer : this.playersAwaitingCreation) {
			players.add(createGraphicsPlayerFrom(curentPlayer,consoleView));
		}
		return players;
	}
	
	/**
	 * Méthode permettant d'ajouter un nouveau joueur graphique à la liste des joueurs à créer
	 * @param curentPlayer Information du joueur
	 * @param consoleView Vue permettant d'afficher des informatios
	 * @return PlayerControllerGraphicsOriented Joueur graphique correspondant aux données fournies
	 */
	private PlayerControllerGraphicsOriented createGraphicsPlayerFrom(PlayerStatus curentPlayer, AbstractView consoleView) {
		Preconditions.checkNotNull(curentPlayer,"[ERROR] Impossible to create all players : provided player data is null");
		Preconditions.checkNotNull(consoleView,"[ERROR] Impossible to create all players : provided view is null");
		if(curentPlayer.isHuman()) {
			return new PlayerControllerGraphicsOriented(curentPlayer.getAlias(), consoleView);
		} else {
			return new PlayerControllerAIGraphicsOriented(curentPlayer.getAlias(), consoleView, curentPlayer.getStrategy());
		}
	}
	
	
	/* ========================================= UTILS ========================================= */
	
	@Override
	public String toString() {
		return this.playersAwaitingCreation.toString();
	}
	
	/**
	 * Méthode permettant de récupérer le nombre de joueurs à créer
	 * @return int correspondant au nombre de joueurs à créer
	 */
	public int size() {
		return this.playersAwaitingCreation.size();
	}
	
	/**
	 * Méthode permettant de récupérer tous les joueurs
	 * @return Collection 
	 */
	public List<PlayerStatus> getAllPlayers() {
		return this.playersAwaitingCreation;
	}
}
