package com.matteoveroni.views.creation.model;

import com.matteoveroni.views.creation.model.exceptions.InvalidVocableException;
import com.matteoveroni.views.creation.model.exceptions.VocableExistsException;

/**
 *
 * @author Matteo Veroni
 */
public class CreationModel {

    private final CreationDAO creationDAO = new CreationDAO();

    public void saveStringToVocable(String str_vocable) throws Exception {
        if (str_vocable != null && !str_vocable.trim().isEmpty() && !creationDAO.existsVocable(str_vocable)) {
            creationDAO.addVocable(str_vocable);
        } else if (creationDAO.existsVocable(str_vocable)) {
            throw new VocableExistsException();
        } else {
            throw new InvalidVocableException();
        }
    }

}
