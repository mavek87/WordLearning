package com.matteoveroni.views.dictionary.model;

import com.matteoveroni.gson.GsonSingleton;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matteo Veroni
 */
public class Translations {

    List<String> translations = new ArrayList<>();

    public Translations() {
    }

    public List<String> getEveryTranslation() {
        return translations;
    }

    public void setTranslations(List<String> translations) {
        this.translations = translations;
    }

    public int size() {
        return translations.size();
    }

    public boolean isEmpty() {
        return translations.isEmpty();
    }

    public boolean addTranslation(String translation) {
        return translations.add(translation);
    }

    public void addTranslations(List<String> translations) {
        this.translations.addAll(translations);
    }

    public boolean removeTranslation(String translation) {
        return translations.remove(translation);
    }

    public String objectToJson() {
        return GsonSingleton.getInstance().toJson(this);
    }
}
