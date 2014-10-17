package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea chatArea;

    @FXML
    private SplitPane chatSplitPane;

    @FXML
    private TextArea editChatArea;

    @FXML
    private Tab editTab;

    @FXML
    private Tab gameTab;

    @FXML
    private Tab miscTab;

    @FXML
    private GridPane myField;

    @FXML
    private TextField nameTextField;

    @FXML
    private Tab networkTab;

    @FXML
    private AnchorPane playingArea;

    @FXML
    private RadioButton radioDeck1;

    @FXML
    private RadioButton radioDeck2;

    @FXML
    private RadioButton radioDeck3;

    @FXML
    private RadioButton radioDeck4;

    @FXML
    private ToggleGroup toggleConnection;

    @FXML
    private Button startButton;

    @FXML
    private ToggleGroup toggleDeck;

    @FXML
    private ToggleGroup toggleServer;


    @FXML
    void initialize() {
        assert chatArea != null : "fx:id=\"chatArea\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert chatSplitPane != null : "fx:id=\"chatSplitPane\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert editChatArea != null : "fx:id=\"editChatArea\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert editTab != null : "fx:id=\"editTab\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert gameTab != null : "fx:id=\"gameTab\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert miscTab != null : "fx:id=\"miscTab\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert myField != null : "fx:id=\"myField\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert nameTextField != null : "fx:id=\"nameTextField\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert networkTab != null : "fx:id=\"networkTab\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert playingArea != null : "fx:id=\"playingArea\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert startButton != null : "fx:id=\"startButton\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert radioDeck1 != null : "fx:id=\"radioDeck1\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert radioDeck2 != null : "fx:id=\"radioDeck2\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert radioDeck3 != null : "fx:id=\"radioDeck3\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert radioDeck4 != null : "fx:id=\"radioDeck4\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert toggleDeck != null : "fx:id=\"toggleDeck\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert toggleConnection != null : "fx:id=\"toggleConnection\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert toggleServer != null : "fx:id=\"toggleServer\" was not injected: check your FXML file 'Sea-Battle.fxml'.";


    }

}
