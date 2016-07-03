package com.matteoveroni.localization;

import java.util.Locale;

/**
 *
 * @author Matteo Veroni
 */
public enum SupportedCountries {

    USA(Locale.US), ITALY(Locale.ITALY);

    private final Locale locale;

    SupportedCountries(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
}
