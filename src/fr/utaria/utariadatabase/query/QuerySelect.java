package fr.utaria.utariadatabase.query;

import fr.utaria.utariadatabase.database.Database;
import fr.utaria.utariadatabase.result.DatabaseSet;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.List;

public class QuerySelect implements IQuery {

	private Database db;
	private String[] fields;
	private String[] froms;
	private String[] conditions;
	private String[] orders;
	private Object[] attributes;


	public QuerySelect(Database db, String... fields) {
		this.db         = db;
		this.fields     = fields;
		this.conditions = new String[0];
		this.orders     = new String[0];
		this.attributes = new String[0];

		if (this.fields.length == 0)
			this.fields = new String[]{ "*" };
	}

	public Object[] getAttributes() { return this.attributes; }



	public QuerySelect from(String ...froms) {
		this.froms = froms;
		return this;
	}
	public QuerySelect where(String ...conditions) {
		this.conditions = conditions;
		return this;
	}
	public QuerySelect order(String ...orders) {
		this.orders = orders;
		return this;
	}
	public QuerySelect attributes(Object ...attributes) {
		this.attributes = attributes;
		return this;
	}


	public DatabaseSet       find   () {
		List<DatabaseSet> sets = this.findAll();
		return (sets == null || sets.size() == 0) ? null : sets.get(0);
	}
	public List<DatabaseSet> findAll() {
		try {
			return this.db.execQueryStatement(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public String getRequest() {
		StringBuilder request = new StringBuilder("SELECT ");

		request.append(StringUtils.join(this.fields, ","));
		request.append(" FROM ");
		request.append(StringUtils.join(this.froms , ","));

		if (this.conditions.length > 0)
			request.append(" WHERE ").append(StringUtils.join(this.conditions, "AND "));
		if (this.orders.length     > 0)
			request.append(" ORDER BY ").append(StringUtils.join(this.orders, ","));

		return request.toString();
	}


}
