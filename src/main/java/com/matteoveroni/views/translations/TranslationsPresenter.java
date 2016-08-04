package com.matteoveroni.views.translations;

import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventGoToPreviousView;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.dictionary.events.EventShowTranslationsActionPanel;
import com.matteoveroni.views.dictionary.listviews.cells.TranslationCell;
import com.matteoveroni.views.dictionary.listviews.listeners.FocusChangeListenerTranslations;
import com.matteoveroni.views.dictionary.listviews.listeners.SelectionChangeListenerTranslations;
import com.matteoveroni.views.dictionary.model.pojo.Translation;
import com.matteoveroni.views.translations.events.EventNewTranslationsToShow;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Translations Presenter (Controller class)
 *
 * @author Matteo Veroni
 */
public class TranslationsPresenter implements Initializable, Disposable {

	@FXML
	private Button btn_search;
	@FXML
	private Button btn_add;
	@FXML
	private ListView<Translation> listview_translations = new ListView<>();
	@FXML
	private BorderPane actionPaneTranslations;

	private SelectionChangeListenerTranslations selectionChangeListenerTranslations;
	private FocusChangeListenerTranslations focusChangeListenerTranslations;

	private final Button btn_left = new Button();
	private final Button btn_right = new Button();

	private static final Logger LOG = LoggerFactory.getLogger(TranslationsPresenter.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		resetView();
		initializeViewComponentsAndBehaviours();
	}

	@Subscribe
	public void onViewChanged(EventViewChanged eventViewChanged) {
	}

	@Subscribe
	public void onEventNewTranslationsToShow(EventNewTranslationsToShow event) {
		List<Translation> newTranslationsToShow = event.getTranslations();
		if (newTranslationsToShow != null) {
			resetView();
			ObservableList<Translation> observableTranslationsList = FXCollections.observableList(event.getTranslations());
			listview_translations.setItems(observableTranslationsList);
		}
	}

	@Subscribe
	public void onEventShowTranslationsActionPanelChange(EventShowTranslationsActionPanel eventShowTranslationsActionPanel) {
		showActionPanel(eventShowTranslationsActionPanel.getShowValue());
	}

	@Override
	public void dispose() {
		disposeAllListenersFromListView();
	}

	@FXML
	void goBack(ActionEvent event) {
		EventBus.getDefault().post(new EventGoToPreviousView());
	}

	@FXML
	void goToMain(ActionEvent event) {
		EventBus.getDefault().post(new EventChangeView(ViewName.MAINMENU));
	}

	private void resetView() {
		showActionPanel(false);
		listview_translations.getSelectionModel().select(null);
		listview_translations.setItems(null);
	}

	private void initializeViewComponentsAndBehaviours() {
		initializeViewButtons();
		setCellFactoryForTranslationsListView();
		defineListViewTranslationsBehaviours();
	}

	private void initializeViewButtons() {
		btn_search.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.SEARCH));
		btn_add.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.PLUS));
		btn_left.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.EDIT));
		btn_left.setPrefWidth(50);
		btn_left.setOnAction((event) -> {
			Translation selectedTranslation = listview_translations.getSelectionModel().getSelectedItem();
			if (selectedTranslation != null) {
				EventBus.getDefault().post(new EventChangeView(ViewName.EDIT, selectedTranslation));
			}
		});
		btn_right.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.TRASH));
		btn_right.setPrefWidth(50);
		btn_right.setOnAction((event) -> {
			Translation selectedTranslation = listview_translations.getSelectionModel().getSelectedItem();
			if (selectedTranslation != null) {
				removeTranslation();
			}
		});
	}

	private void setCellFactoryForTranslationsListView() {
		listview_translations.setCellFactory((ListView<Translation> t) -> {
			ListCell<Translation> translationsListViewCell = new TranslationCell();
			return translationsListViewCell;
		});
	}

	private void defineListViewTranslationsBehaviours() {
		setSelectionChangeListenerForTranslationsListView();
//		setFocusChangeListenerForTranslationsListView();
//		if (listview_translations.getItems() != null) {
//			Collections.sort(listview_translations.getItems(), (Translation t1, Translation t2) -> t1.toString().compareTo(t2.toString()));
//		}
	}

	private void setSelectionChangeListenerForTranslationsListView() {
		disposeChangeListenerFromListView(selectionChangeListenerTranslations);
		selectionChangeListenerTranslations = new SelectionChangeListenerTranslations();
		listview_translations.getSelectionModel().selectedItemProperty().addListener(selectionChangeListenerTranslations);
	}

	private void setFocusChangeListenerForTranslationsListView() {
		disposeChangeListenerFromListView(focusChangeListenerTranslations);
		focusChangeListenerTranslations = new FocusChangeListenerTranslations(listview_translations);
		listview_translations.focusedProperty().addListener(focusChangeListenerTranslations);
	}

	private void showActionPanel(boolean isShown) {
		if (isShown) {
			AnchorPane.setBottomAnchor(listview_translations, 55.0);
			actionPaneTranslations.setLeft(btn_left);
			actionPaneTranslations.setRight(btn_right);
		} else {
			AnchorPane.setBottomAnchor(listview_translations, 0.0);
			actionPaneTranslations.setLeft(null);
			actionPaneTranslations.setRight(null);
		}
	}

	private void removeTranslation() {
	}

	private void disposeAllListenersFromListView() {
		disposeChangeListenerFromListView(selectionChangeListenerTranslations);
		disposeChangeListenerFromListView(focusChangeListenerTranslations);
	}

	private void disposeChangeListenerFromListView(ChangeListener listener) {
		if (listener != null) {
			try {
				listview_translations.getSelectionModel().selectedItemProperty().removeListener(listener);
			} catch (Exception ex) {
				LOG.error(ex.getMessage());
			}
		}
	}
}
