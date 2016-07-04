package com.matteoveroni.dao;

import com.matteoveroni.bus.events.EventSaveObject;
import com.matteoveroni.gson.GsonSingleton;
import org.greenrobot.eventbus.Subscribe;

/**
 *
 * @author Matteo Veroni
 */
public class DaoPrototype {

	private Object objectSaved;

//	@Subscribe
//	public void saveObject(EventSaveObject eventSave) {
//		String objectSerializedToJson = GsonSingleton.getInstance().toJson(eventSave.getObject());
//		objectSaved = objectSerializedToJson;
//		System.out.println(objectSerializedToJson);
//	}
	
	public void save(Object object){
		objectSaved = object;
	}
	
	public void loadDictionary(long id){
		
	}
	
	

//	@Subscribe
//	public void loadObject(EventLoadObject eventLoad) {
//		eventLoad
//	}

}
