package utt.fr.rglb.main.java.player.model;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;

import utt.fr.rglb.main.java.console.view.View;
import utt.fr.rglb.main.java.player.AI.DrawFirstCard;
import utt.fr.rglb.main.java.player.AI.DrawFromColorMajority;
import utt.fr.rglb.main.java.player.AI.DrawMostValuableCard;
import utt.fr.rglb.main.java.player.controller.PlayerControllerAI;
import utt.fr.rglb.main.java.player.controller.PlayerController;

/**
 * Classe englobant tous les joeuurs devant �tre cr��s et les informations qui leur sont associ�s
 */
public class PlayersToCreate {
	private List<PlayerStatus> playersAwaitingCreation;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	public PlayersToCreate() {
		this.playersAwaitingCreation = new ArrayList<PlayerStatus>();
	}
	
	/* ========================================= COLLECTION HANDLING ========================================= */
	
	/**
	 * M�thode permettant de v�rifier si le pseudo founi est d�j� pr�sent dans les joueurs � cr�er
	 * @param alias Pseudo dont on souhaite tester la pr�sence
	 * @return <code>TRUE</code> si le pseudo est pr�sent, <code>FALSE</code> sinon
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
	 * M�thode permettant d'ajouter un joueur humain dans la collection de ceux � cr�er
	 * @param playerNameFromInput Nom du joueur
	 */
	public void addHumanPlayer(String playerNameFromInput) {
		Preconditions.checkNotNull(playerNameFromInput,"[ERROR] Impossible to add a human player : provided name is null");
		this.playersAwaitingCreation.add(new PlayerStatus(playerNameFromInput));
	}

	/**
	 * M�thode permettant d'ajouter un joueur IA dans la collection de ceux � cr�er
	 * @param playerNameFromInput Nom du joueur
	 * @param strategyIndex Index correspondant � la strat�gie choisie
	 */
	public void addIAPlayerProvidingStrategyIndex(String playerNameFromInput, int strategyIndex) {
		Preconditions.checkNotNull(playerNameFromInput,"[ERROR] Impossible to create AI player : provided nickname is null");
		Preconditions.checkNotNull(strategyIndex,"[ERROR] Impossible to create AI player : provided difficulty is null");
		Preconditions.checkArgument(strategyIndex >= 0 && strategyIndex <= 2,"[ERROR] Impossible to create AI player : provided difficulty is invalid (must be between 0 and 2, was : " + strategyIndex + ")");
		if(strategyIndex == 0) {
			this.playersAwaitingCreation.add(new PlayerStatus(playerNameFromInput,new DrawFirstCard()));
		} else if(strategyIndex == 1) {
			this.playersAwaitingCreation.add(new PlayerStatus(playerNameFromInput,new DrawMostValuableCard()));
		} else {
			this.playersAwaitingCreation.add(new PlayerStatus(playerNameFromInput,new DrawFromColorMajority()));
		}
	}
	
	/* ========================================= PLAYER CREATION ========================================= */
	
	/**
	 * M�thode permettant de cr�er tous les joueurs � partir de leurs informations associ�s 
	 * @param consoleView Vue permettant d'afficher des informatios
	 * @return Une Collection de PlayerController correspondant � tous les joueurs devant �tre cr��s
	 */
	public List<PlayerController> createAllPlayersFromTheirRespectiveData(View consoleView) {
		Preconditions.checkNotNull(consoleView,"[ERROR] Impossible to create all players : provided view is null");
		List<PlayerController> players = new ArrayList<PlayerController>(); 
		for(PlayerStatus curentPlayer : this.playersAwaitingCreation) {
			players.add(createPlayerFrom(curentPlayer,consoleView));
		}
		return players;
	}

	/**
	 * M�thode priv�e permettant de cr�er un joueur � partir des informations qui lui sont associ�s
	 * @param curentPlayer Joueur actuel
	 * @param consoleView Vue permettant d'afficher des informations
	 * @return Le PlayerController associ� au joueur
	 */
	private PlayerController createPlayerFrom(PlayerStatus curentPlayer, View consoleView) {
		Preconditions.checkNotNull(curentPlayer,"[ERROR] Impossible to create all players : provided player data is null");
		Preconditions.checkNotNull(consoleView,"[ERROR] Impossible to create all players : provided view is null");
		if(curentPlayer.isHuman()) {
			return new PlayerController(curentPlayer.getAlias(), consoleView);
		} else {
			return new PlayerControllerAI(curentPlayer.getAlias(), consoleView, curentPlayer.getStrategy());
		}
	}
	
	/* ========================================= UTILS ========================================= */
	
	@Override
	public String toString() {
		return this.playersAwaitingCreation.toString();
	}
	
	/**
	 * M�thode permettant de r�cup�rer le nombre de joueurs � cr�er
	 * @return int correspondant au nombre de joueurs � cr�er
	 */
	public int size() {
		return this.playersAwaitingCreation.size();
	}
}
