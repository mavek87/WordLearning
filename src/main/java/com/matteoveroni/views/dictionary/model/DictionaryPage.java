package com.matteoveroni.views.dictionary.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Matteo Veroni
 */
public class DictionaryPage {

	private int pageNumber;
	private int pageDimension;
	private List<Vocable> vocables = new ArrayList<>();
	private Map<Vocable, List<Translation>> dictionary = new HashMap<>();

	public DictionaryPage(int pageNumber, int pageDimension) {
		this.pageNumber = pageNumber;
		this.pageDimension = pageDimension;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageDimension() {
		return pageDimension;
	}

	public void setPageDimension(int pageDimension) {
		this.pageDimension = pageDimension;
	}

	public void setVocables(List<Vocable> vocables) {
		this.vocables = vocables;
	}

	public List<Vocable> getVocables() {
		return this.vocables;
	}

	public void addVocable(Vocable vocable) {
		vocables.add(vocable);
	}

	public Map<Vocable, List<Translation>> getDictionary() {
		return dictionary;
	}

	public void setDictionary(Map<Vocable, List<Translation>> dictionary) {
		this.dictionary = dictionary;
	}

	public void addTranslationForVocable(Translation translation, Vocable vocable) {
		List<Translation> translations = dictionary.get(vocable);
		if (translations == null) {
			translations = new ArrayList<>();
		}
		translations.add(translation);
		dictionary.put(vocable, translations);
	}

	public void addVocableToDictionary(Vocable vocable) {
		// TODO throw exception if vocable exists
		if(!dictionary.containsKey(vocable)) {
			dictionary.put(vocable, null);
		}
	}
}
