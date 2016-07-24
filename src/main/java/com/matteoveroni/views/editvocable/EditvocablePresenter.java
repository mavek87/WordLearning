package com.matteoveroni.views.editvocable;

import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.dictionary.model.Vocable;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Matteo Veroni
 */
public class EditvocablePresenter implements Initializable, Disposable {

    private static final Logger LOG = LoggerFactory.getLogger(EditvocablePresenter.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Subscribe
    public void onViewChanged(EventViewChanged eventViewChanged) {
        if (eventViewChanged.getCurrentViewName() == ViewName.EDIT_VOCABLE && eventViewChanged.getObjectPassed() instanceof Vocable) {
            resetView();
            initializeView();
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        EventBus.getDefault().post(new EventChangeView(ViewName.DICTIONARY));
    }

    private void initializeView() {

    }

    private void resetView() {
    }

    @Override
    public void dispose() {

    }

}
