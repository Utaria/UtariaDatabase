package fr.utaria.utariadatabase.database;

import org.apache.commons.lang3.Validate;

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

		// On essaie de se connecter à la base en question
		Database db = Database.newInstance(databaseName);

		if (db != null)
			DatabaseManager.getInstance().databases.add(db);
		else
			throw new IllegalArgumentException("Une erreur a eu lieue lors de l'enregistrement de la base \"" + databaseName + "\" !");
	}

	public static void applyMigrations(String databaseName) {
		Database db = getDB(databaseName);
		Validate.notNull(db, "la base " + databaseName + " n'est pas enregistrée");

		try {
			db.applyMigrationsFrom(Class.forName(getCallerClassName()));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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

	private static String getCallerClassName() {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();

		for (int i = 1; i < stElements.length; i++) {
			StackTraceElement ste = stElements[i];

			if (!ste.getClassName().equals(DatabaseManager.class.getName()) && ste.getClassName().indexOf("java.lang.Thread") != 0)
				return ste.getClassName();
		}

		return null;
	}

}
