package console;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Alexey Nerodenko
 * Date: 13.10.14
 */
public class InnerMessageHandler {
    private static ArrayBlockingQueue<String> container = new ArrayBlockingQueue<String>(1);

    public static boolean isEmpty() {
        return container.isEmpty();
    }

    public static void putMsg(String s) {
        try {
            container.put(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getMsg() {
        try {
            return container.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }
}
