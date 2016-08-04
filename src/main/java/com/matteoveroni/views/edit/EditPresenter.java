package com.matteoveroni.views.edit;

import com.matteoveroni.bus.events.EventGoToPreviousView;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.dictionary.model.pojo.Translation;
import com.matteoveroni.views.dictionary.model.pojo.Vocable;
import com.matteoveroni.views.edit.events.EventEditTextFieldChanged;
import com.matteoveroni.views.edit.listeners.EditTextFieldListener;
import com.matteoveroni.views.edit.model.EditDAO;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

    EditDAO editDAO = new EditDAO();

    private Object objectToEdit;

    @FXML
    private Label lbl_title;
    @FXML
    private Label lbl_edit;
    @FXML
    private TextField txt_edit;
    @FXML
    private Button btn_goBack;
    @FXML
    private Button btn_save;

    private EditTextFieldListener editTextFieldListener;

    private ResourceBundle resourceBundle;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        btn_goBack.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.REPLY, "1em"));
        btn_save.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.SAVE, "1em"));
        btn_save.setVisible(false);
        removeEditTextFieldListenerIfPresent();
        objectToEdit = null;
    }

    @Subscribe
    public void onViewChanged(EventViewChanged eventViewChanged) {
        if (eventViewChanged.getCurrentViewName() == ViewName.EDIT) {
            objectToEdit = eventViewChanged.getObjectPassed();
            btn_save.setVisible(false);
            removeEditTextFieldListenerIfPresent();

            if (objectToEdit instanceof Vocable) {
                lbl_title.setText(resourceBundle.getString("editvocable"));
                lbl_edit.setText(resourceBundle.getString("vocable"));
                String vocableToUse = ((Vocable) eventViewChanged.getObjectPassed()).getName();
                if (vocableToUse != null && !vocableToUse.isEmpty()) {
                    txt_edit.setText(vocableToUse);
                } else {
                    goBack(null);
                }
            }
            if (objectToEdit instanceof Translation) {
                lbl_title.setText(resourceBundle.getString("edittranslation"));
                lbl_edit.setText(resourceBundle.getString("translation"));
                String translationToUse = ((Translation) eventViewChanged.getObjectPassed()).getTranslation();
                if (translationToUse != null && !translationToUse.isEmpty()) {
                    txt_edit.setText(translationToUse);
                } else {
                    goBack(null);
                }
            }

            editTextFieldListener = new EditTextFieldListener();
            txt_edit.textProperty().addListener(editTextFieldListener);
        }
    }

    @Subscribe
    public void onEditTextFieldChanged(EventEditTextFieldChanged event) {
        String newText = event.getTextFieldStringValue();
        if (newText != null && !newText.trim().isEmpty()) {
            btn_save.setVisible(true);
        } else {
            btn_save.setVisible(false);
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        EventBus.getDefault().post(new EventGoToPreviousView());
        objectToEdit = null;
        removeEditTextFieldListenerIfPresent();
    }

    @FXML
    void saveEdit(ActionEvent event) {
        if (objectToEdit != null && !txt_edit.getText().trim().isEmpty() && (objectToEdit instanceof Vocable || objectToEdit instanceof Translation)) {
            Alert saveAlert = new Alert(AlertType.NONE);
            String saveAlertMsg;
            try {
                editDAO.saveEditedObject(objectToEdit, txt_edit.getText());
                saveAlert.setAlertType(AlertType.INFORMATION);
                saveAlert.setTitle("Success");
                saveAlertMsg = "Edit successfull";
            } catch (Exception ex) {
                saveAlert.setAlertType(AlertType.ERROR);
                saveAlert.setTitle("Error");
                saveAlertMsg = ex.getMessage();
                LOG.error(ex.getMessage());
            }
            saveAlert.setContentText(saveAlertMsg);
            saveAlert.showAndWait();
            goBack(null);
        }
    }

    private void removeEditTextFieldListenerIfPresent() {
        if (editTextFieldListener != null) {
            txt_edit.textProperty().removeListener(editTextFieldListener);
        }
    }

    @Override
    public void dispose() {
        removeEditTextFieldListenerIfPresent();
    }

}
