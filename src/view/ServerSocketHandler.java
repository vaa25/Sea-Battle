package view;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Alexander Vlasov
 */
public class ServerSocketHandler extends Service {

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Socket call() throws Exception {
                return new ServerSocket(20000).accept();
            }
        };
    }
}