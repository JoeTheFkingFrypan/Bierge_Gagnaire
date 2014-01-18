package utt.fr.rglb.main.java.dao;

import java.util.ArrayList;
import java.util.Map;

import utt.fr.rglb.main.java.player.model.PlayersToCreate;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;

/**
 * Classe permettant de parser des informations depuis un texte formatté en JSON
 */
public class JsonParser {

	/* ========================================= HIGH LEVEL ========================================= */

	/**
	 * Méthode permettant de récupérer tous les joueurs à créer en parsant le fichier de configuration
	 * @param jsonText Texte au format JSON
	 * @return Objet encapsulant le nom de tous les joueurs et leur statut (humain, IA) associé, avec leur stratégie, pour peu qu'il s'agisse d'une IA
	 */
	public PlayersToCreate createPlayersFromConfigurationFile(String jsonText) {
		Preconditions.checkNotNull(jsonText,"[ERROR] Impossible to create players from configuration file : provided string is null");
		Preconditions.checkArgument(jsonText.length() > 0,"[ERROR] Impossible to create players from configuration file : provided file is empty");
		try {
			PlayersToCreate playersToCreate = new PlayersToCreate();
			Map<?,?> jsonRootObject = new Gson().fromJson(jsonText,Map.class);
			ArrayList<?> players = (ArrayList<?>) jsonRootObject.get("players");
			if(players.size() < 2 ||  players.size() > 7) {
				throw new ConfigFileDaoException("[ERROR] Configuration file is invalid : there must be between 2 and 7 players --You currently have " + players.size() + " players..");
			}
			for(int i=0; i<players.size(); i++) {
				parseAnotherPlayer(players,i,playersToCreate);
			}
			return playersToCreate;
		} catch (Exception e) {
			throw new ConfigFileDaoException("[ERROR] Configuration file is invalid : please refer to the readMe to see how to fill it properly",e);
		}
	}

	/* ========================================= LOW LEVEL ========================================= */

	/**
	 * Méthode permettant de récupérer toutes les informations d'un unique joueur depuis le fichier JSON, et de l'ajouter dans la collection de joueurs à créer
	 * @param players Collection provenant du parsing du fichier
	 * @param rank Indice du joueur en cours dans la collection
	 * @param playersToCreate Objet encapsulant le nom de tous les joueurs et leur statut (humain, IA) associé, avec leur stratégie, pour peu qu'il s'agisse d'une IA
	 */
	private void parseAnotherPlayer(ArrayList<?> players, int rank, PlayersToCreate playersToCreate) {
		Preconditions.checkNotNull(players,"[ERROR] Impossible to read configuration file : provided array of players is null");
		Preconditions.checkNotNull(playersToCreate,"[ERROR] Impossible to read configuration file : provided PlayersToCreate is null");
		Map<?,?> infoFromPlayer = (Map<?,?>) players.get(rank);
		try {
			String nickname = (String) infoFromPlayer.get("nickname");
			if(playersToCreate.contains(nickname) || nickname.equals("")) {
				throw new ConfigFileDaoException("[ERROR] Configuration settings are invalid : each player must have a different name AND must not be empty (" + nickname + " is incorrect)");
			}
			String status = (String) infoFromPlayer.get("status");
			if(status.equals("human")) {
				playersToCreate.addHumanPlayer(nickname);
			} else {
				String strategy = (String) infoFromPlayer.get("difficultyLevel [0-2]");
				int strategyIndex = Integer.parseInt(strategy);
				playersToCreate.addIAPlayerProvidingStrategyIndex(nickname,strategyIndex);
			}
		} catch (Exception e) {
			throw new ConfigFileDaoException("[ERROR] Configuration file is invalid : please refer to the readMe to see how to fill it properly",e);
		}
	}
}
