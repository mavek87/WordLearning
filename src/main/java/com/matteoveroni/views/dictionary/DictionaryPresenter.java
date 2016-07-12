package com.matteoveroni.views.dictionary;

import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.views.dictionary.model.DictionaryDAO;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.dictionary.bus.events.EventShowTranslationsActionPanel;
import com.matteoveroni.views.dictionary.bus.events.EventShowVocablesActionPanel;
import com.matteoveroni.views.dictionary.listcells.VocablesListViewCell;
import com.matteoveroni.views.dictionary.listcells.TranslationsListViewCell;
import com.matteoveroni.views.dictionary.listeners.SelectionChangeListenerListViewVocables;
import com.matteoveroni.views.dictionary.listeners.FocusChangeListenerListViewVocables;
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
	
	@Subscribe
	public void onEventShowVocableActionPanelChange(EventShowVocablesActionPanel eventShowVocableActionPanel) {
		showActionPanel(eventShowVocableActionPanel.getShowValue(), listview_vocables);
	}
	
	@Subscribe
	public void onEventShowVocableActionPanelChange(EventShowTranslationsActionPanel eventShowTranslationsActionPanel) {
		showActionPanel(eventShowTranslationsActionPanel.getShowValue(), listview_translations);
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
		
		ObservableList<Vocable> observableVocablesList = FXCollections.observableList(lista);
		listview_vocables.setItems(observableVocablesList);
		
		defineListVocablesBehaviours();
		defineTranslationsAreaBehaviours();
	}
	
	private void defineListVocablesBehaviours() {
		setVocablesListViewCellFactory();
		listview_vocables.getSelectionModel().selectedItemProperty().addListener(new SelectionChangeListenerListViewVocables(dictionaryPage, listview_translations));
		listview_vocables.focusedProperty().addListener(new FocusChangeListenerListViewVocables(listview_vocables));
		Collections.sort(listview_vocables.getItems(), (Vocable v1, Vocable v2) -> v1.toString().compareTo(v2.toString()));
	}
	
	private void setVocablesListViewCellFactory() {
		listview_vocables.setCellFactory((ListView<Vocable> d) -> {
			ListCell<Vocable> vocablesListViewCell = new VocablesListViewCell();
			return vocablesListViewCell;
		});
	}
	
	private void defineTranslationsAreaBehaviours() {
		setTranslationsListViewCellFactory();
//        defineTranslationsListViewSelectionBehaviour();
//        defineTranslationsListViewFocusBehaviour();
		Collections.sort(listview_translations.getItems(), (Translation t1, Translation t2) -> t1.toString().compareTo(t2.toString()));
	}
	
	private void setTranslationsListViewCellFactory() {
		listview_translations.setCellFactory((ListView<Translation> t) -> {
			ListCell<Translation> translationsListViewCell = new TranslationsListViewCell();
			return translationsListViewCell;
		});
	}

//	private void defineVocablesListViewFocusBehaviour() {
//		listview_vocables.focusedProperty().addListener(new VocablesListViewFocusChangeListener(listview_vocables));
//	}
//	private void defineTranslationsListViewFocusBehaviour() {
//		listview_translations.focusedProperty().addListener(new ChangeListener<Boolean>() {
//			@Override
//			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//				if (newValue == true) {
//					if (listview_translations.getSelectionModel().getSelectedItem() == null) {
//						if (!listview_translations.getItems().isEmpty()) {
//							listview_translations.getSelectionModel().selectFirst();
//						}
//					}
//					showActionPanelTranslations(true);
//				} else {
//					listview_vocables.getSelectionModel().clearSelection();
//					listview_vocables.getSelectionModel().select(null);
//					showActionPanelTranslations(false);
//				}
//			}
//		});
//	}
//    private void defineVocablesListViewSorting() {
//        Collections.sort(listview_vocables.getItems(), (Vocable voc1, Vocable voc2) -> voc1.toString().compareTo(voc2.toString()));
//    }
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
