package com.matteoveroni.views.edit.model;

import com.matteoveroni.database.Database;
import com.matteoveroni.views.dictionary.model.pojo.Translation;
import com.matteoveroni.views.dictionary.model.pojo.Vocable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class EditDAO {

    private static final Logger LOG = LoggerFactory.getLogger(EditDAO.class);

    public void saveEditedObject(Object objectToEdit, String newObjectToEditName) throws Exception {
        String query;
        if (objectToEdit instanceof Vocable) {
            query = "UPDATE Dictionary SET Vocable='" + newObjectToEditName + "' WHERE Id=" + ((Vocable) objectToEdit).getId();
        } else if (objectToEdit instanceof Translation) {
            query = "UPDATE Translations SET Translation='" + newObjectToEditName + "' WHERE Id=" + ((Translation) objectToEdit).getId();
        } else {
            throw new Exception("Object to save after editing is not a vocable nor a translation");
        }
        LOG.debug(query);
        try (Connection connection = Database.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.execute();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
            Database.getInstance().printSQLException(ex);
        }

    }
}
