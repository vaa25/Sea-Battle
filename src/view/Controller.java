package view;

import common.Coord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Orientation;
import model.Ship;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Rectangle ship1deck1;

    @FXML
    private Label amountLabel1;

    @FXML
    private Label amountLabel2;

    @FXML
    private Label amountLabel3;

    @FXML
    private Label amountLabel4;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ScrollPane chatScrollPane;

    @FXML
    private SplitPane chatSplitPane;

    @FXML
    private TextArea chatTextArea;

    @FXML
    private Button connectButton;

    @FXML
    private AnchorPane editAnchorPane;

    @FXML
    private TextArea editChatTextArea;

    @FXML
    private Tab editTab;

    @FXML
    private GridPane editGridPane;

    @FXML
    private GridPane enemyFieldGridPane;

    @FXML
    private Tab gameTab;

    @FXML
    private TextField ipTextField;

    @FXML
    private Button loadFieldButton;

    @FXML
    private AnchorPane micsAnchorPane;

    @FXML
    private Tab miscTab;

    @FXML
    private GridPane myFieldGridPane;

    @FXML
    private TextField nameTextField;

    @FXML
    private AnchorPane networkAncorPane;

    @FXML
    private Tab networkTab;

    @FXML
    private AnchorPane playAnchorPane;

    @FXML
    private RadioButton radioDeck1;

    @FXML
    private RadioButton radioDeck2;

    @FXML
    private RadioButton radioDeck3;

    @FXML
    private RadioButton radioDeck4;

    @FXML
    private Button saveFieldButton;

    @FXML
    private Button setAllButton;

    @FXML
    private Button setRestButton;

    @FXML
    private Button startButton;

    @FXML
    private TabPane tabPane;

    @FXML
    private ToggleGroup toggleConnection;

    @FXML
    private ToggleGroup toggleDeck;

    @FXML
    private ToggleGroup toggleServer;


    @FXML
    void clientSelected(ActionEvent event) {
    }

    @FXML
    void connect(ActionEvent event) {
    }

    @FXML
    void editChatKeyTyped(KeyEvent event) {
    }

    @FXML
    void editPaneContextMenuRequested(ContextMenuEvent event) {
    }

    @FXML
    void editPaneDragOver(DragEvent event) {
    }

    @FXML
    void editPaneKeyPressed(KeyEvent event) {
        editor.setOrientation(Orientation.Vertical);
    }

    @FXML
    void editPaneKeyReleased(KeyEvent event) {
        editor.setOrientation(Orientation.Horizontal);
    }

    @FXML
    void editPaneMouseClicked(MouseEvent event) {
    }

    @FXML
    void shipStartDrag(MouseEvent event) {
        /* drag was detected, start a drag-and-drop gesture*/
        /* allow any transfer mode */
//            Rectangle source= (Rectangle)event.getSource();
//        System.out.println(source.toString());
//            Dragboard db = source.startDragAndDrop(TransferMode.ANY);
//
//        /* Put a string on a dragboard */
//            ClipboardContent content = new ClipboardContent();
//            content.put(shipImageDataFormat, source);
//            db.setContent(content);
//
//            event.consume();
    }

    @FXML
    void editPaneMouseMoved(MouseEvent event) {

        int x = (int) event.getX() / 20;
        int y = (int) event.getY() / 20;
        Coord coord = new Coord(x, y);
        boolean canPlace = editor.isCanPlace(coord);
        if (canPlace) drawShip(editGridPane, coord, editor.getSelected(), Color.GREEN);
    }

    private void drawShip(GridPane pane, Coord coord, Ship ship, Color color) {

        for (int i = 0; i < ship.getSize(); i++) {

        }
    }


    @FXML
    void enemyFieldClicked(MouseEvent event) {
    }

    @FXML
    void hostSelected(ActionEvent event) {
    }

    @FXML
    void ipEntered(ActionEvent event) {
    }

    @FXML
    void loadField(ActionEvent event) {
    }

    @FXML
    void nameEntered(ActionEvent event) {
    }

    @FXML
    void radioDeckOnAction1(ActionEvent event) {
        editor.setSelected(1);
    }

    @FXML
    void radioDeckOnAction2(ActionEvent event) {
        editor.setSelected(2);
    }

    @FXML
    void radioDeckOnAction3(ActionEvent event) {
        editor.setSelected(3);
    }

    @FXML
    void radioDeckOnAction4(ActionEvent event) {
        editor.setSelected(4);
    }

    @FXML
    void saveField(ActionEvent event) {
    }

    @FXML
    void setAll(ActionEvent event) {
        editor.placeAll();
        updateAmountAvailable();
//        editGridPane.getColumnConstraints().get(0).setFillWidth(true);
    }

    @FXML
    void clearField(ActionEvent event) {
        editor.clearAll();
        updateAmountAvailable();
    }


    @FXML
    void setRest(ActionEvent event) {
        editor.placeRest();
        updateAmountAvailable();
    }

    private void updateAmountAvailable() {
        amountLabel1.setText(String.valueOf(editor.getAmountAvailable(1)));
        amountLabel2.setText(String.valueOf(editor.getAmountAvailable(2)));
        amountLabel3.setText(String.valueOf(editor.getAmountAvailable(3)));
        amountLabel4.setText(String.valueOf(editor.getAmountAvailable(4)));
    }

    @FXML
    void startPlay(ActionEvent event) {


    }

    @FXML
    void withServerSelected(ActionEvent event) {
    }

    @FXML
    void withoutServerSelected(ActionEvent event) {
    }

    @FXML
    void initialize() {
        assert amountLabel1 != null : "fx:id=\"amountLabel1\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert amountLabel2 != null : "fx:id=\"amountLabel2\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert amountLabel3 != null : "fx:id=\"amountLabel3\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert amountLabel4 != null : "fx:id=\"amountLabel4\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert anchorPane != null : "fx:id=\"anchorPane\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert chatScrollPane != null : "fx:id=\"chatScrollPane\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert chatSplitPane != null : "fx:id=\"chatSplitPane\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert chatTextArea != null : "fx:id=\"chatTextArea\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert connectButton != null : "fx:id=\"connectButton\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert editAnchorPane != null : "fx:id=\"editAnchorPane\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert editChatTextArea != null : "fx:id=\"editChatTextArea\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert editTab != null : "fx:id=\"editTab\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert editGridPane != null : "fx:id=\"elitGridPane\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert enemyFieldGridPane != null : "fx:id=\"enemyFieldGridPane\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert gameTab != null : "fx:id=\"gameTab\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert ipTextField != null : "fx:id=\"ipTextField\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert loadFieldButton != null : "fx:id=\"loadFieldButton\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert micsAnchorPane != null : "fx:id=\"micsAnchorPane\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert miscTab != null : "fx:id=\"miscTab\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert myFieldGridPane != null : "fx:id=\"myFieldGridPane\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert nameTextField != null : "fx:id=\"nameTextField\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert networkAncorPane != null : "fx:id=\"networkAncorPane\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert networkTab != null : "fx:id=\"networkTab\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert playAnchorPane != null : "fx:id=\"playAnchorPane\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert radioDeck1 != null : "fx:id=\"radioDeck1\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert radioDeck2 != null : "fx:id=\"radioDeck2\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert radioDeck3 != null : "fx:id=\"radioDeck3\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert radioDeck4 != null : "fx:id=\"radioDeck4\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert saveFieldButton != null : "fx:id=\"saveFieldButton\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert setAllButton != null : "fx:id=\"setAllButton\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert setRestButton != null : "fx:id=\"setRestButton\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert tabPane != null : "fx:id=\"tabPane\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert toggleConnection != null : "fx:id=\"toggleConnection\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert toggleDeck != null : "fx:id=\"toggleDeck\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert toggleServer != null : "fx:id=\"toggleServer\" was not injected: check your FXML file 'Sea-Battle.fxml'.";


    }

    private Editor editor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editor = new Editor();
        editor.setSelected(4);


    }

    private static final DataFormat shipImageDataFormat = new DataFormat("shipImage");
}
