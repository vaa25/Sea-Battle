package networks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Network {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Socket conn;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private HeartBeatSender heartBeatSender;
    private Thread noTimeOutThread;
    private ObjectSender sender;
    private ObjectReceiver receiver;
    private ObjectParser parser;
    private Thread receiverThread;
    private InetAddress host;
    private int port;

    public Network() {
    }

    ;

    public Network(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    private void go() throws IOException {
        //        logger.info( "Пытаюсь создать исходящий поток");
        out = new ObjectOutputStream(conn.getOutputStream());
//        logger.info( "Иcходящий поток успешно создан " + out);
//        logger.info( "Пытаюсь создать входящий поток");
        in = new ObjectInputStream(conn.getInputStream());
//        logger.info( "Входящий поток успешно создан " + in);
        sender = new ObjectSender(out);
        parser = new ObjectParser();
        receiver = new ObjectReceiver(in, parser);
        receiverThread = new Thread(receiver);
        heartBeatSender = new HeartBeatSender(sender);
        noTimeOutThread = new Thread(heartBeatSender);
        noTimeOutThread.start();
//        logger.info("network (" + Thread.currentThread().getName() + ") starts noTimeOutThread (" + noTimeOutThread.getName() + ")");
        receiverThread.start();
//        logger.info("network (" + Thread.currentThread().getName() + ") starts receiverThread (" + receiverThread.getName() + ")");

    }

    public void setAnyConnection() throws IOException {
        if (!setClientConnection(host, port)) setServerConnection(port);

    }

    public ObjectSender getSender() {
        return sender;
    }

    public ObjectParser getParser() {
        return parser;
    }

    public boolean setClientConnection(InetAddress host, int port) {
        logger.info("Устанавливаю клиентское соединение " + host + ":" + port);
        try {
            conn = new Socket(host, port);
            go();
        } catch (IOException e) {
            logger.error("Клиентское соединение установить невозможно \n", e);
            return false;
        }
        logger.info("Клиентское соединение установлено ", conn);
        return true;
    }

    public void setServerConnection(int port) throws IOException {
        logger.info("Устанавливаю серверное соединение");
        ServerSocket server = new ServerSocket(port);
        conn = server.accept();
        go();
        logger.info("Серверное соединение установлено ", conn);

    }

    public void close() {
        logger.info("Network (" + Thread.currentThread().getName() + ") try to interrupt noTimeOutThread (" + noTimeOutThread.getName() + ")");
//        noTimeOut.interrupt();
        noTimeOutThread.interrupt();
//        logger.info("Network (" + Thread.currentThread().getName() + ") try to interrupt receiverThread (" + receiverThread.getName() + ")");
////        receiver.interrupt();
//        receiverThread.interrupt();
        try {
            in.close();
            out.close();
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
//            logger.info("network (" + Thread.currentThread().getName() + ") try to join noTimeOutThread (" + noTimeOutThread.getName() + ")");
            noTimeOutThread.join();
//            logger.info("network (" + Thread.currentThread().getName() + ") noTimeOutThread (" + noTimeOutThread.getName() + ") joined");
        } catch (InterruptedException e) {
            logger.error("Network (" + Thread.currentThread().getName() + ") noTimeOutThread (" + noTimeOutThread.getName() + ") join InterruptedException");
        }
        try {
//            logger.info("network (" + Thread.currentThread().getName() + ") try to join receiverThread (" + receiverThread.getName() + ")");
            receiverThread.join();
//            logger.info("network (" + Thread.currentThread().getName() + ") receiverThread (" + receiverThread.getName() + ") joined");
        } catch (InterruptedException e) {
            logger.error("Network (" + Thread.currentThread().getName() + ") receiverThread (" + receiverThread.getName() + ") join InterruptedException");
        }
    }


}
