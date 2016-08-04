package com.matteoveroni.views.editvocable;

import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventGoToPreviousView;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.dictionary.model.pojo.Vocable;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    @FXML
    private TextField txt_vocable;
    @FXML
    private Button btn_goBack;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (btn_goBack.getGraphic() == null) {
            btn_goBack.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.REPLY, "1em"));
        }
    }

    @Subscribe
    public void onViewChanged(EventViewChanged eventViewChanged) {
        if (eventViewChanged.getCurrentViewName() == ViewName.EDIT_VOCABLE && eventViewChanged.getObjectPassed() instanceof Vocable) {
            resetView();
            String vocableToUse = ((Vocable) eventViewChanged.getObjectPassed()).getName();
            if (vocableToUse != null && !vocableToUse.isEmpty()) {
                txt_vocable.setText(vocableToUse);
            } else {
                goBack(null);
            }
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        EventBus.getDefault().post(new EventGoToPreviousView());
    }

    private void resetView() {
    }

    @Override
    public void dispose() {

    }

}
