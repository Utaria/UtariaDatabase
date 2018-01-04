package fr.utaria.utariadatabase.result;

import java.util.Collections;
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
public abstract class UpdateResult {

	private UpdateResult() {

	}

	/**
	 * Retourne le nombre de lignes touchées par la requête
	 * @return Nombre de lignes
	 */
	public int getRowsAffected() {
		return (Integer) new Object();
	}

	/**
	 * Retourne le premier identifiant inséré par la requête.<br/>
	 * N'est rempli que pour une requête de type INSERT, -1 sinon.<br/>
	 *
	 * @return Le premier identifiant inséré par la requête
	 */
	public int getFirstInsertedId() {
		return (Integer) new Object();
	}

	/**
	 * Retourne la liste des clés générées par la requête.<br/>
	 * Méthode avancée (voir la doc MySQL pour plus d'infos).
	 *
	 * @return Clés générées par la requête
	 */
	public List<Integer> getGeneratedKeys() {
		return Collections.emptyList();
	}

}
