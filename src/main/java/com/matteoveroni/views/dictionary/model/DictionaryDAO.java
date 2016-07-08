package com.matteoveroni.views.dictionary.model;

import com.matteoveroni.App;
import com.matteoveroni.persistence.PersistencyManager;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class DictionaryDAO {

	private static final Logger LOG = LoggerFactory.getLogger(DictionaryDAO.class);

	private final File fileDictionary = new File(App.PATH_DATA + File.separator + "dictionary.json");
	private Dictionary dictionary = new Dictionary();

	public Dictionary getDictionary() {
		try {
			dictionary = (Dictionary) PersistencyManager.getInstance().readObjectFromFile(dictionary, fileDictionary);
		} catch (IOException ex) {
			LOG.error(ex.getMessage());
		}
		return dictionary;
	}

	public void saveDictionary(Dictionary dictionary) {
		try {
			if (dictionary != null) {
				PersistencyManager.getInstance().writeObjectToJsonFile(dictionary, fileDictionary);
			}
		} catch (IOException ex) {
		}
	}

}
