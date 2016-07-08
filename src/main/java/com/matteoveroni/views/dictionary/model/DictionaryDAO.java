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

    private static final Logger LOG = LoggerFactory.getLogger(DictionaryDAO.class);

    private final File fileDictionary = new File(App.PATH_DATA + File.separator + "dictionary.json");
    private final File fileDatabase = new File(App.PATH_DATABASE + File.separator + "database.sqlite");
//	private Dictionary dictionary = new Dictionary();
//
//	public Dictionary getDictionary() {
//		try {
//			dictionary = (Dictionary) PersistencyManager.getInstance().readObjectFromFile(dictionary, fileDictionary);
//		} catch (IOException ex) {
//			LOG.error(ex.getMessage());
//		}
//		return dictionary;
//	}
//
//	public void saveDictionary(Dictionary dictionary) {
//		try {
//			if (dictionary != null) {
//				PersistencyManager.getInstance().writeObjectToJsonFile(dictionary, fileDictionary);
//			}
//		} catch (IOException ex) {
//		}
//	}

    private DictionaryPage dictionaryPage;

    public void getDictionaryPage(int pageNumber, int pageDimension) {
        String query = "SELECT Id, Vocable FROM dictionary LIMIT ? OFFSET ?";
        try (Connection connection = Database.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setInt(1, 2);
            statement.setInt(2, 0);
            try (ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()){
                    String vocable = resultSet.getString("Vocable");
                    System.out.println(vocable);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
    }
}