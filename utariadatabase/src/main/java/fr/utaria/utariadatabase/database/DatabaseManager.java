package fr.utaria.utariadatabase.database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

	private static DatabaseManager instance;

	private List<Database> databases;

	private DatabaseManager() {
		databases = new ArrayList<>();
	}

	public static void registerDatabase(String databaseName) {
		for (Database db : DatabaseManager.getInstance().databases)
			if (db.getName().equals(databaseName))
				return;

		try {
			DatabaseManager.getInstance().databases.add(new Database(databaseName));
		} catch (SQLException e) {
			throw new IllegalArgumentException("Une erreur a eu lieue lors de l'enregistrement de la base \"" + databaseName + "\" !", e);
		}
	}

	public static Database getDB(String databaseName) {
		for (Database db : DatabaseManager.getInstance().databases)
			if (db.getName().equals(databaseName))
				return db;

		throw new IllegalArgumentException("La base de données '" + databaseName + "' n'est pas enregistrée !");
	}

	static void registerDatabaseAccessor(DatabaseAccessor accessor) {
		if (DatabaseManager.getDB(accessor.getDBName()) == null)
			throw new IllegalArgumentException("La base de données '" + accessor.getDBName() + "' n'est pas enregistrée !");
	}

	private static DatabaseManager getInstance() {
		if (instance == null) instance = new DatabaseManager();
		return instance;
	}

}
