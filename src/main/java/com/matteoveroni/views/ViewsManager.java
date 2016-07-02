package com.matteoveroni.views;

import com.airhacks.afterburner.views.FXMLView;
import com.matteoveroni.App;
import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventLanguageChanged;
import com.matteoveroni.views.mainmenu.MainMenuView;
import com.matteoveroni.views.options.OptionsView;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.greenrobot.eventbus.Subscribe;

/**
 *
 * @author Matteo Veroni
 */
public class ViewsManager {

    private final Map<ViewName, FXMLView> views = new HashMap<>();
    private ViewName currentSettedViewName;
    private final Stage stage;
    private Scene currentScene;

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
        System.out.println(" reloadScreensAfterLanguageChanged => " + this.getClass());
        System.out.println("language setted => " + eventLanguageChanged.getLocale());
        System.out.println(Locale.getDefault());

        buildViews();
        ViewsManager.this.useView(currentSettedViewName);
    }

    @Subscribe
    public void useView(EventChangeView eventChangeScreen) {
        ViewsManager.this.useView(eventChangeScreen.getViewName());
    }

    private void useView(ViewName nameOfViewToUse) {
        FXMLView fxmlView = views.get(nameOfViewToUse);
        if (currentScene != null) {
            currentScene.setRoot(new Parent() {
            });
        }
        currentScene = new Scene(fxmlView.getView(), App.WINDOW_WIDTH, App.WINDOW_HEIGHT);
        stage.setScene(currentScene);
        currentSettedViewName = nameOfViewToUse;
        stage.show();
    }

    private void buildViews() {
        for (ViewName viewName : ViewName.values()) {
            buildView(viewName);
        }
    }

    private void buildView(ViewName viewName) {
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
                throw new RuntimeException("View " + viewName + " cannot be build. It doesn\'t exist!");
        }
        fxmlView.getView();
        views.put(viewName, fxmlView);
    }
}
