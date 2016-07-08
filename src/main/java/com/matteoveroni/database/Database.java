package com.matteoveroni.database;

import com.matteoveroni.App;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class Database {

	private volatile static Database databaseUniqueInstance;
	private static final String DATABASE_PATH = App.PATH + File.separator + "Database" + File.separator + "database.sqlite";
	private static final Logger LOG = LoggerFactory.getLogger(Database.class);

	private Database() {
	}

	public static final Database getInstance() {
		Database database_instance = databaseUniqueInstance;
		if (database_instance == null) {
			synchronized (Database.class) {
				database_instance = databaseUniqueInstance;
				if (database_instance == null) {
					databaseUniqueInstance = new Database();
				}
			}
		}
		return databaseUniqueInstance;
	}

	public void createDb() {
		String sqlQuery = "CREATE TABLE 'Names' ('Id' INTEGER PRIMARY KEY  NOT NULL , 'Name' TEXT)";
		LOG.info("Creating db, query => " + sqlQuery);
		
		try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_PATH);
			PreparedStatement statement = connection.prepareStatement(sqlQuery);) {

			statement.execute();
			LOG.info("Database created successfully");

		} catch (SQLException ex) {
			LOG.error(ex.getMessage());
		}
	}

	public Connection getConnection() throws SQLException {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_PATH);
			LOG.info("Opened database successfully");
		} catch (SQLException ex) {
			LOG.error(ex.getClass().getName() + ": " + ex.getMessage());
			if (connection != null) {
				connection.close();
			}
			System.exit(0);
		}
		return connection;
	}
}
