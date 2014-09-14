package network;

import model.Game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChatServer implements Runnable {
    private Server server;
    public static final String SHOT_REGEXP = "[0-1][0-9]\\s[0-1][0-9]|"+
                                             "[0-9]\\s[0-1][0-9]|"+
                                             "[0-9]\\s[0-9]";
    private volatile boolean isPlayerInterruptedChat = false;

    public ChatServer(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        synchronized (Game.seaBattle) {
            Thread sendThread = new Thread(new Runnable() {
                InputStreamReader inputStream = new InputStreamReader(System.in);
                BufferedReader bufferedReader = new BufferedReader(inputStream);
                public void run() {
                    while (!Thread.currentThread().isInterrupted()) {
                        String outputMessage = "";
                        try {
                            if (bufferedReader.ready()) {
                                outputMessage = bufferedReader.readLine();
                                if(outputMessage.matches(SHOT_REGEXP)) {
                                    isPlayerInterruptedChat = true;
                                    server.sendData(outputMessage);
                                    break;
                                }
                                server.sendData(outputMessage);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            sendThread.start();

            String inputMessage = "";
            while(true) {
                try {
                    inputMessage = (String) server.input.readObject();
                    if(inputMessage.matches(SHOT_REGEXP)){
                        if(!isPlayerInterruptedChat){
                            server.sendData(inputMessage);
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
                }
            }
        }

    }
}
