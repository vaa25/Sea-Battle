package networksExample.client;

import common.Message;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

//	private static final Logger LOG = Logger.getLogger(ChatClient.class.getName());

    private Socket conn;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Scanner userInput;

    public ChatClient(InetAddress host, int port) {
        try {
            this.conn = new Socket(host, port);
            System.out.println("Client: Есть подключение " + conn);
            InputStream inputStream = conn.getInputStream();
            System.out.println("Client: создал входящий поток " + inputStream);

            System.out.println("Client: Пытаюсь создать ObjectInputStream");
            in = new ObjectInputStream(inputStream);
            System.out.println("Client: in = " + in.toString());
            out = new ObjectOutputStream(conn.getOutputStream());
            System.out.println("Client: out = " + out.toString());
            userInput = new Scanner(new BufferedInputStream(System.in));
            run();
        } catch (IOException e) {
            System.out.println("Client: Unable to connect.");
            e.printStackTrace();
//			LOG.error("Unable to connect.", e);
        }

    }

    private void run() {
        MessageReceiver receiver = new MessageReceiver(in);
        Thread thread = new Thread(receiver);
        thread.start();


        Message message = null;
        while (true) {
            message = new Message(userInput.nextLine());
            try {
                out.writeObject(message);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
