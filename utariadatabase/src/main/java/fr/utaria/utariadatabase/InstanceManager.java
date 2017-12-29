package fr.utaria.utariadatabase;

public class InstanceManager {

	private static UtariaDatabasePlugin instance;

	private InstanceManager() {
	}

	public static void useInstance(UtariaDatabasePlugin instance) {
		InstanceManager.instance = instance;
	}

	public static UtariaDatabasePlugin getInstance() {
		return instance;
	}

}
