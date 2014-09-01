package networksExample.client;

import common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;

public class MessageReceiver implements Runnable {

    private ObjectInputStream in;

    public MessageReceiver(ObjectInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        Message message = null;
        while (true) {
            try {
                System.out.println("Client: Try to read object");
                message = (Message) in.readObject();
                System.out.println("Client: Read message successfully");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            System.out.println("Client: " + message);
        }
    }

}
