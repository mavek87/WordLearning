package com.matteoveroni.bus.events;

import com.matteoveroni.views.ViewName;

/**
 *
 * @author Matteo Veroni
 */
public class EventViewChanged {

    private final ViewName currentViewName;
    private Object objectPassed;

    public EventViewChanged(ViewName currentViewName) {
        this.currentViewName = currentViewName;
    }

    public EventViewChanged(ViewName currentViewName, Object objectPassed) {
        this.currentViewName = currentViewName;
        this.objectPassed = objectPassed;
    }

    public ViewName getCurrentViewName() {
        return currentViewName;
    }

    public Object getObjectPassed() {
        return objectPassed;
    }
}
