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
    private static final String DATABASE_PATH = App.PATH_DATABASE + File.separator + "database.sqlite";
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

    public void createDb() throws ClassNotFoundException {
        LOG.info("Database creation started");

        String queryCreateDictionary = "CREATE TABLE 'Dictionary' ('Id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, 'Vocable' TEXT)";
        String queryCreateDictionaryTranslations = "CREATE TABLE 'DictionaryTranslations' ('Vocable_Id' INTEGER, 'Translation_Id' INTEGER)";
        String queryCreateTranslations = "CREATE TABLE `Translations` ('Id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, 'Translation' TEXT)";

        LOG.debug("queryCreateDictionary => " + queryCreateDictionary);
        LOG.debug("queryCreateDictionaryTranslations => " + queryCreateDictionaryTranslations);
        LOG.debug("queryCreateTranslations => " + queryCreateTranslations);

        try (Connection connection = getConnection();
            PreparedStatement createDictionaryStatement = connection.prepareStatement(queryCreateDictionary);
            PreparedStatement createDictionaryTranslationsStatement = connection.prepareStatement(queryCreateDictionaryTranslations);
            PreparedStatement createTranslationsStatement = connection.prepareStatement(queryCreateTranslations);) {

            createDictionaryStatement.execute();
            createDictionaryTranslationsStatement.execute();
            createTranslationsStatement.execute();

            LOG.info("Database created successfully");

        } catch (SQLException ex) {
            printSQLException(ex);
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + DATABASE_PATH);
    }

    private static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {

                LOG.error("SQLState: " + ((SQLException) e).getSQLState());
                LOG.error("Error Code: " + ((SQLException) e).getErrorCode());
                LOG.error("Message: " + e.getMessage());

                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
