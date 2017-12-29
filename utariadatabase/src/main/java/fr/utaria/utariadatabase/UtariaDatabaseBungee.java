package fr.utaria.utariadatabase;

import fr.utaria.utariadatabase.database.DatabaseManager;
import fr.utaria.utariadatabase.util.APIReader;
import fr.utaria.utariadatabase.util.Config;
import fr.utaria.utariadatabase.util.ConfigTableAccessor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class UtariaDatabaseBungee extends Plugin implements UtariaDatabasePlugin {


	public void onEnable() {
		InstanceManager.useInstance(this);
		this.init();
	}
	public void onDisable() {}


	@Override
	public void log(Level logLevel, String message) {
		this.getLogger().log(logLevel, message);
	}

	@Override
	public void init() {
		try {
			File file = new File(getDataFolder(), "config.yml");
			if (!file.exists()) {
				getDataFolder().mkdirs();
				file.createNewFile();
			}

			Configuration configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

			if (configuration.getString("environment").isEmpty())
				configuration.set("environment", Config.DEFAULT_ENVIRONMENT);

			Config.environment = configuration.getString("environment");

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

			ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		DatabaseManager.registerDatabase("global");
		ConfigTableAccessor.init(this);
	}

	@Override
	public void runTimerTask(Runnable runnable, int delay, int timer) {
		ProxyServer.getInstance().getScheduler().schedule(this, runnable, delay/20, timer/20, TimeUnit.SECONDS);
	}

}
