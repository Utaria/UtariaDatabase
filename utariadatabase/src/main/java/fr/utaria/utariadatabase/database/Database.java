package fr.utaria.utariadatabase.database;

import fr.utaria.utariadatabase.InstanceManager;
import fr.utaria.utariadatabase.migration.MigrationLogger;
import fr.utaria.utariadatabase.query.DeleteQuery;
import fr.utaria.utariadatabase.query.IQuery;
import fr.utaria.utariadatabase.query.SavingQuery;
import fr.utaria.utariadatabase.query.SelectQuery;
import fr.utaria.utariadatabase.result.DatabaseSet;
import fr.utaria.utariadatabase.result.UpdateResult;
import fr.utaria.utariadatabase.util.APIReader;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.logging.LogFactory;
import org.json.simple.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Database {

	private String name;

	private boolean custom;

	private String remoteConnection;

	private SQLConnection connection;

	private Flyway migrator;

	private Database(String name) {
		this.name = name;

		this.retreiveServerUrl();
		this.connection = SQLConnection.newConnection(this.remoteConnection, name, this.custom);
	}

	String getName() {
		return this.name;
	}

	public SelectQuery select(String... fields) {
		return new SelectQuery(this, fields);
	}

	public SavingQuery update(String table) {
		return new SavingQuery(this, table);
	}

	public DeleteQuery delete(String... conditions) {
		return new DeleteQuery(this, conditions);
	}

	public List<DatabaseSet> execQueryStatement(SelectQuery query) throws SQLException {
		Connection conn = this.connection.getSQLConnection();
		if (conn == null) return null;

		Object[] attributes = query.getAttributes();
		PreparedStatement st = conn.prepareStatement(query.getRequest());

		for (int i = 1; i <= attributes.length; i++)
			st.setObject(i, attributes[i - 1]);

		return DatabaseSet.resultSetToDatabaseSet(st.executeQuery());
	}

	public UpdateResult execUpdateStatement(IQuery query) throws SQLException {
		Connection conn = this.connection.getSQLConnection();
		if (conn == null) return null;

		// Si la connexion est en lecture seule, on interdit l'écriture dans la base.
		if (this.connection.isReadOnly())
			return new UpdateResult(0, new ArrayList<>());

		Object[] attributes = query.getAttributes();
		PreparedStatement st = conn.prepareStatement(query.getRequest(), Statement.RETURN_GENERATED_KEYS);

		for (int i = 1; i <= attributes.length; i++)
			st.setObject(i, attributes[i - 1]);

		// Execution de la requête
		int rows = st.executeUpdate();

		// Gestion des identifiants générés
		List<Integer> keys = new ArrayList<>();

		ResultSet generatedKeys = st.getGeneratedKeys();
		if (generatedKeys != null)
			while (generatedKeys.next())
				keys.add(generatedKeys.getInt(1));

		return new UpdateResult(rows, keys);
	}

	public void applyMigrationsFrom(Class clazz) {
		String path = "migration/" + this.name;

		// On n'active pas les migrations sur une base inaccessible en écriture
		if (this.connection != null && this.connection.isReadOnly())
			return;

		// On regarde avant si le dossier existe.
		if (clazz.getClassLoader().getResource(path) == null) {
			InstanceManager.getInstance().log(Level.WARNING, "Dossier de migration '" + path + "' inexistant. Passons.");
			return;
		}

		this.migrator = new Flyway(clazz.getClassLoader());
		if (this.connection == null) return;

		migrator.setBaselineOnMigrate(true);
		migrator.setTable("_versioning");
		migrator.setInstalledBy("SERVER");
		migrator.setDataSource(this.connection.getDataSource());
		migrator.setLocations(path);

		LogFactory.setLogCreator(aClass -> new MigrationLogger());

		try {
			int nbMigrations = migrator.migrate();
			String msg;

			if (nbMigrations <= 1)
				msg = nbMigrations + " migration appliquée !";
			else
				msg = nbMigrations + " migrations appliquées !";

			InstanceManager.getInstance().log(Level.INFO, msg);
		} catch (Exception ex) {
			InstanceManager.getInstance().log(Level.WARNING, "Impossible d'appliquer les migrations sur la base " + this.name + " !");
			ex.printStackTrace();
		}
	}

	private void retreiveServerUrl() {
		InstanceManager.getInstance().log(Level.INFO, "Demande d'acces a la base " + this.name + ". Recuperation des infos de la base...");
		String token = "?token=" + APIReader.getAuthToken();
		JSONObject obj = APIReader.apiRequest("databases/" + this.name + token, "GET", "");

		if (obj != null && (Boolean) obj.get("success")) {
			this.remoteConnection = (String) obj.get("server");
		} else {
			InstanceManager.getInstance().log(Level.WARNING, "Base de donnees " + this.name + " inconnue par le reseau UTARIA.");
			InstanceManager.getInstance().log(Level.WARNING, "Utilisation du serveur personnel pour celle-ci.");
			this.custom = true;
		}
	}

	static Database newInstance(String name) {
		Database db = new Database(name);
		return db.connection == null ? null : db;
	}

}
