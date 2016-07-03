package com.matteoveroni.views.mainmenu;

import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.views.ViewName;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.greenrobot.eventbus.EventBus;

/**
 * FXML Controller class
 *
 * @author Matteo Veroni
 */
public class MainMenuPresenter implements Initializable {

    @FXML
    private Button bottone;
    
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
    void goToSettings(ActionEvent event) {
        EventBus.getDefault().post(new EventChangeView(ViewName.OPTIONS));
    }

    @FXML
    void closeApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

}
