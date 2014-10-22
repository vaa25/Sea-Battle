package network;

import console.InnerMessageHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 */
public class ChatClient implements Runnable {
    private Client client;
    public static final String SHOT_REGEXP = "([1-9]{1}|[1][0-9]{1}|0)\\s([1-9]{1}|[1][0-9]{1}|0)";
    private volatile boolean breakInitializer = false;

    public ChatClient(Client client) {
        this.client = client;
    }

    public void run() {
        String inputMessage = "";
        Thread sendThread = new Thread(new Runnable() {
            InputStreamReader inputStream = new InputStreamReader(System.in);
            BufferedReader bufferedReader = new BufferedReader(inputStream);

            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    String outputMessage = null;
                    try {
                        if (bufferedReader.ready()) {
                            outputMessage = bufferedReader.readLine();
                            if (outputMessage.matches(SHOT_REGEXP)) {
                                breakInitializer = true;
                                client.sendData(outputMessage);
                                InnerMessageHandler.putMsg(outputMessage);
                                break;
                            }
                            client.sendData(outputMessage);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        sendThread.start();


        while (true) {
            try {
                inputMessage = (String) client.input.readObject();
                if (inputMessage.matches(SHOT_REGEXP)) {

                    if (!breakInitializer) {
                        client.sendData(inputMessage);
                        sendThread.interrupt();
                    }
                    break;
                }
                System.out.println(inputMessage);
            } catch (ClassNotFoundException e) {
                System.err.println("Object of an unknown type was received");
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
