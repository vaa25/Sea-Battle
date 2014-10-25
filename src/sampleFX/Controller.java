package sampleFX;

import common.Coord;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

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
    private Rectangle ship1;
    @FXML
    private Rectangle ship2;
    @FXML
    private GridPane gridPane;

    @FXML
    void keyPressed(KeyEvent event) {
    }

    @FXML
    void shipDragDone(DragEvent event) {
        System.out.println("label DragDone");
        /* the drag and drop gesture ended */
        /* if the data was successfully moved, clear it */
        if (event.getTransferMode() == TransferMode.MOVE) {
            label.setText("");
        }
        event.consume();

    }

    @FXML
    void shipDragDetected(MouseEvent event) {
        System.out.println("shipDragDetected");
        Dragboard db = ((Rectangle) event.getSource()).startDragAndDrop(TransferMode.ANY);

        /* Put a string on a dragboard */
        ClipboardContent content = new ClipboardContent();
        content.put(shipFormat, event.getSource());
        db.setContent(content);
        event.consume();
    }

    @FXML
    void gridPaneMouseMoved(MouseEvent event) {
        Coord coord = getGridCellCoord(event);
        int x = coord.getX();
        int y = coord.getY();
        label.setText(x + " " + y);

    }

    @FXML
    void gridPaneMouseClicked(MouseEvent event) {

    }

    @FXML
    void gridPaneDragDropped(DragEvent event) {
        /* data dropped */
        /* if there is a string data on dragboard, read it and use it */
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (event.getGestureSource().getClass() == Rectangle.class) {
//            gridPane.setText(db.getString());
//            Coord coord=getGridCellCoord(event);
//            int x=coord.getX();
//            int y=coord.getY();
//            gridPane.add((Node)event.getGestureSource(),x,y);
//            success = true;
//            System.out.println("gridPaneDragDropped at "+x+","+y);
        }
        /* let the source know whether the string was successfully
         * transferred and used */
        event.setDropCompleted(success);

        event.consume();
    }

    @FXML
    void gridPaneDragExited(DragEvent event) {
        System.out.println("gridPaneDragExited");
//        textField.setText("textField DragExited");
        event.consume();
    }

    @FXML
    void gridPaneDragEntered(DragEvent event) {


        /* the drag-and-drop gesture entered the target */
//    /* show to the user that it is an actual gesture target */
        if (event.getGestureSource().getClass() == Rectangle.class) {
            System.out.println("gridPaneDragEntered");
        }

        event.consume();
    }

    private Coord getGridCellCoord(MouseEvent event) {
        double cellWidth = gridPane.getWidth() / gridPane.getColumnConstraints().size();
        double cellHeight = gridPane.getHeight() / gridPane.getRowConstraints().size();
        int x = (int) (event.getX() / cellWidth);
        int y = (int) (event.getY() / cellHeight);
        return new Coord(x, y);
    }

    @FXML
    void gridPaneDragOver(DragEvent event) {
        System.out.println("gridPaneDragOver");
        /* data is dragged over the target */
        /* accept it only if it is not dragged from the same node
         * and if it has a string data */
        if (event.getGestureSource().getClass() == Rectangle.class) {
            /* allow for both copying and moving, whatever user chooses */
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }

        event.consume();
    }

    @FXML
    void initialize(URL url, ResourceBundle resourceBundle) {
        assert label != null : "fx:id=\"label\" was not injected: check your FXML file 'sample.fxml'.";
        FirstLineService service = new FirstLineService();
        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {

            @Override
            public void handle(WorkerStateEvent t) {
                System.out.println("done:" + t.getSource().getValue());
            }
        });
        service.start();

    }

    /** The custom format */
    private static final DataFormat shipFormat =
            new DataFormat("ship");
}
