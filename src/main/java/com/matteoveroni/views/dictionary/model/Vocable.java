package com.matteoveroni.views.dictionary.model;

/**
 *
 * @author Matteo Veroni
 */
public class Vocable {

    private final long id;
    private final String name;

    public Vocable(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }

}
