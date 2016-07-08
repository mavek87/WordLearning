package com.matteoveroni.views.dictionary;

import com.matteoveroni.bus.events.EventChangeView;
import com.matteoveroni.gson.GsonSingleton;
import com.matteoveroni.views.dictionary.model.DictionaryDAO;
import com.matteoveroni.views.ViewName;
import com.matteoveroni.views.dictionary.model.Dictionary;
import com.matteoveroni.views.dictionary.model.Translation;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
	private DictionaryDAO model;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	void goBack(ActionEvent event) {
		EventBus.getDefault().post(new EventChangeView(ViewName.MAINMENU));
	}

	@FXML
	void add(ActionEvent event) {
//		System.out.println("aaaaa " + GsonSingleton.getInstance().toJson(model.getDictionary()));
		Dictionary dictionary = model.getDictionary();
		
		dictionary.createWordAndTranslations("parola", new ArrayList<Translation>(new Translation("word")));

	}
}
