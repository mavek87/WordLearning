package com.matteoveroni.bus.events;

import java.util.Locale;

/**
 *
 * @author Matteo Veroni
 */
public class EventLanguageChanged {

    private final Locale language;

    public EventLanguageChanged(Locale language) {
        this.language = language;
    }

    public Locale getLocale() {
        return language;
    }
}
