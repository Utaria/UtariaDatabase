package fr.utaria.utariadatabase.result;

import java.util.List;

public interface UpdateResult {

	int getRowsAffected();

	int getFirstInsertedId();

	List<Integer> getGeneratedKeys();

}
