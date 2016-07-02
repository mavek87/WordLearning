package com.matteoveroni;

import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventRequestLanguageChange;
import com.matteoveroni.localization.LocaleManager;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.ViewsManager;
import java.util.Locale;
import javafx.application.Application;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;

/**
 *
 * @author Matteo Veroni
 */
public class WordLearningMain extends Application {

    private ViewsManager viewsManager;
    private LocaleManager localeManager;

    @Override
    public void start(Stage primaryStage) throws Exception {
        buildMainComponents(primaryStage);
        registerMainComponentsToBus();

        EventBus.getDefault().post(new EventRequestLanguageChange(Locale.getDefault()));
        EventBus.getDefault().post(new EventChangeView(ViewName.MAINMENU));
    }

    private void buildMainComponents(Stage stage) {
        viewsManager = new ViewsManager(stage);
        localeManager = new LocaleManager();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        unregisterMainComponentsFromBus();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void registerMainComponentsToBus() {
        EventBus.getDefault().register(viewsManager);
        EventBus.getDefault().register(localeManager);
    }

    private void unregisterMainComponentsFromBus() {
        EventBus.getDefault().unregister(viewsManager);
        EventBus.getDefault().unregister(localeManager);
    }

}
