package fr.utaria.utariadatabase.result;

import java.util.List;

/**
 * Représente le résultat d'une requête de mise à jour ou de suppression.
 * <p>
 * L'objet contient les composants suivants :
 *   - le nombre de lignes touchées par la MàJ ou la suppression ;
 *   - le premier identifiant inséré (dans le cas d'une requête d'insertion) ;
 *   - la liste des clés générées par la requête (utilisation avancée).
 * </p>
 *
 * @since 1.0.0
 * @author Utarwyn
 */
public interface UpdateResult {

	/**
	 * Retourne le nombre de lignes touchées par la requête
	 * @return Nombre de lignes
	 */
	int getRowsAffected();

	/**
	 * Retourne le premier identifiant inséré par la requête.<br/>
	 * N'est rempli que pour une requête de type INSERT, -1 sinon.<br/>
	 *
	 * @return Le premier identifiant inséré par la requête
	 */
	int getFirstInsertedId();

	/**
	 * Retourne la liste des clés générées par la requête.<br/>
	 * Méthode avancée (voir la doc MySQL pour plus d'infos).
	 *
	 * @return Clés générées par la requête
	 */
	List<Integer> getGeneratedKeys();

}
