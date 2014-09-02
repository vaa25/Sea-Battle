package networks;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Через этот класс можно посылать Message разными потоками через заданный ObjectOutputStream
 *
 * @author Alexander Vlasov
 */
public class MessageSender {
    private ObjectOutputStream objectOutputStream;

    public MessageSender(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public synchronized void sendMessage(Message message) {
//        System.out.println("Пытаюсь послать сообщение " + message);
        try {
            objectOutputStream.writeObject(message);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("Сообщение " + message + " послано");
    }
}
