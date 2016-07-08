package com.matteoveroni.views.dictionary.model;

import com.matteoveroni.gson.GsonSingleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Matteo Veroni
 */
public class Dictionary {

	private final Map<String, List<Translation>> vocabulary = new HashMap<>();
	
	public void createWord(String word) {
		vocabulary.put(word, null);
	}

	public void createWordAndTranslations(String word, List<Translation> translations) {
		vocabulary.put(word, translations);
	}

	public void addTranslationToWord(Translation newTranslation, String word) {
		List<Translation> translationsOfWord = vocabulary.get(word);
		translationsOfWord.add(newTranslation);
		vocabulary.put(word, translationsOfWord);
	}

	public void removeWordAndTranslations(String word) {
		vocabulary.remove(word);
	}

	public List<Translation> getTranslationsForWord(String word) {
		return vocabulary.get(word);
	}

	public void replaceTranslationsForWord(String word, List<Translation> translations) {
		vocabulary.replace(word, translations);
	}

	public boolean containsTranslationsForWord(String word) {
		return !vocabulary.get(word).isEmpty();
	}

	public String convertToJson() {
		return GsonSingleton.getInstance().toJson(this);
	}
}
