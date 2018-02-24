package fr.utaria.utariadatabase.database;

/**
 * Gestionnaire de base de données. Il stocke les connexions ouvertes et les bases connectées.
 * C'est le point central de l'API. Vous pouvez y enregistrer et récupérer l'objet d'une base de données.
 *
 * @since 1.0.0
 * @author Utarwyn
 */
public abstract class DatabaseManager {

	private DatabaseManager() {

	}

	/**
	 * Enregistre une base de données en mémoire et se connecte à celle-ci.
	 * La méthode va stocker en mémoire la connexion (si établie) afin de la réutiliser
	 * aux moments souhaités très rapidement.
	 *
	 * @param databaseName Nom de la base de données à mémoriser
	 * @throws IllegalArgumentException Erreur retournée si la connexion à la base de données n'a pas pu être établie.
	 */
	public static void registerDatabase(String databaseName) {

	}

	public static void applyMigrations(String databaseName) {

	}

	/**
	 * Retourne l'objet lié à la base de données passée en paramètre.
	 * Permet de récupérer simplement un objet représentant la base pour executer des requêtes.
	 *
	 * @param databaseName Nom de la base de données à récupérer
	 * @throws IllegalArgumentException Erreur envoyée si la base de données n'existe pas
	 * @return
	 */
	public static Database getDB(String databaseName) {
		return (Database) new Object();
	}

}
