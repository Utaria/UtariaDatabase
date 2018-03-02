package fr.utaria.utariadatabase.util;

public class Configuration {

	private Object configObj;

	private boolean bukkitMode;

	public Configuration(Object configObj, boolean bukkitMode) {
		this.configObj = configObj;
		this.bukkitMode = bukkitMode;
	}

	public String getString(String path) {
		return (bukkitMode) ?
				((org.bukkit.configuration.file.YamlConfiguration) configObj).getString(path) :
				((net.md_5.bungee.config.Configuration) configObj).getString(path);
	}

	public int getInt(String path) {
		return (bukkitMode) ?
				((org.bukkit.configuration.file.YamlConfiguration) configObj).getInt(path) :
				((net.md_5.bungee.config.Configuration) configObj).getInt(path);
	}

	public boolean isConfigurationSection(String path) {
		return (bukkitMode) ?
				((org.bukkit.configuration.file.YamlConfiguration) configObj).isConfigurationSection(path) :
				((net.md_5.bungee.config.Configuration) configObj).getSection(path) != null;
	}

}
