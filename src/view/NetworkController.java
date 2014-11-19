package view;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.networks.NetworkClient;

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
    @FXML
    private Button connectButton;
    @FXML
    private Button disconnectButton;
    @FXML
    private TextField ipTextField;
    @FXML
    private AnchorPane networkAnchorPane;
    @FXML
    private ToggleGroup toggleConnection;
    @FXML
    private ToggleGroup toggleServer;
    @FXML
    private TitledPane p2pTitledPane;
    @FXML
    private AnchorPane contentElements;

    BlockingQueue received;
    private NetworkClient networkClient;

    boolean hostSelected;
    boolean serverSelected;
    private InetAddress ip;
    SimpleBooleanProperty connected;
    private int port = 30000;
    private Service serverSocketHandler;
    SimpleStringProperty chatMessage;

    @FXML
    void clientSelected(ActionEvent event) {
        ipTextField.setDisable(false);
        hostSelected = false;
    }

    @FXML
    void connect(ActionEvent event) {
        if (!serverSelected) {
//            networkClient = new NetworkClient();
            if (hostSelected) {
                serverSocketHandler.restart();
                connectVisuality();
            } else {
                try {
                    ip = InetAddress.getByName(ipTextField.getText());
                } catch (UnknownHostException e) {
                    chatMessage.set("IP " + ipTextField.getText() + " is incorrect. Enter correct IP\n");
                    chatMessage.set(null);
                    logger.error("IP " + ipTextField.getText() + " is incorrect", e);
                    return;
                }
                try {
                    Socket socket = new Socket(ip, port);
                    System.out.println(socket.isConnected());
                    logger.info(socket + " created");
                    networkClient = new NetworkClient(socket, received);
                    connectVisuality();
                    connectionEstablished();
                } catch (IOException e) {
                    chatMessage.set("Can't connect to host " + ip.toString() + "\n");
                    chatMessage.set(null);
                    logger.error("Can't connect to host " + ip.toString(), e);
                    return;
                }
            }
        } else {
            chatMessage.setValue("Server not available\n");
            chatMessage.setValue(null);
        }
    }

    @FXML
    void disconnect(ActionEvent event) {
        disconnectVisuality();
        if (serverSocketHandler.isRunning()) {
            serverSocketHandler.cancel();
        }
        chatMessage.set("Disconnecting\n");
        chatMessage.set(null);
        if (networkClient != null) networkClient.close();
        connected.set(false);
    }

    @FXML
    void hostSelected(ActionEvent event) {
        ipTextField.setDisable(true);
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
    void withServerSelected(ActionEvent event) {
        p2pTitledPane.setDisable(true);
        serverSelected = true;
    }

    @FXML
    void withoutServerSelected(ActionEvent event) {
        p2pTitledPane.setDisable(false);
        serverSelected = false;
    }

    private void connectVisuality() {
        connectButton.setDisable(true);
        contentElements.setDisable(true);
        disconnectButton.setDisable(false);

    }

    private void disconnectVisuality() {
        connectButton.setDisable(false);
        contentElements.setDisable(false);
        disconnectButton.setDisable(true);
    }

    private void connectionEstablished() {
        connected.set(true);
        chatMessage.setValue("Новое соединение установлено\n");
        chatMessage.setValue(null);
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
//                            logger.error("can't open serverSocket",e);
                        logger.info("try to accept new connection with serverSocket");
                        //                            logger.error(" accept serverSocket error",e);
                        logger.info(serverSocket.accept() + " accepted");
                        return serverSocket.accept();
                    }
                };
            }

            @Override
            public boolean cancel() {
                super.cancel();
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                        logger.info(" serverSocked closed");
                    } catch (IOException e) {
                        logger.error("can't close " + serverSocket, e);
                        return false;
                    }
                }
                return true;
            }
        };
        serverSocketHandler.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                disconnectVisuality();
                logger.error("serverSocketHandler Exception", event.getSource().getException());
                serverSocketHandler.cancel();
            }
        });
        serverSocketHandler.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                Socket socket = (Socket) (workerStateEvent.getSource().getValue());
                logger.info(socket + " handled");
                try {
                    networkClient = new NetworkClient(socket, received);
                    connectionEstablished();
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
    }


}
