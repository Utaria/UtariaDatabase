package fr.utaria.utariadatabase;

import fr.utaria.utariadatabase.database.DatabaseManager;
import fr.utaria.utariadatabase.migration.MigrationManager;
import fr.utaria.utariadatabase.perm.PermissionManager;
import fr.utaria.utariadatabase.util.APIReader;
import fr.utaria.utariadatabase.util.ConfigTableAccessor;

import java.util.logging.Level;

public interface UtariaDatabasePlugin {

	void log(Level logLevel, String message);

	default void init() {
		if (!APIReader.authenticate()) {
			// TODO : utiliser les bases de données en local car impossible de
			//        se connecter à l'API (travail hors-ligne ?)

			// Pour le moment on coupe le serveur...
			InstanceManager.getInstance().log(Level.SEVERE, "Impossible de se connecter a l'API...");
			InstanceManager.getInstance().log(Level.SEVERE, "Avez-vous bien lancé le serveur fourni par UTARIA ?");
			System.exit(1);
			return;
		}

		DatabaseManager.registerDatabase("global");

		ConfigTableAccessor.init(this);
		PermissionManager.init(this);
		MigrationManager.init(this);
	}

	void runTimerTask(Runnable runnable, int delay, int timer);

}