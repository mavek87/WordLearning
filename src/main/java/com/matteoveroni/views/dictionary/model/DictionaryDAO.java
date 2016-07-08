package com.matteoveroni.views.dictionary.model;

import com.matteoveroni.database.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class DictionaryDAO {
	
	private static final Logger LOG = LoggerFactory.getLogger(DictionaryDAO.class);
	
	public String getWord() throws SQLException {
		String sqlQuery = "SELECT * FROM Names";
		String word = "";
		LOG.info("Get Word query => " + sqlQuery);
		
		try (Connection connection = Database.getInstance().getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlQuery);) {
			
			while (resultSet.next()) {
				String sResult = resultSet.getString("Name");
				LOG.debug(sResult);
			}
			
		} catch (SQLException ex) {
			LOG.error(ex.getMessage());
		}
		
		return word;
	}
	
}
