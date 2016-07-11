package com.matteoveroni.views.dictionary;

import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.views.dictionary.model.DictionaryDAO;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.dictionary.model.DictionaryPage;
import com.matteoveroni.views.dictionary.model.Translation;
import com.matteoveroni.views.dictionary.model.Vocable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
public class DictionaryPresenter implements Initializable {

    @Inject
    private DictionaryDAO model;
    @FXML
    private ListView<Vocable> list_vocables = new ListView<>();
    @FXML
    private TextArea textArea_translations;
    @FXML
    private BorderPane actionPaneVocabulary;
    @FXML
    private BorderPane actionPaneTranslations;

    private DictionaryPage dictionaryPage;

    private int pageOffset = 0;
    private int pageDimension = 10;

    private static final Logger LOG = LoggerFactory.getLogger(DictionaryPresenter.class);

    @Subscribe
    public void onViewChanged(EventViewChanged eventViewChanged) {
        if (eventViewChanged.getCurrentViewName() == ViewName.DICTIONARY) {
            resetView();
            loadData();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    void goBack(ActionEvent event) {
        EventBus.getDefault().post(new EventChangeView(ViewName.MAINMENU));
    }

    @FXML
    void add(ActionEvent event) {
    }

    private void loadData() {
        dictionaryPage = model.getDictionaryPage(pageOffset, pageDimension);

        List<Vocable> lista = new ArrayList<>();
        lista.addAll(dictionaryPage.getDictionary().keySet());

        ObservableList<Vocable> myObservableList = FXCollections.observableList(lista);

        list_vocables.setItems(myObservableList);

        defineListVocablesBehaviours();
        defineTranslationsAreaBehaviours();
    }

    private void defineListVocablesBehaviours() {
        setListVocablesCellFactory();
        defineListVocablesSelectionBehaviour();
        defineListVocablesFocusBehaviour();
        defineListVocablesSorting();
    }

    private void defineTranslationsAreaBehaviours() {
        defineTranslationsAreaFocusBehaviour();
    }

    private void setListVocablesCellFactory() {
        list_vocables.setCellFactory((ListView<Vocable> d) -> {
            ListCell<Vocable> cell = new ListCell<Vocable>() {

                @Override
                protected void updateItem(Vocable v, boolean bln) {
                    super.updateItem(v, bln);
                    if (v != null) {
                        setText(v.getName());
                    }
                }
            };

            return cell;
        });
    }

    private void defineListVocablesSelectionBehaviour() {
        list_vocables.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Vocable>() {
            @Override
            public void changed(ObservableValue<? extends Vocable> observable, Vocable oldValue, Vocable newValue) {
                if (newValue != null) {
                    List<Translation> translations = dictionaryPage.getDictionary().get(newValue);
                    LOG.debug("Vocable found => " + newValue.getName());
                    if (translations != null) {
                        populateTextAreaTranslations(translations);
                        showActionPanelVocabulary(true);
                    }
                }
            }

            private void populateTextAreaTranslations(List<Translation> translations) {
                textArea_translations.clear();
                String str_translations = "";
                for (int i = 0; i < translations.size(); i++) {
                    LOG.debug("Translation found => " + translations.get(i));
                    str_translations += translations.get(i);
                    if (i >= 0 && i < translations.size() - 1) {
                        str_translations += ", ";
                    }
                    textArea_translations.setText(str_translations);
                }
            }
        });
    }

    private void defineListVocablesFocusBehaviour() {
        list_vocables.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == true) {
                    if (list_vocables.getSelectionModel().getSelectedItem() == null) {
                        list_vocables.getSelectionModel().selectFirst();
                    }
                    showActionPanelVocabulary(true);
                } else {
                    list_vocables.getSelectionModel().select(null);
                    showActionPanelVocabulary(false);
                }
            }
        });
    }

    private void defineListVocablesSorting() {
        Collections.sort(list_vocables.getItems(), (Vocable voc1, Vocable voc2) -> voc1.toString().compareTo(voc2.toString()));
    }

    private void defineTranslationsAreaFocusBehaviour() {
        textArea_translations.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == true) {
                    showActionPanelTranslations(true);
                } else {
                    showActionPanelTranslations(false);
                }
            }
        });
    }

    private void showActionPanelVocabulary(boolean isShown) {
        if (isShown) {
            AnchorPane.setBottomAnchor(list_vocables, 50.0);
        } else {
            AnchorPane.setBottomAnchor(list_vocables, 0.0);
        }
    }

    private void showActionPanelTranslations(boolean isShown) {
        if (isShown) {
            AnchorPane.setBottomAnchor(textArea_translations, 50.0);
        } else {
            AnchorPane.setBottomAnchor(textArea_translations, 0.0);
        }
    }

    private void resetView() {
        showActionPanelVocabulary(false);
        showActionPanelTranslations(false);
        textArea_translations.clear();
        list_vocables.getSelectionModel().select(null);
    }

//    code for modifiable listview
//    ListView<String> list = new ListView<>();
//    Image testImg = new Rectangle(12, 12, Color.CORNFLOWERBLUE).snapshot(null, null);
//    for (int i = 0;
//    i< 6; i
//
//    
//        ++) {
//            list.getItems().add("label " + i);
//    }
//
//    StringConverter<String> identityStringConverter = new DefaultStringConverter();
//
//    list.setCellFactory (lv  -> new TextFieldListCell<String>(identityStringConverter) {
//
//    private ImageView imageView = new ImageView(testImg);
//
//    @Override
//    public void updateItem(String item, boolean empty) {
//        super.updateItem(item, empty);
//        if (!empty && !isEditing()) {
//            setStaticGraphic();
//        }
//    }
//
//    @Override
//    public void cancelEdit() {
//        super.cancelEdit();
//        setStaticGraphic();
//    }
//
//    @Override
//    public void commitEdit(String newValue) {
//        super.commitEdit(newValue);
//        setStaticGraphic();
//    }
//
//    private void setStaticGraphic() {
//        setGraphic(imageView);
//        setContentDisplay(ContentDisplay.LEFT);
//        setGraphicTextGap(10.2);
//    }
//}
//);
//
//        list.setEditable(true);
}
