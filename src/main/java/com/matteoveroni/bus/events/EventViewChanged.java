package com.matteoveroni.bus.events;

import com.matteoveroni.views.ViewName;

/**
 *
 * @author Matteo Veroni
 */
public class EventViewChanged {

	private final ViewName currentViewName;

	public EventViewChanged(ViewName currentViewName) {
		this.currentViewName = currentViewName;
	}

	public ViewName getCurrentViewName() {
		return currentViewName;
	}
}
