package fr.utaria.utariadatabase;

import java.util.logging.Level;

public interface UtariaDatabasePlugin {

	void log(Level logLevel, String message);

	void init();

}