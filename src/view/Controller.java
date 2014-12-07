package view;

import common.Coord;
import common.ShootResult;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import model.Ship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.networks.NetworkSpecial;
import view.networks.ObjectHandler;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;


public class Controller implements Initializable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private SimpleStringProperty printChatMessage;
    private SimpleBooleanProperty connected;
    private SimpleObjectProperty sendObject;
    private Person myPerson;
    private SimpleBooleanProperty ready;
    private ObjectHandler objectHandler;
    private BlockingQueue received;
    private SimpleBooleanProperty gameOver;
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
            PaneService.refreshPane(playController.enemyFieldPane, new ArrayList<>());
            playController.myNameLabel.setText(myPerson.getName());
        }

    }

    @FXML
    void editTabSelected(Event event) {
        if (editTab.isSelected()) {
            editController.keepFocus();
        }
    }

    @FXML
    void miscTabSelected(Event event) {
        if (!miscTab.isSelected()) {
            miscController.nameEntered(null);
        }
    }
    private void setObjectHandler() {
        received = networkController.received;
        objectHandler = new ObjectHandler(received);
        objectHandler.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                Object object = workerStateEvent.getSource().getValue();
                logger.info("Принял " + object.toString());

                if (object.getClass().equals(String.class)) {
                    printChatMessage.set(playController.enemyPerson.getName() + ": " + object);
                    printChatMessage.set(null);

                } else if (object.equals(Command.Ready)) {
                    playController.enemyReady.set(true);

                } else if (object.equals(Command.NotReady)) {
                    playController.enemyReady.set(false);

                } else if (object.equals(NetworkSpecial.LostConnection)) {
                    connected.set(false);
                    return;

                } else if (object.equals(NetworkSpecial.Disconnect)) {
                    connected.set(false);
                    return;

                } else if (object.equals(Command.BreakPlay)) {
                    printChatMessage.set(" Бой прерван врагом\n");
                    playController.breakPlay();

                } else if (object.getClass().equals(ShootResult.class)) {
                    playController.setShootResult((ShootResult) object);

                } else if (object.getClass().equals(Double.class)) {
                    playController.setEnemyRandom((Double) object);

                } else if (object.getClass().equals(Person.class)) {
                    playController.enemyPerson = (Person) object;
                    playController.enemyNameLabel.setText(playController.enemyPerson.getName());

                } else if (object.getClass().equals(Coord.class)) {
                    playController.setCoord((Coord) object);

                } else if (object.getClass().equals(ArrayList.class)) {
                    playController.setEnemyShips((List<Ship>) object);
                }
                objectHandler.restart();
            }
        });
        objectHandler.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabPane.getSelectionModel().select(editTab);
        setPrintChatMessage();
        setConnected();
        setPlayIsGoing();
        setReady();
        setEnemyReady();
        setAllShipSetted();
        setSendObject();
        setGameOver();
        playTab.setDisable(true);
        myPerson = miscController.getPerson();
    }

    private void setGameOver() {
        SimpleBooleanProperty gameOver = new SimpleBooleanProperty(false);
        playController.gameOver = gameOver;
        gameOver.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue && !playController.iLoose) {
                    sendObject.set(playController.myField.getAliveShips());
                }
                playController.myField.clear();
            }
        });
    }

    private void setSendObject() {
        sendObject = new SimpleObjectProperty();
        playController.sendObject = sendObject;
        chatController.sendObject = sendObject;
        sendObject.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue != null) {
                    networkController.send(newValue);
                    sendObject.set(null);
                }
            }
        });
    }

    private void setAllShipSetted() {
        SimpleBooleanProperty allShipSetted = new SimpleBooleanProperty(false);
        playController.allShipSetted = allShipSetted;
        editController.allShipSetted = allShipSetted;
        allShipSetted.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    playTab.setDisable(false);
                    playController.readyToggleButton.setDisable(false);
                    playController.myField = editController.getMyField();
                } else {
                    playTab.setDisable(true);
                    playController.readyToggleButton.setDisable(true);
                }
            }
        });
    }

    private void setEnemyReady() {
        SimpleBooleanProperty enemyReady = new SimpleBooleanProperty(false);
        playController.enemyReady = enemyReady;
        enemyReady.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    printChatMessage.set(" Враг готов!\n");
                    if (playController.ready.getValue()) {

                        playController.startPlay();
                    }
                } else {
//                    printChatMessage.set(" Враг не готов!\n");
                }
            }


        });
    }

    private void setReady() {
        ready = new SimpleBooleanProperty(false);
        playController.ready = ready;
        ready.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    chatController.print(" Я готов!\n");
                    readyVisibilityOn();
                    if (connected.getValue()) {
                        networkController.send(Command.Ready);
                        if (playController.enemyReady.getValue()) {
                            playController.startPlay();
                        }
                    }
                } else {
//                    chatController.print(" Я не готов!\n");
                    readyVisibilityOff();
                    if (connected.getValue()) {
                        networkController.send(Command.NotReady);
                    }
                }
            }

            private void readyVisibilityOff() {
                editTab.setDisable(false);
                networkTab.setDisable(false);
            }

            private void readyVisibilityOn() {
                editTab.setDisable(true);
                if (connected.getValue()) networkTab.setDisable(true);
            }


        });
    }

    private void setPlayIsGoing() {
        SimpleBooleanProperty playIsGoing = new SimpleBooleanProperty(false);
        playController.playIsGoing = playIsGoing;
        playIsGoing.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    playIsGoingVisibilityOn();
                } else {
                    playIsGoingVisibilityOff();
                }
            }

            private void playIsGoingVisibilityOn() {
                playController.readyToggleButton.setDisable(true);
                playController.breakPlayButton.setDisable(false);
            }

            private void playIsGoingVisibilityOff() {
                playController.readyToggleButton.setDisable(false);
                playController.breakPlayButton.setDisable(true);

            }
        });
    }

    private void setConnected() {
        connected = new SimpleBooleanProperty(false);
        networkController.connected = connected;
        connected.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    connectedVisibilityOn();
                    setObjectHandler();
                    chatController.activate();
                    if (ready.getValue()) {
                        networkController.send(Command.Ready);
                    }
                    sendObject.set(myPerson);
                } else {
                    connectedVisibilityOff();
                    networkController.disconnect();
                    objectHandler.cancel();
                    chatController.disactivate();
                    playController.playIsGoing.set(false);
                }

            }

            private void connectedVisibilityOn() {
                miscTab.setDisable(true);
                if (ready.getValue()) networkTab.setDisable(true);
            }

            private void connectedVisibilityOff() {
                miscTab.setDisable(false);
            }
        });
    }

    private void setPrintChatMessage() {
        printChatMessage = new SimpleStringProperty();
        networkController.printChatMessage = printChatMessage;
        playController.printChatMessage = printChatMessage;
        editController.printChatMessage = printChatMessage;
        printChatMessage.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (t1 != null) {
                    chatController.print(t1);
                    printChatMessage.set(null);
                }
            }
        });
    }
}
