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
    private PrintWriter out;
    private Scanner userInput;

    public ChatClient(InetAddress host, int port) {
        try {
            this.conn = new Socket(host, port);
            System.out.println(conn);
            InputStream inputStream = conn.getInputStream();
            System.out.println(inputStream);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            System.out.println(bufferedInputStream);
            in = new ObjectInputStream(bufferedInputStream);

            System.out.println("in = " + in.toString());
            out = new PrintWriter(new BufferedOutputStream(conn.getOutputStream()));
            System.out.println("out = " + out.toString());
            userInput = new Scanner(new BufferedInputStream(System.in));
            run();
        } catch (IOException e) {
            System.out.println("Unable to connect.");
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
            out.println(message);
            out.flush();
        }
    }

}
