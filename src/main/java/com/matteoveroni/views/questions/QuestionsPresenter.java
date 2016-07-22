package com.matteoveroni.views.questions;

import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.dictionary.model.Vocable;
import com.matteoveroni.views.questions.model.QuestionsModel;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
public class QuestionsPresenter implements Initializable, Disposable {

    @FXML
    private Label lbl_vocable;
    @FXML
    private Button btn_confirmAnswer;
    @FXML
    private TextArea textArea_answer;

    private QuestionsModel model;

    private ChangeListener<String> changeListenerTextAreaAnswer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new QuestionsModel();
        clearView();
        changeListenerTextAreaAnswer = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.isEmpty()) {
                    btn_confirmAnswer.setVisible(false);
                } else {
                    btn_confirmAnswer.setVisible(true);
                }
            }
        };
        textArea_answer.textProperty().addListener(changeListenerTextAreaAnswer);
        getNextVocableIfPresentAndPopulateView();
    }

    @Subscribe
    public void onViewChanged(EventViewChanged eventViewChanged) {
        if (eventViewChanged.getCurrentViewName() == ViewName.QUESTIONS) {
            initialize(null, null);
            getNextVocableIfPresentAndPopulateView();
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        EventBus.getDefault().post(new EventChangeView(ViewName.MAINMENU));
    }

    @FXML
    void answerConfirmed(ActionEvent event) {
        String answer = textArea_answer.getText();
        clearView();
        Alert answerAlert = new Alert(AlertType.NONE);
        if (isAnswerForVocableCorrect(answer, model.getCurrentVocable())) {
            answerAlert.setAlertType(AlertType.INFORMATION);
            answerAlert.setTitle("Risposta esatta");
            answerAlert.setHeaderText("Corretto");
            answerAlert.setContentText("Risposta esatta");
        } else {
            answerAlert.setAlertType(AlertType.ERROR);
            answerAlert.setTitle("Risposta errata");
            answerAlert.setHeaderText("Errato");
            answerAlert.setContentText("Risposta errata");
        }
        answerAlert.showAndWait();
        getNextVocableIfPresentAndPopulateView();
    }

    private void getNextVocableIfPresentAndPopulateView() {
        if (model.hasNextVocable()) {
            drawVocableInTheView(model.getRandomVocable());
        } else {
            System.out.println("Finito esci");
        }
    }

    private boolean isAnswerForVocableCorrect(String answer, Vocable vocable) {
        return answer.toLowerCase().equals(vocable.getName().toLowerCase());
    }

    private void drawVocableInTheView(Vocable vocable) {
        lbl_vocable.setText(vocable.getName());
    }

    private void clearView() {
        lbl_vocable.setText("");
        textArea_answer.setText("");
        btn_confirmAnswer.setVisible(false);
    }

    @Override
    public void dispose() {
        try {
            textArea_answer.textProperty().removeListener(changeListenerTextAreaAnswer);
        } catch (Exception ex) {
        }
    }
}
