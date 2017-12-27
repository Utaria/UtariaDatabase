package fr.utaria.utariadatabase.database;

import fr.utaria.utariadatabase.query.DeleteQuery;
import fr.utaria.utariadatabase.query.IQuery;
import fr.utaria.utariadatabase.query.SavingQuery;
import fr.utaria.utariadatabase.query.SelectQuery;
import fr.utaria.utariadatabase.result.DatabaseSet;
import fr.utaria.utariadatabase.result.UpdateResult;
import fr.utaria.utariadatabase.util.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Database {

	public static final String NOW = "NOW()";


	private String        name;
	private SQLConnection connection;

	private int lastInsertId;


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


	public SelectQuery select(String ...fields) {
		return new SelectQuery(this, fields);
	}

	public SavingQuery update(String table) {
		return new SavingQuery(this, table);
	}

	public DeleteQuery delete(String ...conditions) {
		return new DeleteQuery(this, conditions);
	}


	public List<DatabaseSet> execQueryStatement(SelectQuery query) throws SQLException {
		Connection conn = this.connection.getSQLConnection();
		if (conn == null) return null;

		if (Config.ENABLE_DEBUG)
			System.out.println(query.getRequest());

		Object[] attributes = query.getAttributes();
		PreparedStatement st = conn.prepareStatement(query.getRequest());

		for (int i = 1; i <= attributes.length; i++)
			st.setObject(i, attributes[i - 1]);

		return DatabaseSet.resultSetToDatabaseSet(st.executeQuery());
	}

	public UpdateResult execUpdateStatement(IQuery query) throws SQLException {
		Connection conn = this.connection.getSQLConnection();
		if (conn == null) return null;

		if (Config.ENABLE_DEBUG)
			System.out.println(query.getRequest());

		Object[] attributes = query.getAttributes();
		PreparedStatement st = conn.prepareStatement(query.getRequest(), Statement.RETURN_GENERATED_KEYS);

		for (int i = 1; i <= attributes.length; i++)
			st.setObject(i, attributes[i - 1]);

		// Execution de la requête
		int rows =  st.executeUpdate();

		// Gestion des identifiants générés
		List<Integer> keys = new ArrayList<>();

		ResultSet generatedKeys = st.getGeneratedKeys();
		if (generatedKeys != null)
			while (generatedKeys.next())
				lastInsertId = generatedKeys.getInt(1);

		return new UpdateResult(rows, keys);
	}

}
