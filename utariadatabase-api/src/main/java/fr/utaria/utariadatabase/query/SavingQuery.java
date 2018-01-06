package fr.utaria.utariadatabase.query;

import fr.utaria.utariadatabase.result.UpdateResult;

/**
 * Représente une requête pour sauvegarder des données.
 * <p>
 * Elle accepte les différents composants :<br/>
 *  - Table où la sauvegarde doit avoir lieue<br/>
 *  - Champs à sauvegarder<br/>
 *  - Valeurs que doivent prendre les champs définis<br/>
 *  - Condition de sauvegarde (si précisé, la requête sera un UPDATE et non un INSERT)<br/>
 *  - Attributs de la requête<br/>
 *  <br/>
 *  <br/>
 *  <b>La méthode execute() doit être appelée à la fin pour executer la requête fabriquée.</b><br/>
 *  Elle retourne le résultat via l'objet {@link fr.utaria.utariadatabase.result.UpdateResult}.<br/>
 *  <br/>
 *  <b>ATTENTION! </b> Toutes les méthodes de cette classe retourne le même objet, vous pouvez donc faire :<br/>
 *                     <i>.saving("players").fields("playername", "coins").values("?", "?").where("id = ?").attributes("Utarwyn", 500, 10)<b>.execute()</b>;</i>
 * </p>
 *
 * @since 1.0.0
 * @author Utarwyn
 */
public abstract class SavingQuery {

	private SavingQuery() {

	}

	/**
	 * Enregistre les champs qui vont être touchés par la sauvegarde dans la table précisée
	 * @param fields Champs touchés
	 * @return Le même objet pour appeler les autres méthodes
	 */
	public SavingQuery fields(String... fields) {
		return this;
	}

	/**
	 * Applique des valeurs aux champs précisés via la méthode {@link fr.utaria.utariadatabase.query.SavingQuery#fields(String...)}.<br/>
	 * Si aucun champ n'a été précisé, les valeurs seront appliquées dans l'ordre aux champs de la table.
	 *
	 * @param values Valeurs à appliquer
	 * @return Le même objet pour appeler les autres méthodes
	 */
	public SavingQuery values(Object... values) {
		return this;
	}

	/**
	 * Applique les conditions de la requête. Transforme de la même manière la requête en <b>UPDATE</b>.<br/>
	 * Il est possible d'utiliser les points d'interrogations pour préparer les attributs (voir doc MySQL).
	 *
	 * @param conditions Conditions de la requête sous le format SQL (exemple: "coins < 500")
	 * @return Le même objet pour appeler les autres méthodes
	 */
	public SavingQuery where(String... conditions) {
		return this;
	}

	/**
	 * Défini les attributs de la requête (remplace un par un dans l'ordre les "?" de la requête)<br/>
	 * Fonctionne de la même manière qu'avec les requêtes préparées (voir doc MySQL).
	 *
	 * @param attributes Attributs à passer à la requête
	 * @return Le même objet pour appeler les autres méthodes
	 */
	public SavingQuery attributes(Object... attributes) {
		return this;
	}

	/**
	 * Permet de lancer une requête de type REPLACE.<br/>
	 * C'est-à-dire que si les données existes déjà, elles seront remplacées.
	 *
	 * @return Le même objet pour appeler les autres méthodes
	 */
	public SavingQuery replaceIfExists() {
		return this;
	}

	/**
	 * Exécute la requête pré-fabriquée grâce aux méthodes précédentes.
	 * <b>DOIT être la dernière méthode appelée sur l'objet.</b>
	 * @return Le résultat de la requête
	 */
	public UpdateResult execute() {
		return (UpdateResult) new Object();
	}

}
