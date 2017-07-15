package fr.utaria.utariadatabase.database;

import fr.utaria.utariadatabase.result.DatabaseSet;

public class Test extends DatabaseAccessor {

	public Test() {
		super("global");

		this.run();
	}


	private void run() {
		// Test d'une requête de recherche complète
		DatabaseSet set = this.getDB().select().from("kits")
				                      .join("ranks", "ranks.id", "kits.rank_id").where("kits.id = ?")
				                      .attributes(2).find();

		System.out.println("Test #01 : " + ((set != null) ? set : "Aucun résultat"));

		// Test d'une insertion dans la BDD
		int rowsCreated = this.getDB().update("config").values(15, "test", "Mon super test !").execute();
		System.out.println("Lignes crées = " + rowsCreated);

		// Test d'une mise à jour de la BDD
		int rowsUpdated = this.getDB().update("config").fields("value").values(20).where("id = ?").attributes(1).execute();
		System.out.println("Lignes mises à jour = " + rowsUpdated);

		// Test d'une suppression dans la BDD
		int rowsDeleted = this.getDB().delete("id = ?").from("config").attributes(15).execute();
		System.out.println("Lignes supprimées = " + rowsDeleted);
	}

}