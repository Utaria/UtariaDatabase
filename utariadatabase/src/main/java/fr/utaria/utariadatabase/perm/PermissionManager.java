package fr.utaria.utariadatabase.perm;

import fr.utaria.utariadatabase.UtariaDatabasePlugin;

import java.util.ArrayList;
import java.util.List;

// TODO : à développer pour sécuriser les accès à la base de données.
//        Pas très important étant donné qu'une vérification à lieue
//        avant la mise en production des plugins.
public class PermissionManager {

	private static PermissionManager instance;

	private List<PermissionNode> nodes;

	private UtariaDatabasePlugin plugin;

	private PermissionManager(UtariaDatabasePlugin plugin) {
		this.nodes = new ArrayList<>();
		this.plugin = plugin;
	}

	public static void init(UtariaDatabasePlugin plugin) {
		if (instance != null) return;
		instance = new PermissionManager(plugin);
	}

}
