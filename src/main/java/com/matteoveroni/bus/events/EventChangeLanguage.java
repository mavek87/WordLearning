package com.matteoveroni.bus.events;

import java.util.Locale;

/**
 *
 * @author Matteo Veroni
 */
public class EventChangeLanguage {

    private final Locale language;

    public EventChangeLanguage(Locale language) {
        this.language = language;
    }

    public Locale getLocale() {
        return language;
    }
}
