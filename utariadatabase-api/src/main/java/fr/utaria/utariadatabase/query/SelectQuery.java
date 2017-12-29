package fr.utaria.utariadatabase.query;

import fr.utaria.utariadatabase.result.DatabaseSet;

import java.util.List;

public interface SelectQuery {

	SelectQuery from(String... froms);

	SelectQuery join(String table, String field1, String field2);

	SelectQuery leftjoin(String table, String field1, String field2);

	SelectQuery where(String... conditions);

	SelectQuery groupBy(String... groupsBy);

	SelectQuery order(String... orders);

	SelectQuery limit(int length);

	SelectQuery limit(int begin, int end);

	SelectQuery attributes(Object... attributes);

	DatabaseSet find();

	List<DatabaseSet> findAll();

}
