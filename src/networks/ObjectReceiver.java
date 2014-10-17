package networks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;

/**
 * Отдельный поток для асинхронного получения объектов из ObjectInputStream
 *
 * @author Alexander Vlasov
 */
public class ObjectReceiver implements Runnable {
    private ObjectInputStream in;
    private ObjectParser parser;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //    private boolean interrupt;
    public ObjectReceiver(ObjectInputStream in, ObjectParser parser) {
        this.in = in;
        this.parser = parser;
    }


    @Override
    public void run() {
        while (true) {
            try {
                logger.info(Thread.currentThread().getName() + " ObjectReceiver пытается принять произвольный объект");
                Object object = in.readObject();
                logger.info(Thread.currentThread().getName() + " ObjectReceiver принял объект " + object);
                if (object != Special.HeartBeat) parser.put(object);
            } catch (EOFException e) {
                logger.error(Thread.currentThread().getName() + " ObjectReceiver (" + Thread.currentThread().getName() + ") EOFException: ObjectInputStream closed first", e);
                break;
            } catch (SocketException e) {
                logger.error(Thread.currentThread().getName() + "ObjectReceiver (" + Thread.currentThread().getName() + ") SocketException: ObjectInputStream closed first", e);
                break;
            } catch (IOException e) {
                logger.error(Thread.currentThread().getName() + "ObjectReceiver (" + Thread.currentThread().getName() + ") IOException: ", e);
                break;
            } catch (ClassNotFoundException e) {
                logger.error(Thread.currentThread().getName() + "ObjectReceiver (" + Thread.currentThread().getName() + ") ClassNotFoundException: Unknown class received", e);
                break;
            }
        }
        logger.info(Thread.currentThread().getName() + " ObjectReceiver (" + Thread.currentThread().getName() + ") returns");
    }

//    public void interrupt() {
//        System.out.println("MessageReceiver (" + Thread.currentThread().getName() + ")set interrupt = true");
//        interrupt = true;
//        Thread.currentThread().interrupt();
//    }

}
