package com.matteoveroni.views;

import com.airhacks.afterburner.views.FXMLView;
import com.matteoveroni.App;
import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventLanguageChanged;
import com.matteoveroni.views.mainmenu.MainMenuView;
import com.matteoveroni.views.options.OptionsView;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.greenrobot.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class ViewsManager {

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
    public void reloadTranslatedViewsAfterLanguageChanged(EventLanguageChanged eventLanguageChanged) {
        buildViews();
        useView(currentSettedViewName);
    }

    @Subscribe
    public void useView(EventChangeView eventChangeScreen) {
        useView(eventChangeScreen.getViewName());
    }

    private void useView(ViewName nameOfViewToUse) {
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
    }

    private void applyGeneralCSSToScene() {
        for (String fontFamily : javafx.scene.text.Font.getFamilies()) {
            System.out.println("Font family => " + fontFamily);
        }
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
            case OPTIONS:
                fxmlView = new OptionsView();
                break;
            default:
                String errorMessage = "View " + viewName + " cannot be build. It doesn\'t exist!";
                LOG.error(errorMessage);
                throw new RuntimeException(errorMessage);
        }
        fxmlView.getView();
        views.put(viewName, fxmlView);
    }
}
