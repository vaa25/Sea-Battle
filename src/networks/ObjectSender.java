package networks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Через этот класс можно посылать объекты через заданный ObjectOutputStream
 *
 * @author Alexander Vlasov
 */
public class ObjectSender {
    private ObjectOutputStream objectOutputStream;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ObjectSender(ObjectOutputStream objectOutputStream) {
        this.objectOutputStream = objectOutputStream;
    }

    public synchronized void sendObject(Object object) {
        logger.info(Thread.currentThread().getName() + " пытается послать сообщение " + object);
        try {
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
        } catch (IOException e) {
            logger.error(Thread.currentThread().getName(), e);
            try {
                objectOutputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        logger.info(Thread.currentThread().getName() + " послал сообщение " + object);
    }
}
