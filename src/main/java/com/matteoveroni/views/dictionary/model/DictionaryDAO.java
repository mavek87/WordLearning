package com.matteoveroni.views.dictionary.model;

import com.matteoveroni.App;
import com.matteoveroni.database.Database;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class DictionaryDAO {

	private final File fileDictionary = new File(App.PATH_DATA + File.separator + "dictionary.json");
	private final File fileDatabase = new File(App.PATH_DATABASE + File.separator + "database.sqlite");

	private static final Logger LOG = LoggerFactory.getLogger(DictionaryDAO.class);

	public DictionaryPage getDictionaryPage(int pageNumber, int pageDimension) {
		DictionaryPage dictionaryPage = new DictionaryPage(pageNumber, pageDimension);
		String query = "SELECT Id, Vocable FROM dictionary LIMIT ? OFFSET ?";
		try (Connection connection = Database.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, 2);
			statement.setInt(2, 0);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					String str_vocable = resultSet.getString("Vocable");
					System.out.println(str_vocable);
					dictionaryPage.addVocable(new Vocable(str_vocable));
					dictionaryPage.addVocableToDictionary(new Vocable(str_vocable));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return dictionaryPage;
	}
}
