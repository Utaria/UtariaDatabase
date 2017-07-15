package fr.utaria.utariadatabase.database;

import fr.utaria.utariadatabase.query.QuerySelect;
import fr.utaria.utariadatabase.result.DatabaseSet;
import fr.utaria.utariadatabase.util.Config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class Database {

	private String        name;
	private SQLConnection connection;


	Database(String name) {
		this.name = name;

		try {
			this.connection = SQLConnection.newConnection(Config.remoteSSHUrl, name);
		} catch (SQLException e) {
			System.err.println("Connexion impossible à la base de données !");
			e.printStackTrace();
		}
	}

	String getName() {
		return this.name;
	}

	public QuerySelect select(String ...fields) {
		return new QuerySelect(this, fields);
	}

	public List<DatabaseSet> execQueryStatement(QuerySelect query) throws SQLException {
		Connection conn = this.connection.getSQLConnection();
		if (conn == null) return null;

		System.out.println(query.getRequest());

		Object[] attributes = query.getAttributes();
		PreparedStatement st = conn.prepareStatement(query.getRequest());

		for (int i = 1; i <= attributes.length; i++)
			st.setObject(i, attributes[i - 1]);

		return DatabaseSet.resultSetToDatabaseSet(st.executeQuery());
	}

	public List<DatabaseSet> request(String req, List<Object> attr) {
		return null;
	}

}
