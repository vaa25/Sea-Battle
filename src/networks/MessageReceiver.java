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
public class MessageReceiver implements Runnable {
    private ObjectInputStream in;
    private MessageParser parser;

    //    private boolean interrupt;
    public MessageReceiver(ObjectInputStream in, MessageParser parser) {
        this.in = in;
        this.parser = parser;
    }


    @Override
    public void run() {
        while (true) {
            try {
//                System.out.println( "Пытаюсь принять сообщение");
                Message message = (Message) in.readObject();
//                System.out.println( "Сообщение " + message + " принято");
                parser.addMessage(message);
            } catch (EOFException e) {
                System.out.println("MessageReceiver (" + Thread.currentThread().getName() + ") EOFException: ObjectInputStream closed first");
//                e.printStackTrace();
                break;
            } catch (SocketException e) {
                System.out.println("MessageReceiver (" + Thread.currentThread().getName() + ") SocketException: ObjectInputStream closed first");
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
        System.out.println("MessageReceiver (" + Thread.currentThread().getName() + ") returns");
    }

//    public void interrupt() {
//        System.out.println("MessageReceiver (" + Thread.currentThread().getName() + ")set interrupt = true");
//        interrupt = true;
//        Thread.currentThread().interrupt();
//    }

}
