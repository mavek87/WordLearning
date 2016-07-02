package com.matteoveroni.views.options;

import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventRequestLanguageChange;
import com.matteoveroni.localization.SupportedNation;
import com.matteoveroni.views.ViewName;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.greenrobot.eventbus.EventBus;

/**
 * FXML Controller class
 *
 * @author Matteo Veroni
 */
public class OptionsPresenter implements Initializable {

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    void setEnglishLanguage(ActionEvent event) {
        EventBus.getDefault().post(new EventRequestLanguageChange(SupportedNation.USA.getLocale()));
    }

    @FXML
    void setItalianLanguage(ActionEvent event) {
        EventBus.getDefault().post(new EventRequestLanguageChange(SupportedNation.ITALY.getLocale()));
    }

    @FXML
    void goBack(ActionEvent event) {
        EventBus.getDefault().post(new EventChangeView(ViewName.MAINMENU));
    }

}
