<p align="center">
    <img src="https://i.imgur.com/rNl9EhX.png" alt="Logo de Utaria" width="500">
</p>

<h1 align="center">UtariaDatabase</h1>
<h4 align="center">
Plugin d'interaction avec les bases de donnnées d'Utaria
<br>
Fonctionne avec Spigot, Paper et Waterfall 1.8-1.12.
</h4>

<p align="center">
    <a href="https://twitter.com/Utaria_FR">
        <img src="https://img.shields.io/twitter/follow/Utaria_FR.svg?style=social&label=Suivez-nous%20sur%20Twitter" alt="Suivez-nous">
    </a>
    <a href="https://discord.gg/UNgPrPk">
        <img src="https://img.shields.io/discord/220472433344380928.svg" alt="Discord">
    </a>
    <br>
    <a href="https://github.com/Utaria/UtariaDatabase/commits/master">
        <img src="https://img.shields.io/github/last-commit/Utaria/UtariaDatabase/master.svg" alt="GitHub last commit">
    </a>
    <a href="https://github.com/Utaria/UtariaDatabase/blob/master/LICENSE.md">
        <img src="https://img.shields.io/badge/Licenses-CC%20BY--SA%203.0%20&%20MIT-green.svg" alt="License">
    </a>
</p>

>
> Ce plugin a été réalisé dans le cadre de la mise en place et l'exploitation du serveur de jeu Survie pour "UTARIA" de décembre 2016 à mars 2018. Le présent dépôt a été mis en place peu de temps avant la fin de la V1.
> Développé par [Utarwyn](https://github.com/utarwyn). 
>


## En quoi consiste ce programme ?

Ce programme a été réalisé dans le but de rendre simple une tâche qui peut s'avérer très complexe pour les développeurs débutants : connecter un plugin Minecraft à une ou plusieurs bases de données.
Ses principaux objectifs : simplicité, performance, polyvalence.

Il intègre de nombreuses fonctionnalités, comme :
* Support de **MySQL** et des bases **SQLite** (via des fichiers .db)
* Une **gestion rapide et performante des connexions** aux bases de données (système de piscine)
* Une **API simple mais complète** pour les développeurs de plugin Minecraft
* Un **système de migration intégré** pour collaborer facilement et gérer les déploiements en production
* Fonctionne avec **PaperSpigot et Waterfall** *(seuls testés)*
* Support des connexions via tunnel SSH, du multi-requêtes et de base SQLite interne.

> :warning: Attention ! Ce plugin a été réalisé dans le cadre du projet **UTARIA**, il n'est donc pas utilisable tel quel. Si vous souhaitez l'utiliser, de nombreux changements sont à prévoir. Dans ce cas, il serait plus judicieux de prendre appui dessus pour réaliser votre **propre plugin**.

## Un exemple d'utilisation en cas réel ?

```JAVA
// Enregistrement d'une base de données
DatabaseManager.registerDatabase("MASUPERBASE");

// Lancement d'une requête dessus
Database maSuperBase = DatabaseManager.getDB("MASUPERBASE");

SelectQuery selectQuery = maSuperBase.select("prenom", "email", "age")
				.from("utilisateurs")
				.where("age > 18", "ville = ?")
				.order("age DESC")
				.attributes("PARIS");

// On analyse les résultats avec :
for (DatabaseSet set : selectQuery.findAll()) {
	System.out.println("prenom = " + set.getString("prenom"));
	System.out.println("email  = " + set.getString("email"));
	System.out.println("age    = " + set.getInteger("age"));
	System.out.println();
}
```

## Quelles technologies utilise-t-il ?

Le programme nécessite **Java8**, **PaperSpigot 1.8.8 ou Waterfall** et utilise les programmes tiers suivants :

* Librairie DBCP2 par Apache [disponible ici](https://commons.apache.org/proper/commons-dbcp/).
* Connecteur JAVA (JDBC) pour serveurs MySQL [disponible ici](https://dev.mysql.com/downloads/connector/j/).
* Connecteur JAVA (JDBC) pour serveurs SQLite [disponible ici](https://bitbucket.org/xerial/sqlite-jdbc).
* FlywayDB pour executer des migrations sur les base de données [(disponible ici)](https://flywaydb.org/).

L'outil utilise **Maven** donc toutes ces librairies sont incluses sans manipulation de votre part.

## Comment je fais pour contribuer au code ?

> :warning: Impossible pour le moment. Contactez-moi en cas de problème via mon compte Twitter [@Utarwyn](https://twitter.com/Utarwyn).

## Remerciements

On remercie tous les participants de notre belle aventure et les joueurs, sans qui nous ne serions pas là aujourd'hui. :fire: :heart_eyes: 

## Licence

> Voir le fichier [LICENSE.md](https://github.com/Utaria/UtariaDatabase/blob/master/LICENSE.md)

---

> GitHub [@Utaria](https://github.com/utaria) &nbsp;&middot;&nbsp;
> Twitter [@Utaria_FR](https://twitter.com/Utaria_FR)