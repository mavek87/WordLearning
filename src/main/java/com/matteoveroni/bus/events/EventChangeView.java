package com.matteoveroni.bus.events;

import com.matteoveroni.views.ViewName;

/**
 *
 * @author Matteo Veroni
 */
public class EventChangeView {

    private final ViewName viewName;

    public EventChangeView(ViewName viewName) {
        this.viewName = viewName;
    }

    public ViewName getViewName() {
        return viewName;
    }
}
