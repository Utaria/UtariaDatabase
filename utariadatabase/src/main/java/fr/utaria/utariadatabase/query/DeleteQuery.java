package fr.utaria.utariadatabase.query;

import fr.utaria.utariadatabase.database.Database;
import fr.utaria.utariadatabase.result.UpdateResult;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;

public class DeleteQuery implements IQuery {

	private Database db;

	private String table;

	private String[] conditions;

	private Object[] attributes;

	public DeleteQuery(Database database, String... conditions) {
		this.db = database;

		this.conditions = conditions;
		this.attributes = new Object[0];
	}

	public Object[] getAttributes() {
		return this.attributes;
	}

	public DeleteQuery from(String table) {
		this.table = table;
		return this;
	}

	public DeleteQuery attributes(Object... attributes) {
		this.attributes = attributes;
		return this;
	}

	public UpdateResult execute() {
		try {
			return this.db.execUpdateStatement(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String getRequest() {
		StringBuilder request = new StringBuilder();

		// On fait les vérifications
		if (this.table == null)
			throw new NullPointerException("La table ne peut pas être vide !");

		// On créé la requête
		request.append("DELETE FROM ").append(this.table);
		request.append(" WHERE ").append(StringUtils.join(this.conditions, " AND "));

		return request.toString();
	}

}
