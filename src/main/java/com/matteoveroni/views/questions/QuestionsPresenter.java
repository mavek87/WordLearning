package com.matteoveroni.views.questions;

import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.bus.events.EventViewChanged;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.dictionary.model.Vocable;
import com.matteoveroni.views.questions.model.QuestionsModel;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.utils.FontAwesomeIconFactory;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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
    private Button btn_goBack;
    @FXML
    private TextArea textArea_answer;
    @FXML
    private BorderPane borderPane_trainingFinished;
    @FXML
    private VBox vbox_questionsPanel;

    private QuestionsModel model;
    private ChangeListener<String> changeListenerTextAreaAnswer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new QuestionsModel();
        clearView();

        if (btn_goBack.getGraphic() == null) {
            btn_goBack.setGraphic(FontAwesomeIconFactory.get().createIcon(FontAwesomeIcon.REPLY, "1.3em"));
        }

        changeListenerTextAreaAnswer = (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.isEmpty()) {
                btn_confirmAnswer.setVisible(false);
            } else {
                btn_confirmAnswer.setVisible(true);
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
        String answer = textArea_answer.getText().toLowerCase();
        String questionVocable = lbl_vocable.getText().toLowerCase();
        Alert answerAlert = new Alert(AlertType.NONE);
        if (isAnswerForVocableCorrect(answer, model.getCurrentVocable())) {
            answerAlert.setAlertType(AlertType.INFORMATION);
            answerAlert.setTitle("Risposta corretta");
            answerAlert.setHeaderText("Risposta corretta");
            answerAlert.setContentText("Una traduzione di " + questionVocable + " è " + answer);
        } else {
            answerAlert.setAlertType(AlertType.ERROR);
            answerAlert.setTitle("Risposta errata");
            answerAlert.setHeaderText("Risposta errata");
            answerAlert.setContentText(answer + " non è una traduzione di " + questionVocable);
        }
        clearView();
        answerAlert.showAndWait();
        getNextVocableIfPresentAndPopulateView();
    }

    private void getNextVocableIfPresentAndPopulateView() {
        if (model.hasNextVocable()) {
            drawVocableInTheView(model.getRandomVocable());
        } else {
            vbox_questionsPanel.setVisible(false);
            borderPane_trainingFinished.setVisible(true);
        }
    }

    private boolean isAnswerForVocableCorrect(String answer, Vocable vocable) {
        return model.isAnswerForVocableRight(answer.toLowerCase(), vocable);
    }

    private void drawVocableInTheView(Vocable vocable) {
        lbl_vocable.setText(vocable.getName());
    }

    private void clearView() {
        lbl_vocable.setText("");
        textArea_answer.setText("");
        btn_confirmAnswer.setVisible(false);
        vbox_questionsPanel.setVisible(true);
        borderPane_trainingFinished.setVisible(false);
    }

    @Override
    public void dispose() {
        try {
            textArea_answer.textProperty().removeListener(changeListenerTextAreaAnswer);
        } catch (Exception ex) {
        }
    }
}
