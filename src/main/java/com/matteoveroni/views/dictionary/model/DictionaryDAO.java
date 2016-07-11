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

	public DictionaryPage getDictionaryPage(int offset, int pageDimension) {
		DictionaryPage dictionaryPage = new DictionaryPage(offset, pageDimension);
		String query = "SELECT Id, Vocable FROM dictionary LIMIT ? OFFSET ?";
		try (Connection connection = Database.getInstance().getConnection();
			PreparedStatement statementVocables = connection.prepareStatement(query);) {
			statementVocables.setInt(1, pageDimension);
			statementVocables.setInt(2, offset);
			try (ResultSet resultSetVocables = statementVocables.executeQuery()) {
				while (resultSetVocables.next()) {
					Vocable vocable = new Vocable(resultSetVocables.getString("Vocable"));
					dictionaryPage.addVocable(vocable);
					dictionaryPage.addVocableToDictionary(vocable);

					long long_id = resultSetVocables.getLong("Id");
					String queryTranslations = "SELECT t.Translation FROM Translations t LEFT JOIN DictionaryTranslations dt ON t.Id=dt.Translation_Id WHERE dt.Vocable_Id=" + long_id;
					try (PreparedStatement statementTranslations = connection.prepareStatement(queryTranslations);
						ResultSet resultSetTranslations = statementTranslations.executeQuery()) {
						while (resultSetTranslations.next()) {
							Translation translation = new Translation(resultSetTranslations.getString("Translation"));
							dictionaryPage.addTranslationForVocable(translation, vocable);
						}
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return dictionaryPage;
	}
}
