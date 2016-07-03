package com.matteoveroni.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author Matteo Veroni
 */
public class GsonSingleton {

	private static final GsonBuilder GSON_BUILDER = new GsonBuilder();
	private volatile static Gson uniqueGsonInstance;

	private GsonSingleton() {
	}

	public static Gson getInstance() {
        Gson gsonInstance = GsonSingleton.uniqueGsonInstance;
        if (gsonInstance == null) {
			synchronized (GsonSingleton.class) {
                gsonInstance = GsonSingleton.uniqueGsonInstance;
				if (gsonInstance == null) {
					GsonSingleton.uniqueGsonInstance = gsonInstance = GSON_BUILDER.create();
				}
			}
		}
		return gsonInstance;
	}
}
