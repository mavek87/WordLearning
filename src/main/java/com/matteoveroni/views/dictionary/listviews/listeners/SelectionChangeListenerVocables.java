package com.matteoveroni.views.dictionary.listviews.listeners;

import com.matteoveroni.views.dictionary.bus.events.EventShowVocablesActionPanel;
import com.matteoveroni.views.dictionary.model.DictionaryPage;
import com.matteoveroni.views.dictionary.model.Translation;
import com.matteoveroni.views.dictionary.model.Vocable;
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

	private static final Logger LOG = LoggerFactory.getLogger(SelectionChangeListenerVocables.class);

	public SelectionChangeListenerVocables(DictionaryPage dictionaryPage, ListView<Translation> listViewTranslations) {
		this.dictionaryPage = dictionaryPage;
		this.listViewTranslations = listViewTranslations;
	}

	@Override
	public void changed(ObservableValue<? extends Vocable> observable, Vocable oldValue, Vocable newValue) {
		if (newValue != null) {
			List<Translation> translations = dictionaryPage.getDictionary().get(newValue);
			LOG.debug("SELECTION = " + newValue.getName());

			if (translations != null) {
				populateTranslationsListView(translations);
				EventBus.getDefault().post(new EventShowVocablesActionPanel(true));
			}
		}
	}

	private void populateTranslationsListView(List<Translation> translations) {
		ObservableList<Translation> observableListOfTranslations = FXCollections.observableArrayList(translations);
		listViewTranslations.setItems(observableListOfTranslations);
	}

}
