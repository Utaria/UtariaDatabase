package fr.utaria.utariadatabase.query;

import fr.utaria.utariadatabase.result.UpdateResult;

/**
 * Représente une requête pour supprimer des données.
 * <p>
 * Elle accepte les différents composants :<br/>
 *  - Table où la suppression doit avoir lieue<br/>
 *  - Condition de suppressions<br/>
 *  - Attributs de la requête<br/>
 *  <br/>
 *  <br/>
 *  <b>La méthode execute() doit être appelée à la fin pour executer la requête fabriquée.</b><br/>
 *  Elle retourne le résultat via l'objet {@link fr.utaria.utariadatabase.result.UpdateResult}.<br/>
 *  <br/>
 *  <b>ATTENTION! </b> Toutes les méthodes de cette classe retourne le même objet, vous pouvez donc faire :<br/>
 *                     <i>.delete("playername = ?").from("players").attributes("Utarwyn")<b>.execute()</b>;</i>
 * </p>
 *
 * @since 1.0.0
 * @author Utarwyn
 */
public interface DeleteQuery {

	/**
	 * Défini le nom de la table où la requête va être executée.
	 * @param table Nom de la table
	 * @return Le même objet pour appeler les autres méthodes
	 */
	DeleteQuery from(String table);

	/**
	 * Défini les attributs de la requête (remplace un par un dans l'ordre les "?" de la requête)
	 * @param attributes Attributs à passer à la requête
	 * @return Le même objet pour appeler les autres méthodes
	 */
	DeleteQuery attributes(Object... attributes);

	/**
	 * Exécute la requête pré-fabriquée grâce aux méthodes précédentes.
	 * <b>DOIT être la dernière méthode appelée sur l'objet.</b>
	 * @return Le résultat de la requête
	 */
	UpdateResult execute();

}
