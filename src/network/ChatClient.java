package network;

import model.Game;

import java.io.*;

public class ChatClient implements Runnable {
    private Client client;
    public static final String SHOT_REGEXP = "[0-1][0-9]\\s[0-1][0-9]|"+
                                             "[0-9]\\s[0-1][0-9]|"+
                                             "[0-9]\\s[0-9]";
    private volatile boolean isPlayerInterruptedChat = false;

    public ChatClient(Client client) {
        this.client = client;
    }

    public void run() {
        synchronized (Game.seaBattle) {
            String inputMessage = "";
            Thread sendThread = new Thread(new Runnable() {
                InputStreamReader inputStream = new InputStreamReader(System.in);
                BufferedReader bufferedReader = new BufferedReader(inputStream);
                public void run() {
                    while(!Thread.currentThread().isInterrupted()){
                        String outputMessage = null;
                        try {
                            if (bufferedReader.ready()) {
                                outputMessage = bufferedReader.readLine();
                                if(outputMessage.matches(SHOT_REGEXP)) {
                                    isPlayerInterruptedChat = true;
                                    client.sendData(outputMessage);
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
                    if(inputMessage.matches(SHOT_REGEXP)){
                        if(!isPlayerInterruptedChat){
                            client.sendData(inputMessage);
                            sendThread.interrupt();
                        }
                        Game.seaBattle.notify();
                        break;
                    }
                    System.out.println(inputMessage);
                } catch (ClassNotFoundException e) {
                    System.err.println("Object of an unknown type was recieved");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
        }
    }
}
