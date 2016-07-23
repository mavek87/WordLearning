package com.matteoveroni.views.dictionary.model;

import com.matteoveroni.gson.GsonSingleton;

/**
 *
 * @author Matteo Veroni
 */
public class Translation {

    private final long id;
    private String translation;

    public Translation(long id, String translation) {
        this.id = id;
        this.translation = translation;
    }

    public long getId() {
        return id;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String objectToJson() {
        return GsonSingleton.getInstance().toJson(this);
    }

    @Override
    public String toString() {
        return getTranslation();
    }

}
