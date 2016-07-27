package com.matteoveroni.views.creation;

import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.views.ViewName;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Matteo Veroni
 */
public class CreationPresenter implements Initializable, Disposable {

    private static final Logger LOG = LoggerFactory.getLogger(CreationPresenter.class);

    @FXML
    private Label lbl_create;
    @FXML
    private Button btn_goBack;
    @FXML
    private RadioButton radio_vocable;
    @FXML
    private RadioButton radio_translation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btn_goBack.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.REPLY));
                
        final ToggleGroup toggleGroup = new ToggleGroup();
        radio_vocable.setToggleGroup(toggleGroup);
        radio_vocable.setSelected(true);
        radio_translation.setToggleGroup(toggleGroup);

    }

    @Subscribe
    public void onViewChanged(EventViewChanged eventViewChanged) {
        if (eventViewChanged.getCurrentViewName() == ViewName.CREATION) {
            resetView();
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        EventBus.getDefault().post(new EventChangeView(ViewName.DICTIONARY));
    }

    private void resetView() {
    }

    @Override
    public void dispose() {
    }

}
