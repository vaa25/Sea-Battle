package view.networks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.networks.serializators.Serializator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class NetworkClient {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private OutputStream out;
    private MyObjectInputStream in;
    private Socket socket;

    public NetworkClient(Socket socket, BlockingQueue queue) throws IOException {
        try {
            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();
            this.out = out;
            this.in = new MyObjectInputStream(in, queue);
            this.socket = socket;
        } catch (IOException e) {
            logger.error("Error of opening OutputStream or InputStream at " + socket, e);
            throw e;
        }
        new Thread(in).start();
    }

    public boolean send(Object object) {
        byte[] bytes = Serializator.debuild(object);
        try {
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            logger.error("Connection with server lost: ", e);
            return false;
        }
        return true;
    }

    public void close() {
        if (!socket.isClosed()) try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
