package com.matteoveroni.views.dictionary.listviews.cells;

import com.matteoveroni.customjavafxcomponents.EditableListViewCell;
import com.matteoveroni.views.dictionary.model.Translation;
import javafx.scene.control.ListCell;

/**
 *
 * @author Matteo Veroni
 */
public class TranslationCell extends ListCell<Translation> {
    
    EditableListViewCell editableListViewCell;

    public TranslationCell() {
        editableListViewCell = new EditableListViewCell();
    }
    
    

    @Override
	protected void updateItem(Translation translation, boolean empty) {
		super.updateItem(translation, empty);
		if (empty || translation == null) {
			setGraphic(null);
			setText(null);
		} else {
            editableListViewCell.setCellText(translation.getTranslation());
            setGraphic(editableListViewCell);
//			setText(translation.getTranslation());
		}
	}
}
