package com.matteoveroni.views.dictionary.model;

import java.util.List;

/**
 *
 * @author Matteo Veroni
 */
public class WordManagementModel {

	private Dictionary dictionary;

	public WordManagementModel(Dictionary dictionary) {
		this.dictionary = dictionary;
	}

	public Dictionary getDictionary() {
		return dictionary;
	}

	public void setDictionary(Dictionary dictionary) {
		this.dictionary = dictionary;
	}

	public void insertWord(String word, List<String> listOfTranslations) {
		if (!dictionary.containsTranslationsForWord(word)) {
			Translations translations = new Translations();
			translations.setTranslations(listOfTranslations);
			dictionary.createWordAndTranslations(word, translations);
		} else {
			dictionary.getTranslationsForWord(word).addTranslations(listOfTranslations);
		}
	}
}
