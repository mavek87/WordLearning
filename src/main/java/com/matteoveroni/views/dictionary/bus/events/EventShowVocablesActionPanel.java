package com.matteoveroni.views.dictionary.bus.events;

/**
 *
 * @author Matteo Veroni
 */
public class EventShowVocablesActionPanel {

	private final boolean showValue;

	public EventShowVocablesActionPanel(boolean showValue) {
		this.showValue = showValue;
	}

	public boolean getShowValue() {
		return showValue;
	}
}
