package networks;

import common.Main;
import common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class Network implements Runnable {

    private Main main;
    private Socket conn;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean interrupt;
    private Thread noTimeOut;
    private BlockingQueue<Message> buffer;
    public Network(Main main, InetAddress host, int port) throws IOException {

        if (!setClientConnection(host, port)) setServerConnection(port);
        in = new ObjectInputStream(conn.getInputStream());
        out = new ObjectOutputStream(conn.getOutputStream());
        this.main = main;
        noTimeOut = new Thread(new NoTimeOut());
        noTimeOut.start();
    }


    public void interrupt() {
        interrupt = true;

    }

    public void sendMessage(Message message) throws IOException {

        out.writeObject(message);
        out.flush();
    }


    private boolean setClientConnection(InetAddress host, int port) {
        System.out.println("устанавливаю клиентское соединение");
        try {
            conn = new Socket(host, port);

        } catch (IOException e) {
            System.err.println("Клиентское соединение установить невозможно \n" + e);
            return false;
        }
        System.out.println("Клиентское соединение установлено");
        return true;
    }

    private void setServerConnection(int port) {
        System.out.println("Устанавливаю серверное соединение");
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Unable start server on port " + port + "\n" + e);
        }
        try {
            conn = server.accept();
            System.out.println("Серверное соединение установлено");
        } catch (IOException e) {
        }
    }


    @Override
    public void run() {

        while (!interrupt) {
            try {
                Message message = (Message) in.readObject();
                System.out.println(message);
                main.setMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        try {
            noTimeOut.interrupt();
            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class NoTimeOut implements Runnable {
        @Override
        public void run() {
            while (!interrupt) {
                try {
                    sendMessage(new Message(Message.MessageType.NOTIMEOUT));
                    try {
                        Thread.sleep(20000);
                    } catch (InterruptedException e) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
