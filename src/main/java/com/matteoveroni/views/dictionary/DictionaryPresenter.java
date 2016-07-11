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
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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

	private DictionaryPage dictionaryPage;

	@Subscribe
	public void onViewChanged(EventViewChanged eventViewChanged) {
		if (eventViewChanged.getCurrentViewName() == ViewName.DICTIONARY) {
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
		dictionaryPage = model.getDictionaryPage(0, 2);

//		ObservableList<Vocable> myObservableList = FXCollections.observableList(dictionaryPage.getVocables());
		List<Vocable> lista = new ArrayList<>();
		lista.addAll(dictionaryPage.getDictionary().keySet());

		ObservableList<Vocable> myObservableList = FXCollections.observableList(lista);

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

		list_vocables.setItems(myObservableList);

		list_vocables.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Vocable>() {
			@Override
			public void changed(ObservableValue<? extends Vocable> observable, Vocable oldValue, Vocable newValue) {
				if (newValue != null) {
					List<Translation> translations = dictionaryPage.getDictionary().get(newValue);
					System.out.println("Vocable => " + newValue.getName());
					if (translations != null) {
						for (Translation translation : translations) {
							System.out.println("Translation => " + translation.getTranslation());
						}
					}
				}
			}
		});
	}
}

//
//public class DictionaryPresenter implements Initializable {
//
//	@Inject
//	private DictionaryDAO model;
//	@FXML
//	private ListView<Vocable> listView_vocables = new ListView<>();
//	private ObservableMap<Vocable, List<Translation>> observableMap_dictionary = FXCollections.observableHashMap();;
//
//	@Subscribe
//	public void onViewChanged(EventViewChanged eventViewChanged) {
//		if (eventViewChanged.getCurrentViewName() == ViewName.DICTIONARY) {
//			loadData();
//		}
//	}
//
//	@Override
//	public void initialize(URL location, ResourceBundle resources) {
//	}
//
//	@FXML
//	void goBack(ActionEvent event) {
//		EventBus.getDefault().post(new EventChangeView(ViewName.MAINMENU));
//	}
//
//	@FXML
//	void add(ActionEvent event) {
//
//	}
//
//	private void loadData() {
//		DictionaryPage dictionaryPage = model.getDictionaryPage(0, 2);
//
////		List<Vocable> lista = new ArrayList<>();
////		lista.add(new Vocable("ciao"));
//	
//		observableMap_dictionary = FXCollections.observableMap(dictionaryPage.getDictionary());
//		
//		listView_vocables.getItems().setAll(observableMap_dictionary.keySet());
//		
//		for(Vocable v : observableMap_dictionary.keySet()){
//			System.out.println("v " + v.getName());
//		}
//				
////		List<Vocable> listVocables = new ArrayList<>();
////		listVocables.addAll(observableMap_dictionary.keySet());
//		
////		ObservableList<Vocable> myObservableList = FXCollections.observableList(listVocables);
//		
////		myObservableList.getItems().setAll(extensionToMimeMap.keySet());
//		
////		ObservableList<Vocable> myObservableList = FXCollections.observableList(dictionaryPage.getVocables());
//
//		listView_vocables.setCellFactory((ListView<Vocable> d) -> {
//			ListCell<Vocable> cell = new ListCell<Vocable>() {
//
//				@Override
//				protected void updateItem(Vocable v, boolean bln) {
//					super.updateItem(v, bln);
//					if (v != null) {
//						setText(v.getName());
//					}
//				}
//
//			};
//
//			return cell;
//		});
//
//		listView_vocables.setItems(myObservableList);
//	}
//}
