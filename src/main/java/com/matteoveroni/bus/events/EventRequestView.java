package com.matteoveroni.bus.events;

import com.matteoveroni.views.ViewName;

/**
 *
 * @author Matteo Veroni
 */
public class EventRequestView {

    private final ViewName viewName;

    public EventRequestView(ViewName viewName) {
        this.viewName = viewName;
    }

    public ViewName getViewNameRequested() {
        return viewName;
    }

}
