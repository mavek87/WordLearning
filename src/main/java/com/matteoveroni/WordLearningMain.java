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

//		DaoPrototype d = new DaoPrototype();
//		EventBus.getDefault().register(d);
		LOG.debug("Creating program folder");
		createProgramFolder();
		
		Database.connect();

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
		EventBus.getDefault().unregister(viewsManager);
		EventBus.getDefault().unregister(localeManager);
	}

	private void createProgramFolder() {
		File wordLearningFolder;
		File databaseFolder;
		File databaseFile;
		try {
			wordLearningFolder = new File(App.PATH);
			if (!wordLearningFolder.isDirectory()) {
				if (!wordLearningFolder.mkdir()) {
					throw new Exception();
				}
			}
			databaseFolder = new File(App.PATH + File.separator + "Database");
			if (!databaseFolder.isDirectory()) {
				if (!databaseFolder.mkdir()) {
					throw new Exception();
				}
			}
			databaseFile = new File(databaseFolder.getAbsolutePath() + File.separator + "database.sqlite");
			if (!databaseFile.isFile()) {
				if (!databaseFile.createNewFile()) {
					throw new Exception();
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
