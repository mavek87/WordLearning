package com.matteoveroni.views.dictionary.listcells;

import com.matteoveroni.views.dictionary.model.Translation;
import javafx.scene.control.ListCell;

/**
 *
 * @author Matteo Veroni
 */
public class TranslationsListViewCell extends ListCell<Translation> {

	@Override
	protected void updateItem(Translation translation, boolean empty) {
		super.updateItem(translation, empty);
		if (empty || translation == null) {
			setGraphic(null);
			setText(null);
		} else {
			setText(translation.getTranslation());
		}
	}
}
