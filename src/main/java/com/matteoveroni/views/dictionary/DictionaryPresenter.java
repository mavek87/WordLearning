package com.matteoveroni.views.dictionary;

import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.views.dictionary.model.DictionaryDAO;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.dictionary.bus.events.EventShowTranslationsActionPanel;
import com.matteoveroni.views.dictionary.bus.events.EventShowVocablesActionPanel;
import com.matteoveroni.views.dictionary.listviews.cells.VocablesCell;
import com.matteoveroni.views.dictionary.listviews.cells.TranslationsCell;
import com.matteoveroni.views.dictionary.listviews.listeners.FocusChangeListenerTranslations;
import com.matteoveroni.views.dictionary.listviews.listeners.SelectionChangeListenerVocables;
import com.matteoveroni.views.dictionary.listviews.listeners.FocusChangeListenerVocables;
import com.matteoveroni.views.dictionary.listviews.listeners.SelectionChangeListenerTranslations;
import com.matteoveroni.views.dictionary.model.DictionaryPage;
import com.matteoveroni.views.dictionary.model.Translation;
import com.matteoveroni.views.dictionary.model.Vocable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
	private ListView<Vocable> listview_vocables = new ListView<>();
	@FXML
	private ListView<Translation> listview_translations = new ListView<>();
	@FXML
	private BorderPane actionPaneVocabulary;
	@FXML
	private BorderPane actionPaneTranslations;

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

	private void initializeView() {
		dictionaryPage = model.getDictionaryPage(pageOffset, pageDimension);

		List<Vocable> lista = new ArrayList<>();
		lista.addAll(dictionaryPage.getDictionary().keySet());

		ObservableList<Vocable> observableVocablesList = FXCollections.observableList(lista);
		listview_vocables.setItems(observableVocablesList);

		defineListViewVocablesBehaviours();
		defineListViewTranslationsBehaviours();
	}

	private void defineListViewVocablesBehaviours() {
		setCellFactoryForVocablesList();
		setSelectionChangeListenerForVocablesListView();
		setFocusChangeListenerForVocablesListView();
		if (listview_vocables.getItems() != null) {
			Collections.sort(listview_vocables.getItems(), (Vocable v1, Vocable v2) -> v1.toString().compareTo(v2.toString()));
		}
	}

	private void setCellFactoryForVocablesList() {
		listview_vocables.setCellFactory((ListView<Vocable> d) -> {
			ListCell<Vocable> vocablesListViewCell = new VocablesCell();
			return vocablesListViewCell;
		});
	}

	private void setSelectionChangeListenerForVocablesListView() {
		if (selectionChangeListenerVocables != null) {
			try {
				listview_vocables.getSelectionModel().selectedItemProperty().removeListener(selectionChangeListenerVocables);
			} catch (Exception ex) {
			}
		}
		selectionChangeListenerVocables = new SelectionChangeListenerVocables(dictionaryPage, listview_translations);
		listview_vocables.getSelectionModel().selectedItemProperty().addListener(selectionChangeListenerVocables);
	}

	private void setFocusChangeListenerForVocablesListView() {
		if (focusChangeListenerVocables != null) {
			try {
				listview_vocables.focusedProperty().removeListener(focusChangeListenerVocables);
			} catch (Exception ex) {
			}
		}
		focusChangeListenerVocables = new FocusChangeListenerVocables(listview_vocables);
		listview_vocables.focusedProperty().addListener(focusChangeListenerVocables);
	}

	private void defineListViewTranslationsBehaviours() {
		setCellFactoryForTranslationsListView();
		setSelectionChangeListenerForTranslationsListView();
		setFocusChangeListenerForTranslationsListView();
		if (listview_translations.getItems() != null) {
			Collections.sort(listview_translations.getItems(), (Translation t1, Translation t2) -> t1.toString().compareTo(t2.toString()));
		}
	}

	private void setSelectionChangeListenerForTranslationsListView() {
		if (selectionChangeListenerTranslations != null) {
			try {
				listview_translations.getSelectionModel().selectedItemProperty().removeListener(selectionChangeListenerTranslations);
			} catch (Exception ex) {
			}
		}
		selectionChangeListenerTranslations = new SelectionChangeListenerTranslations();
		listview_translations.getSelectionModel().selectedItemProperty().addListener(selectionChangeListenerTranslations);
	}

	private void setFocusChangeListenerForTranslationsListView() {
		if (focusChangeListenerTranslations != null) {
			try {
				listview_translations.focusedProperty().removeListener(focusChangeListenerTranslations);
			} catch (Exception ex) {
			}
		}
		focusChangeListenerTranslations = new FocusChangeListenerTranslations(listview_translations);
		listview_translations.focusedProperty().addListener(focusChangeListenerTranslations);
	}

	private void setCellFactoryForTranslationsListView() {
		listview_translations.setCellFactory((ListView<Translation> t) -> {
			ListCell<Translation> translationsListViewCell = new TranslationsCell();
			return translationsListViewCell;
		});
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

//    private void clearListView(ListView listview) {
//        listview.setItems(FXCollections.observableArrayList(new ArrayList<Translation>()));
//        listview.refresh();
//    }
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
