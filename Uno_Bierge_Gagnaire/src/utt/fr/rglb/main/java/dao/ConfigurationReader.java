package utt.fr.rglb.main.java.dao;

import java.io.File;
import java.io.IOException;

import utt.fr.rglb.main.java.player.model.PlayersToCreate;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.Files;

/**
 * Classe permettant de récupérer des informations stockées dans un fichier 
 * </br>Encapsule les classes requises pour être capable de parser du JSON depuis le fichier
 */
public class ConfigurationReader {
	private JsonParser jsonParser;
	
	/* ========================================= CONSTRUCTOR ========================================= */
	
	/**
	 * Constructeur de parser de configuration
	 */
	public ConfigurationReader() {
		this.jsonParser = new JsonParser();
	}
	
	/* ========================================= FILE READING ========================================= */
	
	/**
	 * Méthode permettant de créer tous les joueurs (humains et/ou IA) depuis le fichier de configuration
	 * @param urlToFile String contenant le chemin du fichier
	 * @return PlayersToCreate Objet encapsulant les informations de tous les joueurs devant être créés
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
