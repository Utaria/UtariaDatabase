package fr.utaria.utariadatabase.util;

public class Config {

	private Config() {}


	public static final String DEFAULT_ENVIRONMENT = "production";
	public static final String API_TOKEN           = "z8v5dDMU2BhX4cDVAVjds6VnyvvebyrS5KdzD8CeauZZErz5Nf";

	public static final String SQL_DRIVER    = "com.mysql.jdbc.Driver";
	public static final String LOCAL_SSH_URL = "localhost";

	public static final String MYSQL_USER     = "root";
	public static final String MYSQL_PASSWORD = "root";

	public static final int BASE_LOCAL_PORT   = 8740;
	public static final int MYSQL_REMOTE_PORT = 3306;


	public static String environment;
	public static String remoteSSHUrl;

}
