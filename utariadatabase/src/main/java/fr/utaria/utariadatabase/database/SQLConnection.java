package fr.utaria.utariadatabase.database;

import com.google.common.io.ByteStreams;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Logger;
import com.jcraft.jsch.Session;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import fr.utaria.utariadatabase.util.Config;
import fr.utaria.utariadatabase.util.JSchLogger;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

class SQLConnection {

	private static JSchLogger logger;
	private static int        localPortStk;

	private Connection connection;
	private Session    sshSession;

	private int    localPort;
	private String remoteHost;
	private String databaseName;


	private SQLConnection(String remoteHost, String databaseName) {
		this.remoteHost   = remoteHost;
		this.databaseName = databaseName;
		this.localPort    = SQLConnection.localPortStk++;
	}

	static {
		logger       = new JSchLogger();
		localPortStk = Config.BASE_LOCAL_PORT;
	}



	Connection getSQLConnection() {
		return this.connection;
	}


	private boolean createSSHTunnel() throws SQLException {
		String sshUser = "db" + this.databaseName;
		if (!Config.ENABLE_SSH) return true;

		try {
			// Création de l'instance et démarrage de la session SSH
			JSch jsch = new JSch();
			JSch.setLogger(logger);

			this.sshSession = jsch.getSession(sshUser, this.remoteHost, 22);

			// Récupération des clés de sécurité dans le plugin
			String      keyName          = sshUser.replace("db", "");
			InputStream privateKeyStream = SQLConnection.class.getResourceAsStream("/keys/" + Config.environment + "/" + keyName + ".key");
			InputStream publicKeyStream  = SQLConnection.class.getResourceAsStream("/keys/" + Config.environment + "/" + keyName + ".key.pub");

			if (privateKeyStream == null || publicKeyStream == null)
				throw new NullPointerException("Clé privée ou publique inexistante pour l'utilisateur " + sshUser + " !");

			jsch.addIdentity("dbglobal", ByteStreams.toByteArray(privateKeyStream), ByteStreams.toByteArray(publicKeyStream), null);

			// Configuration de la session SSH
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			config.put("ConnectionAttempts", "3");
			sshSession.setConfig(config);

			// Connexion...
			sshSession.connect();

			logger.log(Logger.INFO, "Connecté en SSH à l'adresse " + sshUser + "@" + this.remoteHost);

			// Vérificiation de la présence du driver SQL
			Class.forName(Config.SQL_DRIVER).newInstance();

			// Création du tunnel SSH vers la base de données
			sshSession.setPortForwardingL(this.localPort, this.remoteHost, Config.MYSQL_REMOTE_PORT);

			logger.log(Logger.INFO, "Tunnel SSH établi avec succès : localhost:" + this.localPort + " ===> " + this.remoteHost + ":" + Config.MYSQL_REMOTE_PORT);
			logger.log(Logger.INFO, "Prêt à initialiser la connexion SQL.");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	private boolean connectToDataBase() throws SQLException {
		String sqlUrl  = (Config.ENABLE_SSH) ? Config.LOCAL_SSH_URL : Config.remoteSSHUrl;
		int    sqlPort = (Config.ENABLE_SSH) ? this.localPort       : Config.MYSQL_REMOTE_PORT;

		try {
			// Connexion à la base de données
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setServerName(sqlUrl);
			dataSource.setPortNumber(sqlPort);
			dataSource.setUser(Config.MYSQL_USER);
			dataSource.setAllowMultiQueries(true);

			dataSource.setPassword(Config.MYSQL_PASSWORD);
			dataSource.setDatabaseName(this.databaseName);

			connection = dataSource.getConnection();

			logger.log(Logger.INFO, "Base de données '" + this.databaseName + "' prête et connectée.", true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}


	public void closeConnections() {
		this.closeDataBaseConnection();
		this.closeSSHConnection();
	}

	private void closeDataBaseConnection() {
		try {
			if (connection != null && !connection.isClosed()) {
				logger.log(Logger.INFO, "Fermeture de la connexion à la base de données " + this.databaseName);
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void closeSSHConnection() {
		if (sshSession != null && sshSession.isConnected()) {
			System.out.println("Closing SSH Connection");
			sshSession.disconnect();
		}
	}

	public static SQLConnection newConnection(String host, String databaseName) throws SQLException  {
		SQLConnection conn = new SQLConnection(host, databaseName);
		return (conn.createSSHTunnel() && conn.connectToDataBase()) ? conn : null;
	}

}
