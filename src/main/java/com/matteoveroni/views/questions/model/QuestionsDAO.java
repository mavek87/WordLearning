package com.matteoveroni.views.questions.model;

import com.matteoveroni.views.dictionary.model.*;
import com.matteoveroni.database.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
        String queryVocables = "SELECT Id, Vocable FROM dictionary ORDER BY Vocable ASC";
        List<Vocable> listOfAllVocables = new ArrayList<>();
        
        try (Connection connection = Database.getInstance().getConnection();
            PreparedStatement statementVocables = connection.prepareStatement(queryVocables);
            ResultSet resultSetVocables = statementVocables.executeQuery()) {

            Vocable vocable;
            while (resultSetVocables.next()) {
                vocable = new Vocable(resultSetVocables.getString("Vocable"));
                listOfAllVocables.add(vocable);
            }
            
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
            Database.getInstance().printSQLException(ex);
        }
        return listOfAllVocables;
    }

    private void populateDictionaryPageWithVocablesAndTranslations(DictionaryPage dictionaryPage, final PreparedStatement statementVocables, final PreparedStatement statementTranslations) throws SQLException {
        try (ResultSet resultSetVocables = statementVocables.executeQuery()) {
            while (resultSetVocables.next()) {
                // Check resultSetVocables.getString("Vocable") != null
                Vocable vocable = new Vocable(resultSetVocables.getString("Vocable"));
                LOG.debug("vocable => " + vocable.toString());
                dictionaryPage.addVocable(vocable);

                long vocableId = resultSetVocables.getLong("Id");
                statementTranslations.setLong(1, vocableId);
                populateDictionaryPageWithTranslations(dictionaryPage, statementTranslations, vocable);
            }
        }
    }

    private void populateDictionaryPageWithTranslations(DictionaryPage dictionaryPage, final PreparedStatement statementTranslations, Vocable vocable) throws SQLException {
        try (ResultSet resultSetTranslations = statementTranslations.executeQuery()) {
            while (resultSetTranslations.next()) {
                // Check resultSetTranslations.getString("Translation") != null
                Translation translation = new Translation(resultSetTranslations.getString("Translation"));
                LOG.debug("translation => " + translation.toString());
                dictionaryPage.addTranslationForVocable(translation, vocable);
            }
        }

    }
}
