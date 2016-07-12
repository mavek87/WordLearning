package com.matteoveroni.views.dictionary.listviews.cells;

import com.matteoveroni.views.dictionary.model.Vocable;
import javafx.scene.control.ListCell;

/**
 *
 * @author Matteo veroni
 */
public class VocablesCell extends ListCell<Vocable> {

	@Override
	protected void updateItem(Vocable vocable, boolean empty) {
		super.updateItem(vocable, empty);
		if (empty || vocable == null) {
			setGraphic(null);
			setText(null);
		} else {
			setText(vocable.getName());
		}
	}
}
