package com.matteoveroni.views.creation.model.events;

import javafx.scene.control.RadioButton;

/**
 *
 * @author Matteo Veroni
 */
public class EventRadioButtonSelectionChanged {

    private final RadioButton radioButtonSelected;

    public EventRadioButtonSelectionChanged(RadioButton radioButtonSelected) {
        this.radioButtonSelected = radioButtonSelected;
    }

    public RadioButton getRadioButtonSelected() {
        return radioButtonSelected;
    }
}
