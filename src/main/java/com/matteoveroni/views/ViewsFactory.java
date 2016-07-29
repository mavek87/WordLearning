package com.matteoveroni.views;

import com.airhacks.afterburner.views.FXMLView;
import com.matteoveroni.views.creation.CreationView;
import com.matteoveroni.views.dictionary.DictionaryView;
import com.matteoveroni.views.editvocable.EditvocableView;
import com.matteoveroni.views.mainmenu.MainMenuView;
import com.matteoveroni.views.options.OptionsView;
import com.matteoveroni.views.questions.QuestionsView;
import com.matteoveroni.views.translations.TranslationsView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Matteo Veroni
 */
public class ViewsFactory {
    private static final Logger LOG = LoggerFactory.getLogger(ViewsManager.class);

    public static final FXMLView create(ViewName viewName) throws ViewsFactoryCreationException {
        LOG.debug("Building view => " + viewName);
        switch (viewName) {
            case MAINMENU:
                return new MainMenuView();
            case DICTIONARY:
                return new DictionaryView();
            case EDIT_VOCABLE:
                return new EditvocableView();
            case TRANSLATIONS:
                return new TranslationsView();
            case CREATION:
                return new CreationView();
            case OPTIONS:
                return new OptionsView();
            case QUESTIONS:
                return new QuestionsView();
            default:
                String errorMessage = "View " + viewName + " cannot be build. It doesn\'t exist!";
                LOG.error(errorMessage);
                throw new ViewsFactoryCreationException(errorMessage);
        }
    }
}
