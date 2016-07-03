package com.matteoveroni.localization;

import com.matteoveroni.App;
import com.matteoveroni.bus.events.EventLanguageChanged;
import com.matteoveroni.bus.events.EventChangeLanguage;
import java.util.Locale;
import javax.annotation.PostConstruct;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class LocaleManager {

    private Locale applicationLocale;
    private static final Logger LOG = LoggerFactory.getLogger(LocaleManager.class);

    @PostConstruct
    public void init() {
        setLocaleIfSupported(Locale.getDefault());
        if (!isApplicationLocaleSetted()) {
            setLocale(App.DEFAULT_LOCALE);
        }
    }

    @Subscribe
    public void changeApplicationLocale(EventChangeLanguage eventRequestLanguageChange) {
        LOG.info("Changing application locale");
        setLocaleIfSupported(eventRequestLanguageChange.getLocale());
        LOG.info("locale setted => " + applicationLocale);
    }

    public Locale getApplicationLocale() {
        return applicationLocale;
    }

    private void setLocaleIfSupported(Locale locale) {
        if (isLocaleSupported(locale)) {
            setLocale(locale);
        }
    }

    private boolean isLocaleSupported(Locale locale) {
        for (SupportedCountries supportedLocale : SupportedCountries.values()) {
            if (supportedLocale.getLocale().equals(locale)) {
                return true;
            }
        }
        return false;
    }

    private boolean isApplicationLocaleSetted() {
        return applicationLocale != null;
    }

    private void setLocale(Locale locale) {
        applicationLocale = locale;
        Locale.setDefault(locale);
        EventBus.getDefault().post(new EventLanguageChanged(applicationLocale));
    }
}
