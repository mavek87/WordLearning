package com.matteoveroni.bus.events;

import java.util.Locale;

/**
 *
 * @author Matteo Veroni
 */
public class EventRequestLanguageChange {

    private final Locale language;

    public EventRequestLanguageChange(Locale language) {
        this.language = language;
    }

    public Locale getLocale() {
        return language;
    }
}
