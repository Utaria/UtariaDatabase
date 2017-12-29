package fr.utaria.utariadatabase.database;

public abstract class DatabaseAccessor {

	private String databaseName;


	public DatabaseAccessor(String databaseName) {
		this.databaseName = databaseName;
		if (databaseName == null) return;

		DatabaseManager.registerDatabaseAccessor(this);
	}


	String getDBName() {
		return this.getDB().getName();
	}

	public Database getDB() {
		if (databaseName == null) return null;
		return DatabaseManager.getDB(databaseName);
	}

}
