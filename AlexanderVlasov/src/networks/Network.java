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
    private InetAddress host;
    private int port;
    public Network(Main main, InetAddress host, int port) throws IOException {
        this.main = main;
        if (!setClientConnection(host, port)) setServerConnection(port);
        System.out.println(System.nanoTime() + " " + main.getName() + "Пытаюсь создать исходящий поток");
        out = new ObjectOutputStream(conn.getOutputStream());
        System.out.println(System.nanoTime() + " " + main.getName() + "Иcходящий поток успешно создан " + out);
        noTimeOut = new Thread(new NoTimeOut());
        System.out.println(System.nanoTime() + " " + main.getName() + "Пытаюсь создать входящий поток");
        in = new ObjectInputStream(conn.getInputStream());
        System.out.println(System.nanoTime() + " " + main.getName() + "Входящий поток успешно создан " + in);

        this.host = host;
        this.port = port;
    }


    public void interrupt() {
        interrupt = true;

    }

    public void sendMessage(Message message) throws IOException {
        System.out.println(System.nanoTime() + " " + main.getName() + "Пытаюсь послать сообщение " + message);
        out.writeObject(message);
        out.flush();
        System.out.println(System.nanoTime() + " " + main.getName() + "Сообщение " + message + " послано");
    }


    private boolean setClientConnection(InetAddress host, int port) {
        System.out.println(System.nanoTime() + " " + main.getName() + "Устанавливаю клиентское соединение");
        try {
            conn = new Socket(host, port);

        } catch (IOException e) {
            System.err.println(System.nanoTime() + " " + main.getName() + "Клиентское соединение установить невозможно \n" + e);
            return false;
        }
        System.out.println(System.nanoTime() + " " + main.getName() + "Клиентское соединение установлено " + conn);
        return true;
    }

    private void setServerConnection(int port) {
        System.out.println(System.nanoTime() + " " + main.getName() + "Устанавливаю серверное соединение");
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(System.nanoTime() + " " + main.getName() + "Unable start server on port " + port + "\n" + e);
        }
        try {
            conn = server.accept();
            System.out.println(System.nanoTime() + " " + main.getName() + "Серверное соединение установлено " + conn);
        } catch (IOException e) {
        }
    }


    @Override
    public void run() {
        noTimeOut.start();
        while (!interrupt) {
            try {
                System.out.println(System.nanoTime() + " " + main.getName() + "Пытаюсь принять сообщение");
                Message message = (Message) in.readObject();
                System.out.println(System.nanoTime() + " " + main.getName() + "Сообщение " + message + " принято");
                main.setMessage(message);
            } catch (IOException e) {
                System.out.print(System.nanoTime() + " " + main.getName());
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
        private int count = 0;
        @Override
        public void run() {
            while (!interrupt) {
                try {
                    System.out.println(System.nanoTime() + " " + main.getName() + "Пытаюсь послать NoTimeOut " + count);
                    sendMessage(new Message(Message.MessageType.NOTIMEOUT));
                    System.out.println(System.nanoTime() + " " + main.getName() + "NoTimeOut послан " + count++);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
