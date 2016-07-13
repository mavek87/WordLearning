package com.matteoveroni.customjavafxcomponents;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Matteo Veroni
 */
public class EditableListViewCell extends BorderPane {

	private static final String PATH_VISTA_FXML = "/com/matteoveroni/customjavafxcomponents/editableListViewCell.fxml";

	public EditableListViewCell() {
		caricaVistaFXMLFascicolo();
	}

	private void caricaVistaFXMLFascicolo() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_VISTA_FXML));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

}
