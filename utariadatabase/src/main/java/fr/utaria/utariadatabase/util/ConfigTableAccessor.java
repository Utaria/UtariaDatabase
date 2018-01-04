package fr.utaria.utariadatabase.util;

import fr.utaria.utariadatabase.UtariaDatabasePlugin;
import fr.utaria.utariadatabase.database.DatabaseAccessor;
import fr.utaria.utariadatabase.result.DatabaseSet;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigTableAccessor extends DatabaseAccessor implements Runnable {

	private static ConfigTableAccessor instance;

	private ConcurrentHashMap<String, String> datas;

	private ConfigTableAccessor(UtariaDatabasePlugin plugin) {
		super("global");

		this.datas = new ConcurrentHashMap<>();

		plugin.runTimerTask(this, 200, 200);
		this.run();
	}

	@Override
	public void run() {
		List<DatabaseSet> sets = this.getDB().select("`key`", "value").from("config").findAll();

		for (DatabaseSet set : sets)
			this.datas.put(set.getString("key"), set.getString("value"));
	}

	public static void init(UtariaDatabasePlugin databasePlugin) {
		if (instance != null) return;
		instance = new ConfigTableAccessor(databasePlugin);
	}

	public static Integer getInteger(String name) {
		String obj = getString(name);
		return (obj != null) ? Integer.valueOf(obj) : null;
	}

	public static Double getDouble(String name) {
		String obj = getString(name);
		return (obj != null) ? Double.valueOf(obj) : null;
	}

	public static Boolean getBoolean(String name) {
		return Boolean.valueOf(getString(name));
	}

	public static String getString(String name) {
		return instance.datas.get(name);
	}

}
