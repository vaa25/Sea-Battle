package common;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Alexander Vlasov
 */
public class QueueTrace {
    private static BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    public static boolean add(String s) {
        return queue.add(s);
    }

    public static String take() throws InterruptedException {
        return queue.take();
    }

    public static synchronized void print() {
        while (true) {
            String s = null;
            try {
                s = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (s == null) break;
            else System.out.println(s);
        }
    }
}
