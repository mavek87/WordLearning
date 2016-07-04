package com.matteoveroni.views.dictionary.model;

import com.matteoveroni.gson.GsonSingleton;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Matteo Veroni
 */
public class Dictionary {

	private long id;
	private final Map<String, Translations> vocabulary = new HashMap<>();

	public Dictionary(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void createWord(String word) {
		vocabulary.put(word, null);
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
		return vocabulary.containsKey(word) && !vocabulary.get(word).getEveryTranslation().isEmpty();
	}

	public String objectToJson() {
		return GsonSingleton.getInstance().toJson(this);
	}
}
