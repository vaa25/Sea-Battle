package networks;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Через этот класс можно посылать Message разными потоками через заданный ObjectOutputStream
 *
 * @author Alexander Vlasov
 */
public class ObjectSender {
    private ObjectOutputStream objectOutputStream;

    public ObjectSender(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public synchronized void sendMessage(Object object) {
        System.out.println(Thread.currentThread().getName() + " ObjectSender пытается послать сообщение " + object);
        try {
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " ObjectSender послал сообщение " + object);
    }
}
