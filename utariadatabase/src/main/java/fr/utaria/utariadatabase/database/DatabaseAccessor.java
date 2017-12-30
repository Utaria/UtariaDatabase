package fr.utaria.utariadatabase.database;

public abstract class DatabaseAccessor {

	private String databaseName;

	public DatabaseAccessor(String databaseName) {
		if (databaseName == null)
			throw new NullPointerException("Le nom de la base ne peut pas être vide !");

		this.databaseName = databaseName;

		// Enregistre la base si besoin ...
		DatabaseManager.registerDatabase(this.databaseName);

		// ... puis l'accesseur rapide !
		DatabaseManager.registerDatabaseAccessor(this);
	}

	String getDBName() {
		return this.getDB().getName();
	}

	public Database getDB() {
		return DatabaseManager.getDB(databaseName);
	}

}
