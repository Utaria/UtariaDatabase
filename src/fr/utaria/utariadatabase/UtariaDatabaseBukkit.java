package fr.utaria.utariadatabase;

import fr.utaria.utariadatabase.util.APIReader;
import fr.utaria.utariadatabase.util.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Level;

public class UtariaDatabaseBukkit extends JavaPlugin implements UtariaDatabasePlugin {

	public void onEnable() {
		InstanceManager.useInstance(this);
		this.init();
	}
	public void onDisable() {

	}


	@Override
	public void init() {
		FileConfiguration config = this.getConfig();

		if (!config.isString("environment"))
			config.set("environment", Config.DEFAULT_ENVIRONMENT);

		Config.environment = config.getString("environment");

		try {
			Config.remoteSSHUrl = (String) APIReader.get("database", "url", new HashMap<String, String>() {{
				put("token", Config.API_TOKEN);
				put("environment", Config.environment);
			}});

			this.getLogger().log(Level.INFO, "Utilisation de l'environement '" + Config.environment + "'.");
		} catch (Exception e) {
			e.printStackTrace();
			this.getLogger().log(Level.SEVERE, "Impossible de récupérer l'adresse hôte pour l'environnement " + Config.environment + " !");
		}

		this.saveConfig();
	}

	@Override
	public void log(Level logLevel, String message) {
		this.getLogger().log(logLevel, message);
	}


}
