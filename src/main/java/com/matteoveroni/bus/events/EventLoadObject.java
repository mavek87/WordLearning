package com.matteoveroni.bus.events;

/**
 *
 * @author Matteo Veroni
 */
public class EventLoadObject {

	private final Object objectToLoad;
	private final Object objectInstanceToServe;

	public EventLoadObject(Object objectToLoad, Object objectInstanceToServe) {
		this.objectToLoad = objectToLoad;
		this.objectInstanceToServe = objectInstanceToServe;
	}

	public Object getObjectToLoad() {
		return objectToLoad;
	}

	public Object getObjectInstanceToServe() {
		return objectInstanceToServe;
	}

}
