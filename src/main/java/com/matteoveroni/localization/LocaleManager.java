package com.matteoveroni.localization;

import com.matteoveroni.App;
import com.matteoveroni.bus.events.EventLanguageChanged;
import com.matteoveroni.bus.events.EventRequestLanguageChange;
import java.util.Locale;
import javax.annotation.PostConstruct;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 *
 * @author Matteo Veroni
 */
public class LocaleManager {

    private Locale applicationLocale;

    @PostConstruct
    public void init() {
        setLocaleIfSupported(Locale.getDefault());
        if (!isApplicationLocaleSetted()) {
            setLocale(App.DEFAULT_LOCALE);
        }
    }

    @Subscribe
    public void changeApplicationLocale(EventRequestLanguageChange eventRequestLanguageChange) {
        System.out.println(" changeApplicationLocale => " + this.getClass());
        setLocaleIfSupported(eventRequestLanguageChange.getLocale());
        System.out.println("language setted => " + applicationLocale);
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
        for (SupportedNation supportedLocale : SupportedNation.values()) {
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
