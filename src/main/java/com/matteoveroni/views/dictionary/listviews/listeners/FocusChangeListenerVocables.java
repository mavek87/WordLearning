package com.matteoveroni.views.dictionary.listviews.listeners;

import com.matteoveroni.views.dictionary.events.EventShowVocablesActionPanel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.greenrobot.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class FocusChangeListenerVocables implements ChangeListener<Boolean> {

    private static final Logger LOG = LoggerFactory.getLogger(FocusChangeListenerVocables.class);

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        LOG.debug("FOCUS = " + newValue);
        if (newValue == true) {
            EventBus.getDefault().post(new EventShowVocablesActionPanel(true));
        } else {
            EventBus.getDefault().post(new EventShowVocablesActionPanel(false));
        }
    }
}
