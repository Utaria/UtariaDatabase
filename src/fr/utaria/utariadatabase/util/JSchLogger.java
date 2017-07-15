package fr.utaria.utariadatabase.util;

import com.jcraft.jsch.Logger;
import fr.utaria.utariadatabase.UtariaDatabaseBukkit;

import java.util.Hashtable;
import java.util.logging.Level;

public class JSchLogger implements Logger {

	private static Hashtable<Integer, Level> levels = new Hashtable<>();


	static {
		levels.put(DEBUG, Level.CONFIG);
		levels.put(INFO, Level.INFO);
		levels.put(WARN, Level.WARNING);
		levels.put(ERROR, Level.SEVERE);
		levels.put(FATAL, Level.SEVERE);
	}

	public JSchLogger() {}


	public boolean isEnabled(int level) {
		return false;
	}

	public void log(int level, String message) {
		this.log(level, message, false);
	}
	public void log(int level, String message, boolean force) {
		if (force || Config.environment.equals("development"))
			UtariaDatabaseBukkit.getInstance().getLogger().log(levels.get(level), message);
	}

}
