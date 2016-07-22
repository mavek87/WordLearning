package com.matteoveroni.views.questions.model;

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
    private final QuestionsDAO questiosDAO = new QuestionsDAO();

    public QuestionsModel() {
        this.vocables = questiosDAO.getVocables();
    }

    public Vocable getRandomVocable() {
        int i = estraiIndiceCasualePerListaStringhe();
        currentVocable = vocables.remove(i);
        return currentVocable;
    }

    public Vocable getCurrentVocable() {
        return currentVocable;
    }

    protected void setVocables(List<Vocable> vocables) {
        this.vocables = vocables;
    }
    
    public boolean hasNextVocable(){
        return !vocables.isEmpty();
    }

    private int estraiIndiceCasualePerListaStringhe() {
        Random random = new Random();
        return random.nextInt(vocables.size());
    }

}
