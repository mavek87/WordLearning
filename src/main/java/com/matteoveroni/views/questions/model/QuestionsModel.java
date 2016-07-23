package com.matteoveroni.views.questions.model;

import com.matteoveroni.views.dictionary.model.Translation;
import com.matteoveroni.views.dictionary.model.Vocable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Matteo Veroni
 */
public class QuestionsModel {

    private List<Vocable> vocables = new ArrayList<>();
    private Vocable currentVocable;
    private final QuestionsDAO questionsDAO = new QuestionsDAO();

    public QuestionsModel() {
        this.vocables = questionsDAO.getVocables();
    }

    public Vocable getRandomVocable() {
        int i = extractRandomIndex();
        currentVocable = vocables.remove(i);
        return currentVocable;
    }

    public Vocable getCurrentVocable() {
        return currentVocable;
    }

    protected void setVocables(List<Vocable> vocables) {
        this.vocables = vocables;
    }

    public boolean hasNextVocable() {
        return !vocables.isEmpty();
    }

    public List<Translation> getTranslationsForVocable(Vocable vocable) {
        return questionsDAO.getTranslationsForVocable(vocable);
    }

    public boolean isAnswerForVocableRight(String answer, Vocable vocable) {
        return questionsDAO.isTranslationForVocableEqualsToStringPresent(vocable, answer);
    }

    private int extractRandomIndex() {
        Random random = new Random();
        return random.nextInt(vocables.size());
    }

}
