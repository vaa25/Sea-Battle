package common;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * отдельный поток для асинхронного получения Message из ObjectInputStream
 *
 * @author Alexander Vlasov
 */
public class MessageReceiver implements Runnable {
    private ObjectInputStream in;
    private MessageGetter main;

    public MessageReceiver(ObjectInputStream in, MessageGetter messageReceiver) {
        this.in = in;
        this.main = messageReceiver;
    }


    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(System.nanoTime() + " " + main.getName() + "Пытаюсь принять сообщение");
                Message message = (Message) in.readObject();
                System.out.println(System.nanoTime() + " " + main.getName() + "Сообщение " + message + " принято");
                main.getMessage(message);
            } catch (IOException e) {
                System.out.print(System.nanoTime() + " " + main.getName());
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
