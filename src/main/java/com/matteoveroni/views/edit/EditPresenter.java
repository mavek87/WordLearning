package com.matteoveroni.views.edit;

import com.matteoveroni.bus.events.EventGoToPreviousView;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.dictionary.model.pojo.Translation;
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
import javafx.scene.control.Label;
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
public class EditPresenter implements Initializable, Disposable {

    private static final Logger LOG = LoggerFactory.getLogger(EditPresenter.class);

    @FXML
    private Label lbl_title;
    @FXML
    private Label lbl_edit;
    @FXML
    private TextField txt_edit;
    @FXML
    private Button btn_goBack;

    private ResourceBundle resourceBundle;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        if (btn_goBack.getGraphic() == null) {
            btn_goBack.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.REPLY, "1em"));
        }
    }

    @Subscribe
    public void onViewChanged(EventViewChanged eventViewChanged) {
        if (eventViewChanged.getCurrentViewName() == ViewName.EDIT) {
            if (eventViewChanged.getObjectPassed() instanceof Vocable) {
                lbl_title.setText(resourceBundle.getString("editvocable"));
                lbl_edit.setText(resourceBundle.getString("vocable"));
                String vocableToUse = ((Vocable) eventViewChanged.getObjectPassed()).getName();
                if (vocableToUse != null && !vocableToUse.isEmpty()) {
                    txt_edit.setText(vocableToUse);
                } else {
                    goBack(null);
                }
            }
            if (eventViewChanged.getObjectPassed() instanceof Translation) {
                lbl_title.setText(resourceBundle.getString("edittranslation"));
                lbl_edit.setText(resourceBundle.getString("translation"));
                String translationToUse = ((Translation) eventViewChanged.getObjectPassed()).getTranslation();
                if (translationToUse != null && !translationToUse.isEmpty()) {
                    txt_edit.setText(translationToUse);
                } else {
                    goBack(null);
                }
            }
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        EventBus.getDefault().post(new EventGoToPreviousView());
    }

    @Override
    public void dispose() {
    }

}
