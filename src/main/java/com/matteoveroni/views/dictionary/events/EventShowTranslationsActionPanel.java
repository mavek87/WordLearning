package com.matteoveroni.views.dictionary.events;

/**
 *
 * @author Matteo Veroni
 */
public class EventShowTranslationsActionPanel {

	private final boolean showValue;

	public EventShowTranslationsActionPanel(boolean showValue) {
		this.showValue = showValue;
	}

	public boolean getShowValue() {
		return showValue;
	}
}
