package fr.utaria.utariadatabase.util;

import fr.utaria.utariadatabase.UtariaDatabasePlugin;
import fr.utaria.utariadatabase.database.DatabaseAccessor;
import fr.utaria.utariadatabase.result.DatabaseSet;

import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigTableAccessor extends DatabaseAccessor implements Runnable {

	private static ConfigTableAccessor instance;

	private ConcurrentHashMap<String, Object> datas;


	private ConfigTableAccessor(UtariaDatabasePlugin plugin) {
		super("global");

		this.datas = new ConcurrentHashMap<>();

		plugin.runTimerTask(this, 200, 200);
		this.run();
	}

	@Override
	public void run() {
		List<DatabaseSet> sets = this.getDB().select("name", "value").from("config").findAll();

		for (DatabaseSet set : sets)
			this.datas.put(set.getString("name"), set.getObject("value"));
	}



	public static void init(UtariaDatabasePlugin databasePlugin) {
		if (instance != null) return;
		instance = new ConfigTableAccessor(databasePlugin);
	}

	public static Integer getInteger(String name) {
		Object obj = instance.datas.get(name);
		return (obj != null && (obj instanceof Integer)) ? (Integer) obj : null;
	}

	public static Double getDouble(String name) {
		Object obj = instance.datas.get(name);
		return (obj != null && (obj instanceof Double)) ? (Double) obj : null;
	}

	public static Boolean getBoolean(String name) {
		Object obj = instance.datas.get(name);
		return (obj != null && (obj instanceof Boolean)) ? (Boolean) obj : null;
	}

	public static String getString(String name) {
		Object obj = instance.datas.get(name);
		return (obj != null && (obj instanceof String)) ? (String) obj : null;
	}

	public static Timestamp getTimestamp(String name) {
		Object obj = instance.datas.get(name);
		return (obj != null && (obj instanceof Timestamp)) ? (Timestamp) obj : null;
	}

}
