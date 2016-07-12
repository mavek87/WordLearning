package com.matteoveroni.views.dictionary.listeners;

import com.matteoveroni.views.dictionary.bus.events.EventShowVocablesActionPanel;
import com.matteoveroni.views.dictionary.model.Vocable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import org.greenrobot.eventbus.EventBus;

/**
 *
 * @author Matteo Veroni
 */
public class FocusChangeListenerListViewVocables implements ChangeListener<Boolean> {

	private final ListView<Vocable> listview_vocables;

	public FocusChangeListenerListViewVocables(ListView<Vocable> listview_vocables) {
		this.listview_vocables = listview_vocables;
	}

	@Override
	public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		if (newValue == true) {
			if (listview_vocables.getSelectionModel().getSelectedItem() == null) {
				if (!listview_vocables.getItems().isEmpty()) {
					listview_vocables.getSelectionModel().selectFirst();
				}
			}
			EventBus.getDefault().post(new EventShowVocablesActionPanel(true));
		} else {
			listview_vocables.getSelectionModel().clearSelection();
			listview_vocables.getSelectionModel().select(null);
			EventBus.getDefault().post(new EventShowVocablesActionPanel(false));
		}
	}
}
