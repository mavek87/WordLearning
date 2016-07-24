package com.matteoveroni.views;

import com.airhacks.afterburner.views.FXMLView;
import com.matteoveroni.App;
import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventChangeWindowDimension;
import com.matteoveroni.bus.events.EventLanguageChanged;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.views.dictionary.DictionaryView;
import com.matteoveroni.views.editvocable.EditvocableView;
import com.matteoveroni.views.mainmenu.MainMenuView;
import com.matteoveroni.views.options.OptionsView;
import com.matteoveroni.views.questions.QuestionsView;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
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
    private ViewName currentSettedViewName;
    private final Stage stage;
    private Scene currentScene;
    private static final Logger LOG = LoggerFactory.getLogger(ViewsManager.class);

    public ViewsManager(Stage stage) {
        this.stage = stage;
        stage.setTitle(App.NAME + " - v. " + App.VERSION);
        buildViews();
    }

    public FXMLView getView(ViewName viewName) {
        return views.get(viewName);
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
        useView(currentSettedViewName);
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
        currentScene = new Scene(fxmlView.getView(), App.WINDOW_WIDTH, App.WINDOW_HEIGHT);
        applyGeneralCSSToScene();
        stage.setScene(currentScene);
        currentSettedViewName = nameOfViewToUse;
        stage.show();
        if (objectPassed == null) {
            EventBus.getDefault().post(new EventViewChanged(currentSettedViewName));
        } else {
            EventBus.getDefault().post(new EventViewChanged(currentSettedViewName, objectPassed));
        }
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
        LOG.debug("Building view => " + viewName);
        views.remove(viewName);
        FXMLView fxmlView;
        switch (viewName) {
            case MAINMENU:
                fxmlView = new MainMenuView();
                break;
            case DICTIONARY:
                fxmlView = new DictionaryView();
                break;
            case EDIT_VOCABLE:
                fxmlView = new EditvocableView();
            case OPTIONS:
                fxmlView = new OptionsView();
                break;
            case QUESTIONS:
                fxmlView = new QuestionsView();
                break;
            default:
                String errorMessage = "View " + viewName + " cannot be build. It doesn\'t exist!";
                LOG.error(errorMessage);
                throw new RuntimeException(errorMessage);
        }
        fxmlView.getView();
        views.put(viewName, fxmlView);
        EventBus.getDefault().register(fxmlView.getPresenter());
        LOG.debug(viewName + " presenter registered to bus");
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

    @Override
    public void dispose() {
        disposeViews();
    }
}
