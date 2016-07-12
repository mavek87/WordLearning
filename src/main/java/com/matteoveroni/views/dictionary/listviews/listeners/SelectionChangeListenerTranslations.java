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
public class SelectionChangeListenerTranslations implements ChangeListener<Translation> {

	private static final Logger LOG = LoggerFactory.getLogger(SelectionChangeListenerTranslations.class);

	@Override
	public void changed(ObservableValue<? extends Translation> observable, Translation oldValue, Translation newValue) {
		if (newValue != null) {
			LOG.debug("Translation SELECTED = " + newValue.getTranslation());
			EventBus.getDefault().post(new EventShowVocablesActionPanel(true));

		}
	}
}
