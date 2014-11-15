package view;

import common.Coord;
import common.ShootResult;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.DataFormat;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import model.Orientation;
import model.Player;
import model.Ship;
import networks.Network;
import networks.Special;

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


public class Controller implements Initializable {
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
    private boolean editPaneOut;
    private boolean keyPressed;
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
    private Pane editPane;

    @FXML
    private Pane enemyFieldPane;

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
    private Pane myFieldPane;

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
    private Pane shipyardPane;
    @FXML
    private ToggleGroup toggleServer;

    private ServerSocketHandler serverSocketHandler;
    private ObjectHandler objectHandler;
    private FieldDisplay myFieldDisplay;
    private FieldDisplay enemyFieldDisplay;
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
                    chatTextArea.appendText(" Враг: " + object);
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
                    myFieldDisplay.paint();
                    chatTextArea.appendText(" Враг ударил в " + coord + ": " + shootResult + "\n");
                    if (shootResult.equals(ShootResult.MISSED)) {
                        myTurn = true;
                    }
                    if (shootResult.equals(ShootResult.KILLED)) {
                        if (player.isGameOver()) {
                            gameOver();
                            gameIsGoing = false;
                        }
                    }
                }
                objectHandler.restart();
            }
        });
        objectHandler.start();
    }

    @FXML
    void gameTabSelected(Event event) {
        if (((Tab) (event.getSource())).isSelected()) {
            if (myFieldDisplay != null) {
                myFieldDisplay.paint();
            }
            if (enemyFieldDisplay != null) {
                enemyFieldDisplay.clear();
            }
        }
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
        network.getParser().registerEmergency(String.class);
        network.getParser().registerEmergency(Coord.class);
        network.getParser().registerEmergency(Special.class);
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
    void editPaneKeyPressed(KeyEvent event) {
        keyPressed = true;
        System.out.println(event.getText() + " pressed");
//        if (editPaneOut==false){
        editor.setOrientation(Orientation.Vertical);
//        }
    }

    @FXML
    void editPaneKeyReleased(KeyEvent event) {
        keyPressed = false;
        System.out.println(event.getText() + " released");
//        if (editPaneOut==false){
        editor.setOrientation(Orientation.Horizontal);
//        }
    }

    @FXML
    void editPaneMouseClicked(MouseEvent event) {
        editor.editPaneMouseClicked();
        refreshPane(editPane, editor.getPlaced());
//        if (keyPressed)editor.setOrientation(Orientation.Vertical);
    }
    @FXML
    void editPaneMouseExited(MouseEvent event) {
        refreshPane(editPane, editor.getPlaced());
        editPaneOut = true;
    }

    @FXML
    void editPaneMouseMoved(MouseEvent event) {
        editor.editPaneMouseMoved(event);
        editPaneOut = false;

    }




    @FXML
    void enemyFieldClicked(MouseEvent event) {

        if (!gameIsGoing || !myTurn) return;
        int x = (int) (event.getX() / 20);
        int y = (int) (event.getY() / 20);
        Coord coord = new Coord(x, y);
        if (player.getEnemyField().getCell(coord).isShoot()) {
            chatTextArea.appendText(" Этот квадрат уже обстрелян. Выберите другой квадрат.\n");
            return;
        }
        ShootResult shootResult = player.turn(coord);
        if (shootResult == ShootResult.MISSED) {
            myTurn = false;
        }
        chatTextArea.appendText(" Ход " + turn++ + ": Бью " + coord + " " + shootResult + "\n");
        enemyFieldDisplay.paint();
        if (shootResult == ShootResult.KILLED) {
            if (player.isGameOver()) {
                gameOver();
            }
        }
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
//        clearMyPanes();
        editor.placeAll();
        updateAmountAvailable();
        clearMyPanes();
//        editor.refreshEditPane();
        refreshPane(editPane, editor.getPlaced());
        refreshPane(myFieldPane, editor.getPlaced());

//        editPane.getColumnConstraints().get(0).setFillWidth(true);
    }

    public void refreshPane(Pane pane, List<Ship> placed) {
        clearPane(pane);
        for (Ship ship : placed) {
            drawShip(pane, ship, Color.BLACK);
        }
    }

    private void drawShip(Pane pane, Ship ship, Color color) {
        Coord coord = ship.getShipCoords()[0];
        Orientation orientation = ship.getOrientation();
        int width, height;
        if (orientation == Orientation.Horizontal) {
            width = ship.getSize();
            height = 1;
        } else {
            height = ship.getSize();
            width = 1;
        }
        Rectangle rectangle = new Rectangle(coord.getX() * 20, coord.getY() * 20, width * 20, height * 20);
        rectangle.setFill(color);
        pane.getChildren().add(rectangle);
    }
    @FXML
    void clearField(ActionEvent event) {
        editor.clearAll();
//        updateAmountAvailable();
        clearMyPanes();
        editor.setSelected((int) (toggleDeck.getSelectedToggle().getUserData()));
    }

    private void clearMyPanes() {
        clearPane(editPane);
        clearPane(myFieldPane);
    }


    private void clearPane(Pane gridPane) {
        ObservableList<Node> list = gridPane.getChildren();
        list.clear();
        drawGrid(gridPane);
    }

    private void drawGrid(Pane pane) {
        ObservableList<Node> nodes = pane.getChildren();
        for (int i = 0; i <= 10; i++) {
            Line line = new Line(i * 20, 0, i * 20, 10 * 20);
            Line line2 = new Line(0, i * 20, 10 * 20, i * 20);
            nodes.addAll(line, line2);
        }
    }
    @FXML
    void setRest(ActionEvent event) {
        if (editor.placeRest()) {
//            updateAmountAvailable();
//            paintMyShips(editPane, editor.getPlaced());
            refreshPane(editPane, editor.getPlaced());
            refreshPane(myFieldPane, editor.getPlaced());

        }
    }

    private void gameStarts() {
        chatTextArea.appendText("Игра началась" + "\n");
        player = new Player(10, 10, "Z");
        player.setParser(network.getParser());
        player.setSender(network.getSender());
        player.setMyField(editor.getMyField());
        readyToggleButton.setDisable(true);
        myFieldDisplay = new FieldDisplay(myFieldPane, player.getMyField());
        enemyFieldDisplay = new FieldDisplay(enemyFieldPane, player.getEnemyField());
        myFieldDisplay.paint();
        enemyFieldDisplay.paint();
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
        assert editPane != null : "fx:id=\"elitGridPane\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert enemyFieldPane != null : "fx:id=\"enemyFieldPane\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert gameTab != null : "fx:id=\"gameTab\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert ipTextField != null : "fx:id=\"ipTextField\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert loadFieldButton != null : "fx:id=\"loadFieldButton\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert micsAnchorPane != null : "fx:id=\"micsAnchorPane\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert miscTab != null : "fx:id=\"miscTab\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
        assert myFieldPane != null : "fx:id=\"myFieldPane\" was not injected: check your FXML file 'Sea-Battle.fxml'.";
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radioDeck1.setUserData(1);
        radioDeck2.setUserData(2);
        radioDeck3.setUserData(3);
        radioDeck4.setUserData(4);
        editor = new Editor(editPane);
        editor.setAmountLabel((int) (radioDeck1.getUserData()), amountLabel1);
        editor.setAmountLabel((int) (radioDeck2.getUserData()), amountLabel2);
        editor.setAmountLabel((int) (radioDeck3.getUserData()), amountLabel3);
        editor.setAmountLabel((int) (radioDeck4.getUserData()), amountLabel4);
        editor.initAvailable();
        editor.setSelected((int) (toggleDeck.getSelectedToggle().getUserData()));
        myShips = new ArrayList<>();
        hostSelected = true;
        serverSelected = false;
        editChatTextArea.setDisable(true);
        readyToggleButton.setDisable(true);
        ready = false;
        enemyReady = false;
//        setAll(null);
        gameIsGoing = false;
        setServerSocketHandler();
        drawGrid(myFieldPane);
        drawGrid(editPane);
        drawGrid(enemyFieldPane);
    }

    private static final DataFormat shipImageDataFormat = new DataFormat("shipImage");



    private void gameOver() {
        chatTextArea.appendText(" Игра окончена" + "\n");
        if (player.isMyLoose()) chatTextArea.appendText(" Я проиграл.\n");
        else chatTextArea.appendText(" Я выиграл.\n");
        readyToggleButton.setDisable(false);
        editTab.setDisable(false);
        miscTab.setDisable(false);
        networkTab.setDisable(false);
        readyToggleButton.setSelected(false);
    }


}
