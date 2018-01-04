package fr.utaria.utariadatabase.database;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import fr.utaria.utariadatabase.InstanceManager;
import fr.utaria.utariadatabase.util.APIReader;
import org.bukkit.configuration.file.YamlConfiguration;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;

class SQLConnection {

	private Connection connection;

	private String host;

	private String databaseName;

	private SQLConnection(String host, String databaseName) {
		this.host = host;
		this.databaseName = databaseName;
	}

	Connection getSQLConnection() {
		return this.connection;
	}

	public boolean isFlat() {
		return this.host.endsWith(".db");
	}

	private boolean connectToRemoteDataBase() {
		if (this.isFlat()) return false;
		YamlConfiguration configuration = APIReader.getUtariaConfig();
		if (configuration == null || !configuration.isConfigurationSection("sql.remote")) return false;

		String host = this.host;
		int port = 3306;

		if (host.contains(":")) {
			String[] s = host.split(":");
			host = s[0];
			port = Integer.parseInt(s[1]);
		}

		return this.startMysqlConnection(
				host, port,
				configuration.getString("sql.remote.user"),
				configuration.getString("sql.remote.password")
		);
	}

	private boolean connectToFlatfileDatabase() {
		if (this.connection != null || !this.isFlat()) return false;

		try {
			// Connexion à la base de données locale (SQLite)
			SQLiteDataSource dataSource = new SQLiteDataSource();
			if (this.host.startsWith("internal:"))
				dataSource.setUrl("jdbc:sqlite::resource:" + getClass().getResource("/db/" + this.host.replace("internal:", "")));
			else
				dataSource.setUrl("jdbc:sqlite:" + this.host);

			dataSource.setReadOnly(true);

			this.connection = dataSource.getConnection();

			InstanceManager.getInstance().log(Level.INFO, "Base de donnees locale '" + this.databaseName + "' prete et connectee.");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean connectToCustomDatabase() {
		YamlConfiguration configuration = APIReader.getUtariaConfig();
		if (configuration == null || !configuration.isConfigurationSection("sql.custom")) return false;

		return this.startMysqlConnection(
				configuration.getString("sql.custom.host"),
				configuration.getInt("sql.custom.port"),
				configuration.getString("sql.custom.user"),
				configuration.getString("sql.custom.password")
		);
	}

	private boolean startMysqlConnection(String host, int port, String user, String password) {
		try {
			// Connexion à la base de données (MySQL)
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setServerName(host);
			dataSource.setPortNumber(port);
			dataSource.setUser(user);
			dataSource.setAllowMultiQueries(true);

			dataSource.setPassword(password);
			dataSource.setDatabaseName(this.databaseName);

			this.connection = dataSource.getConnection();

			InstanceManager.getInstance().log(Level.INFO, "Base de donnees '" + this.databaseName + "' prete et connectee.");
			return true;
		} catch (Exception e) {
			e.printStackTrace();

			InstanceManager.getInstance().log(Level.SEVERE, " ");
			InstanceManager.getInstance().log(Level.SEVERE, " ");
			InstanceManager.getInstance().log(Level.SEVERE, "  ERREUR DE CONNEXION SQL !!");
			InstanceManager.getInstance().log(Level.SEVERE, " ");
			InstanceManager.getInstance().log(Level.SEVERE, "  Impossible de se connecter a la base de donnees " + this.databaseName + " via le serveur " + host + ":" + port + " !");
			InstanceManager.getInstance().log(Level.SEVERE, " ");
			InstanceManager.getInstance().log(Level.SEVERE, " ");

			return false;
		}
	}

	public void closeConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				InstanceManager.getInstance().log(Level.INFO, "Fermeture de la connexion à la base de données " + this.databaseName);
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static SQLConnection newConnection(String host, String databaseName, boolean custom) {
		SQLConnection conn = new SQLConnection(host, databaseName);

		if (!custom)
			return (conn.connectToRemoteDataBase() || conn.connectToFlatfileDatabase()) ? conn : null;
		else
			return conn.connectToCustomDatabase() ? conn : null;
	}

}
