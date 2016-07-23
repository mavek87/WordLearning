package com.matteoveroni.views.dictionary;

import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.views.dictionary.model.DictionaryDAO;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.dictionary.bus.events.EventShowTranslationsActionPanel;
import com.matteoveroni.views.dictionary.bus.events.EventShowVocablesActionPanel;
import com.matteoveroni.views.dictionary.listviews.cells.TranslationCell;
import com.matteoveroni.views.dictionary.listviews.listeners.FocusChangeListenerTranslations;
import com.matteoveroni.views.dictionary.listviews.listeners.SelectionChangeListenerVocables;
import com.matteoveroni.views.dictionary.listviews.listeners.FocusChangeListenerVocables;
import com.matteoveroni.views.dictionary.listviews.listeners.SelectionChangeListenerTranslations;
import com.matteoveroni.views.dictionary.model.DictionaryPage;
import com.matteoveroni.views.dictionary.model.Translation;
import com.matteoveroni.views.dictionary.model.Vocable;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Matteo Veroni
 */
public class DictionaryPresenter implements Initializable, Disposable {

    @Inject
    private DictionaryDAO model;
    @FXML
    private ListView<Vocable> listview_vocables = new ListView<>();
    @FXML
    private ListView<Translation> listview_translations = new ListView<>();
    @FXML
    private BorderPane actionPaneVocabulary;
    @FXML
    private BorderPane actionPaneTranslations;
    @FXML
    private HBox hbbox_bottomActions;

    private SelectionChangeListenerVocables selectionChangeListenerVocables;
    private FocusChangeListenerVocables focusChangeListenerVocables;
    private SelectionChangeListenerTranslations selectionChangeListenerTranslations;
    private FocusChangeListenerTranslations focusChangeListenerTranslations;

    private DictionaryPage dictionaryPage;

    private int pageOffset = 0;
    private int pageDimension = 10;

    private static final Logger LOG = LoggerFactory.getLogger(DictionaryPresenter.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Subscribe
    public void onViewChanged(EventViewChanged eventViewChanged) {
        if (eventViewChanged.getCurrentViewName() == ViewName.DICTIONARY) {
            resetView();
            initializeView();
        }
    }

    @Subscribe
    public void onEventShowVocableActionPanelChange(EventShowVocablesActionPanel eventShowVocablesActionPanel) {
        showActionPanel(eventShowVocablesActionPanel.getShowValue(), listview_vocables);
    }

    @Subscribe
    public void onEventShowTranslationsActionPanelChange(EventShowTranslationsActionPanel eventShowTranslationsActionPanel) {
        showActionPanel(eventShowTranslationsActionPanel.getShowValue(), listview_translations);
    }

    @FXML
    void goBack(ActionEvent event) {
        EventBus.getDefault().post(new EventChangeView(ViewName.MAINMENU));
    }

    @Override
    public void dispose() {
        disposeSelectionChangeListenerVocables();
        disposeFocusChangeListenerVocables();
        disposeSelectionChangeListenerTranslations();
        disposeFocusChangeListenerTranslations();
    }

    private void initializeView() {
        dictionaryPage = model.getDictionaryPage(pageOffset, pageDimension);

        List<Vocable> lista = new ArrayList<>();
        lista.addAll(dictionaryPage.getDictionary().keySet());

        ObservableList<Vocable> observableVocablesList = FXCollections.observableList(lista);
        listview_vocables.setItems(observableVocablesList);

        setCellFactoryForVocablesList();
        setCellFactoryForTranslationsListView();

        defineListViewVocablesBehaviours();
        defineListViewTranslationsBehaviours();

        listview_vocables.setEditable(true);
        listview_translations.setEditable(true);
    }

    private void setCellFactoryForVocablesList() {

        StringConverter<Vocable> vocableStringConverter = new StringConverter<Vocable>() {
            @Override
            public String toString(Vocable vocable) {
                return vocable.getName();
            }

            // TODO non va bene qui
            @Override
            public Vocable fromString(String string) {
                return new Vocable(0, string);
            }
        };

        listview_vocables.setCellFactory(lv -> new TextFieldListCell<Vocable>(vocableStringConverter) {
            @Override
            public void updateItem(Vocable item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty && !isEditing()) {
                    setStaticGraphic();
                }
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setStaticGraphic();
            }

            @Override
            public void commitEdit(Vocable newValue) {
                super.commitEdit(newValue);
                if(newValue==null || newValue.getName().trim().isEmpty()){
                    Alert a = new Alert(AlertType.CONFIRMATION);
                    a.showAndWait();
                }
                setStaticGraphic();
            }

            private void setStaticGraphic() {
                setContentDisplay(ContentDisplay.LEFT);
                setGraphicTextGap(10.2);
            }
        });

        listview_vocables.setEditable(true);

//        listview_vocables.setCellFactory((ListView<Vocable> t) -> {
//            ListCell<Vocable> vocablesListViewCell = new VocableCell();
//            return vocablesListViewCell;
//        listview_vocables.setCellFactory((ListView<Vocable> d) -> {
//            TextFieldListCell<Vocable> vocablesListViewCell = new VocableCell();
//			StringConverter<Vocable> converter = new StringConverter<Vocable>() {
//
//				@Override
//				public String toString(Vocable vocable) {
//					return vocable.getName();
//				}
//
//				@Override
//				public Vocable fromString(String string) {
//					Vocable vocable = vocablesListViewCell.getItem();
//					if (vocable == null) {
//						Vocable newVocable = new Vocable("aa");
//                        newVocable.setName(string);
//						return newVocable;
//					} else {
//						return vocable;
//					}
//				}
//			};
//        listview_vocables.setCellFactory(TextFieldListCell.forListView(new StringConverter<Vocable>() {
//            @Override
//            public String toString(Vocable vocable) {
//                return vocable.getName();
//            }
//
//            @Override
//            public Vocable fromString(String string) {
//                return new Vocable(string);
//            }
//
//            @Override
//            public void cancelEdit() {
//                super.cancelEdit();
//                setStaticGraphic();
//            }
//
//        }));
//			vocablesListViewCell.setConverter(converter);
//            return vocablesListViewCell;
//        });
    }

    private void setCellFactoryForTranslationsListView() {
        listview_translations.setCellFactory((ListView<Translation> t) -> {
            ListCell<Translation> translationsListViewCell = new TranslationCell();
            return translationsListViewCell;
        });
    }

    private void defineListViewVocablesBehaviours() {
        setSelectionChangeListenerForVocablesListView();
        setFocusChangeListenerForVocablesListView();
        if (listview_vocables.getItems() != null) {
            Collections.sort(listview_vocables.getItems(), (Vocable v1, Vocable v2) -> v1.toString().compareTo(v2.toString()));
        }
    }

    private void setSelectionChangeListenerForVocablesListView() {
        disposeSelectionChangeListenerVocables();
        selectionChangeListenerVocables = new SelectionChangeListenerVocables(dictionaryPage, listview_translations);
        listview_vocables.getSelectionModel().selectedItemProperty().addListener(selectionChangeListenerVocables);
    }

    private void setFocusChangeListenerForVocablesListView() {
        disposeFocusChangeListenerVocables();
        focusChangeListenerVocables = new FocusChangeListenerVocables(listview_vocables);
        listview_vocables.focusedProperty().addListener(focusChangeListenerVocables);
    }

    private void defineListViewTranslationsBehaviours() {
        setSelectionChangeListenerForTranslationsListView();
        setFocusChangeListenerForTranslationsListView();
        if (listview_translations.getItems() != null) {
            Collections.sort(listview_translations.getItems(), (Translation t1, Translation t2) -> t1.toString().compareTo(t2.toString()));
        }
    }

    private void setSelectionChangeListenerForTranslationsListView() {
        disposeSelectionChangeListenerTranslations();
        selectionChangeListenerTranslations = new SelectionChangeListenerTranslations();
        listview_translations.getSelectionModel().selectedItemProperty().addListener(selectionChangeListenerTranslations);
    }

    private void setFocusChangeListenerForTranslationsListView() {
        disposeFocusChangeListenerTranslations();
        focusChangeListenerTranslations = new FocusChangeListenerTranslations(listview_translations);
        listview_translations.focusedProperty().addListener(focusChangeListenerTranslations);
    }

    private void showActionPanel(boolean isShown, ListView listview) {
        if (isShown) {
            AnchorPane.setBottomAnchor(listview, 50.0);
        } else {
            AnchorPane.setBottomAnchor(listview, 0.0);
        }
    }

    private void resetView() {
        showActionPanel(false, listview_vocables);
        showActionPanel(false, listview_translations);
        listview_vocables.getSelectionModel().select(null);
        listview_translations.getSelectionModel().select(null);
        listview_vocables.setItems(null);
        listview_translations.setItems(null);
    }

    private void disposeSelectionChangeListenerVocables() {
        if (selectionChangeListenerVocables != null) {
            try {
                listview_vocables.getSelectionModel().selectedItemProperty().removeListener(selectionChangeListenerVocables);
            } catch (Exception ex) {
            }
        }
    }

    private void disposeFocusChangeListenerVocables() {
        if (focusChangeListenerVocables != null) {
            try {
                listview_vocables.focusedProperty().removeListener(focusChangeListenerVocables);
            } catch (Exception ex) {
            }
        }
    }

    private void disposeSelectionChangeListenerTranslations() {
        if (selectionChangeListenerTranslations != null) {
            try {
                listview_translations.getSelectionModel().selectedItemProperty().removeListener(selectionChangeListenerTranslations);
            } catch (Exception ex) {
            }
        }
    }

    private void disposeFocusChangeListenerTranslations() {
        if (focusChangeListenerTranslations != null) {
            try {
                listview_translations.focusedProperty().removeListener(focusChangeListenerTranslations);
            } catch (Exception ex) {
            }
        }
    }
}
