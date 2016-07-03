package com.matteoveroni;

import com.airhacks.afterburner.injection.Injector;
import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.localization.LocaleManager;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.ViewsManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class WordLearningMain extends Application {

    private ViewsManager viewsManager;
    private LocaleManager localeManager;
    private static final Logger LOG = LoggerFactory.getLogger(WordLearningMain.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        LOG.trace("Application Word Learning Started");

        buildMainComponents(primaryStage);
        subscribeMainComponentsToBus();

        LOG.debug("Send request to set first view => " + ViewName.MAINMENU + " on event bus");
        EventBus.getDefault().post(new EventChangeView(ViewName.MAINMENU));
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        unsuscribeMainComponentsFromBus();
        Injector.forgetAll();
        LOG.trace("Application Word Learning stopped");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void buildMainComponents(Stage stage) {
        LOG.debug("Building main components");
        viewsManager = new ViewsManager(stage);
        localeManager = new LocaleManager();
        LOG.debug("Main components builded");
    }

    private void subscribeMainComponentsToBus() {
        LOG.debug("Subscribing main components to event bus");
        EventBus.getDefault().register(viewsManager);
        EventBus.getDefault().register(localeManager);
    }

    private void unsuscribeMainComponentsFromBus() {
        LOG.debug("Unsubscribing main components to event bus");
        EventBus.getDefault().unregister(viewsManager);
        EventBus.getDefault().unregister(localeManager);
    }

}
