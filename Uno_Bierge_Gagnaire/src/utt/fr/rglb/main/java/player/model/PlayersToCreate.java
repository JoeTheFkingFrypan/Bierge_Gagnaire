package utt.fr.rglb.main.java.player.model;

import java.util.ArrayList;
import java.util.List;

import utt.fr.rglb.main.java.console.view.View;
import utt.fr.rglb.main.java.player.AI.DrawFirstCard;
import utt.fr.rglb.main.java.player.AI.DrawFromColorMajority;
import utt.fr.rglb.main.java.player.AI.DrawMostValuableCard;
import utt.fr.rglb.main.java.player.controller.PlayerControllerAI;
import utt.fr.rglb.main.java.player.controller.PlayerController;

public class PlayersToCreate {
	private List<PlayerStatus> playersAwaitingCreation;
	
	public PlayersToCreate() {
		this.playersAwaitingCreation = new ArrayList<PlayerStatus>();
	}
	
	public boolean contains(String alias) {
		for(PlayerStatus ps : this.playersAwaitingCreation) {
			if(ps.getAlias().equals(alias)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return this.playersAwaitingCreation.toString();
	}

	public void addHumanPlayer(String playerNameFromInput) {
		this.playersAwaitingCreation.add(new PlayerStatus(playerNameFromInput));
	}

	public void addIAPlayerProvidingStrategyIndex(String playerNameFromInput, int strategyIndex) {
		if(strategyIndex == 0) {
			this.playersAwaitingCreation.add(new PlayerStatus(playerNameFromInput,new DrawFirstCard()));
		} else if(strategyIndex == 1) {
			this.playersAwaitingCreation.add(new PlayerStatus(playerNameFromInput,new DrawMostValuableCard()));
		} else {
			this.playersAwaitingCreation.add(new PlayerStatus(playerNameFromInput,new DrawFromColorMajority()));
		}
	}
	
	public List<PlayerController> createAllPlayersFromTheirRespectiveData(View consoleView) {
		List<PlayerController> players = new ArrayList<PlayerController>(); 
		for(PlayerStatus curentPlayer : this.playersAwaitingCreation) {
			players.add(createPlayerFrom(curentPlayer,consoleView));
		}
		return players;
	}

	private PlayerController createPlayerFrom(PlayerStatus curentPlayer, View consoleView) {
		if(curentPlayer.isHuman()) {
			return new PlayerController(curentPlayer.getAlias(), consoleView);
		} else {
			return new PlayerControllerAI(curentPlayer.getAlias(), consoleView, curentPlayer.getStrategy());
		}
	}
}
