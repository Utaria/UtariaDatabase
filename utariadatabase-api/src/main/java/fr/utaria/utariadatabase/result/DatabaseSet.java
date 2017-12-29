package fr.utaria.utariadatabase.result;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public interface DatabaseSet {

	String getString(String column);

	Integer getInteger(String column);

	Boolean getBoolean(String column);

	Object getObject(String column);

	Short getShort(String column);

	Byte getByte(String column);

	BigDecimal getBigDecimal(String column);

	Float getFloat(String column);

	Double getDouble(String column);

	Long getLong(String column);

	Timestamp getTimestamp(String column);

	Date getDate(String column);

}
