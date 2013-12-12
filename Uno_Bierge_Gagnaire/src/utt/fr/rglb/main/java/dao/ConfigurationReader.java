package utt.fr.rglb.main.java.dao;

import java.io.File;
import java.io.IOException;

import utt.fr.rglb.main.java.player.model.PlayersToCreate;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.Files;

/**
 * Classe permettant de r�cup�rer des informations stock�es dans un fichier 
 * Encapsule les classes requises pour �tre capable de parser du JSON depuis le fichier
 */
public class ConfigurationReader {
	private JsonParser jsonParser;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	public ConfigurationReader() {
		this.jsonParser = new JsonParser();
	}
	
	/* ========================================= FILE READING ========================================= */
	
	/**
	 * M�thode permettant de cr�er tous les joueurs (humains et/ou IA) depuis le fichier de configuration
	 * @param urlToFile String contenant le chemin du fichier
	 * @return 
	 */
	public PlayersToCreate readConfigurationAt(String urlToFile) {
		Preconditions.checkNotNull(urlToFile,"[ERROR] Impossible to read configuration file : provided URL is null");
		Preconditions.checkArgument(urlToFile.length() > 0,"[ERROR] Impossible to read configuration file : provided URL is empty");
		try {
			String jsonText = Files.toString(new File(urlToFile), Charsets.UTF_8);
			return this.jsonParser.createPlayersFromConfigurationFile(jsonText);
		} catch (IOException e) {
			throw new ConfigFileDaoException("[ERROR] An error occured while opening/reading configuration file ",e);
		}
	}
}
