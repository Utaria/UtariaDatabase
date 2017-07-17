package fr.utaria.utariadatabase.result;

import java.util.List;

public class UpdateResult {

	private int           rowsAffected;
	private List<Integer> generatedKeys;

	public UpdateResult(int rowsAffected, List<Integer> generatedKeys) {
		this.rowsAffected  = rowsAffected;
		this.generatedKeys = generatedKeys;
	}


	public int getRowsAffected() {
		return this.rowsAffected;
	}

	public int getFirstInsertedId() {
		return (this.generatedKeys.size() > 0) ? this.generatedKeys.get(0) : -1;
	}

	public List<Integer> getGeneratedKeys() {
		return this.generatedKeys;
	}


	@Override
	public String toString() {
		return "{UpdateResult #" + this.hashCode() + " (rowsAffected=" + this.rowsAffected + " generatedKeys=" + this.generatedKeys + ")}";
	}

}
