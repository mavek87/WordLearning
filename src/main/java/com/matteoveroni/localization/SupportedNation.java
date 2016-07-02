package com.matteoveroni.localization;

import java.util.Locale;

/**
 *
 * @author Matteo Veroni
 */
public enum SupportedNation {

    USA(Locale.US), ITALY(Locale.ITALY);

    private final Locale locale;

    SupportedNation(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
}
