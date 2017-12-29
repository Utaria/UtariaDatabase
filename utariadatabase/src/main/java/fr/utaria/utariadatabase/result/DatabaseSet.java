package fr.utaria.utariadatabase.result;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseSet {

	private Map<String, Object> set = new HashMap<>();

	private DatabaseSet() {
	}

	private void setObject(String key, Object value) {
		set.put(key, value);
	}

	public String getString(String key) {
		if (set.containsKey(key) && set.get(key) instanceof String)
			return (String) set.get(key);
		else
			return null;
	}

	public Integer getInteger(String key) {
		if (set.containsKey(key) && set.get(key) instanceof Integer)
			return (Integer) set.get(key);
		else
			return null;
	}

	public Boolean getBoolean(String key) {
		if (set.containsKey(key) && set.get(key) instanceof Boolean)
			return (Boolean) set.get(key);
		else
			return null;
	}

	public Object getObject(String key) {
		return set.get(key);
	}

	public Short getShort(String key) {
		if (set.containsKey(key) && set.get(key) instanceof Short)
			return (Short) set.get(key);
		else
			return null;
	}

	public Byte getByte(String key) {
		if (set.containsKey(key) && set.get(key) instanceof Byte)
			return (Byte) set.get(key);
		else
			return null;
	}

	public BigDecimal getBigDecimal(String key) {
		if (set.containsKey(key) && set.get(key) instanceof BigDecimal)
			return (BigDecimal) set.get(key);
		else
			return null;
	}

	public Float getFloat(String key) {
		if (set.containsKey(key) && set.get(key) instanceof Float)
			return (Float) set.get(key);
		else
			return null;
	}

	public Double getDouble(String key) {
		if (set.containsKey(key) && set.get(key) instanceof Double)
			return (Double) set.get(key);
		else
			return null;
	}

	public Long getLong(String key) {
		if (set.containsKey(key) && set.get(key) instanceof Long)
			return (Long) set.get(key);
		else
			return null;
	}

	public Timestamp getTimestamp(String key) {
		if (set.containsKey(key) && set.get(key) instanceof Timestamp)
			return (Timestamp) set.get(key);
		else
			return null;
	}

	public Date getDate(String key) {
		if (set.containsKey(key) && set.get(key) instanceof Date)
			return (Date) set.get(key);
		else
			return null;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("{DatabaseSet #" + this.hashCode() + " (");

		int i = 0;
		for (String key : this.set.keySet()) {
			Object obj = this.set.get(key);

			s.append(key).append("=").append(obj);
			s.append((i < this.set.size() - 1) ? " " : "");

			i++;
		}

		s.append(")}");

		return s.toString();
	}

	public static List<DatabaseSet> resultSetToDatabaseSet(ResultSet resultSet) {
		List<DatabaseSet> result = new ArrayList<>();

		try {
			int columns = resultSet.getMetaData().getColumnCount();

			while (resultSet.next()) {
				DatabaseSet set = new DatabaseSet();

				for (int i = 0; i < columns; i++)
					set.setObject(resultSet.getMetaData().getColumnLabel(i + 1), resultSet.getObject(i + 1));

				result.add(set);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

}