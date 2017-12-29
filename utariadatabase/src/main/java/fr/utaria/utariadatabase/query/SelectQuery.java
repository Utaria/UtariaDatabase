package fr.utaria.utariadatabase.query;

import fr.utaria.utariadatabase.database.Database;
import fr.utaria.utariadatabase.result.DatabaseSet;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectQuery implements IQuery {

	private Database db;

	private String[] fields;

	private String[] froms;

	private String[] conditions;

	private String[] orders;

	private String[] groupsBy;

	private int[] limits;

	private Object[] attributes;

	private List<String[]> joins;

	private List<String[]> leftJoins;

	public SelectQuery(Database db, String... fields) {
		this.db = db;
		this.fields = fields;
		this.conditions = new String[0];
		this.orders = new String[0];
		this.groupsBy = new String[0];
		this.attributes = new String[0];
		this.limits = new int[0];

		this.joins = new ArrayList<>();
		this.leftJoins = new ArrayList<>();

		if (this.fields.length == 0)
			this.fields = new String[]{"*"};
	}

	public Object[] getAttributes() {
		return this.attributes;
	}

	public SelectQuery from(String... froms) {
		this.froms = froms;
		return this;
	}

	public SelectQuery join(String table, String field1, String field2) {
		this.joins.add(new String[]{table, field1, field2});
		return this;
	}

	public SelectQuery leftjoin(String table, String field1, String field2) {
		this.leftJoins.add(new String[]{table, field1, field2});
		return this;
	}

	public SelectQuery where(String... conditions) {
		this.conditions = conditions;
		return this;
	}

	public SelectQuery groupBy(String... groupsBy) {
		this.groupsBy = groupsBy;
		return this;
	}

	public SelectQuery order(String... orders) {
		this.orders = orders;
		return this;
	}

	public SelectQuery limit(int length) {
		this.limits = new int[]{length, -1};
		return this;
	}

	public SelectQuery limit(int begin, int end) {
		this.limits = new int[]{begin, end};
		return this;
	}

	public SelectQuery attributes(Object... attributes) {
		this.attributes = attributes;
		return this;
	}

	public DatabaseSet find() {
		// Pour optimiser la requête, on limite le nombre de résultat à 1
		this.limit(1);

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
		request.append(StringUtils.join(this.froms, ","));

		if (this.joins.size() > 0)
			for (String[] join : this.joins)
				request.append(" JOIN ").append(join[0]).append(" ON ").append(join[1]).append(" = ").append(join[2]);

		if (this.leftJoins.size() > 0)
			for (String[] join : this.leftJoins)
				request.append(" LEFT JOIN ").append(join[0]).append(" ON ").append(join[1]).append(" = ").append(join[2]);

		if (this.conditions.length > 0)
			request.append(" WHERE ").append(StringUtils.join(this.conditions, " AND "));
		if (this.groupsBy.length > 0)
			request.append(" GROUP BY ").append(StringUtils.join(this.groupsBy, ","));
		if (this.orders.length > 0)
			request.append(" ORDER BY ").append(StringUtils.join(this.orders, ","));
		if (this.limits.length > 0)
			if (this.limits[1] > 0) // limites
				request.append(" LIMIT ").append(this.limits[0]).append(",").append(this.limits[1]);
			else                    // nombre à récupérer
				request.append(" LIMIT ").append(this.limits[0]);


		return request.toString();
	}

}
