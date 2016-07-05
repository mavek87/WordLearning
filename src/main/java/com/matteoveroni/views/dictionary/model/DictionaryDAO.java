package com.matteoveroni.views.dictionary.model;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.annotation.PostConstruct;

/**
 *
 * @author Matteo Veroni
 */
public class DictionaryDAO {

	private Object objectSaved;

	private static final String DATABASE_PATH = "com.matteoveroni.persistence.testDb.sqlite";

	@PostConstruct
	public void init() {
		Connection c = null;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_PATH);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}

//	@Subscribe
//	public void saveObject(EventSaveObject eventSave) {
//		String objectSerializedToJson = GsonSingleton.getInstance().toJson(eventSave.getObject());
//		objectSaved = objectSerializedToJson;
//		System.out.println(objectSerializedToJson);
//	}
	public void save(Object object) {
		objectSaved = object;
	}

	public void loadDictionary(long id) {

	}

//	@Subscribe
//	public void loadObject(EventLoadObject eventLoad) {
//		eventLoad
//	}
}
