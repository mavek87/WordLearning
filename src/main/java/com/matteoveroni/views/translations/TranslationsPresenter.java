package com.matteoveroni.views.translations;

import com.matteoveroni.bus.events.EventChangeView;
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
import java.util.Collections;
import java.util.ResourceBundle;
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

	private final Button buttonLeft = new Button();
	private final Button buttonRight = new Button();

	private static final Logger LOG = LoggerFactory.getLogger(TranslationsPresenter.class);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btn_search.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.SEARCH));
		btn_add.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.PLUS));
		buildViewComponents();
		resetView();
		loadViewDataAndBehaviours();
	}

	@Subscribe
	public void onViewChanged(EventViewChanged eventViewChanged) {
		if (eventViewChanged.getCurrentViewName() == ViewName.DICTIONARY) {
		}
	}

	@Subscribe
	public void onEventNewTranslationsToShow(EventNewTranslationsToShow event) {
		resetView();
		ObservableList<Translation> observableTranslationsList = FXCollections.observableList(event.getTranslations());
		listview_translations.setItems(observableTranslationsList);
	}

	@Subscribe
	public void onEventShowTranslationsActionPanelChange(EventShowTranslationsActionPanel eventShowTranslationsActionPanel) {
		showActionPanel(eventShowTranslationsActionPanel.getShowValue());
	}

	@FXML
	void goBack(ActionEvent event) {
		EventBus.getDefault().post(new EventChangeView(ViewName.MAINMENU));
	}

	@FXML
	void goToMain(ActionEvent event) {
		EventBus.getDefault().post(new EventChangeView(ViewName.MAINMENU));
	}

	private void loadViewDataAndBehaviours() {
		setCellFactoryForTranslationsListView();
		defineListViewTranslationsBehaviours();
	}

	private void setCellFactoryForTranslationsListView() {
		listview_translations.setCellFactory((ListView<Translation> t) -> {
			ListCell<Translation> translationsListViewCell = new TranslationCell();
			return translationsListViewCell;
		});
	}

	private void defineListViewTranslationsBehaviours() {
		setSelectionChangeListenerForTranslationsListView();
		setFocusChangeListenerForTranslationsListView();
		if (listview_translations.getItems() != null) {
			Collections.sort(listview_translations.getItems(), (Translation t1, Translation t2) -> t1.toString().compareTo(t2.toString()));
		}
	}

	private void setSelectionChangeListenerForTranslationsListView() {
		disposeCurrentSelectionChangeListenerTranslations();
		selectionChangeListenerTranslations = new SelectionChangeListenerTranslations();
		listview_translations.getSelectionModel().selectedItemProperty().addListener(selectionChangeListenerTranslations);
	}

	private void setFocusChangeListenerForTranslationsListView() {
		disposeCurrentFocusChangeListenerTranslations();
		focusChangeListenerTranslations = new FocusChangeListenerTranslations(listview_translations);
		listview_translations.focusedProperty().addListener(focusChangeListenerTranslations);
	}

	private void showActionPanel(boolean isShown) {
		if (isShown) {
			AnchorPane.setBottomAnchor(listview_translations, 55.0);
			actionPaneTranslations.setLeft(buttonLeft);
			actionPaneTranslations.setRight(buttonRight);

		} else {
			AnchorPane.setBottomAnchor(listview_translations, 0.0);
			actionPaneTranslations.setLeft(null);
			actionPaneTranslations.setRight(null);
		}
	}

	private void removeTranslation() {
	}

	private void buildViewComponents() {
		buttonLeft.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.EDIT));
		buttonLeft.setPrefWidth(50);
		buttonRight.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.TRASH));
		buttonRight.setPrefWidth(50);

		buttonLeft.setOnAction((event) -> {
			Translation selectedTranslation = (Translation) listview_translations.getSelectionModel().getSelectedItem();
			if (selectedTranslation != null) {
				EventBus.getDefault().post(new EventChangeView(ViewName.EDIT_VOCABLE, selectedTranslation));
			}
		});
		buttonRight.setOnAction((event) -> {
			Translation selectedTranslation = (Translation) listview_translations.getSelectionModel().getSelectedItem();
			if (selectedTranslation != null) {
				removeTranslation();
			}
		});
	}

	private void resetView() {
		showActionPanel(false);
		listview_translations.getSelectionModel().select(null);
		listview_translations.setItems(null);
	}

	@Override
	public void dispose() {
		disposeCurrentSelectionChangeListenerTranslations();
		disposeCurrentFocusChangeListenerTranslations();
	}

	private void disposeCurrentSelectionChangeListenerTranslations() {
		if (selectionChangeListenerTranslations != null) {
			try {
				listview_translations.getSelectionModel().selectedItemProperty().removeListener(selectionChangeListenerTranslations);
			} catch (Exception ex) {
			}
		}
	}

	private void disposeCurrentFocusChangeListenerTranslations() {
		if (focusChangeListenerTranslations != null) {
			try {
				listview_translations.focusedProperty().removeListener(focusChangeListenerTranslations);
			} catch (Exception ex) {
			}
		}
	}

}
