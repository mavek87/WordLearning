package com.matteoveroni.views.creation.model;

import com.matteoveroni.database.Database;
import com.matteoveroni.views.dictionary.model.pojo.Translation;
import com.matteoveroni.views.dictionary.model.pojo.Vocable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class CreationDAO {

    private static final Logger LOG = LoggerFactory.getLogger(CreationDAO.class);

    public boolean existsVocable(String str_vocable) {
        String queryExistsVocable = "SELECT COUNT (Vocable) AS NMB_VOCABLES_MATCHING FROM Dictionary WHERE Vocable='" + str_vocable + "' COLLATE NOCASE ORDER BY Vocable ASC";
        int size = 0;
        try (Connection connection = Database.getInstance().getConnection();
            PreparedStatement statementVocables = connection.prepareStatement(queryExistsVocable);
            ResultSet resultSetVocables = statementVocables.executeQuery()) {

            if (resultSetVocables.next()) {
                size = resultSetVocables.getInt("NMB_VOCABLES_MATCHING");
            }

        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
            Database.getInstance().printSQLException(ex);
        }
        return size > 0;
    }

    public void addVocable(String str_vocable) throws SQLException {
        String addVocableQuery = "INSERT INTO Dictionary (Vocable) VALUES('" + str_vocable + "')";
        try (Connection connection = Database.getInstance().getConnection();
            PreparedStatement statementVocables = connection.prepareStatement(addVocableQuery);) {
            
            statementVocables.execute();
            
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
            Database.getInstance().printSQLException(ex);
        }
    }

    // NEVER TESTED
    public List<Translation> getTranslationsForVocable(Vocable vocable) {
        List<Translation> translationsForVocable = new ArrayList<>();
        String queryGetTranslations = "SELECT t.Id,t.Translation FROM translations AS t LEFT JOIN DictionaryTranslations dt ON t.Id=dt.Translation_Id WHERE dt.Vocable_Id =" + vocable.getId() + " COLLATE NOCASE ORDER BY t.Translation ASC";

        try (Connection connection = Database.getInstance().getConnection();
            PreparedStatement statementVocables = connection.prepareStatement(queryGetTranslations);
            ResultSet resultSetTranslations = statementVocables.executeQuery()) {

            while (resultSetTranslations.next()) {
                long translationId = resultSetTranslations.getLong("Id");
                String translationName = resultSetTranslations.getString("Translation");
                if (translationId >= 0 && translationName != null) {
                    Translation translation = new Translation(translationId, translationName);
                    translationsForVocable.add(translation);
                } else {
                    break;
                }
            }

        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
            Database.getInstance().printSQLException(ex);
        }
        return translationsForVocable;
    }

    public boolean isTranslationForVocableEqualsToStringPresent(Vocable vocable, String string) {
        String queryGetTranslations = "SELECT COUNT (*) AS NUMB_RESULTS FROM translations AS t LEFT JOIN DictionaryTranslations dt ON t.Id=dt.Translation_Id WHERE dt.Vocable_Id=" + vocable.getId() + " AND t.Translation='" + string + "' COLLATE NOCASE ORDER BY t.Translation ASC";
        try (Connection connection = Database.getInstance().getConnection();
            PreparedStatement statementVocables
            = connection.prepareStatement(queryGetTranslations);
            ResultSet resultSetTranslations = statementVocables.executeQuery()) {

            int size = 0;
            if (resultSetTranslations.next()) {
                size = resultSetTranslations.getInt("NUMB_RESULTS");
                System.out.println("SELECT COUNT (*) FROM translations AS t LEFT JOIN DictionaryTranslations dt ON t.Id=dt.Translation_Id WHERE dt.Vocable_Id=" + vocable.getId() + " AND t.Translation='" + string + "' COLLATE NOCASE ORDER BY t.Translation ASC");
                System.out.println("Size " + size);
                return size > 0;
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
            Database.getInstance().printSQLException(ex);
        }
        return false;
    }
}
