package sampleFX;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label label;

    @FXML
    private GridPane root;
    @FXML
    private TextField textField;

    @FXML
    void keyPressed(KeyEvent event) {
    }

    @FXML
    void labelDragDetected(MouseEvent event) {
        System.out.println("label DragDetected");
        Dragboard db = label.startDragAndDrop(TransferMode.ANY);

        /* Put a string on a dragboard */
        ClipboardContent content = new ClipboardContent();
        content.putString(label.getText());
        db.setContent(content);

        event.consume();
    }

    @FXML
    void textFieldDragEntered(DragEvent event) {
        System.out.println("textField DragEnter");

        /* the drag-and-drop gesture entered the target */
//    /* show to the user that it is an actual gesture target */
        if (event.getGestureSource() != textField &&
                event.getDragboard().hasString()) {
            textField.setText("textField DragEnter");
        }

        event.consume();
    }

    @FXML
    void textFieldDragOver(DragEvent event) {
        System.out.println("textField DragOver");
        /* data is dragged over the target */
        /* accept it only if it is not dragged from the same node
         * and if it has a string data */
        if (event.getGestureSource() != textField &&
                event.getDragboard().hasString()) {
            /* allow for both copying and moving, whatever user chooses */
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }

        event.consume();
    }

    @FXML
    void initialize() {
        assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'sample.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'sample.fxml'.";


    }

}
