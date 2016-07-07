package com.matteoveroni.views.dictionary.model;

import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Matteo Veroni
 */
public class WordManagementModel {

	private Dictionary dictionary;

	public WordManagementModel() {
	}

	@PostConstruct
	public void init() {
		this.dictionary = new Dictionary(1);
		// TODO => load from file or db

		
		Translations translations1 = new Translations();
		translations1.addTranslation("see");
		translations1.addTranslation("watch");
		dictionary.createWordAndTranslations("vedere", translations1);
//        dictionary.createWord("vedere");
//        dictionary.getTranslationsForWord("vedere").addTranslations(translations1);
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
