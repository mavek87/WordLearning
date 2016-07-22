package com.matteoveroni.views.question;

import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.question.model.QuestionModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * FXML Controller class
 *
 * @author Matteo Veroni
 */
public class QuestionsPresenter implements Initializable {

	@FXML
	private Label lbl_vocable;
	@FXML
	private Button btn_confirmAnswer;
	@FXML
	private TextArea textArea_answer;

	private QuestionModel model;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Subscribe
	public void onViewChanged(EventViewChanged eventViewChanged) {
		if (eventViewChanged.getCurrentViewName() == ViewName.QUESTIONS) {
			clearView();
			model = new QuestionModel();
			textArea_answer.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (newValue.isEmpty()) {
						btn_confirmAnswer.setVisible(false);
					} else {
						btn_confirmAnswer.setVisible(true);
					}
				}
			});
			disegnaElementoNellaVista(model.estraiElementoDallaLista());
		}
	}

	@FXML
	void goBack(ActionEvent event) {
		EventBus.getDefault().post(new EventChangeView(ViewName.MAINMENU));
	}

	@FXML
	void answerConfirmed(ActionEvent event) {
		//todo controllo risposta
		clearView();
		disegnaElementoNellaVista(model.estraiElementoDallaLista());
	}

	private void disegnaElementoNellaVista(String vocable) {
		lbl_vocable.setText(vocable);
	}

	private void clearView() {
		lbl_vocable.setText("");
		btn_confirmAnswer.setVisible(false);
	}
}
