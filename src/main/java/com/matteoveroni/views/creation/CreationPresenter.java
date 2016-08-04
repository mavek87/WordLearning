package com.matteoveroni.views.creation;

import com.airhacks.afterburner.views.FXMLView;
import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventGoToPreviousView;
import com.matteoveroni.bus.events.EventRequestView;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.bus.events.EventSendView;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.creation.model.CreationModel;
import com.matteoveroni.views.creation.model.events.EventRadioButtonSelectionChanged;
import com.matteoveroni.views.creation.model.listeners.RadioToggleGroupChangeListener;
import com.matteoveroni.views.dictionary.model.pojo.Translation;
import com.matteoveroni.views.dictionary.model.pojo.Vocable;
import com.matteoveroni.views.translations.TranslationsPresenter;
import com.matteoveroni.views.translations.events.EventNewTranslationsToShow;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FXML Controller class
 *
 * @author Matteo Veroni
 */
public class CreationPresenter implements Initializable, Disposable {

	private final CreationModel model = new CreationModel();

	private final ToggleGroup toggleGroup = new ToggleGroup();
	private final RadioToggleGroupChangeListener radioToggleGroupChangeListener = new RadioToggleGroupChangeListener();

	@FXML
	private RadioButton radio_translation;
	@FXML
	private RadioButton radio_vocable;
	@FXML
	private Button btn_goBack;
	@FXML
	private HBox hbox_vocable;
	private final Label lbl_newVocable = new Label();
	private final TextField txt_newVocable = new TextField();
	private final Button btn_saveNewVocable = new Button();

	@FXML
	private HBox hbox_searchVocable;
	private final Label lbl_searchVocable = new Label();
	private final TextField txt_searchVocable = new TextField();
	private ListView<Vocable> listView_searchVocable;

	@FXML
	private HBox hbox_translation;
	private final Label lbl_newTranslation = new Label();
	private final TextField txt_newTranslation = new TextField();
	private final Button btn_saveNewTranslation = new Button();

	private ResourceBundle resourceBundle;

	private static final Logger LOG = LoggerFactory.getLogger(CreationPresenter.class);

	@Override
	public void initialize(URL location, ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
		btn_goBack.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.REPLY));

		radio_vocable.setToggleGroup(toggleGroup);
		radio_translation.setToggleGroup(toggleGroup);
		toggleGroup.selectedToggleProperty().addListener(radioToggleGroupChangeListener);

		createVocableComponents();
		createTranslationComponents();
	}

	@Subscribe
	public void onViewChanged(EventViewChanged eventViewChanged) {
		if (eventViewChanged.getCurrentViewName() == ViewName.CREATION) {
			resetView();
			changeViewForVocableSelection();
		}
	}

	@Subscribe
	public void onViewRequestedReceived(EventSendView eventSendedView) {
		try {
			FXMLView fxmlView = eventSendedView.getFXMLView();
			hbox_searchVocable.getChildren().add(fxmlView.getView());
			System.out.println("fxmlview requested => " + fxmlView.toString());
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
			ex.printStackTrace();
		}
	}

	@Subscribe
	public void RadioButtonSelectionChanged(EventRadioButtonSelectionChanged event) {
		RadioButton radioButtonSelected = event.getRadioButtonSelected();
		LOG.debug("Selected Radio Button => " + radioButtonSelected.getText());
		if (radioButtonSelected == radio_vocable) {
			changeViewForVocableSelection();
		} else if (radioButtonSelected == radio_translation) {
			changeViewForTranslationSelection();
		}
	}

	@FXML
	void goBack(ActionEvent event) {
		EventBus.getDefault().post(new EventGoToPreviousView());
	}

	private void changeViewForVocableSelection() {
		changeViewForVocableSelection(true);
	}

	private void changeViewForVocableSelection(boolean isVocableSelected) {
		hbox_vocable.getChildren().clear();
		if (isVocableSelected) {
			hbox_vocable.getChildren().add(lbl_newVocable);
			hbox_vocable.getChildren().add(txt_newVocable);
			hbox_vocable.getChildren().add(btn_saveNewVocable);
			changeViewForTranslationSelection(false);
		}
	}

	private void changeViewForTranslationSelection() {
		changeViewForTranslationSelection(true);
	}

	private void changeViewForTranslationSelection(boolean isTranslationSelected) {
		hbox_searchVocable.getChildren().clear();
		if (isTranslationSelected) {
			EventBus.getDefault().post(new EventRequestView(ViewName.TRANSLATIONS));
			changeViewForVocableSelection(false);
		}
	}

	private void resetView() {
		radio_vocable.setSelected(true);
		txt_newVocable.clear();
	}

	private void createVocableComponents() {
		btn_saveNewVocable.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.SAVE));
		btn_saveNewVocable.setPrefWidth(50);
		btn_saveNewVocable.setOnAction((ActionEvent event) -> {
			saveVocable();
		});
	}

	private void saveVocable() {
		Alert alertSaveVocable = new Alert(AlertType.NONE);
		alertSaveVocable.setTitle("Save vocable");
		String str_vocable = txt_newVocable.getText();
		try {
			model.saveStringToVocable(str_vocable);
			alertSaveVocable.setAlertType(AlertType.INFORMATION);
			alertSaveVocable.setContentText("Salvataggio " + str_vocable + " riuscito!");
			resetView();
			changeViewForVocableSelection(true);
		} catch (Exception ex) {
			alertSaveVocable.setAlertType(AlertType.ERROR);
			alertSaveVocable.setHeaderText("Errore salvataggio");
			String alertMessage = "Cause: " + ex.getCause() + "\nMessage: " + ex.getMessage();
			alertSaveVocable.setContentText(alertMessage);
		}
		alertSaveVocable.showAndWait();
	}

	private void createTranslationComponents() {
		lbl_searchVocable.setText("search a vocable");

		btn_saveNewVocable.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.SAVE));
		btn_saveNewVocable.setPrefWidth(50);
		btn_saveNewVocable.setOnAction((ActionEvent event) -> {
			saveVocable();
		});
	}

	@Override
	public void dispose() {
		try {
			toggleGroup.selectedToggleProperty().removeListener(radioToggleGroupChangeListener);
		} catch (Exception ex) {
		}
	}

}
