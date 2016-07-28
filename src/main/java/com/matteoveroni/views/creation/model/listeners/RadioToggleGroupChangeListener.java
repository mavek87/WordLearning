package com.matteoveroni.views.creation.model.listeners;

import com.matteoveroni.views.creation.model.events.EventRadioButtonSelectionChanged;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import org.greenrobot.eventbus.EventBus;

/**
 *
 * @author Matteo Veroni
 */
public class RadioToggleGroupChangeListener implements ChangeListener<Toggle> {

    @Override
    public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
        EventBus.getDefault().post(new EventRadioButtonSelectionChanged((RadioButton) newValue.getToggleGroup().getSelectedToggle()));
    }

}
