package com.matteoveroni.views.dictionary.listviews.listeners;

import com.matteoveroni.views.dictionary.events.EventShowVocablesActionPanel;
import com.matteoveroni.views.dictionary.model.DictionaryPage;
import com.matteoveroni.views.dictionary.model.pojo.Translation;
import com.matteoveroni.views.dictionary.model.pojo.Vocable;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import org.greenrobot.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class SelectionChangeListenerVocables implements ChangeListener<Vocable> {

    private final DictionaryPage dictionaryPage;
    private final ListView<Translation> listViewTranslations;
    
    private final List<Translation> emptyArrayListTranslations = new ArrayList<>();

    private static final Logger LOG = LoggerFactory.getLogger(SelectionChangeListenerVocables.class);

    public SelectionChangeListenerVocables(DictionaryPage dictionaryPage, ListView<Translation> listViewTranslations) {
        this.dictionaryPage = dictionaryPage;
        this.listViewTranslations = listViewTranslations;
    }

    @Override
    public void changed(ObservableValue<? extends Vocable> observable, Vocable oldVocable, Vocable newVocable) {
        if (newVocable != null) {
            List<Translation> translations = dictionaryPage.getDictionary().get(newVocable);
            LOG.debug("SELECTION = " + newVocable.getName());

            if (translations == null) {
                translations = emptyArrayListTranslations;
            } else {
                EventBus.getDefault().post(new EventShowVocablesActionPanel(true));
            }
            populateTranslationsListView(translations);
        }
    }

    private void populateTranslationsListView(List<Translation> translations) {
        ObservableList<Translation> observableListOfTranslations = FXCollections.observableArrayList(translations);
        listViewTranslations.setItems(observableListOfTranslations);
    }

}
