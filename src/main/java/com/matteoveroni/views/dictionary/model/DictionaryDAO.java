package com.matteoveroni.views.dictionary.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.annotation.PostConstruct;

/**
 *
 * @author Matteo Veroni
 */
public class DictionaryDAO {

	private static final String DATABASE_PATH = "com.matteoveroni.persistence.testDb.sqlite";

	@PostConstruct
	public void init() {
		Connection c = null;
		String sqlQuery = "CREATE TABLE 'Names' ('Id' INTEGER PRIMARY KEY  NOT NULL , 'Name' TEXT)";
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_PATH);
			PreparedStatement p = c.prepareStatement(sqlQuery);
			p.execute();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}

}
