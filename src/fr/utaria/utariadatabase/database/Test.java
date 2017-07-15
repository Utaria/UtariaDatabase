package fr.utaria.utariadatabase.database;

import fr.utaria.utariadatabase.result.DatabaseSet;

public class Test {

	private Test() {}


	public static void test() {
		DatabaseSet set = DatabaseManager.getDB("global").select().from("automessages").where("id = ?").attributes(2).find();

		System.out.println((set != null) ? set : "Aucun résultat");
	}

}