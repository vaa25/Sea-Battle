package networksExample.server;

import common.Message;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatServer implements Runnable {

//	private static final Logger LOG = Logger.getLogger(ChatServer.class.getName());

    private int port;
    private ServerSocket server;
    private boolean shutdown;
    private BlockingQueue<Message> buffer = new LinkedBlockingQueue<>();
    private Collection<ConnectionHandler> handlers = new LinkedBlockingQueue<ConnectionHandler>();

    public ChatServer(int port) {
        this.port = port;
        createUnexpectedErrorHandler();

    }

    private void createUnexpectedErrorHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread t, Throwable e) {
//				LOG.error("Unexpected exceprion", e);
            }
        });
    }

    private void startServer() {
        try {
//			LOG.info("Starting server on port " + port);
            server = new ServerSocket(port);
//			LOG.info("Server started");
        } catch (IOException e) {
//			LOG.error("Unable start server on port " + port, e);
        }

        MessageSender msender = new MessageSender(buffer, handlers);
        Thread mthread = new Thread(msender);
        mthread.start();

        while (!shutdown) {
            Socket conn = null;
            try {
                System.out.println("Server: Жду подключение " + server);
                conn = server.accept();
                System.out.println("Server: Есть подключение " + conn);
//				LOG.info("New connection");
            } catch (IOException e) {
//				LOG.error("Connection not accepted", e);
            }
            ConnectionHandler handler = new ConnectionHandler(conn, buffer);
            handlers.add(handler);
            Thread thread = new Thread(handler);
            thread.start();
        }

    }


    @Override
    public void run() {
        startServer();
    }
}
