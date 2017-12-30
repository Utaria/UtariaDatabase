package fr.utaria.utariadatabase.database;

/**
 * Accesseur raccourci pour accéder à la même base de données à partir d'un objet.
 * <p>
 * Pour faire fonctionner ce raccourci, il faut hériter la classe qui va lancer les requêtes à celle-ci.<br/>
 * Il vous faudra fournir le nom de la base de données à laquelle vous voulez accéder dans le constructeur.<br/>
 * (via la méthode <b>super(<i>"Nom de la base de données"</i>)</b> en première ligne)
 * <br/><br/>
 * Si vous ne pouvez pas utiliser ce raccourci d'héritage, il vous est possible d'utiliser la méthode {@link fr.utaria.utariadatabase.database.DatabaseManager#getDB(String)}.
 * <br/>
 * <b>ATTENTION ! </b> Dans ce cas n'oubliez pas d'initialiser la connexion à la base avec la méthode {@link fr.utaria.utariadatabase.database.DatabaseManager#registerDatabase(String)} avant !
 * </p>
 *
 * @since 1.0.0
 * @author Utarwyn
 */
public abstract class DatabaseAccessor {

	/**
	 * Construit l'accesseur rapide à la base de données passée en paramètre.
	 * Se charge pour vous d'initialiser la connexion à la base si jamais elle n'est pas encore enregistrée.
	 *
	 * @param databaseName Nom de la base à lier à l'accesseur.
	 * @throws NullPointerException Renvoie cette erreur si le nom passé est vide.
	 * @throws IllegalArgumentException Renvoie cette erreur si la connexion n'a pas pu être établie avec la base souhaitée.
	 */
	public DatabaseAccessor(String databaseName) {
	}

	/**
	 * Retourne le nom de la base de données liée
	 * @return Nom de la base de données
	 */
	String getDBName() {
		return null;
	}

	/**
	 * Retourne l'objet de la base connectée sur lequel vous pouvez executer vos requêtes.
	 *
	 * @return Objet représentant la base de données souhaitée
	 * @throws IllegalArgumentException Renvoie une erreur si la base n'a pas pu être enregistrée avant
	 */
	public Database getDB() {
		return null;
	}

}
