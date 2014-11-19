package view;

import common.Coord;
import common.ShootResult;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import model.Player;
import model.Ship;
import view.networks.NetworkSpecial;
import view.networks.ObjectHandler;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;


public class Controller implements Initializable {
    private Player player;
    private int turn;
    private boolean ready;
    private boolean gameIsGoing;
    private List<Ship> myShips;
    private ObjectHandler objectHandler;
    SimpleStringProperty chatMessage;
    SimpleBooleanProperty connected;
    private BlockingQueue received;
    @FXML
    private ChatController chatController;
    @FXML
    private MiscController miscController;
    @FXML
    private EditController editController;
    @FXML
    private PlayController playController;
    @FXML
    private NetworkController networkController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Tab editTab;

    @FXML
    private Tab playTab;

    @FXML
    private Tab miscTab;

    @FXML
    private Tab networkTab;

    @FXML
    private TabPane tabPane;

    @FXML
    void playTabSelected(Event event) {
        if (playTab.isSelected() && editController != null) {
            PaneService.refreshPane(playController.myFieldPane, editController.getPlaced());
        }

    }

    @FXML
    void editTabSelected(Event event) {
        if (editTab.isSelected()) {
            editController.keepFocus();
        }
    }

    private void gameStarts() {
//        chatTextArea.appendText("Игра началась" + "\n");
//        player = new Player(10, 10, "Z");
//        player.setParser(network.getParser());
//        player.setSender(network.getSender());
////        player.setMyField(editor.getMyField());
////        readyToggleButton.setDisable(true);
////        myFieldDisplay = new FieldDisplay(myFieldPane, player.getMyField());
////        enemyFieldDisplay = new FieldDisplay(enemyFieldPane, player.getEnemyField());
//        myFieldDisplay.paint();
//        enemyFieldDisplay.paint();
    }

    private boolean checkReady() {
//        if (editor == null || editor.getPlaced().size() != 10) {
//            chatTextArea.appendText(" Сначала расставь все корабли" + "\n");
//            return false;
////        }
//        if (!connected) {
//            chatTextArea.appendText(" Сначала установи соединение с кем-либо!" + "\n");
//            return false;
//        }
        return true;
    }

    private boolean startPlay() {

        gameStarts();
//        if (player.isIFirst()) {
//            chatTextArea.appendText(" Мой ход" + "\n");
//            myTurn = true;
//        } else {
//            chatTextArea.appendText(" Враг ходит первый" + "\n");
//            myTurn = false;
//        }
//        readyToggleButton.setDisable(true);
        turn = 1;
        editTab.setDisable(true);
        return true;
    }

    private void setObjectHandler() {
        received = networkController.received;
        objectHandler = new ObjectHandler(received);
        objectHandler.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                Object object = workerStateEvent.getSource().getValue();
                if (object.getClass().equals(String.class)) {
                    chatMessage.set(" Враг: " + object);
                }
                if (object.equals(Command.Ready)) {
                    playController.enemyReady = true;
                    chatMessage.set(" Враг готов!" + "\n");

                    if (ready) {
                        gameIsGoing = true;
                        Controller.this.startPlay();
                    }
                }
                if (object.equals(Command.NotReady)) {
                    chatMessage.set(" Враг не готов!" + "\n");
                    playController.enemyReady = false;
                }
                if (object.equals(NetworkSpecial.LostConnection)) {
                    networkController.disconnect(null);
                    objectHandler.cancel();
                    return;
                }
                if (object.getClass().equals(Coord.class)) {
                    chatMessage.set(" Ход " + turn++ + "\n");
                    Coord coord = (Coord) object;
                    ShootResult shootResult = player.receiveShoot(coord);
                    playController.myFieldDisplay.paint();
                    chatMessage.set(" Враг ударил в " + coord + ": " + shootResult + "\n");
                    if (shootResult.equals(ShootResult.MISSED)) {
                        playController.myTurn = true;
                    }
                    if (shootResult.equals(ShootResult.KILLED)) {
                        if (player.isGameOver()) {
                            Controller.this.gameOver();
                            gameIsGoing = false;
                        }
                    }
                }
                objectHandler.restart();
            }
        });
        objectHandler.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Main init");
        tabPane.getSelectionModel().select(editTab);
        myShips = new ArrayList<>();

        chatMessage = new SimpleStringProperty();
        networkController.chatMessage = chatMessage;
        playController.chatMessage = chatMessage;
        editController.chatMessage = chatMessage;
        chatMessage.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1 != null) chatController.print(t1);
            }
        });

        connected = new SimpleBooleanProperty(false);
        networkController.connected = connected;
        connected.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) setObjectHandler();
                else networkController.disconnect(null);

            }
        });

        SimpleBooleanProperty allShipSetted = new SimpleBooleanProperty(false);
        playController.allShipSetted = allShipSetted;
        editController.allShipSetted = allShipSetted;
        allShipSetted.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) playTab.setDisable(false);
                else playTab.setDisable(true);
            }
        });

        playTab.setDisable(true);


//        hostSelected = true;
//        serverSelected = false;
//        editChatTextArea.setDisable(true);
//        ready = false;
//        enemyReady = false;
////        setAll(null);
//        gameIsGoing = false;
//        setServerSocketHandler();
    }

    private void gameOver() {
//        chatTextArea.appendText(" Игра окончена" + "\n");
//        if (player.isMyLoose()) chatTextArea.appendText(" Я проиграл.\n");
//        else chatTextArea.appendText(" Я выиграл.\n");
//        readyToggleButton.setDisable(false);
        editTab.setDisable(false);
        miscTab.setDisable(false);
        networkTab.setDisable(false);
//        readyToggleButton.setSelected(false);
    }


}
