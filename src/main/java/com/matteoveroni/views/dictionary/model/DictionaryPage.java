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

	private int offset;
	private int pageDimension;
	private Map<Vocable, List<Translation>> dictionary = new HashMap<>();

	public DictionaryPage(int offset, int pageDimension) {
		this.offset = offset;
		this.pageDimension = pageDimension;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getPageDimension() {
		return pageDimension;
	}

	public void setPageDimension(int pageDimension) {
		this.pageDimension = pageDimension;
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

	public void addVocable(Vocable vocable) {
		// TODO throw exception if vocable exists
		if(!dictionary.containsKey(vocable)) {
			dictionary.put(vocable, null);
		}
	}
}
