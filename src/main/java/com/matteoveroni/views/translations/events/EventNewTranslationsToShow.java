package com.matteoveroni.views.translations.events;

import com.matteoveroni.views.dictionary.model.pojo.Translation;
import java.util.List;

/**
 *
 * @author Matteo Veroni
 */
public class EventNewTranslationsToShow {

    private final List<Translation> translations;

    public EventNewTranslationsToShow(List<Translation> translations) {
        this.translations = translations;
    }

    public List<Translation> getTranslations() {
        return translations;
    }
}
