package com.matteoveroni.views.creation;

import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.creation.model.events.EventRadioButtonSelectionChanged;
import com.matteoveroni.views.creation.model.listeners.RadioToggleGroupChangeListener;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
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

    private final ToggleGroup toggleGroup = new ToggleGroup();
    private final RadioToggleGroupChangeListener radioToggleGroupChangeListener = new RadioToggleGroupChangeListener();

    @FXML
    private RadioButton radio_translation;
    @FXML
    private RadioButton radio_vocable;
    @FXML
    private Label lbl_create;
    @FXML
    private Button btn_goBack;
    @FXML
    private HBox hbox_vocable;

    private final Label lbl_newVocable = new Label();
    private final TextField txt_newVocable = new TextField();
    private final Button btn_saveNewVocable = new Button();

    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;

        btn_goBack.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.REPLY));

        radio_vocable.setToggleGroup(toggleGroup);
        radio_translation.setToggleGroup(toggleGroup);
        toggleGroup.selectedToggleProperty().addListener(radioToggleGroupChangeListener);

        createVocableComponents();

        resetView();
    }

    @Subscribe
    public void onViewChanged(EventViewChanged eventViewChanged) {
        if (eventViewChanged.getCurrentViewName() == ViewName.CREATION) {
            resetView();
        }
    }

    @Subscribe
    public void RadioButtonSelectionChanged(EventRadioButtonSelectionChanged event) {
        RadioButton radioButtonSelected = event.getRadioButtonSelected();
        LOG.debug("Selected Radio Button => " + radioButtonSelected.getText());
        if (radioButtonSelected == radio_vocable) {
            changeViewForVocableSelection();
        } else if (radioButtonSelected == radio_translation) {
            changeViewForTranslationSelection();
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        EventBus.getDefault().post(new EventChangeView(ViewName.DICTIONARY));
    }

    private void changeViewForVocableSelection() {
        changeViewForVocableSelection(true);
    }

    private void changeViewForTranslationSelection() {
        changeViewForTranslationSelection(true);
    }

    private void changeViewForVocableSelection(boolean isVocableSelected) {
        if (isVocableSelected) {
            if (hbox_vocable.getChildren().isEmpty()) {
                hbox_vocable.getChildren().add(lbl_newVocable);
                hbox_vocable.getChildren().add(txt_newVocable);
                hbox_vocable.getChildren().add(btn_saveNewVocable);
            }
            changeViewForTranslationSelection(false);
        } else {
            hbox_vocable.getChildren().clear();
        }
    }

    private void changeViewForTranslationSelection(boolean isTranslationSelected) {
        if (isTranslationSelected) {
//            if (hbox_vocable.getChildren().isEmpty()) {
//                hbox_vocable.getChildren().add(lbl_newVocable);
//                hbox_vocable.getChildren().add(txt_newVocable);
//                hbox_vocable.getChildren().add(btn_saveNewVocable);
//            }
            changeViewForVocableSelection(false);
        } else {
//            hbox_vocable.getChildren().clear();
        }
    }

    private void resetView() {
        radio_vocable.setSelected(true);
        txt_newVocable.clear();
        changeViewForVocableSelection(true);
    }

    private void createVocableComponents() {
        btn_saveNewVocable.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.SAVE));
        btn_saveNewVocable.setOnAction((ActionEvent event) -> {
            saveVocable();
        });
    }

    private void saveVocable() {
        LOG.debug("salvo " + txt_newVocable.getText());
        String newVocable = txt_newVocable.getText();
        Alert alertSaveVocable = new Alert(AlertType.NONE);
        alertSaveVocable.setTitle("Save vocable");
        if (!newVocable.trim().isEmpty()) {
            alertSaveVocable.setAlertType(AlertType.INFORMATION);
            alertSaveVocable.setContentText("Salvataggio " + newVocable + " riuscito!");
            resetView();
        } else {
            alertSaveVocable.setAlertType(AlertType.ERROR);
            alertSaveVocable.setContentText("Salvataggio vocabolo fallito!");
        }
        alertSaveVocable.showAndWait();
    }

    @Override
    public void dispose() {
        try {
            toggleGroup.selectedToggleProperty().removeListener(radioToggleGroupChangeListener);
        } catch (Exception ex) {
        }
    }

}
