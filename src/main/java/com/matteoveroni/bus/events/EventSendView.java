package com.matteoveroni.bus.events;

import com.airhacks.afterburner.views.FXMLView;
import com.matteoveroni.views.ViewName;

/**
 *
 * @author Matteo Veroni
 */
public class EventSendView {

    private final FXMLView view;
    private final ViewName viewName;

    public EventSendView(FXMLView view, ViewName viewName) {
        this.view = view;
        this.viewName = viewName;
        if(view == null){
            System.out.println("View null");
        }
        if(viewName == null){
            System.out.println("View Name null");
        }
    }

    public FXMLView getFXMLView() {
        return view;
    }

    public ViewName getViewName() {
        return viewName;
    }

}
