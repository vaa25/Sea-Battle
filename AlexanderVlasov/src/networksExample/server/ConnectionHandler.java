package networksExample.server;

import common.Message;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class ConnectionHandler implements Runnable {

//	private static final Logger LOG = Logger.getLogger(ConnectionHandler.class.getName());

    private Socket conn;
    private BlockingQueue<Message> buffer;
    private Scanner in;
    private PrintWriter out;

    public ConnectionHandler(Socket conn, BlockingQueue<Message> buffer) {
        this.conn = conn;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            in = new Scanner(new BufferedInputStream(conn.getInputStream()));
            System.out.println("in = " + in.toString());
            out = new PrintWriter(new BufferedOutputStream(conn.getOutputStream()));
            System.out.println("out = " + out.toString());
            send(new Message("Connected."));
            while (true) {
                String message = in.nextLine();
                buffer.add(new Message(message));
            }
        } catch (IOException e) {
//			LOG.error("IO error", e);
        }
    }

    public void send(Message message) {
        out.println(message);
        out.flush();

    }

}
