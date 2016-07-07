package com.matteoveroni.views.dictionary.model;

import com.matteoveroni.database.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class DictionaryDAO {

    private static final Logger LOG = LoggerFactory.getLogger(DictionaryDAO.class);

    public String getWord() throws SQLException {
        Connection connection = Database.getInstance().getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String sqlQuery = "SELECT * FROM Names";
        String word = "";
        LOG.info("Get Word query => " + sqlQuery);

        if (connection != null) {
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sqlQuery);

                while (resultSet.next()) {
                    String sResult = resultSet.getString("Name");
                    LOG.debug(sResult);
                }

//            List ar = Arrays.asList(resultSet.getArray("Name"));
//            System.out.println(ar.get(0));
            } catch (Exception ex) {
                LOG.error(ex.getClass().getName() + ": " + ex.getMessage());
                System.exit(0);
            } finally {
                DbUtils.closeQuietly(connection, statement, resultSet);
            }
        }
        return word;
    }

}
