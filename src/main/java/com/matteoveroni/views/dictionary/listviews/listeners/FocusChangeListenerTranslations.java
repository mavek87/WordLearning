package com.matteoveroni.views.dictionary.listviews.listeners;

import com.matteoveroni.views.dictionary.bus.events.EventShowTranslationsActionPanel;
import com.matteoveroni.views.dictionary.model.Translation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import org.greenrobot.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class FocusChangeListenerTranslations implements ChangeListener<Boolean> {

	private final ListView<Translation> listview_translations;

	private static final Logger LOG = LoggerFactory.getLogger(FocusChangeListenerTranslations.class);

	public FocusChangeListenerTranslations(ListView<Translation> listview_translations) {
		this.listview_translations = listview_translations;
	}

	@Override
	public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		LOG.debug("FOCUS = " + newValue);
		if (newValue == true) {
			if (listview_translations.getSelectionModel().getSelectedItem() == null) {
				if (listview_translations.getItems() != null && !listview_translations.getItems().isEmpty()) {
					listview_translations.getSelectionModel().selectFirst();
				}
			}
		} else {
			listview_translations.getSelectionModel().clearSelection();
			listview_translations.getSelectionModel().select(null);
			EventBus.getDefault().post(new EventShowTranslationsActionPanel(false));
		}
	}
}
