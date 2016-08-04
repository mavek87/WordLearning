package com.matteoveroni.views;

import com.airhacks.afterburner.views.FXMLView;
import com.matteoveroni.App;
import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventChangeWindowDimension;
import com.matteoveroni.bus.events.EventLanguageChanged;
import com.matteoveroni.bus.events.EventRequestView;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.bus.events.EventSendView;
import com.matteoveroni.bus.events.EventGoToPreviousView;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class ViewsManager implements Disposable {

	private final Map<ViewName, FXMLView> views = new HashMap<>();
	private final Stage stage;
	private Scene currentScene;
	private final Deque<ViewName> usedViewsNamesStack = new ArrayDeque<>();
	private static final Logger LOG = LoggerFactory.getLogger(ViewsManager.class);

	public ViewsManager(Stage stage) {
		this.stage = stage;
		stage.setTitle(App.NAME + " - v. " + App.VERSION);
		buildViews();
	}

	public FXMLView getView(ViewName viewName) {
		return views.get(viewName);
	}

	@Override
	public void dispose() {
		disposeViews();
	}

	@Subscribe
	public void onViewRequested(EventRequestView event) {
		try {
			ViewName viewRequested = event.getViewNameRequested();
			System.out.println("views.get(viewRequested), viewRequested) " + views.get(viewRequested).getChildren().size());
			if (views.containsKey(viewRequested)) {
				System.out.println("fxmlview requested => " + views.get(viewRequested).toString());
				EventBus.getDefault().post(new EventSendView(views.get(viewRequested), viewRequested));
			} else {
				throw new Exception("View Requested not set");
			}
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
		}
	}

	@Subscribe
	public void onGoToPreviousView(EventGoToPreviousView event) {
		if (usedViewsNamesStack.size() > 1) {
			usedViewsNamesStack.pollLast();
			useView(usedViewsNamesStack.pollLast());
		}
	}

	@Subscribe
	public void changeWindowDimension(EventChangeWindowDimension eventChangeWindowDimension) {
		stage.setWidth(eventChangeWindowDimension.getWidth());
		stage.setHeight(eventChangeWindowDimension.getHeight());
	}

	@Subscribe
	public void reloadTranslatedViewsAfterLanguageChanged(EventLanguageChanged eventLanguageChanged) {
		disposeViews();
		buildViews();
		usedViewsNamesStack.peekLast();
		useView(usedViewsNamesStack.peekLast());
	}

	@Subscribe
	public void useView(EventChangeView eventChangeView) {
		if (eventChangeView.getObjectPassed() == null) {
			useView(eventChangeView.getViewName());
		} else {
			useView(eventChangeView.getViewName(), eventChangeView.getObjectPassed());
		}
	}

	private void useView(ViewName nameOfViewToUse) {
		useView(nameOfViewToUse, null);
	}

	private void useView(ViewName nameOfViewToUse, Object objectPassed) {
		LOG.info("Use view => " + nameOfViewToUse);
		FXMLView fxmlView = views.get(nameOfViewToUse);
		if (currentScene != null) {
			currentScene.setRoot(new Parent() {
			});
		}
//		Scene currentScene = new Scene(new Parent() {
//		});
//		currentScene.setRoot(new Parent() {
//		});
		currentScene = new Scene(fxmlView.getView(), App.WINDOW_WIDTH, App.WINDOW_HEIGHT);
		if (usedViewsNamesStack.size() < 2 || !nameOfViewToUse.equals(usedViewsNamesStack.getLast())) {
			usedViewsNamesStack.addLast(nameOfViewToUse);
		}
		System.out.println(usedViewsNamesStack.size());
		applyGeneralCSSToScene();
		showSceneOnStage(currentScene);
		if (objectPassed == null) {
			EventBus.getDefault().post(new EventViewChanged(usedViewsNamesStack.getLast()));
		} else {
			EventBus.getDefault().post(new EventViewChanged(usedViewsNamesStack.getLast(), objectPassed));
		}
	}

	private void showSceneOnStage(Scene scene) {
		stage.setScene(scene);
		stage.show();
	}

	private void applyGeneralCSSToScene() {
		final String uri = getClass().getResource("app.css").toExternalForm();
		currentScene.getStylesheets().add(uri);
	}

	private void buildViews() {
		LOG.debug("Building all the views");
		for (ViewName viewName : ViewName.values()) {
			buildView(viewName);
		}
	}

	private void buildView(ViewName viewName) {
		try {
			FXMLView fxmlView = ViewsFactory.create(viewName);
			fxmlView.getView();
			views.put(viewName, fxmlView);
			EventBus.getDefault().register(fxmlView.getPresenter());
			LOG.debug(viewName + " presenter registered to bus");
		} catch (ViewsFactoryCreationException ex) {
			LOG.error(ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}
	}

	private void unregisterViewFromBus(FXMLView view) {
		EventBus.getDefault().unregister(view.getPresenter());
	}

	private void disposeView(Disposable view) {
		view.dispose();
	}

	private void disposeViews() {
		for (FXMLView view : views.values()) {
			try {
				unregisterViewFromBus(view);
			} catch (Exception ex) {
			}
			LOG.debug(views.values().size() + " view\'s presenters unregistered from bus");
			try {
				disposeView((Disposable) view);
			} catch (Exception ex) {
			}
			LOG.debug(views.values().size() + " view\'s disposed");
		}
	}
}
