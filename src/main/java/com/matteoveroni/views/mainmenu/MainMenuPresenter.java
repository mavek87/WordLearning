package com.matteoveroni.views.mainmenu;

import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.dictionary.model.pojo.Translation;
import com.matteoveroni.views.translations.events.EventNewTranslationsToShow;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * FXML Controller class
 *
 * @author Matteo Veroni
 */
public class MainMenuPresenter implements Initializable {

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Subscribe
    public void onViewChanged(EventViewChanged eventViewChanged) {
        if (eventViewChanged.getCurrentViewName() == ViewName.MAINMENU) {
        }
    }

    @FXML
    void startTraining(ActionEvent event) {
        EventBus.getDefault().post(new EventChangeView(ViewName.QUESTIONS));
    }

    @FXML
    void goToDictionary(ActionEvent event) {
        EventBus.getDefault().post(new EventChangeView(ViewName.DICTIONARY));
    }

    @FXML
    void goToSettings(ActionEvent event) {
        EventBus.getDefault().post(new EventChangeView(ViewName.OPTIONS));
    }

    @FXML
    void goToTranslations(ActionEvent event) {
//        Translation t1 = new Translation(1, "ciao");
//        Translation t2 = new Translation(2, "arrivedorci !");
//        final List<Translation> lt = new ArrayList<>();
//        lt.add(t1);
//        lt.add(t2);
//        EventBus.getDefault().post(new EventNewTranslationsToShow(lt));
//        EventBus.getDefault().post(new EventChangeView(ViewName.TRANSLATIONS));
    }

    @FXML
    void closeApp(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }

}
