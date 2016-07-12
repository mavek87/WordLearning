package com.matteoveroni;

import com.airhacks.afterburner.injection.Injector;
import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.database.Database;
import com.matteoveroni.localization.LocaleManager;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.ViewsManager;
import java.io.File;
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
        LOG.debug("Application Word Learning Started");

        buildMainComponents(primaryStage);
        subscribeMainComponentsToBus();

        LOG.debug("Creating program folder");
        createProgramFolder();

        LOG.debug("Send request to set first view => " + ViewName.MAINMENU + " on event bus");
        EventBus.getDefault().post(new EventChangeView(ViewName.MAINMENU));
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        unsuscribeMainComponentsFromBus();
        Injector.forgetAll();
        LOG.debug("Application Word Learning stopped");
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
        viewsManager.dispose();
        EventBus.getDefault().unregister(viewsManager);
        EventBus.getDefault().unregister(localeManager);
    }

    private void createProgramFolder() {
        try {
            createWordLearningFolder();
            createDatabaseFolder();
            createDatabaseFile();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void createWordLearningFolder() throws Exception {
        File wordLearningFolder = new File(App.PATH);
        LOG.debug("word learning folder => " + wordLearningFolder.getAbsolutePath());
        if (!wordLearningFolder.isDirectory()) {
            if (!wordLearningFolder.mkdir()) {
                throw new Exception();
            }
        }
    }

    private void createDatabaseFolder() throws Exception {
        File databaseFolder = new File(App.PATH_DATABASE);
        LOG.debug("database folder => " + databaseFolder.getAbsolutePath());
        if (!databaseFolder.isDirectory()) {
            if (!databaseFolder.mkdir()) {
                throw new Exception();
            }
        }
    }

    private void createDatabaseFile() throws Exception {
        File databaseFile = new File(App.PATH_DATABASE + File.separator + "database.sqlite");
        if (!databaseFile.isFile()) {
            if (!databaseFile.createNewFile()) {
                throw new Exception();
            } else {
                Database.getInstance().createDb();
            }
        }
    }
}
