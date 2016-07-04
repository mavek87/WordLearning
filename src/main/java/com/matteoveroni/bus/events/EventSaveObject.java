package com.matteoveroni.bus.events;

/**
 *
 * @author Matteo Veroni
 */
public class EventSaveObject {

	private final Object object;

	public EventSaveObject(Object object) {
		this.object = object;
	}

	public Object getObject() {
		return object;
	}

}
