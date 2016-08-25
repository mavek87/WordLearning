package com.matteoveroni.views.dictionary;

import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventGoToPreviousView;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.views.dictionary.model.DictionaryDAO;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.dictionary.events.EventShowVocablesActionPanel;
import com.matteoveroni.views.dictionary.listviews.listeners.SelectionChangeListenerVocables;
import com.matteoveroni.views.dictionary.listviews.listeners.FocusChangeListenerVocables;
import com.matteoveroni.views.dictionary.model.DictionaryPage;
import com.matteoveroni.views.dictionary.model.pojo.Translation;
import com.matteoveroni.views.dictionary.model.pojo.Vocable;
import com.matteoveroni.views.translations.TranslationsPresenter;
import com.matteoveroni.views.translations.TranslationsView;
import com.matteoveroni.views.translations.events.EventNewTranslationsToShow;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import impl.org.controlsfx.i18n.Translations;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
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
	private BorderPane actionPaneVocabulary;
	@FXML
	private BorderPane pane_translations;
	@FXML
	private HBox hbbox_bottomActions;
	@FXML
	private Button btn_goBack;
	@FXML
	private Button btn_add;

	private enum ActionPaneType {
		VOCABULARY, TRANSLATIONS;
	}

	private SelectionChangeListenerVocables selectionChangeListenerVocables;
	private FocusChangeListenerVocables focusChangeListenerVocables;

	private DictionaryPage dictionaryPage;

	private int pageOffset = 0;
	private int pageDimension = 10;

	private static final Logger LOG = LoggerFactory.getLogger(DictionaryPresenter.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btn_goBack.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.REPLY));
		btn_add.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.PLUS));
		
		List<Translation> lt = new ArrayList<>();
		Translation t = new Translation(5, "aaa");
		lt.add(t);
		
		TranslationsView tv = new TranslationsView();
		Parent p = tv.getView();

		TranslationsPresenter tp = (TranslationsPresenter) tv.getPresenter();
		tp.onEventNewTranslationsToShow(new EventNewTranslationsToShow(lt));
		pane_translations.setCenter(p);
	}

	@Subscribe
	public void onViewChanged(EventViewChanged eventViewChanged) {
		if (eventViewChanged.getCurrentViewName() == ViewName.DICTIONARY) {
			resetViewAndSelection();
			loadViewDataAndBehaviours();
		}
	}

	@Subscribe
	public void onEventShowVocableActionPanelChange(EventShowVocablesActionPanel eventShowVocablesActionPanel) {
		showActionPanel(eventShowVocablesActionPanel.getShowValue(), listview_vocables, ActionPaneType.VOCABULARY);
	}

	@FXML
	void goBack(ActionEvent event) {
		EventBus.getDefault().post(new EventGoToPreviousView());
	}

	@FXML
	void goToAdd(ActionEvent event) {
		EventBus.getDefault().post(new EventChangeView(ViewName.CREATION));
	}

	@FXML
	void goToVocableEdit(ActionEvent event) {
		Vocable selectedVocable = listview_vocables.getSelectionModel().getSelectedItem();
		if (selectedVocable != null) {
			EventBus.getDefault().post(new EventChangeView(ViewName.EDIT, selectedVocable));
		}
	}

	public int getPageOffset() {
		return pageOffset;
	}

	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}

	public int getPageDimension() {
		return pageDimension;
	}

	public void setPageDimension(int pageDimension) {
		this.pageDimension = pageDimension;
	}

	private void loadViewDataAndBehaviours() {
		dictionaryPage = model.getDictionaryPage(pageOffset, pageDimension);

		List<Vocable> lista = new ArrayList<>();
		lista.addAll(dictionaryPage.getDictionary().keySet());
		ObservableList<Vocable> observableVocablesList = FXCollections.observableList(lista);
		listview_vocables.setItems(observableVocablesList);

		setCellFactoryForVocablesList();
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
				if (newValue == null || newValue.getName().trim().isEmpty()) {
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
	}

	private void showActionPanel(boolean isShown, ListView listview, ActionPaneType actionPaneType) {
		if (isShown) {
			Button btn_create = new Button();
			btn_create.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.PLUS));
			btn_create.setPrefWidth(50);
			Button btn_center = new Button();
			btn_center.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.EDIT));
			btn_center.setPrefWidth(50);
			Button btn_right = new Button();
			btn_right.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.TRASH));
			btn_right.setPrefWidth(50);
			btn_center.setOnAction((event) -> {
				Vocable selectedVocable = (Vocable) listview.getSelectionModel().getSelectedItem();
				if (selectedVocable != null) {
					EventBus.getDefault().post(new EventChangeView(ViewName.EDIT, selectedVocable));
				}
			});
			btn_right.setOnAction((event) -> {
				Vocable selectedVocable = listview_vocables.getSelectionModel().getSelectedItem();
				if (selectedVocable != null) {
				}
			});
			actionPaneVocabulary.setLeft(btn_create);
			actionPaneVocabulary.setCenter(btn_center);
			actionPaneVocabulary.setRight(btn_right);
		} else {
			actionPaneVocabulary.setLeft(null);
			actionPaneVocabulary.setCenter(null);
			actionPaneVocabulary.setRight(null);
		}
	}

	private void resetViewAndSelection() {
		showActionPanel(false, listview_vocables, ActionPaneType.VOCABULARY);
		listview_vocables.getSelectionModel().select(null);
		listview_vocables.setItems(null);
	}

	@Override
	public void dispose() {
		disposeSelectionChangeListenerVocables();
		disposeFocusChangeListenerVocables();
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
}
