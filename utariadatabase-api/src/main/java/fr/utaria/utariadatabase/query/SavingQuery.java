package fr.utaria.utariadatabase.query;

import fr.utaria.utariadatabase.result.UpdateResult;

public interface SavingQuery {

	SavingQuery fields(String... fields);

	SavingQuery values(Object... values);

	SavingQuery where(String... conditions);

	SavingQuery attributes(Object... attributes);

	UpdateResult execute();

}
