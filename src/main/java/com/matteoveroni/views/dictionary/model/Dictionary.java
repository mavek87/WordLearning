package com.matteoveroni.views.dictionary.model;

import com.matteoveroni.gson.GsonSingleton;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author Matteo Veroni
 */
public class Dictionary {

    private final Locale dictionaryLanguage;
    private final Locale translationsLanguage;
    private final Map<String, Translations> vocabulary = new HashMap<>();

    public Dictionary(Locale dictionaryLanguage, Locale translationsLanguage) {
        this.dictionaryLanguage = dictionaryLanguage;
        this.translationsLanguage = translationsLanguage;
    }

    public void createWordAndTranslations(String word, Translations translations) {
        vocabulary.put(word, translations);
    }

    public void removeWordAndTranslations(String word) {
        vocabulary.remove(word);
    }

    public Translations getTranslationsForWord(String word) {
        return vocabulary.get(word);
    }

    public void replaceTranslationsForWord(String word, Translations translations) {
        vocabulary.replace(word, translations);
    }

    public boolean containsTranslationsForWord(String word) {
        return vocabulary.containsKey(word) && !vocabulary.get(word).getTranslations().isEmpty();
    }

    public Locale getDictionaryLanguage() {
        return dictionaryLanguage;
    }

    public Locale getTranslationsLanguage() {
        return translationsLanguage;
    }
    
    public String objectToJson(){
        return GsonSingleton.getInstance().toJson(this);
    }
}
