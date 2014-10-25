package view;

import common.Coord;
import common.ShootResult;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.Orientation;
import model.Player;
import model.Ship;
import networks.Network;
import networks.ObjectListener;
import networks.Special;
import sampleFX.FirstLineService;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Controller implements Initializable, ObjectListener {
    private Editor editor;
    private Player player;
    private List<Ship> myShips;
    private Network network;
    private boolean hostSelected;
    private boolean serverSelected;
    private InetAddress ip;
    private boolean connected;
    private boolean myTurn;
    private int turn;
    private int port = 20000;
    private boolean ready;
    private boolean enemyReady;
    private boolean gameIsGoing;
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
    private TabPane tabPane;
    @FXML
    private ToggleButton readyToggleButton;
    @FXML
    private ToggleGroup toggleConnection;

    @FXML
    private ToggleGroup toggleDeck;

    @FXML
    private ToggleGroup toggleServer;
    private ServerSocketHandler serverSocketHandler;
    private ObjectHandler objectHandler;

    private void setServerSocketHandler() {
        serverSocketHandler = new ServerSocketHandler();
        serverSocketHandler.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                try {
                    connectButton.setDisable(true);
                    network.go((Socket) (workerStateEvent.getSource().getValue()));
                    connectionEstablished();
                } catch (IOException e) {
                    connectButton.setDisable(false);
                    e.printStackTrace();
                    return;
                }
            }
        });

    }

    private void setObjectHandler(BlockingQueue queue) {

        objectHandler = new ObjectHandler(queue);
        objectHandler.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                Object object = workerStateEvent.getSource().getValue();

                if (object.getClass().equals(String.class)) {
                    chatTextArea.appendText(" Враг: " + (String) object);
                }
                if (object.equals(Special.Ready)) {
                    enemyReady = true;
                    chatTextArea.appendText(" Враг готов!" + "\n");

                    if (ready) {
                        gameIsGoing = true;
                        startPlay();
                    }
                }
                if (object.equals(Special.NotReady)) {
                    chatTextArea.appendText(" Враг не готов!" + "\n");
                    enemyReady = false;
                }
                if (object.getClass().equals(Coord.class)) {
                    chatTextArea.appendText(" Ход " + turn++ + "\n");
                    Coord coord = (Coord) object;
                    ShootResult shootResult = player.receiveShoot(coord);
                    displayShootMy(coord, shootResult);
                    if (shootResult.equals(ShootResult.MISSED)) {
                        myTurn = true;
                    }
                    if (shootResult.equals(ShootResult.KILLED)) {
                        if (player.isGameOver()) {
                            gameOver();
                            if (player.isEnemyLoose()) {
                                chatTextArea.appendText(" Я победил" + "\n");
                            } else {
                                chatTextArea.appendText(" Я проиграл" + "\n");

                            }

                        }
                    }
                }
                objectHandler.restart();
            }
        });
        objectHandler.start();
    }

    @FXML
    void clientSelected(ActionEvent event) {
        ipTextField.setEditable(true);
        hostSelected = false;
    }

    @FXML
    void myFieldClicked(MouseEvent event) {


    }

    private void connectionEstablished() {
        connected = true;
        networkTab.setDisable(true);
        miscTab.setDisable(true);
        gameTab.setDisable(false);
        editChatTextArea.setDisable(false);
        readyToggleButton.setDisable(false);
        connectButton.setDisable(true);
        network.getParser().registerListener(String.class, this);
        network.getParser().registerListener(Coord.class, this);
        network.getParser().registerListener(Special.class, this);
        BlockingQueue queue = new LinkedBlockingQueue();
        network.getParser().setEmergency(queue);
        setObjectHandler(queue);
    }
    @FXML
    void connect(ActionEvent event) {
        network = new Network();
        if (hostSelected) {
            serverSocketHandler.restart();
        } else {
            try {
                ip = InetAddress.getByName(ipTextField.getText());
            } catch (UnknownHostException e) {
                e.printStackTrace();
                return;
            }
            if (!network.setClientConnection(ip, port)) return;
            else connectionEstablished();
        }


    }

    @FXML
    void editChatKeyTyped(KeyEvent event) {

    }

    @FXML
    void editChatKeyReleased(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            chatTextArea.appendText(" Я: " + editChatTextArea.getText());
            network.getSender().sendObject(editChatTextArea.getText());
            editChatTextArea.setText("");
        }
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
        if (!myTurn) return;
        int x = (int) (event.getX() / 20);
        int y = (int) (event.getY() / 20);
        Coord coord = new Coord(x, y);
        ShootResult shootResult = player.turn(coord);
        if (shootResult == ShootResult.MISSED) {
            myTurn = false;
        }
        chatTextArea.appendText(" Ход " + turn++ + ": Бью " + coord + " " + shootResult + "\n");

    }

    @FXML
    void hostSelected(ActionEvent event) {
        ipTextField.setEditable(false);
        hostSelected = true;
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
//        clearMyFields();
        editor.placeAll();
        updateAmountAvailable();
        clearMyFields();
        paintMyShips(editGridPane, editor.getPlaced());
        paintMyShips(myFieldGridPane, editor.getPlaced());
//        editGridPane.getColumnConstraints().get(0).setFillWidth(true);
    }

    @FXML
    void clearField(ActionEvent event) {
        editor.clearAll();
        updateAmountAvailable();
        clearMyFields();
    }

    private void clearMyFields() {
        clearGridPane(editGridPane);
        clearGridPane(myFieldGridPane);
    }

    private void clearGridPane(GridPane gridPane) {
        ObservableList<Node> list = gridPane.getChildren();
        Node group = list.get(0);
        list.clear();
        list.add(group);
    }
    @FXML
    void setRest(ActionEvent event) {
        if (editor.placeRest()) {
            updateAmountAvailable();
            paintMyShips(editGridPane, editor.getPlaced());
            paintMyShips(myFieldGridPane, editor.getPlaced());

        }
    }

    private void paintMyShips(GridPane pane, List<Ship> ships) {
        for (Ship ship : ships)
            for (Coord coord : ship.getShipCoords()) {

                pane.add(new Text("  X"), coord.getX(), coord.getY());
            }
    }

    private void gameStarts() {
        readyToggleButton.setDisable(true);
    }
    private void updateAmountAvailable() {
        amountLabel1.setText(String.valueOf(editor.getAmountAvailable(1)));
        amountLabel2.setText(String.valueOf(editor.getAmountAvailable(2)));
        amountLabel3.setText(String.valueOf(editor.getAmountAvailable(3)));
        amountLabel4.setText(String.valueOf(editor.getAmountAvailable(4)));
    }
    @FXML
    void ready(ActionEvent event) {
        if (readyToggleButton.isSelected()) {
            if (checkReady()) {
                ready = true;
                network.getSender().sendObject(Special.Ready);
                chatTextArea.appendText(" Я готов!" + "\n");
                if (enemyReady) {
                    gameIsGoing = true;
                    startPlay();
                }
            } else {
                readyToggleButton.setSelected(false);
            }
        } else {
            ready = false;
            network.getSender().sendObject(Special.NotReady);
            chatTextArea.appendText(" Я не готов!" + "\n");
        }
    }

    private boolean checkReady() {
        if (editor == null || editor.getPlaced().size() != 10) {
            chatTextArea.appendText(" Сначала расставь все корабли" + "\n");
            return false;
        }
        if (!connected) {
            chatTextArea.appendText(" Сначала установи соединение с кем-либо!" + "\n");
            return false;
        }
        return true;
    }

    private boolean startPlay() {
        gameStarts();
        chatTextArea.appendText("Игра началась" + "\n");
        player = new Player(10, 10, "Z");
        player.setParser(network.getParser());
        player.setSender(network.getSender());
        player.setMyField(editor.getMyField());
        if (player.isIFirst()) {
            chatTextArea.appendText(" Мой ход" + "\n");
            myTurn = true;
        } else {
            chatTextArea.appendText(" Враг ходит первый" + "\n");
            myTurn = false;
        }
        readyToggleButton.setDisable(true);
        turn = 1;
        editTab.setDisable(true);
        return true;
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
        assert tabPane != null : "fx:id=\"tabPane\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert toggleConnection != null : "fx:id=\"toggleConnection\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert toggleDeck != null : "fx:id=\"toggleDeck\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert toggleServer != null : "fx:id=\"toggleServer\" was not injected: check your FXML file 'Sea-Battle.fxml'.";


    }

    FirstLineService service = new FirstLineService();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editor = new Editor();
        editor.setSelected(4);
        myShips = new ArrayList<>();
        hostSelected = true;
        serverSelected = false;
        editChatTextArea.setDisable(true);
        readyToggleButton.setDisable(true);
        ready = false;
        enemyReady = false;
        setAll(null);
        gameIsGoing = false;
        setServerSocketHandler();
    }

    private static final DataFormat shipImageDataFormat = new DataFormat("shipImage");

    @Override
    public void takeFromParser(Object object) {
        if (object.getClass().equals(String.class)) {
            chatTextArea.appendText(" Враг: " + (String) object);
        }
        if (object.equals(Special.Ready)) {
            enemyReady = true;
            chatTextArea.appendText(" Враг готов!" + "\n");

            if (ready) {
                gameIsGoing = true;
                startPlay();
            }
        }
        if (object.equals(Special.NotReady)) {
            chatTextArea.appendText(" Враг не готов!" + "\n");
            enemyReady = false;
        }
        if (object.getClass().equals(Coord.class)) {
            chatTextArea.appendText(" Ход " + turn++);
            Coord coord = (Coord) object;
            ShootResult shootResult = player.receiveShoot(coord);
            displayShootEnemy(coord, shootResult);
            if (shootResult.equals(ShootResult.MISSED)) {
                myTurn = true;
            }
            if (shootResult.equals(ShootResult.KILLED)) {
                if (player.isGameOver()) {
                    gameOver();
                    if (player.isEnemyLoose()) {
                        chatTextArea.appendText(" Я победил" + "\n");
                    } else {
                        chatTextArea.appendText(" Я проиграл" + "\n");

                    }

                }
            }
        }
    }

    private void gameOver() {
        chatTextArea.appendText(" Игра окончена" + "\n");
        readyToggleButton.setDisable(false);
        editTab.setDisable(false);
        miscTab.setDisable(false);
        networkTab.setDisable(false);
    }

    private void displayShootEnemy(Coord coord, ShootResult shootResult) {
        int x = coord.getX();
        int y = coord.getY();
        if (shootResult.equals(ShootResult.MISSED)) enemyFieldGridPane.add(new Text("  ."), x, y);
        else if (shootResult.equals(ShootResult.HURT)) enemyFieldGridPane.add(new Text("  *"), x, y);
        else if (shootResult.equals(ShootResult.KILLED)) {
            clearGridPane(enemyFieldGridPane);
            List<Ship> ships = player.getReconstructedShips();
            for (Ship ship : ships) {
                for (Coord coord2 : ship.getShipCoords()) {
                    enemyFieldGridPane.add(new Text("  Ж"), coord2.getX(), coord2.getY());
                }
            }
            List<Coord> wrecks = player.getWrecks();
            for (Coord coord2 : wrecks) {
                enemyFieldGridPane.add(new Text("  *"), coord2.getX(), coord2.getY());

            }
        }
    }

    private void displayShootMy(Coord coord, ShootResult shootResult) {
        int x = coord.getX();
        int y = coord.getY();
        ObservableList<Node> source = myFieldGridPane.getChildren();
        System.out.println(source.size());
        if (shootResult.equals(ShootResult.MISSED)) enemyFieldGridPane.add(new Text("  ."), x, y);
        else if (shootResult.equals(ShootResult.HURT)) enemyFieldGridPane.add(new Text("  *"), x, y);
        else if (shootResult.equals(ShootResult.KILLED)) {
            clearGridPane(enemyFieldGridPane);
            List<Ship> ships = player.getReconstructedShips();
            for (Ship ship : ships) {
                for (Coord coord2 : ship.getShipCoords()) {
                    enemyFieldGridPane.add(new Text("  Ж"), coord2.getX(), coord2.getY());
                }
            }
            List<Coord> wrecks = player.getWrecks();
            for (Coord coord2 : wrecks) {
                enemyFieldGridPane.add(new Text("  *"), coord2.getX(), coord2.getY());

            }
        }
    }
}
