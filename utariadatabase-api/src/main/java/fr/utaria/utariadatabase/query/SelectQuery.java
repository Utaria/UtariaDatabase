package fr.utaria.utariadatabase.query;

import fr.utaria.utariadatabase.result.DatabaseSet;

import java.util.Collections;
import java.util.List;

/**
 * Représente une requête pour selectionner des données.
 * <p>
 * Elle accepte les différents composants :<br/>
 *  - Table où la suppression doit avoir lieue<br/>
 *  - Condition de suppressions<br/>
 *  - Attributs de la requête<br/>
 *  <br/>
 *  <br/>
 *  <b>La méthode find() ou findAll() doivent être appelées à la fin pour executer la requête fabriquée.</b><br/>
 *  Elle retourne le(s) résultat(s) via l'objet {@link fr.utaria.utariadatabase.result.DatabaseSet}.<br/>
 *  <br/>
 *  <b>ATTENTION! </b> Toutes les méthodes de cette classe retourne le même objet, vous pouvez donc faire :<br/>
 *                     <i>.select("playername, coins").from("players").where("id = ?").attributes(50)<b>.find()</b>;</i>
 * </p>
 *
 * @since 1.0.0
 * @author Utarwyn
 */
public abstract class SelectQuery {

	private SelectQuery() {

	}

	/**
	 * Enregistre les tables de selection des données
	 * @param froms Table où la selection va être lancée
	 * @return Le même objet pour appeler les autres méthodes
	 */
	public SelectQuery from(String... froms) {
		return this;
	}

	/**
	 * Ajoute une jointure de selection.<br/>
	 * La condition doit être vraie dans les deux tables.<br/>
	 * Pour plus d'aide, se reporter à la documentation de MySQL.
	 *
	 * @param table Table à associer à la requête
	 * @param field1 1er champ de la liaison
	 * @param field2 2ème champ de la liaison
	 * @return Le même objet pour appeler les autres méthodes
	 */
	public SelectQuery join(String table, String field1, String field2) {
		return this;
	}

	/**
	 * Ajoute une jointure de selection.<br/>
	 * Ne vérifie pas la condition dans la table à associer.<br/>
	 * Pour plus d'aide, se reporter à la documentation de MySQL.
	 *
	 * @param table Table à associer à la requête
	 * @param field1 1er champ de la liaison
	 * @param field2 2ème champ de la liaison
	 * @return Le même objet pour appeler les autres méthodes
	 */
	public SelectQuery leftjoin(String table, String field1, String field2) {
		return this;
	}

	/**
	 * Applique les conditions de la requête.<br/>
	 * Il est possible d'utiliser les points d'interrogations pour préparer les attributs (voir doc MySQL).
	 *
	 * @param conditions Conditions de la requête sous le format SQL (exemple: "coins < 500")
	 * @return Le même objet pour appeler les autres méthodes
	 */
	public SelectQuery where(String... conditions) {
		return this;
	}

	/**
	 * Groupe les attributs selon certains critères. Méthode MySQL avancée.<br/>
	 * Son utilisation est retreinte à certains cas très précis. Se reporter à la documentation.
	 *
	 * @param groupsBy Critères ou champs pour appliquer le regroupement sur la requête
	 * @return Le même objet pour appeler les autres méthodes
	 */
	public SelectQuery groupBy(String... groupsBy) {
		return this;
	}

	/**
	 * Trie les résultats de la requête selon certains critères.<br/>
	 *
	 * @param orders Chaîne d'un critère d'ordre au format SQL (ex: "coins DESC")
	 * @return Le même objet pour appeler les autres méthodes
	 */
	public SelectQuery order(String... orders) {
		return this;
	}

	/**
	 * Limite les résultats de la requête à <b>length</b> valeurs.
	 * @param length Nombre de résultats à obtenir au maximum
	 * @return Le même objet pour appeler les autres méthodes
	 */
	public SelectQuery limit(int length) {
		return this;
	}

	/**
	 * Limite les résultats entre la valeur <b>begin</b> et la valeur <b>end</b>.
	 * @param begin Indice de démarrage de la limitation
	 * @param end Indice de fin de la limitation
	 * @return Le même objet pour appeler les autres méthodes
	 */
	public SelectQuery limit(int begin, int end) {
		return this;
	}

	/**
	 * Défini les attributs de la requête (remplace un par un dans l'ordre les "?" de la requête)<br/>
	 * Fonctionne de la même manière qu'avec les requêtes préparées (voir doc MySQL).
	 *
	 * @param attributes Attributs à passer à la requête
	 * @return Le même objet pour appeler les autres méthodes
	 */
	public SelectQuery attributes(Object... attributes) {
		return this;
	}

	/**
	 * Lance la requête de sélection dans le but d'obtenir <b>un seul tuple</b>.<br/>
	 * Le tuple est la collection des valeurs des attributs de la table.
	 *
	 * @return Le résultat souhaité s'il a été trouvé, null sinon.
	 */
	public DatabaseSet find() {
		return (DatabaseSet) new Object();
	}

	/**
	 * Lance la requête de sélection dans le but d'obtenir <b>plusieurs tuples</b>.<br/>
	 * Le tuple est la collection des valeurs des attributs de la table.
	 *
	 * @return Liste des tuples trouvés en résultat de la requête.
	 *         Le tableau est vide si aucun tuple n'a été trouvé.
	 */
	public List<DatabaseSet> findAll() {
		return Collections.emptyList();
	}

}
