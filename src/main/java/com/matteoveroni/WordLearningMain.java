package com.matteoveroni;

import com.airhacks.afterburner.injection.Injector;
import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.localization.LocaleManager;
import com.matteoveroni.persistence.PersistencyManager;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.ViewsManager;
import com.matteoveroni.views.dictionary.model.Dictionary;
import com.matteoveroni.views.dictionary.model.Translation;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
		File jsonFile;
		try {
			wordLearningFolder = new File(App.PATH);
			LOG.debug("word learning folder => " + wordLearningFolder.getAbsolutePath());
			if (!wordLearningFolder.isDirectory()) {
				if (!wordLearningFolder.mkdir()) {
					throw new Exception();
				}
			}
			databaseFolder = new File(App.PATH + File.separator + "Database");
			LOG.debug("database folder => " + databaseFolder.getAbsolutePath());
			if (!databaseFolder.isDirectory()) {
				if (!databaseFolder.mkdir()) {
					throw new Exception();
				}
			}
			jsonFile = new File(databaseFolder.getAbsolutePath() + File.separator + "jsonDictionary.json");
			if (!jsonFile.isFile()) {
				if (!jsonFile.createNewFile()) {
					throw new Exception();
				} else {
					PersistencyManager.getInstance().writeObjectToJsonFile(createHardCodedDictionaryForTest(), jsonFile);
				}
			} else {
				Dictionary dictionary = new Dictionary();
				dictionary = (Dictionary) PersistencyManager.getInstance().readObjectFromFile(dictionary, jsonFile);
				System.out.println("dictionary " + dictionary.convertToJson());
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private Dictionary createHardCodedDictionaryForTest() {
		String wordGuardare = "guardare";
		List<Translation> translationsOfGuardare = new ArrayList<>();
		translationsOfGuardare.add(new Translation("look"));
		translationsOfGuardare.add(new Translation("watch"));

		String wordSentire = "sentire";
		List<Translation> translationsOfSentire = new ArrayList<>();
		translationsOfSentire.add(new Translation("look"));
		translationsOfSentire.add(new Translation("watch"));

		Dictionary dictionary = new Dictionary();
		dictionary.createWordAndTranslations(wordSentire, translationsOfSentire);
		dictionary.createWordAndTranslations(wordGuardare, translationsOfGuardare);
		return dictionary;
	}
}
