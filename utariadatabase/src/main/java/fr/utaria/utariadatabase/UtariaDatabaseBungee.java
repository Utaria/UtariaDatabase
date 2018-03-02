package fr.utaria.utariadatabase;

import fr.utaria.utariadatabase.util.Configuration;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class UtariaDatabaseBungee extends Plugin implements UtariaDatabasePlugin {

	public void onEnable() {
		InstanceManager.useInstance(this);
		this.init();
	}

	public void onDisable() {
	}

	@Override
	public void log(Level logLevel, String message) {
		this.getLogger().log(logLevel, message);
	}

	@Override
	public void runTimerTask(Runnable runnable, int delay, int timer) {
		ProxyServer.getInstance().getScheduler().schedule(this, runnable, delay / 20, timer / 20, TimeUnit.SECONDS);
	}

	@Override
	public Configuration getConfiguration(File file) {
		try {
			return new Configuration(ConfigurationProvider.getProvider(YamlConfiguration.class).load(file), false);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
