package fr.utaria.utariadatabase;

import fr.utaria.utariadatabase.util.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

public class UtariaDatabaseBukkit extends JavaPlugin implements UtariaDatabasePlugin {

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
		Bukkit.getScheduler().runTaskTimerAsynchronously(this, runnable, delay, timer);
	}

	@Override
	public Configuration getConfiguration(File file) {
		return new Configuration(YamlConfiguration.loadConfiguration(file), true);
	}

}
