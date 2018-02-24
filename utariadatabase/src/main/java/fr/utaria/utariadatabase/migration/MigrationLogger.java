package fr.utaria.utariadatabase.migration;

import fr.utaria.utariadatabase.InstanceManager;
import org.flywaydb.core.api.logging.Log;

import java.util.logging.Level;

public class MigrationLogger implements Log {

	@Override
	public void debug(String s) {
		// No debug
	}

	@Override
	public void info(String s) {
		// No info
	}

	@Override
	public void warn(String s) {
		InstanceManager.getInstance().log(Level.WARNING, s);
	}

	@Override
	public void error(String s) {
		InstanceManager.getInstance().log(Level.SEVERE, s);
	}

	@Override
	public void error(String s, Exception e) {
		InstanceManager.getInstance().log(Level.SEVERE, s);
	}

}
