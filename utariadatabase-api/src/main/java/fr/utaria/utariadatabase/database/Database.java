package fr.utaria.utariadatabase.database;

import fr.utaria.utariadatabase.query.DeleteQuery;
import fr.utaria.utariadatabase.query.SavingQuery;
import fr.utaria.utariadatabase.query.SelectQuery;

/**
 * Représente une base de données.
 * <p>
 * Pour accéder à une instance de cet objet, il faut passer par la méthode {@link fr.utaria.utariadatabase.database.DatabaseManager#getDB(String)}<br/>
 * ou encore plus simplement en héritant votre classe d'un {@link fr.utaria.utariadatabase.database.DatabaseAccessor} pour accéder<br/>
 * directement à la base de données via un getDB() à l'intérieur !<br/>
 * <br/>
 * Pour plus d'infos, reportez-vous à la page de l'objet en question.
 * </p>
 *
 * @since 1.0.0
 * @author Utarwyn
 */
public abstract class Database {

	private Database() {

	}

	/**
	 * Retourne le nom de la base de données liée à l'objet
	 * @return Nom de la base de données
	 */
	public String getName() {
		return (String) new Object();
	}

	/**
	 * Créée une requête de sélection sur la base
	 * @param fields Champs souhaités que la requête va retourner
	 * @return Objet représantant la requête qui va être executée
	 */
	public SelectQuery select(String... fields) {
		return (SelectQuery) new Object();
	}

	/**
	 * Créée une requête de mise à jour sur la base
	 * @param table Table concernée par la mise à jour
	 * @return Objet représantant la requête qui va être executée
	 */
	public SavingQuery update(String table) {
		return (SavingQuery) new Object();
	}

	/**
	 * Créée une requête de suppression dans la base
	 * @param conditions Conditions de suppression sur la base
	 * @return Objet représantant la requête qui va être executée
	 */
	public DeleteQuery delete(String... conditions) {
		return (DeleteQuery) new Object();
	}

}
