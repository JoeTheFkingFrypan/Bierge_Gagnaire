package utt.fr.rglb.main.java.dao;

import java.util.ArrayList;
import java.util.Map;

import utt.fr.rglb.main.java.player.model.PlayersToCreate;

import com.google.gson.Gson;

public class JsonParser {

	public PlayersToCreate createPlayersFromConfigurationFile(String jsonText) {
		PlayersToCreate playersToCreate = new PlayersToCreate();
		Map<?,?> jsonRootObject = new Gson().fromJson(jsonText,Map.class);
		ArrayList<?> players = (ArrayList<?>) jsonRootObject.get("players");
		if(players.size() < 2 ||  players.size() > 7) {
			throw new ConfigFileDaoException("[ERROR] Configuration settings are invalid : there must be between 2 and 7 players --You currently have " + players.size() + " players..");
		}
		for(int i=0; i<players.size(); i++) {
			parseAnotherPlayer(players,i,playersToCreate);
		}
		return playersToCreate;
	}

	private void parseAnotherPlayer(ArrayList<?> players, int rank, PlayersToCreate playersToCreate) {
		Map<?,?> infoFromPlayer = (Map<?,?>) players.get(rank);
		String nickname = (String) infoFromPlayer.get("nickname");
		if(playersToCreate.contains(nickname)) {
			throw new ConfigFileDaoException("[ERROR] Configuration settings are invalid : each player must have a different name (currently, at least 2 of them are named : " + nickname + ")");
		}
		String status = (String) infoFromPlayer.get("status");
		if(status.equals("human")) {
			playersToCreate.addHumanPlayer(nickname);
		} else {
			String strategy = (String) infoFromPlayer.get("difficultyLevel [0-2]");
			int strategyIndex = Integer.parseInt(strategy);
			playersToCreate.addIAPlayerProvidingStrategyIndex(nickname,strategyIndex);
		}
	}
}
