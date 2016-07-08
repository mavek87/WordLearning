package com.matteoveroni.views.dictionary.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matteo Veroni
 */
public class DictionaryPage {

    private int pageNumber;
    private int pageDimension;
    private List<Vocable> vocables = new ArrayList<>();

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
}
