package networksExample.server;

import common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ConnectionHandler implements Runnable {

//	private static final Logger LOG = Logger.getLogger(ConnectionHandler.class.getName());

    private Socket conn;
    private BlockingQueue<Message> buffer;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ConnectionHandler(Socket conn, BlockingQueue<Message> buffer) {
        this.conn = conn;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(conn.getOutputStream());
            System.out.println("Server: Создал исходящий поток " + out.toString());
            System.out.println("Server: Пытаюсь создать ObjectInputStream");
            in = new ObjectInputStream(conn.getInputStream());
            System.out.println("Server: Создал входящий поток " + in.toString());
            send(new Message("Server: Connected."));
            while (true) {
                Message message = null;
                try {
                    System.out.println("Server: Try to read object");
                    message = (Message) in.readObject();
                    System.out.println("Server: Received message " + message);
                    buffer.add(message);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
//			LOG.error("IO error", e);
        }
    }

    public void send(Message message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
