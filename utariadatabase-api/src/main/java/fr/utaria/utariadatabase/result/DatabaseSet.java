package fr.utaria.utariadatabase.result;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Représente un tuple récupéré depuis la base de données.<br/>
 * C'est donc la collection des valeurs des attributs de la table correspondante.
 * <p>
 * Il est donc possible de récupérer les valeurs via cette classe grâce aux différentes méthodes
 * de récupérer précisées ci-dessous. Il suffit à chaque fois de préciser le nom de la columne
 * où la valeur souhaitée se situe afin de la récupérer ! A noter que si la colonne n'existe pas,
 * une valeur nulle sera retournée.
 * <br/><br/>
 * <i>Exemple: <b>getString("playername")</b> va me retourner le nom du joueur du tuple.</i>
 * </p>
 *
 * @since 1.0.0
 * @author Utarwyn
 */
public interface DatabaseSet {

	/**
	 * Récupère une valeur de type "chaîne de caractère"
	 * @param column Colonne où la valeur souhaitée se trouve
	 * @return Valeur trouvée ou null
	 */
	String getString(String column);

	/**
	 * Récupère une valeur de type "entier"
	 * @param column Colonne où la valeur souhaitée se trouve
	 * @return Valeur trouvée ou null
	 */
	Integer getInteger(String column);

	/**
	 * Récupère une valeur de type "booléen"
	 * @param column Colonne où la valeur souhaitée se trouve
	 * @return Valeur trouvée ou null
	 */
	Boolean getBoolean(String column);

	/**
	 * Récupère une valeur de type "objet"<br/>
	 * Méthode globale. Est capable de récupérer tout type de valeur.
	 *
	 * @param column Colonne où la valeur souhaitée se trouve
	 * @return Valeur trouvée ou null
	 */
	Object getObject(String column);

	/**
	 * Récupère une valeur de type "short".<br/>
	 * Méthode de récupération avancée.
	 *
	 * @param column Colonne où la valeur souhaitée se trouve
	 * @return Valeur trouvée ou null
	 */
	Short getShort(String column);

	/**
	 * Récupère une valeur de type "byte".<br/>
	 * Méthode de récupération avancée.
	 *
	 * @param column Colonne où la valeur souhaitée se trouve
	 * @return Valeur trouvée ou null
	 */
	Byte getByte(String column);

	/**
	 * Récupère une valeur de type "big decimal".<br/>
	 * Méthode de récupération avancée.
	 *
	 * @param column Colonne où la valeur souhaitée se trouve
	 * @return Valeur trouvée ou null
	 */
	BigDecimal getBigDecimal(String column);

	/**
	 * Récupère une valeur de type "nombre à virgule"
	 * @param column Colonne où la valeur souhaitée se trouve
	 * @return Valeur trouvée ou null
	 */
	Float getFloat(String column);

	/**
	 * Récupère une valeur de type "long nombre à virgule"
	 * @param column Colonne où la valeur souhaitée se trouve
	 * @return Valeur trouvée ou null
	 */
	Double getDouble(String column);

	/**
	 * Récupère une valeur de type "long"
	 * @param column Colonne où la valeur souhaitée se trouve
	 * @return Valeur trouvée ou null
	 */
	Long getLong(String column);

	/**
	 * Récupère une valeur de type "timestamp" (nombre de secondes écoulées depuis le 1er janvier 1970)
	 * @param column Colonne où la valeur souhaitée se trouve
	 * @return Valeur trouvée ou null
	 */
	Timestamp getTimestamp(String column);

	/**
	 * Récupère une valeur de type "date"
	 * @param column Colonne où la valeur souhaitée se trouve
	 * @return Valeur trouvée ou null
	 */
	Date getDate(String column);

}
