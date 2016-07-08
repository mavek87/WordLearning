package com.matteoveroni.persistence;

import com.matteoveroni.gson.GsonSingleton;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class PersistencyManager {

	private volatile static PersistencyManager persistenceUniqueInstance;
	private static final Logger LOG = LoggerFactory.getLogger(PersistencyManager.class);

	private PersistencyManager() {
	}

	public static final PersistencyManager getInstance() {
		PersistencyManager persitence_instance = persistenceUniqueInstance;
		if (persitence_instance == null) {
			synchronized (PersistencyManager.class) {
				persitence_instance = persistenceUniqueInstance;
				if (persitence_instance == null) {
					persistenceUniqueInstance = new PersistencyManager();
				}
			}
		}
		return persistenceUniqueInstance;
	}

	public void writeObjectToJsonFile(Object object, File jsonFile) throws IOException {
		String jsonDictionary = GsonSingleton.getInstance().toJson(object);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFile))) {
			writer.write(jsonDictionary);
		}
	}

	public <T> Object readObjectFromFile(T object, File jsonFile) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(jsonFile))) {
			return GsonSingleton.getInstance().fromJson(reader, object.getClass());
		}
	}
}
