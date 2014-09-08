package networks;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketException;

/**
 * Отдельный поток для асинхронного получения Message из ObjectInputStream
 *
 * @author Alexander Vlasov
 */
public class ObjectReceiver implements Runnable {
    private ObjectInputStream in;
    private ObjectParser parser;

    //    private boolean interrupt;
    public ObjectReceiver(ObjectInputStream in, ObjectParser parser) {
        this.in = in;
        this.parser = parser;
    }


    @Override
    public void run() {
        while (true) {
            try {
                System.out.println(Thread.currentThread().getName() + " ObjectReceiver пытается принять произвольный объект");
                Object object = in.readObject();
                System.out.println(Thread.currentThread().getName() + " ObjectReceiver принял объект " + object);
                if (object != Special.HeartBeat) parser.put(object);
            } catch (EOFException e) {
                System.out.println(Thread.currentThread().getName() + " ObjectReceiver (" + Thread.currentThread().getName() + ") EOFException: ObjectInputStream closed first");
//                e.printStackTrace();
                break;
            } catch (SocketException e) {
                System.out.println("ObjectReceiver (" + Thread.currentThread().getName() + ") SocketException: ObjectInputStream closed first");
//                e.printStackTrace();
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
        System.out.println(Thread.currentThread().getName() + " ObjectReceiver (" + Thread.currentThread().getName() + ") returns");
    }

//    public void interrupt() {
//        System.out.println("MessageReceiver (" + Thread.currentThread().getName() + ")set interrupt = true");
//        interrupt = true;
//        Thread.currentThread().interrupt();
//    }

}
