package com.matteoveroni.database;

import com.matteoveroni.App;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author Matteo Veroni
 */
public class Database {

	private volatile static Database database_instance;

	private static final String DATABASE_PATH = App.PATH + File.separator + "Database" + File.separator + "database.sqlite";

	public static void connect() {
		Connection c = null;
		String sqlQuery = "CREATE TABLE 'Names' ('Id' INTEGER PRIMARY KEY  NOT NULL , 'Name' TEXT)";
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_PATH);
			PreparedStatement p = c.prepareStatement(sqlQuery);
			p.execute();
			System.out.println("Opened database successfully");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
	}

}
