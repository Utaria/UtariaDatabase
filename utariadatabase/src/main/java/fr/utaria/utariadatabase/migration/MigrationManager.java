package fr.utaria.utariadatabase.migration;

import fr.utaria.utariadatabase.UtariaDatabasePlugin;

public class MigrationManager {

	private static MigrationManager instance;

	private MigrationManager() {

	}

	public static void init(UtariaDatabasePlugin plugin) {
		if (instance != null) return;
		instance = new MigrationManager();
	}

}
