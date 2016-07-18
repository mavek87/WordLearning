package com.matteoveroni.customjavafxcomponents;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Matteo Veroni
 */
public class EditableListViewCell extends BorderPane {

    private static final String PATH_VISTA_FXML = "/com/matteoveroni/customjavafxcomponents/editableListViewCell.fxml";

    @FXML
    private TextField txt_cell;
    @FXML
    private Button btn_firstButton;
    @FXML
    private Button btn_secondButton;

    private boolean editable = false;

    public EditableListViewCell() {
        loadFXMLComponentView();
        
    }

    public String getCellText() {
        return txt_cell.getText();
    }

    public void setCellText(String cell_stringText) {
        this.txt_cell.setText(cell_stringText);
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @FXML
    void firstButtonAction(ActionEvent event) {

        if (editable) {

        } else {

        }

    }

    @FXML
    void secondButtonAction(ActionEvent event) {

        if (editable) {

        } else {

        }

    }

    private void loadFXMLComponentView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PATH_VISTA_FXML));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

}
