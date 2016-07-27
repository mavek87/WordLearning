package com.matteoveroni.views.options;

import com.matteoveroni.App;
import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventChangeWindowDimension;
import com.matteoveroni.bus.events.EventChangeLanguage;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.localization.SupportedCountries;
import com.matteoveroni.views.ViewName;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * FXML Controller class
 *
 * @author Matteo Veroni
 */
public class OptionsPresenter implements Initializable, Disposable {

    @FXML
    private Button btn_english;
    @FXML
    private Button btn_italian;
    @FXML
    private Button btn_goBack;
    @FXML
    private ComboBox<String> cmb_windowSize;

    private ChangeListener<String> changeListenerCmbWindowSize;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        btn_goBack.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.REPLY, "1em"));

        populateComboboxWindowSize();
        cmb_windowSize.getSelectionModel().selectLast();
        addListenerOnComboboxWindowSizeChange();

        Image image_en_GB = new Image("/com/matteoveroni/icons/countries/en_GB.png", 30, 20, false, false);
        btn_english.setGraphic(new ImageView(image_en_GB));

        Image image_it_IT = new Image("/com/matteoveroni/icons/countries/it_IT.png", 30, 20, false, false);
        btn_italian.setGraphic(new ImageView(image_it_IT));
    }

    @Subscribe
    public void onViewChanged(EventViewChanged eventViewChanged) {
        if (eventViewChanged.getCurrentViewName() == ViewName.OPTIONS) {
        }
    }

    @FXML
    void setEnglishLanguage(ActionEvent event) {
        Platform.runLater(() -> {
            EventBus.getDefault().post(new EventChangeLanguage(SupportedCountries.USA.getLocale()));
        });
    }

    @FXML
    void setItalianLanguage(ActionEvent event) {
        Platform.runLater(() -> {
            EventBus.getDefault().post(new EventChangeLanguage(SupportedCountries.ITALY.getLocale()));
        });
    }

    @FXML
    void goBack(ActionEvent event) {
        EventBus.getDefault().post(new EventChangeView(ViewName.MAINMENU));
    }

    private void populateComboboxWindowSize() {
        int n = App.WINDOW_DIMENSIONS.length;
        for (int i = 0; i < n; i++) {
            String windowWidth = "" + (int) App.WINDOW_DIMENSIONS[i][0];
            String windowHeight = "" + (int) App.WINDOW_DIMENSIONS[i][1];
            cmb_windowSize.getItems().add(windowWidth + " x " + windowHeight);
        }
    }

    private void addListenerOnComboboxWindowSizeChange() {
        if (changeListenerCmbWindowSize != null) {
            cmb_windowSize.valueProperty().removeListener(changeListenerCmbWindowSize);
        }
        changeListenerCmbWindowSize = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int selectedIndex = cmb_windowSize.getSelectionModel().getSelectedIndex();
                App.WINDOW_WIDTH = App.WINDOW_DIMENSIONS[selectedIndex][0];
                App.WINDOW_HEIGHT = App.WINDOW_DIMENSIONS[selectedIndex][1];
                EventBus.getDefault().post(new EventChangeWindowDimension(App.WINDOW_WIDTH, App.WINDOW_HEIGHT));
            }
        };
        cmb_windowSize.valueProperty().addListener(changeListenerCmbWindowSize);
    }

    @Override
    public void dispose() {
        try {
            cmb_windowSize.valueProperty().removeListener(changeListenerCmbWindowSize);
        } catch (Exception ex) {
        }
    }
}
