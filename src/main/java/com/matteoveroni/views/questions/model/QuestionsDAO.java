package com.matteoveroni.views.questions.model;

import com.matteoveroni.views.dictionary.model.*;
import com.matteoveroni.database.Database;
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
public class QuestionsDAO {

    private static final Logger LOG = LoggerFactory.getLogger(QuestionsDAO.class);

    public List<Vocable> getVocables() {
        String queryGetVocables = "SELECT Id, Vocable FROM Dictionary ORDER BY Vocable ASC";
        List<Vocable> listOfAllVocables = new ArrayList<>();

        try (Connection connection = Database.getInstance().getConnection();
            PreparedStatement statementVocables = connection.prepareStatement(queryGetVocables);
            ResultSet resultSetVocables = statementVocables.executeQuery()) {

            while (resultSetVocables.next()) {
                long vocableId = resultSetVocables.getLong("Id");
                String vocableName = resultSetVocables.getString("Vocable");
                if (vocableId >= 0 && vocableName != null) {
                    Vocable vocable = new Vocable(vocableId, vocableName);
                    listOfAllVocables.add(vocable);
                } else {
                    break;
                }
            }

        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
            Database.getInstance().printSQLException(ex);
        }
        return listOfAllVocables;
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
