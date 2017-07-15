package fr.utaria.utariadatabase.database;

public class DatabaseAccessor {

	private String databaseName;


	public DatabaseAccessor(String databaseName) {
		this.databaseName = databaseName;

		DatabaseManager.registerDatabaseAccessor(this);
	}


	String getDBName() {
		return this.getDB().getName();
	}

	public Database getDB() {
		return DatabaseManager.getDB(databaseName);
	}

}
