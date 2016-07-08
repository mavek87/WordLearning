package com.matteoveroni.views.dictionary;

import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.views.dictionary.model.DictionaryDAO;
import com.matteoveroni.views.ViewName;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.inject.Inject;
import org.greenrobot.eventbus.EventBus;

/**
 * FXML Controller class
 *
 * @author Matteo Veroni
 */
public class DictionaryPresenter implements Initializable {

	@Inject
	private DictionaryDAO dao;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    @FXML
    void goBack(ActionEvent event) {
        EventBus.getDefault().post(new EventChangeView(ViewName.MAINMENU));
    }
}
