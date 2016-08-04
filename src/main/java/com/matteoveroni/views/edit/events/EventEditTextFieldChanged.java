package com.matteoveroni.views.edit.events;

/**
 *
 * @author Matteo Veroni
 */
public class EventEditTextFieldChanged {

    private final String textFieldStringValue;

    public EventEditTextFieldChanged(String textFieldStringValue) {
        this.textFieldStringValue = textFieldStringValue;
    }

    public String getTextFieldStringValue() {
        return textFieldStringValue;
    }
}
