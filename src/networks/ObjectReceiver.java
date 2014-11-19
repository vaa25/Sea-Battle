package networks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import view.Command;

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
                logger.info(Thread.currentThread().getName() + " пытается принять произвольный объект");
                Object object = in.readObject();
                logger.info(Thread.currentThread().getName() + " принял объект " + object);
                if (object != Command.HeartBeat) parser.put(object);
            } catch (EOFException e) {
                logger.error(Thread.currentThread().getName() + " EOFException: ObjectInputStream closed first:", e);
                close();
                break;
            } catch (SocketException e) {
                logger.error(Thread.currentThread().getName() + " SocketException: ObjectInputStream closed first:", e);
                close();
                break;
            } catch (IOException e) {
                logger.error(Thread.currentThread().getName() + " IOException:", e);
                close();
                break;
            } catch (ClassNotFoundException e) {
                logger.error(Thread.currentThread().getName() + " ClassNotFoundException: Unknown class received:", e);
                break;
            }
        }
        logger.info(Thread.currentThread().getName() + " returns");
    }
    private void close(){
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public void interrupt() {
//        System.out.println("MessageReceiver (" + Thread.currentThread().getName() + ")set interrupt = true");
//        interrupt = true;
//        Thread.currentThread().interrupt();
//    }

}
