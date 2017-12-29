package fr.utaria.utariadatabase.database;

import fr.utaria.utariadatabase.query.DeleteQuery;
import fr.utaria.utariadatabase.query.SavingQuery;
import fr.utaria.utariadatabase.query.SelectQuery;

public interface Database {

	String getName();

	SelectQuery query(String... fields);

	SavingQuery update(String table);

	DeleteQuery delete(String... conditions);

}
