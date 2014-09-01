package networksExample.client;

import java.io.IOException;
import java.io.ObjectInputStream;

public class MessageReceiver implements Runnable {

    private ObjectInputStream in;

    public MessageReceiver(ObjectInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        String message = null;
        while (true) {
            try {
                message = in.readObject().toString();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println(message);
        }
    }

}
