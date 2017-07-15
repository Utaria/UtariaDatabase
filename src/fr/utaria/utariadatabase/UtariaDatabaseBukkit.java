package fr.utaria.utariadatabase;

import com.google.common.collect.ImmutableMap;
import fr.utaria.utariadatabase.database.DatabaseManager;
import fr.utaria.utariadatabase.database.Test;
import fr.utaria.utariadatabase.util.APIReader;
import fr.utaria.utariadatabase.util.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class UtariaDatabaseBukkit extends JavaPlugin {

	private static UtariaDatabaseBukkit instance;


	public void onEnable() {
		instance = this;

		this.initConfig();

		DatabaseManager.registerDatabase("global");
		Test.test();
	}
	public void onDisable() {

	}


	public void initConfig() {
		FileConfiguration config = this.getConfig();

		if (!config.isString("environment"))
			config.set("environment", Config.DEFAULT_ENVIRONMENT);

		Config.environment = config.getString("environment");

		try {
			Config.remoteSSHUrl = (String) APIReader.get("database", "url", ImmutableMap.of(
					"token", Config.API_TOKEN,
					"environment", Config.environment
			));

			this.getLogger().log(Level.INFO, "Utilisation de l'environement '" + Config.environment + "'.");
		} catch (Exception e) {
			e.printStackTrace();
			this.getLogger().log(Level.SEVERE, "Impossible de récupérer l'adresse hôte pour l'environnement " + Config.environment + " !");
		}

		this.saveConfig();
	}


	public static UtariaDatabaseBukkit getInstance() {
		return instance;
	}

}
