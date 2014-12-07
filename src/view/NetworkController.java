package view;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.networks.NetworkClient;
import view.networks.NetworkSpecial;

import java.io.IOException;
import java.net.*;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Alexander Vlasov
 */
public class NetworkController implements Initializable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    BlockingQueue received;
    boolean hostSelected;
    boolean serverSelected;
    SimpleBooleanProperty connected;
    SimpleBooleanProperty waitingConnection;
    SimpleStringProperty printChatMessage;
    @FXML
    private Button connectButton;
    @FXML
    private Button disconnectButton;
    @FXML
    private TextField ipTextField;
    @FXML
    private TextField portTextField;
    @FXML
    private AnchorPane networkAnchorPane;
    @FXML
    private ToggleGroup toggleConnection;
    @FXML
    private ToggleGroup toggleServer;
    @FXML
    private TitledPane p2pTitledPane;
    @FXML
    private VBox personListVBox;
    @FXML
    private TabPane networkModeTabPane;
    private NetworkClient networkClient;
    private InetAddress ip;
    private int port = 30000;
    private Service serverSocketHandler;

    public boolean send(Object object) {
        return networkClient.send(object);
    }

    @FXML
    void personListVBoxMouseClicked(MouseEvent event) {

    }
    @FXML
    void clientSelected(ActionEvent event) {
        ipTextField.setDisable(false);
        portTextField.setDisable(false);
        hostSelected = false;
    }

    @FXML
    void connect(ActionEvent event) {
        if (!serverSelected) {
//            networkClient = new NetworkClient();
            if (hostSelected) {
                waitConnectionAsHost();

            } else {
                connectToHostAsClient();
            }
        } else {
            printChatMessage.setValue("Server not available\n");
            printChatMessage.setValue(null);
        }
    }

    private void connectToHostAsClient() {
        try {
            ip = InetAddress.getByName(ipTextField.getText());
        } catch (UnknownHostException e) {
            printChatMessage.set("IP " + ipTextField.getText() + " is incorrect. Enter correct IP\n");
            printChatMessage.set(null);
            logger.error("IP " + ipTextField.getText() + " is incorrect", e);
            return;
        }
        try {
            Socket socket = new Socket(ip, port);
            logger.info(socket + " created");
            networkClient = new NetworkClient(socket, received);
            connectingVisuality();
            connected();
        } catch (IOException e) {
            printChatMessage.set("Can't connect to host " + ip.toString() + "\n");
            printChatMessage.set(null);
            logger.error("Can't connect to host " + ip.toString(), e);
            return;
        }
    }
    @FXML
    void disconnect(ActionEvent event) {
        if (networkClient != null) networkClient.send(NetworkSpecial.Disconnect);
        waitingConnection.set(false);
        connected.set(false);
    }

    void disconnect() {
        disconnectVisuality();
        if (serverSocketHandler.isRunning()) {
            if (!serverSocketHandler.cancel()) {
                logger.error("Can't cancel serverSocketHandler");
            }
        }
        printChatMessage.set("Disconnecting\n");
        printChatMessage.set(null);
        if (networkClient != null) {
            networkClient.close();
            networkClient = null;
        }
        waitingConnection.set(false);
    }
    @FXML
    void hostSelected(ActionEvent event) {
        ipTextField.setDisable(true);
        portTextField.setDisable(true);
        hostSelected = true;
    }

    @FXML
    void ipEntered(ActionEvent event) {
        try {
            ip = InetAddress.getByName(ipTextField.getText());
        } catch (UnknownHostException e) {
            ipTextField.clear();
        }
    }

    @FXML
    void portEntered(ActionEvent event) {
        try {
            int port = Integer.valueOf(ipTextField.getText());
            if (port < 1000 || port > 65535) ipTextField.clear();
            else this.port = port;
        } catch (NumberFormatException e) {
            ipTextField.clear();
        }
    }

    @FXML
    void withServerSelected(ActionEvent event) {
        p2pTitledPane.setDisable(true);
        serverSelected = true;
    }

    @FXML
    void withoutServerSelected(ActionEvent event) {
        p2pTitledPane.setDisable(false);
        serverSelected = false;
    }

    void connectingVisuality() {
        connectButton.setDisable(true);
        networkModeTabPane.setDisable(true);
        disconnectButton.setDisable(false);

    }

    void disconnectVisuality() {
        connectButton.setDisable(false);
        networkModeTabPane.setDisable(false);
        disconnectButton.setDisable(true);
    }

    private void connected() {
        connected.set(true);
        printChatMessage.setValue("Новое соединение установлено\n");
        printChatMessage.setValue(null);
    }

    private void waitConnectionAsHost() {
        serverSocketHandler.restart();
    }
    private void setServerSocketHandler() {
        serverSocketHandler = new Service() {
            ServerSocket serverSocket;

            @Override
            protected Task createTask() {
                logger.info("new task for serverSocketHandler created");
                return new Task() {
                    @Override
                    protected Socket call() throws IOException {
                        serverSocket = new ServerSocket(port);
                        waitingConnection.set(true);
                        Platform.runLater(() -> connectingVisuality());

                        logger.info("try to accept new connection with serverSocket");
                        Socket socket = serverSocket.accept();
                        if (socket.isConnected()) logger.info(socket + " connected");
                        return socket;
                    }
                };
            }

            @Override
            public boolean cancel() {
                super.cancel();
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                        logger.info(serverSocket + " closed");
                    } catch (IOException e) {
                        logger.error("can't close " + serverSocket, e);
                        return false;
                    }
                }
                waitingConnection.set(false);
                return true;
            }
        };
        serverSocketHandler.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                disconnectVisuality();
                logger.error("serverSocketHandler Exception", event.getSource().getException());
                serverSocketHandler.cancel();
                waitingConnection.set(false);
            }
        });
        serverSocketHandler.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                Socket socket = (Socket) (workerStateEvent.getSource().getValue());
                logger.info(socket + " handled");
                try {
                    received.clear();
                    networkClient = new NetworkClient(socket, received);
                    connected();
                } catch (IOException e) {
                    logger.error("Can't create networkClient", e);
                    e.printStackTrace();
                    return;
                }
            }
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        received = new LinkedBlockingQueue<>();
        setServerSocketHandler();
        withoutServerSelected(null);
        hostSelected(null);

        waitingConnection = new SimpleBooleanProperty(false);
        waitingConnection.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (!t1) {
                    if (!connected.getValue()) {
                        disconnectVisuality();
                        serverSocketHandler.cancel();
                    }

                }
            }
        });
    }


}
