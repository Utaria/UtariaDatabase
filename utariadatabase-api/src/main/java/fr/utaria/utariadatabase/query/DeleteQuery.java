package fr.utaria.utariadatabase.query;

import fr.utaria.utariadatabase.result.UpdateResult;

public interface DeleteQuery {

	DeleteQuery from(String table);

	DeleteQuery attributes(Object... attributes);

	UpdateResult execute();

}
