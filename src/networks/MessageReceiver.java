package networks;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Отдельный поток для асинхронного получения Message из ObjectInputStream
 *
 * @author Alexander Vlasov
 */
public class MessageReceiver implements Runnable {
    private ObjectInputStream in;
    private MessageParser parser;
    private boolean interrupt;
    public MessageReceiver(ObjectInputStream in, MessageParser parser) {
        this.in = in;
        this.parser = parser;
    }


    @Override
    public void run() {
        while (!interrupt) {
            try {
//                System.out.println( "Пытаюсь принять сообщение");
                Message message = (Message) in.readObject();
//                System.out.println( "Сообщение " + message + " принято");
                parser.addMessage(message);
            } catch (IOException e) {
//                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        System.out.println("MessageReceiver (" + Thread.currentThread().getName() + ") returns");
    }

    public void interrupt() {
        System.out.println("Set MessageReceiver (" + Thread.currentThread().getName() + ") interrupt = true");
        interrupt = true;
        Thread.currentThread().interrupt();
    }

}
