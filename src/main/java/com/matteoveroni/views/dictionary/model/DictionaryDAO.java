package com.matteoveroni.views.dictionary.model;

import com.matteoveroni.database.Database;
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

    public DictionaryPage getDictionaryPage(int offset, int pageDimension) {
        DictionaryPage dictionaryPage = new DictionaryPage(offset, pageDimension);
        String queryVocables = "SELECT Id, Vocable FROM dictionary ORDER BY Vocable ASC LIMIT ? OFFSET ?";
        String queryTranslations = "SELECT t.ID, t.Translation FROM Translations t LEFT JOIN DictionaryTranslations dt ON t.Id=dt.Translation_Id WHERE dt.Vocable_Id=?";

        try (Connection connection = Database.getInstance().getConnection();
            PreparedStatement statementVocables = connection.prepareStatement(queryVocables);
            PreparedStatement statementTranslations = connection.prepareStatement(queryTranslations)) {

            statementVocables.setInt(1, pageDimension);
            statementVocables.setInt(2, offset);

            populateDictionaryPageWithVocablesAndTranslations(dictionaryPage, statementVocables, statementTranslations);
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
            Database.getInstance().printSQLException(ex);
        }
        return dictionaryPage;
    }

    private void populateDictionaryPageWithVocablesAndTranslations(DictionaryPage dictionaryPage, final PreparedStatement statementVocables, final PreparedStatement statementTranslations) throws SQLException {
        try (ResultSet resultSetVocables = statementVocables.executeQuery()) {
            while (resultSetVocables.next()) {
                long vocableId = resultSetVocables.getLong("Id");
                String vocableName = resultSetVocables.getString("Vocable");
                
                if (vocableId >= 0 && vocableName != null) {
                    Vocable vocable = new Vocable(vocableId, vocableName);
                    LOG.debug("vocable => " + vocable.toString());
                    dictionaryPage.addVocable(vocable);
                    statementTranslations.setLong(1, vocableId);
                    populateDictionaryPageWithTranslations(dictionaryPage, statementTranslations, vocable);
                } else {
                    break;
                }
            }
        }
    }

    private void populateDictionaryPageWithTranslations(DictionaryPage dictionaryPage, final PreparedStatement statementTranslations, Vocable vocable) throws SQLException {
        try (ResultSet resultSetTranslations = statementTranslations.executeQuery()) {
            while (resultSetTranslations.next()) {
                long translationId = resultSetTranslations.getLong("Id");
                String translationName = resultSetTranslations.getString("Translation");
                
                if (translationId >= 0 && translationName != null) {
                    Translation translation = new Translation(translationId, translationName);
                    LOG.debug("translation => " + translation.toString());
                    dictionaryPage.addTranslationForVocable(translation, vocable);
                } else {
                    break;
                }
            }
        }

    }
}
