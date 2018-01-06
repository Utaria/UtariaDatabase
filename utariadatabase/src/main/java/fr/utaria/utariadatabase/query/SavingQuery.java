package fr.utaria.utariadatabase.query;

import fr.utaria.utariadatabase.database.Database;
import fr.utaria.utariadatabase.result.UpdateResult;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SavingQuery implements IQuery {

	private Database db;

	private String[] fields;

	private String table;

	private String[] conditions;

	private Object[] values;

	private List<Object> attributes;

	private boolean replaceIfExists;

	public SavingQuery(Database database, String table) {
		this.db = database;
		this.table = table;

		this.fields = new String[0];
		this.conditions = new String[0];
		this.attributes = new ArrayList<>();
	}

	public Object[] getAttributes() {
		return this.attributes.toArray();
	}

	public SavingQuery fields(String... fields) {
		this.fields = fields;
		return this;
	}

	public SavingQuery values(Object... values) {
		this.values = values;
		this.attributes(values);

		return this;
	}

	public SavingQuery where(String... conditions) {
		this.conditions = conditions;
		return this;
	}

	public SavingQuery attributes(Object... attributes) {
		Collections.addAll(this.attributes, attributes);
		return this;
	}

	public SavingQuery replaceIfExists() {
		this.replaceIfExists = true;
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

		// On fait toutes les vérifications
		if (this.table == null)
			throw new NullPointerException("La table ne peut pas être vide !");
		if (this.fields.length == 0 && this.conditions.length > 0)
			throw new IllegalArgumentException("Vous devez préciser les champs à mettre à jour !");
		if (this.fields.length != this.values.length)
			if (this.conditions.length > 0 || this.fields.length > 0)
				throw new IllegalArgumentException("Le nombre de champs doit être égal au nombre de valeurs !");

		// On commence à créer la requête
		if (this.conditions.length > 0) {
			request.append("UPDATE `").append(this.table).append('`');

			request.append(" SET");
			for (String field : this.fields)
				request.append(" `").append(field).append("` = ?,");

			if (this.fields.length > 0)
				request.deleteCharAt(request.length() - 1);

			request.append(" WHERE ").append(StringUtils.join(this.conditions, " AND "));
		} else {
			String action = "INSERT";

			if (this.replaceIfExists)
				action = "REPLACE";

			request.append(action).append(" INTO `").append(this.table).append('`');

			if (this.fields.length > 0)
				request.append("(").append(StringUtils.join(this.fields, ",")).append(")");

			request.append(" VALUES ");
			request.append("(").append(this.generateAttrValues(this.values)).append(")");
		}


		return request.toString();
	}

	private String generateAttrValues(Object[] values) {
		String[] tab = new String[values.length];

		for (int i = 0; i < tab.length; i++) tab[i] = "?";

		return StringUtils.join(tab, ",");
	}

}
