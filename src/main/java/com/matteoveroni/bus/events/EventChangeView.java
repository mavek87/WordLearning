package com.matteoveroni.bus.events;

import com.matteoveroni.views.ViewName;

/**
 *
 * @author Matteo Veroni
 */
public class EventChangeView {

    private final ViewName viewName;
    private Object objectPassed;

    public EventChangeView(ViewName viewName) {
        this.viewName = viewName;
    }

    public EventChangeView(ViewName viewName, Object objectPassed) {
        this.viewName = viewName;
        this.objectPassed = objectPassed;
    }

    public ViewName getViewName() {
        return viewName;
    }

    public Object getObjectPassed() {
        return objectPassed;
    }
}
