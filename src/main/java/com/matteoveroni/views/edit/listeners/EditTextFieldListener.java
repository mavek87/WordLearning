package com.matteoveroni.views.edit.listeners;

import com.matteoveroni.views.edit.events.EventEditTextFieldChanged;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import org.greenrobot.eventbus.EventBus;

/**
 *
 * @author Matteo Veroni
 */
public class EditTextFieldListener implements ChangeListener<String>{

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        EventBus.getDefault().post(new EventEditTextFieldChanged(newValue));
    }
    
}
