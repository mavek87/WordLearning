package com.matteoveroni.views.dictionary.listviews.cells;

import com.matteoveroni.views.dictionary.model.Vocable;
import javafx.scene.control.cell.TextFieldListCell;

/**
 *
 * @author Matteo veroni
 */
public class VocableCell extends TextFieldListCell<Vocable> {

    @Override
    public void updateItem(Vocable vocable, boolean empty) {
        super.updateItem(vocable, empty);
        if (empty || vocable == null) {
            setGraphic(null);
            setText(null);
        } else {
            setText(vocable.getName());
        }
    }
}
