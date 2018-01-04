package fr.utaria.utariadatabase.util;

/**
 * Classe pour accéder très facilement aux options du réseau.<br/>
 * Utilisation avancée.<br/>
 * <br/>
 * Javadoc à compléter.
 *
 * @since 1.0.0
 * @author Utarwyn
 */
public abstract class ConfigTableAccessor {

	private ConfigTableAccessor() {

	}

	public static Integer getInteger(String name) {
		return 0;
	}

	public static Double getDouble(String name) {
		return 0D;
	}

	public static Boolean getBoolean(String name) {
		return false;
	}

	public static String getString(String name) {
		return "";
	}

}
