package utt.fr.rglb.main.java.dao;

import java.io.File;
import java.io.IOException;

import utt.fr.rglb.main.java.player.model.PlayersToCreate;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.io.Files;

public class ConfigurationReader {
	private JsonParser jsonParser;
	
	public ConfigurationReader() {
		this.jsonParser = new JsonParser();
	}
	
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
